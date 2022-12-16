package furhatos.app.newskill.flow

import furhatos.app.newskill.flow.main.Greeting
import furhatos.app.newskill.flow.main.Idle
import furhatos.flow.kotlin.*

val Parent: State = state {

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