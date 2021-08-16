package com.example.pawsome.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pawsome.responses.PhotoResult
import com.example.pawsome.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dog_view.*

class DogViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_view)

        val dogInfo: PhotoResult = intent.getSerializableExtra("Photo_Key") as PhotoResult
        Picasso.get().load(dogInfo.url).into(dogImage)
        dogImage.minimumHeight = 500
        dogImage.maxHeight = 700
        dogName.text = if(dogInfo.breeds[0].name != null) dogInfo.breeds[0].name
                    else ""
        weight.text = if(dogInfo.breeds[0].weight.metric != null) "Weight: ${dogInfo.breeds[0].weight.metric} kg"
                    else ""
        height.text = if(dogInfo.breeds[0].height.metric != null) "Height: ${dogInfo.breeds[0].height.metric} cm"
                    else ""
        temperament.text = if(dogInfo.breeds[0].temperament != null) dogInfo.breeds[0].temperament
                    else ""
        lifeSpan.text = if(dogInfo.breeds[0].life_span != null) "Lifespan: ${dogInfo.breeds[0].life_span}"
                    else ""
    }
}