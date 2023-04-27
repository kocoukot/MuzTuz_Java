package com.artline.muztus.ui.tutorial

import android.app.Dialog
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.artline.muztus.R
import com.artline.muztus.commonFuncs.LevelsInfo
import com.artline.muztus.databinding.ActivityTutorialLvlBinding
import com.artline.muztus.sounds.GameSound
import com.artline.muztus.sounds.GameSoundPlay
import com.muztus.core.compose.ToastShower
import com.muztus.core.ext.SupportInfoBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import java.util.concurrent.LinkedBlockingQueue

class TutorialFragment : Fragment(), SupportInfoBar {
    private var _binding: ActivityTutorialLvlBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<TutorialViewModel>()

    private val toastShower: ToastShower = ToastShower.Base()
    private val delay = 450

    private val dialogsToShow = LinkedBlockingQueue<Dialog?>()
    private var handler: Handler? = null

    //    private var editText: EditText? = null
    private var width = 0
    private var idButton = 0


    private var correctAnswer: String? = null
    private var artistName: Array<String> = emptyArray()
    private var lvlPast = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ActivityTutorialLvlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels

        artistName = LevelsInfo().correctAnswersList[0][0]
        correctAnswer = artistName[0]

        with(binding) {
            buttonSayAnswer.apply {
                setOnClickListener {
                    if (editTextTutorial.text.isNotEmpty()) {
                        viewModel.checkAnswer(editTextTutorial.text.toString())
                    }
                }
            }


            buttonShowAmountLetters.setOnClickListener {
                useHelp(R.string.showLettersAmount, buttonShowAmountLetters)
            }
            buttonShowOneLetter.setOnClickListener {
                useHelp(R.string.showOneLetter, buttonShowOneLetter)
            }

            buttonSongName.setOnClickListener {
                useHelp(R.string.showSongName, buttonSongName)
            }

            buttonHelpAnswer.setOnClickListener {
                useHelp(R.string.showAnswerHelp, buttonHelpAnswer)
            }

            imageBlueRow1.x = (width / 4 - 170).toFloat()
            imageBlueRow2.x = (width / 2 - 40).toFloat()
            // blueRow1.getLocationInWindow(test1);

            imageBlueRow1.setBackgroundResource(R.drawable.row_anim_left)
            imageBlueRow2.setBackgroundResource(R.drawable.row_anim_right)
            imageBlueRow6.setBackgroundResource(R.drawable.row_anim_right)
            imageBlueRow7.setBackgroundResource(R.drawable.row_anim_right)

            (imageBlueRow1.background as AnimationDrawable).start()
            (imageBlueRow2.background as AnimationDrawable).start()
            (imageBlueRow6.background as AnimationDrawable).start()
            (imageBlueRow7.background as AnimationDrawable).start()

        }

        //   LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();
        showInfo(R.string.helloFriend, 0)
        showInfo(R.string.firstHelp, 0)
        showInfo(R.string.secondHelp1, 1)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.isAnswerCorrect.observe(viewLifecycleOwner) { isCorrect ->
            if (isCorrect) {
                (requireActivity() as GameSoundPlay).playGameSound(GameSound.SoundWin)
                binding.amoutnLettes.text = "М У М И Й  Т Р О Л Л Ь"
//                val progressEditor = preferencesProgress!!.edit()
//                progressEditor.putInt("solved" + 0 + 0, 1)
//                progressEditor.apply()
                showInfo(R.string.lastText, 7)
            } else {
                (requireActivity() as GameSoundPlay).playGameSound(GameSound.SoundWrongAnswer)
                //если ответ неправильный, то сообщаем об этом
                toastShower.showToast(requireContext(), "Неправильный ответ!")
            }

//            val answer = binding.editTextTutorial.text.toString().trim { it <= ' ' }
//                .lowercase(Locale.getDefault()) //берем ответ, переводим в строку, обрезаем пробелы и приводим к нижнему регистру
//            for (s in artistName) {                //перебор всех ответов
//                if (answer == s) {
//                    lvlPast = 1
//
//                    //если правильный ответ угадан
//
//                    break
//                }
//            }
//
//            if (answer.isEmpty()) {
//                (requireActivity() as GameSoundPlay).playGameSound(GameSound.SoundWrongAnswer)
//                toastShower.showToast(requireContext(), "Для начала нужно ввести хоть какой-то ответ!")
//            } else if (lvlPast != 1) {
//
//            }
        }
    }

    private fun showDialog(dialog: Dialog) {
        if (dialogsToShow.isEmpty()) {
            dialog.show()
        }
        dialogsToShow.offer(dialog)
        dialog.setOnDismissListener {
            dialogsToShow.remove(dialog)
            if (!dialogsToShow.isEmpty()) {
                if (dialogsToShow.peek() != null) {
                    dialogsToShow.peek()!!.show()
                }
            }
        }
    }

    private fun showInfo(message: Int, number: Int) {
        val builder = Dialog(requireContext())
        builder.setCanceledOnTouchOutside(false)
        builder.setContentView(R.layout.inform)
        val text = builder.findViewById<TextView>(R.id.textInform)
        val button = builder.findViewById<Button>(R.id.buttonInformOK)
        text.setText(message)
        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f
        )
        layout.setMargins(50, 50, 50, 50)
        text.layoutParams = layout
        button.setOnClickListener {
            builder.cancel()
            with(binding) {
                when (number) {
                    1 -> {
                        buttonShowAmountLetters.isEnabled = true
                        imageBlueRow1.visibility = View.VISIBLE
                    }

                    2 -> {
                        buttonShowOneLetter.isEnabled = true
                        imageBlueRow1.x = (width / 4 + 150).toFloat()
                        imageBlueRow1.visibility = View.VISIBLE
                    }

                    3 -> {
                        buttonSongName.isEnabled = true
                        imageBlueRow2.visibility = View.VISIBLE
                    }

                    4 -> {
                        imageBlueRow6.visibility = View.INVISIBLE
                        imageBlueRow7.visibility = View.INVISIBLE
                        imageBlueRow2.visibility = View.VISIBLE
                        imageBlueRow2.x = (width / 2 + 200).toFloat()
                        showInfo(R.string.answerHelp, 5)
                        buttonHelpAnswer.isEnabled = true
                        //                    SharedPreferences.Editor editor = preferencesProgress.edit();
                        //                    editor.putInt("solved" + 0 + 0, 1);
                        //                    editor.apply();
                    }

                    5 -> {
                        imageBlueRow2.visibility = View.INVISIBLE
                        buttonSayAnswer.isEnabled = true
                        editTextTutorial.isClickable = true
                        editTextTutorial.isFocusableInTouchMode = true
                        editTextTutorial.isFocusable = true
                        editTextTutorial.freezesText = false
                        editTextTutorial.isCursorVisible = true
                        editTextTutorial.isContextClickable = true
                    }

                    6 -> {
                        showInfo(R.string.lastText, 7)

                    }

                    7 -> {
                        finishTutorial()
                    }
                }
            }
        }
        showDialog(builder)
    }

    private fun finishTutorial() {
//        val editor = preferencesProgress!!.edit()
//        editor.putInt("solved" + 0 + 0, 1)
//        editor.apply()
//        val intentFinish = Intent()
//        intentFinish.putExtra("key", 100)

        findNavController().popBackStack()
//        setResult(RESULT_OK, intent_finish)
//        finish()
    }

    private fun useHelp(message: Int, buttonPressed: Button?) {
        handler = Handler(Looper.getMainLooper())
        val builder = Dialog(requireContext())
        builder.setCanceledOnTouchOutside(false)
        builder.setContentView(R.layout.help)
        val text = builder.findViewById<TextView>(R.id.textInform)
        val buttonYes = builder.findViewById<Button>(R.id.buttonHelpYes)
        val buttonNo = builder.findViewById<Button>(R.id.buttonHelpNo)
        buttonNo.isEnabled = false
        text.setText(message)
        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f
        )
        layout.setMargins(50, 50, 50, 50)
        text.layoutParams = layout
        buttonYes.setOnClickListener {
            with(binding) {
                when (buttonPressed) {
                    binding.buttonShowAmountLetters -> {
                        builder.cancel()
                        buttonPressed.isEnabled = false
                        setTextViewAmountLetters(-1)
                        binding.amoutnLettes.visibility = View.VISIBLE
                        imageBlueRow1.visibility = View.INVISIBLE
                        handler!!.postDelayed({ showInfo(R.string.secondHelp2, 2) }, delay.toLong())
                    }

                    binding.buttonShowOneLetter -> {
                        builder.cancel()
                        buttonPressed.isEnabled = false
                        imageBlueRow1.visibility = View.INVISIBLE
                        chooseOneLetter()
                    }

                    binding.buttonSongName -> {
                        builder.cancel()
                        buttonPressed.isEnabled = false
                        imageBlueRow2.visibility = View.INVISIBLE
                        binding.textSongName.visibility = View.VISIBLE
                        handler!!.postDelayed({
                            showInfo(R.string.commonText, 4)
                            imageBlueRow6.visibility = View.VISIBLE
                            imageBlueRow7.visibility = View.VISIBLE
                            // showInfo(R.string.lastText, 6);
                        }, delay.toLong())
                    }

                    binding.buttonHelpAnswer -> {
                        binding.amoutnLettes.text = "М У М И Й  Т Р О Л Л Ь"
                        showInfo(R.string.lastText, 7)
                        builder.cancel()
                    }
                }
            }
        }
        builder.show()
    }

    private fun onCheckAnswer() {
        val answer = binding.editTextTutorial.text.toString().trim { it <= ' ' }
            .lowercase(Locale.getDefault()) //берем ответ, переводим в строку, обрезаем пробелы и приводим к нижнему регистру
        for (s in artistName) {                //перебор всех ответов
            if (answer == s) {
                lvlPast = 1
                (requireActivity() as GameSoundPlay).playGameSound(GameSound.SoundWin)

                //если правильный ответ угадан
                binding.amoutnLettes.text = "М У М И Й  Т Р О Л Л Ь"
//                val progressEditor = preferencesProgress!!.edit()
//                progressEditor.putInt("solved" + 0 + 0, 1)
//                progressEditor.apply()
                showInfo(R.string.lastText, 7)
                break
            }
        }
        if (answer.isEmpty()) {
            (requireActivity() as GameSoundPlay).playGameSound(GameSound.SoundWrongAnswer)
            toastShower.showToast(requireContext(), "Для начала нужно ввести хоть какой-то ответ!")
        } else if (lvlPast != 1) {
            (requireActivity() as GameSoundPlay).playGameSound(GameSound.SoundWrongAnswer)
            //если ответ неправильный, то сообщаем об этом
            toastShower.showToast(requireContext(), "Неправильный ответ!")
        }
    }

    private fun setTextViewAmountLetters(id: Int) {
        val lettersAmount = StringBuilder()
        val correctAnswer = "Мумий Тролль"
        var choosenLetter = ""
        if (id >= 0) {
            choosenLetter = correctAnswer[id].toString().uppercase(Locale.getDefault())
        }
        for (i in correctAnswer.indices) {
            if (i == id) {
                lettersAmount.append(choosenLetter).append(" ")
            } else {
                if (correctAnswer[i].toString() != " ") {
                    lettersAmount.append("_ ")
                } else {
                    lettersAmount.append("   ")
                }
            }
        }
        binding.amoutnLettes.text = lettersAmount.toString().trim { it <= ' ' }
    }

    private fun chooseOneLetter() {
        val correctAnswer = "Мумий Тролль"
        val builder = Dialog(requireContext())
        builder.setCanceledOnTouchOutside(false)
        builder.setContentView(R.layout.show_one_letter)
        val linearLayout = builder.findViewById<LinearLayout>(R.id.layoutHelpShowOneLetter)
        for (i in correctAnswer.indices) {
            val imageView = ImageView(requireContext())
            val layout = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layout.setMargins(0, 50, 0, 50)
            imageView.layoutParams = layout
            if (correctAnswer[i].toString() != " ") {
                imageView.setImageResource(R.drawable.button_letter)
                imageView.id = i
                imageView.setOnClickListener { v ->
                    idButton = v.id
                    builder.cancel()
                    setTextViewAmountLetters(idButton)
                    binding.amoutnLettes.visibility = View.VISIBLE
                    handler!!.postDelayed({ showInfo(R.string.thirdHelp, 3) }, delay.toLong())
                }
            } else {
                imageView.setImageResource(R.mipmap.vybor_bukvy_podskazki_space)
            }
            linearLayout.addView(imageView)
        }
        builder.show()
    }
}