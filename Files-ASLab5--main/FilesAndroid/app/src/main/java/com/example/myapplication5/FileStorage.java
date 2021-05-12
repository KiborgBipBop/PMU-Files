package com.example.myapplication5;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileStorage
{

    private static final String KEY = "MyKey12345678901";
    static String fileName = "storage.txt";
    static String fileNameDecrypted = "storage.json";
    static Gson gson = new Gson();

    public static void write(Context context, GalleryData data)
    {
        String json = gson.toJson(data);
        saveFile(context, json, fileNameDecrypted);

        try
        {
            String encrypted = new AESCrypt(KEY).encrypt(json);
            saveFile(context, encrypted, fileName);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void saveFile(Context context, String data, String fileName)
    {
        try
        {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static GalleryData read(Context context)
    {
        try
        {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);
            }
            String encrypted = sb.toString();

            String decrypted = new AESCrypt(KEY).decrypt(encrypted);
            return gson.fromJson(decrypted, GalleryData.class);
        } catch (FileNotFoundException ex)
        {

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isFilePresent(Context context, String fileName)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }
}
