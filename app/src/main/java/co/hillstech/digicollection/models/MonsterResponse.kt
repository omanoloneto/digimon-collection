package co.hillstech.digicollection.models

class MonsterResponse (
        var status: Boolean,
        var message: String,
        var monster: Monster?
) {
    fun fieldTypeDontSet(): Boolean{
        return !status && message == "FIELD_TYPE_DONT_SET"
    }

    fun isFieldClear(): Boolean {
        return status && message == "IS_FIELD_CLEAR"
    }

    fun isFieldMonstersDontSet(): Boolean {
        return !status && message == "FIELD_MONSTERS_DONT_SET"
    }

    fun isDigiviceNotCharged(): Boolean {
        return !status && message == "DIGIVICE_HAVE_NO_CHARGE"
    }
}