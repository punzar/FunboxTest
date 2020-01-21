package com.punzar.funboxtest;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public List<SmartPhone> readCsv(Context context) throws IOException {

        List<SmartPhone> list = new ArrayList<>();
        InputStream is = context.getAssets().open("data.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                Charset.forName("UTF-8")));
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(", ");
            double price = Double.parseDouble(tokens[1].substring(1, tokens[1].length() - 1));
            int count = Integer.parseInt(tokens[2].substring(1, tokens[2].length() - 1));
            list.add(new SmartPhone(tokens[0].substring(1, tokens[0].length() - 1), price, count));
        }
        return list;
    }
}
