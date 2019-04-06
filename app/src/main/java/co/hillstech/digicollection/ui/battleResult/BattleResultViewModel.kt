package co.hillstech.digicollection.ui.battleResult

import co.hillstech.digicollection.models.Monster

class BattleResultViewModel {
    var buddy: Monster? = null
    var wild: Monster? = null
    var result: BattleResultEnum? = null
    var exp: Int = 0
    var coins: Int = 0
}