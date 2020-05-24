package uz.ssd.bookzumda.model.system.message


/**
 * Created by MrShoxruxbek on 22,May,2020
 */
data class SystemMessage (
    val text: String,
    val type: SystemMessageType = SystemMessageType.ALERT
)