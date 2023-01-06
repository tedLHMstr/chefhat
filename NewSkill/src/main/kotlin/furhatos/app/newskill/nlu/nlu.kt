package furhatos.app.newskill.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.ListEntity
import furhatos.nlu.common.Number
import furhatos.util.Language


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

class CookingTime : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("fast", "quick", "instant", "short", "I do not have a lot of time", "hurry", "rush", "easy")
    }
}

class IngredientList : ListEntity<Ingredient>()


class SetTimer : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Can you set a timer",
                "Set a timer",
                "Can you start a timer"
        )
    }
}

class TimerTime(val time : Number? = null, val minOrSec : MinOrSec? = null) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
                "Can you set a timer for @time @minOrSec",
                "Set a timer of @time @minOrSec",
                "Can you start a timer of @time @minOrSec",
                "Set a timer for @time @minOrSec",
                "@time @minOrSec"
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
                "Cook something with @ingredients",
                "Add @ingredients",
                "I also have @ingredients"
        )
    }
}

class RemoveIngredient(val ingredients : IngredientList? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I do not have @ingredients",
                "Remove @ingredients",
                "I do not want @ingredients",
                "I do not want anything with @ingredients"
        )
    }
}

class AskAmount(val ingredients : IngredientList? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "How much of @ingredients",
                "How many of @ingredients",
                "How many @ingredients",
                "What was the amount of @ingredients",
                "How much?"
        )
    }
}

class AskIngredients: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What ingredients do I need?",
                "What does it contain?",
                "Ingredients",
                "Can you tell me what I need to cook that?",
                "What ingredients does it contain?"
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
