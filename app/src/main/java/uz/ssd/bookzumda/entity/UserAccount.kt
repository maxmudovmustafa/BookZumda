package uz.ssd.bookzumda.entity

import com.squareup.moshi.JsonClass

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@JsonClass(generateAdapter = true)
class UserAccount(
    var full_name: String,
    var gender: String,
    var phone: String
)