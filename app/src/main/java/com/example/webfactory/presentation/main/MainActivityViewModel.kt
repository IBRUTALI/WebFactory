package com.example.webfactory.presentation.main

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.main.SignOutUseCase

class MainActivityViewModel(
    private val optionsItemUseCase: SignOutUseCase
): ViewModel() {

 fun out(){
    optionsItemUseCase.execute()
 }

}