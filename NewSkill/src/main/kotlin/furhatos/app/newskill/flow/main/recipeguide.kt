package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.nlu.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.Number

val RecipeGuide = state(Parent) {
    var index = 0;

    onEntry {
        furhat.say("I will now guide you through this recipe. When you are done with a step, just tell me and we continue.")
        furhat.say("I have the ability to keep track of the time, so just tell me if you want me to set a timer.")
        reentry()
    }

    onReentry {
        if (index < users.current.userData.currentRecipe.getSteps().size) {
            println("index: "+users.current.userData.currentRecipe.getSteps()[index])
            furhat.say(users.current.userData.currentRecipe.getSteps()[index])
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
        println(it.intent)
        parallel {
            if (it.intent.time != null && it.intent.minOrSec != null) {
                furhat.say("Alright, setting a timer for ${it.intent.time} ${it.intent.minOrSec}. I will tell you when the timer runs out.")
                goto(timerState(it.intent.time, it.intent.minOrSec))
            }
        }
        furhat.listen()
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
    var delay: Int = if (minOrSec != null && (minOrSec.toString() == "minutes" || minOrSec.toString() == "minute")) {
        time.toString().toInt() * 1000 * 60
    } else {
        time.toString().toInt() * 1000
    }
    onTime(delay = delay) {
        furhat.say("Timer is done")
    }
}