package com.project.konkor.util

import com.project.konkor.R
import com.project.konkor.model.CategoryModel
import com.project.konkor.model.LessonModel
import com.project.konkor.model.YearModel
import com.project.konkor.util.Constants.ENSANI
import com.project.konkor.util.Constants.HONAR
import com.project.konkor.util.Constants.RIAZI
import com.project.konkor.util.Constants.TAJROBI
import com.project.konkor.util.Constants.ZABAN

object DataGenerator {


    fun getYearsList(): List<YearModel> {
        return mutableListOf(
            YearModel("1", 1391, isNewSystem = false, isAbroad = false),
            YearModel("2", 1392, isNewSystem = false, isAbroad = false),
            YearModel("3", 1393, isNewSystem = false, isAbroad = false),
            YearModel("4", 1394, isNewSystem = false, isAbroad = false),
            YearModel("5", 1395, isNewSystem = false, isAbroad = false),
            YearModel("6", 1396, isNewSystem = false, isAbroad = false),
            YearModel("7", 1397, isNewSystem = false, isAbroad = false),
            YearModel("8", 1398, isNewSystem = false, isAbroad = false),
            YearModel("9", 1398, isNewSystem = true, isAbroad = false),
            YearModel("10", 1399, isNewSystem = false, isAbroad = false),
            YearModel("11", 1399, isNewSystem = true, isAbroad = false)
        )
    }

    fun getCategoryList(): List<CategoryModel> {
        return mutableListOf(
            CategoryModel(1, RIAZI, "ریاضی", R.drawable.ic_think),
            CategoryModel(2, TAJROBI, "تجربی", R.drawable.ic_chemistry),
            CategoryModel(3, HONAR, "هنر", R.drawable.ic_paint_palette),
            CategoryModel(4, ZABAN, "زبان", R.drawable.ic_languages),
            CategoryModel(5, ENSANI, "انسانی", R.drawable.ic_nurturing)
        )
    }

    fun getRiaziLessen(): List<LessonModel> {
        return mutableListOf(
            LessonModel("1", "دروس عمومی", "", 100, 75),
            LessonModel("2", "ادبیات", "adabiat", 25, 18),
            LessonModel("3", "دینی", "religion", 25, 17),
            LessonModel("4", "عربی", "arabic", 25, 20),
            LessonModel("5", "زبان", "language", 25, 20),
            LessonModel("6", "دروس اختصاصی", "", 135, 175),
            LessonModel("7", "ریاضیات", "math", 55, 85),
            LessonModel("8", "فیزیک", "physics", 45, 55),
            LessonModel("9", "شیمی", "chemistry", 35, 35)
        )
    }

    fun getTajrobiLessen(): List<LessonModel> {
        return mutableListOf(
            LessonModel("10", "دروس عمومی", "", 100, 75),
            LessonModel("11", "ادبیات", "adabiat", 25, 18),
            LessonModel("12", "دینی", "religion", 25, 17),
            LessonModel("13", "عربی", "arabic", 25, 20),
            LessonModel("14", "زبان", "language", 25, 20),
            LessonModel("15", "دروس اختصاصی", "", 170, 175),
            LessonModel("16", "ریاضیات", "math", 30, 47),
            LessonModel("17", "فیزیک", "physics", 30, 37),
            LessonModel("18", "شیمی", "chemistry", 35, 35),
            LessonModel("19", "زمین شناسی", "geology", 25, 20),
            LessonModel("20", "زیست", "biology", 50, 36)
        )

    }

    fun getEnsaniLessen(): List<LessonModel> {
        return mutableListOf(
            LessonModel("21", "دروس عمومی", "", 100, 75),
            LessonModel("22", "ادبیات", "adabiat", 25, 18),
            LessonModel("23", "دینی", "religion", 25, 17),
            LessonModel("24", "عربی", "arabic", 25, 20),
            LessonModel("25", "زبان", "language", 25, 20),
            LessonModel("26", "دروس اختصاصی", "", 180, 165),
            LessonModel("27", "ریاضیات", "math", 20, 25),
            LessonModel("28", "اقتصاد", "economy", 15, 10),
            LessonModel("29", "ادبیات تخصصی", "specializedLiterature", 30, 30),
            LessonModel("30", "عربی تخصصی", "specializedArabic", 20, 20),
            LessonModel("31", "تاریخ", "history", 15, 13),
            LessonModel("32", "جغرافیا", "geography", 15, 12),
            LessonModel("33", "علوم اجتماعی", "socialSciences", 20, 15),
            LessonModel("34", "فلسفه", "philosophy", 16, 16),
            LessonModel("35", "منطق", "logic", 9, 9),
            LessonModel("36", "روانشناسی", "psychology", 20, 15)
        )
    }

    fun getZabanLessen(): List<LessonModel> {
        return mutableListOf(
            LessonModel("37", "دروس عمومی", "", 100, 75),
            LessonModel("38", "ادبیات", "adabiat", 25, 18),
            LessonModel("39", "دینی", "religion", 25, 17),
            LessonModel("40", "عربی", "arabic", 25, 20),
            LessonModel("41", "زبان", "language", 25, 20),
            LessonModel("42", "دروس اختصاصی", "", 70, 105),
            LessonModel("43", "واژگان", "vocabulary", 15, 23),
            LessonModel("44", "ریدینگ", "reading", 20, 30),
            LessonModel("45", "گرامر", "grammar", 10, 15),
            LessonModel("46", "کاربرد زبانی", "languageFunctions", 10, 15),
            LessonModel("47", "کلوز تست", "clozeTest", 10, 15),
            LessonModel("48", "ترتیب واژه ها", "orderOfWords", 5, 8)
        )
    }

    fun getHonarLessen(): List<LessonModel> {
        return mutableListOf(
            LessonModel("49", "دروس عمومی", "", 100, 75),
            LessonModel("50", "ادبیات", "adabiat", 25, 18),
            LessonModel("51", "دینی", "religion", 25, 17),
            LessonModel("52", "عربی", "arabic", 25, 20),
            LessonModel("53", "زبان", "language", 25, 20),
            LessonModel("54", "دروس اختصاصی", "", 160, 165),
            LessonModel("55", "درک عمومی هنر", "darkOmomiHonar", 30, 25),
            LessonModel("56", "ترسیم فنی", "tarsimFani", 20, 26),
            LessonModel("57", "خلاقیت تصویری و تجسمی", "khalaghiatTasviryVaTajasomi", 20, 24),
            LessonModel("58", "خلاقیت نمایشی", "khalaghiatNamayeshi", 20, 13),
            LessonModel("50", "خلاقیت موسیقی", "khalaghiatMosighi", 20, 20),
            LessonModel("60", "خواص مواد", "khavasMavad", 20, 17),
            LessonModel("61", "درک عمومی ریاضی و فیزیک", "darkOmomiRiaziVaPhysics", 30, 40)
        )
    }



}