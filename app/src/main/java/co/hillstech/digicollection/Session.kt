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

    fun authenticate(context: Context, name: String, pass: String) {

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

                        context.startActivity(Intent(context, LobbyActivity::class.java))
                        (context as Activity).finish()

                    } else {

                        context.showMessageDialog(context.getString(R.string.error), context.getString(R.string.user_or_password_is_invalid), context.getString(R.string.ok))

                    }

                } ?: run {

                    context.showMessageDialog(context.getString(R.string.error), context.getString(R.string.user_or_password_is_invalid), context.getString(R.string.ok))

                }
            }

            override fun onFailure(call: Call<UserResponse?>?, t: Throwable?) {
                context.showMessageDialog(context.getString(R.string.error), context.getString(R.string.user_or_password_is_invalid), context.getString(R.string.ok))
                Log.e("ERROR", t?.message)
            }
        })
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