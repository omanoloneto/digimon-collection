package co.hillstech.digicollection

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.util.Log
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.activities.LobbyActivity
import co.hillstech.digicollection.models.Location
import co.hillstech.digicollection.models.Monster
import co.hillstech.digicollection.models.User
import co.hillstech.digicollection.models.UserResponse
import co.hillstech.digicollection.utils.showMessageDialog
import com.onesignal.OneSignal
import com.wooplr.spotlight.SpotlightConfig
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Session {
    var latitude: Double? = null
    var longitude: Double? = null

    var user: User? = null
    var color: String? = null

    var wild: Int = 0

    var spotlightConfig = SpotlightConfig()

    var location: Location? = null
    var guardians: MutableList<Monster> = mutableListOf()

    var digimon: Monster? = null

    fun authenticate(context: Context, name: String, pass: String, successCallback: (() -> Unit)? = null, errorCallback: (() -> Unit)? = null) {

        val call = UserService().login().exe(name, pass)

        call.enqueue(object : Callback<UserResponse?> {

            override fun onResponse(call: Call<UserResponse?>?,
                                    response: Response<UserResponse?>?) {
                response?.body()?.let {

                    if (it.status) {

                        val preferences = context.getSharedPreferences("DigiCollePref", MODE_PRIVATE)

                        preferences.edit()
                                .putString("username", name)
                                .putString("password", pass)
                                .commit()

                        OneSignal.deleteTag("username")
                        OneSignal.sendTags(JSONObject()
                                .put("username", it.data.id))

                        Session.user = it.data as User

                        Session.user?.crest?.let {
                            with(Session.spotlightConfig) {
                                headingTvColor = Color.parseColor("#ffffff")
                                subHeadingTvColor = Color.parseColor("#ffffff")
                                lineAndArcColor = Color.parseColor(it.color)
                            }
                        }

                        successCallback?.let { it() }

                        context.startActivity(Intent(context, LobbyActivity::class.java))
                        (context as Activity).finish()

                    } else {

                        errorCallback?.let { it() } ?: run {
                            context.showMessageDialog(context.getString(R.string.error), context.getString(R.string.user_or_password_is_invalid), context.getString(R.string.ok))
                        }

                    }

                } ?: run {

                    errorCallback?.let { it() } ?: run {
                        context.showMessageDialog(context.getString(R.string.error), context.getString(R.string.user_or_password_is_invalid), context.getString(R.string.ok))
                    }

                }
            }

            override fun onFailure(call: Call<UserResponse?>?, t: Throwable?) {

                errorCallback?.let { it() } ?: run {
                    context.showMessageDialog(context.getString(R.string.error), context.getString(R.string.user_or_password_is_invalid), context.getString(R.string.ok))
                }

            }
        })
    }

    fun monsterLevel(context: Context, type: Int): String {
        return when (type) {
            1 -> context.getString(R.string.fresh)
            2 -> context.getString(R.string.intraining)
            3 -> context.getString(R.string.rookie)
            4 -> context.getString(R.string.champion)
            5 -> context.getString(R.string.ultimate)
            6 -> context.getString(R.string.mega)
            else -> context.getString(R.string.unknown)
        }
    }

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