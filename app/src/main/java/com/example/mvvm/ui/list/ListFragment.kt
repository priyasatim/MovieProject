package com.example.mvvm.ui.list

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.data.models.MovieSearchList
import com.example.mvvm.data.network.MovieApi
import com.example.mvvm.data.repository.MovieRepository
import com.example.mvvm.ui.details.*
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), RecycleviewClickListener {
    private lateinit var viewModel: MovieViewModel
    private lateinit var factory: ViewModelFactory
    var movieResponse = arrayListOf<MovieSearchList>()
    var context = this
    var connectivity : ConnectivityManager? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_list, container, false)

        connectivity = activity?.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "List"
        return view
    }

    private fun isNetworkAvailable(): Boolean {
        progressbar.visibility = View.GONE
        val connectivityManager = getContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    override fun onRecycleViewItemClick(view: View, movie: MovieSearchList,pos: Int) {
        when(view.id){
            R.id.constraint_layout -> {
                    val direction = ListFragmentDirections.actionListFragmentToDetailFragment(movie.imdbID)
                    view.findNavController().navigate(direction)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressbar.visibility = View.VISIBLE

        if(isNetworkAvailable()) {
            val api = MovieApi()
            val repository = MovieRepository(api)
            factory = ViewModelFactory(repository)
            viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
            viewModel.getMovie()
            viewModel.movie.observe(viewLifecycleOwner, Observer { movies ->
                recycleview_movies.also {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)

                    it.adapter = MovieAdapter(movies, this)
                    movieResponse = movies as ArrayList<MovieSearchList>
                    progressbar.visibility = View.GONE
                }
            })
        }else{
            progressbar.visibility = View.GONE
            Toast.makeText(activity,"Internet not connected",Toast.LENGTH_LONG).show()
        }
    }
}