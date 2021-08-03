package com.reviakin_package.pokemon_app.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.reviakin_package.pokemon_app.mvvm.viewmodel.FindViewModel
import com.reviakin_package.pokemon_app.R
import com.reviakin_package.pokemon_app.helper.LoadingState
import com.reviakin_package.pokemon_app.app.App
import com.reviakin_package.pokemon_app.component.AppComponent
import com.reviakin_package.pokemon_app.database.entity.PokemonEntity
import com.reviakin_package.pokemon_app.fragment.callbacks.FragmentCallback
import com.reviakin_package.pokemon_app.pojo.PokemonUnit
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.ClassCastException

class FindFragment : Fragment(), View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mEditTextFind: EditText
    private lateinit var mBtnFind: ImageButton
    private lateinit var mPokemonIcon: CircleImageView
    private lateinit var mTvName: TextView
    private lateinit var mTvHeight: TextView
    private lateinit var mTvWeight: TextView
    private lateinit var mTvBaseExp: TextView
    private lateinit var mBtnSave: Button
    private lateinit var mPicasso: Picasso
    private lateinit var mProfileLayout: LinearLayout
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mBottomNavigationView: BottomNavigationView

    private lateinit var mAppComponent: AppComponent

    private lateinit var mMainView: View

    private var mCurrentPokemon: PokemonEntity? = null

    private var mSavedPokemon = false

    private lateinit var mNavigationListener: FragmentCallback.OnNavigateFromFindFragment

    companion object {
        fun newInstance() = FindFragment()
    }

    private lateinit var viewModel: FindViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMainView = inflater.inflate(R.layout.find_fragment, container, false)
        return mMainView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mNavigationListener = context as FragmentCallback.OnNavigateFromFindFragment
        }catch (e: ClassCastException){
            throw ClassCastException(activity.toString() + " must implement OnNavigateFromFindFragment");
        }
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init(){
        mEditTextFind = mMainView.findViewById(R.id.editTextTextPokemonName)
        mBtnFind = mMainView.findViewById(R.id.btn_find)
        mPokemonIcon = mMainView.findViewById(R.id.iv_icon)
        mTvName = mMainView.findViewById(R.id.tv_name)
        mTvHeight = mMainView.findViewById(R.id.tv_height)
        mTvWeight = mMainView.findViewById(R.id.tv_weight)
        mTvBaseExp = mMainView.findViewById(R.id.tv_base_exp)
        mBtnSave = mMainView.findViewById(R.id.btn_save)
        mProfileLayout = mMainView.findViewById(R.id.profile_layout)
        mProgressBar = mMainView.findViewById(R.id.progress_bar)
        mBottomNavigationView = mMainView.findViewById(R.id.bottom_navigation)

        mBottomNavigationView.setOnNavigationItemSelectedListener(this)

        mBtnFind.setOnClickListener(this)
        mBtnSave.setOnClickListener(this)

        mAppComponent = (requireActivity().applicationContext as App).appComponent

        viewModel = mAppComponent
            .getFindViewModel()

        viewModel.dataExist.observe(this, checkExistPokemonListener)
        viewModel.dataLastFind.observe(this, findPokemonListener)
        viewModel.loadingState.observe(this, loadingStateListener)
        mPicasso = mAppComponent.getPicassoComponent().getPicasso()
    }

    private fun showData(pokemonEntity: PokemonEntity){
        mPicasso.load(pokemonEntity.icon).into(mPokemonIcon)
        mTvName.text = pokemonEntity.name
        mTvHeight.text = pokemonEntity.height.toString()
        mTvWeight.text = pokemonEntity.weight.toString()
        mTvBaseExp.text = pokemonEntity.baseExperience.toString()
    }


    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.btn_find -> {
                    if(!mEditTextFind.text.toString().trim().equals("")){
                        viewModel.fetchFindData(mEditTextFind.text.toString())
                    }
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
            }
        }
    }


    private var checkExistPokemonListener = object : Observer<Boolean>{
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

    private var findPokemonListener = object : Observer<PokemonEntity>{
        override fun onChanged(t: PokemonEntity?) {
            if(t != null){
                mCurrentPokemon = t
                showData(t)
                mProfileLayout.visibility = View.VISIBLE
            }
        }
    }

    private var loadingStateListener = object : Observer<LoadingState>{
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

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.action_random -> {
                mNavigationListener.onRandomFragmentNavigate()
            }
            R.id.action_favorites -> {
                mNavigationListener.onSavedFragmentNavigate()
            }
        }
        return false
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.cleanCache()
    }
}