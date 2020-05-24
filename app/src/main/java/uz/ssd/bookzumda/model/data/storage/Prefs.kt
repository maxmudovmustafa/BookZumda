package uz.ssd.bookzumda.model.data.storage

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.entity.UserAccount
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


    private val AUTH_DATA = "auth_data"
    private val KEY_USER_ACCOUNT = "ad_account"
    private val KEY_AUTH_TOKEN = "auth_token"
    private val authPrefs by lazy { getSharedPreference(AUTH_DATA) }

    var account: UserAccount?
        get() {
            val json = authPrefs.getString(KEY_USER_ACCOUNT, null) ?: return null
            return moshi.adapter(UserAccount::class.java).fromJson(json)
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
}