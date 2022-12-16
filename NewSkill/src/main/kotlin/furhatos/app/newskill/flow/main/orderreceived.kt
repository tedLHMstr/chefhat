package furhatos.app.newskill.flow.main

import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.nlu.*
import furhatos.app.newskill.users.order
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.util.Language

fun orderReceived(fruitList: FruitList) : State = state(Options) {
    onEntry {
        furhat.say("${fruitList.text}, what a lovely choice!")
        fruitList.list.forEach {
            users.current.order.fruits.list.add(it)
        }
        furhat.ask("Anything else?")
    }

    onReentry {
        furhat.ask("Did you want something else?")
    }

    onResponse<No> {
        goto(DelTime)
    }
}

val DelTime = state(Parent) {
    onEntry {
        furhat.ask("When do you want the fruit delivered?")
    }
    onResponse<Time> {
        users.current.order.deliveryTime = it.intent
        goto(Confirm)
    }
    onResponse {
        furhat.say("I did not understand that.")
        reentry()
    }
}

val Confirm = state(Parent) {
    onEntry {
        furhat.ask("Okay, here is your order of ${users.current.order.fruits}. It will be delivered at ${users.current.order.deliveryTime}. Is this what you want to order?")
    }
    onResponse<No> {
        users.current.order.fruits.list.clear()
        furhat.say("Hmm okay, lets start over.")
        goto(TakingOrder)
    }
    onResponse<Yes> {
        users.current.order.fruits.list.clear()
        furhat.say("Alright, have a nice day.")
        goto(Idle)
    }
}

val Options = state(Parent) {
    onResponse<BuyFruit> {
        val fruits = it.intent.fruits
        if (fruits != null) {
            goto(orderReceived(fruits))
        }
        else {
            propagate()
        }
    }

    onResponse<RequestOptions> {
        furhat.say("We have ${Fruit().getEnum(Language.ENGLISH_US).joinToString(", ")}")
        furhat.ask("Do you want some?")
    }

    onResponse<Yes> {
        random(
            { furhat.ask("What kind of fruit do you want?") },
            { furhat.ask("What type of fruit?") }
        )
    }
}
