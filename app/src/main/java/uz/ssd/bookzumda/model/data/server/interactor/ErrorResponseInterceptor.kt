package uz.ssd.bookzumda.model.data.server.interactor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class ErrorResponseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val code = response.code()
        if (code in 400..500) throw ServerError(code)

        return response
    }
}