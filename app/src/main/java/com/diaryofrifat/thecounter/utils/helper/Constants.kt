package com.diaryofrifat.thecounter.utils.helper

import com.diaryofrifat.thecounter.R

/**
 * This is a class that contains all the needed constants
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
class Constants {
    class Invalid {
        companion object {
            const val INVALID_INTEGER: Int = -1
            const val INVALID_LONG: Long = -1
        }
    }

    class Default {
        companion object {
            const val DEFAULT_STRING: String = ""
            const val DEFAULT_INTEGER: Int = 0
            const val DEFAULT_LONG: Long = 0
            const val DEFAULT_BOOLEAN: Boolean = false
            const val DEFAULT_LANGUAGE: String = "en"
            const val TRUE_INTEGER: Int = 1
            const val FALSE_INTEGER: Int = 0
            const val SPACE_STRING: String = " "
        }
    }

    class Common {
        companion object {
            const val REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$"
            const val ANDROID_HASH_KEY = "Hash Key"
            const val COMMON_TIME_ZONE = "UTC"
            const val APP_COMMON_DATE_FORMAT: String = "yyyy-MM-dd HH:mm:ss"
            const val APP_PROFILE_DATE_FORMAT: String = "dd/MM/yyyy"
            const val APP_COMMON_ONLY_DATE_FORMAT: String = "yyyy-MM-dd"
            const val APP_COMMON_TIME_FORMAT: String = "hh:mm a"
            const val APP_COMMON_DAY_FORMAT: String = "E"
            const val APP_COMMON_MONTH_FORMAT: String = "MMM"
            const val ROLE = "Bearer "
            const val TELEPHONE_URI_STARTING = "tel:"
            const val BASE_URL_APP_RESOURCES = "file:///android_res/"
            const val HTML_JUSTIFIED_STYLE = "<html>" +
                    "<style type='text/css'>" +
                    "@font-face {" +
                    "font-family: MyFont;" +
                    "src: url('font/raleway_regular.ttf')" +
                    "}" +
                    "body {" +
                    "margin: 0;" +
                    "line-height: 1.5;" +
                    "padding: 0;" +
                    "font-family: MyFont;" +
                    "font-size: 14px;" +
                    "text-align: justify;" +
                    "color: #7E7777" +
                    "}" +
                    "</style>" +
                    "<body>%s" +
                    "</body>" +
                    "</html>"
            const val HTML_SMALL_JUSTIFIED_STYLE = "<html>" +
                    "<style type='text/css'>" +
                    "@font-face {" +
                    "font-family: MyFont;" +
                    "src: url('font/raleway_regular.ttf')" +
                    "}" +
                    "body {" +
                    "margin: 0;" +
                    "line-height: 1.5;" +
                    "padding: 0;" +
                    "font-family: MyFont;" +
                    "font-size: 12px;" +
                    "text-align: justify;" +
                    "color: #FFFFFF" +
                    "}" +
                    "</style>" +
                    "<body>%s" +
                    "</body>" +
                    "</html>"
            const val HTML_MIME_TYPE = "text/html"
            const val HTML_ENCODING = "UTF-8"
            const val TWEET_URL = "https://twitter.com/intent/tweet?text="
            const val TWITTER_APPENDING_URL_PREFIX = "&url="
            const val TWITTER_APPENDING_VIA_PREFIX = "&via="
            const val TWITTER_APPENDING_HASHTAGS_PREFIX = "&hastags="
            const val TWITTER_ANDROID_APP_PACKAGE_PREFIX = "com.twitter"
            const val FACEBOOK_SHARING_URL = "https://www.facebook.com/sharer/sharer.php?u="
            const val FACEBOOK_ANDROID_APP_PACKAGE_PREFIX = "com.facebook.katana"
            const val PRODUCT_TYPE_BIDS = 2
            const val PRODUCT_TYPE_SOFTWARE = 1
        }
    }

    class TableNames {
        companion object {
            const val USER = "USER"
            const val SOFTWARE = "SOFTWARE"
        }
    }

    class ColumnNames {
        companion object {
            const val ID = "ID"
            const val SOFTWARE_ID = "SOFTWARE_ID"
            const val USER_ID = "USER_ID"
            const val USER_NAME = "USER_NAME"
            const val TITLE = "TITLE"
            const val DESCRIPTION = "DESCRIPTION"
            const val SPEED_PER_SECOND = "SPEED_PER_SECOND"
            const val CORES = "CORES"
            const val CONSUMER_RANKING_POINTS = "CONSUMER_RANKING_POINTS"
            const val PRICE_PER_MONTH = "PRICE_PER_MONTH"
            const val PRICE_PER_YEAR = "PRICE_PER_YEAR"
            const val IMAGE = "IMAGE"
            const val FIRST_NAME = "FIRST_NAME"
            const val LAST_NAME = "LAST_NAME"
            const val EMAIL = "EMAIL"
            const val I_AM = "I_AM"
            const val ID_OR_PASSPORT_NUMBER = "ID_OR_PASSPORT_NUMBER"
            const val VAT_ID = "VAT_ID"
            const val PHONE_NUMBER = "PHONE_NUMBER"
            const val FISCAL_COUNTRY = "FISCAL_COUNTRY"
            const val COUNTRY = "COUNTRY"
            const val GENDER = "GENDER"
            const val MARITAL_STATUS = "MARITAL_STATUS"
            const val DATE_OF_BIRTH = "DATE_OF_BIRTH"
            const val ZIP_CODE = "ZIP_CODE"
            const val CITY = "CITY"
            const val FULL_ADDRESS = "FULL_ADDRESS"
            const val PHOTO = "PHOTO"
            const val IS_PASSPORT_VERIFIED = "IS_PASSPORT_VERIFIED"
            const val IS_NID_VERIFIED = "IS_NID_VERIFIED"
            const val IS_UTILITY_BILL_VERIFIED = "IS_UTILITY_BILL_VERIFIED"
            const val STATE = "STATE"
            const val NUMBER = "NUMBER"
            const val COMPLEMENT = "COMPLEMENT"
        }
    }

    class File {
        companion object {
            val DIRECTORY_ROOT = DataUtils.getString(R.string.app_name)
            val PREFIX_IMAGE = "IMG_"
            val PREFIX_CROPPED_IMAGE = "IMG_CROPPED_"
            val SUFFIX_IMAGE = ".jpg"
        }
    }

    class JsonKeys {
        companion object {
            const val SUCCESS = "success"
            const val DATA = "data"
            const val TOKEN = "token"
            const val MESSAGE = "message"
            const val ACCESS_TOKEN = "access_token"
            const val ACCESS_TYPE = "access_type"
            const val USER_INFO = "user_info"
            const val NAME = "name"
            const val FIRST_NAME = "first_name"
            const val LAST_NAME = "last_name"
            const val EMAIL = "email"
            const val PHONE = "phone"
            const val ID = "id"
            const val SOFTWARES = "softwares"
            const val ADDRESS = "address"
            const val IMAGE = "image"
            const val STATUS = "status"
            const val REFERRAL_LINK = "ref"
            const val AVAILABLE_MTR_BALANCE = "available_coin_balance"
            const val AVAILABLE_BIDS_BALANCE = "available_bids_balance"
            const val AVAILABLE_POINTS_BALANCE = "available_point_balance"
            const val PENDING_WITHDRAWALS = "pending_balance"
            const val MONTHLY = "monthly"
            const val YEARLY = "yearly"
            const val SELLER = "seller"
            const val BIDERATOR = "biderator"
            const val CONSUMER = "consumer"
            const val LUCKY = "lucky"
            const val MONTHLY_WINNERS = "monthlyWinners"
            const val YEARLY_WINNERS = "yearlyWinners"
            const val POINTS = "points"
            const val REWARD = "reward"
            const val SELLER_POINTS = "seller_points"
            const val CONSUMER_POINTS = "consumer_points"
            const val BIDERATOR_POINTS = "biderator_points"
            const val TOP_SELLERS = "top_sellers"
            const val TOP_CONSUMERS = "top_consumers"
            const val TOP_BIDERATORS = "top_biderators"
            const val MONTHLY_SELLER_POINTS = "monthly_seller_points"
            const val MONTHLY_CONSUMER_POINTS = "monthly_consumer_points"
            const val MONTHLY_BIDERATOR_POINTS = "monthly_bidarator_points"
            const val YEARLY_BIDERATOR_POINTS = "yearly_bidarator_points"
            const val YEARLY_CONSUMER_POINTS = "yearly_consumer_points"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val SPEED_PER_SECOND = "speed_per_second"
            const val CORES = "cors"
            const val CONSUMER_RANKING_POINTS = "consumer_ranking_points"
            const val PRICE_PER_MONTH = "price_per_month"
            const val PRICE_PER_YEAR = "price_per_year"
            const val SELLER_HISTORY = "seller_history"
            const val CONSUMER_HISTORY = "consumer_history"
            const val BIDERATOR_HISTORY = "biderator_history"
            const val CURRENT_PAGE = "current_page"
            const val LAST_PAGE = "last_page"
            const val NEXT_PAGE_URL = "next_page_url"
            const val SOFTWARE_ID = "software_id"
            const val CREATED_AT = "created_at"
            const val UPDATED_AT = "updated_at"
            const val PRODUCT_TYPE = "product_type"
            const val HISTORIES = "histories"
            const val ORDER_ID = "order_id"
            const val PRICE = "price"
            const val MTR_WALLET = "mtr_wallet"
            const val BIDS_WALLET = "bids_wallet"
            const val POINT_WALLET = "point_wallet"
            const val BALANCE = "balance"
            const val AMOUNT = "amount"
            const val REDSYS = "redsys"
            const val BANK = "bank"
            const val ACTIVE = "active"
            const val PENDING = "pending"
            const val ADMIN_PENDING = "admin_pending"
            const val STARTED_AT = "started_at"
            const val EXPIRES_AT = "expires_at"
            const val IS_GOOGLE_AUTH_ENABLED = "is_google_auth_enabled"
            const val USER_SETTINGS = "user_settings"
            const val IS_GOOGLE_AUTH_ENABLED_AND_ON = "two_fa_enabled_at_login"
            const val BANKS = "banks"
            const val SWIFT_CODE = "swift_code"
            const val COUNTRY = "country"
            const val HOLDER_NAME = "holder_name"
            const val HOLDER_ADDRESS = "holder_address"
            const val HOLDER_IBAN = "holder_iban"
            const val FEES_AND_VAT = "feesAndVat"
            const val FEES = "fees"
            const val VAT = "vat"
            const val MONTHLY_PRICE_FEES = "monthly_price_fees"
            const val MONTHLY_PRICE_VAT = "monthly_price_vat"
            const val YEARLY_PRICE_FEES = "yearly_price_fees"
            const val YEARLY_PRICE_VAT = "yearly_price_vat"
            const val MONTHLY_TOTAL_PRICE = "monthly_total_price"
            const val YEARLY_TOTAL_PRICE = "yearly_total_price"
            const val REFERENCE_ID = "reference_id"
            const val BIDS_HISTORY = "bids_history"
            const val BIDSTORE_ORDER_ID = "bidstore_order_id"
            const val DEPOSIT = "deposit"
            const val ADDRESS_TYPE = "address_type"
            const val TRANSACTION_ID = "transaction_id"
            const val CONFIRMATIONS = "confirmations"
            const val BANK_ACCOUNT_ID = "bank_account_id"
            const val BANK_ACCOUNTS = "bank_accounts"
            const val BADGE = "badges"
            const val PHOTO = "photo"
            const val USER = "user"
            const val COUNTRY_CODE = "country_code"
            const val PHONE_NUMBER = "phone_number"
            const val GENDER = "gender"
            const val MARITAL_STATE = "marital_state"
            const val BIRTH_DATE = "birth_date"
            const val ID_OR_PASSPORT_NUMBER = "id_or_passport_number"
            const val ACTION = "action"
            const val IP_ADDRESS = "ip_address"
            const val SOURCE = "source"
            const val LOCATION = "location"
            const val ACTIVITY = "activity"
            const val POINT_WITHDRAWAL_HISTORY = "pointWithdrawalHistory"
            const val POINT_WITHDRAWAL_ID = "cashpoint_withdrawal_id"
            const val BANK_RECEIPT = "bank_receipt"
            const val ADMIN_MESSAGE = "admin_msg"
            const val USER_BANK_NAME = "user_bank_name"
            const val WITHDRAWALS = "withdrawals"
            const val GOOGLE_AUTH_ENABLED = "gauth_enabled"
            const val FULL_ADDRESS = "full_address"
            const val CITY = "city"
            const val ZIP = "zip"
            const val VAT_ID = "vat_id"
            const val I_AM = "i_am"
            const val FISCAL_COUNTRY = "fiscal_country"
            const val COUNTRIES = "countries"
            const val PASSPORT_VERIFIED = "passport_verified"
            const val NID_VERIFIED = "nid_verified"
            const val UTILITY_BILL_VERIFIED = "utility_bill_verified"
            const val CHILDREN_NUMBER = "children_number"
            const val BIDERATION_STATUS = "biderationStatus"
            const val TOTAL_SOLD = "total_bid"
            const val TOTAL_BIDERATED = "total_biderated"
            const val IN_USER_WALLETS = "in_user_wallets"
            const val TOTAL_DISTRIBUTED = "total_distributed"
            const val TOTAL_SAFE = "total_safe"
            const val TOTAL_BIDERATED_BLOCKS = "total_biderated_blocks"
            const val TOTAL_BIDERATED_PER_DAY = "total_biderated_per_day"
            const val SHARK_CLUB = "shark_club"
            const val STATE = "state"
            const val NUMBER = "number"
            const val COMPLEMENT = "complement"
            const val LANGUAGES = "languages"
            const val CODE = "code"
            const val LANG = "lang"
            const val LANGUAGE = "language"
        }
    }

    class PreferenceKeys {
        companion object {
            const val FCM_TOKEN = "fcm_token"
            const val LOGGED_IN = "logged_in"
            const val IS_FIRST_TIME = "is_first_time"
            const val ACCESS_TOKEN = "access_token"
            const val ACCESS_TYPE = "access_type"
            const val NAME = "name"
            const val EMAIL = "email"
            const val MOBILE = "mobile"
            const val PHONE = "phone"
            const val FIRST_NAME = "first_name"
            const val LAST_NAME = "last_name"
            const val REFERRAL_LINK = "referral_link"
            const val IS_GOOGLE_AUTH_SET = "is_google_auth_set"
            const val IS_GOOGLE_AUTH_SET_AND_ON = "is_google_auth_set_and_on"
            const val IS_GOOGLE_AUTH_VERIFIED = "is_google_auth_verified"
            const val USER_ID = "user_id"
        }
    }

    class IntentKeys {
        companion object {
            const val CLINIC_ID = "clinic_id"
            const val SERVICE_ID = "service_id"
            const val MONTH_LIMIT = "month_limit"
            const val CONTENT_POSITION = "content_position"
            const val EMAIL = "email"
            const val TOKEN = "token"
            const val ACTION = "action"
            const val WALLET_ID = "wallet_id"
            const val WALLET_ADDRESS = "wallet_address"
            const val WALLET_BALANCE = "wallet_balance"
            const val AMOUNT = "amount"
            const val NOTE = "note"
        }
    }

    class API {
        companion object {
            const val REGISTER = "registration"
            const val LOGIN = "login"
            const val REQUEST_TO_RESET_PASSWORD = "send-reset-password-code"
            const val RESET_PASSWORD = "reset-password"
            const val DASHBOARD = "dashboard"
            const val MY_RANKINGS = "my-rankings"
            const val RANKING_LIST = "ranking-list"
            const val MY_SELLER_HISTORY = "my-seller-history"
            const val MY_CONSUMER_HISTORY = "my-consumer-history"
            const val MY_BIDERATOR_HISTORY = "my-biderator-history"
            const val SOFTWARE_LICENSE_PURCHASE_HISTORY = "purchase-history"
            const val SOFTWARES = "softwares"
            const val WALLETS = "wallets"
            const val MTCORE_WALLET_RECEIVING_ADDRESS = "wallet-deposit-address-"
            const val GENERATE_MTCORE_WALLET_RECEIVING_ADDRESS = "create-wallet-address"
            const val PRICING_PANEL = "pricing-panel"
            const val UPLOAD_BANK_RECEIPT = "upload-bank-receipt"
            const val CHANGE_PASSWORD = "password-change"
            const val SET_TWO_FACTOR_AUTHENTICATION = "gauth-setting"
            const val REMOVE_TWO_FACTOR_AUTHENTICATION = "remove-gauth-setting"
            const val VERIFY_TWO_FACTOR_AUTHENTICATION = "gauth-verification"
            const val SOFTWARE_LICENSE_DETAILS = "buy-software-license/"
            const val BANK_SUBSCRIPTION_REQUEST = "bank-subscription-request"
            const val BIDS_HISTORY = "bids-history"
            const val LOG_OUT = "logout"
            const val MY_BADGES = "my-badges"
            const val UPLOAD_PROFILE_PICTURE = "upload-image"
            const val PROFILE_ACTIVITY = "user-activity"
            const val USER_ID_UPLOAD = "user-id-ipload"
            const val POINT_WITHDRAWAL = "cash-point-withdrawal"
            const val POINT_WITHDRAWAL_HISTORY = "cash-point-withdrawal-history"
            const val CONFIRM_POINT_WITHDRAWAL_HISTORY = "cash-point-withdrawal-confirm"
            const val USER_BANK_LIST = "user-bank-list"
            const val VALIDATE_MTCORE_WALLET = "send-coin"
            const val SEND_MTCORE = "send"
            const val MTCORE_DEPOSIT_HISTORY = "wallet-details-deposit-activity-"
            const val MTCORE_WITHDRAWAL_HISTORY = "wallet-details-withdrawal-activity-"
            const val SETTINGS = "settings"
            const val PROFILE = "profile"
            const val ADD_BANK_ACOUNT = "add-bank-account"
            const val SAVE_BASIC_INFO = "save-basic-info"
            const val COUNTRY_LIST = "country-list"
            const val UPDATE_PERSONAL_INFO = "save-personal-info"
            const val UPDATE_ADDRESS = "save-address"
            const val SET_LANGUAGE = "language-setting"
        }

        class Path {
            companion object {
                const val WALLET_ID_WITH_BRACKETS = "{wallet_id}"
                const val WALLET_ID = "wallet_id"
                const val SOFTWARE_ID_WITH_BRACKETS = "{software_id}"
                const val SOFTWARE_ID = "software_id"
            }
        }

        class Header {
            companion object {
                const val RESPONSE_FORMAT = "Accept:application/json"
            }

            class Field {
                companion object {
                    const val AUTHORIZATION = "Authorization"
                }
            }
        }

        class Body {
            class Field {
                companion object {
                    const val ID = "id"
                    const val BANK_DEPOSIT_ID = "bank_deposit_id"
                    const val FIRST_NAME = "first_name"
                    const val LAST_NAME = "last_name"
                    const val TOKEN = "token"
                    const val EMAIL = "email"
                    const val PASSWORD = "password"
                    const val CONFIRM_PASSWORD = "password_confirmation"
                    const val PAGE = "page"
                    const val WALLET_ID = "wallet_id"
                    const val IMAGE = "image"
                    const val OLD_PASSWORD = "old_password"
                    const val VALUE = "value"
                    const val GOOGLE_AUTH_CODE = "gauthcode"
                    const val SOFTWARE_ID = "software_id"
                    const val REFERENCE_ID = "reference_id"
                    const val PAYMENT_METHOD = "payment_method"
                    const val PRICE = "price"
                    const val INTERVAL = "interval"
                    const val BANK_ID = "bank_id"
                    const val PASSPORT_IMAGE = "passport_image"
                    const val ID_TYPE = "id_type"
                    const val NID_FRONT_SIDE = "nid_front_image"
                    const val NID_BACK_SIDE = "nid_back_image"
                    const val UTILITY_BILL_IMAGE = "utility_bill_image"
                    const val AMOUNT = "amount"
                    const val POINT_WITHDRAWAL_REQUEST_ID = "cash_point_withdrawal_request_id"
                    const val WALLET_ADDRESS = "wallet_address"
                    const val NOTE = "note"
                    const val ACCEPT_TERMS = "accept_terms"
                    const val HOLDER_IBAN = "holder_iban"
                    const val HOLDER_ADDRESS = "holder_address"
                    const val HOLDER_NAME = "holder_name"
                    const val SWIFT_CODE = "swift_code"
                    const val COUNTRY = "country"
                    const val ADDRESS = "address"
                    const val BANK_NAME = "name"
                    const val STATE = "state"
                    const val ZIP = "zip"
                    const val CITY = "city"
                    const val NEIGHBORHOOD = "neighborhood"
                    const val FULL_ADDRESS = "full_address"
                    const val NUMBER = "number"
                    const val COMPLEMENT = "complement"
                    const val PHONE_NUMBER = "phone_number"
                    const val VAT_ID = "vat_id"
                    const val ID_OR_PASSPORT_NUMBER = "id_or_passport_number"
                    const val GENDER = "gender"
                    const val I_AM = "i_am"
                    const val MARITAL_STATE = "marital_state"
                    const val FISCAL_COUNTRY = "fiscal_country"
                    const val BIRTH_DATE = "birth_date"
                    const val LANG = "lang"
                }
            }
        }
    }

    class SelectionIds {
        companion object {
            const val APPOINTMENT_TYPE = "AppointmentType"
            const val CLINIC = "Clinic"
            const val SESSION = "Session"
        }
    }

    class LanguageCodes {
        companion object {
            const val ENGLISH = "en"
            const val PORTUGUESE = "pt"
        }
    }
}