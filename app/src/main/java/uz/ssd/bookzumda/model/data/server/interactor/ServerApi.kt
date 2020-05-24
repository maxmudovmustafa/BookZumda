package uz.ssd.bookzumda.model.data.server.interactor

import android.telecom.Call
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
interface ServerApi {
    companion object {
        const val API_PATH = "api/v2/secure"
    }

    /**
     * @param hash @see [SignInRequest]
     */
    @FormUrlEncoded
    @GET("$API_PATH/profile/data")
    fun getUser(): Response<String>
}