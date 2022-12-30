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
                { furhat.ask("Alright, ${users.current.userData.name}! How about I tell you what you should eat? " +
                        "Or do you have anything at home to cook with?") },
                { furhat.ask("Okay ${users.current.userData.name}! Do you want me to help you pick what to eat?" +
                        "Or do you have anything at home to cook with?") }
        )
    }

    onResponse<Yes> {
        goto(ProvideAlternative)
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame, eat shit and have a splendid day!")
        goto(Idle)
    }

    onResponse<HaveIngredient> {
        furhat.say("Okey, let's see what we can do with that") // kanske lägga till så att den säger ingrediensen istället
        goto(ProvideIngredientAlternative)
     }

}
val ProvideIngredientAlternative = state(Parent) {
    val recipeProvider = ProvideUniqueRecipe();
    val count = 0
    val finalRecipe = null
    onEntry {
        while(count < 2){
            var currentRecipe = recipes_[count]
            count++
            var ingredients = currentRecipe.getIngredients()

            for (ing in ingredients){
                if(ing.name == SAID INGREDIENT NAME HERE){
                    finalRecipe = currentRecipe
                    break
                }
            }
        }
        if (finalRecipe != null){
            furhat.ask("Maybe we can cook ${finalRecipe.getTitle()} with your INGREDIENT?")
        } else {
            furhat.ask("It seems like I don't know anything to cook with that ingredient")
        }
    }

    // lägga till fortsättning här!

}


val ProvideAlternative = state(Parent) {
    var index = 0;
    val recipeProvider = ProvideUniqueRecipe();
    var currentRecipe = recipes_[0]

    //onButton("ResponseButton", id="1", color = Color.Blue, size = Size.Large) {
    //    furhat.say("Are you going to cook right now? I guide you through the recipe if you want.")
    //}


    onEntry {
        val recipe = recipeProvider.provideRecipe()
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


