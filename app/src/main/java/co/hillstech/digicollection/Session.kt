package co.hillstech.digicollection

import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.models.User
import com.wooplr.spotlight.SpotlightConfig

object Session {
    var username: String? = null
    var password: String? = null

    var latitude: Double? = null
    var longitude: Double? = null

    var user: User? = null
    var color: String? = null

    var wild: Int = 0

    var spotlightConfig = SpotlightConfig()

    fun getScanProgress(monster: Monster): Int {
        user?.scanList?.let {
            var scan = it.find { it.id == monster.id }
            scan?.let {
                it.progress = monster.progress
                return it.progress
            } ?: run {
                it.add(monster)
                return monster.progress
            }
            return 0
        }
        return 0
    }
}