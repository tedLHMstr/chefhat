package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.app.newskill.flow.Parent
import furhatos.app.newskill.nlu.*
import furhatos.gestures.Gestures

val EndSession = state(Parent) {
    onEntry {
        furhat.say {
            "You have finished cooking, I am ready to serve you in the future. Goodbye ${users.current.userData.name}."
        }
        goto(Idle)
    }
}