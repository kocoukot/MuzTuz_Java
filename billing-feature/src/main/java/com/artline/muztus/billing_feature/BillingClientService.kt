package com.artline.muztus.billing_feature

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.consumePurchase
import kotlinx.coroutines.runBlocking

class BillingClientService(
    private val onProductsReceived: (List<ShopItem>) -> Unit,
    private val onProductSuccess: (Int) -> Unit,
) {

    private var prodList: List<ShopItem> = emptyList()
    private lateinit var billingClient: BillingClient

    fun clientInit(context: Context) {
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
    }

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                getProductsList()
                handlePurchase(purchases.first())
                println("purchases ${purchases.first()}")
                prodList.find { it.productId.equals(purchases.first().products.first(), true) }
                    ?.let {
                        onProductSuccess.invoke(it.description.toInt())
                    }
            } else {
            }
        }

    fun billStartConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    getProductsList()
                }
            }

            override fun onBillingServiceDisconnected() {
                billStartConnection()
            }
        })
    }


    private fun getProductsList() {
        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    listOf(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(ONE_COIN_ID)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build(),

                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(TWO_COIN_ID)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build(),
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(THREE_COIN_ID)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build(),
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(FOUR_COIN_ID)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build()
                    )
                )
                .build()
        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                prodList = productDetailsList.map { prodDetails ->
                    println("productDetailsList $prodDetails")

                    ShopItem(
                        productId = prodDetails.productId,
                        description = prodDetails.description,
                        formattedPrice = prodDetails.oneTimePurchaseOfferDetails?.formattedPrice.orEmpty(),
                        imgRes = when (prodDetails.productId) {
                            ONE_COIN_ID -> R.drawable.img_coins_1
                            TWO_COIN_ID -> R.drawable.img_coins_2
                            THREE_COIN_ID -> R.drawable.img_coins_3
                            else -> R.drawable.img_coins_4
                        },
                        prodDetail = prodDetails,

                        )
                }.sortedBy { it.formattedPrice }
//                println("productDetailsList $productDetailsList")
                onProductsReceived.invoke(prodList)
            } else {
                println("billling msg ${billingResult.debugMessage} ")
            }
        }
    }

    fun launchBillFlow(product: ProductDetails, activity: Activity) {
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(product)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        billingClient.launchBillingFlow(activity, billingFlowParams)
    }

    private fun handlePurchase(purchase: Purchase) {
        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
        runBlocking {
            billingClient.consumePurchase(consumeParams)
        }
    }

    companion object {
        private const val ONE_COIN_ID = "com.artline.muztus.one_coin"
        private const val TWO_COIN_ID = "com.artline.muztus.two_coin"
        private const val THREE_COIN_ID = "com.artline.muztus.three_coin"
        private const val FOUR_COIN_ID = "com.artline.muztus.four_coin"
    }


}