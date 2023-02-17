package com.franprados.myappgame

import data.DataDbHelper
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import model.Game

class MainActivity : AppCompatActivity(), OnGameClickListener {
    private lateinit var rVG: RecyclerView
    private var gameAdapter: GameAdapter = GameAdapter(this)
    private var db: DataDbHelper? = null
    private lateinit var games: ArrayList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, FormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGameItemClicked(position: Int) {
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra("idGame", games[position].getId())
        startActivity(intent)
    }

    private fun loadData() {
        games = getGames()
        gameAdapter.GameAdapter(games, this)

        rVG = findViewById(R.id.recyclerViewGames)
        rVG.setHasFixedSize(true)
        rVG.layoutManager = LinearLayoutManager(this)
        rVG.adapter = gameAdapter
    }

    private fun getGames(): ArrayList<Game>{
        db = DataDbHelper(this)
        val games: ArrayList<Game> = db!!.getAllGames()
        return games
    }
}