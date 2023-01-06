package furhatos.app.newskill.flow.main

import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.nlu.Info
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.Greeting
import furhatos.nlu.common.No
import furhatos.nlu.common.TellName
import furhatos.nlu.common.Yes
import furhatos.app.newskill.users.userData

val Greeting : State = state(Parent) {
    onEntry {
        random(
                {furhat.say("Hello!")},
                {furhat.say("Hola!")}
        )
        furhat.gesture(Gestures.BigSmile(duration=5.0))
        furhat.listen()
    }
    onResponse<Greeting> {
        goto(AskName)
    }
    onResponse<Info> {
        furhat.say("I am a robot chef, you can ask me anything about food")
        goto(AskName)
    }
    onResponse {
        furhat.say("I did not catch that. " +
                "If you want some info say info, otherwise greet me and we will move on!")
        furhat.listen()
    }
    onNoResponse {
        furhat.say("I did not catch that. " +
                "If you want some info say info, otherwise greet me and we will move on!")
        furhat.listen()
    }
}

val AskName : State = state(Parent) {
    onEntry {
        furhat.say("My name is Chefhat, I am here to help you with recipes")
        furhat.ask("What is your name?")
    }
    onResponse <TellName> {
        users.current.userData.name = it.intent.name.toString()
        goto(ProvideRecipe)
/*
        goto(ProvideRecipeWOZ)
*/
    }
    onResponse {
        furhat.say("I did not catch that name. Some names I am good at catching are Todd and Sheila." +
                "Aliases are cool!")
        furhat.listen()
    }
    onNoResponse {
        furhat.say("Please tell me your name. Some names I am good at catching are Todd and Sheila." +
                "Aliases are cool!")
        furhat.listen()
    }
}
