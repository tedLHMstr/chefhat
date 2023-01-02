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
                        "Or do you have anything at home to cook with?") }
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
    val recipeProvider = ProvideUniqueRecipe();
    var currentRecipe = recipes_[0]

    //onButton("ResponseButton", id="1", color = Color.Blue, size = Size.Large) {
    //    furhat.say("Are you going to cook right now? I guide you through the recipe if you want.")
    //}


    onEntry {
        val recipe = recipeProvider.provideRecipe(ingredients)
        currentRecipe = recipe;
        if (index == 0) {
            furhat.ask("Would you like ${recipe.getTitle()}?")
        } else {
            furhat.ask("Would you like ${recipe.getTitle()} instead?")
        }
    }

    onResponse<Yes> {
        users.current.userData.currentRecipe = currentRecipe
        furhat.say("Awesome!")
        goto(RecipeGuide)
    }
    onResponse<No> {
        index += 1
        reentry()
    }
}


