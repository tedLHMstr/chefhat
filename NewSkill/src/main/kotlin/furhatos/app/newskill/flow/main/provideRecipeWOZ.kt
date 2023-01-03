package furhatos.app.newskill.flow.main

import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.flow.recipes.ProvideUniqueRecipe
import furhatos.app.newskill.flow.recipes.Recipe
import furhatos.app.newskill.flow.recipes.recipes_
import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val ProvideRecipeWOZ = state(Parent) {
    val recipeProvider = ProvideUniqueRecipe(null, false);
    var index = 0
    var recipe: Recipe = recipes_[0]

    onButton("Provide recipe?", id="21", color = Color.Blue) {
        furhat.say("Do you want me to provide you with a recipe?")
    }
    onButton("Give recipe", id="22", color = Color.Blue) {
        recipe = recipeProvider.provideRecipe(index)["recipe"] as Recipe
        furhat.say("Alright, do you want ${recipe.getTitle()}")
    }
    onButton("I have chicken", id="23", color = Color.Green) {
        furhat.say("Great, do you have any vegetables or other ingredients that you would like to include in the dish?")
    }
    onButton("I don't have any of those", id="24", color = Color.Red) {
        furhat.say("That's okay, we can work with what you have. Do you have any specific dietary restrictions or preferences that I should know about?")
    }
    onButton("Anything else?", id="25", color = Color.Blue) {
        furhat.say("Okay, do you want anything else?")
    }
    onButton("Increment recipe", id="26", color = Color.Blue) {
        index++
    }
    onButton("End of recipes", id="27", color = Color.Blue) {
        furhat.say("That's all I have in my recipe bank..")
    }
    onButton("Goto guide", id="28", color = Color.Blue) {
        users.current.userData.currentRecipe = recipe
        furhat.say("Awesome!")
        goto(RecipeGuideWOZ)
    }
}