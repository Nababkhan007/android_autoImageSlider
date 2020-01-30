package com.example.autoimageslider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> images;
    private ImageView imageSliderImageView;
    private View parentView;
    private ViewPager viewPager;

    public ViewPagerAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            parentView = layoutInflater
                    .inflate(R.layout.image_slider_custom_layout, null);
        }

        imageSliderImageView = parentView.findViewById(R.id.imageSliderImageViewId);
        Picasso.get().load(images.get(position)).into(imageSliderImageView);

        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ImageShowActivity.class).putExtra("image", images.get(position)));
            }
        });

        viewPager = (ViewPager) container;
        viewPager.addView(parentView, 0);
        return parentView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        viewPager = (ViewPager) container;
        parentView = (View) object;
        viewPager.removeView(parentView);
    }
}
