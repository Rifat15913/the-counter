package com.diaryofrifat.thecounter.main.data.remote.service.mtcore

import com.google.gson.GsonBuilder
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.remote.response.BaseResponse
import com.diaryofrifat.thecounter.main.data.remote.response.LoginResponse
import com.diaryofrifat.thecounter.main.data.remote.response.SoftwareResponse
import com.diaryofrifat.thecounter.main.data.remote.service.ConnectivityInterceptor
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.DataUtils
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * This is the API service interface of the project. This interface contains all the basic methods
 * needed for remote purposes.
 * @author Mohd. Asfaq-E-Azam Rifat
 */
interface ApiService {
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
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.REGISTER)
    fun register(
        @Query(Constants.API.Body.Field.FIRST_NAME) firstName: String,
        @Query(Constants.API.Body.Field.LAST_NAME) lastName: String,
        @Query(Constants.API.Body.Field.EMAIL) email: String,
        @Query(Constants.API.Body.Field.PASSWORD) password: String,
        @Query(Constants.API.Body.Field.CONFIRM_PASSWORD) confirmPassword: String,
        @Query(Constants.API.Body.Field.COUNTRY) country: String
    )
            : Flowable<Response<BaseResponse>>

    /**
     * This method logs in an user
     *
     * @param email email of the user
     * @param password password of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.LOGIN)
    fun login(
        @Query(Constants.API.Body.Field.EMAIL) email: String,
        @Query(Constants.API.Body.Field.PASSWORD) password: String
    )
            : Flowable<Response<LoginResponse>>

    /**
     * This method requests to reset the password of the user account
     *
     * @param email email of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.REQUEST_TO_RESET_PASSWORD)
    fun requestToResetPassword(@Query(Constants.API.Body.Field.EMAIL) email: String)
            : Flowable<Response<BaseResponse>>

    /**
     * This method resets the password of the user account
     *
     * @param email email of the user
     * @param token token received at user's email
     * @param password new password
     * @param confirmPassword confirm password
     * @return response consists of success status and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.RESET_PASSWORD)
    fun resetPassword(
        @Query(Constants.API.Body.Field.EMAIL) email: String,
        @Query(Constants.API.Body.Field.TOKEN) token: String,
        @Query(Constants.API.Body.Field.PASSWORD) password: String,
        @Query(Constants.API.Body.Field.CONFIRM_PASSWORD) confirmPassword: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the referral link of the user
     * @param accessToken access token of the user
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.DASHBOARD)
    fun getDashboardData(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<Response<BaseResponse>>

    /**
     * This method gets the rankings of the user
     * @param accessToken access token of the user
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.MY_RANKINGS)
    fun getMyRankings(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<Response<BaseResponse>>

    /**
     * This method gets the top ranking list
     * @param accessToken access token of the user
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.RANKING_LIST)
    fun getRankingList(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<Response<BaseResponse>>

    /**
     * This method gets the software collection
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.SOFTWARES)
    fun getSoftwareList(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<Response<SoftwareResponse>>

    /**
     * This method gets the seller history of the user
     * @param accessToken access token of the user
     * @param page current page
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.MY_SELLER_HISTORY)
    fun getMySellerHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PAGE) page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the consumer history of the user
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.MY_CONSUMER_HISTORY)
    fun getMyConsumerHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PAGE) page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the biderator history of the user
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.MY_BIDERATOR_HISTORY)
    fun getMyBideratorHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PAGE) page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the software license purchase history of the user
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.SOFTWARE_LICENSE_PURCHASE_HISTORY)
    fun getSoftwareLicensePurchaseHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PAGE) page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets wallets of the user
     * @param accessToken access token of the user
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.WALLETS)
    fun getWallets(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<Response<BaseResponse>>

    /**
     * This method gets wallet address of the user
     * @param accessToken access token of the user
     * @param walletID ID of the user's wallet
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(
        Constants.API.MTCORE_WALLET_RECEIVING_ADDRESS
                + Constants.API.Path.WALLET_ID_WITH_BRACKETS
    )
    fun getMtcoreWalletReceivingAddress(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Path(Constants.API.Path.WALLET_ID)
        walletID: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method generates wallet address of the user
     * @param accessToken access token of the user
     * @param walletID ID of the user's wallet
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.GENERATE_MTCORE_WALLET_RECEIVING_ADDRESS)
    fun generateMtcoreWalletReceivingAddress(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Query(Constants.API.Body.Field.WALLET_ID)
        walletID: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method provides the pricing panel of the software
     * @param accessToken access token of the user
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.PRICING_PANEL)
    fun getPricingPanel(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<Response<BaseResponse>>

    /**
     * This method uploads bank receipt for the purchased software
     * @param accessToken access token of the user
     * @param bankSubscriptionID order id from the bank
     * @param image image to be uploaded
     * @return base response consists of success status, data and message
     * */
    @Multipart
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.UPLOAD_BANK_RECEIPT)
    fun uploadBankReceipt(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Part(Constants.API.Body.Field.BANK_DEPOSIT_ID)
        bankSubscriptionID: RequestBody,
        @Part
        image: MultipartBody.Part
    ): Flowable<Response<BaseResponse>>

    /**
     * This method uploads profile picture of the user
     * @param accessToken access token of the user
     * @param image image as profile picture to be uploaded
     * @return base response consists of success status, data and message
     * */
    @Multipart
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.UPLOAD_PROFILE_PICTURE)
    fun uploadProfilePicture(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Part
        image: MultipartBody.Part
    ): Flowable<Response<BaseResponse>>

    /**
     * This method changes the password of the user account
     *
     * @param accessToken access token of the user
     * @param oldPassword old password
     * @param newPassword new password
     * @param confirmNewPassword confirm password
     * @return response consists of success status and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.CHANGE_PASSWORD)
    fun changePassword(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Query(Constants.API.Body.Field.OLD_PASSWORD)
        oldPassword: String,
        @Query(Constants.API.Body.Field.PASSWORD)
        newPassword: String,
        @Query(Constants.API.Body.Field.CONFIRM_PASSWORD)
        confirmNewPassword: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method sets two factor authentication
     *
     * @param accessToken access token of the user
     * @param value enabling value for setting Google Authentication
     * @return response consists of success status and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.SET_TWO_FACTOR_AUTHENTICATION)
    fun setTwoFactorAuthentication(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Query(Constants.API.Body.Field.VALUE)
        value: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method removes two factor authentication
     *
     * @param accessToken access token of the user
     * @param googleAuthCode code from Google Authenticator application
     * @return response consists of success status and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.REMOVE_TWO_FACTOR_AUTHENTICATION)
    fun removeTwoFactorAuthentication(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Query(Constants.API.Body.Field.GOOGLE_AUTH_CODE)
        googleAuthCode: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method verifies two factor authentication
     *
     * @param accessToken access token of the user
     * @param googleAuthCode code from Google Authenticator application
     * @return response consists of success status and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.VERIFY_TWO_FACTOR_AUTHENTICATION)
    fun verifyTwoFactorAuthentication(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Query(Constants.API.Body.Field.GOOGLE_AUTH_CODE)
        googleAuthCode: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method logs out the user
     * @param accessToken access token of the user
     * @return response consists of success status
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.LOG_OUT)
    fun logOut(@Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String)
            : Flowable<Response<BaseResponse>>

    /**
     * This method gets details of the software license
     *
     * @param accessToken access token of the user
     * @param softwareID ID of the software
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(
        Constants.API.SOFTWARE_LICENSE_DETAILS
                + Constants.API.Path.SOFTWARE_ID_WITH_BRACKETS
    )
    fun getSoftwareLicenseDetails(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Path(Constants.API.Path.SOFTWARE_ID)
        softwareID: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method requests for bank subscription
     *
     * @param accessToken access token of the user
     * @param softwareID ID of the software
     * @param referenceID reference ID
     * @param paymentMethod payment method (bank/card)
     * @param price price of the software
     * @param interval interval of the software
     * @param bankID ID of the desired bank
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.BANK_SUBSCRIPTION_REQUEST)
    fun requestBankSubscription(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Query(Constants.API.Body.Field.SOFTWARE_ID)
        softwareID: String,
        @Query(Constants.API.Body.Field.REFERENCE_ID)
        referenceID: String,
        @Query(Constants.API.Body.Field.PAYMENT_METHOD)
        paymentMethod: String,
        @Query(Constants.API.Body.Field.PRICE)
        price: String,
        @Query(Constants.API.Body.Field.INTERVAL)
        interval: String,
        @Query(Constants.API.Body.Field.BANK_ID)
        bankID: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the bids history of the user
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.BIDS_HISTORY)
    fun getBidsHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PAGE) page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the badges of the user
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.MY_BADGES)
    fun getProfileBadges(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the profile activity of the user
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.PROFILE_ACTIVITY)
    fun getProfileActivity(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PAGE) page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method uploads identity of the user
     * @param accessToken access token of the user
     * @param image image as id
     * @return base response consists of success status, data and message
     * */
    @Multipart
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.USER_ID_UPLOAD)
    fun uploadPassport(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Part(Constants.API.Body.Field.ID_TYPE)
        idType: RequestBody,
        @Part
        image: MultipartBody.Part
    ): Flowable<Response<BaseResponse>>

    /**
     * This method uploads identity of the user
     * @param accessToken access token of the user
     * @param frontImage front image as id
     * @param backImage back image as id
     * @return base response consists of success status, data and message
     * */
    @Multipart
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.USER_ID_UPLOAD)
    fun uploadNationalID(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Part(Constants.API.Body.Field.ID_TYPE)
        idType: RequestBody,
        @Part
        frontImage: MultipartBody.Part,
        @Part
        backImage: MultipartBody.Part
    ): Flowable<Response<BaseResponse>>

    /**
     * This method uploads identity of the user
     * @param accessToken access token of the user
     * @param image image as id
     * @return base response consists of success status, data and message
     * */
    @Multipart
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.USER_ID_UPLOAD)
    fun uploadUtilityBill(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Part(Constants.API.Body.Field.ID_TYPE)
        idType: RequestBody,
        @Part
        image: MultipartBody.Part
    ): Flowable<Response<BaseResponse>>

    /**
     * This method requests for point withdrawal
     *
     * @param accessToken access token of the user
     * @param bankAccountID ID of the desired bank
     * @param amount amount to be withdrawn
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.POINT_WITHDRAWAL)
    fun requestPointWithdrawal(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Query(Constants.API.Body.Field.BANK_ID)
        bankAccountID: String,
        @Query(Constants.API.Body.Field.AMOUNT)
        amount: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method confirms the points withdrawal by the user
     *
     * @param accessToken access token of the user
     * @param requestID id of the request
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.CONFIRM_POINT_WITHDRAWAL_HISTORY)
    fun confirmPointWithdrawalHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Query(Constants.API.Body.Field.POINT_WITHDRAWAL_REQUEST_ID)
        requestID: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the points history of the user
     * @param accessToken access token of the user
     * @param page page no
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.POINT_WITHDRAWAL_HISTORY)
    fun getPointsHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PAGE) page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the bank list of the user
     * @param accessToken access token of the user
     * @param page page no
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.USER_BANK_LIST)
    fun getUserBankList(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PAGE) page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method validates the wallet of the user
     * @param accessToken access token of the user
     * @param walletAddress wallet address of the receiving user
     * @param walletID wallet id of the sending user
     * @param amount amount to be sent
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.VALIDATE_MTCORE_WALLET)
    fun validateUserWallet(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.WALLET_ADDRESS) walletAddress: String,
        @Query(Constants.API.Body.Field.WALLET_ID) walletID: String,
        @Query(Constants.API.Body.Field.AMOUNT) amount: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method sends mtcore coin to others
     * @param accessToken access token of the user
     * @param googleAuthCode google auth code to verify
     * @param walletAddress wallet address of the receiving user
     * @param walletID wallet id of the sending user
     * @param amount amount to be sent
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.SEND_MTCORE)
    fun sendMtcore(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.GOOGLE_AUTH_CODE) googleAuthCode: String,
        @Query(Constants.API.Body.Field.WALLET_ADDRESS) walletAddress: String,
        @Query(Constants.API.Body.Field.WALLET_ID) walletID: String,
        @Query(Constants.API.Body.Field.AMOUNT) amount: String,
        @Query(Constants.API.Body.Field.NOTE) note: String?
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets mtcore wallet deposit history of the user
     * @param accessToken access token of the user
     * @param walletID ID of the user's wallet
     * @param page page no
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(
        Constants.API.MTCORE_DEPOSIT_HISTORY
                + Constants.API.Path.WALLET_ID_WITH_BRACKETS
    )
    fun getMtcoreDepositHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Path(Constants.API.Path.WALLET_ID)
        walletID: String,
        @Query(Constants.API.Body.Field.PAGE)
        page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets mtcore wallet withdrawal history of the user
     * @param accessToken access token of the user
     * @param walletID ID of the user's wallet
     * @param page page no
     * @return base response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(
        Constants.API.MTCORE_WITHDRAWAL_HISTORY
                + Constants.API.Path.WALLET_ID_WITH_BRACKETS
    )
    fun getMtcoreWithdrawalHistory(
        @Header(Constants.API.Header.Field.AUTHORIZATION)
        accessToken: String,
        @Path(Constants.API.Path.WALLET_ID)
        walletID: String,
        @Query(Constants.API.Body.Field.PAGE)
        page: Int
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the settings of the user
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.SETTINGS)
    fun getSettings(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the profile of the user
     * @param accessToken access token of the user
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.PROFILE)
    fun getProfile(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String
    ): Flowable<Response<BaseResponse>>

    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.ADD_BANK_ACOUNT)
    fun addBankAccount(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.BANK_NAME) bankName: String,
        @Query(Constants.API.Body.Field.ADDRESS) bankAddress: String,
        @Query(Constants.API.Body.Field.COUNTRY) bankCountry: String,
        @Query(Constants.API.Body.Field.SWIFT_CODE) swiftCode: String,
        @Query(Constants.API.Body.Field.HOLDER_NAME) holderName: String,
        @Query(Constants.API.Body.Field.HOLDER_ADDRESS) holderAddress: String,
        @Query(Constants.API.Body.Field.HOLDER_IBAN) holderIBAN: String,
        @Query(Constants.API.Body.Field.ACCEPT_TERMS) acceptTerms: String
    ): Flowable<Response<BaseResponse>>

    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.SAVE_BASIC_INFO)
    fun updateNameAndEmail(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.FIRST_NAME) firstName: String,
        @Query(Constants.API.Body.Field.LAST_NAME) lastName: String,
        @Query(Constants.API.Body.Field.EMAIL) email: String
    ): Flowable<Response<BaseResponse>>

    /**
     * This method gets the country list
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @GET(Constants.API.COUNTRY_LIST)
    fun getCountryList(): Flowable<Response<BaseResponse>>

    /**
     * This method updates personal information of the user
     *
     * @param accessToken access token of the user
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
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.UPDATE_PERSONAL_INFO)
    fun updatePersonalInformation(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.PHONE_NUMBER) phoneNumber: String?,
        @Query(Constants.API.Body.Field.VAT_ID) vatID: String?,
        @Query(Constants.API.Body.Field.ID_OR_PASSPORT_NUMBER) idOrPassportNumber: String?,
        @Query(Constants.API.Body.Field.I_AM) iAm: String?,
        @Query(Constants.API.Body.Field.GENDER) gender: String?,
        @Query(Constants.API.Body.Field.MARITAL_STATE) maritalState: String?,
        @Query(Constants.API.Body.Field.FISCAL_COUNTRY) fiscalCountry: String?,
        @Query(Constants.API.Body.Field.BIRTH_DATE) birthDate: String?
    ): Flowable<Response<BaseResponse>>

    /**
     * This method updates address of the user
     *
     * @param accessToken access token of the user
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
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.UPDATE_ADDRESS)
    fun updateAddress(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.COUNTRY) country: String?,
        @Query(Constants.API.Body.Field.STATE) state: String?,
        @Query(Constants.API.Body.Field.ZIP) zipCode: String?,
        @Query(Constants.API.Body.Field.CITY) city: String?,
        @Query(Constants.API.Body.Field.NEIGHBORHOOD) neighborhood: String?,
        @Query(Constants.API.Body.Field.FULL_ADDRESS) fullAddress: String?,
        @Query(Constants.API.Body.Field.NUMBER) number: String?,
        @Query(Constants.API.Body.Field.COMPLEMENT) complement: String?
    ): Flowable<Response<BaseResponse>>

    /**
     * This method sets language of the user
     *
     * @param accessToken access token of the user
     * @param languageCode desired language code
     * @return response consists of success status, data and message
     * */
    @Headers(Constants.API.Header.RESPONSE_FORMAT)
    @POST(Constants.API.SET_LANGUAGE)
    fun setLanguage(
        @Header(Constants.API.Header.Field.AUTHORIZATION) accessToken: String,
        @Query(Constants.API.Body.Field.LANG) languageCode: String
    ): Flowable<Response<BaseResponse>>

    companion object {
        private val gson = GsonBuilder().setLenient().create()

        private val customClient = OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor())
            .build()

        private val sRetrofitBuilder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(customClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(DataUtils.getString(R.string.api_base_url))
            .build()

        private var sInstance: ApiService? = null

        /**
         * This method returns an instance of the this service
         *
         * @return instance of the this service
         * */
        fun on(): ApiService {
            if (sInstance == null) {
                sInstance = sRetrofitBuilder.create(ApiService::class.java)
            }

            return sInstance!!
        }
    }
}