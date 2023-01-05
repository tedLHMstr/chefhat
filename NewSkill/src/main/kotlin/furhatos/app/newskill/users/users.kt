package furhatos.app.newskill.users

import furhatos.app.newskill.flow.recipes.Recipe
import furhatos.app.newskill.flow.recipes.recipes_
import furhatos.app.newskill.nlu.*
import furhatos.nlu.common.Time
import furhatos.records.User

class UserData (
        var name : String = String(),
        var currentRecipe: Recipe = recipes_[0]
)

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())

