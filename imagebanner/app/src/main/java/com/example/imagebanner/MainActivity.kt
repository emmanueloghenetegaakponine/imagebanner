package com.example.imagebanner

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.imagebanner.adapters.SliderAdapter
import com.example.imagebanner.models.SliderModel

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
    }
    private var viewPager: ViewPager? = null
    private var tvTitle: TextView? = null
    private var linearDots: LinearLayout? = null
    private var adapter: SliderAdapter? = null
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initAdapter()
    }

    private fun initAdapter() {
        adapter = SliderAdapter()
        //set adapter
        viewPager?.adapter = adapter
        //set data
        adapter?.setData(generateDummyData())

        //add buttom dots and title for first item
        addbuttomDots(linearDots, generateDummyData().size,0)
        tvTitle?.text = generateDummyData()[0].title
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                //add bottom dot and title
                addbuttomDots(linearDots, generateDummyData().size, position)
                tvTitle?.text = generateDummyData()[position].title
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == 1) {
                    // state 1 -> means user scrooled the page manually
                    stopSlider()
                    startSlider()
                }
            }

        })
        //start the slider
        startSlider()

    }

    private fun initViews() {
        viewPager = findViewById(R.id.view_pager)
        tvTitle = findViewById(R.id.tv_title)
        linearDots = findViewById(R.id.linear_dots)

        //initialize handler
        handler = Handler(Looper.getMainLooper())
    }

    private fun generateDummyData(): MutableList<SliderModel>{
        val list = mutableListOf<SliderModel>()

    list.add(
        SliderModel(
            "Title 1",
        ""
        )
    )

        list.add(
            SliderModel(
                "Title 2",
                ""
            )
        )
        return list


    }

    private fun addbuttomDots(linearLayout: LinearLayout?, size: Int, currentPos: Int) {
        val dots = arrayOfNulls<ImageView>(size)

        linearLayout?.removeAllViews()

        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val widthHeight = 18

            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(10,0,10,0)

            dots[i]?.layoutParams = params
            dots[i]?.setImageResource(R.drawable.dots_bg)

            //add view to linear layout
            linearLayout?.addView(dots[i])
        }
        if(dots.isNotEmpty()){
            dots[currentPos]?.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_green_dark), PorterDuff.Mode.SRC_ATOP)
        }
    }
    private fun startSlider() {
        Log.d(TAG, "startSlider: called")
        handler?.postDelayed(mRunnable,3000)
    }

    private fun stopSlider() {
        Log.d(TAG, "stopSlider: called")
        handler?.removeCallbacks(mRunnable)
    }


    private val mRunnable: Runnable = object : Runnable {
        override fun run() {
            var currentPosition = viewPager?.currentItem ?: 0

            val totalItems = generateDummyData().size

            currentPosition += 1

            if(currentPosition >= totalItems) {
                currentPosition = 0
            }
            viewPager?.currentItem = currentPosition
            handler?.postDelayed(this,3000)
        }

    }
}