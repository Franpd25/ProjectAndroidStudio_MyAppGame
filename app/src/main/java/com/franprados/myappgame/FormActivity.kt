package com.franprados.myappgame

import data.DataDbHelper
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import model.Game

class FormActivity: AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var date: EditText
    private lateinit var buttonA: Button
    private lateinit var buttonD: Button
    private lateinit var textView: TextView
    private lateinit var spinner: Spinner

    private var db: DataDbHelper? = null
    private var idGame: Int = -1


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_view)

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        date = findViewById(R.id.date)
        date.setOnClickListener{showDatePickerDialog()}
        buttonA = findViewById(R.id.button)
        buttonD = findViewById(R.id.button2)
        textView = findViewById(R.id.textView)

        val gameTypes: ArrayList<String> = ArrayList()
        gameTypes.add("Acción")
        gameTypes.add("Lucha")
        gameTypes.add("Deporte")
        gameTypes.add("Shooter")
        gameTypes.add("Otros")
        val aapt: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gameTypes)
        spinner = findViewById(R.id.spinnerGameType)
        spinner.setAdapter(aapt)

        idGame = intent.getIntExtra("idGame",-1)
        if (idGame > -1) {
            buttonD.visibility = View.VISIBLE
            buttonA.setText("Update")
            textView.setText("Update/Remove Game")
        }
        db = DataDbHelper(this)
        var gameToModify: Game? = db!!.getGameById(idGame)
        if (gameToModify != null) {
            title.setText(gameToModify.getName())
            description.setText(gameToModify.getDescription())
            date.setText(gameToModify.getDate())
            spinner.setSelection(gameToModify.getGameType().toInt())
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day:Int, month:Int, year:Int){
        var monthFixed = month+1
        date.setText("$day/$monthFixed/$year")
    }


    fun addGame(view: View) {
        db = DataDbHelper(this)
        val game = Game(title.text.toString(), description.text.toString(), date.text.toString(), spinner.selectedItemId.toString())
        var r: Long = -1

        if (title.text.isEmpty() || description.text.isEmpty()) {
            Toast.makeText(this, "Please enter required fields.", Toast.LENGTH_SHORT).show()
        } else {
            if (idGame > -1) { // UPDATE...
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Attention, updating game!!!")
                    .setMessage("Are you sure to update the game?")
                    .setNegativeButton("Cancel") { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton("Accept") { view, _ ->
                        view.dismiss()
                        game.setId(idGame)
                        r = db!!.updateGame(game).toLong()
                        if (r > -1) {
                            Toast.makeText(applicationContext, "Game saved successfully.",Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "Game not saved.",Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                    .setCancelable(false)
                    .create()
                dialog.show()
            } else { // CREATE...
                r = db!!.insertGame(game)
                if (r > -1) {
                    Toast.makeText(applicationContext, "Game saved successfully.",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Game not saved.",Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }

    fun deleteGame(view: View) {
        db = DataDbHelper(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Attention, eliminating gamee!!!")
            .setMessage("Are you sure to delete the game?")
            .setNegativeButton("Cancel") { view, _ ->
                view.dismiss()
            }
            .setPositiveButton("Accept") { view, _ ->
                view.dismiss()
                db!!.deleteGame(idGame)
                Toast.makeText(applicationContext, "Game removed successfully.",Toast.LENGTH_SHORT).show()
                finish()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }
}
