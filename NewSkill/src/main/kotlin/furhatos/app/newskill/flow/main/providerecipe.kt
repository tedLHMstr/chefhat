package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.flow.recipes.*
import furhatos.app.newskill.nlu.*

val ProvideRecipe = state(Parent) {
    var ingredients: IngredientList? = null
    onEntry {
        random(
                { furhat.ask("Okay ${users.current.userData.name}! Do you want me to help you pick what to eat?" +
                        " Or do you have anything at home to cook with?") }
        )
    }

    onResponse<HelpMe> {
        goto(provideAlternative(null, false))
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame, eat shit and have a splendid day!")
        goto(Idle)
    }
    onResponse<HaveIngredient> {
        ingredients = it.intent.ingredients
        if (ingredients != null) {
            furhat.say("Alright, I'll see if there are any recipes with $ingredients")
        }
        goto(provideAlternative(ingredients, false))
    }

    onPartialResponse<HaveIngredient> {
        ingredients = it.intent.ingredients
        if (ingredients != null) {
            furhat.say("Alright, I'll see if there are any recipes with $ingredients")
        }
        raise(it, it.secondaryIntent)
    }
    onResponse<CookingTime> {
        furhat.say("I will do my best to find a recipe that does not take too long to cook.")
        goto(provideAlternative(ingredients, true))
    }

}

fun provideAlternative(ingredients: IngredientList?, quick: Boolean?) : State = state(Parent) {
    val recipeProvider = ProvideUniqueRecipe(ingredients, quick);
    var currentRecipe = recipes_[0]
    var recipeIndex = 0

    onEntry {
        val res = recipeProvider.provideRecipe(recipeIndex)
        currentRecipe = res["recipe"] as Recipe;
        val foundMatch = res["foundMatch"] as Boolean
        if (!foundMatch && ingredients != null) {
            furhat.ask("I don't have a recipe that contains any of the ingredients you mentioned, would you like ${currentRecipe.getTitle()} instead?")
        } else {
            furhat.ask("Would you like ${currentRecipe.getTitle()}, it will take approximately ${currentRecipe.getTime()} minutes?")
        }
    }

    onReentry {
        recipeIndex ++
        val res = recipeProvider.provideRecipe(recipeIndex)
        println(res)
        currentRecipe = res["recipe"] as Recipe;
        if (res["endOfRecipes"] == true) {
            var end = furhat.askYN("That is all I have, are you sure you do not want any of the recipes i provided?")
            if (end == true) {
                goto(Idle)
            } else {
                recipeIndex = 0
                furhat.say("Alright, lets try again.")
                goto(ProvideRecipe)
            }
        }

        furhat.ask("Would you like ${currentRecipe.getTitle()} instead, it will take approximately ${currentRecipe.getTime()} minutes?")
    }

    onResponse<Yes> {
        users.current.userData.currentRecipe = currentRecipe
        furhat.say("Awesome!")
        goto(RecipeGuide)
    }
    onResponse<No> {
        reentry()
    }
    onNoResponse {
        furhat.ask("I did not understand that.")
    }
    onResponse {
        furhat.ask("I did not understand that, do you want ${currentRecipe.getTitle()}?")
    }
}


