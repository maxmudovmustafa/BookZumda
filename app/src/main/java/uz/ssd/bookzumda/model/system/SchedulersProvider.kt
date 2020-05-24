package uz.ssd.bookzumda.model.system

import io.reactivex.Scheduler

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
interface SchedulersProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun trampoline(): Scheduler
    fun newThread(): Scheduler
    fun io(): Scheduler
}