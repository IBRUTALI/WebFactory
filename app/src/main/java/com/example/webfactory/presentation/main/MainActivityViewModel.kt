package com.example.webfactory.presentation.main

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.main.OptionsItemUseCase

class MainActivityViewModel(
    private val optionsItemUseCase: OptionsItemUseCase
): ViewModel() {

 fun out(){
    optionsItemUseCase.execute()
 }

}