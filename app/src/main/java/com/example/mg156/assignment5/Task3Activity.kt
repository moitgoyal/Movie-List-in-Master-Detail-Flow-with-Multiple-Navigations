package com.example.mg156.assignment5

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson

class Task3Activity : AppCompatActivity(), RecyclerViewFragment.OnFragmentInteractionListener {

    lateinit var recFragment: Fragment
    lateinit var toolbarTop: Toolbar
    lateinit var toolbarBottom: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task3)

        movieList = Gson().fromJson(movies , Array <MovieDataClass>::class.java ).toMutableList()
        posterTable = mutableMapOf()
        posterTable["Fight Club"] = R.drawable.fight_club
        posterTable["Forrest Gump"] = R.drawable.forrest_gump
        posterTable["The Godfather"] = R.drawable.godfather
        posterTable["The Godfather: Part II"] = R.drawable.godfather_2
        posterTable["Psycho"] = R.drawable.psycho
        posterTable["Pulp Fiction"] = R.drawable.pulp_fiction
        posterTable["Schindler's List"] = R.drawable.schindler_list
        posterTable["The Shawshank Redemption"] = R.drawable.shawshank_redemption
        posterTable["The Dark Knight"] = R.drawable.the_dark_knight
        posterTable["The Green Mile"] = R.drawable.the_green_mile

        toolbarTop = findViewById(R.id.toolbar_top) as Toolbar
        setSupportActionBar(toolbarTop)

        title = ""

        if (savedInstanceState == null) {
            recFragment = RecyclerViewFragment.newInstance(R.id.recViewFragmentLayout.toString())
        }
        supportFragmentManager.beginTransaction().replace(R.id.task3framelayout,
                recFragment).commit()

        toolbarBottom = findViewById(R.id.toolbar_bottom) as Toolbar
        toolbarBottom.inflateMenu(R.menu.task3_bottom_toolbar_menu)
        setUpToolbarItemSelected()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.task3_top_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.task3_top_toolbar_menu_unhide) {
            toolbarBottom.setVisibility(View.VISIBLE)
        } else if (id == R.id.task3_top_toolbar_sort_by_title) {
            val temp = recFragment as RecyclerViewFragment
            temp.sortMovieDataByTitle()
        }
        return true
    }

    internal fun setUpToolbarItemSelected() {
        toolbarBottom.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == R.id.task3_bottom_toolbar_sort_by_rating) {
                val temp = recFragment as RecyclerViewFragment
                temp.sortMovieDataByRating()
            }
            false
        })
        toolbarBottom.setNavigationIcon(R.drawable.cancel)
        toolbarBottom.setNavigationOnClickListener(View.OnClickListener { toolbarBottom.setVisibility(View.GONE) })
    }

    override fun onFragmentInteraction(movie : MovieDataClass){
        supportFragmentManager.beginTransaction().replace(R.id.task3framelayout, MovieFragment.newInstance(movie,posterTable.get(movie.title)!!)).addToBackStack(null).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, "recFragment", recFragment)
    }
}
