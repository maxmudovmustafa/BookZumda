package uz.ssd.bookzumda.di.provider

import okhttp3.OkHttpClient
import uz.ssd.bookzumda.model.data.server.interactor.ErrorResponseInterceptor
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class OkHttpClientWithErrorHandlerProvider @Inject constructor(
    private val client: OkHttpClient
) : Provider<OkHttpClient> {

    override fun get() = client
        .newBuilder()
        .addNetworkInterceptor(ErrorResponseInterceptor())
        .build()
}