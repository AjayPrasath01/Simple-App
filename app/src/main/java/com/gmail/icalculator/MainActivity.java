package com.gmail.icalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static TextView resultbox;
    Button ac_btn;
    GestureDetector gesture;
    GestureListener gesturelistener;
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        setContentView(R.layout.activity_main);
        resultbox = findViewById(R.id.textView);
        ac_btn = findViewById(R.id.ac);
        layout = findViewById(R.id.layout);
        gesturelistener = new GestureListener(layout);
    }

    @Override
    public void onClick(View v) {
        Button clicked_btn = findViewById(v.getId());
        String name = clicked_btn.getText().toString();
        if (name.equals("=")) {
            String result = eval(resultbox.getText().toString());
            resultbox.setText(result);
        }else if (name.equals("%")){
            Double value = new Double(resultbox.getText().toString());
            resultbox.setText(String.valueOf(value/100));
        }
        else if(name.equals("+/-")){
            String value = resultbox.getText().toString();
            if (value.contains("-")){
                resultbox.setText(value.replace("-", ""));
            }else{
                resultbox.setText("-" + value);
            }
        }else if (name.equals("AC") || name.equals("C")){
            ac_btn.setText(R.string.ac);
            resultbox.setText("0");
        }else{
            String text_in_resultbox = resultbox.getText().toString();
            ac_btn.setText(R.string.cancel);
            if (text_in_resultbox.equals("0")){
                if ("1234567890.".contains(name)) {
                    resultbox.setText(name);
                }
            }else {
                String check_text = text_in_resultbox.substring(1, text_in_resultbox.length());
                if (!((check_text.contains("+") || check_text.contains("/") || check_text.contains("x") || check_text.contains("-")) && ("x/-+".contains(name)))) {
                    if (text_in_resultbox.length() < 14) {
                        resultbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                        resultbox.setText(text_in_resultbox + name);
                    } else if ((text_in_resultbox.length() >= 14) && (text_in_resultbox.length() < 21)) {
                        resultbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                        resultbox.setText(text_in_resultbox + name);
                    } else if ((text_in_resultbox.length() >= 21) && (text_in_resultbox.length() < 29)) {
                        resultbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        resultbox.setText(text_in_resultbox + name);
                    }
                }

            }
        }
    }

    String eval(String input) {
        try {
            double return_value = 0;
            String numbers = "1234567890.";
            String number_str = "";
            String symbol = "";
            ArrayList number_lst = new ArrayList<Double>();
            for (int i = 0; i < input.length(); i++) {
                if (numbers.contains(input.charAt(i) + "")) {
                    number_str += Character.toString(input.charAt(i));
                } else {
                    if ((Character.toString(input.charAt(i)).equals("-")) && (i == 0)) {
                        number_str += Character.toString(input.charAt(i));
                    } else {
                        symbol = Character.toString(input.charAt(i));
                        number_lst.add(Double.parseDouble(number_str));
                        number_str = "";
                    }
                }
            }
            number_lst.add(Double.parseDouble(number_str));
            switch (symbol) {
                case "+":
                    return_value = (((double) number_lst.get(0)) + ((double) number_lst.get(1)));
                    break;
                case "-":
                    return_value = ((double) number_lst.get(0) - (double) number_lst.get(1));
                    break;
                case "x":
                    return_value = ((double) number_lst.get(0) * (double) number_lst.get(1));
                    break;
                case "/":
                    return_value = ((double) number_lst.get(0) / (double) number_lst.get(1));
                    break;
            }
            if (return_value % 1 == 0) {
                return String.valueOf((int) return_value);
            } else {
                return String.valueOf(return_value);
            }
        }catch (ArithmeticException e){
            return "Error";
        }
    }
}
