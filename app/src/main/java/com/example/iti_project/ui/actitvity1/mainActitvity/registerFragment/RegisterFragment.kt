package com.example.iti_project.ui.actitvity1.mainActitvity.registerFragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.RoomDatabase.RoomDataBaseImp
import com.example.iti_project.data.repo.UserRepo.UserRepoImp
import com.example.iti_project.ui.actitvity1.mainActitvity.signupSuccessful.CongratsFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class RegisterFragment : Fragment() {

    private lateinit var btn_create_account: Button
    private lateinit var et_user_name: TextInputLayout
    private lateinit var et_email: TextInputLayout
    private lateinit var et_password: TextInputLayout
    private lateinit var et_confirm_password: TextInputLayout
    private lateinit var tv_equality_password_error: TextView
    private lateinit var tv_password_error: TextView
    private lateinit var tv_email_error: TextView
    private lateinit var cb_acceptance: CheckBox
    private var fcv_welcome : FragmentContainerView? = null
    val registerFragmentViewModel: RegisterViewModel by viewModels {
         RegisterViewModelFactory(UserRepoImp(LocalDataSourceImp(RoomDataBaseImp.getInstance(requireContext()),null)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_create_account = view.findViewById(R.id.btn_create_account)
        et_user_name = view.findViewById(R.id.et_user_name)
        et_email = view.findViewById(R.id.et_email)
        et_password = view.findViewById(R.id.et_password)
        tv_password_error = view.findViewById(R.id.tx_password_error)
        tv_email_error = view.findViewById(R.id.tx_email_error)
        et_confirm_password = view.findViewById(R.id.et_confirm_password)
        tv_equality_password_error = view.findViewById(R.id.tx_password_error_equality)
        cb_acceptance = view.findViewById(R.id.cb_acceptance)
        cb_acceptance.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onClickListener()
            } else {
                btn_create_account.setBackgroundColor(Color.parseColor("#858585"))

            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.P)

    fun onClickListener() {
        btn_create_account.setBackgroundColor(Color.parseColor("#ed6e3a"))
        btn_create_account.setOnClickListener {
            val password = et_password.editText?.text.toString() ?: ""
            val isValid = registerFragmentViewModel.checkedPassword(password)
            if (isValid) {
//                Log.i("TAG", "onClickListner:")
                tv_password_error.hint = ""
                tv_password_error.visibility = View.GONE
                val confirmPassword = et_confirm_password.editText?.text.toString() ?: ""
                if (confirmPassword == password) {
                    tv_equality_password_error.hint = ""
                    tv_equality_password_error.visibility = View.GONE
                    val email = et_email.editText?.text.toString() ?: ""
                    val isEmailUnique = registerFragmentViewModel.checkedEmail(email)
                    if (isEmailUnique) {
                        tv_email_error.hint = ""
                        tv_email_error.visibility = View.GONE
                        val userName = et_user_name.editText?.text.toString() ?: ""
                        registerFragmentViewModel.insertData(userName, email, password)
                        registerFragmentViewModel.isSuccess.observe(viewLifecycleOwner) {
                            if (it) {
//                                findNavController().navigate(R.id.loginFragment)
                                fcv_welcome = view?.findViewById(R.id.fcv_welcome)
                                fcv_welcome?.visibility = View.VISIBLE
                            } else {
                                Snackbar.make(
                                    btn_create_account,
                                    getString(R.string.error_message_email),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        showErrorMessage(et_email , tv_email_error )
                        tv_email_error.text = getString(R.string.error_message_email)
                    }
                }else{
                    showErrorMessage( et_confirm_password , tv_equality_password_error)
                    showErrorMessage( et_password , null)
                    tv_equality_password_error.text = getString(R.string.error_message_password_equality)
                }
            } else {
                showErrorMessage( et_password,tv_password_error)
                tv_password_error.text = getString(R.string.error_message)
                Log.i("TAG", "nonoo")
            }
        }
    }

    private fun showErrorMessage( et: TextInputLayout , tvError: TextView?) {
        et.editText?.setHintTextColor(Color.RED)
        et.boxStrokeColor = Color.RED
        if (tvError !=null) {
            tvError.visibility = View.VISIBLE
            tvError.setTextColor(Color.RED)
        }

    }


}