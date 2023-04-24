//package com.artline.muztus.old
//
//import android.app.Dialog
//import android.content.Intent
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.os.Handler
//import android.view.KeyEvent
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.artline.muztus.R
//import com.artline.muztus.sounds.GameSound
//import com.artline.muztus.sounds.MusicPlayerService.pause
//import com.artline.muztus.sounds.MusicPlayerService.resume
//import com.artline.muztus.sounds.SoundsPlayerService.start
//import com.artline.muztus.commonFuncs.LevelsInfo
//import com.muztus.core.compose.ToastShower
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.MobileAds
//import com.google.android.gms.ads.reward.RewardItem
//import com.google.android.gms.ads.reward.RewardedVideoAd
//import com.google.android.gms.ads.reward.RewardedVideoAdListener
//import java.util.Locale
//
//class Level : AppCompatActivity(), RewardedVideoAdListener {
//    private val toastShower: ToastShower = ToastShower.Base()
//
//    private val helpLettersAmount = 100
//    private val helpOneLetter = 250
//    private val helpSongName = 350
//    private val helpAnswer = 500
//    private val maxDuration = 7000
//    private val usedHelpsList = intArrayOf(0, 0, 0, 0)
//    private val PREFERENCESSounds = "Preferences.sounds"
//    private val PREFERENCESProgress = "Preferences.progress"
//    private val PREFERENCESPrizes = "Preferences.prizes"
//    var freeID = "ca-app-pub-8364051315582457/5955782184"
//    private var coinsWinAmount = 0
//    private var starsAmount = 0
//    private var lvlPast = 0
//    private var helpPrice = 0
//    private var imageView: ImageView? = null
//    private var freeCoinsImage: ImageView? = null
//    private var editText: EditText? = null
//    private var artistName: Array<String> = emptyArray()
//    private var textViewAmountLetters: TextView? = null
//    private var textViewCoins: TextView? = null
//    private var textViewStars: TextView? = null
//    private var textSongName: TextView? = null
//    private var artistSong: String? = null
//    private var correctAnswer: String? = null
//    private var buttonShowAmountLetters: Button? = null
//    private var buttonSongName: Button? = null
//    private var buttonShowOneLetter: Button? = null
//    private var buttonHelpAnswer: Button? = null
//    private var buttonSayAnswer: Button? = null
//    private var lvlID = 0
//    private var coins = 0
//    private var stars = 0
//    private var artistPicture = 0
//    private var lvlPremia //переделать в short?
//            = 0
//    private var start: Long = 0
//    private var lvlDuration: Long = 0
//    private var preferencesProgress: SharedPreferences? = null
//    private var preferencesPrizes: SharedPreferences? = null
//    private var preferencesSounds: SharedPreferences? = null
//
//    private var buttonsList: Array<Button?> = emptyArray()
//    private var mRewardedVideoAd: RewardedVideoAd? = null
//    private var musicButton: ImageView? = null
//    private var soundsButton: ImageView? = null
//    private var musicOff = true
//
//    // test
//    // String freeID = "ca-app-pub-3940256099942544/5224354917";
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_lvl)
//        val intent = intent
//        // musicOff = intent.getBooleanExtra("musicOff", false);
//        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE)
//        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE)
//        preferencesSounds = getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE)
//
//        if ((preferencesPrizes?.getInt("freeCoinsCounter", 0) ?: 0) >= 5) {
//            val prizesEditor = preferencesPrizes?.edit()
//            prizesEditor?.putInt("freeCoinsCounter", 0)
//            prizesEditor?.apply()
//        }
//        setViews() //ищем все вью
//        setLvlInformation() //получаем входную информацию
//        lvlPast = preferencesProgress?.getInt("solved$lvlPremia$lvlID", 0) ?: 0
//
//        for (i in usedHelpsList.indices) {
//            val helpUsed = preferencesProgress?.getInt("helpUsed$lvlPremia$lvlID$i", 0) ?: 0
//            usedHelpsList[i] = helpUsed
//        }
//        lvlBuild() //строим уровень по полученным данным (картинка, ответ, песня, состояние музыки и звуков, монеты)
//        if (lvlPast == 1) {                                    //если уровень пройден
//            toDoIfLvlPast()
//        } else {
//            start = System.currentTimeMillis() //запускаем счетчик
//        }
//        MobileAds.initialize(this) { }
//        //MobileAds.initialize(this, "ca-app-pub-8364051315582457~3265789330");
//        // Use an activity context to get the rewarded video instance.
//        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
//        mRewardedVideoAd?.rewardedVideoAdListener = this
//        loadRewardedVideoAd()
//    }
//
//    public override fun onResume() {
//        mRewardedVideoAd!!.resume(this)
//        super.onResume()
//        if (!musicOff) {
//            if (preferencesSounds!!.getBoolean("musicPlay", true)) {
//                resume(this)
//            }
//        }
//        musicOff = false
//    }
//
//    public override fun onPause() {
//        mRewardedVideoAd!!.pause(this)
//        super.onPause()
//        if (!musicOff) {
//            pause()
//        }
//    }
//
//    public override fun onDestroy() {
//        mRewardedVideoAd!!.destroy(this)
//        super.onDestroy()
//        val editor = preferencesProgress!!.edit()
//        editor.putInt("solved$lvlPremia$lvlID", lvlPast)
//        editor.apply()
//        finishLvl()
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
//    //проверка введенного ответа
//    fun onCheckAnswer(view: View?) {
//        var answer = editText!!.text.toString().trim { it <= ' ' }
//            .lowercase(Locale.getDefault()) //берем ответ, переводим в строку, обрезаем пробелы и приводим к нижнему регистру
//        answer = answer.replace("[ёЁ]".toRegex(), "е") //меняем все ё на е
//        for (s: String in artistName) {                //перебор всех ответов
//            if ((answer == s)) {
//                val finish = System.currentTimeMillis() //останавливаем счетчик
//                lvlDuration = finish - start
//                lvlPast = 1
//                start(this, GameSound.SOUND_WIN, preferencesSounds!!.getBoolean("soundsPlay", true))
//
//                //если правильный ответ угадан
//                val builder = Dialog(this)
//                builder.setCanceledOnTouchOutside(false)
//                builder.setContentView(R.layout.layout_end_lvl)
//                val imageStar1 = builder.findViewById<ImageView>(R.id.imageStar1) //звезды оценки
//                val imageStar2 = builder.findViewById<ImageView>(R.id.imageStar2)
//                val imageStar3 = builder.findViewById<ImageView>(R.id.imageStar3)
//                val imageViews = arrayOf(imageStar1, imageStar2, imageStar3)
//                val starsList = arrayOf(
//                    R.drawable.zvezda1_prizovogo_okna,
//                    R.drawable.zvezda2_prizovogo_okna,
//                    R.drawable.zvezda3_prizovogo_okna
//                )
//                val textCoinsWonAtFish = builder.findViewById<TextView>(R.id.textCoinsWonAtFish)
//                val buttonOk = builder.findViewById<Button>(R.id.buttonFinishLvl)
//                for (i in 0 until helpsUsedAmount()) {
//                    imageViews[i].setImageResource(starsList[i])
//                    coinsWinAmount += 10
//                    starsAmount += 1
//                }
//                coinsWinAmount = if (coinsWinAmount == 0) 5 else coinsWinAmount
//                var mult = 1
//                if (lvlDuration < maxDuration) {
//                    mult = 2
//                    textCoinsWonAtFish.text = "$coinsWinAmount x2"
//                } else {
//                    textCoinsWonAtFish.text = coinsWinAmount.toString()
//                }
//                textViewCoins!!.text = (coins + coinsWinAmount * mult).toString()
//                textViewStars!!.text = (stars + starsAmount).toString()
//                val progressEditor = preferencesProgress!!.edit()
//                progressEditor.putInt("solved$lvlPremia$lvlID", lvlPast)
//                progressEditor.putLong("lvlDuration$lvlPremia$lvlID", lvlDuration)
//                progressEditor.apply()
//                val prizesEditor = preferencesPrizes!!.edit()
//                prizesEditor.putInt("coins", coins + coinsWinAmount * mult)
//                prizesEditor.putInt("stars", stars + starsAmount)
//                if (preferencesPrizes!!.getInt("freeCoinsCounter", 0) > 0) {
//                    prizesEditor.putInt(
//                        "freeCoinsCounter",
//                        preferencesPrizes!!.getInt("freeCoinsCounter", 0) + 1
//                    )
//                }
//                prizesEditor.apply()
//                buttonOk.setOnClickListener(
//                    View.OnClickListener
//                    //заканчиваем уровень после нажатия ОК
//                    {
//                        builder.cancel()
//                        finishLvl()
//                    })
//                builder.show()
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
//
//            //если ответ неправильный, то сообщаем об этом
//
//            toastShower.showToast(this,"Неправильный ответ!",)
//        }
//    }
//
//    private fun helpsUsedAmount(): Int {
//        var amount = 3
//        for (i in 0 until usedHelpsList.size - 1) {
//            if (usedHelpsList[i] > 0) {
//                amount -= 1
//            }
//        }
//        return amount
//    }
//
//    fun onHelpUse(view: View) {
//        start(
//            this,
//            GameSound.SOUND_APPEAR_VIEW,
//            preferencesSounds!!.getBoolean("soundsPlay", true)
//        )
//        var message = 0
//        var button: Button? = null
//        if (coinsCalc(helpPrice(view.id))) {
//            when (view.id) {
//                R.id.buttonShowAmountLetters -> {
//                    setTextViewAmountLetters(-1)
//                    message = R.string.showLettersAmount
//                    button = buttonShowAmountLetters
//                }
//
//                R.id.buttonShowOneLetter -> {
//                    message = R.string.showOneLetter
//                    button = buttonShowOneLetter
//                }
//
//                R.id.buttonSongName -> {
//                    message = R.string.showSongName
//                    button = buttonSongName
//                }
//
//                R.id.buttonHelpAnswer -> {
//                    message = R.string.showAnswerHelp
//                    button = buttonHelpAnswer
//                }
//            }
//            useHelp(message, button)
//        }
//    }
//
//    private fun helpPrice(helpId: Int): Int {
//        when (helpId) {
//            R.id.buttonShowAmountLetters -> helpPrice = helpLettersAmount
//            R.id.buttonShowOneLetter -> helpPrice = helpOneLetter
//            R.id.buttonSongName -> helpPrice = helpSongName
//            R.id.buttonHelpAnswer -> helpPrice = helpAnswer
//        }
//        return helpPrice
//    }
//
//    private fun useHelp(message: Int, buttonPressed: Button?) {
//        val builder = Dialog(this)
//        builder.setCanceledOnTouchOutside(false)
//        builder.setContentView(R.layout.help)
//        val text = builder.findViewById<TextView>(R.id.textInform)
//        val buttonYes = builder.findViewById<Button>(R.id.buttonHelpYes)
//        val buttonNo = builder.findViewById<Button>(R.id.buttonHelpNo)
//        text.setText(message)
//        val layout = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            0.67f
//        )
//        layout.setMargins(50, 50, 50, 50)
//        text.layoutParams = layout
//        buttonYes.setOnClickListener {
//            buttonPressed!!.isClickable = false
//            builder.cancel()
//            helpButtonYes(buttonPressed)
//        }
//        buttonNo.setOnClickListener { builder.cancel() }
//        builder.show()
//    }
//
//    private fun helpButtonYes(buttonPressed: Button?) {
//        //подсказка первая
//        if (buttonPressed == buttonShowAmountLetters) {
//            buttonPressed!!.isEnabled = false
//            textViewAmountLetters!!.visibility = View.VISIBLE
//            CoinsChange()
//            saveHelpUsed(0)
//            start(
//                this,
//                GameSound.SOUND_SPEND_MONEY,
//                preferencesSounds!!.getBoolean("soundsPlay", true)
//            )
//        } else if (buttonPressed == buttonShowOneLetter) {
//            chooseOneLetter()
//        } else if (buttonPressed == buttonSongName) {
//            buttonPressed!!.isEnabled = false
//            textSongName!!.visibility = View.VISIBLE
//            saveHelpUsed(2)
//            CoinsChange()
//            start(
//                this,
//                GameSound.SOUND_SPEND_MONEY,
//                preferencesSounds!!.getBoolean("soundsPlay", true)
//            )
//        } else if (buttonPressed == buttonHelpAnswer) {
//            lvlPast = 1
//            val editor = preferencesProgress!!.edit()
//            editor.putInt("solved$lvlPremia$lvlID", lvlPast)
//            editor.apply()
//            val prizesEditor = preferencesPrizes!!.edit()
//            prizesEditor.putInt("freeCoinsCounter", 1)
//            prizesEditor.apply()
//            toDoIfLvlPast()
//            saveHelpUsed(3)
//            CoinsChange()
//            start(
//                this,
//                GameSound.SOUND_GAMEOVER,
//                preferencesSounds!!.getBoolean("soundsPlay", true)
//            )
//        }
//    }
//
//    private fun saveHelpUsed(helpID: Int) {
//        usedHelpsList[helpID] = 1
//        val editor = preferencesProgress!!.edit()
//        editor.putInt("helpUsed$lvlPremia$lvlID$helpID", 1)
//        editor.apply()
//    }
//
//    private fun CoinsChange() {
//        val handler = Handler()
//        val firstNum = intArrayOf(coins)
//        val secondNum = coins - helpPrice
//        //int step = 1;
//        handler.post(object : Runnable {
//            override fun run() {
//                textViewCoins!!.text = firstNum[0].toString()
//                if (firstNum[0] >= secondNum + 4) {
//                    firstNum[0] -= 5
//                }
//                handler.postDelayed(this, 0.0001.toLong())
//            }
//        })
//        coins -= helpPrice
//        val editor = preferencesPrizes!!.edit()
//        editor.putInt("coins", coins)
//        editor.apply()
//    }
//
//    private fun chooseOneLetter() {
//        val builder = Dialog(this)
//        builder.setCanceledOnTouchOutside(false)
//        builder.setContentView(R.layout.show_one_letter)
//        val linearLayout = builder.findViewById<LinearLayout>(R.id.layoutHelpShowOneLetter)
//        for (i in 0 until correctAnswer!!.length) {
//            val imageView = ImageView(this)
//            val layout = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//            layout.setMargins(1, 50, 1, 50)
//            layout.weight = 1f
//            imageView.layoutParams = layout
//            if (correctAnswer!![i].toString() != " ") {
//                imageView.setImageResource(R.drawable.button_letter)
//                imageView.id = i
//                imageView.setOnClickListener { v ->
//                    start(
//                        this@Level,
//                        GameSound.SOUND_SPEND_MONEY,
//                        preferencesSounds!!.getBoolean("soundsPlay", true)
//                    )
//                    buttonShowOneLetter!!.isEnabled = false
//                    buttonShowAmountLetters!!.isEnabled = false
//                    builder.cancel()
//                    CoinsChange()
//                    saveHelpUsed(1)
//                    setTextViewAmountLetters(v.id)
//                    textViewAmountLetters!!.visibility = View.VISIBLE
//                }
//            } else {
//                imageView.setImageResource(R.mipmap.vybor_bukvy_podskazki_space)
//            }
//            linearLayout.addView(imageView)
//        }
//        builder.show()
//    }
//
//    // задаем тект для подсказки по количеству букв и выбранной букве
//    private fun setTextViewAmountLetters(id: Int) {
//        val editor = preferencesProgress!!.edit()
//        editor.putInt("choosenLetterID$lvlPremia$lvlID", id)
//        editor.apply()
//        val lettersAmount = StringBuilder()
//        var choosenLetter = ""
//        if (id >= 0) {
//            choosenLetter = correctAnswer!![id].toString().uppercase(Locale.getDefault())
//        }
//        for (i in 0 until correctAnswer!!.length) {
//            if (i == id) {
//                lettersAmount.append(choosenLetter).append(" ")
//            } else {
//                if (correctAnswer!![i].toString() != " ") {
//                    lettersAmount.append("_ ")
//                } else {
//                    lettersAmount.append("   ")
//                }
//            }
//        }
//        textViewAmountLetters!!.text = lettersAmount.toString().trim { it <= ' ' }
//    }
//
//    //закрываем уровень
//    private fun finishLvl() {
//        musicOff = true
//        val intentLvlPast = Intent()
//        val intentLvlExited = Intent()
//        intentLvlPast.putExtra("lvlID", lvlID)
//        intentLvlExited.putExtra("lvlID", lvlID)
//        setResult(RESULT_OK, intentLvlPast)
//        setResult(RESULT_CANCELED, intentLvlExited)
//        finish()
//    }
//
//    private fun coinsCalc(helpPrice: Int): Boolean {
//        return if (coins - helpPrice >= 0) {
//            true
//        } else {
//            start(
//                this,
//                GameSound.SOUND_WARNING_VIEW,
//                preferencesSounds!!.getBoolean("soundsPlay", true)
//            )
//            toastShower.showToast(this, "К сожалению у Вас недостаточно монет. Для подсказки необходимо $helpPrice монет.")
//
//            false
//        }
//    }
//
//    private fun setViews() {
//        imageView = findViewById(R.id.imageViewArtist)
//        editText = findViewById(R.id.editTextTipedAnswer)
//        textViewAmountLetters = findViewById(R.id.textViewAmoutnLettes)
//        textViewCoins = findViewById(R.id.levelCoins)
//        textViewStars = findViewById(R.id.levelStars)
//
//        // hints
//        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters)
//        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter)
//        buttonSongName = findViewById(R.id.buttonSongName)
//        buttonHelpAnswer = findViewById(R.id.buttonHelpAnswer)
//        buttonSayAnswer = findViewById(R.id.buttonSayAnswer)
//        textSongName = findViewById(R.id.textSongName)
//        freeCoinsImage = findViewById(R.id.freeCoins)
//        musicButton = findViewById(R.id.levelMusic)
//        soundsButton = findViewById(R.id.levelSounds)
//        buttonsList = arrayOf(
//            buttonShowAmountLetters,
//            buttonShowOneLetter,
//            buttonSongName,
//            buttonHelpAnswer,
//            buttonSayAnswer
//        )
//    }
//
//    private fun setLvlInformation() {
//        val newLvl = intent
//        lvlID = newLvl.getIntExtra("position", 0)
//        lvlPremia = newLvl.getIntExtra("premiaIDtoLVL", 0)
//        artistPicture = LevelsInfo().premiaImagesList[lvlPremia][lvlID]
//        artistName = LevelsInfo().correctAnswersList[lvlPremia][lvlID]
//        correctAnswer = artistName[0]
//        artistSong = LevelsInfo().albomsList[lvlPremia][lvlID]
//        coinsStarsUpDate()
//        if (preferencesPrizes!!.getInt("freeCoinsCounter", 0) == 0) {
//            freeCoinsImage!!.visibility = View.VISIBLE
//        } else {
//            freeCoinsImage!!.visibility = View.INVISIBLE
//        }
//
//        //Правильный ответ
//        // lvlPast = newLvl.getBooleanExtra("lvlPast", false);                //получение информации о факте прохождения песни
//    }
//
//    private fun lvlBuild() {
//        textSongName!!.text = artistSong //присвоение вьюхе песни
//        imageView!!.setImageResource(artistPicture) //присвоение вьюхе картинки артиста
//        if (usedHelpsList[0] == 1) {
//            textViewAmountLetters!!.visibility = View.VISIBLE
//            buttonShowAmountLetters!!.isEnabled = false
//        }
//        if (usedHelpsList[1] == 1) {
//            buttonShowAmountLetters!!.isEnabled = false
//            buttonShowOneLetter!!.isEnabled = false
//            textViewAmountLetters!!.visibility = View.VISIBLE
//            setTextViewAmountLetters(
//                preferencesProgress!!.getInt(
//                    "choosenLetterID$lvlPremia$lvlID",
//                    -1
//                )
//            )
//        }
//        if (usedHelpsList[2] == 1) {
//            buttonSongName!!.isEnabled = false
//            textSongName!!.visibility = View.VISIBLE
//        }
//    }
//
//    private fun toDoIfLvlPast() {
//        for (b: Button? in buttonsList) {
//            b!!.isEnabled = false
//        }
//        editText!!.isClickable = false //блокируем эдит текст
//        editText!!.isCursorVisible = false
//        editText!!.isEnabled = false
//        val text = StringBuilder() // присваиваем и открываем ответ
//        for (i in 0 until correctAnswer!!.length) {
//            text.append(correctAnswer!![i]).append(" ")
//        }
//        textViewAmountLetters!!.text =
//            text.toString().trim { it <= ' ' }.uppercase(Locale.getDefault())
//        textViewAmountLetters!!.visibility = View.VISIBLE
//        textSongName!!.visibility = View.VISIBLE //открываем название песни
//    }
//
//    private fun coinsStarsUpDate() {
//        coins = preferencesPrizes!!.getInt("coins", 0)
//        stars = preferencesPrizes!!.getInt("stars", 0)
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
//            editor.putBoolean("soundsPlay", false)
//        } else {
//            soundsButton!!.setImageResource(R.drawable.button_sound)
//            start(this, GameSound.SOUND_ON_MUSIC, true)
//            editor.putBoolean("soundsPlay", true)
//        }
//        editor.apply()
//    }
//
//    fun onGetFreeCoins(view: View?) {
//        if (mRewardedVideoAd!!.isLoaded) {
//            mRewardedVideoAd!!.show()
//        }
//    }
//
//    private fun loadRewardedVideoAd() {
//        mRewardedVideoAd!!.loadAd(
//            freeID,
//            AdRequest.Builder().build()
//        )
//    }
//
//    override fun onRewarded(reward: RewardItem) {
//        val prizesEditor = preferencesPrizes!!.edit()
//        prizesEditor.putInt("coins", coins + 150)
//        prizesEditor.putInt("freeCoinsCounter", 1)
//        prizesEditor.apply()
//        coinsStarsUpDate()
//        freeCoinsImage!!.visibility = View.INVISIBLE
//        start(
//            this,
//            GameSound.SOUND_GOT_COINS,
//            preferencesSounds!!.getBoolean("soundsPlay", true)
//        )
//    }
//
//    override fun onRewardedVideoAdLeftApplication() {}
//    override fun onRewardedVideoAdFailedToLoad(i: Int) {}
//    override fun onRewardedVideoAdLoaded() {}
//    override fun onRewardedVideoAdOpened() {}
//    override fun onRewardedVideoStarted() {}
//    override fun onRewardedVideoAdClosed() {
//        loadRewardedVideoAd()
//    }
//
//    override fun onRewardedVideoCompleted() {}
//}