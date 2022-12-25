package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.app.newskill.flow.Parent
import furhatos.flow.kotlin.Color
import furhatos.app.newskill.flow.recipes.*

val ProvideRecipe = state(Parent) {
    onEntry {
        random(
                { furhat.ask("Alright, ${users.current.userData.name}! How about I tell you what you should eat?") },
                { furhat.ask("Okay ${users.current.userData.name}! Do you want me to help you pick what to eat?") }
        )
    }

    onResponse<Yes> {
        goto(ProvideAlternative)
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame, eat shit and have a splendid day!")
        goto(Idle)
    }

}

val ProvideAlternative = state(Parent) {
    var index = 0;
    val recipeProvider = ProvideUniqueRecipe();

    onButton("ResponseButton", id="1", color = Color.Blue, size = Size.Large) {
        furhat.say("Are you going to cook right now? I guide you through the recipe if you want.")
    }

    onEntry {
        val recipe = recipeProvider.provideRecipe()
        if (index == 0) {
            furhat.ask("Would you like $recipe?")
        } else {
            furhat.ask("Would you like $recipe instead?")
        }
    }

    onResponse<Yes> {
        furhat.say("Awesome!")
    }
    onResponse<No> {
        index += 1
        reentry()
    }
    onResponse {
        furhat.say("I did not understand that.")
        reentry()
    }
}


