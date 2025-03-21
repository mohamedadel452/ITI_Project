package com.example.iti_project.ui.actitvity1.mainActitvity.loginFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.models.UiState
import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var etEmail: TextInputLayout
    private lateinit var etPassword: TextInputLayout
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView
    val loginViewModel:LoginViewModel by viewModels(){
        LoginViewModelFactory(UserRepoImp(LocalDataSourceImp(requireContext())
        ))

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        etEmail = view.findViewById(R.id.et_email)
        emailEditText = view.findViewById(R.id.sign_in_email)
        etPassword = view.findViewById(R.id.et_password)
        passwordEditText = view.findViewById(R.id.sign_in_password)
        loginButton = view.findViewById(R.id.sign_in_login_btn)
        signUpTextView = view.findViewById(R.id.tv_signUp)


        signUpTextView.setOnClickListener {
            emailEditText.text.clear()
            passwordEditText.text.clear()
            findNavController().navigate(R.id.registerFragment)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.Userlogin(email, password)
            } else {
                Toast.makeText(requireContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        loginViewModel.LoginState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                }
                is UiState.Success -> {

                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                    val action = LoginFragmentDirections.actionLoginFragmentToRecipeActivity()
                    findNavController().navigate(action,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true) // Clear back stack up to and including LoginFragment
                            .build()
                    )
                    activity?.finish()


                }

                is UiState.Error -> {

                    Toast.makeText(requireContext(), uiState.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}
