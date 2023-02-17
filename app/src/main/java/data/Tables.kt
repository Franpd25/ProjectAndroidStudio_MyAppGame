package data

import model.Game

class Tables {
    abstract class Games {
        companion object {
            val _ID = "_id"
            val TABLE_NAME = "games"
            val COLUMN_NAME = "name"
            val COLUMN_DESCRIPTION = "description"
            val COLUMN_DATE = "date"
            val COLUMN_GAMETYPE = "gameType"
            var games: MutableList<Game> = ArrayList()
        }
    }
}