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
                {furhat.say("Hello")},
                {furhat.say("Whazzup man?")}
        )
        furhat.gesture(Gestures.BigSmile(duration=5.0))
        furhat.listen()
    }
    onResponse<Greeting> {
        goto(AskName)
    }
    onResponse<Info> {
        furhat.say("I am a talking robot that sells fruit, have some respect.")
        goto(AskName)
    }
}

val AskName : State = state(Parent) {
    onEntry {
        furhat.ask("What is your name?")
    }
    onResponse<TellName> {
        users.current.userData.name = it.intent as String
        goto(TakingOrder)
    }
}