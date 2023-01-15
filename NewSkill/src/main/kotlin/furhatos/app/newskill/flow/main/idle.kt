package furhatos.app.newskill.flow.main

import furhatos.flow.kotlin.*


// VI LÄR JU KUNNA ÄNDRA DENNA BARA FÖR ATT FÅ LITE BÄTTRE IDLE STATE
val Idle: State = state {

    init {
        when {
            users.count > 0 -> {
                furhat.attend(users.random)
                goto(Greeting)
            }
            users.count == 0 && furhat.isVirtual() -> furhat.say("I can't see anyone. Add a virtual user please. ")
            users.count == 0 && !furhat.isVirtual() -> furhat.say("I can't see anyone. Step closer please. ")
        }
    }

    onEntry {
        furhat.attend(users.other)
    }

    onUserEnter {
        furhat.attend(it)
        goto(Greeting)
    }
}
