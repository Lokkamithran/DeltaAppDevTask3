package com.example.pawsome

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawsome.activities.DogViewActivity
import com.example.pawsome.extensions.ctx
import com.example.pawsome.responses.PhotoResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_view.view.*

class RecyclerAdapter(private val photoList: List<PhotoResult>):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindRepo(photoList[position])
    }

    override fun getItemCount() = photoList.size

    class ViewHolder(v: View): RecyclerView.ViewHolder(v),View.OnClickListener{

        private var photo: PhotoResult?=null
        init {
            v.setOnClickListener(this)
            v.dogImage.setBackgroundResource(R.drawable.ic_launcher_background)
        }
        fun bindRepo(photo: PhotoResult){
            this.photo=photo
            itemView.dogImage.setImageDrawable(null)
            Picasso.get().load(photo.url).into(itemView.dogImage)
            itemView.dogBreed.text = if(photo.breeds.isNotEmpty()) photo.breeds[0].name
                                else ""
        }
        override fun onClick(v: View) {
            val intent = Intent(itemView.context, DogViewActivity::class.java)
            intent.putExtra("Photo_Key", photo)
            itemView.context.startActivity(intent)
        }
    }
}