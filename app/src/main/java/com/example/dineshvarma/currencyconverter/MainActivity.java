package com.example.dineshvarma.currencyconverter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView value1, value2;
    Vibrator vibrator;
    double base=1.0, target,n=1,result1;
    String[] names = {"Australian dollar", "Bulgarian lev","Brazilian real", "Canadian dollar", "Swiss franc",
            "Chinese yuan", "Czech koruna", "changeDanish krone", "Euro", "British pound", "Hong Kong dollar", "Croatian kuna",
            "Hungarian forint", "Indonesian rupiah", "Israeli new shekel", "Indian rupee", "apanese yen", "South Korean won",
            "Mexican peso", "Malaysian ringgit", "Norwegian krone", "New Zealand dollar", "Philippine peso","Polish złoty",
            "Romanian leu", "Russian ruble", "Swedish krona", "Singapore dollar", "Thai baht", "Turkish lira", "US dollar",
            "South African rand"};
    String[] codes = {"AUD","BGN","BRL","CAD","CHF","CNY","CZK","DKK","EUR","GBP","HKD","HRK","HUF","IDR","ILS","INR",
            "JPY","KRW","MXN","MYR","NOK","NZD","PHP","PLN","RON","RUB","SEK","SGD","THB","TRY","USD","ZAR"};
    boolean state1, num1, dot1, state2, num2, dot2, v1=true;
    String baseCode="USD",targetCode="INR", result="", name, date, rate;
    JSONObject jsonObject = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        value1 = (TextView) findViewById(R.id.value1);
        value2 = (TextView) findViewById(R.id.value2);
        spinner1.setAdapter(new CustomAdapter(this));
        spinner2.setAdapter(new CustomAdapter(this));

        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vibrator.vibrate(50);
                TextView textView = (TextView) findViewById(R.id.name1);
                textView.setText(names[i]);
                baseCode = codes[i];
                result();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 vibrator.vibrate(50);
                TextView textView = (TextView) findViewById(R.id.name2);
                textView.setText(names[i]);
                targetCode = codes[i];
                result();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner1.setSelection(30);
        spinner2.setSelection(15);
        DownloadTask task=new DownloadTask();
        task.execute("http://api.fixer.io/latest?base=USD");
        Thread thread = new Thread();
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            jsonObject = new JSONObject(result);
            date = jsonObject.getString("date");
            TextView textView = (TextView) findViewById(R.id.date);
            textView.setText("Exchange rates are provided by fixer.io (" + date + ")");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        result();

     }

    public void acclick(View view) {
      ac();

    }

    public void backspace(View view) {
        vibrator.vibrate(50);
        if (v1){
            String text = value1.getText().toString();
            if (text.length()>0){
                char check = text.charAt(text.length()-1);
                if (check=='.'){
                    dot1 = false;
                }
                text = text.substring(0, text.length()-1);
                value1.setText(text);
            }if (text.length()==0){
                value1.setText("0");
                state1 = false;
                dot1 = false;
                num1 = false;
            }
        }else {
            String text = value2.getText().toString();
            if (text.length()>0){
                char check = text.charAt(text.length()-1);
                if (check=='.'){
                    dot2 = false;
                }
                text = text.substring(0, text.length()-1);
                value2.setText(text);
            }if (text.length()==0){
                value2.setText("0");
                state2 = false;
                num2 = false;
                dot2 = false;
            }

        }
        result();

    }

    public void numclick(View view) {
        vibrator.vibrate(50);

        if (v1) {
            if (value1.length() < 13) {
                if (state1||dot1) {
                    value1.append(view.getTag().toString());
                } else {
                    value1.setText(view.getTag().toString());
                }
                state1 = true;
                num1 = true;
            }
        }else {
            if (value2.length() < 13) {
                if (state2||dot2) {
                    value2.append(view.getTag().toString());
                } else {
                    value2.setText(view.getTag().toString());

                }
                state2 = true;
                num2 = true;
            }
        }
        result();
    }

    public void dotclick(View view) {
        vibrator.vibrate(50);

        if (v1) {
            if (!dot1) {
                value1.append(".");
            }
            dot1 = true;
            num1 = false;
        }else {
            if(!dot2){
                value2.append(".");
            }
            dot2 = true;
            num2 = false;
        }

    }

    public void ans1click(View view) {
        vibrator.vibrate(50);
        v1 = true;
        value1.setTextColor(Color.parseColor("#FF5722"));
        value2.setTextColor(Color.parseColor("#000000"));
        ac();

    }

    public void ans2click(View view) {
        vibrator.vibrate(50);
        v1 = false;
        value2.setTextColor(Color.parseColor("#FF5722"));
        value1.setTextColor(Color.parseColor("#000000"));
        ac();
    }
    public void result() {
        try {
            jsonObject = new JSONObject(result);
            name = jsonObject.getString("rates");
            jsonObject = new JSONObject(name);
            if (baseCode=="USD"){
                base = 1;
            }else {
                rate = jsonObject.getString(baseCode);
                base = Double.valueOf(rate);
            }
             if (targetCode=="USD"){
                 target = 1;
             }else {
                 String rate1 = jsonObject.getString(targetCode);
                 target = Double.valueOf(rate1);
             }

        if (v1) {
            if (value1 != null && value1.length() != 0) {
                n = Double.valueOf(value1.getText().toString());
                result1 = target / base * n;
                value2.setText(new DecimalFormat("##.######").format(result1));

            } else {
                value2.setText("");
            }
        }else {
            if (value2 != null && value2.length() != 0) {
                n = Double.valueOf(value2.getText().toString());
                result1 = base/target * n;
                value1.setText(new DecimalFormat("##.######").format(result1));

            } else {
                value1.setText("");
            }

        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
public void ac(){
    vibrator.vibrate(50);
    if (v1){
        value1.setText("0");
        state1 = false;
        dot1 = false;
        num1 = false;

    }else {
        value2.setText("0");
        state2 = false;
        dot2 = false;
        num2 = false;

    }
    result();
}

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


    }
    }

