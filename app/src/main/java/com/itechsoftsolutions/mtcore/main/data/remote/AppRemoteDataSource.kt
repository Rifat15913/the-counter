package com.itechsoftsolutions.mtcore.main.data.remote

import com.itechsoftsolutions.mtcore.main.data.remote.response.BaseResponse
import com.itechsoftsolutions.mtcore.main.data.remote.response.LoginResponse
import com.itechsoftsolutions.mtcore.main.data.remote.response.SoftwareResponse
import com.itechsoftsolutions.mtcore.main.data.remote.service.mtcore.ApiService
import com.itechsoftsolutions.mtcore.main.data.remote.service.retrophoto.RetroPhoto
import com.itechsoftsolutions.mtcore.main.data.remote.service.retrophoto.RetroPhotoService
import com.itechsoftsolutions.mtcore.utils.helper.Constants
import com.itechsoftsolutions.mtcore.utils.helper.SharedPrefUtils
import io.reactivex.Flowable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File


/**
 * This is the remote data source class of the project. This class contains all the basic methods
 * needed for remote purposes.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class AppRemoteDataSource {

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
        return ApiService.on().register(firstName, lastName, email, password, password, country)
            .onBackpressureLatest()
    }

    /**
     * This method logs in an user
     *
     * @param email email of the user
     * @param password password of the user
     * @return response consists of success status, data and message
     * */
    fun login(email: String, password: String):
            Flowable<Response<LoginResponse>> {
        return ApiService.on().login(email, password).onBackpressureLatest()
    }

    /**
     * This method resets the password of the user account
     *
     * @param email email of the user
     * @return response consists of success status, data and message
     * */
    fun requestToResetPassword(email: String): Flowable<Response<BaseResponse>> {
        return ApiService.on().requestToResetPassword(email).onBackpressureLatest()
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
        return ApiService.on().resetPassword(email, token, password, confirmPassword)
            .onBackpressureLatest()
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
        return ApiService.on().changePassword(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            oldPassword,
            newPassword,
            confirmNewPassword
        )
            .onBackpressureLatest()
    }

    /**
     * This method sets two factor authentication
     *
     * @return response consists of success status and message
     * */
    fun setTwoFactorAuthentication(): Flowable<Response<BaseResponse>> {
        return ApiService.on().setTwoFactorAuthentication(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            if (SharedPrefUtils.readBoolean(Constants.PreferenceKeys.IS_GOOGLE_AUTH_SET)) {
                1
            } else {
                0
            }.toString()
        ).onBackpressureLatest()
    }

    /**
     * This method removes two factor authentication
     *
     * @param googleAuthCode code from Google Authenticator application
     * @return response consists of success status and message
     * */
    fun removeTwoFactorAuthentication(googleAuthCode: String): Flowable<Response<BaseResponse>> {
        return ApiService.on().removeTwoFactorAuthentication(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            googleAuthCode
        ).onBackpressureLatest()
    }

    /**
     * This method verifies two factor authentication
     *
     * @param googleAuthCode code from Google Authenticator application
     * @return response consists of success status and message
     * */
    fun verifyTwoFactorAuthentication(googleAuthCode: String): Flowable<Response<BaseResponse>> {
        return ApiService.on().verifyTwoFactorAuthentication(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            googleAuthCode
        ).onBackpressureLatest()
    }

    /**
     * This method gets the referral link of the user
     * @return base response consists of success status, data and message
     * */
    fun getDashboardData(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getDashboardData(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
    }

    /**
     * This method gets the rankings of the user
     * @return base response consists of success status, data and message
     * */
    fun getMyRankings(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getMyRankings(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
    }

    /**
     * This method gets the top ranking list
     * @return base response consists of success status, data and message
     * */
    fun getRankingList(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getRankingList(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
    }

    /**
     * This method gets wallets of the user
     * @return base response consists of success status, data and message
     * */
    fun getWallets(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getWallets(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
    }

    /**
     * This method gets wallet address of the user
     * @param walletID ID of the user's wallet
     * @return base response consists of success status, data and message
     * */
    fun getMtcoreWalletReceivingAddress(walletID: String): Flowable<Response<BaseResponse>> {
        return ApiService.on().getMtcoreWalletReceivingAddress(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            walletID
        ).onBackpressureLatest()
    }

    /**
     * This method gets mtcore wallet deposit history of the user
     * @param walletID ID of the user's wallet
     * @param page page no
     * @return base response consists of success status, data and message
     * */
    fun getMtcoreDepositHistory(walletID: String, page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getMtcoreDepositHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            walletID, page
        ).onBackpressureLatest()
    }

    /**
     * This method gets mtcore wallet withdrawal history of the user
     * @param walletID ID of the user's wallet
     * @param page page no
     * @return base response consists of success status, data and message
     * */
    fun getMtcoreWithdrawalHistory(walletID: String, page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getMtcoreWithdrawalHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            walletID, page
        ).onBackpressureLatest()
    }

    /**
     * This method generates wallet address of the user
     * @param walletID ID of the user's wallet
     * @return base response consists of success status, data and message
     * */
    fun generateMtcoreWalletReceivingAddress(walletID: String): Flowable<Response<BaseResponse>> {
        return ApiService.on().generateMtcoreWalletReceivingAddress(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            walletID
        ).onBackpressureLatest()
    }

    /**
     * This method provides the pricing panel of the software
     * @return base response consists of success status, data and message
     * */
    fun getPricingPanel(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getPricingPanel(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
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
        val requestedID = RequestBody.create(MediaType.parse("text/plain"), bankSubscriptionID)
        val requestedFile = RequestBody.create(
            MediaType.parse(
                if (isPdf) "application/pdf" else "image/*"
            ), imageFile
        )

        // MultipartBody.Part is used to send also the actual file name
        val image = MultipartBody.Part
            .createFormData(
                Constants.API.Body.Field.IMAGE,
                imageFile.name,
                requestedFile
            )

        return ApiService.on().uploadBankReceipt(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            requestedID, image
        ).onBackpressureLatest()
    }

    /**
     * This method uploads profile picture of the user
     * @param imageFile image as profile picture to be uploaded
     * @return base response consists of success status, data and message
     * */
    fun uploadProfilePicture(
        imageFile: File
    ): Flowable<Response<BaseResponse>> {
        val requestedFile = RequestBody.create(MediaType.parse("image/*"), imageFile)

        // MultipartBody.Part is used to send also the actual file name
        val image = MultipartBody.Part
            .createFormData(
                Constants.API.Body.Field.IMAGE,
                imageFile.name,
                requestedFile
            )

        return ApiService.on().uploadProfilePicture(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            image
        ).onBackpressureLatest()
    }

    /**
     * This method gets the software collection
     * @return response consists of success status, data and message
     * */
    fun getSoftwareList(): Flowable<Response<SoftwareResponse>> {
        return ApiService.on().getSoftwareList(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
    }

    /**
     * This method gets the seller history of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    fun getMySellerHistory(page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getMySellerHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN), page
        ).onBackpressureLatest()
    }

    /**
     * This method gets the consumer history of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    fun getMyConsumerHistory(page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getMyConsumerHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN), page
        ).onBackpressureLatest()
    }

    /**
     * This method gets the bids history of the user
     * @return response consists of success status, data and message
     * */
    fun getBidsHistory(page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getBidsHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN), page
        ).onBackpressureLatest()
    }

    /**
     * This method gets the points history of the user
     * @param page page no
     * @return response consists of success status, data and message
     * */
    fun getPointsHistory(page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getPointsHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN), page
        ).onBackpressureLatest()
    }

    /**
     * This method gets the profile activity of the user
     * @param page page no
     * @return response consists of success status, data and message
     * */
    fun getProfileActivity(page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getProfileActivity(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN), page
        ).onBackpressureLatest()
    }

    /**
     * This method gets the badges of the user
     * @return response consists of success status, data and message
     * */
    fun getProfileBadges(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getProfileBadges(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
    }

    /**
     * This method gets the biderator history of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    fun getMyBideratorHistory(page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getMyBideratorHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN), page
        ).onBackpressureLatest()
    }

    /**
     * This method gets the software license purchase history of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    fun getSoftwareLicensePurchaseHistory(page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getSoftwareLicensePurchaseHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN), page
        ).onBackpressureLatest()
    }

    /**
     * This method logs out the user
     * @return response consists of success status
     * */
    fun logOut(): Flowable<Response<BaseResponse>> {
        return ApiService.on()
            .logOut(
                SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                        Constants.Default.SPACE_STRING +
                        SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
            ).onBackpressureLatest()
    }

    /**
     * This method gets details of the software license
     *
     * @param softwareID ID of the software
     * @return base response consists of success status, data and message
     * */
    fun getSoftwareLicenseDetails(softwareID: String): Flowable<Response<BaseResponse>> {
        return ApiService.on().getSoftwareLicenseDetails(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            softwareID
        ).onBackpressureLatest()
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
        return ApiService.on().requestBankSubscription(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            softwareID,
            referenceID,
            paymentMethod,
            price,
            interval,
            bankID
        ).onBackpressureLatest()
    }

    /**
     * This method requests for point withdrawal
     *
     * @param bankAccountID ID of the desired bank
     * @param amount amount to be withdrawn
     * @return base response consists of success status, data and message
     * */
    fun requestPointWithdrawal(
        bankAccountID: String,
        amount: String
    ): Flowable<Response<BaseResponse>> {
        return ApiService.on().requestPointWithdrawal(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            bankAccountID, amount
        ).onBackpressureLatest()
    }

    /**
     * This method confirms the points withdrawal by the user
     *
     * @param requestID id of the request
     * @return base response consists of success status, data and message
     * */
    fun confirmPointWithdrawalHistory(requestID: String): Flowable<Response<BaseResponse>> {
        return ApiService.on().confirmPointWithdrawalHistory(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            requestID
        ).onBackpressureLatest()
    }

    /**
     * This method uploads identity of the user
     * @param imageFile image as id
     * @return base response consists of success status, data and message
     * */
    fun uploadPassport(imageFile: File): Flowable<Response<BaseResponse>> {
        val requestedIDType = RequestBody.create(MediaType.parse("text/plain"), "1")
        val requestedFile = RequestBody.create(MediaType.parse("image/*"), imageFile)

        val image = MultipartBody.Part
            .createFormData(
                Constants.API.Body.Field.PASSPORT_IMAGE,
                imageFile.name,
                requestedFile
            )

        return ApiService.on().uploadPassport(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            requestedIDType, image
        ).onBackpressureLatest()
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
        val requestedIDType = RequestBody.create(MediaType.parse("text/plain"), "2")
        val requestedFrontFile = RequestBody.create(MediaType.parse("image/*"), frontImageFile)
        val requestedBackFile = RequestBody.create(MediaType.parse("image/*"), backImageFile)

        val frontImage = MultipartBody.Part
            .createFormData(
                Constants.API.Body.Field.NID_FRONT_SIDE,
                frontImageFile.name,
                requestedFrontFile
            )

        val backImage = MultipartBody.Part
            .createFormData(
                Constants.API.Body.Field.NID_BACK_SIDE,
                backImageFile.name,
                requestedBackFile
            )

        return ApiService.on().uploadNationalID(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            requestedIDType, frontImage, backImage
        ).onBackpressureLatest()
    }

    /**
     * This method uploads identity of the user
     * @param imageFile image as id
     * @return base response consists of success status, data and message
     * */
    fun uploadUtilityBill(imageFile: File): Flowable<Response<BaseResponse>> {
        val requestedIDType = RequestBody.create(MediaType.parse("text/plain"), "3")
        val requestedFile = RequestBody.create(MediaType.parse("image/*"), imageFile)

        val image = MultipartBody.Part
            .createFormData(
                Constants.API.Body.Field.UTILITY_BILL_IMAGE,
                imageFile.name,
                requestedFile
            )

        return ApiService.on().uploadUtilityBill(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            requestedIDType, image
        ).onBackpressureLatest()
    }

    /**
     * This method gets the bank list of the user
     * @param page page no
     * @return response consists of success status, data and message
     * */
    fun getUserBankList(page: Int): Flowable<Response<BaseResponse>> {
        return ApiService.on().getUserBankList(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN), page
        ).onBackpressureLatest()
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
        return ApiService.on().validateUserWallet(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            walletAddress, walletID, amount
        ).onBackpressureLatest()
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
        return ApiService.on().sendMtcore(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            googleAuthCode,
            walletAddress, walletID, amount, note
        ).onBackpressureLatest()
    }

    /**
     * This method gets the settings of the user
     * @return response consists of success status, data and message
     * */
    fun getSettings(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getSettings(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
    }

    /**
     * This method gets the profile of the user
     * @return response consists of success status, data and message
     * */
    fun getProfile(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getProfile(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN)
        ).onBackpressureLatest()
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
        return ApiService.on().addBankAccount(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            bankName,
            bankAddress,
            bankCountry,
            swiftCode,
            holderName,
            holderAddress,
            holderIBAN, acceptTerms
        ).onBackpressureLatest()
    }

    fun updateNameAndEmail(
        firstName: String,
        lastName: String,
        email: String
    ): Flowable<Response<BaseResponse>> {
        return ApiService.on().updateNameAndEmail(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            firstName, lastName, email
        ).onBackpressureLatest()
    }

    fun getCountryList(): Flowable<Response<BaseResponse>> {
        return ApiService.on().getCountryList()
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
        return ApiService.on().updatePersonalInformation(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
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
        return ApiService.on().updateAddress(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
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
        return ApiService.on().setLanguage(
            SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TYPE) +
                    Constants.Default.SPACE_STRING +
                    SharedPrefUtils.readString(Constants.PreferenceKeys.ACCESS_TOKEN),
            languageCode
        )
    }

    fun getAllPhotos(): Flowable<List<RetroPhoto>> {
        return RetroPhotoService.on().getAllPhotos().onBackpressureLatest()
    }
}