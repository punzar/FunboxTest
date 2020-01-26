package com.punzar.funboxtest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SaveLoadAdapter {
    private final static String FILE_NAME = "myfile";

    public static void save(Context context, List<SmartPhone> list) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        fos.write(json.getBytes());
        fos.close();

    }

    public static List<SmartPhone> load(Context context) throws IOException {
        File file = context.getFileStreamPath(FILE_NAME);
        if (file.exists()) {
            FileInputStream fis = context.openFileInput(FILE_NAME);


            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }

            String contents = stringBuilder.toString();
            Gson gson = new Gson();
            Type typeOfListSmartPhones = new TypeToken<List<SmartPhone>>() {
            }.getType();
            List<SmartPhone> list = gson.fromJson(contents, typeOfListSmartPhones);

            reader.close();
            inputStreamReader.close();
            fis.close();
            return list;
        }
        return null;
    }
}
