# 🎓 Konkor (اپلیکیشن جامع آزمون‌های کنکور)

<div align="center">

![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![Language](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![UI](https://img.shields.io/badge/UI-XML%20Layouts-informational.svg)
![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Clean-orange.svg)
![Min SDK](https://img.shields.io/badge/Min%20SDK-23-lightgrey.svg)
![Target SDK](https://img.shields.io/badge/Target%20SDK-34-brightgreen.svg)
![Gradle](https://img.shields.io/badge/Gradle-9.5.1-blueviolet.svg)
![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)

**یک اپلیکیشن اندروید مدرن، ماژولار و بهینه‌سازی‌شده برای برگزاری آزمون‌های شبیه‌ساز کنکور سراسری، تحلیل کارنامه و رندر فرمول‌های ریاضی.**

*A modern, modular Android application designed for national exam simulations, performance analysis, and LaTeX formula rendering.*

---

</div>

## 📌 ویژگی‌های برجسته (Key Features)

* ⏱️ **شبیه‌ساز آزمون کنکور با زمان‌سنج اختصاصی:** برگزاری آزمون آنلاین و آفلاین همراه با شمارشگر معکوس و اعلام پایان زمان آزمون.
* 📊 **محاسبه دقیق درصد بر اساس فرمول نمره منفی کنکور:** فرمول استاندارد سازمان سنجش `(۳R - W) / (۳ × Total) * 100` با اعشار دقیق و نمایش تعداد صحیح، غلط و نزده.
* 🧮 **پشتیبانی از فرمول‌های ریاضی و فیزیک (LaTeX):** رندر بی‌درنگ نمادها و فرمول‌های پیچیده ریاضیاتی با استفاده از کتابخانه موتور نمایش ریاضی `AndroidMath`.
* 🌓 **حالت روز و شب پویا (Dynamic Day/Night Mode):** پشتیبانی کامل از دارک‌مود همراه با تصاویر سازگار با پس‌زمینه تیره.
* 🔤 **پشتیبانی از فونت‌های متنوع فارسی (Free & Open Source):** انتخاب فونت‌های بادر، نازنین، میترا و مروارید با پیاده‌سازی کشینگ همزمان و بدون مسدودسازی ترد اصلی.
* 💾 **معماری آفلاین‌محور (Offline-first با Room):** ذخیره پیشینه نتایج آزمون‌ها، نشان‌گذاری سوالات علاقه‌مندی و کش هوشمند داده‌ها.
* 🔐 **احراز هویت و تمدید خودکار توکن (OAuth JWT / Refresh Token):** مدیریت دسترسی‌ها و اتصال پایدار با استفاده از `OkHttp Authenticator`.

---

## 🏗️ معماری نرم‌افزار (Architecture & Tech Stack)

این پروژه بر اساس اصول مهندسی نرم‌افزار و معماری پیشنهادی گوگل برای اندروید (**MVVM + Clean Architecture principles**) طراحی شده است:

```
app/
 ├── adapter/        # آداپتورهای RecyclerView برای سوالات و کلید پاسخ
 ├── di/             # ماژول‌های تزریق وابستگی (Hilt Dependency Injection)
 ├── model/          # موجودیت‌های لایه دامنه (Domain Models)
 ├── network/        # لایه شبکه (Retrofit, OkHttp Authenticator, DTOs, Mappers)
 ├── repository/     # لایه مخزن داده (Repository Pattern)
 ├── room/           # پایگاه‌داده محلی (Room Database, DAOs, Entities, Mappers)
 ├── sharedPref/     # تنظیمات و توکن‌های نشست کاربر
 ├── ui/
 │    ├── activity/  # اکتیویتی‌های اصلی و ناوبری
 │    ├── dialog/    # دیالوگ‌های بازگشت، پایان آزمون و خروج
 │    └── fragment/  # فرگمنت‌های دسته‌بندی، آزمون، کارنامه و تنظیمات
 ├── util/           # توابع کمکی، فرمول‌ها، فونت‌ها و وضعیت داده‌ها (DataState)
 └── viewmodel/      # مدیریت وضعیت ویوها (AndroidX ViewModels)
```

### 🛠️ ابزارها و کتابخانه‌ها (Libraries & Tools)

* **Language:** [Kotlin](https://kotlinlang.org/)
* **Dependency Injection:** [Hilt (Dagger)](https://dagger.dev/hilt/)
* **Local Database:** [Room Database](https://developer.android.com/training/data-storage/room) با پشتیبانی از Coroutines و Flow
* **Networking:** [Retrofit 2](https://square.github.io/retrofit/) + [Gson](https://github.com/google/gson) + [OkHttp Logging Interceptor](https://square.github.io/okhttp/)
* **Asynchronous Programming:** Kotlin Coroutines & Flow (StateFlow / FlowOn Dispatchers)
* **UI & Layouts:** XML Layouts (ConstraintLayout, ViewPager2, RecyclerView) + Material Design 3
* **View Binding:** برای ارتباط امن و بهینه با لایه‌های XML بدون نشت حافظه
* **Navigation:** [Jetpack Navigation Component](https://developer.android.com/guide/navigation) با قابلیت SafeArgs
* **Push Notifications & Analytics:** Firebase Messaging & Firebase Analytics
* **Math Rendering:** [AndroidMath](https://github.com/gregcockroft/AndroidMath) (LaTeX display engine)
* **Animation:** [Lottie](https://airbnb.design/lottie/)

---

## 🚀 نحوه راه‌اندازی و اجرا (Getting Started)

### پیش‌نیازها
* **Android Studio:** Hedgehog / Iguana / Jellyfish یا بالاتر
* **JDK:** Java 17 یا بالاتر
* **Gradle:** 9.5.1 (به همراه Gradle Wrapper تنظیم‌شده)

### مراحل بیلد
۱. ریپازیتوری را کلون کنید:
```bash
git clone https://github.com/your-username/konkor-android.git
cd konkor-android
```

۲. تنظیم فایربیس (Firebase):
* فایل نمونه `app/google-services.json.example` در پروژه قرار دارد. برای اجرای پروژه، می‌توانید یک کپی از آن را با نام `google-services.json` در دایرکتوری `app/` ذخیره کنید یا فایل اختصاصی پروژه فایربیس خود را در آن قرار دهید (فایل اصلی `google-services.json` به دلایل امنیتی در `.gitignore` نادیده گرفته می‌شود).

۳. پروژه را در اندروید استودیو باز کرده و اجازه دهید وابستگی‌ها Sync شوند:
```bash
./gradlew build
```

۴. اجرای اپلیکیشن:
```bash
./gradlew installDebug
```

---

## 🔒 امنیت و انتشار (Security & Signing)

برای تولید خروجی ریلیز (`release APK / AAB`):
1. فایل `keystore.properties.example` را به `keystore.properties` تغییر نام دهید.
2. مسیر فایل `.jks` و رمزهای عبور کلید را در آن وارد کنید.
3. فایل `keystore.properties` در `.gitignore` قرار داده شده و هرگز در گیت ردیابی نمی‌شود.

---

## 📄 مجوز (License)

این پروژه تحت مجوز [MIT License](LICENSE) منتشر شده است.
