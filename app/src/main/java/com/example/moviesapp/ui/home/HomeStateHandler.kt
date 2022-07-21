package com.example.moviesapp.ui.home

import com.example.moviesapp.R
import javax.inject.Inject

class HomeStateHandler @Inject constructor() {
    private var framesList = listOf(
        R.drawable.puff_0,
        R.drawable.puff_1,
        R.drawable.puff_2,
        R.drawable.puff_3,
        R.drawable.puff_4,
        R.drawable.puff_5,
        R.drawable.puff_6,
        R.drawable.puff_7,
        R.drawable.puff_8,
        R.drawable.puff_9,
    )
    private val frameSize = framesList.size - 1


    inner class StateHolder {
        internal val frames
            get() = framesList
        internal val frameLength
            get() = frameSize
    }
}