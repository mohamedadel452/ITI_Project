package com.example.iti_project.ui.actitvity1.mainActitvity.splashFragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp


class SplashFragment : Fragment() {
    private val splashViewModel: SplashViewModel by viewModels {
        SplashViewModelFactory(SharedPreferenceImp.getInstance(requireContext()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lottieAnimationView: LottieAnimationView = view.findViewById(R.id.lottieAnimationView)


        lottieAnimationView.setAnimation(R.raw.animee)

        lottieAnimationView.playAnimation()


        Handler(Looper.getMainLooper()).postDelayed({
            splashViewModel.checkLoginStatus { isLoggedIn ->
                if (isLoggedIn) {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

                }

            }
        }, 5000) //  5 secs
    }


}