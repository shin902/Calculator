package app.okabe.okashin.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.okabe.okashin.calculator.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.*

class MainActivity : AppCompatActivity() {


    /*
        連続計算に対応
        文字入力欄が2行にならないように
     */
    //TODO 符号を表示したい
    //TODO 税率変更
    //TODO 適宜無効化、非表示
    //FIXME 9/7*7をしたら、全てがわかるでしょう。

    // バインディングクラスの変数
    private lateinit var binding: ActivityMainBinding

    // 1番目に入力される変数を作る
    private var firstNumber: BigDecimal = BigDecimal(0)

    // 2番目に入力される変数を作る
    private var secondNumber: BigDecimal = BigDecimal(0)

    // 合計を入れる変数を作る
    private var totalNumber: BigDecimal = BigDecimal(0)

    private var floatValue: Double = 1.0

    private var maxLength: Int = 8
    private var roundNumber: Int = 8

    // 符号入力状態を待つ変数を、最初はempty(空)で作る
    private var operator: String = "empty"

    private fun viewRoundedText(number: BigDecimal): String {
        // numberが少数だったら
        return if (number.toDouble() % 1 != 0.0) {
            roundNumber = maxLength - number.toInt().toString().length -1
            number.setScale(roundNumber, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
        } else {
            number.toPlainString()
        }
    }

    private fun inputNumber(number: Int) {
        /*使われていない プラスマイナスボタンの処理・実装
        binding.plusMinusButton.setOnClickListener {
            if (binding.numberText.text == "0") {
                firstNumber = -0
                binding.numberText.text = "-0"
            }else if (binding.numberText.text == "-0") {
                firstNumber = 0
                binding.numberText.text = "0"
            } else {
                binding.plusMinusButton.isEnabled = false
            }
        }*/

        val numberText: String = binding.numberText.text.toString()

        // numberTextが負の数だったら
        if (numberText.contains("-")) {
            when (operator) {
                "empty" -> {
                    // 1番目に入力された数を10倍してnumberを引く
                    firstNumber =
                        firstNumber * 10.toBigDecimal() - number.toBigDecimal()
                    // 数字を入力するTextViewに反映
                    binding.numberText.text = viewRoundedText(firstNumber)
                    return
                }
            }

        // numberTextが少数だったら
        } else if (numberText.contains(".")) {
            when (operator) {
                "empty" -> {
                    firstNumber =
                        (firstNumber.toDouble() + number.toDouble() / 10.0.pow(floatValue)).toBigDecimal()      //TODO ゴリ押s...
                    binding.numberText.text = viewRoundedText(firstNumber)
                    floatValue += 1.0
                    return
                }
                else -> {
                    secondNumber =
                        (secondNumber.toDouble() + number.toDouble() / 10.0.pow(floatValue)).toBigDecimal()  //TODO ゴリ押s...
                    binding.numberText.text = viewRoundedText(secondNumber)
                    floatValue += 1.0
                    return
                }
            }
        }

        // 符号の入力状態を待つ変数が、emptyかどうか
        when (operator) {
            "empty" -> {
                // 一番目に入力された数を10倍してnumberを足す
                firstNumber =
                    firstNumber * BigDecimal(10) + BigDecimal(number)
                // 数字を入力するTextViewに反映
                binding.numberText.text = viewRoundedText(firstNumber)
            }
            else -> {
                // 2番目に入力された数を10倍してnumberを足す
                secondNumber =
                    secondNumber * BigDecimal(10) + BigDecimal(number)
                binding.numberText.text = viewRoundedText(secondNumber)
            }
        }
    }

    private fun calculation() {
        when (operator) {
            "plus" -> { totalNumber = firstNumber + secondNumber }
            "minus" -> { totalNumber = firstNumber - secondNumber }
            "multiply" -> { totalNumber = firstNumber * secondNumber }
            "division" -> { totalNumber = firstNumber.divide(secondNumber, MathContext.DECIMAL128) }
            "pow" -> {
                totalNumber = firstNumber.pow(secondNumber.toInt())
                binding.numberText.text = viewRoundedText(totalNumber)
            }
        }
    }


    private fun continueCalc() {
        if (secondNumber == BigDecimal(0)) {
            binding.numberText.text = viewRoundedText(secondNumber)
        } else {
            calculation()
            firstNumber = totalNumber
            binding.numberText.text = viewRoundedText(firstNumber)
            secondNumber = BigDecimal(0)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // 画面起動時は、符号ボタンと等号ボタンを利用できないようにする
        /*
        binding.plusButton.isEnabled = false
        binding.minusButton.isEnabled = false
        binding.multiplyButton.isEnabled = false
        binding.equalButton.isEnabled = false
        binding.divisionButton.isEnabled = false
        binding.dotButton.isEnabled = false
        binding.percentButton.isEnabled = false
        */

        // TextViewの初期化
        binding.numberText.text = firstNumber.toPlainString()


        // ボタン0が押されたら
        binding.numberButton0.setOnClickListener { inputNumber(0) }

        // ボタン1が押されたら
        binding.numberButton1.setOnClickListener { inputNumber(1) }

        // ボタン2が押されたら
        binding.numberButton2.setOnClickListener { inputNumber(2) }

        // ボタン3が押されたら
        binding.numberButton3.setOnClickListener { inputNumber(3) }

        // ボタン4が押されたら
        binding.numberButton4.setOnClickListener { inputNumber(4) }

        // ボタン5が押されたら
        binding.numberButton5.setOnClickListener { inputNumber(5) }
        // ボタン6が押されたら
        binding.numberButton6.setOnClickListener { inputNumber(6) }

        // ボタン7が押されたら
        binding.numberButton7.setOnClickListener { inputNumber(7) }

        // ボタン8が押されたら
        binding.numberButton8.setOnClickListener { inputNumber(8) }

        // ボタン9が押されたら
        binding.numberButton9.setOnClickListener { inputNumber(9) }


        // +ボタンが押されたら
        binding.plusButton.setOnClickListener {
            continueCalc()
            operator = "plus"
        }


        // -ボタンが押されたら
        binding.minusButton.setOnClickListener {
            continueCalc()
            when (operator) {
                "empty" -> {
                    if (firstNumber == BigDecimal(0)) {
                        binding.numberText.text = "-0"
                    } else {
                        operator = "minus"
                    }
                } else -> { operator = "minus" }
            }
        }

        // *ボタンが押されたら
        binding.multiplyButton.setOnClickListener {
            continueCalc()
            operator = "multiply"
        }

        // ÷ボタンが押されたら
        binding.divisionButton.setOnClickListener {
            continueCalc()
            operator = "division"
        }

        // %ボタンが押されたら
        binding.percentButton.setOnClickListener {
            continueCalc()
            firstNumber *= BigDecimal.valueOf(0.01)
            binding.numberText.text = viewRoundedText(firstNumber)
        }

        // .ボタンが押されたら
        binding.dotButton.setOnClickListener {
            if (! binding.numberText.text.contains(".")) {
                when (operator) {
                    "empty" -> {
                        binding.numberText.text = viewRoundedText(firstNumber) + "."
                    } else -> {
                        floatValue = 1.0
                        binding.numberText.text = viewRoundedText(secondNumber) + "."
                    }
                }
            }
        }

        // √ボタンが押されたら
        binding.rootButton.setOnClickListener {
            continueCalc()
            firstNumber = sqrt(firstNumber.toDouble()).toBigDecimal()       //TODO BigDecimalで計算したいよね‐‐
            binding.numberText.text = viewRoundedText(firstNumber)
        }

        // ^ボタンが押されたら
        binding.powButton.setOnClickListener {
            continueCalc()
            operator = "pow"
        }

        // πボタンが押されたら
        binding.piButton.setOnClickListener {
            continueCalc()
            firstNumber *= BigDecimal.valueOf(PI)                 //TODO 円周率のBigDecimalはないかも？
            binding.numberText.text = viewRoundedText(firstNumber)
        }

        // 税 ボタンが押されたら
        binding.taxButton.setOnClickListener {
            continueCalc()
            firstNumber *= BigDecimal.valueOf(1.1)
            binding.numberText.text = viewRoundedText(firstNumber)
        }

        // =ボタンが押されたら
        binding.equalButton.setOnClickListener {
            calculation()

            // 変数の初期化
            firstNumber = BigDecimal(0)
            secondNumber = BigDecimal(0)
            operator = "empty"

            // 合計を表示
            binding.numberText.text = viewRoundedText(totalNumber)
            totalNumber = BigDecimal(0)
            floatValue = 1.0
        }

        // Cボタンが押されたら
        binding.clearButton.setOnClickListener {
            if (secondNumber == BigDecimal(0)) {
                firstNumber = BigDecimal(0)
                binding.numberText.text = viewRoundedText(firstNumber)
            } else {
                secondNumber = BigDecimal(0)
                binding.numberText.text = viewRoundedText(secondNumber)
            }
            floatValue = 1.0
        }

        // ACボタンが押されたら
        binding.allClearButton.setOnClickListener {
            // 変数の初期化
            firstNumber = BigDecimal(0)
            secondNumber = BigDecimal(0)
            totalNumber = BigDecimal(0)
            operator = "empty"
            floatValue = 1.0
            binding.numberText.text = viewRoundedText(firstNumber)
        }
    }
}