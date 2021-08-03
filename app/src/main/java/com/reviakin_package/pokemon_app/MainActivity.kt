package com.reviakin_package.pokemon_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.reviakin_package.pokemon_app.fragment.callbacks.FragmentCallback

class MainActivity : AppCompatActivity(), FragmentCallback.OnNavigateFromFindFragment,
    FragmentCallback.OnNavigateFromRandomFindFragment,
    FragmentCallback.OnNavigateFromSavedFragment {

    private lateinit var mController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onRandomFragmentNavigate() {
        mController.navigate(R.id.randomFindFragment)
    }

    override fun onSavedFragmentNavigate() {
        mController.navigate(R.id.savedFragment)
    }

    override fun onRandomFindFragmentBackClick() {
        mController.popBackStack()
    }

    override fun onSavedFragmentBackClick() {
        mController.popBackStack()
    }
}