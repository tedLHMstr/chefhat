package furhatos.app.newskill.flow.recipes

import kotlin.random.Random
import furhatos.app.newskill.nlu.*

class ProvideUniqueRecipe {
    private lateinit var lastRecipe: String;

    fun provideRecipe(ingredients: IngredientList?): Recipe {

        var recipes: MutableList<Recipe> = mutableListOf()

        if (ingredients != null && ingredients.isNotEmpty) {
             for (recipe in recipes_) {
                 println(recipe.getTitle())
                 val ing = recipe.getIngredients()
                 ing_loop@ for (i in ing) {
                     ingredients.list.forEach {
                         if (it.toText().equals(i.name, ignoreCase = true)) {
                             println("Match")
                             recipes.add(recipe)
                         }
                     }
                 }
             }
        } else {
            recipes = recipes_
        }

        println(recipes)

        if (recipes.size === 1) {
            return recipes[0]
        }

        val randomIndex = Random.nextInt(recipes.size);
        var recipe = recipes[randomIndex];

        while(this::lastRecipe.isInitialized && recipe.getTitle() == lastRecipe) {
            val randomIndex = Random.nextInt(recipes.size);
            recipe = recipes[randomIndex];
        }
        lastRecipe = recipe.getTitle();
        return recipe;
    }

}

class Ingredient constructor(name: String, amount: Number, unit: String) {
    var name = name;
    private var amount = amount;
    private var unit = unit;

    override fun toString(): String {
        return "$name, $amount $unit"
    }
}

class Recipe(title: String, steps: ArrayList<String>, time: Number, difficulty: String, ingredients: Array<Ingredient>) {
    private var title = title;
    private var steps = steps;
    private var time = time;
    private var difficulty = difficulty;
    private var ingredients = ingredients;

    fun getTitle(): String {
        return title;
    }
    fun getSteps(): ArrayList<String> {
        return steps;
    }
    fun getIngredients(): Array<Ingredient> {
        return ingredients
    }
}

val recipes_ = arrayListOf<Recipe>(
        Recipe("Chicken", arrayListOf<String>("Put the oven on 250 degrees", "Put the chicken in the BBQ sauce", "Chop onion and carrots, slice the potatoes", "Put the chopped onions and sliced potatoes in the oven for around 18-20 minutes.", "Fry the chicken in a hot pan until done. It usually takes about 4 minutes on each side.", "Serve the chicken, onions and potato. Top with chopped carrots."), 30, "hard", arrayOf(Ingredient("Chicken", 500, "g"), Ingredient("Carrots", 300, "g"), Ingredient("Potatoes", 400, "g"))),
        Recipe("Fish", arrayListOf<String>("Put the oven on 175 degrees", "Put the fish in water with the salt", "Grate the horseradish", "Put butter in a frying pan until the butter is browned", "Pour the butter in a bowl and let chill", "Fry fish on low temperature for 5 minutes", "Serv fish with horseradish. browned butter and mashed potatoes"), 20, "medium", arrayOf(Ingredient("Fish", 600, "g"), Ingredient("Horseradish", 100, "g"), Ingredient("Potatoes", 400, "g")))
)

// Notes:
// - Add keywords to recipes to be able to search.