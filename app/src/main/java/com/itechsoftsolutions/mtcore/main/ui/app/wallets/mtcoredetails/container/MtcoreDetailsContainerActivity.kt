package com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.container

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.activity.container.ActivityMtcoreContainerFragment
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.receive.ReceiveMtcoreFragment
import com.itechsoftsolutions.mtcore.main.ui.app.wallets.mtcoredetails.send.SendMtcoreFragment
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseActivity
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import kotlinx.android.synthetic.main.activity_mtcore_details_container.*

class MtcoreDetailsContainerActivity :
    BaseActivity<MtcoreDetailsContainerMvpView, MtcoreDetailsContainerPresenter>(),
    MtcoreDetailsContainerMvpView {

    companion object {

        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context, walletID: String, action: Int, walletBalance: String) {
            val intent = Intent(context, MtcoreDetailsContainerActivity::class.java)
            intent.putExtra(Constants.IntentKeys.WALLET_ID, walletID)
            intent.putExtra(Constants.IntentKeys.ACTION, action)
            intent.putExtra(Constants.IntentKeys.WALLET_BALANCE, walletBalance)
            runCurrentActivity(context, intent)
        }
    }

    private var mAction: Int? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_mtcore_details_container

    override fun getActivityPresenter(): MtcoreDetailsContainerPresenter {
        return MtcoreDetailsContainerPresenter()
    }

    override fun startUI() {
        extractDataFromIntent()
        setListeners()
        initialize()
    }

    private fun initialize() {
        mAction?.let {
            bottom_bar.selectedItemId = when (it) {

                R.id.text_view_send -> {
                    R.id.action_send
                }

                R.id.text_view_receive -> {
                    R.id.action_receive
                }

                else -> {
                    R.id.action_activity
                }
            }
        }

        presenter.walletBalance?.let {
            text_view_amount.text = it
        }
    }

    private fun setListeners() {
        setClickListener(image_view_navigator)

        bottom_bar.setOnNavigationItemSelectedListener {
            it.isCheckable = true
            when (it.itemId) {
                R.id.action_send -> {
                    visitSendMtcore()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.action_receive -> {
                    visitReceiveMtcore()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.action_activity -> {
                    visitActivityMtcore()
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }
    }

    private fun extractDataFromIntent() {
        if (intent != null) {
            val bundle = intent.extras
            if (bundle != null) {
                if (bundle.containsKey(Constants.IntentKeys.WALLET_ID)) {
                    presenter.walletID = bundle.getString(Constants.IntentKeys.WALLET_ID)
                }

                if (bundle.containsKey(Constants.IntentKeys.WALLET_BALANCE)) {
                    presenter.walletBalance = bundle.getString(Constants.IntentKeys.WALLET_BALANCE)
                }

                if (bundle.containsKey(Constants.IntentKeys.ACTION)) {
                    mAction = bundle.getInt(Constants.IntentKeys.ACTION)
                }
            }
        }
    }

    private fun visitReceiveMtcore() {
        presenter.walletID?.let {
            val fragment = ReceiveMtcoreFragment()
            val bundle = Bundle()
            bundle.putString(Constants.IntentKeys.WALLET_ID, it)
            fragment.arguments = bundle

            commitFragment(R.id.constraint_layout_full_fragment_container, fragment)
        }
    }

    private fun visitSendMtcore() {
        presenter.walletID?.let {
            val fragment = SendMtcoreFragment()
            val bundle = Bundle()
            bundle.putString(Constants.IntentKeys.WALLET_ID, it)
            fragment.arguments = bundle

            commitFragment(R.id.constraint_layout_full_fragment_container, fragment)
        }
    }

    private fun visitActivityMtcore() {
        presenter.walletID?.let {
            val fragment =
                ActivityMtcoreContainerFragment()
            val bundle = Bundle()
            bundle.putString(Constants.IntentKeys.WALLET_ID, it)
            fragment.arguments = bundle

            commitFragment(R.id.constraint_layout_full_fragment_container, fragment)
        }
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_navigator -> {
                onBackPressed()
            }
        }
    }
}