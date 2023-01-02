package furhatos.app.newskill.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.ListEntity
import furhatos.nlu.common.Number
import furhatos.util.Language
import furhatos.app.newskill.flow.recipes.*

// Our Fruit entity.
class Fruit : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("banana", "orange", "apple", "cherimoya", "pear")
    }
}

class Ingredient : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("chicken", "fish", "beans", "cucumber",  "Bread", "Cheese", "Spaghetti", "Ground beef", "Onion", "Carrot", "Garlic", "Canned tomatoes", "Tomato sauce",
            "Flour", "Baking powder", "Salt", "Sugar", "Milk", "Egg", "Butter", "Blueberries", "Olive oil", "Chili powder", "Cumin", "Paprika", "Beef broth", "Kidney beans",
            "Corn", "Shrimp", "Pepper", "Lemon juice", "White wine", "Coconut oil", "Red curry paste", "Coconut milk", "Chicken broth", "Bell peppers", "tomatoes", "Black beans",
            "Cilantro", "eggs", "egg", "potatoes", "carrots", "fish", "beef", "pork", "Horseradish")
    }
}

class MinOrSec : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
                "minutes", "minute", "seconds", "second"
        )
    }
}



class IngredientList : ListEntity<Ingredient>()
class FruitList : ListEntity<QuantifiedFruit>()


class QuantifiedFruit(
        val count : Number? = Number(1),
        val fruit : Fruit? = null) : ComplexEnumEntity() {

    override fun getEnum(lang: Language): List<String> {
        return listOf("@count @fruit", "@fruit")
    }

    override fun toText(): String {
        return generate("$count $fruit")
    }
}

// Our BuyFruit intent
class BuyFruit(val fruits : FruitList? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@fruits",
                "I want a banana",
                "I would like an apple",
                "I want to buy a @fruits",
                "I would like a @fruits",
                "Can I have a @fruits",
                "I want some @fruits"
        )
    }
}

class SetTimer(val time : Number? = null, val minOrSec : MinOrSec? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Can you set a timer for @time @minOrSec",
                "Set a timer of @time @minOrSec",
                "Can you start a timer of @time @minOrSec",
                "Set a timer for @time @minOrSec"
        )
    }
}

class RepeatStep: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Can you take that step one more time.",
                "Can you repeat that?",
                "What?",
                "Sorry, what did you say?",
                "What was I supposed to do now?"
        )
    }
}

class NextStep: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I am done, take me to the next step.",
                "Whats next?",
                "Done",
                "I am finished."
        )
    }
}

class HaveIngredient(val ingredients : IngredientList? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I have some @ingredients",
                "@ingredients",
                "I got some chicken in my fridge",
                "Cook something with @ingredients"
        )
    }
}

class AttendFurhat: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Okay Furhat",
                "Hello Furhat"
        )
    }
}

class AskSpecific: Intent() {
    private val recipes: List<Recipe> = mutableListOf<Recipe>()
    // Add recipes to list (from list of recipes) and return the titles of each in getExamples below.
    override fun getExamples(lang: Language): List<String> {

        return listOf(
                "Today I would like some @food",
                "Do you have @food"
        )
    }
}

class HelpMe: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("What options do you have?",
                "You can suggest something",
                "Yes, please help me!",
                "Help")
    }
}

class RequestOptions: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("What options do you have?",
                "What fruits do you have?",
                "What are the alternatives?",
                "What do you have?")
    }
}

class Info: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Who are you?",
                "What is this?",
                "What is your purpose?",
                "Who is your master?",
                "Explain what you do."
                )
    }
}
