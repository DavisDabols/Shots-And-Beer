package com.davisdabols.shotsandbeer

import android.app.Application
import com.davisdabols.shotsandbeer.common.LineNumberDebugThree
import com.davisdabols.shotsandbeer.injection.DaggerInjectionComponent
import com.davisdabols.shotsandbeer.injection.InjectionComponent
import com.davisdabols.shotsandbeer.injection.InjectionModule
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(LineNumberDebugThree())
        }
        Timber.d("App created")
        component = DaggerInjectionComponent
            .builder()
            .injectionModule(InjectionModule(this))
            .build()
    }

    companion object {
        lateinit var component: InjectionComponent
            private set
    }
}
