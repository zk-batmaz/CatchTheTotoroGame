package com.qbra.catchthetotorogame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.qbra.catchthetotorogame.databinding.ActivityGame2Binding
import kotlin.random.Random

class Game2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityGame2Binding
    var click : Int = 1
    var endGame : Int = 0
    var runnable : Runnable = Runnable {  }
    var handler : Handler = Handler(Looper.getMainLooper())
    val totoroList = ArrayList<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGame2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        totoroList.add(binding.imageView11)
        totoroList.add(binding.imageView12)
        totoroList.add(binding.imageView13)
        totoroList.add(binding.imageView14)
        totoroList.add(binding.imageView15)
        totoroList.add(binding.imageView16)
        totoroList.add(binding.imageView17)
        totoroList.add(binding.imageView18)
        totoroList.add(binding.imageView19)

        hideTotoro()

        object : CountDownTimer(5000, 1000){
            override fun onTick(p0: Long) {
                binding.textView.setText("Time: ${p0/1000}")
                if(endGame == 1){
                    cancel()
                    finishGame()
                }
            }

            override fun onFinish() {
                finishGame()
            }

        }.start()
    }

    fun pressedImage(view : View) {
        click ++
    }

    fun hideTotoro() {
        handler.post(object : Runnable {
            override fun run() {
                if (click > 0)
                {
                    for (image in totoroList) {
                        image.visibility = View.INVISIBLE
                    }
                    val randomNumber: Int = Random.nextInt(9)
                    totoroList[randomNumber].visibility = View.VISIBLE
                }
                else{
                    endGame = 1
                }
                click = 0
                handler.postDelayed(this, 1000)
            }
        })
        handler.post(runnable)
    }


    fun finishGame() {
        binding.textView.setText("Time: 0")
        val alert = AlertDialog.Builder(this@Game2Activity)

        handler.removeCallbacks(runnable)

        if(endGame == 1){
            alert.setTitle("Game Over!")
            alert.setMessage("Play Again?")
            alert.setPositiveButton("Yes") {dialog, id->
                val intent = intent
                finish()
                startActivity(intent)
            }
            alert.setNegativeButton("No") {dialog, id->
                val intent = Intent(this@Game2Activity, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
        else {
            alert.setTitle("End of the Game")
            alert.setMessage("Play One More Time")
            alert.setPositiveButton("Yes") {dialog, id->
                val intent = Intent(this@Game2Activity, GameActivity::class.java)
                finish()
                startActivity(intent)
            }
            alert.setNegativeButton("No") {dialog, id->
                val intent = Intent(this@Game2Activity, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
        alert.show()
    }
}