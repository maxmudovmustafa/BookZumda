package uz.ssd.bookzumda.ui.global

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.*
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.annotation.StringDef
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace
import timber.log.Timber
import uz.ssd.bookzumda.R
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * Created by MrShoxruxbek on 22,May,2020
 */

fun Navigator.setLaunchScreen(screen: SupportAppScreen) {
    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getTintDrawable(drawableRes: Int, colorRes: Int): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)!!.mutate()
    val wrapped = DrawableCompat.wrap(source)
    DrawableCompat.setTint(wrapped, color(colorRes))
    return wrapped
}

fun Context.getTintDrawable(
    drawableRes: Int,
    colorResources: IntArray,
    states: Array<IntArray>
): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)!!.mutate()
    val wrapped = DrawableCompat.wrap(source)
    DrawableCompat.setTintList(
        wrapped,
        ColorStateList(states, colorResources.map { color(it) }.toIntArray())
    )
    return wrapped
}

fun TextView.setStartDrawable(drawable: Drawable) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        drawable,
        null,
        null,
        null
    )
}

fun ImageView.tint(colorRes: Int) = this.setColorFilter(this.context.color(colorRes))

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun TextView.showTextOrHide(str: String?) {
    this.text = str
    this.visible(!str.isNullOrBlank())
}

fun Fragment.tryOpenLink(link: String?, basePath: String? = "https://google.com/search?q=") {
    if (link != null) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    when {
                        URLUtil.isValidUrl(link) -> Uri.parse(link)
                        else -> Uri.parse(basePath + link)
                    }
                )
            )
        } catch (e: Exception) {
            Timber.e("tryOpenLink error: $e")
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://google.com/search?q=$link")
                )
            )
        }
    }
}

fun Fragment.shareText(text: String?) {
    text?.let {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                },
                getString(R.string.share_to)
            )
        )
    }
}

fun Fragment.sendEmail(email: String?) {
    if (email != null) {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null)),
                null
            )
        )
    }
}

fun Fragment.showSnackMessage(message: String) {
    view?.showSnackMessage(message)
}

fun View.showSnackMessage(message: String) {
    val ssb = SpannableStringBuilder().apply {
        append(message)
        setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            message.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    Snackbar.make(this, ssb, Snackbar.LENGTH_LONG).show()
}

fun Activity.hideKeyboard() {
    currentFocus?.apply {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun View.setBackgroundTintByColor(@ColorInt color: Int) {
    val wrappedDrawable = DrawableCompat.wrap(background)
    DrawableCompat.setTint(wrappedDrawable.mutate(), color)
}

fun Toolbar.setTitleEllipsize(ellipsize: TextUtils.TruncateAt) {
    val fakeTitle = "fakeTitle"
    title = fakeTitle
    for (i in 0..childCount) {
        val child = getChildAt(i)
        if (child is TextView && child.text == fakeTitle) {
            child.ellipsize = ellipsize
            break
        }
    }
    title = ""
}

fun <TYPE : Any> Moshi.toJson(value: TYPE): String =
    adapter(value.javaClass).toJson(value)

fun Long.formattedMoney(showDecimal: Boolean = true, tiyinToSum: Boolean = true) =
    formatMoney(if (tiyinToSum) (this / 100.toDouble()) else this.toDouble(), showDecimal)

fun formatMoney(value: Double, showDecimal: Boolean): String {
    val decimalFormat = DecimalFormat("###,###,##0")
    decimalFormat.groupingSize = 3
    decimalFormat.minimumFractionDigits = 0

    val s = DecimalFormatSymbols()
    s.groupingSeparator = ' '
    val symbols = decimalFormat.decimalFormatSymbols
    s.decimalSeparator = symbols.decimalSeparator
    decimalFormat.decimalFormatSymbols = s

    decimalFormat.minimumFractionDigits = if (showDecimal) 2 else 0
    decimalFormat.maximumFractionDigits = if (showDecimal) 2 else 0

    return decimalFormat.format(value)
}

fun getColoredSpanned(message: String, color: String) = "<font color=$color>$message</font>"

@Throws(IOException::class)
fun Context.readJsonAsset(fileName: String): String {
    val inputStream = assets.open(fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charsets.UTF_8)
}

@StringDef(PHONE_9_MASK, PHONE_8_MASK, CPF_MASK, ZIP_CODE_PT_BR, MONTH_YEAR, CREDIT_CARD)
@Retention(AnnotationRetention.SOURCE)
annotation class MaskType

const val PHONE_9_MASK = "(##) ###-##-##"
const val PHONE_8_MASK = "(##) ####-####"
const val CPF_MASK = "###.###.###-##"
const val ZIP_CODE_PT_BR = "#####-###"

const val MONTH_YEAR = "##/##"

const val CREDIT_CARD = "#### #### #### ####"

fun String.unmask(): String {
    return replace("[\\./\\(\\) \\-\\+]".toRegex(), "")
}

@Suppress("UNUSED")
fun EditText.insert(@MaskType mask: String): TextWatcher {
    val textWatcher = MaskTextWatcher(mask)

    addTextChangedListener(textWatcher)

    return textWatcher
}

fun EditText.insertPhoneMask(): TextWatcher {
    val textWatcher = object : MaskTextWatcher() {
        override fun getMask(unmaskedValue: String): String {
            if (unmaskedValue.length < 11) {
                return PHONE_8_MASK
            }

            return PHONE_9_MASK
        }
    }

    addTextChangedListener(textWatcher)

    return textWatcher
}

fun String.formatPhone(): String {
    var _phone = this

    if (length == 8)
        _phone = String.format("%s-%s", substring(0, 4), substring(4, length))
    else if (length == 9)
        _phone = String.format("%s-%s", substring(0, 5), substring(5, length))
    else if (length == 10)
        _phone = String.format("%s %s-%s", substring(0, 2), substring(2, 6), substring(6, length))
    else if (length == 11)
        _phone = String.format("%s %s-%s", substring(0, 2), substring(2, 7), substring(7, length))

    return _phone
}

open class MaskTextWatcher(val mask: String = "") : SimpleTextWatcher() {

    internal var oldValue = ""

    internal var isUpdating: Boolean = false

    open fun getMask(unmaskedValue: String): String {
        return mask
    }

    override fun afterTextChanged(edit: Editable) {
        val unmaskedString = edit.toString().unmask()
        val maskedString = StringBuilder("")
        val mask = getMask(unmaskedString)

        // EditText was GC'ed

        if (isUpdating) {
            oldValue = unmaskedString
            isUpdating = false

            return
        }

        var i = 0

        for (m in mask.toCharArray()) {
            if (m != '#' && i < unmaskedString.length) {
                maskedString.append(m)
                continue
            }

            try {
                maskedString.append(unmaskedString[i])
            } catch (e: Exception) {
                break
            }

            i++
        }

        isUpdating = true

        edit.replace(0, edit.length, maskedString)
    }
}

open class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(edit: Editable) {}
}