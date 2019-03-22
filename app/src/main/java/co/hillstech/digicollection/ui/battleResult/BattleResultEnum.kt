package co.hillstech.digicollection.ui.battleResult

enum class BattleResultEnum {
    WIN,
    LOSE
}

fun BattleResultEnum.toMessage(): String {
    return when (this) {
        BattleResultEnum.WIN -> "Você venceu!"
        BattleResultEnum.LOSE -> "Você perdeu."
    }
}