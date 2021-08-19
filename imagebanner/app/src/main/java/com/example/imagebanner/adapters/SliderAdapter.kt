package com.example.imagebanner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.imagebanner.R
import com.example.imagebanner.models.SliderModel

class SliderAdapter: PagerAdapter() {

    private var mSliderList: MutableList<SliderModel> = mutableListOf( )

    fun setData(sliderList: MutableList<SliderModel>) {
        this.mSliderList = sliderList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
       return mSliderList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return  view == obj

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageUrl = mSliderList[position].imageUrl

        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.slider_item_layout, container, false)
        //find image view
        val imageView: ImageView = view.findViewById(R.id.image_view)

            // set image using glide
        Glide.with(container.context)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)

        container.addView(view)
        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)

    }
}