package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // variables to hold operands and calculations
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get all the widgets
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);
        // get all the buttons
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button10);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonAdd);
        Button buttonClear = (Button) findViewById(R.id.clear);
        Button buttonBack = (Button) findViewById(R.id.backspace);
        Button buttonNegate = (Button) findViewById(R.id.negate);
        Button buttonPower = (Button) findViewById(R.id.power);

        // append what button clicked to be shown on screen/widget
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);
        // what happens once click operation buttons
        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try{
                    Double doubleValue = Double.valueOf(value);
                    performOperation(op, doubleValue);
                }
                catch (NumberFormatException e){
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };
        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonPower.setOnClickListener(opListener);
        //negate with a anonymous function
        buttonNegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if (value.length() == 0){
                    newNumber.setText("-");
                }
                else{
                    try{
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    }
                    catch(NumberFormatException e){
                        newNumber.setText("");
                    }
                }
            }
        });
        // Clear
        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNumber.setText("");
                result.setText("");
                displayOperation.setText("");
                pendingOperation = "=";
                operand1 = null;
                operand2 = null;

            }
        };
        buttonClear.setOnClickListener(clearListener);

        //backspace
        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newNumber.getText().toString();
                if (text.length() == 1 || text.length() == 0){
                    newNumber.setText("");
                    return;
                }
                text = text.substring(0,text.length()-1);
                newNumber.setText(text);
            }
        };
        buttonBack.setOnClickListener(backListener);



    }
    private void performOperation(String operation, Double value){
        if (operand1 == null){
            operand1 = value;
        }
        else{
            operand2 = value;

            if (pendingOperation.equals("=")){
                pendingOperation = operation;
            }
            switch (pendingOperation){
                case "=":
                    operand1 = operand2;
                    break;
                case "÷":
                    if (operand2 == 0){
                        operand1 = 0.0; //make this undefined but editText or something needs changing
                    }
                    else{
                        operand1 /= operand2;
                    }
                    break;
                case "+":
                    operand1 += operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "×":
                    operand1 *= operand2;
                    break;
                case "^":
                    operand1 = Math.pow(operand1, operand2);
                    break;


            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");

    }
}