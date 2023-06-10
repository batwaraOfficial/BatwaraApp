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

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: View, b : Boolean){
        if(b) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    @BindingAdapter("invisible")
    @JvmStatic
    fun invisible(view: View, b : Boolean){
        if(b) view.visibility = View.INVISIBLE
        else view.visibility = View.VISIBLE
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: MaterialTextView, b : Boolean){
        if(b) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: MaterialButton, b : Boolean){
        if(b) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: AppCompatImageView, b : Boolean){
        if(b) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: MaterialCardView, b : Boolean){
        if(b) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: ConstraintLayout, b : Boolean){
        if(b) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view: LinearLayout, b : Boolean){
        if(b) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    @BindingAdapter("displayTime")
    @JvmStatic
    fun displayTime(view : MaterialTextView, timeStamp :Long?){
        val secs = (timeStamp?:0)/60
        val mins = secs/60
        val hours = mins/60
        val days = hours/24
        val months = days/30
        val years = months/12

        var displayText = "$secs secs ago"
        if(mins>0) displayText = "$mins min(s) ago"
        if(hours>0) displayText = "$hours hour(s) ago"
        if(days>0) displayText = "$days day(s) ago"
        if(months>0) displayText = "$months month(s) ago"
        if(years>0) displayText = "$years year(s) ago"

        view.text = displayText
    }

    @BindingAdapter("enable")
    @JvmStatic
    fun enable(view : AppCompatImageButton, flag :Boolean){
        view.fadeVisibility(flag)
        view.isClickable = flag
    }
}