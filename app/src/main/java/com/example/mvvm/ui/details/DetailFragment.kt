package com.example.mvvm.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvm.R
import com.example.mvvm.data.loadImage
import com.example.mvvm.data.network.MovieApi
import com.example.mvvm.data.repository.MovieRepository
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    lateinit var factory :ViewDetailsFactory
    lateinit var id:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_detail, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Details"

        arguments?.let {
            val safeArgs = DetailFragmentArgs.fromBundle(it)
            id = safeArgs.id
        }
        val api = MovieApi()
        val repository = MovieRepository(api)
        factory = ViewDetailsFactory(repository)
        detailsViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        detailsViewModel.getDetails(id)

        detailsViewModel.details.observe(viewLifecycleOwner, Observer {
            tv_movie_name.text = it?.Title
            tv_movie_year.text = "Year : " + it?.Year
            tv_release_date.text = "Released Date : " + it?.Released
            tv_genre.text = "Genre : " + it?.Genre
            tv_director.text = "Director : " + it?.Director
            tv_actor.text = "Actors : " + it?.Actors
            tv_plot_summary.text = "Description : " + it?.Plot
            loadImage(imageview, it.Poster)
        })
        return view
    }
}