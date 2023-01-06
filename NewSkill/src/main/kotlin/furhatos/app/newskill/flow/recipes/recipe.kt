package furhatos.app.newskill.flow.recipes

import kotlin.random.Random
import furhatos.app.newskill.nlu.*

class ProvideUniqueRecipe(ingredients: MutableList<furhatos.app.newskill.nlu.Ingredient>?, quick: Boolean?) {
    private lateinit var lastRecipe: String;
    private var recipes: MutableList<Recipe>
    private var foundMatch: Boolean

    init {
        recipes = mutableListOf()
        foundMatch = true
        if (!ingredients.isNullOrEmpty()) {
            for (recipe in recipes_) {
                val ing = recipe.getIngredients()
                ing_loop@ for (i in ing) {
                    ingredients.forEach {
                        if (it.toText().equals(i.name, ignoreCase = true)) {
                            recipes.add(recipe)
                        }
                    }
                }
            }
            if (recipes.size == 0) {
                recipes = recipes_
                foundMatch = false
            }
        } else {
            recipes = recipes_
            foundMatch = false
        }
        if (quick == true) {
            recipes.sortWith(Comparator { first: Recipe, second: Recipe ->
                if (first.getTime() != second.getTime()) {
                    first.getTime() as Int - second.getTime() as Int
                }
                else {
                    0
                }
            })
        }
    }

    fun provideRecipe(index: Int): Map<String, Any> {
        val res = mutableMapOf<String, Any>()
        if (index < recipes.size) {
            res["foundMatch"] = foundMatch
            res["recipe"] = recipes[index]
            res["endOfRecipes"] = false
        } else {
            res["foundMatch"] = foundMatch
            res["recipe"] = recipes[0]
            res["endOfRecipes"] = true
        }
        return res
    }

}

class Ingredient constructor(name: String, amount: Number, unit: String) {
    val name = name;
    private var amount = amount;
    private var unit = unit;

    override fun toString(): String {
        return "$name, $amount $unit"
    }

    fun getIngredientName(): String {
        return name
    }

    fun tellIngredientPlusAmount(): String {
        return if (unit == "") {
            "$amount $name"
        } else {
            "$amount $unit of $name"
        }
    }
}

class Instruction constructor(step: String, ingredients: List<Ingredient>) {
    private val step = step;
    private val ingredients = ingredients;

    fun getStep(): String {
        return step
    }

    fun getIngredients(): List<Ingredient> {
        return ingredients
    }
}


class Recipe(title: String, steps: ArrayList<Instruction>, time: Number, difficulty: String, ingredients: Array<Ingredient>) {
    private var title = title;
    private var steps = steps;
    private var time = time;
    private var difficulty = difficulty;
    private var ingredients = ingredients;

    fun getTitle(): String {
        return title;
    }
    fun getSteps(): ArrayList<Instruction> {
        return steps;
    }
    fun getIngredients(): Array<Ingredient> {
        return ingredients
    }
    fun getTime(): Number {
        return time
    }
}

val recipes_ = arrayListOf<Recipe>(
        Recipe("Grilled Cheese Sandwich",
                arrayListOf<Instruction>(
                        Instruction("Butter two slices of bread", listOf<Ingredient>(
                                Ingredient("Bread", 2, "slices")
                        )),
                        Instruction("Place cheese between the slices of bread", listOf<Ingredient>(
                                Ingredient("Cheese", 2, "slices")
                        )),
                        Instruction("Heat a pan over medium heat", emptyList()),
                        Instruction("Place sandwich in the pan and cook until the bread is golden brown and the cheese is melted", emptyList()),
                        Instruction( "Serve hot", emptyList())
                ),
            10,
            "easy",
            arrayOf(
                Ingredient("Bread", 2, "slices"),
                Ingredient("Cheese", 2, "slices")
            )
        ),
        Recipe("Spaghetti Bolognese",
                arrayListOf<Instruction>(
                        Instruction("Bring a pot of salted water to a boil", emptyList()),
                        Instruction("Add spaghetti to the pot and cook according to package instructions", listOf<Ingredient>(
                                Ingredient("Spaghetti", 500, "g")
                        )),
                        Instruction("Meanwhile, heat olive oil in a separate pan over medium heat", emptyList()),
                        Instruction("Add ground beef and cook until browned", listOf<Ingredient>(
                                Ingredient("Ground beef", 500, "g")
                        )),
                        Instruction("Add diced onions, carrots, and garlic to the pan and cook for an additional 5 minutes", listOf<Ingredient>(
                                Ingredient("Onion", 1, ""),
                                Ingredient("Carrot", 2, ""),
                                Ingredient("Garlic", 3, "cloves")
                        )),
                        Instruction("Add canned tomatoes and tomato sauce to the pan", listOf<Ingredient>(
                                Ingredient("Canned tomatoes", 1, "can"),
                                Ingredient("Tomato sauce", 1, "can")
                        )),
                        Instruction("Reduce heat to low and simmer for 20 minutes", emptyList()),
                        Instruction("Drain spaghetti and add it to the pan with the sauce", emptyList()),
                        Instruction( "Serve hot", emptyList())
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
        Recipe("Blueberry Pancakes",
                arrayListOf<Instruction>(
                        Instruction("Mix flour, baking powder, salt, and sugar in a large bowl", listOf<Ingredient>(
                                Ingredient("Flour", 1, "cup"),
                                Ingredient("Baking powder", 1, "tsp"),
                                Ingredient("Salt", 1, "tsp"),
                                Ingredient("Sugar", 2, "tbsp")
                        )),
                        Instruction("Whisk milk, egg, and melted butter in a separate bowl", listOf<Ingredient>(
                                Ingredient("Milk", 1, "cup"),
                                Ingredient("Egg", 1, ""),
                                Ingredient("Butter", 2, "tbsp")
                        )),
                        Instruction("Pour the wet ingredients into the dry ingredients and mix until just combined", emptyList()),
                        Instruction("Fold in the blueberries", listOf<Ingredient>(
                                Ingredient("Blueberries", 1, "cup")
                        )),
                        Instruction("Heat a pan over medium heat and coat with cooking spray", emptyList()),
                        Instruction("Pour ¼ cup of batter into the pan for each pancake", emptyList()),
                        Instruction("Cook until bubbles form on the surface, then flip and cook until golden brown", emptyList()),
                        Instruction( "Serve hot with syrup", emptyList())
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
        Recipe("Classic Beef Chili",
                arrayListOf<Instruction>(
                        Instruction("In a large pot, heat the olive oil over medium heat", listOf<Ingredient>(
                                Ingredient("Olive oil", 2, "tbsp")
                        )),
                        Instruction("Add the diced onions and minced garlic to the pot and cook until the onions are translucent", listOf<Ingredient>(
                                Ingredient("Onion", 1, ""),
                                Ingredient("Garlic", 2, "cloves")
                        )),
                        Instruction("Increase the heat to high and add the ground beef to the pot", listOf<Ingredient>(
                                Ingredient("Ground beef", 500, "g")
                        )),
                        Instruction("Cook the beef, breaking it up into small pieces, until it is browned", emptyList()),
                        Instruction("Stir in the chili powder, cumin, paprika, and salt", listOf<Ingredient>(
                                Ingredient("Chili powder", 2, "tbsp"),
                                Ingredient("Cumin", 2, "tsp"),
                                Ingredient("Paprika", 2, "tsp"),
                                Ingredient("Salt", 1, "tsp")
                        )),
                        Instruction("Add the canned diced tomatoes, tomato sauce, and beef broth to the pot and bring to a boil", listOf<Ingredient>(
                                Ingredient("Canned diced tomatoes", 1, "can"),
                                Ingredient("Tomato sauce", 1, "can"),
                                Ingredient("Beef broth", 500, "ml")
                        )),
                        Instruction("Reduce the heat to low and simmer for 30 minutes", emptyList()),
                        Instruction("Stir in the kidney beans and corn and continue to simmer for an additional 10 minutes", listOf<Ingredient>(
                                Ingredient("Kidney beans", 1, "can"),
                                Ingredient("Corn", 1, "can")
                        )),
                        Instruction( "Serve hot with toppings such as cheese, sour cream, and green onions", emptyList())
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

        Recipe("Lemon Garlic Shrimp Scampi",
                arrayListOf<Instruction>(
                        Instruction("Heat a large pan over medium-high heat", emptyList()),
                        Instruction("Melt the butter in the pan", listOf<Ingredient>(
                                Ingredient("Butter", 4, "tbsp")
                        )),
                        Instruction("Add the minced garlic and cook for 1-2 minutes until fragrant", listOf<Ingredient>(
                                Ingredient("Garlic", 4, "cloves")
                        )),
                        Instruction("Add the shrimp to the pan and season with salt and pepper", listOf<Ingredient>(
                                Ingredient("Shrimp", 500, "g"),
                                Ingredient("Salt", 1, "tsp"),
                                Ingredient("Pepper", 1, "tsp")
                        )),
                        Instruction("Cook the shrimp for 2-3 minutes on each side until pink", emptyList()),
                        Instruction("Stir in the lemon juice and white wine", listOf<Ingredient>(
                                Ingredient("Lemon juice", 4, "tbsp"),
                                Ingredient("White wine", 1, "cup")
                        )),
                        Instruction("Reduce the heat to low and simmer for an additional 2-3 minutes", emptyList()),
                        Instruction( "Serve hot over pasta or rice", emptyList())
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
        arrayListOf<Instruction>(
                Instruction("Heat a large pan or wok over medium-high heat", emptyList()),
                Instruction("Add the coconut oil to the pan and let it melt", listOf<Ingredient>(
                        Ingredient("Coconut oil", 2, "tbsp")
                )),
                Instruction("Add the diced chicken and cook until browned on all sides", listOf<Ingredient>(
                        Ingredient("Chicken", 500, "g")
                )),
                Instruction("Stir in the red curry paste and cook for an additional minute", listOf<Ingredient>(
                        Ingredient("Red curry paste", 2, "tbsp")
                )),
                Instruction("Add the coconut milk, chicken broth, and sugar to the pan", listOf<Ingredient>(
                        Ingredient("Coconut milk", 1, "can"),
                        Ingredient("Chicken broth", 1, "cup"),
                        Ingredient("Sugar", 1, "tbsp")
                )),
                Instruction("Bring the mixture to a boil, then reduce the heat to low and simmer for 15 minutes", emptyList()),
                Instruction("Stir in the bell peppers and baby corn and simmer for an additional 5 minutes", listOf<Ingredient>(
                        Ingredient("Bell peppers", 2, ""),
                        Ingredient("Baby corn", 1, "can")
                )),
                Instruction( "Serve hot over rice or noodles", emptyList())
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

        Recipe("Vegan Chili",
                arrayListOf<Instruction>(
                        Instruction("Heat the olive oil in a large pot over medium heat", listOf<Ingredient>(
                                Ingredient("Olive oil", 2, "tbsp")
                        )),
                        Instruction("Add the diced onions and minced garlic to the pot and cook until the onions are translucent", listOf<Ingredient>(
                                Ingredient("Onion", 1, ""),
                                Ingredient("Garlic", 2, "cloves")
                        )),
                        Instruction("Stir in the diced bell peppers and cook for an additional 5 minutes", listOf<Ingredient>(
                                Ingredient("Bell pepper", 2, "")
                        )),
                        Instruction("Add the diced tomatoes, tomato sauce, kidney beans, black beans, and corn to the pot", listOf<Ingredient>(
                                Ingredient("Diced tomatoes", 1, "can"),
                                Ingredient("Tomato sauce", 1, "can"),
                                Ingredient("Kidney beans", 1, "can"),
                                Ingredient("Black beans", 1, "can"),
                                Ingredient("Corn", 1, "can")
                        )),
                        Instruction("Stir in the chili powder, cumin, paprika, and salt", listOf<Ingredient>(
                                Ingredient("Chili powder", 2, "tbsp"),
                                Ingredient("Cumin", 2, "tsp"),
                                Ingredient("Paprika", 2, "tsp"),
                                Ingredient("Salt", 1, "tsp")
                        )),
                        Instruction("Reduce the heat to low and simmer for 30 minutes", emptyList()),
                        Instruction("Stir in the chopped fresh cilantro and serve hot with toppings such as avocado, cheese, and sour cream", listOf<Ingredient>(
                                Ingredient("Cilantro", 1, "bunch")
                        ))
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