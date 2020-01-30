package com.example.autoimageslider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ImageSliderActivity extends AppCompatActivity {
    private List<String> images;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager imageSliderViewPager;
    private LinearLayout sliderDotspanel;
    private ImageView[] dots;
    private ImageView prevIconIv, nextIconIv;
    private int dotsCount;
    private int currentPage = 0;
    private Timer timer;
    private final static long DELAY_MS = 500;
    private final static long PERIOD_MS = 3000;
    private static int TOTAL_IMAGES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        initialization();

        addImage();

        imageSlider();

        prevIconIv.setVisibility(View.GONE);
    }

    private void addImage() {
        images.add(0, "https://as2.ftcdn.net/jpg/00/27/42/23/500_F_27422334_WhgM2yszbWDY6peZdZ9AjuULkA4Q78qT.jpg");
        images.add(1, "https://as1.ftcdn.net/jpg/02/12/43/28/500_F_212432820_Zf6CaVMwOXFIylDOEDqNqzURaYa7CHHc.jpg");
        images.add(2, "https://as2.ftcdn.net/jpg/02/81/26/63/500_F_281266321_Y20WMaFGtspOjoqsE3gUi11p5I4N3CcS.jpg");
        images.add(3, "https://as2.ftcdn.net/jpg/02/99/39/31/500_F_299393162_s4PXhshRvNZj57clBeVMIQz5d68QaA0I.jpg");
        images.add(4, "https://as1.ftcdn.net/jpg/02/98/00/18/500_F_298001885_wtZbgGO2VKhZeycTlyigLrqukEXcJVp5.jpg");
        images.add(5, "https://as1.ftcdn.net/jpg/01/48/99/10/500_F_148991050_JKLlwAdywLDae9I71szNYagGsgw4mXOv.jpg");
        images.add(6, "https://as2.ftcdn.net/jpg/01/81/89/95/500_F_181899591_sdxVtZS5fQXduM6Wvi8HqfA93xXxFf6x.jpg");
        images.add(7, "https://as2.ftcdn.net/jpg/01/81/73/75/500_F_181737526_ZKNJNbhoFwqYcACFazgrZGHnxoDbM1wm.jpg");
    }

    private void initialization() {
        images = new ArrayList<>();
        prevIconIv = findViewById(R.id.prevIconIvId);
        nextIconIv = findViewById(R.id.nextIconIvId);
    }

    public void imageSlider() {
        TOTAL_IMAGES = images.size();
        imageSliderViewPager = findViewById(R.id.imageSliderViewPagerId);
        sliderDotspanel = findViewById(R.id.SliderDots);

//        set your own imageList from server images
        viewPagerAdapter = new ViewPagerAdapter(this, images);
        imageSliderViewPager.setAdapter(viewPagerAdapter);
        imageSliderViewPager.setAdapter(viewPagerAdapter);

        autoSlideTimer();

        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        if (images.size() == 1) {
            prevIconIv.setVisibility(View.GONE);
            nextIconIv.setVisibility(View.GONE);

        } else if (images.size() > 1) {
            nextIconIv.setVisibility(View.VISIBLE);
        }

        imageSliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

                    if (position < images.size()) {
                        if (position == 0) {
                            prevIconIv.setVisibility(View.GONE);
                            nextIconIv.setVisibility(View.VISIBLE);

                        } else if (position == 1) {
                            prevIconIv.setVisibility(View.VISIBLE);
                            nextIconIv.setVisibility(View.VISIBLE);

                        } else if (position == 2) {
                            prevIconIv.setVisibility(View.VISIBLE);
                            nextIconIv.setVisibility(View.VISIBLE);

                        } else if (position == images.size() - 1) {
                            nextIconIv.setVisibility(View.GONE);

                        } else {
                            nextIconIv.setVisibility(View.VISIBLE);
                        }
                    }
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void prev(View view) {
        imageSliderViewPager.setCurrentItem(imageSliderViewPager.getCurrentItem() - 1, true);
    }

    public void next(View view) {
        imageSliderViewPager.setCurrentItem(imageSliderViewPager.getCurrentItem() + 1, true);
    }

    private void autoSlideTimer() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == TOTAL_IMAGES) {
                    currentPage = 0;
                }

                imageSliderViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
}


