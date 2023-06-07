package com.example.batwaraapp.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.textview.MaterialTextView

object InterfaceUtils {

    /**
     * Fades the view in||out based on visibility parameter
     * */
    fun View.fadeVisibility(visibility: Boolean, duration: Long = 250) {
        var transition: Transition = Fade()
        transition.duration = duration
        transition.addTarget(this)
        TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
        this.isVisible = visibility
    }

    /**
     * To be used instead of setOnClickListener.
     * Pass isAnimated==true to animate clicks.
     * Puts some wait time in-case user clicks multiple times on same view.
     * */
    fun View.uniClick(isAnimated: Boolean = false, debounceWaitTime: Int = 520, onClickFunctionality: () -> Unit) {
        fun animateView() {
            val animX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.9f, 1.0f)
            val animY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.9f, 1.0f)
            val animator = AnimatorSet()
            animator.play(animX).with(animY)
            animator.start()
        }
        setOnClickListener {
            if ((tag != null) && (tag as Long) > System.currentTimeMillis()) return@setOnClickListener
            else {
                tag = System.currentTimeMillis() + debounceWaitTime.toLong()
                if (isAnimated) animateView()
                onClickFunctionality()
            }
        }
    }

    /**
     * Makes toasting a bit easy.
     * Just in case to customize the toast, will be easier if used custom function from the beginning.
     * */
    fun toast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Sets silver shine to textView in UI.
     * */
    fun getGradientSilverDescent(textView: MaterialTextView, activityName: String) {
        val paint = textView.paint
        val width = paint.measureText((activityName))
        val textShader: Shader = LinearGradient(
            0f, 0f, width, textView.textSize, intArrayOf(
                Color.parseColor("#E1F5FE"), // start color
                Color.parseColor("#FAFAFA"), // middle color
                Color.parseColor("#E8EAF6"), // end color
            ), null, Shader.TileMode.CLAMP
        )
        textView.paint.shader = textShader
    }

}