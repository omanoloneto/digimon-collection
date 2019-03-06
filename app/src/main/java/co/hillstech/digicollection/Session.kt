package co.hillstech.digicollection

import android.util.Log
import co.hillstech.digicollection.Classes.Monster
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.models.Scanner
import co.hillstech.digicollection.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Session {
    companion object {

        var code: String? = null
        var specie: String? = null
        var mysim: String? = null
        var monsters: List<Monster> = listOf()
        var seens: List<String> = listOf()

        var username: String? = null
        var password: String? = null

        var latitude: Double? = null
        var longitude: Double? = null

        var user: User? = null
        var color: String? = null

        fun getScanProgress(id: Int, type: Int): Int {
            user?.scanList?.let{
                var scan = it.find { it.id == id }
                scan?.let {
                    it.progress += (6-type)
                    updateScan(it.id, it.progress)
                    return it.progress
                } ?: run {
                    scan = Scanner(id, (6-type))
                    it.add(scan!!)
                    updateScan(scan!!.id, scan!!.progress)
                    return scan!!.progress
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
}