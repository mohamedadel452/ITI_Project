package com.example.iti_project.ui.actitvity1.mainActitvity.splashFragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.iti_project.R
import com.example.iti_project.data.DataSource.LocalDataSource.InterFace.LocalDataSourceImp
import com.example.iti_project.data.DataSource.LocalDataSource.LocalData.SharedPrefrence.SharedPreferenceImp
import com.example.iti_project.data.repo.UserRepo.UserRepoImp


class SplashFragment : Fragment() {
    private val splashViewModel: SplashViewModel by viewModels {
        SplashViewModelFactory(UserRepoImp(LocalDataSourceImp(requireContext())))
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
            if (isAdded) {
                splashViewModel.checkLoginStatus { isLoggedIn ->
                    if (isLoggedIn) {
                        findNavController().navigate(R.id.recipeActivity, null,
                            NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build())

                    } else {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment, null,
                            NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build())

                    }
                }
            }
        }, 5000) //  5 secs
    }


}
