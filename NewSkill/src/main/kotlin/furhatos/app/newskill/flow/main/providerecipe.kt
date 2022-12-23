package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.app.newskill.flow.Parent
import furhatos.flow.kotlin.Color
import kotlin.random.Random

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

class ProvideUniqueRecipe {
    private lateinit var lastRecipe: String;

    fun provideRecipe(): String {
        val randomIndex = Random.nextInt(recipes.size);
        var recipe = recipes[randomIndex];
        while(this::lastRecipe.isInitialized && recipe == lastRecipe) {
            val randomIndex = Random.nextInt(recipes.size);
            var recipe = recipes[randomIndex];
        }
        lastRecipe = recipe;
        return recipe;
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

val recipes = arrayListOf(
        "Chicken", "Fish", "Vegan", "Cucumber"
)

class Ingredient constructor(name: String, amount: Number, unit: String) {
    private var name = name;
    private var amount = amount;
    private var unit = unit;

    override fun toString(): String {
        return "$name, $amount $unit"
    }
}
class Recipe constructor(title: String, steps: Array<String>, time: Number, difficulty: String, ingredients: Array<Ingredient>) {
    private var title = title;
    private var steps = steps;
    private var time = time;
    private var difficulty = difficulty;
    private var ingredients = ingredients;

    fun getTitle(): String {
        return title;
    }
    fun getSteps(): Array<String> {
        return steps;
    }
}

val recipeSteps = mapOf(
        "Chicken" to arrayListOf<String>("Put the oven on 250 degrees", "Put the chicken in the BBQ sauce", "Chop onion and carrots, slice the potatoes", "Put the chopped onions and sliced potatoes in the oven for around 18-20 minutes.", "Fry the chicken in a hot pan until done. It usually takes about 4 minutes on each side.", "Serve the chicken, onions and potato. Top with chopped carrots.")
)
