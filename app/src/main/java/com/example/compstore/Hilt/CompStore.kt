package com.example.compstore.Hilt


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp //Обозначает что приложение будет использовать hilt для внедрения зависимостей
class CompStore : Application()