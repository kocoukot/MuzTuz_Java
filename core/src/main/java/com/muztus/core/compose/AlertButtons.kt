package com.muztus.core.compose

import com.muztus.core.R

sealed class AlertButtons {
    abstract val positiveButtonTitle: Int

    class YesNoButtonsAlert(
        override val positiveButtonTitle: Int = R.string.yes,
        val negativeButtonTitle: Int = R.string.no
    ) : AlertButtons()

    class OkButtonAlert(override val positiveButtonTitle: Int = R.string.ok) : AlertButtons()

}
