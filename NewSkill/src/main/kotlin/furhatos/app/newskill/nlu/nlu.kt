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
