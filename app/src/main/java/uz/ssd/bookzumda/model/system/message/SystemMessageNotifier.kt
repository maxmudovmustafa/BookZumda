package uz.ssd.bookzumda.model.system.message

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class SystemMessageNotifier {
    private val notifierRelay = PublishRelay.create<SystemMessage>()

    val notifier : Observable<SystemMessage> = notifierRelay.hide()
    fun send(message: SystemMessage) = notifierRelay.accept(message)
    fun send(message: String) = notifierRelay.accept(SystemMessage(message))
}