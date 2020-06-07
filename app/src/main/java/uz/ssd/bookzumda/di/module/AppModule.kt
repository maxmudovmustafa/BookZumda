package uz.ssd.bookzumda.di.module

import android.content.Context
import com.squareup.moshi.Moshi
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import uz.ssd.bookzumda.BuildConfig
import uz.ssd.bookzumda.di.ServerPath
import uz.ssd.bookzumda.di.provider.*
import uz.ssd.bookzumda.model.data.AppDatabase
import uz.ssd.bookzumda.model.data.BooksDao
import uz.ssd.bookzumda.model.data.CategoryDao
import uz.ssd.bookzumda.model.data.MyBookDao
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.system.AppScheduler
import uz.ssd.bookzumda.model.system.ResourceManager
import uz.ssd.bookzumda.model.system.SchedulersProvider
import uz.ssd.bookzumda.model.system.message.SystemMessageNotifier

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(String::class.java).withName(ServerPath::class.java)
            .toInstance(BuildConfig.PAY_ENDPOINT)
        bind(Prefs::class.java).singleton()
        bind(Moshi::class.java).toProvider(MoshiProvider::class.java).providesSingleton()

        bind(BooksDao::class.java).toProvider(BookDaoProvider::class.java).providesSingleton()
        bind(MyBookDao::class.java).toProvider(MyBookDaoProvider::class.java).providesSingleton()
        bind(CategoryDao::class.java).toProvider(CategoryProvider::class.java).providesSingleton()
        bind(AppDatabase::class.java).toProvider(AppDataBaseProvider::class.java)
            .providesSingleton()

        bind(SchedulersProvider::class.java).toInstance(AppScheduler())
        bind(ResourceManager::class.java).singleton()
        bind(SystemMessageNotifier::class.java).toInstance(SystemMessageNotifier())

        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }
}