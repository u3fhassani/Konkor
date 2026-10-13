package com.project.konkor.util

import java.util.Calendar
import java.util.TimeZone

class PersianCalendar(calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"))) {

    val year: Int
    val month: Int
    val day: Int
    val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
    val minute: Int = calendar.get(Calendar.MINUTE)
    val dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)

    init {
        val gYear = calendar.get(Calendar.YEAR)
        val gMonth = calendar.get(Calendar.MONTH) + 1
        val gDay = calendar.get(Calendar.DAY_OF_MONTH)

        val jalali = gregorianToJalali(gYear, gMonth, gDay)
        year = jalali[0]
        month = jalali[1]
        day = jalali[2]
    }

    val persianWeekDayName: String
        get() = when (dayOfWeek) {
            Calendar.SATURDAY -> "شنبه"
            Calendar.SUNDAY -> "یکشنبه"
            Calendar.MONDAY -> "دوشنبه"
            Calendar.TUESDAY -> "سه‌شنبه"
            Calendar.WEDNESDAY -> "چهارشنبه"
            Calendar.THURSDAY -> "پنج‌شنبه"
            Calendar.FRIDAY -> "جمعه"
            else -> ""
        }

    val persianShortDate: String
        get() = String.format("%04d/%02d/%02d", year, month, day)

    val persianShortDateTime: String
        get() = String.format("%04d/%02d/%02d %02d:%02d", year, month, day, hour, minute)

    private fun gregorianToJalali(gy: Int, gm: Int, gd: Int): IntArray {
        val gDaysInMonth = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val gy2 = if (gm > 2) gy + 1 else gy
        var gDayNo = 365 * gy + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) + ((gy2 + 399) / 400) - 80 + gd + gDaysInMonth[gm - 1]
        var jy = -1595 + 33 * (gDayNo / 12053)
        gDayNo %= 12053
        jy += 4 * (gDayNo / 1461)
        gDayNo %= 1461
        if (gDayNo > 365) {
            jy += ((gDayNo - 1) / 365)
            gDayNo = (gDayNo - 1) % 365
        }
        val jm: Int
        val jd: Int
        if (gDayNo < 186) {
            jm = 1 + (gDayNo / 31)
            jd = 1 + (gDayNo % 31)
        } else {
            jm = 7 + ((gDayNo - 186) / 30)
            jd = 1 + ((gDayNo - 186) % 30)
        }
        return intArrayOf(jy, jm, jd)
    }
}
