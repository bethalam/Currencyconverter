 package com.example.dineshvarma.currencyconverter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int flags[]={R.drawable.currency_aud, R.drawable.currency_bgn, R.drawable.currency_brl, R.drawable.currency_cad, R.drawable.currency_chf,
            R.drawable.currency_cny, R.drawable.currency_czk, R.drawable.currency_dkk,R.drawable.currency_eur, R.drawable.currency_gbp, R.drawable.currency_hkd, R.drawable.currency_hrk,
            R.drawable.currency_huf, R.drawable.currency_idr, R.drawable.currency_ils, R.drawable.currency_inr, R.drawable.currency_jpy, R.drawable.currency_krw,
            R.drawable.currency_mxn, R.drawable.currency_myr, R.drawable.currency_nok, R.drawable.currency_nzd, R.drawable.currency_php, R.drawable.currency_pln,
            R.drawable.currency_ron, R.drawable.currency_rub, R.drawable.currency_sek, R.drawable.currency_sgd, R.drawable.currency_thb,R.drawable.currency_try,
            R.drawable.currency_usd, R.drawable.zar};

    String[] names = {"Australian dollar (AUD)", "Bulgarian lev (BGN)","Brazilian real (BRL)", "Canadian dollar (CAD)", "Swiss franc (CHF)",
            "Chinese yuan (CNY)", "Czech koruna (CZK)", "changeDanish krone (DKK)", "Euro (EUR)", "British pound (GBP)", "Hong Kong dollar (HKD)", "Croatian kuna (HRK)",
            "Hungarian forint (HUF)", "Indonesian rupiah (IDR)", "Israeli new shekel (ILS)", "Indian rupee (INR)", "apanese yen (JPY)", "South Korean won (KRW)",
            "Mexican peso (MXN)", "Malaysian ringgit (MYR)", "Norwegian krone (NOK)", "New Zealand dollar (NZD)", "Philippine peso (PHP)","Polish złoty (PLN)",
            "Romanian leu (RON)", "Russian ruble (RUB)", "Swedish krona (SEK)", "Singapore dollar (SGD)", "Thai baht (THB)", "Turkish lira (TRY)", "United States dollar (USD)",
            "South African rand (ZAR)"};
    String[] codes = {"AUD","BGN","BRL","CAD","CHF","CNY","CZK","DKK","EUR","GBP","HKD","HRK","HUF","IDR","ILS","INR",
            "JPY","KRW","MXN","MYR","NOK","NZD","PHP","PLN","RON","RUB","SEK","SGD","THB","TRY","USD","ZAR"};
    LayoutInflater inflter;
     CustomAdapter(Context c){
        context = c;
         inflter = (LayoutInflater.from(c));
    }
    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView txt = new TextView(context);
        txt.setTextSize(24);
        txt.setText(codes[i]);
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
    @RequiresApi (api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon =  convertView.findViewById(R.id.currency_imageView);
        TextView name = convertView.findViewById(R.id.currency_textView);
        icon.setImageResource(flags[position]);
        name.setText(names[position]);
        return convertView;

    }
}
