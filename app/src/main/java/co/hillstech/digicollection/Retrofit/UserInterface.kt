package co.hillstech.digicollection.Retrofit

import co.hillstech.digicollection.models.*
import co.hillstech.digicollection.models.Location
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.ui.battleResult.BattleResultViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Insert {
    @GET("user/insert/")
    fun exe(@Query("name") name: String,
            @Query("password") password: String): Call<CreateClass>
}

interface Login {
    @GET("user/login/")
    fun exe(@Query("name") name: String,
            @Query("password") password: String): Call<UserResponse>
}

interface Store {
    @GET("store/getitems/")
    fun getItems(@Query("user") user: String): Call<StoreResponse>
}

interface User {
    @GET("user/updatewallet/")
    fun updateWallet(@Query("user") user: String,
                     @Query("wallet") wallet: Int): Call<BooleanResponse>

    @GET("user/getUserDevices/")
    fun getUserDevices(@Query("user") user: Int): Call<MutableList<Digivice>>

    @GET("user/changeEquipedDigivice/")
    fun changeEquipedDigivice(@Query("user") user: Int,
                              @Query("digiviceId") digivice: Int): Call<MutableList<Digivice>>

    @GET("user/updatedigivice/")
    fun updateDigivice(@Query("user") user: String,
                       @Query("digivice") digivice: Int,
                       @Query("equipe") equipe: Boolean): Call<BooleanResponse>

    @GET("user/checkdigivicecharge/")
    fun checkDigiviceCharge(@Query("user") user: Int,
                            @Query("digivice") digivice: Int): Call<ChargeResponse>

    @GET("user/updateBuddy/")
    fun updateBuddy(@Query("monster") monsterId: Int,
                    @Query("user") userId: Int): Call<BooleanResponse>

    @GET("user/updatescan/")
    fun updateScan(@Query("user") user: Int,
                   @Query("monster") monster: Int,
                   @Query("progress") progress: Int): Call<BooleanResponse>

    @GET("user/getUserMonsters/")
    fun getUserMonsters(@Query("user") userId: Int): Call<MutableList<Monster>>
}

interface Location {
    @GET("location/insert/")
    fun insert(@Query("lat") lat: String,
               @Query("lon") lon: String,
               @Query("lonmin") lonmin: String,
               @Query("lonmax") lonmax: String,
               @Query("latmin") latmin: String,
               @Query("latmax") latmax: String,
               @Query("tipo") tipo: String): Call<BooleanResponse>

    @GET("location/getinfectedmonster/")
    fun getInfectedMonster(@Query("location") location: Int,
                           @Query("digivice") digivice: Int,
                           @Query("user") user: Int): Call<MonsterResponse>

    @GET("location/getLocations/")
    fun getLocations(@Query("user") user: Int): Call<List<Location>>

    @GET("location/unlockNextLocation/")
    fun unlockNextLocation(@Query("locationId") locationId: Int,
                           @Query("user") user: Int): Call<BooleanResponse>
}

interface Monster {
    @GET("monster/getBattleResult/")
    fun getBattleResult(@Query("buddy") buddyId: Int,
                           @Query("wild") wildSpeciesId: Int,
                           @Query("user") userId: Int): Call<BattleResultViewModel>

    @GET("monster/getBossBattleResult/")
    fun getBossBattleResult(@Query("buddy") buddyId: Int,
                           @Query("boss") bossId: Int,
                           @Query("user") userId: Int): Call<BattleResultViewModel>

    @GET("monster/evolve/")
    fun evolve(@Query("from") from: Int,
               @Query("to") to: Int,
               @Query("user") user: Int): Call<Monster>

    @GET("monster/rename/")
    fun rename(@Query("monster") monster: Int,
               @Query("nick") nick: String,
               @Query("user") user: Int): Call<BooleanResponse>

    @GET("monster/digiconvert/")
    fun digiconvert(@Query("monster") monster: Int,
                    @Query("user") user: Int,
                    @Query("nick") nick: String = ""): Call<Monster>

    @GET("monster/getEvolutionLine/")
    fun getEvolutionLine(@Query("monster") buddyId: Int,
                           @Query("user") userId: Int): Call<MutableList<Monster>>
}