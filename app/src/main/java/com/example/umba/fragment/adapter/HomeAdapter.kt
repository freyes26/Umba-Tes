package com.example.umba.fragment.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umba.R
import com.example.umba.fragment.HomeDirections
import com.example.umba.repository.database.model.Movie
import com.example.umba.repository.network.Connetion
import java.io.File

class HomeAdapter() : ListAdapter<Movie, HomeAdapter.HomeViewHolder>(diftUtilCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HomeViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class HomeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val movieItemView : ImageView = itemView.findViewById(R.id.movie_image)
        private val title : TextView = itemView.findViewById(R.id.title)

        fun bind(movie: Movie?){
            if(Connetion.isConnected(itemView.context) && movie?.poster_path != null){
                Glide.with(itemView.context).load("${ itemView.context.resources.getString(R.string.UrlImage)+movie?.poster_path}").into(movieItemView)
            }
            else{
                val file = File(movieItemView.context.filesDir,"${movie?.id.toString() +itemView.context.resources.getString(R.string.from_image)}")
                if(file.exists()){
                    val bitmap = BitmapFactory.decodeFile(file.toString())
                    movieItemView.setImageBitmap(bitmap)
                    title.text = itemView.resources.getString(R.string.Empty)
                }
                else{
                    movieItemView.setImageResource(R.drawable.ic_baseline_wifi_off)
                    title.text = movie?.title
                }
            }
            movieItemView.setOnClickListener {
                val action = HomeDirections.actionHome2ToMovieDetail(movie?.id.toString())
                movieItemView.findNavController().navigate(action)
            }
        }

        companion object{
            fun create(parent: ViewGroup) : HomeViewHolder{
                val view : View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.movie_list,parent,false)
                return HomeViewHolder(view)
            }
        }
    }
}

private object diftUtilCallBack : DiffUtil.ItemCallback<Movie> (){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =  oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}