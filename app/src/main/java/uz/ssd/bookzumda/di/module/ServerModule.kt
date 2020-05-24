package uz.ssd.bookzumda.di.module

import okhttp3.OkHttpClient
import org.xml.sax.ErrorHandler
import toothpick.config.Module
import uz.ssd.bookzumda.di.WithErrorHandler
import uz.ssd.bookzumda.di.provider.ApiProvider
import uz.ssd.bookzumda.di.provider.OkHttpClientWithErrorHandlerProvider
import uz.ssd.bookzumda.di.provider.OkHttpProvider
import uz.ssd.bookzumda.model.data.server.interactor.ServerApi

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class ServerModule : Module() {
    init {
        bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java)
        bind(OkHttpClient::class.java).withName(WithErrorHandler::class.java)
            .toProvider(OkHttpClientWithErrorHandlerProvider::class.java)
            .providesSingleton()
        bind(ServerApi::class.java).toProvider(ApiProvider::class.java)
            .providesSingleton()

        // Error handler with logout logic
        bind(ErrorHandler::class.java).singleton()
    }
}