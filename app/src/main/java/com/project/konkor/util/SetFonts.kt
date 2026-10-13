package com.project.konkor.util

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.project.konkor.R
import com.project.konkor.di.IoDispatcher
import com.project.konkor.di.MainDispatcher
import com.project.konkor.sharedPref.SharedPreferencesSetting
import com.project.konkor.util.Constants.FONT_BADR
import com.project.konkor.util.Constants.FONT_B_NAZANIN
import com.project.konkor.util.Constants.FONT_MITRA
import com.project.konkor.util.Constants.FONT_MORVARID
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class SetFonts
@Inject
constructor(
    @ApplicationContext private val context: Context,
    private val sharedPrefSetting: SharedPreferencesSetting,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private val typefaceCache = ConcurrentHashMap<Int, Typeface>()

    fun getFontTypeface(): Typeface {
        val mode = sharedPrefSetting.fontMode
        return typefaceCache.getOrPut(mode) {
            val fontRes = when (mode) {
                FONT_BADR -> R.font.b_badr
                FONT_B_NAZANIN -> R.font.b_nazanin
                FONT_MITRA -> R.font.far_mitra
                FONT_MORVARID -> R.font.far_morvarid
                else -> R.font.b_badr
            }
            ResourcesCompat.getFont(context, fontRes) ?: Typeface.DEFAULT
        }
    }

    fun setFontOnTextView(textView: TextView) {
        val tf = getFontTypeface()
        if (textView.typeface != tf) {
            textView.typeface = tf
        }
    }

    fun setFontOnView(view: View) {
        val tf = getFontTypeface()
        applyTypeface(view, tf)
    }

    private fun applyTypeface(view: View, tf: Typeface) {
        when (view) {
            is TextView -> if (view.typeface != tf) view.typeface = tf
            is Button -> if (view.typeface != tf) view.typeface = tf
            is TextInputEditText -> if (view.typeface != tf) view.typeface = tf
            is TextInputLayout -> if (view.typeface != tf) view.typeface = tf
            is Toolbar -> setTypefaceOnToolbar(view, tf)
            is ViewGroup -> view.forEach { child -> applyTypeface(child, tf) }
        }
    }

    private fun setTypefaceOnToolbar(toolbar: Toolbar, tf: Typeface) {
        val menu = toolbar.menu
        for (index in 0 until menu.size()) {
            val menuItem = menu.getItem(index)
            val title = menuItem.title ?: continue
            val s = SpannableString(title)
            s.setSpan(
                CustomTypefaceSpan(context, "", tf), 0, s.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            menuItem.title = s
        }
    }

}