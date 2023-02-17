package data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.Game

class DataDbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private lateinit var db: SQLiteDatabase
    private lateinit var values: ContentValues

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "games"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblGames = ("CREATE TABLE "+Tables.Games.TABLE_NAME+" (" +
                Tables.Games._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Tables.Games.COLUMN_NAME + " TEXT NOT NULL," +
                Tables.Games.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                Tables.Games.COLUMN_DATE + " TEXT NOT NULL," +
                Tables.Games.COLUMN_GAMETYPE + " TEXT NOT NULL)")
        db?.execSQL(createTblGames)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+Tables.Games.TABLE_NAME)
        onCreate(db)
    }

    fun insertGame(game: Game): Long {
        db = this.writableDatabase
        values = ContentValues()

        values.put(Tables.Games.COLUMN_NAME, game.getName())
        values.put(Tables.Games.COLUMN_DESCRIPTION, game.getDescription())
        values.put(Tables.Games.COLUMN_DATE, game.getDate())
        values.put(Tables.Games.COLUMN_GAMETYPE, game.getGameType())

        val success = db.insert(Tables.Games.TABLE_NAME, null, values)
        db.close()
        return success
    }

    @SuppressLint("Range", "Recycle")
    fun getGameById(id: Int): Game? {
        var g: Game? = null
        val query ="SELECT * FROM "+Tables.Games.TABLE_NAME+" WHERE "+Tables.Games._ID+" = "+id
        db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(query)
            return g
        }

        var id: Int
        var name: String
        var description: String
        var date: String
        var gameType: String

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(Tables.Games._ID))
                name = cursor.getString(cursor.getColumnIndex(Tables.Games.COLUMN_NAME))
                description = cursor.getString(cursor.getColumnIndex(Tables.Games.COLUMN_DESCRIPTION))
                date = cursor.getString(cursor.getColumnIndex(Tables.Games.COLUMN_DATE))
                gameType = cursor.getString(cursor.getColumnIndex(Tables.Games.COLUMN_GAMETYPE))

                g = Game(id = id, name = name, description = description, date = date, gameType = gameType)
            }while(cursor.moveToNext())
        }
        db.close()
        return g
    }

    @SuppressLint("Range", "Recycle")
    fun getAllGames(): ArrayList<Game> {
        val games: ArrayList<Game> = ArrayList()
        val query = "SELECT * FROM "+Tables.Games.TABLE_NAME+" ORDER BY name"
        db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }

        var id: Int
        var name: String
        var description: String

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(Tables.Games._ID))
                name = cursor.getString(cursor.getColumnIndex(Tables.Games.COLUMN_NAME))
                description = cursor.getString(cursor.getColumnIndex(Tables.Games.COLUMN_DESCRIPTION))

                val game = Game(id = id, name = name, description = description)
                games.add(game)
            }while(cursor.moveToNext())
        }
        db.close()
        return games
    }

    fun updateGame(game: Game): Int {
        val args = arrayOf(game.getId().toString())
        db = this.writableDatabase
        values = ContentValues()

        values.put(Tables.Games.COLUMN_NAME, game.getName())
        values.put(Tables.Games.COLUMN_DESCRIPTION, game.getDescription())
        values.put(Tables.Games.COLUMN_DATE, game.getDate())
        values.put(Tables.Games.COLUMN_GAMETYPE, game.getGameType())
        val u = db.update(Tables.Games.TABLE_NAME, values," _id = ?", args)
        db.close()
        return u
    }

    fun deleteGame(id: Int) : Int {
        val args = arrayOf(id.toString())
        db = this.writableDatabase

        val d = db.delete(Tables.Games.TABLE_NAME, " _id = ?", args)
        db.close()
        return d
    }
}