package com.example.batwaraapp.utils

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.batwaraapp.R
import com.example.batwaraapp.utils.InterfaceUtils.fadeVisibility
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

object BindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: AppCompatImageView, url:String?){
        if(url.isNullOrEmpty() == true) {
            view.setImageResource(R.drawable.profileicon)
        } else {
            Picasso.get().load(url).placeholder(R.drawable.profileicon).into(view)
        }
    }

//    @BindingAdapter("visible")
//    @JvmStatic
//    fun visible(view: View, b : Boolean){
//        if(b) view.visibility = View.VISIBLE
//        else view.visibility = View.GONE
//    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: View, b : Boolean){
        view.fadeVisibility(b)
    }

    @BindingAdapter("invisible")
    @JvmStatic
    fun invisible(view: View, b : Boolean){
        view.fadeVisibility(b)
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: MaterialTextView, b : Boolean){
        view.fadeVisibility(b)
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: MaterialButton, b : Boolean){
        view.fadeVisibility(b)
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: AppCompatImageView, b : Boolean){
        view.fadeVisibility(b)
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: MaterialCardView, b : Boolean){
        view.fadeVisibility(b)
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: ConstraintLayout, b : Boolean){
        view.fadeVisibility(b)
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: LinearLayout, b : Boolean){
        view.fadeVisibility(b)
    }
}