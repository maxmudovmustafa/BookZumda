package uz.ssd.bookzumda.util

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import uz.ssd.bookzumda.R

/**
 * Created by MrShoxruxbek on 02,June,2020
 */
class Dialog {
    companion object{
        fun createDialog(view: View, cancel: Boolean, activity: Activity): Dialog {
            val dialog = Dialog(activity, R.style.Theme_Dim_Dialog)
            dialog.setContentView(view)
            dialog.setCancelable(cancel)
            dialog.setCanceledOnTouchOutside(cancel)
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            dialog.window?.setGravity(Gravity.CENTER)
            return dialog
        }

        fun confirmDialog(activity: Activity): AlertDialog.Builder {
            val dialogBuilder = AlertDialog.Builder(activity)
            dialogBuilder.setTitle(activity.getString(R.string.warn))

            return dialogBuilder
        }
    }
}