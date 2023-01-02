package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.app.newskill.flow.Parent
import furhatos.flow.kotlin.Color
import furhatos.app.newskill.flow.recipes.*
import furhatos.app.newskill.nlu.*

val ProvideRecipe = state(Parent) {

    onEntry {
        random(
                { furhat.ask("Okay ${users.current.userData.name}! Do you want me to help you pick what to eat?" +
                        " Or do you have anything at home to cook with?") }
        )
    }

    onResponse<HelpMe> {
        goto(provideAlternative(null))
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame, eat shit and have a splendid day!")
        goto(Idle)
    }

    onResponse<HaveIngredient> {
        val ingredients = it.intent.ingredients
        print(it)
        if (ingredients != null) {
            furhat.say("Alright, I'll see if there are any recipes with $ingredients")
        }
        goto(provideAlternative(ingredients))
    }

}

fun provideAlternative(ingredients: IngredientList?) : State = state(Parent) {
    var index = 0;
    val recipeProvider = ProvideUniqueRecipe(ingredients);
    var currentRecipe = recipes_[0]
    var recipeIndex = 0

    onEntry {
        val res = recipeProvider.provideRecipe(recipeIndex)
        currentRecipe = res["recipe"] as Recipe;
        val foundMatch = res["foundMatch"] as Boolean
        if (!foundMatch) {
            furhat.ask("I don't have a recipe that contains any of the ingredients you mentioned, would you like ${currentRecipe.getTitle()} instead?")
        } else {
            furhat.ask("Would you like ${currentRecipe.getTitle()}?")
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

        furhat.ask("Would you like ${currentRecipe.getTitle()} instead?")
    }

    onResponse<Yes> {
        users.current.userData.currentRecipe = currentRecipe
        furhat.say("Awesome!")
        goto(RecipeGuide)
    }
    onResponse<No> {
        index += 1
        println("no, onRepsponse ")
        reentry()
    }
    onNoResponse {
        furhat.ask("I did not understand that.")
    }
    onResponse {
        furhat.ask("I did not understand that, do you want ${currentRecipe.getTitle()}?")
    }
}


