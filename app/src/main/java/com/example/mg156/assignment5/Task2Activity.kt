package com.example.mg156.assignment5

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson

class Task2Activity : AppCompatActivity(), RecyclerViewFragment.OnFragmentInteractionListener {

    lateinit var recFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)
        title = ""

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

        if (savedInstanceState == null) {
            recFragment = RecyclerViewFragment.newInstance(R.id.recViewFragmentLayout.toString())
        }

        supportFragmentManager.beginTransaction().replace(R.id.task2framelayout,
                recFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, "recFragment", recFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.home) {
            val intent = Intent(this, NavigationDrawerActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.task2) {
            val intent = Intent(this, Task2Activity::class.java)
            startActivity(intent)
        }
        if (id == R.id.task3) {
            val intent = Intent(this, Task3Activity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFragmentInteraction(movie : MovieDataClass){
            supportFragmentManager.beginTransaction().replace(R.id.task2framelayout, MovieFragment.newInstance(movie,posterTable.get(movie.title)!!)).addToBackStack(null).commit()
    }

}
