package furhatos.app.newskill.flow.main

import furhatos.app.newskill.users.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No

val TakingOrder = state(parent = Options) {
    onEntry {
        random(
                { furhat.ask("Okay ${users.current.userData.name}, how about some fruits?") },
                { furhat.ask("${users.current.userData.name}, do you want some fruits?") }
        )
    }

    onResponse<No> {
        furhat.say("Okay, that's a shame. Have a splendid day!")
        goto(Idle)
    }

}
