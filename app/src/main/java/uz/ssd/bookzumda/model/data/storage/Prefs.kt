package uz.ssd.bookzumda.model.data.storage

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import uz.ssd.bookzumda.model.entity.BooksEntity
import uz.ssd.bookzumda.model.entity.UserAccount
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class Prefs @Inject constructor(
    private val context: Context,
    private val moshi: Moshi
) {
    private fun getSharedPreference(prefName: String) =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    private val AUTH_KEY = "auth_key"
    private val AUTH_KOD = "auth_kod"
    private val authData by lazy { getSharedPreference(AUTH_KEY) }

    var item: String
        get() = authData.getString(AUTH_KOD, null) ?: ""
        set(value) = authData.edit().putString(AUTH_KOD, value).apply()


    private val DATE_KEY = "auth_key"
    private val DATE_KOD = "auth_kod"
    private val dateData by lazy { getSharedPreference(DATE_KEY) }

    var currentDate: String
        get() = dateData.getString(DATE_KOD, null) ?: getDate()
        set(value) = dateData.edit().putString(DATE_KOD, value).apply()

    private val AUTH_DATA = "auth_data"
    private val KEY_USER_ACCOUNT = "ad_account"
    private val authPrefs by lazy { getSharedPreference(AUTH_DATA) }

    var account: UserAccount
        get() {

            val json =
                authPrefs.getString(KEY_USER_ACCOUNT, null) ?: return UserAccount("", "", "+998")
            return moshi.adapter(UserAccount::class.java).fromJson(json) ?: UserAccount(
                "",
                "",
                "+998"
            )
        }
        set(value) {
            authPrefs.edit()
                .putString(KEY_USER_ACCOUNT, moshi.adapter(UserAccount::class.java).toJson(value))
                .apply()
        }


    private val ORDERS_DATA = "orders_data"
    private val KEY_CREDIT_BALLOONS = "credit_books"
    private val KEY_REPAIR_BALLOONS = "sale_books"
    private val orderPrefs by lazy { getSharedPreference(ORDERS_DATA) }

    private val balloonRepairType =
        Types.newParameterizedType(List::class.java, BooksEntity::class.java)
    var booksList: List<BooksEntity>
        get() {
            val json = orderPrefs.getString(KEY_REPAIR_BALLOONS, null)!!
            return moshi.adapter<List<BooksEntity>>(balloonRepairType).fromJson(json)
                ?: emptyList()
        }
        set(value) {
            val json = moshi.adapter<List<BooksEntity>>(balloonRepairType).toJson(value)
            orderPrefs.edit().putString(KEY_REPAIR_BALLOONS, json).apply()
        }

    fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        return sdf.format(Date())
    }
}