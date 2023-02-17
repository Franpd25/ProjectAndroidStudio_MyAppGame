package com.franprados.myappgame

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model.Game

class GameAdapter (private val onGameClickListener: OnGameClickListener) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    var games: ArrayList<Game> = ArrayList()
    lateinit var context: Context

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById(R.id.titleTextView) as TextView
        val description = view.findViewById(R.id.descriptionTextView) as TextView

        fun bind(game: Game, context: Context){
            title.text = game.getName()
            description.text = game.getDescription()
        }
    }

    @SuppressLint("NotConstructor")
    fun GameAdapter(games: ArrayList<Game>, c: Context) {
        this.games = games
        this.context = c
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GameViewHolder(layoutInflater.inflate(R.layout.game_item, parent, false))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val item = games.get(position)
        holder.bind(item, context)

        holder.itemView.setOnClickListener{
            onGameClickListener.onGameItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }
}