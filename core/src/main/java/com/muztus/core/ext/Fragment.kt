package com.muztus.core.ext

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.annotation.Keep
import androidx.annotation.MainThread
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val Fragment.navController
    get() = findNavController()

fun Fragment.hideSystemUI() {
    requireActivity().window?.apply {
        statusBarColor = Color.TRANSPARENT
//            if (Build.VERSION.SDK_INT in 21..29) {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        WindowCompat.setDecorFitsSystemWindows(this, false)
    }
}

fun Fragment.showSystemUI() {

    requireActivity().window?.apply {
//            if (Build.VERSION.SDK_INT in 21..29) {
        statusBarColor = Color.WHITE
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            } else if (Build.VERSION.SDK_INT >= 30) {
        WindowCompat.setDecorFitsSystemWindows(this, true)
//            }
    }
}

inline fun <reified T> Fragment.arg(key: String): Lazy<T?> =
    lazy { this.arguments?.get(key) as? T? }

inline fun <reified T> Fragment.requireArg(key: String): Lazy<T> =
    lazy { this.requireArguments().get(key) as T }


@MainThread
fun <T : ViewBinding> Fragment.viewBinding(
    binder: (view: View) -> T,
) = ViewBindingDelegate(binder) { viewLifecycleOwner }

class ViewBindingDelegate<T : ViewBinding>(
    private val binder: (view: View) -> T,
    private val viewLifecycleOwnerProvider: () -> LifecycleOwner,
) : ReadOnlyProperty<Fragment, T> {
    private val lifecycleObserver: BindingLifecycleObserver = BindingLifecycleObserver()
    private val viewLifecycleOwner: LifecycleOwner
        get() = viewLifecycleOwnerProvider()
    private var isViewCreated = false
    private var binding: T? = null

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        return if (!isViewCreated || binding == null) {
            binder(thisRef.requireView())
                .also { binding = it }
        } else {
            binding!!
        }
    }

    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {
        private val handler = Handler(Looper.getMainLooper())

        @Keep
        fun onCreateView() {
            isViewCreated = true
        }

        @Keep
        fun onDestroyView() {
            if (isViewCreated) {
                isViewCreated = false
                viewLifecycleOwner.lifecycle.removeObserver(this)
                handler.post { binding = null }
            }
        }
    }
}

