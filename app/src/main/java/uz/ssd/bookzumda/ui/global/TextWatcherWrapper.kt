package uz.ssd.bookzumda.ui.global

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
open class TextWatcherWrapper: TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {}

}