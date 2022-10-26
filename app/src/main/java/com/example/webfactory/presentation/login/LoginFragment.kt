package com.example.webfactory.presentation.login

import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.webfactory.R
import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.models.User
import com.example.webfactory.databinding.FragmentLoginBinding
import io.reactivex.rxjava3.core.Single

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private lateinit var viewModel: LoginFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)
        navController = findNavController(view)

        viewModel = ViewModelProvider(
            this,
            LoginFragmentViewModelFactory(requireContext())
        )
            .get(LoginFragmentViewModel::class.java)

        binding.login.setOnClickListener {
            login(
                User(email = binding.username.text.toString()),
                password = binding.password.text.toString()
            )
        }
        binding.register.setOnClickListener {
            register(
                User(email = binding.username.text.toString()),
                password = binding.password.text.toString()
            )
        }

    }

    private fun login(user: User, password: String) {
        viewModel.login(user, password)
        if(viewModel.userAuthState()){
            Toast.makeText(context, "Авторизация прошла успешно!", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_loginFragment_to_nav_home)
        } else {
            Toast.makeText(context, "Авторизация провалена!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun register(user: User, password: String) {
        if (viewModel.register(user, password)) {
            Toast.makeText(context, "Регистрация прошла успешно!", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, "Регистрация провалена!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


