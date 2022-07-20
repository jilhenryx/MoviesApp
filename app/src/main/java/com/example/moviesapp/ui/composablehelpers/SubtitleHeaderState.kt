package com.example.moviesapp.ui.composablehelpers

data class SubtitleHeaderState(
    val text: String,
    val isError: Boolean
) {
    companion object {
        internal fun default(): SubtitleHeaderState = SubtitleHeaderState("", false)
    }
}