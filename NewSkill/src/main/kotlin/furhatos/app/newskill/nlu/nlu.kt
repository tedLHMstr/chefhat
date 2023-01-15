package furhatos.app.newskill.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.ListEntity
import furhatos.nlu.common.Number
import furhatos.util.Language


class Ingredient : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("chicken", "fish", "bean", "cucumber",  "Bread", "Cheese", "Spaghetti", "Ground beef", "Onion", "Carrot", "Garlic", "Canned tomatoes", "Tomato sauce",
            "Flour", "Baking powder", "Salt", "Sugar", "Milk", "Egg", "Butter", "Blueberries", "Olive oil", "Chili powder", "Cumin", "Paprika", "Beef broth", "Kidney beans",
            "Corn", "Shrimp", "Pepper", "Lemon juice", "White wine", "Coconut oil", "Red curry paste", "Coconut milk", "Chicken broth", "Bell peppers", "tomatoes", "Black beans",
            "Cilantro", "egg", "potatoes", "carrots", "fish", "beef", "pork", "Horseradish")
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
        return listOf("fast", "quick", "instant", "short", "I do not have a lot of time", "hurry", "rush", "easy", "rapid", "swift", "immediate", "short on time")
    }
}

class IngredientList : ListEntity<Ingredient>()


class SetTimer : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Can you set a timer",
                "Set a timer",
                "Can you start a timer",
                "Please keep track of the time",
                "Keep the time",
                "Help me with the time"
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
                "@time @minOrSec",
                "Start a timer for @time @minOrSec",
                "Could you set a timer for @time @minOrSec",
                "I need a timer for @time @minOrSec",
                "Please set a timer for @time @minOrSec",
                "Could you start a timer for @time @minOrSec",
                "I need a timer set for @time @minOrSec",
                "Please start a timer for @time @minOrSec",
                "Could you set a timer for me for @time @minOrSec",
                "I need you to set a timer for @time @minOrSec",
                "Please start a timer of @time @minOrSec for me",
                "Could you start a timer of @time @minOrSec for me",
                "I need you to start a timer of @time @minOrSec",
                "Please set a timer of @time @minOrSec for me"
        )
    }
}

class RepeatStep: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Can you take that step one more time.",
                "Can you repeat that?",
                "Could you repeat that?",
                "What?",
                "Sorry, what did you say?",
                "What was I supposed to do now?",
                "Could you repeat that, please?",
                "Could you say that again?",
                "Sorry?",
                "I didn't catch that",
                "Could you clarify what you just said?",
                "I'm sorry, I missed what you said",
                "Excuse me?",
                "I'm sorry, I didn't understand that",
                "Could you repeat the last thing you said?",
                "I'm sorry, I'm having trouble hearing you",
                "Could you speak up, please? I didn't quite catch that.",
                "I'm sorry, I didn't get that.",
                "Could you repeat the instructions?"
        )
    }
}

class NextStep: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I am done, take me to the next step.",
                "Whats next?",
                "Done",
                "I am finished.",
                "next",
                "Yeah, I have", //started the oven, chopped something...
                "Continue",
                "move on",
                "Im done with"
        )
    }
}

class HaveIngredient(val ingredients : IngredientList? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I have some @ingredients",
                "@ingredients", // denna är ju ganska lugn pga denna
                "I got some chicken in my fridge",
                "Cook something with @ingredients",
                "Add @ingredients",
                "I also have @ingredients",
                "I got some @ingredients",
                "can you find something with @ingredients?",
                "what about @ingredients?",
                "Maybe @ingredients",
                "I like @ingredients",
                "I want some @ingredients"
        )
    }
}

class RemoveIngredient(val ingredients : IngredientList? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I do not have @ingredients",
                "Remove @ingredients",
                "I do not want @ingredients",
                "I do not want anything with @ingredients",
                "That's wrong, not @ingredients",
                "No @ingredients",
                "Delete @ingredients",
                "I did not say @ingredients",
                "Take away @ingredients",
                "@ingredients, be gone!"
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
        return listOf(
                "What options do you have?",
                "You can suggest something",
                "Yes, please help me!",
                "Help",
                "Yes, pick something out for me",
                "Random",
                "Yes help me",
                "Yes",
                "I have no preference",
                "Get me anything",
                "I don't mind",
                "Whatever",
                "Anything",
                "Sure"
        )
    }
}

class Info: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Who are you?",
                "What is this?",
                "What is your purpose?",
                "Who is your master?",
                "Explain what you do.",
                "Info",
                "Help"
                )
    }
}
