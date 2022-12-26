package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.nlu.*
import furhatos.gestures.Gestures

val RecipeGuide = state(Parent) {
    var index = 0;

    onEntry {
        furhat.say("I will now guide you through this recipe. When you are done with a step, just tell me and we continue.")
        reentry()
    }

    onReentry {
        if (index < users.current.userData.currentRecipe.getSteps().size) {
            println("index: "+users.current.userData.currentRecipe.getSteps()[index])
            furhat.say(users.current.userData.currentRecipe.getSteps()[index])
            furhat.listen()
            furhat.pauseSpeaking()
        } else {
            println("Else")
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

    onResponse {
        furhat.gesture(Gestures.Wink(strength = 1.0, duration = 2.0))
        furhat.attend(users.current)
        furhat.listen()
    }

    onNoResponse {
        furhat.listen()
    }
}