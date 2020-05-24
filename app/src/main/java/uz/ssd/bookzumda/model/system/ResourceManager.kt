package uz.ssd.bookzumda.model.system

import android.content.Context
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class ResourceManager @Inject constructor(private val context: Context) {

    fun getString(id: Int) = context.getString(id)

    fun getString(id: Int, vararg formatArgs: Any) =
        String.format(context.getString(id, *formatArgs))
}