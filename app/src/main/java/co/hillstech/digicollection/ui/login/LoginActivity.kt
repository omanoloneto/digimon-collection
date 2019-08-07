package co.hillstech.digicollection.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import co.hillstech.digicollection.R
import co.hillstech.digicollection.activities.bases.BaseFragmentActivity
import co.hillstech.digicollection.utils.showToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.view_create_account.*
import kotlinx.android.synthetic.main.view_login.*


class LoginActivity : BaseFragmentActivity(), LoginPresenter.View {

    private val presenter = LoginPresenter()
    private var loginStage = LoginEnum.LOGIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.view = this

        setStatusBarColor()

        setupViews()
    }

    override fun setupViews() {
        viewLoginFragment.visibility = View.VISIBLE

        viewCreateAccount.setOnClickListener { switchToCreateAccount() }

        viewForgotPassword.setOnClickListener { switchToForgotPassword() }

        viewLoginButton.setOnClickListener {
            presenter.tryLogin(
                    viewLoginUsername.text.toString(),
                    viewLoginPassword.text.toString()
            )
        }

        viewCreateAccountButton.setOnClickListener {
            presenter.createAccount(
                    viewCreateAccountUsername.text.toString(),
                    viewCreateAccountPassword.text.toString()
            )
        }
    }

    override fun onBackPressed() {
        when(loginStage){
            LoginEnum.LOGIN -> super.onBackPressed()
            LoginEnum.CREATE_ACCOUNT -> switchToLogin()
            LoginEnum.FORGOT_PASSWORD -> switchToLogin()
        }
    }

    //TODO: remove this function
    override fun getContext(): Context {
        return this
    }

    override fun showProgressLoader() {
        progressRingCall(this)
    }

    override fun hideProgressLoader() {
        progressRingDismiss()
    }

    override fun showLoginErrorMessage() {
        showToast(getString(R.string.user_or_password_is_invalid))
    }

    override fun showLoginInputsErrorMessage() {
        showToast(getString(R.string.enter_the_data_required))
    }

    override fun onCreateAccountSuccess() {
        showToast(getString(R.string.create_criado))
        switchToLogin()
    }

    override fun showCreateAccountErrorMessage() {
        showToast(getString(R.string.create_naocriado))
    }

    override fun showUnexpectedErrorMessage() {
        showToast(getString(R.string.an_unexpected_error_happened))
    }

    private fun switchToLogin() {
        viewCreateAccountFragment.visibility = View.GONE
        viewLoginFragment.visibility = View.VISIBLE
        loginStage = LoginEnum.LOGIN
    }

    private fun switchToCreateAccount() {
        viewLoginFragment.visibility = View.GONE
        viewCreateAccountFragment.visibility = View.VISIBLE
        loginStage = LoginEnum.CREATE_ACCOUNT
    }

    private fun switchToForgotPassword() {
        //TODO: forgot password activity
    }

}
