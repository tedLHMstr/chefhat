package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.flow.recipes.*
import furhatos.app.newskill.nlu.*
import furhatos.app.newskill.nlu.Ingredient

val ProvideRecipe = state(Parent) {
    var ingredients: IngredientList? = null
    onEntry {
        random(
                { furhat.ask("Okay ${users.current.userData.name}! Do you want me to pick out a random recipe for you?" +
                        " Or do you have anything specific you want to cook with, maybe something you already have at home?")
                }
        )
    }

    onResponse<HelpMe> {
        goto(provideAlternative(null, false))
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame, approach me again if you change your mind!")
        goto(Idle)
    }

    onResponse<HaveIngredient> {
        ingredients = it.intent.ingredients
        if (ingredients != null) {
            furhat.say("Alright, I'll see if I have any recipes containing $ingredients")
        }
        goto(provideAlternative(ingredients, false))
    }

    onPartialResponse<HaveIngredient> {
        ingredients = it.intent.ingredients
        if (ingredients != null) {
            furhat.say("Alright, I'll see if I have any recipes containing $ingredients")
        }
        raise(it, it.secondaryIntent)
    }

    onResponse<CookingTime> {
        furhat.say("I will do my best to find a recipe that does not take too long to cook.")
        goto(provideAlternative(ingredients, true))
    }

    onResponse {
        furhat.say("Please let me know if you want me to pick out a random recipe for you or if you have something you want to cook." +
                " If you have something, just say those ingredients and I will find something for you")
        furhat.listen()
    }
    onNoResponse {
        furhat.say("Please let me know if you want me to pick out a random recipe for you or if you have something you want to cook." +
                "If you have something, just say those ingredients and I will find something for you")
        furhat.listen()
    }
}

fun provideAlternative(ingredients: IngredientList?, quick: Boolean?) : State = state(Parent) {
    var currentRecipe = recipes_[0]
    var recipeIndex = 0
    var ingredientsList = ingredients?.list
    var recipeProvider = ProvideUniqueRecipe(ingredientsList, quick)

    fun changeIngredients(newIng: MutableList<Ingredient>?, removeIng: MutableList<Ingredient>?) {
        if (newIng != null && newIng.size > 0) {
            ingredientsList?.addAll(newIng)
        }
        if (removeIng != null && removeIng.size > 0) {
            removeIng.forEach {
                ingredientsList?.remove(it)
            }
        }
        if(newIng != null && newIng.size > 0 || removeIng != null && removeIng.size > 0) {
            recipeProvider = ProvideUniqueRecipe(ingredientsList, quick)
            recipeIndex = 0
        }
    }

    onEntry {
        val res = recipeProvider.provideRecipe(recipeIndex)
        currentRecipe = res["recipe"] as Recipe
        val foundMatch = res["foundMatch"] as Boolean
        if (!foundMatch && ingredients != null) {
            furhat.ask("I don't have a recipe that contains any of the ingredients you mentioned, would you like ${currentRecipe.getTitle()} instead?")
        } else {
            random(
                    {
                        furhat.ask("Would you like ${currentRecipe.getTitle()}, it will take approximately ${currentRecipe.getTime()} minutes?")
                        furhat.ask("What about some ${currentRecipe.getTitle()}, it will take approximately ${currentRecipe.getTime()} minutes?")
                    }
            )
        }
    }

    onReentry {
        recipeIndex ++
        val res = recipeProvider.provideRecipe(recipeIndex)
        println(res)
        currentRecipe = res["recipe"] as Recipe
        if (res["endOfRecipes"] == true) {
            val end = furhat.askYN("That is all I have, are you sure you do not want any of the recipes i provided?")
            if (end == true) {
                goto(Idle)
            } else {
                recipeIndex = 0
                furhat.say("Alright, lets try again.")
                goto(ProvideRecipe)
            }
        }
        random (
                {
                    furhat.ask("Would you like ${currentRecipe.getTitle()} instead, it will take approximately ${currentRecipe.getTime()} minutes?")
                    furhat.ask("What about some ${currentRecipe.getTitle()} instead, it will take approximately ${currentRecipe.getTime()} minutes?")
                }
        )

    }

    onEvent("NewIngredients") {
        if (ingredientsList != null) {
            furhat.say("Alright, I'll see if there are any recipes containing ${ingredientsList.joinToString(", ")}")
        }
        val res = recipeProvider.provideRecipe(recipeIndex)
        currentRecipe = res["recipe"] as Recipe
        val foundMatch = res["foundMatch"] as Boolean
        if (!foundMatch && ingredientsList != null) {
            furhat.ask("I don't have a recipe that contains any of the ingredients you mentioned, would you like ${currentRecipe.getTitle()} instead?")
        } else {
            furhat.ask("Would you like ${currentRecipe.getTitle()}, it will take approximately ${currentRecipe.getTime()} minutes?")
        }
    }

    onResponse<HaveIngredient> {
        val added = it.intent.ingredients?.list
        changeIngredients(added, null)
        if (!added.isNullOrEmpty()) {
            raise("NewIngredients")
        }
    }

    onResponse<RemoveIngredient> {
        val removed = it.intent.ingredients?.list
        changeIngredients(null, removed)
        if (!removed.isNullOrEmpty()) {
            raise("NewIngredients")
        }
    }

    onResponse<AskIngredients> {
        furhat.say("You will need the following: " +
                currentRecipe.getIngredients().joinToString(", ", "", "", 100, "") { it -> it.tellIngredientPlusAmount() }
        )
        furhat.listen()
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
        furhat.ask("Please let me know if you want ${currentRecipe.getTitle()} or not. You can also add or remove ingredients from the list!")
        furhat.listen()
    }
    onResponse {
        furhat.ask("Please let me know if you want ${currentRecipe.getTitle()} or not. You can also add or remove ingredients from the list!")
        furhat.listen()
    }
}


