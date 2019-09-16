package com.itechsoftsolutions.mtcore.main.data

import android.app.Activity
import android.content.Context
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.local.AppLocalDataSource
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareEntity
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.software.SoftwareStatusEntity
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.main.data.remote.AppRemoteDataSource
import com.itechsoftsolutions.mtcore.main.data.remote.response.BaseResponse
import com.itechsoftsolutions.mtcore.main.data.remote.response.LoginResponse
import com.itechsoftsolutions.mtcore.main.data.remote.response.SoftwareResponse
import com.itechsoftsolutions.mtcore.main.data.remote.service.retrophoto.RetroPhoto
import com.itechsoftsolutions.mtcore.main.ui.app.authentication.login.LoginActivity
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.utils.helper.DataUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.net.HttpURLConnection

/**
 * This is the repository class of the project. This class contains all the basic methods needed
 * for the project purposes.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class BaseRepository(context: Context) {
    private val mAppLocalDataSource = AppLocalDataSource(context)
    private val mAppRemoteDataSource = AppRemoteDataSource()

    companion object {
        private lateinit var sInstance: BaseRepository

        /**
         * This method returns an instance of the this class
         *
         * @return instance of the this class
         * */
        fun on(): BaseRepository {
            return sInstance
        }

        /**
         * This method initializes the class
         * @param context application context
         * */
        @Synchronized
        fun init(context: Context) {
            synchronized(BaseRepository::class.java) {
                sInstance = BaseRepository(context)
            }
        }
    }

    fun insertUserToDatabase(entity: UserEntity): Completable {
        return mAppLocalDataSource.insertCompletable(entity)
    }

    /**
     * This method inserts list of software into database
     * @return stream of the states
     * */
    fun insertSoftwaresToDatabase(list: List<SoftwareEntity>): Completable {
        return mAppLocalDataSource.insertBulkSoftware(list)
    }

    /**
     * This method registers an user
     *
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param email email of the user
     * @param password password of the user
     * @param country country of the user
     * @return base response consists of success status, data and message
     * */
    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        country: String
    ):
            Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.register(firstName, lastName, email, password, country)
            .onBackpressureLatest()
    }

    /**
     * This method logs in an user
     *
     * @param email email of the user
     * @param password password of the user
     * @return response consists of success status, data and message
     * */
    fun login(email: String, password: String): Flowable<Response<LoginResponse>> {
        return mAppRemoteDataSource.login(email, password)
    }

    /**
     * This method resets the password of the user account
     *
     * @param email email of the user
     * @return response consists of success status, data and message
     * */
    fun requestToResetPassword(email: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.requestToResetPassword(email)
    }

    /**
     * This method resets the password of the user account
     *
     * @param email email of the user
     * @param token token received at user's email
     * @param password new password
     * @param confirmPassword confirm password
     * @return response consists of success status and message
     * */
    fun resetPassword(email: String, token: String, password: String, confirmPassword: String)
            : Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.resetPassword(email, token, password, confirmPassword)
    }

    /**
     * This method changes the password of the user account
     *
     * @param oldPassword old password
     * @param newPassword new password
     * @param confirmNewPassword confirm password
     * @return response consists of success status and message
     * */
    fun changePassword(oldPassword: String, newPassword: String, confirmNewPassword: String)
            : Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.changePassword(oldPassword, newPassword, confirmNewPassword)
    }

    /**
     * This method sets two factor authentication
     *
     * @return response consists of success status and message
     * */
    fun setTwoFactorAuthentication(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.setTwoFactorAuthentication()
    }

    /**
     * This method removes two factor authentication
     *
     * @param googleAuthCode code from Google Authenticator application
     * @return response consists of success status and message
     * */
    fun removeTwoFactorAuthentication(googleAuthCode: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.removeTwoFactorAuthentication(googleAuthCode)
    }

    /**
     * This method verifies two factor authentication
     *
     * @param googleAuthCode code from Google Authenticator application
     * @return response consists of success status and message
     * */
    fun verifyTwoFactorAuthentication(googleAuthCode: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.verifyTwoFactorAuthentication(googleAuthCode)
    }

    /**
     * This method gets the referral link of the user
     * @return base response consists of success status, data and message
     * */
    fun getDashboardData(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getDashboardData()
    }

    /**
     * This method gets the rankings of the user
     * @return base response consists of success status, data and message
     * */
    fun getMyRankings(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getMyRankings()
    }

    /**
     * This method gets the top ranking list
     * @return base response consists of success status, data and message
     * */
    fun getRankingList(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getRankingList()
    }

    /**
     * This method gets wallets of the user
     * @return base response consists of success status, data and message
     * */
    fun getWallets(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getWallets()
    }

    /**
     * This method gets wallet address of the user
     * @param walletID ID of the user's wallet
     * @return base response consists of success status, data and message
     * */
    fun getMtcoreWalletReceivingAddress(walletID: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getMtcoreWalletReceivingAddress(walletID)
    }

    /**
     * This method gets mtcore wallet deposit history of the user
     * @param walletID ID of the user's wallet
     * @param page page no
     * @return base response consists of success status, data and message
     * */
    fun getMtcoreDepositHistory(walletID: String, page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getMtcoreDepositHistory(walletID, page)
    }

    /**
     * This method gets mtcore wallet withdrawal history of the user
     * @param walletID ID of the user's wallet
     * @param page page no
     * @return base response consists of success status, data and message
     * */
    fun getMtcoreWithdrawalHistory(walletID: String, page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getMtcoreWithdrawalHistory(walletID, page)
    }

    /**
     * This method generates wallet address of the user
     * @param walletID ID of the user's wallet
     * @return base response consists of success status, data and message
     * */
    fun generateMtcoreWalletReceivingAddress(walletID: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.generateMtcoreWalletReceivingAddress(walletID)
    }

    /**
     * This method provides the pricing panel of the software
     * @return base response consists of success status, data and message
     * */
    fun getPricingPanel(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getPricingPanel()
    }

    /**
     * This method uploads bank receipt for the purchased software
     * @param bankSubscriptionID order id from the bank
     * @param imageFile image file to be uploaded
     * @return base response consists of success status, data and message
     * */
    fun uploadBankReceipt(
        bankSubscriptionID: String,
        imageFile: File,
        isPdf: Boolean
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.uploadBankReceipt(bankSubscriptionID, imageFile, isPdf)
    }

    /**
     * This method uploads profile picture of the user
     * @param imageFile image as profile picture to be uploaded
     * @return base response consists of success status, data and message
     * */
    fun uploadProfilePicture(
        imageFile: File
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.uploadProfilePicture(imageFile)
    }

    /**
     * This method gets the software collection
     * @return response consists of success status, data and message
     * */
    fun getSoftwareListFromCloud(): Flowable<Response<SoftwareResponse>> {
        return mAppRemoteDataSource.getSoftwareList()
    }

    /**
     * This method gets the consumer history of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    fun getMyConsumerHistory(page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getMyConsumerHistory(page)
    }

    /**
     * This method gets the bids history of the user
     * @return response consists of success status, data and message
     * */
    fun getBidsHistory(page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getBidsHistory(page)
    }

    /**
     * This method gets the points history of the user
     * @param page page no
     * @return response consists of success status, data and message
     * */
    fun getPointsHistory(page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getPointsHistory(page)
    }

    /**
     * This method gets the profile activity of the user
     * @param page page no
     * @return response consists of success status, data and message
     * */
    fun getProfileActivity(page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getProfileActivity(page)
    }

    /**
     * This method gets the badges of the user
     * @return response consists of success status, data and message
     * */
    fun getProfileBadges(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getProfileBadges()
    }

    /**
     * This method gets the seller history of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    fun getMySellerHistory(page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getMySellerHistory(page)
    }

    /**
     * This method gets the biderator history of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    fun getMyBideratorHistory(page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getMyBideratorHistory(page)
    }

    /**
     * This method gets the software license purchase history of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    fun getSoftwareLicensePurchaseHistory(page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getSoftwareLicensePurchaseHistory(page)
    }

    /**
     * This method gets the software collection from database
     * @return list of software
     * */
    fun getSoftwareListFromDatabase(): Flowable<List<SoftwareEntity>> {
        return mAppLocalDataSource.getSoftwareList()
    }

    /**
     * This method provides the current user
     * @return entity of the user
     * */
    fun getUserFromDatabase(userID: String): Flowable<UserEntity> {
        return mAppLocalDataSource.getUser(userID)
    }

    /**
     * This method deletes all the data of user table
     * */
    fun deleteUserTableData(): Completable {
        return mAppLocalDataSource.deleteUserTableData()
    }

    /**
     * This method gets the software collection from database
     * @return list of software
     * */
    fun getSoftwareStatusListFromDatabase(): Flowable<List<SoftwareStatusEntity>> {
        return mAppLocalDataSource.getSoftwareStatusList()
    }

    /**
     * This method logs out the user
     * @param activity current activity
     * @param eventFromDrawer if it's called from the navigation drawer or not
     * @return disposable operation
     * */
    fun logOut(activity: Activity, eventFromDrawer: Boolean = false): Disposable {
        val dialog = ProgressDialogUtils.on().showProgressDialog(activity)
        return mAppRemoteDataSource.logOut()
            .flatMapCompletable {
                mAppLocalDataSource.logOut(
                    (it.code() == HttpURLConnection.HTTP_OK
                            && it.isSuccessful)
                            || it.code() == HttpURLConnection.HTTP_UNAUTHORIZED
                ).andThen(
                    mAppLocalDataSource.deleteUserTableData()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onError(e: Throwable) {
                    dialog?.dismiss()
                    ProgressDialogUtils.on().hideProgressDialog()
                    Timber.e(e)
                    ToastUtils.error(DataUtils.getString(R.string.dashboard_log_out_error_unsuccessful))
                }

                override fun onComplete() {
                    dialog?.dismiss()
                    ProgressDialogUtils.on().hideProgressDialog()

                    if (!eventFromDrawer) {
                        ToastUtils.warning(DataUtils.getString(R.string.session_expired))
                    }

                    LoginActivity.startActivity(activity)
                    activity.finish()
                }
            })
    }

    /**
     * This method gets details of the software license
     *
     * @param softwareID ID of the software
     * @return base response consists of success status, data and message
     * */
    fun getSoftwareLicenseDetails(softwareID: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getSoftwareLicenseDetails(softwareID)
    }

    /**
     * This method requests for bank subscription
     *
     * @param softwareID ID of the software
     * @param referenceID reference ID
     * @param paymentMethod payment method (bank/card)
     * @param price price of the software
     * @param interval interval of the software
     * @param bankID ID of the desired bank
     * @return base response consists of success status, data and message
     * */
    fun requestBankSubscription(
        softwareID: String, referenceID: String, paymentMethod: String,
        price: String, interval: String, bankID: String
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.requestBankSubscription(
            softwareID, referenceID, paymentMethod,
            price, interval, bankID
        )
    }

    /**
     * This method requests for point withdrawal
     *
     * @param bankAccountID ID of the desired bank
     * @param amount amount to be withdrawn
     * @return base response consists of success status, data and message
     * */
    fun requestPointWithdrawal(bankAccountID: String, amount: String)
            : Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.requestPointWithdrawal(bankAccountID, amount)
    }

    /**
     * This method confirms the points withdrawal by the user
     *
     * @param requestID id of the request
     * @return base response consists of success status, data and message
     * */
    fun confirmPointWithdrawalHistory(requestID: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.confirmPointWithdrawalHistory(requestID)
    }

    /**
     * This method uploads identity of the user
     * @param imageFile image as id
     * @return base response consists of success status, data and message
     * */
    fun uploadPassport(imageFile: File): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.uploadPassport(imageFile)
    }

    /**
     * This method uploads identity of the user
     * @param frontImageFile front image as id
     * @param backImageFile back image as id
     * @return base response consists of success status, data and message
     * */
    fun uploadNationalID(
        frontImageFile: File,
        backImageFile: File
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.uploadNationalID(frontImageFile, backImageFile)
    }

    /**
     * This method uploads identity of the user
     * @param imageFile image as id
     * @return base response consists of success status, data and message
     * */
    fun uploadUtilityBill(imageFile: File): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.uploadUtilityBill(imageFile)
    }

    /**
     * This method gets the bank list of the user
     * @param page page no
     * @return response consists of success status, data and message
     * */
    fun getUserBankList(page: Int): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getUserBankList(page)
    }

    /**
     * This method validates the wallet of the user
     * @param walletAddress wallet address of the receiving user
     * @param walletID wallet id of the sending user
     * @param amount amount to be sent
     * @return response consists of success status, data and message
     * */
    fun validateUserWallet(
        walletAddress: String,
        walletID: String,
        amount: String
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.validateUserWallet(walletAddress, walletID, amount)
    }

    /**
     * This method sends mtcore coin to others
     * @param googleAuthCode google auth code to verify
     * @param walletAddress wallet address of the receiving user
     * @param walletID wallet id of the sending user
     * @param amount amount to be sent
     * @param note note to be added
     * @return response consists of success status, data and message
     * */
    fun sendMtcore(
        googleAuthCode: String,
        walletAddress: String,
        walletID: String,
        amount: String,
        note: String?
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.sendMtcore(
            googleAuthCode,
            walletAddress,
            walletID,
            amount,
            note
        )
    }

    /**
     * This method gets the settings of the user
     * @return response consists of success status, data and message
     * */
    fun getSettings(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getSettings()
    }

    /**
     * This method gets the profile of the user
     * @return response consists of success status, data and message
     * */
    fun getProfile(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getProfile()
    }

    fun getAllPhotosFromServer(): Flowable<List<RetroPhoto>> {
        return mAppRemoteDataSource.getAllPhotos()
    }

    fun addBankAccount(
        bankName: String,
        bankAddress: String,
        bankCountry: String,
        swiftCode: String,
        holderName: String,
        holderAddress: String,
        holderIBAN: String,
        acceptTerms: String
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.addBankAccount(
            bankName,
            bankAddress,
            bankCountry,
            swiftCode,
            holderName,
            holderAddress,
            holderIBAN,
            acceptTerms
        )
    }

    fun updateNameAndEmail(
        firstName: String,
        lastName: String,
        email: String
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.updateNameAndEmail(firstName, lastName, email)
    }

    /**
     * This method updates personal information of the user
     *
     * @param phoneNumber phone number
     * @param vatID vat id
     * @param idOrPassportNumber id or passport number
     * @param iAm what I am
     * @param gender gender
     * @param maritalState marital state
     * @param fiscalCountry fiscal country
     * @param birthDate birth date
     * @return response consists of success status, data and message
     * */
    fun updatePersonalInformation(
        phoneNumber: String?,
        vatID: String?,
        idOrPassportNumber: String?,
        iAm: String?,
        gender: String?,
        maritalState: String?,
        fiscalCountry: String?,
        birthDate: String?
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.updatePersonalInformation(
            phoneNumber, vatID, idOrPassportNumber, iAm,
            gender, maritalState, fiscalCountry, birthDate
        )
    }

    /**
     * This method updates address of the user
     *
     * @param country country
     * @param state state
     * @param zipCode zip code
     * @param city city
     * @param neighborhood neighborhood
     * @param fullAddress address in details
     * @param number number
     * @param complement complement
     * @return response consists of success status, data and message
     * */
    fun updateAddress(
        country: String?,
        state: String?,
        zipCode: String?,
        city: String?,
        neighborhood: String?,
        fullAddress: String?,
        number: String?,
        complement: String?
    ): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.updateAddress(
            country, state, zipCode, city, neighborhood,
            fullAddress, number, complement
        )
    }

    /**
     * This method sets language of the user
     *
     * @param languageCode desired language code
     * @return response consists of success status, data and message
     * */
    fun setLanguage(languageCode: String): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.setLanguage(languageCode)
    }

    fun getCountryList(): Flowable<Response<BaseResponse>> {
        return mAppRemoteDataSource.getCountryList()
    }
}
