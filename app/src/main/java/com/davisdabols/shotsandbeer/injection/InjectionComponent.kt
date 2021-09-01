package com.davisdabols.shotsandbeer.injection

//import com.davisdabols.shotsandbeer.ui.GameViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [InjectionModule::class])
interface InjectionComponent {

 //   fun inject(target: GameViewModel)

}
