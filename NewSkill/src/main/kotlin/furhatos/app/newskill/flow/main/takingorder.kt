package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No

val TakingOrder = state(parent = Options) {
    onEntry {
        random(
                { furhat.ask("How about I tell you what you should eat?") },
                { furhat.ask("Do you want me to help you pick what to eat?") }
        )
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame, eat shit and have a splendid day!")
        goto(Idle)
    }

}
