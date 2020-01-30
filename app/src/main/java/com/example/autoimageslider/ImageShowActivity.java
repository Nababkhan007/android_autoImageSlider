package com.example.autoimageslider;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ImageShowActivity extends AppCompatActivity {
    private ImageView showImageIv;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        showImageIv = findViewById(R.id.showImageIvId);

        image = getIntent().getStringExtra("image");
        Picasso.get().load(image).into(showImageIv);
    }
}
