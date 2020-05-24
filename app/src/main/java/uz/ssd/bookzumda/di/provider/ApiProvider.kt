package uz.ssd.bookzumda.di.provider

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import uz.ssd.bookzumda.di.ServerPath
import uz.ssd.bookzumda.model.data.server.interactor.ServerApi
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class ApiProvider @Inject constructor(
    /*@WithErrorHandler */
    private val okHttpClient: OkHttpClient,
    private val moshi: Moshi,
    @ServerPath private val serverPath: String
) : Provider<ServerApi> {

    override fun get() = getOriginalApi()

    private fun getOriginalApi() =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(serverPath)
            .build()
            .create(ServerApi::class.java)
}