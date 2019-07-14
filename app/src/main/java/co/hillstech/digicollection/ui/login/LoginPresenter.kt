package co.hillstech.digicollection.ui.login

import android.content.Context
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.services.apis.UserAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginPresenter {

    var view: View? = null

    fun tryLogin(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            view?.showProgressLoader()
            Session.authenticate(
                    context = view!!.getContext(),
                    name = username,
                    pass = password,
                    errorCallback = {
                        view?.hideProgressLoader()
                        view?.showLoginErrorMessage()
                    }
            )
        } else {
            view?.hideProgressLoader()
            view?.showLoginInputsErrorMessage()
        }
    }

    fun createAccount(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            view?.showProgressLoader()
            GlobalScope.launch(Dispatchers.Default) {
                try{
                    val response = UserAPI.createAccount(username, password).await()
                    if(response.status == "true") {
                        GlobalScope.launch(Dispatchers.Main) {
                            view?.onCreateAccountSuccess()
                        }
                    }
                }catch (e: Exception){
                    GlobalScope.launch(Dispatchers.Main){
                        view?.showUnexpectedErrorMessage()
                    }
                }finally {
                    GlobalScope.launch(Dispatchers.Main){
                        view?.hideProgressLoader()
                    }
                }
            }
        }else{
            view?.hideProgressLoader()
            view?.showLoginInputsErrorMessage()
        }
    }

    interface View {
        fun getContext(): Context
        fun showProgressLoader()
        fun hideProgressLoader()
        fun showLoginErrorMessage()
        fun showLoginInputsErrorMessage()
        fun showCreateAccountErrorMessage()
        fun showUnexpectedErrorMessage()
        fun onCreateAccountSuccess()
    }
}