package furhatos.app.newskill.users

import furhatos.app.newskill.flow.recipes.Recipe
import furhatos.app.newskill.flow.recipes.recipes_
import furhatos.app.newskill.nlu.*
import furhatos.nlu.common.Time
import furhatos.records.User

class FruitData (
        var fruits : FruitList = FruitList(),
        var deliveryTime: Time = Time()
)

class UserData (
        var name : String = String(),
        var currentRecipe: Recipe = recipes_[0]
)

val User.order : FruitData
    get() = data.getOrPut(FruitData::class.qualifiedName, FruitData())

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())

