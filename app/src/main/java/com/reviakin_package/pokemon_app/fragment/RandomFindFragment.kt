package com.reviakin_package.pokemon_app.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.reviakin_package.pokemon_app.R
import com.reviakin_package.pokemon_app.app.App
import com.reviakin_package.pokemon_app.component.AppComponent
import com.reviakin_package.pokemon_app.fragment.callbacks.FragmentCallback
import com.reviakin_package.pokemon_app.helper.LoadingState
import com.reviakin_package.pokemon_app.mvvm.viewmodel.FindViewModel
import com.reviakin_package.pokemon_app.mvvm.viewmodel.RandomFindViewModel
import com.reviakin_package.pokemon_app.pojo.PokemonUnit
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.ClassCastException

class RandomFindFragment : Fragment(), View.OnClickListener {

    private lateinit var mPokemonIcon: CircleImageView
    private lateinit var mTvName: TextView
    private lateinit var mTvHeight: TextView
    private lateinit var mTvWeight: TextView
    private lateinit var mTvBaseExp: TextView
    private lateinit var mBtnSave: Button
    private lateinit var mPicasso: Picasso
    private lateinit var mProfileLayout: LinearLayout
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mBtnRefresh: ImageButton
    private lateinit var mBtnBack: Button

    private lateinit var mAppComponent: AppComponent

    private lateinit var mMainView: View

    private var mCurrentPokemon: PokemonUnit? = null

    private var mSavedPokemon = false

    private lateinit var mNavigationListener: FragmentCallback.OnNavigateFromRandomFindFragment

    companion object {
        fun newInstance() = RandomFindFragment()
    }

    private lateinit var viewModel: RandomFindViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMainView = inflater.inflate(R.layout.random_find_fragment, container, false)
        return mMainView
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mNavigationListener = context as FragmentCallback.OnNavigateFromRandomFindFragment
        }catch (e: ClassCastException){
            throw ClassCastException(activity.toString() + " must implement OnNavigateFromRandomFindFragment");
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFindData()
    }

    private fun init(){
        mPokemonIcon = mMainView.findViewById(R.id.iv_icon)
        mTvName = mMainView.findViewById(R.id.tv_name)
        mTvHeight = mMainView.findViewById(R.id.tv_height)
        mTvWeight = mMainView.findViewById(R.id.tv_weight)
        mTvBaseExp = mMainView.findViewById(R.id.tv_base_exp)
        mBtnSave = mMainView.findViewById(R.id.btn_save)
        mProfileLayout = mMainView.findViewById(R.id.profile_layout)
        mProgressBar = mMainView.findViewById(R.id.progress_bar)
        mBtnRefresh = mMainView.findViewById(R.id.btn_refresh)
        mBtnBack = mMainView.findViewById(R.id.btn_back)

        mAppComponent = (requireActivity().applicationContext as App).appComponent

        viewModel = mAppComponent
            .getRandomFindViewModel()

        mBtnBack.setOnClickListener(this)
        mBtnRefresh.setOnClickListener(this)
        mBtnSave.setOnClickListener(this)

        viewModel.dataExist.observe(this, checkExistPokemonListener)
        viewModel.dataFind.observe(this, findPokemonListener)
        viewModel.loadingState.observe(this, loadingStateListener)
        mPicasso = mAppComponent.getPicassoComponent().getPicasso()
    }

    private fun showData(pokemonUnit: PokemonUnit){
        mPicasso.load(pokemonUnit.sprites.frontDefault).into(mPokemonIcon)
        mTvName.text = pokemonUnit.name
        mTvHeight.text = pokemonUnit.height.toString()
        mTvWeight.text = pokemonUnit.weight.toString()
        mTvBaseExp.text = pokemonUnit.baseExperience.toString()
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.btn_refresh -> {
                    viewModel.fetchFindData()
                }
                R.id.btn_save -> {
                    if(mCurrentPokemon != null) {
                        when(mSavedPokemon){
                            true -> {
                                viewModel.deletePokemon(mCurrentPokemon!!.name)
                            }
                            false -> {
                                viewModel.savePokemon(mCurrentPokemon!!)
                            }
                        }
                    }
                }
                R.id.btn_back -> {
                    mNavigationListener.onRandomFindFragmentBackClick()
                }
            }
        }
    }

    private var checkExistPokemonListener = object : Observer<Boolean> {
        override fun onChanged(t: Boolean?) {
            if(t != null){
                when(t){
                    true -> {
                        mSavedPokemon = true
                        mBtnSave.text = requireActivity().resources.getString(R.string.saved)
                    }
                    false -> {
                        mSavedPokemon = false
                        mBtnSave.text = requireActivity().resources.getString(R.string.save)
                    }
                }
            }
        }

    }

    private var findPokemonListener = object : Observer<PokemonUnit> {
        override fun onChanged(t: PokemonUnit?) {
            if(t != null){
                mCurrentPokemon = t
                showData(t)
            }
        }
    }

    private var loadingStateListener = object : Observer<LoadingState> {
        override fun onChanged(t: LoadingState?) {
            when(t?.status){
                LoadingState.Status.RUNNING -> {
                    mProfileLayout.visibility = View.GONE
                    mProgressBar.visibility = View.VISIBLE
                }
                LoadingState.Status.SUCCESS -> {
                    mProfileLayout.visibility = View.VISIBLE
                    mProgressBar.visibility = View.GONE
                }
                LoadingState.Status.FAILED -> {
                    mProfileLayout.visibility = View.GONE
                    mProgressBar.visibility = View.GONE
                }
            }
        }
    }
}