package co.hillstech.digicollection.services.apis

import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.models.Event
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.services.ServiceAPI
import co.hillstech.digicollection.services.interfaces.UserInterface
import kotlinx.coroutines.Deferred

object UserAPI {
    private val service by lazy {
        ServiceAPI.retrofit.create(UserInterface::class.java)
    }

    fun getUserMonsters(userId: Int): Deferred<MutableList<Monster>> {
        return service.getUserMonsters(userId)
    }

    fun updateBuddy(monsterId: Int, userId: Int): Deferred<BooleanResponse> {
        return service.updateBuddy(monsterId, userId)
    }
}