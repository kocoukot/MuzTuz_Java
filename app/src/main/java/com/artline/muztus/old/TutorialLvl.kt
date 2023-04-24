//package com.artline.muztus.old
//
//import android.app.Dialog
//import android.content.Intent
//import android.content.SharedPreferences
//import android.graphics.drawable.AnimationDrawable
//import android.os.Bundle
//import android.os.Handler
//import android.util.DisplayMetrics
//import android.view.KeyEvent
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.artline.muztus.R
//import com.artline.muztus.sounds.GameSound
//import com.artline.muztus.sounds.MusicPlayerService.pause
//import com.artline.muztus.sounds.MusicPlayerService.resume
//import com.artline.muztus.sounds.SoundsPlayerService.start
//import com.artline.muztus.commonFuncs.LevelsInfo
//import com.muztus.core.compose.ToastShower
//import java.util.Locale
//import java.util.concurrent.LinkedBlockingQueue
//
//class TutorialLvl : AppCompatActivity() {
//    private val toastShower: ToastShower = ToastShower.Base()
//    private val delay = 450
//    private val PREFERENCESSounds = "Preferences.sounds"
//    private val PREFERENCESPrizes = "Preferences.prizes"
//    private val PREFERENCESProgress = "Preferences.progress"
//    private val dialogsToShow = LinkedBlockingQueue<Dialog?>()
//    private var handler: Handler? = null
//    private var buttonShowAmountLetters: Button? = null
//    private var buttonShowOneLetter: Button? = null
//    private var buttonSongName: Button? = null
//    private var buttonAnswerHelp: Button? = null
//    private var buttonSayAnswer: Button? = null
//    private var editText: EditText? = null
//    private var width = 0
//    private var idButton = 0
//    private var blueRow1: ImageView? = null
//    private var blueRow2: ImageView? = null
//    private var blueRow6: ImageView? = null
//    private var blueRow7: ImageView? = null
//    private var textSongName: TextView? = null
//    private var amoutnLettes: TextView? = null
//    private var textViewCoins: TextView? = null
//    private var textViewStars: TextView? = null
//    private var preferencesProgress: SharedPreferences? = null
//    private var preferencesPrizes: SharedPreferences? = null
//    private var preferencesSounds: SharedPreferences? = null
//    private var musicOff = false
//    private var musicButton: ImageView? = null
//    private var soundsButton: ImageView? = null
//    private var toast1: Toast? = null
//    private var correctAnswer: String? = null
//    private var artistName: Array<String> = emptyArray()
//    private var lvlPast = 0
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_tutorial_lvl)
//        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        width = displayMetrics.widthPixels
//        val intent = intent
//        musicOff = intent.getBooleanExtra("musicOff", false)
//        artistName = LevelsInfo().correctAnswersList[0][0]
//        correctAnswer = artistName[0]
//        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE)
//        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE)
//        preferencesSounds = getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE)
//        blueRow1 = findViewById(R.id.imageBlueRow1)
//        blueRow2 = findViewById(R.id.imageBlueRow2)
//        blueRow6 = findViewById(R.id.imageBlueRow6)
//        blueRow7 = findViewById(R.id.imageBlueRow7)
//        textViewCoins = findViewById(R.id.tutorialCoins)
//        textViewStars = findViewById(R.id.tutorialStars)
//        editText = findViewById(R.id.editTextTutorial)
//        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters)
//        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter)
//        buttonSongName = findViewById(R.id.buttonSongName)
//        buttonAnswerHelp = findViewById(R.id.buttonHelpAnswer)
//        buttonSayAnswer = findViewById(R.id.buttonSayAnswer)
//        amoutnLettes = findViewById(R.id.amoutnLettes)
//        textSongName = findViewById(R.id.textSongName)
//        musicButton = findViewById(R.id.tutorialMusic)
//        soundsButton = findViewById(R.id.tutorialSounds)
//        buttonShowAmountLetters?.getWidth()
//        blueRow1?.setX((width / 4 - 170).toFloat())
//        blueRow2?.setX((width / 2 - 40).toFloat())
//        // blueRow1.getLocationInWindow(test1);
//        blueRow1?.setBackgroundResource(R.drawable.row_anim_left)
//        blueRow2?.setBackgroundResource(R.drawable.row_anim_right)
//        blueRow6?.setBackgroundResource(R.drawable.row_anim_right)
//        blueRow7?.setBackgroundResource(R.drawable.row_anim_right)
//        val animationLeft = blueRow1?.getBackground() as AnimationDrawable
//        val animationRight = blueRow2?.getBackground() as AnimationDrawable
//        val animationSix = blueRow6?.getBackground() as AnimationDrawable
//        val animationSeven = blueRow7?.getBackground() as AnimationDrawable
//        animationLeft.start()
//        animationRight.start()
//        animationSix.start()
//        animationSeven.start()
//
//
//        //   LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();
//        coinsStarsUpDate()
//        showInfo(R.string.helloFriend, 0)
//        showInfo(R.string.firstHelp, 0)
//        showInfo(R.string.secondHelp1, 1)
//    }
//
//    private fun showDialog(dialog: Dialog) {
//        if (dialogsToShow.isEmpty()) {
//            dialog.show()
//        }
//        dialogsToShow.offer(dialog)
//        dialog.setOnDismissListener {
//            dialogsToShow.remove(dialog)
//            if (!dialogsToShow.isEmpty()) {
//                if (dialogsToShow.peek() != null) {
//                    dialogsToShow.peek()!!.show()
//                }
//            }
//        }
//    }
//
//    private fun showInfo(message: Int, number: Int) {
//        val builder = Dialog(this)
//        builder.setCanceledOnTouchOutside(false)
//        builder.setContentView(R.layout.inform)
//        val text = builder.findViewById<TextView>(R.id.textInform)
//        val button = builder.findViewById<Button>(R.id.buttonInformOK)
//        text.setText(message)
//        val layout = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            0.67f
//        )
//        layout.setMargins(50, 50, 50, 50)
//        text.layoutParams = layout
//        button.setOnClickListener {
//            builder.cancel()
//            if (number == 1) {
//                buttonShowAmountLetters!!.isEnabled = true
//                blueRow1!!.visibility = View.VISIBLE
//            } else if (number == 2) {
//                buttonShowOneLetter!!.isEnabled = true
//                blueRow1!!.x = (width / 4 + 150).toFloat()
//                blueRow1!!.visibility = View.VISIBLE
//            } else if (number == 3) {
//                buttonSongName!!.isEnabled = true
//                blueRow2!!.visibility = View.VISIBLE
//            } else if (number == 4) {
//                blueRow6!!.visibility = View.INVISIBLE
//                blueRow7!!.visibility = View.INVISIBLE
//                blueRow2!!.visibility = View.VISIBLE
//                blueRow2!!.x = (width / 2 + 200).toFloat()
//                showInfo(R.string.answerHelp, 5)
//                buttonAnswerHelp!!.isEnabled = true
//                //                    SharedPreferences.Editor editor = preferencesProgress.edit();
////                    editor.putInt("solved" + 0 + 0, 1);
////                    editor.apply();
//            } else if (number == 5) {
//                blueRow2!!.visibility = View.INVISIBLE
//                buttonSayAnswer!!.isEnabled = true
//                editText!!.isClickable = true
//                editText!!.isFocusableInTouchMode = true
//                editText!!.isFocusable = true
//                editText!!.freezesText = false
//                editText!!.isCursorVisible = true
//                editText!!.isContextClickable = true
//            } else if (number == 6) {
//                showInfo(R.string.lastText, 7)
//                val editor = preferencesProgress!!.edit()
//                editor.putInt("solved" + 0 + 0, 1)
//                editor.apply()
//            } else if (number == 7) {
//                finishTutorial()
//            }
//        }
//        showDialog(builder)
//    }
//
//    private fun finishTutorial() {
//        musicOff = true
//        val editor = preferencesProgress!!.edit()
//        editor.putInt("solved" + 0 + 0, 1)
//        editor.apply()
//        val intent_finish = Intent()
//        intent_finish.putExtra("key", 100)
//        setResult(RESULT_OK, intent_finish)
//        finish()
//    }
//
//    fun onShowAmountLetters(view: View?) {
//        useHelp(R.string.showLettersAmount, buttonShowAmountLetters)
//    }
//
//    fun onShowOneLetter(view: View?) {
//        useHelp(R.string.showOneLetter, buttonShowOneLetter)
//    }
//
//    fun onShowSongName(view: View?) {
//        useHelp(R.string.showSongName, buttonSongName)
//    }
//
//    fun onShowAnswer(view: View?) {
//        useHelp(R.string.showAnswerHelp, buttonAnswerHelp)
//    }
//
//    private fun useHelp(message: Int, buttonPressed: Button?) {
//        handler = Handler()
//        val builder = Dialog(this)
//        builder.setCanceledOnTouchOutside(false)
//        builder.setContentView(R.layout.help)
//        val text = builder.findViewById<TextView>(R.id.textInform)
//        val buttonYes = builder.findViewById<Button>(R.id.buttonHelpYes)
//        val buttonNo = builder.findViewById<Button>(R.id.buttonHelpNo)
//        buttonNo.isEnabled = false
//        text.setText(message)
//        val layout = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            0.67f
//        )
//        layout.setMargins(50, 50, 50, 50)
//        text.layoutParams = layout
//        buttonYes.setOnClickListener {
//            if (buttonPressed == buttonShowAmountLetters) {
//                builder.cancel()
//                buttonPressed!!.isEnabled = false
//                setTextViewAmountLetters(-1)
//                amoutnLettes!!.visibility = View.VISIBLE
//                blueRow1!!.visibility = View.INVISIBLE
//                handler!!.postDelayed({ showInfo(R.string.secondHelp2, 2) }, delay.toLong())
//            } else if (buttonPressed == buttonShowOneLetter) {
//                builder.cancel()
//                buttonPressed!!.isEnabled = false
//                blueRow1!!.visibility = View.INVISIBLE
//                chooseOneLetter()
//            } else if (buttonPressed == buttonSongName) {
//                builder.cancel()
//                buttonPressed!!.isEnabled = false
//                blueRow2!!.visibility = View.INVISIBLE
//                textSongName!!.visibility = View.VISIBLE
//                handler!!.postDelayed({
//                    showInfo(R.string.commonText, 4)
//                    blueRow6!!.visibility = View.VISIBLE
//                    blueRow7!!.visibility = View.VISIBLE
//                    // showInfo(R.string.lastText, 6);
//                }, delay.toLong())
//            } else if (buttonPressed == buttonAnswerHelp) {
//                amoutnLettes!!.text = "М У М И Й  Т Р О Л Л Ь"
//                showInfo(R.string.lastText, 7)
//                builder.cancel()
//            }
//        }
//        builder.show()
//    }
//
//    fun onCheckAnswer(view: View?) {
//        val answer = editText!!.text.toString().trim { it <= ' ' }
//            .lowercase(Locale.getDefault()) //берем ответ, переводим в строку, обрезаем пробелы и приводим к нижнему регистру
//        for (s in artistName) {                //перебор всех ответов
//            if (answer == s) {
//                lvlPast = 1
//                start(this, GameSound.SOUND_WIN, preferencesSounds!!.getBoolean("soundsPlay", true))
//                //если правильный ответ угадан
//                amoutnLettes!!.text = "М У М И Й  Т Р О Л Л Ь"
//                val progressEditor = preferencesProgress!!.edit()
//                progressEditor.putInt("solved" + 0 + 0, 1)
//                progressEditor.apply()
//                showInfo(R.string.lastText, 7)
//                break
//            }
//        }
//        if (answer.isEmpty()) {
//            start(
//                this,
//                GameSound.SOUND_WRONG_ANSWER,
//                preferencesSounds!!.getBoolean("soundsPlay", true)
//            )
//            toastShower.showToast(this, "Для начала нужно ввести хоть какой-то ответ!")
//
//        } else if (lvlPast != 1) {
//            start(
//                this,
//                GameSound.SOUND_WRONG_ANSWER,
//                preferencesSounds!!.getBoolean("soundsPlay", true)
//            )
//            //если ответ неправильный, то сообщаем об этом
//            toastShower.showToast(this, "Неправильный ответ!")
//        }
//    }
//
//    private fun setTextViewAmountLetters(id: Int) {
//        val lettersAmount = StringBuilder()
//        val correctAnswer = "Мумий Тролль"
//        var choosenLetter = ""
//        if (id >= 0) {
//            choosenLetter = correctAnswer[id].toString().uppercase(Locale.getDefault())
//        }
//        for (i in 0 until correctAnswer.length) {
//            if (i == id) {
//                lettersAmount.append(choosenLetter).append(" ")
//            } else {
//                if (correctAnswer[i].toString() != " ") {
//                    lettersAmount.append("_ ")
//                } else {
//                    lettersAmount.append("   ")
//                }
//            }
//        }
//        amoutnLettes!!.text = lettersAmount.toString().trim { it <= ' ' }
//    }
//
//    private fun chooseOneLetter() {
//        val correctAnswer = "Мумий Тролль"
//        val builder = Dialog(this)
//        builder.setCanceledOnTouchOutside(false)
//        builder.setContentView(R.layout.show_one_letter)
//        val linearLayout = builder.findViewById<LinearLayout>(R.id.layoutHelpShowOneLetter)
//        for (i in 0 until correctAnswer.length) {
//            val imageView = ImageView(this)
//            val layout = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//            layout.setMargins(0, 50, 0, 50)
//            imageView.layoutParams = layout
//            if (correctAnswer[i].toString() != " ") {
//                imageView.setImageResource(R.drawable.button_letter)
//                imageView.id = i
//                imageView.setOnClickListener { v ->
//                    idButton = v.id
//                    builder.cancel()
//                    setTextViewAmountLetters(idButton)
//                    amoutnLettes!!.visibility = View.VISIBLE
//                    handler!!.postDelayed({ showInfo(R.string.thirdHelp, 3) }, delay.toLong())
//                }
//            } else {
//                imageView.setImageResource(R.mipmap.vybor_bukvy_podskazki_space)
//            }
//            linearLayout.addView(imageView)
//        }
//        builder.show()
//    }
//
//    private fun coinsStarsUpDate() {
//        textViewCoins!!.text = preferencesPrizes!!.getInt("coins", 0).toString()
//        textViewStars!!.text = preferencesPrizes!!.getInt("stars", 0).toString()
//        if (preferencesSounds!!.getBoolean("musicPlay", true)) {
//            musicButton!!.setImageResource(R.drawable.button_music)
//        } else {
//            musicButton!!.setImageResource(R.drawable.buton_music_off)
//        }
//        if (preferencesSounds!!.getBoolean("soundsPlay", true)) {
//            soundsButton!!.setImageResource(R.drawable.button_sound)
//        } else {
//            soundsButton!!.setImageResource(R.drawable.buton_sound_off)
//        }
//    }
//
//    fun onMelody(view: View?) {
//        val editor = preferencesSounds!!.edit()
//        if (preferencesSounds!!.getBoolean("musicPlay", true)) {
//            musicButton!!.setImageResource(R.drawable.buton_music_off)
//            pause()
//            editor.putBoolean("musicPlay", false)
//        } else {
//            musicButton!!.setImageResource(R.drawable.button_music)
//            resume(this)
//            editor.putBoolean("musicPlay", true)
//        }
//        editor.apply()
//    }
//
//    fun onSounds(view: View?) {
//        val editor = preferencesSounds!!.edit()
//        if (preferencesSounds!!.getBoolean("soundsPlay", true)) {
//            start(this, GameSound.SOUND_OFF_MUSIC, true)
//            soundsButton!!.setImageResource(R.drawable.buton_sound_off)
//            // MusicPlayerService.pause();
//            editor.putBoolean("soundsPlay", false)
//        } else {
//            start(this, GameSound.SOUND_ON_MUSIC, true)
//            soundsButton!!.setImageResource(R.drawable.button_sound)
//            //  MusicPlayerService.resume(this);
//            editor.putBoolean("soundsPlay", true)
//        }
//        editor.apply()
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        musicOff = true
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        when (keyCode) {
//            KeyEvent.KEYCODE_MENU -> {
//                musicOff = false
//                return false
//            }
//
//            KeyEvent.KEYCODE_HOME -> {
//                musicOff = false
//                return false
//            }
//        }
//        return super.onKeyDown(keyCode, event)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (!musicOff) {
//            pause()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (!musicOff) {
//            if (preferencesSounds!!.getBoolean("musicPlay", true)) {
//                resume(this)
//            }
//        }
//        musicOff = false
//    }
//}