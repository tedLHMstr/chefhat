package furhatos.app.newskill.flow

import furhatos.app.newskill.flow.main.Greeting
import furhatos.app.newskill.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Time

val Parent: State = state {
    onButton("Yes", id="11", color = Color.Green) {
        furhat.say("Yes")
        furhat.listen()
    }
    onButton("No", id="12", color = Color.Green) {
        furhat.say("No")
        furhat.listen()
    }
    onButton("What do you mean?", id="13", color = Color.Green) {
        furhat.say("What du you mean?")
        furhat.listen()
    }
    onButton("Info", id="14", color = Color.Green) {
        furhat.say("I am a chef robot that can help you both decide what to eat and also guide you through recipes.")
        furhat.listen()
    }

    onUserLeave(instant = true) {
        when {
            users.count == 0 -> goto(Idle)
            it == users.current -> furhat.attend(users.other)
        }
    }

    onUserEnter {
        val curr = users.current
        furhat.attend(it)
        print(users.count)
        if (users.count > 1) {
            furhat.say("I will help you in a moment, stick by.")
            furhat.attend(curr)
        } else {
            goto(Greeting)
        }
        reentry()
    }
}