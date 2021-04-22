package com.medical.reconscious.utils

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.format.DateFormat
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.medical.reconscious.R
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

@SuppressLint("SimpleDateFormat")
fun timeFormart(time: String) : String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val formatter = SimpleDateFormat("dd.mm.yyyy HH:mm")
    val output: String = formatter.format(parser.parse(time))

    return output
}

//-----------date-----------

fun getTypeFromDate(str_date: String, targetFormart: String): String {
    var string = ""
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("KST")
        string = DateFormat.format(targetFormart, sdf.parse(str_date)) as String
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return string
}

fun setStatusEditBackground(layout: ConstraintLayout, imageView: ImageView, isValid: Boolean) {
    if (isValid) {
        layout.setBackgroundResource(R.drawable.layout_bg_edittext)
        imageView.visibility = View.GONE
    } else {
        layout.setBackgroundResource(R.drawable.layout_bg_edittext_error)
        imageView.visibility = View.VISIBLE
    }
}

fun openSoftKeyboard(context: Context, view: View) {
    view.requestFocus()
    // open the soft keyboard
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun getJournalAvater(feeling: String) : Int {

    var avater = R.drawable.icon_feel_amazing

    when (feeling) {
        "amazing" -> avater = R.drawable.icon_feel_amazing
        "good" -> avater = R.drawable.icon_feel_good
        "content" -> avater = R.drawable.icon_feel_content
        "so_so" -> avater = R.drawable.icon_feel_soso
        "bad" -> avater = R.drawable.icon_feel_bad
        "awful" -> avater = R.drawable.icon_feel_awful
        else -> {
            print("x is neither 1 nor 2")
        }
    }

    return avater
}

fun getCardType(type: String) : Int {
    var card = R.drawable.ic_card_visa

    when (type) {
        "VISA" -> card = R.drawable.ic_card_visa
        "MASTERCARD" -> card = R.drawable.ic_card_mastercard
        "JCB" -> card = R.drawable.ic_card_jcb
        "DISCOVER" -> card = R.drawable.ic_card_discover
        "MAESTRO" -> card = R.drawable.ic_card_maestro
        "DINERS_CLUB" -> card = R.drawable.ic_card_diners
        "AMERICAN_EXPRESS" -> card = R.drawable.ic_card_amex
        else -> {
            print("x is neither 1 nor 2")
        }
    }

    return card
}

fun getLastFourNumberInCard(number: String) : String {
    return number.substring(number.length - 4, number.length)
}

fun showError(context: Context, jsoneString: String) {

    try {
        val jsonObject = JSONObject(jsoneString)
        val keys = jsonObject.keys()

        while (keys.hasNext()) {
            val key = keys.next()
            var errorString = jsonObject.getString(key)

            val re = Regex("[^A-Za-z0-9 ]")
            errorString = re.replace(errorString, "")
//            for (i in 0 until errorAry.length()) {
//                val item = errorAry.getString(i)
//
                Toast.makeText(context, errorString, Toast.LENGTH_LONG).show()
//            }
        }
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }
}

fun heightAnimation(view: ConstraintLayout, from: Int, to: Int, duration: Long) {
    val anim = ValueAnimator.ofInt(from, to)
    anim.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        val layoutParams: ViewGroup.LayoutParams = view.getLayoutParams()
        layoutParams.height = value
        view.setLayoutParams(layoutParams)
    }
    anim.duration = duration
    anim.start()
}

//-----------------string------------------

fun getUpercaseString(string: String): String {
    val s1 = string.substring(0, 1).toUpperCase(Locale.ROOT)
    return s1 + string.substring(1)
}

fun filterStringWithout_(string: String): String {
    return string.replace("_".toRegex(), " ")
}

fun filterStringWithoutSpace(string: String): String {
    return string.replace(" ".toRegex(), "_")
}