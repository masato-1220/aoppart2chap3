package com.example.aop_part2_chapter3

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPiker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPiker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPiker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var changePasswordMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPiker1
        numberPiker2
        numberPiker3

        openButton.setOnClickListener {

            if (changePasswordMode) {
                Toast.makeText(this, "パスワード変更中です", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPiker1.value}${numberPiker2.value}${numberPiker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                //パスワード成功
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                //失敗
                showErrorAlertDialog()
            }
        }

        changePasswordButton.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPiker1.value}${numberPiker2.value}${numberPiker3.value}"

            if (changePasswordMode) {
                //パスワードを保存する機能
                val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

                passwordPreferences.edit(true){

                }

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)



            } else {
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    //パスワード成功

                    changePasswordMode = true
                    Toast.makeText(this, "変更するパスワードを入力してください", Toast.LENGTH_SHORT).show()

                    changePasswordButton.setBackgroundColor(Color.RED)

                    //startActivity(
                } else {
                    showErrorAlertDialog()
                }

            }
        }


    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("失敗")
            .setMessage("パスワードが間違っています")
            .setPositiveButton("確認") { _, _ -> }
            .create()
            .show()
    }

}