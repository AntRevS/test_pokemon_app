package com.reviakin_package.pokemon_app.fragment.callbacks

class FragmentCallback {

    interface OnNavigateFromFindFragment{
        fun onRandomFragmentNavigate()
        fun onSavedFragmentNavigate()
    }

    interface OnNavigateFromRandomFindFragment{
        fun onRandomFindFragmentBackClick()
    }

    interface OnNavigateFromSavedFragment{
        fun onSavedFragmentBackClick()
    }
}