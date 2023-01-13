package com.example.webfactory.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.domain.models.User
import com.example.webfactory.R
import com.example.webfactory.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

        isUserAuth()

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

    private fun isUserAuth() {
        if (viewModel.isUserAuthenticated) {
            navController.navigate(R.id.action_loginFragment_to_nav_home)
        }
    }

    private fun login(user: User, password: String) {
        viewModel.login(user, password).asLiveData().observe(viewLifecycleOwner, {
            if (it == true) {
                navController.navigate(R.id.action_loginFragment_to_nav_home)
            }
            })
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


