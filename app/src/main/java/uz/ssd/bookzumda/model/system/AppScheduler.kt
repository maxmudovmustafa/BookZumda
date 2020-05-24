package uz.ssd.bookzumda.model.system

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class AppScheduler: SchedulersProvider {
    override fun ui() = AndroidSchedulers.mainThread()

    override fun computation() = Schedulers.computation()

    override fun trampoline() = Schedulers.trampoline()

    override fun newThread() = Schedulers.newThread()

    override fun io() = Schedulers.io()
}