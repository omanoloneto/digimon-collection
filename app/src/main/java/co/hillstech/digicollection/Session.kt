package co.hillstech.digicollection

import android.util.Log
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Session {
    var username: String? = null
    var password: String? = null

    var latitude: Double? = null
    var longitude: Double? = null

    var user: User? = null
    var color: String? = null

    fun getScanProgress(monster: Monster): Int {
        user?.scanList?.let {
            var scan = it.find { it.id == monster.id }
            scan?.let {
                it.progress += (6 - monster.type)
                updateScan(it.id, it.progress)
                return it.progress
            } ?: run {
                it.add(monster)
                updateScan(monster.id, monster.progress)
                return monster.progress
            }
            return 0
        }
        return 0
    }

    private fun updateScan(monster: Int, progress: Int) {
        Session.user?.let {
            val call = UserService().user().updateScan(it.id, monster, progress)

            call.enqueue(object : Callback<BooleanResponse?> {

                override fun onResponse(call: Call<BooleanResponse?>?,
                                        response: Response<BooleanResponse?>?) {
                    response?.body()?.let {
                        Log.e("SUCCESS", it.status.toString())
                    } ?: run {
                        Log.e("ERROR", "ERROR")
                    }
                }

                override fun onFailure(call: Call<BooleanResponse?>?, t: Throwable?) {
                    Log.e("ERROR", t?.message)
                }
            })
        }
    }
}