package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView calculate, result;
    MaterialButton clear, lparenthesis, rparenthesis, divide, plus, multiply, minus, equals, delete;
    MaterialButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, dot;
    boolean lparent = false;
    boolean res = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculate = findViewById(R.id.calculate);
        result = findViewById(R.id.res);

        buttonId(delete, R.id.delete);
        buttonId(clear, R.id.clear);
        buttonId(lparenthesis, R.id.lparenthesis);
        buttonId(rparenthesis, R.id.rparenthesis);

        buttonId(divide, R.id.divide);
        buttonId(plus, R.id.plus);
        buttonId(multiply, R.id.multiply);
        buttonId(minus, R.id.minus);
        buttonId(equals, R.id.equal);
        buttonId(btn1, R.id.one);
        buttonId(btn2, R.id.two);
        buttonId(btn3, R.id.three);
        buttonId(btn4, R.id.four);
        buttonId(btn5, R.id.five);
        buttonId(btn6, R.id.six);
        buttonId(btn7, R.id.seven);
        buttonId(btn8, R.id.eight);
        buttonId(btn9, R.id.nine);
        buttonId(btn0, R.id.zero);
        buttonId(dot, R.id.Dot);

    }

    void buttonId(MaterialButton button, int id) {
        button = findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = calculate.getText().toString();


        switch (buttonText) {

            case "(":
                if (dataToCalculate.substring(dataToCalculate.length() - 1).equals("(")) {

                } else if (dataToCalculate.substring(dataToCalculate.length() - 1).equals(")")) {
                    dataToCalculate = dataToCalculate + (" * ") + buttonText;
                    lparent = true;
                } else if (!isNumber(dataToCalculate.substring(dataToCalculate.length() - 1))) {
                    dataToCalculate = dataToCalculate + buttonText;
                    lparent = true;
                } else
                    dataToCalculate = dataToCalculate + (" * ") + buttonText;
                lparent = true;
                break;
            case ")":
                if (lparent == true && isNumber(dataToCalculate.substring(dataToCalculate.length() - 1))) {
                    dataToCalculate = dataToCalculate + buttonText;
                    lparent = false;

                }
                break;
            case "C":
                if (dataToCalculate.length() > 0) {
                    calculate.setText("");
                    result.setText("");
                }

                return;
            case "=":
                calculate.setText(result.getText());
                result.setText("");
                res = true;

                return;
            case " ":
                if (dataToCalculate.length() > 0) {
                    dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                }

                break;
            default:
                if (!isNumber(buttonText)) {
                    res = false;
                    if (dataToCalculate.length() > 0) {
                        if (isNumber(dataToCalculate.substring(dataToCalculate.length() - 1)) || dataToCalculate.substring(dataToCalculate.length() - 1).equals(")")) {
                            dataToCalculate = dataToCalculate + buttonText;
                        }
                    }

                } else {
                    if (res) {
                        calculate.setText(buttonText);
                        dataToCalculate = buttonText;
                        res = false;
                    } else
                        dataToCalculate = dataToCalculate + buttonText;
                }
                break;


        }
        calculate.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if (dataToCalculate.length() == 0) {
            result.setText("");
        } else if (finalResult.equals("Err")) {
            result.setText("");
        } else if (finalResult.equals("NaN")) {
            result.setText("Error");
        } else if (!finalResult.equals("Err")) {
            result.setText(finalResult);
        }

    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }

    boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}