package co.hillstech.digicollection.services.apis

import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.services.ServiceAPI
import co.hillstech.digicollection.services.interfaces.DexInterface
import kotlinx.coroutines.Deferred

object DexAPI {
    private val service by lazy {
        ServiceAPI.retrofit.create(DexInterface::class.java)
    }

    fun requestMonsterList(userId: Int): Deferred<MutableList<Monster>> = DexAPI.service.requestMonsterList(userId)
}