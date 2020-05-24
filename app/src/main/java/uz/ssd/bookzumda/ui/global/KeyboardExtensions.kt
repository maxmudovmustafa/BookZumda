package uz.ssd.bookzumda.ui.global

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import android.widget.EditText
import androidx.annotation.NonNull

/**
 * Created by Islam Matnazarov (aka @matnazaroff) on 08/12/2019.
 */

/**
 * Show the soft input.
 *
 * @param view The view.
 */
fun Activity.showSoftInput(@NonNull view: View) {
    showSoftInput(view, 0)
}

/**
 * Show the soft input.
 *
 * @param view  The view.
 * @param flags Provides additional operating flags.  Currently may be
 * 0 or have the [InputMethodManager.SHOW_IMPLICIT] bit set.
 */
fun Activity.showSoftInput(@NonNull view: View, flags: Int) {
    val imm = application.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.requestFocus()
    imm.showSoftInput(view, flags, object : ResultReceiver(Handler()) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            if (resultCode == RESULT_UNCHANGED_HIDDEN || resultCode == RESULT_HIDDEN) {
                toggleSoftInput()
            }
        }
    })
    imm.toggleSoftInput(SHOW_FORCED, HIDE_IMPLICIT_ONLY)
}

/**
 * Hide the soft input.
 */
fun Activity.hideSoftInput() {
    var view = currentFocus
    if (view == null) {
        val decorView = window.decorView
        val focusView = decorView.findViewWithTag<View>("keyboardTagView")
        if (focusView == null) {
            view = EditText(this)
            view.tag = "keyboardTagView"
            (decorView as ViewGroup).addView(view, 0, 0)
        } else {
            view = focusView
        }
        view.requestFocus()
    }
    hideSoftInput(view)
}

/**
 * Hide the soft input.
 *
 * @param view The view.
 */
fun Activity.hideSoftInput(view: View) {
    val imm = application.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0, object : ResultReceiver(Handler()) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            if (resultCode == RESULT_UNCHANGED_SHOWN || resultCode == RESULT_SHOWN) {
                toggleSoftInput()
            }
        }
    })
}

/**
 * Toggle the soft input display or not.
 */
fun Activity.toggleSoftInput() {
    val imm = application.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(0, 0)
}