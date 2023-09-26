package app.okabe.okashin.calculator

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.okabe.okashin.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    /*
        連続計算に対応
        文字入力欄が2行にならないように
     */
    //TODO ドットボタンの実装
    //TODO 割り算の連続計算

    // バインディングクラスの変数
    private lateinit var binding: ActivityMainBinding

    // 1番目に入力される変数を作る
    var firstNumber: Double = 0.0
    // 2番目に入力される変数を作る
    var secondNumber: Double = 0.0
    // 合計を入れる変数を作る
    var totalNumber: Double = 0.0
    // 符号入力状態を待つ変数を、最初はempty(空)で作る
    var operator: String = "empty"
    var MAXLENGTH:Int = 8



    fun InputNumber(number: Int) {
        // 符号ボタンと等号ボタンを利用できるようにする
        binding.plusButton.isEnabled = true
        binding.minusButton.isEnabled = true
        binding.multiplyButton.isEnabled = true
        binding.equalButton.isEnabled = true
        binding.divisionButton.isEnabled = true
        binding.dotButton.isEnabled = true
        binding.percentButton.isEnabled = true

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

        if (binding.numberText.length() < MAXLENGTH) {
            // 符号の入力状態を待つ変数が、emptyかどうか
            if (operator == "empty") {
                // 一番目に入力された数を10倍してnumberを足す
                firstNumber = firstNumber*10 + number
                // 数字を入力するTextViewに反映
                binding.numberText.text = firstNumber.toString()
            } else if (operator == "negative") {
                // 一番目に入力された数を10倍してnumberを引く
                firstNumber = firstNumber*10 - number
                // 数字を入力するTextViewに反映
                binding.numberText.text = firstNumber.toString()
            } else {
                // 2番目に入力された数を10倍してnumberを足す
                secondNumber = secondNumber*10 + number
                binding.numberText.text = secondNumber.toString()
            }
        }

    }

    fun Caluc(firstNumber: Double, secondNumber: Double){
        // 符号が+だったら
        if (operator == "plus") {
            totalNumber = firstNumber + secondNumber
        }
        // 符号が-だったら
        else if (operator == "minus") {
            totalNumber = firstNumber - secondNumber
        }
        // 符号が*だったら
         else if (operator == "multiply") {
            totalNumber = firstNumber * secondNumber
        }
        // 符号が÷だったら
        else if (operator == "division") {
            totalNumber = firstNumber / secondNumber
            // 123.456789 -> 小数点第5位四捨五入
            // 12.3456789 -> 小数点大6位四捨五入
            val totalInt: Int = Math.round(totalNumber).toInt()
            val totalLength: Int = totalInt.toString().length
            val roundNum: Int = MAXLENGTH-totalLength
            Log.d("", Math.pow(10.0, roundNum.toDouble()).toString())
            totalNumber = Math.round(totalNumber*Math.pow(10.0, roundNum.toDouble()) / Math.pow(10.0, roundNum.toDouble())).toDouble()
        }
    }

    fun CountinueCalc() {
        if (operator == "empty" || operator == "negative") {
            // 2番目に入力された文字をTextViewに反映させる
            binding.numberText.text = secondNumber.toString()
        } else {
            Caluc(firstNumber, secondNumber)
            firstNumber = totalNumber
            binding.numberText.text = firstNumber.toString()
            secondNumber = 0.0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // 画面起動時は、符号ボタンと等号ボタンを利用できないようにする
        binding.plusButton.isEnabled = false
        binding.minusButton.isEnabled = false
        binding.multiplyButton.isEnabled = false
        binding.equalButton.isEnabled = false
        binding.divisionButton.isEnabled = false
        binding.dotButton.isEnabled = false
        binding.percentButton.isEnabled = false

        // 一番目に入力される変数を、TextViewに反映させる
        binding.numberText.text = firstNumber.toString()


        // ボタン0がタップされたら
        binding.numberButton0.setOnClickListener { InputNumber(0) }

        // ボタン1がタップされたら
        binding.numberButton1.setOnClickListener { InputNumber(1) }

        // ボタン2がタップされたら
        binding.numberButton2.setOnClickListener { InputNumber(2) }

        // ボタン3がタップされたら
        binding.numberButton3.setOnClickListener { InputNumber(3) }
        // ボタン4がタップされたら
        binding.numberButton4.setOnClickListener { InputNumber(4) }

        // ボタン5がタップされたら
        binding.numberButton5.setOnClickListener { InputNumber(5) }
        // ボタン6がタップされたら
        binding.numberButton6.setOnClickListener { InputNumber(6) }

        // ボタン7がタップされたら
        binding.numberButton7.setOnClickListener { InputNumber(7) }

        // ボタン8がタップされたら
        binding.numberButton8.setOnClickListener { InputNumber(8) }

        // ボタン9がタップされたら
        binding.numberButton9.setOnClickListener { InputNumber(9)  }


        // +ボタンが押されたら
        binding.plusButton.setOnClickListener {
            CountinueCalc()
            // 符号を入れる前に、+を表す「plus」という文字を入れる
            operator = "plus"
        }


        // -ボタンが押されたら
        binding.minusButton.setOnClickListener {
            CountinueCalc()
            if (firstNumber == 0.0) {
                operator = "negative"
                secondNumber = 0.0
            } else {
                // 符号を入れる前に、-を表す「minus」という文字を入れる
                operator = "minus"
            }
        }


        // *ボタンが押されたら
        binding.multiplyButton.setOnClickListener {
            CountinueCalc()
            // 符号を入れる前に、*を表す「multiply」という文字を入れる
            operator = "multiply"
        }

        // %ボタンが押されたら
        binding.percentButton.setOnClickListener {
            CountinueCalc()
            firstNumber /= 100
            binding.numberText.text = firstNumber.toString()
        }

        // ÷ボタンが押されたら
        binding.divisionButton.setOnClickListener {
            CountinueCalc()
            operator = "division"
        }

        // =ボタンが押されたら
        binding.equalButton.setOnClickListener {
            Caluc(firstNumber, secondNumber)

            // 1番目、2番目に入力される数字を入れる変数を0にセット
            firstNumber = 0.0
            secondNumber = 0.0
            // 符号の入力状態を待つ変数をemptyにセット
            operator = "empty"
            // 合計をTextViewに表示
            binding.numberText.text = totalNumber.toString()
            totalNumber = 0.0
            }

        // Cボタンが押されたら
        binding.allClearButton.setOnClickListener {
            // 1番目、2番目に入力される数字を入れる変数を0にセット
            firstNumber = 0.0
            secondNumber = 0.0
            // 合計を入れる変数を初期化
            totalNumber = 0.0
            // 符号の入力状態を待つ変数をemptyにセット
            operator = "empty"
            // TextViewを初期化
            binding.numberText.text = firstNumber.toString()
        }
    }
}