package com.qbra.catchthetotorogame

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.qbra.catchthetotorogame.databinding.ActivityGameBinding
import java.util.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var totoroList = ArrayList<ImageView>()

    var runnable : Runnable = Runnable {  }
    var handler : Handler = Handler(Looper.getMainLooper())

    var score : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        totoroList.add(binding.imageView)
        totoroList.add(binding.imageView3)
        totoroList.add(binding.imageView4)
        totoroList.add(binding.imageView5)
        totoroList.add(binding.imageView6)
        totoroList.add(binding.imageView7)
        totoroList.add(binding.imageView8)
        totoroList.add(binding.imageView9)
        totoroList.add(binding.imageView10)

        hideTotoro()

        object : CountDownTimer(20000,1000) {
            override fun onTick(p0: Long) {
                binding.timeText.setText("Time: ${p0 / 1000}")
            }

            override fun onFinish() {
                binding.timeText.setText("Time: 0")
                val alert = AlertDialog.Builder(this@GameActivity)

                handler.removeCallbacks(runnable)

                if(score >= 30){
                    alert.setTitle("Your Score: ${score}")
                    alert.setMessage("Next Episode?")
                    alert.setPositiveButton("Yes") {dialog, id->
                        val intent = Intent(this@GameActivity, Game2Activity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    alert.setNegativeButton("No") {dialog, id->
                        val intent = Intent(this@GameActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    alert.show()
                }
                else {
                    alert.setTitle("Your Score: ${score}")
                    alert.setMessage("Play Again?")
                    alert.setPositiveButton("Yes") {dialog, id->
                        val intent = intent
                        finish()
                        startActivity(intent)
                    }
                    alert.setNegativeButton("No") {dialog, id->
                        val intent = Intent(this@GameActivity, MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    alert.show()
                }
            }

        }.start()
    }

    fun hideTotoro() {
        var randomNum  : Int
        runnable = object : Runnable {
            override fun run() {
                for (image in totoroList){
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                randomNum = random.nextInt(9)
                totoroList[randomNum].visibility = View.VISIBLE

                handler.postDelayed(this, 500)
            }
        }
        handler.post(runnable)
    }

    fun pressedImage(view : View) {
        score++
        binding.scoreText.setText("Score: ${score}")
    }
}