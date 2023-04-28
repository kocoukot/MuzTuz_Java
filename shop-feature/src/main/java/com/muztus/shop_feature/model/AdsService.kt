package com.muztus.shop_feature.model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.muztus.shop_feature.BuildConfig
import timber.log.Timber

class AdsService(
    private val context: Context,
    private val onRewardSuccess: (Int) -> Unit
) {


    private var rewardedAd: RewardedAd? = null

    @SuppressLint("VisibleForTests")
    private val adRequest = AdRequest.Builder().build()

    init {
        adInit()
    }

    private fun adInit() {
        RewardedAd.load(
            context,
            BuildConfig.GOOGLE_WATCH_KEY,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
//                Log.d(TAG, adError?.toString())
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Timber.d("ads client", "Ad was loaded.")
                    rewardedAd = ad
                }
            })
    }

    fun showAd(activity: Activity) {
        if (rewardedAd != null) {
            rewardedAd?.show(activity) {
                rewardedAd?.fullScreenContentCallback = adCallBack
                onRewardSuccess.invoke(it.amount)
            }
        }
    }

    private val adCallBack = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            Timber.d("ads client", "ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Set the ad reference to null so you don't show the ad a second time.
            Timber.d("ads client", "ad was dismissed fullscreen content..")
            adInit()
            rewardedAd = null
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            // Called when ad fails to show.
            Timber.d("ads client", "ad was failed to show fullscreen content.")
            rewardedAd = null
        }

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            Timber.d("ads client", "ad was recorded an impression.")
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Timber.d("ads client", "ad was showed fullscreen content.")
        }
    }
}