package furhatos.app.newskill.flow.main

import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val RecipeGuideWOZ = state(Parent) {
    var stepIndex = 0

  //  onButton("Go back", id="31", color = Color.Yellow) {
  //      goto(ProvideRecipeWOZ)
  //  }
    onButton("Begin?", id="32", color = Color.Yellow) {
        furhat.say("Are you ready to begin cooking?")
    }
    onButton("Tell step", id="33", color = Color.Yellow) {
        furhat.say(users.current.userData.currentRecipe.getSteps()[stepIndex].getStep())
    }
    onButton("Tell next step", id="34", color = Color.Yellow) {
        stepIndex++
        if (stepIndex < users.current.userData.currentRecipe.getSteps().size) {
            furhat.say(users.current.userData.currentRecipe.getSteps()[stepIndex].getStep())
        } else {
            furhat.say("We are done!!")
        }
    }
}