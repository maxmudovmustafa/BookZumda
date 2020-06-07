package uz.ssd.bookzumda.util

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by MrShoxruxbek on 02,June,2020
 */
object Date {
    fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        return sdf.format(Date())
    }
}