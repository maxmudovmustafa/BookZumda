package uz.ssd.bookzumda.entity

import com.squareup.moshi.JsonClass

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@JsonClass(generateAdapter = true)
class UserAccount(
    val full_name: String,
    val avatarUrl: String,
    val birthday: String,
    val gender: String,
    val email: String,
    val phone: String,
    val backupPhoneNumber: String
)