package furhatos.app.init

import furhatos.app.init.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class InitSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
