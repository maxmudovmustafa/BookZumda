package uz.ssd.bookzumda.di.provider

import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class MoshiProvider @Inject constructor(): Provider<Moshi> {

    override fun get() : Moshi = Moshi.Builder().build()
}