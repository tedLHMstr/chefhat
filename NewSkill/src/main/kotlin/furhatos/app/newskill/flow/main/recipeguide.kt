package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.nlu.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.Number
import furhatos.nlu.common.Thanks

val RecipeGuide = state(Parent) {
    var index = 0;

    onEntry {
        furhat.say("I will now guide you through this recipe. When you are done with a step, just tell me and we continue.")
        furhat.say("I have the ability to keep track of the time, so just tell me if you want me to set a timer.")
        reentry()
    }

    onReentry {
        if (index < users.current.userData.currentRecipe.getSteps().size) {
            println("step: "+users.current.userData.currentRecipe.getSteps()[index].getStep())
            furhat.say(users.current.userData.currentRecipe.getSteps()[index].getStep())
            furhat.listen()
        } else {
            furhat.say("We are done! I hope it tastes awesome.")
            goto(EndSession)
        }
    }

    onResponse<RepeatStep> {
        reentry()
    }

    onResponse<NextStep> {
        index += 1
        reentry()
    }

    onResponse<SetTimer> {
        println("SetTimer entered")
        furhat.ask("Alright, for how long?")
    }

    onResponse<AskAmount> {
        val ingredients = it.intent.ingredients?.list
        println(ingredients)
        var amount = ""
        if (ingredients != null) {
            for (ingredient in users.current.userData.currentRecipe.getSteps()[index].getIngredients()) {
                for (askedIng in ingredients) {
                    println("$askedIng, ${ingredient.getIngredientName()}")
                    if (askedIng.toString().equals(ingredient.getIngredientName(), ignoreCase = true)) {
                        amount += "${ingredient.tellIngredientPlusAmount()}, "
                    }
                }
            }
            if (amount == "") {
                furhat.say("You should not use ${ingredients.joinToString(", ")} in this step..")
            } else {
                furhat.say(amount)
            }
        } else if(users.current.userData.currentRecipe.getSteps()[index].getIngredients().isNotEmpty()) {
            furhat.say("That would be: ${users.current.userData.currentRecipe.getSteps()[index].getIngredients().joinToString(", ", "", "", 10, "") { it -> it.tellIngredientPlusAmount() }}")
        } else {
            furhat.say("Go by your gut feeling, I have no clue..")
        }

        furhat.listen()
    }

    onResponse<TimerTime>(partial = listOf(SetTimer())) {
        parallel {
            if (it.intent.time != null && it.intent.minOrSec != null) {
                furhat.say("Alright, setting a timer for ${it.intent.time} ${it.intent.minOrSec}. I will tell you when the timer runs out.")
                goto(timerState(it.intent.time, it.intent.minOrSec))
            }
        }
        furhat.listen()
    }

    onResponse<Thanks> {
        furhat.ask({
            random {
                +"No problem, my single lifelong purpose is to help you."
                +"There is no need to thank me, this is my job."
                +"Well, I am here to serve you for the rest of my life."
            }
        })

    }

    onResponse {
        furhat.gesture(Gestures.Wink(strength = 1.0, duration = 2.0))
        furhat.attend(users.current)
        furhat.listen()
    }

    onNoResponse {
        furhat.listen()
    }
}

fun timerState(time: Number?, minOrSec: MinOrSec?) = state(Parent) {
    val delay: Int = if (minOrSec != null && (minOrSec.toString() == "minutes" || minOrSec.toString() == "minute")) {
        time.toString().toInt() * 1000 * 60
    } else {
        time.toString().toInt() * 1000
    }
    onTime(delay = delay) {
        furhat.say("Timer is done")
    }
}