package com.rayliu.lifecycleexplorer.utils

import androidx.annotation.ColorRes
import com.rayliu.lifecycleexplorer.R

object CardGenerators {
    private val alphabet = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H",
        "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X",
        "Y", "Z"
    )

    private val colorResIds = listOf(
        R.color.lemon_meringue,
        R.color.desert_sand,
        R.color.tuscany,
        R.color.jasper_orange,
        R.color.saffron,
        R.color.maximum_yellow_red,
        R.color.meat_brown,
        R.color.mustard,
        R.color.maximum_yellow,
        R.color.light_red,
        R.color.lilac,
        R.color.english_lavender,
        R.color.charm,
        R.color.bone,
        R.color.pale_spring_bud,
        R.color.medium_spring_bud,
        R.color.powder_blue,
        R.color.jet_stream,
        R.color.sage
    )

    fun generateFragmentTag(index: Int): String? {
        if (index >= alphabet.size || index < 0) {
            return null
        }

        return "Fragment ID: ${alphabet[index]}"
    }

    @ColorRes
    fun generateRandomColorRes(): Int {
        return colorResIds.random()
    }
}
