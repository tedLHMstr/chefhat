package furhatos.app.newskill.flow.recipes

import kotlin.random.Random
import furhatos.app.newskill.nlu.*

class ProvideUniqueRecipe {
    private lateinit var lastRecipe: String;

    fun provideRecipe(ingredients: IngredientList?): Map<String, Any> {

        var res: MutableMap<String, Any> = mutableMapOf()
        res["foundMatch"] = true

        var recipes: MutableList<Recipe> = mutableListOf()

        if (ingredients != null && ingredients.isNotEmpty) {
             for (recipe in recipes_) {
                 val ing = recipe.getIngredients()
                 ing_loop@ for (i in ing) {
                     ingredients.list.forEach {
                         if (it.toText().equals(i.name, ignoreCase = true)) {
                             recipes.add(recipe)
                         }
                     }
                 }
             }
        } else {
            recipes = recipes_
        }

        if (recipes.size === 1) {
            res["recipe"] = recipes[0]

        } else if (recipes.size === 0) {
            recipes = recipes_
            res["foundMatch"] = false
        }

        val randomIndex = Random.nextInt(recipes.size);
        var recipe = recipes[randomIndex];
        res["recipe"] = recipe;

        while(this::lastRecipe.isInitialized && recipe.getTitle() == lastRecipe) {
            val randomIndex = Random.nextInt(recipes.size);
            res["recipe"] = recipes[randomIndex];
        }
        lastRecipe = recipe.getTitle();
        return res;
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
        Recipe("Fish", arrayListOf<String>("Put the oven on 175 degrees", "Put the fish in water with the salt", "Grate the horseradish", "Put butter in a frying pan until the butter is browned", "Pour the butter in a bowl and let chill", "Fry fish on low temperature for 5 minutes", "Serv fish with horseradish. browned butter and mashed potatoes"), 20, "medium", arrayOf(Ingredient("Fish", 600, "g"), Ingredient("Horseradish", 100, "g"), Ingredient("Potatoes", 400, "g"))),
        Recipe("Grilled Cheese Sandwich", arrayListOf<String>("Butter two slices of bread",
                "Place cheese between the slices of bread",
                "Heat a pan over medium heat",
                "Place sandwich in the pan and cook until the bread is golden brown and the cheese is melted",
                "Serve hot"
                ),
            10,
            "easy",
            arrayOf(
                Ingredient("Bread", 2, "slices"),
                Ingredient("Cheese", 2, "slices")
            )
        ),
        Recipe(
            "Spaghetti Bolognese",
            arrayListOf<String>(
                "Bring a pot of salted water to a boil",
                "Add spaghetti to the pot and cook according to package instructions",
                "Meanwhile, heat olive oil in a separate pan over medium heat",
                "Add ground beef and cook until browned",
                "Add diced onions, carrots, and garlic to the pan and cook for an additional 5 minutes",
                "Add canned tomatoes and tomato sauce to the pan",
                "Reduce heat to low and simmer for 20 minutes",
                "Drain spaghetti and add it to the pan with the sauce",
                "Serve hot"
            ),
            60,
            "medium",
            arrayOf(
                Ingredient("Spaghetti", 500, "g"),
                Ingredient("Ground beef", 500, "g"),
                Ingredient("Onion", 1, ""),
                Ingredient("Carrot", 2, ""),
                Ingredient("Garlic", 3, "cloves"),
                Ingredient("Canned tomatoes", 1, "can"),
                Ingredient("Tomato sauce", 1, "can")
            )
        ),
        Recipe(
            "Blueberry Pancakes",
            arrayListOf<String>(
                "Mix flour, baking powder, salt, and sugar in a large bowl",
                "Whisk milk, egg, and melted butter in a separate bowl",
                "Pour the wet ingredients into the dry ingredients and mix until just combined",
                "Fold in the blueberries",
                "Heat a pan over medium heat and coat with cooking spray",
                "Pour ¼ cup of batter into the pan for each pancake",
                "Cook until bubbles form on the surface, then flip and cook until golden brown",
                "Serve hot with syrup"
            ),
            20,
            "easy",
            arrayOf(
                Ingredient("Flour", 1, "cup"),
                Ingredient("Baking powder", 1, "tsp"),
                Ingredient("Salt", 1, "tsp"),
                Ingredient("Sugar", 2, "tbsp"),
                Ingredient("Milk", 1, "cup"),
                Ingredient("Egg", 1, ""),
                Ingredient("Butter", 2, "tbsp"),
                Ingredient("Blueberries", 1, "cup")
            )
        ),
        Recipe(
    "Classic Beef Chili",
    arrayListOf<String>(
        "In a large pot, heat the olive oil over medium heat",
        "Add the diced onions and minced garlic to the pot and cook until the onions are translucent",
        "Increase the heat to high and add the ground beef to the pot",
        "Cook the beef, breaking it up into small pieces, until it is browned",
        "Stir in the chili powder, cumin, paprika, and salt",
        "Add the canned diced tomatoes, tomato sauce, and beef broth to the pot and bring to a boil",
        "Reduce the heat to low and simmer for 30 minutes",
        "Stir in the kidney beans and corn and continue to simmer for an additional 10 minutes",
        "Serve hot with toppings such as cheese, sour cream, and green onions"
    ),
    60,
    "medium",
    arrayOf(
        Ingredient("Olive oil", 2, "tbsp"),
        Ingredient("Onion", 1, ""),
        Ingredient("Garlic", 2, "cloves"),
        Ingredient("Ground beef", 500, "g"),
        Ingredient("Chili powder", 2, "tbsp"),
        Ingredient("Cumin", 2, "tsp"),
        Ingredient("Paprika", 2, "tsp"),
        Ingredient("Salt", 1, "tsp"),
        Ingredient("Canned diced tomatoes", 1, "can"),
        Ingredient("Tomato sauce", 1, "can"),
        Ingredient("Beef broth", 500, "ml"),
        Ingredient("Kidney beans", 1, "can"),
        Ingredient("Corn", 1, "can")
    )
),

Recipe(
    "Lemon Garlic Shrimp Scampi",
    arrayListOf<String>(
        "Heat a large pan over medium-high heat",
        "Melt the butter in the pan",
        "Add the minced garlic and cook for 1-2 minutes until fragrant",
        "Add the shrimp to the pan and season with salt and pepper",
        "Cook the shrimp for 2-3 minutes on each side until pink",
        "Stir in the lemon juice and white wine",
        "Reduce the heat to low and simmer for an additional 2-3 minutes",
        "Serve hot over pasta or rice"
    ),
    20,
    "easy",
    arrayOf(
        Ingredient("Butter", 4, "tbsp"),
        Ingredient("Garlic", 4, "cloves"),
        Ingredient("Shrimp", 500, "g"),
        Ingredient("Salt", 1, "tsp"),
        Ingredient("Pepper", 1, "tsp"),
        Ingredient("Lemon juice", 4, "tbsp"),
        Ingredient("White wine", 1, "cup")
    )
),

Recipe(
    "Thai Red Curry Chicken",
    arrayListOf<String>(
        "Heat a large pan or wok over medium-high heat",
        "Add the coconut oil to the pan and let it melt",
        "Add the diced chicken and cook until browned on all sides",
        "Stir in the red curry paste and cook for an additional minute",
        "Add the coconut milk, chicken broth, and sugar to the pan",
        "Bring the mixture to a boil, then reduce the heat to low and simmer for 15 minutes",
        "Stir in the bell peppers and baby corn and simmer for an additional 5 minutes",
        "Serve hot over rice or noodles"
    ),
    30,
    "medium",
    arrayOf(
        Ingredient("Coconut oil", 2, "tbsp"),
        Ingredient("Chicken", 500, "g"),
        Ingredient("Red curry paste", 2, "tbsp"),
        Ingredient("Coconut milk", 1, "can"),
        Ingredient("Chicken broth", 1, "cup"),
        Ingredient("Sugar", 1, "tbsp"),
        Ingredient("Bell peppers", 2, ""),
        Ingredient("Baby corn", 1, "can")
    )
),

Recipe(
    "Vegan Chili",
    arrayListOf<String>(
        "Heat the olive oil in a large pot over medium heat",
        "Add the diced onions and minced garlic to the pot and cook until the onions are translucent",
        "Stir in the diced bell peppers and cook for an additional 5 minutes",
        "Add the diced tomatoes, tomato sauce, kidney beans, black beans, and corn to the pot",
        "Stir in the chili powder, cumin, paprika, and salt",
        "Reduce the heat to low and simmer for 30 minutes",
        "Stir in the chopped fresh cilantro and serve hot with toppings such as avocado, cheese, and sour cream"
    ),
    60,
    "medium",
    arrayOf(
        Ingredient("Olive oil", 2, "tbsp"),
        Ingredient("Onion", 1, ""),
        Ingredient("Garlic", 2, "cloves"),
        Ingredient("Bell pepper", 2, ""),
        Ingredient("Diced tomatoes", 1, "can"),
        Ingredient("Tomato sauce", 1, "can"),
        Ingredient("Kidney beans", 1, "can"),
        Ingredient("Black beans", 1, "can"),
        Ingredient("Corn", 1, "can"),
        Ingredient("Chili powder", 2, "tbsp"),
        Ingredient("Cumin", 2, "tsp"),
        Ingredient("Paprika", 2, "tsp"),
        Ingredient("Salt", 1, "tsp"),
        Ingredient("Cilantro", 1, "bunch")
    )
)
)       

// Notes:
// - Add keywords to recipes to be able to search.