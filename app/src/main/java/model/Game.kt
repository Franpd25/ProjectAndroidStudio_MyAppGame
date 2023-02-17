package model

import java.util.Date

class Game {
    private var id: Int = 0
    private var name: String = ""
    private var description: String = ""
    private var date: String = ""
    private var gameType: String = ""

    constructor(id: Int, name: String, description: String, date: String, gameType: String) {
        this.id = id
        this.name = name
        this.description = description
        this.date = date
        this.gameType = gameType
    }

    constructor(name: String, description: String, date: String, gameType: String) {
        this.name = name
        this.description = description
        this.date = date
        this.gameType = gameType
    }

    constructor(id: Int, name: String, description: String) {
        this.id = id
        this.name = name
        this.description = description
    }

    fun getId():Int {
        return id;
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName():String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getDate(): String {
        return date
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun getGameType(): String {
        return gameType
    }

    fun setGameType(gameType: String) {
        this.gameType = gameType
    }

    override fun toString(): String {
        return "Game(id=$id, name='$name', description='$description', date='$date', gameType='$gameType')"
    }
}