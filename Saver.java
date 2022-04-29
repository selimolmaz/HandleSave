package com.example.filertester;


import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Saver {
    public Saver(String path, File saveFile) {
        Path = path;
        this.saveFile = saveFile;
        if (!saveFile.exists()){
            fileMaker();
        }
        //reader line by line
    }

    protected final String Path;
    protected File saveFile;






    public void fileMaker() {

        String FEATURE_FILE_PATH = Environment.getExternalStorageDirectory() + "/Record/"+"Save";
        File filestring = new File(FEATURE_FILE_PATH);
        if (filestring.exists()) {
        } else {
            filestring.mkdirs();
        }
        try {
            saveFile = new File("/mnt/sdcard/Record/Save/save.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReaderLineByLine(File file) {
        if (pathChecker(file.getAbsolutePath())) {
            File inputFile = new File("/mnt/sdcard/Record/Save/save.txt");
            File tempFile = new File("/mnt/sdcard/Record/Save/save.txt");


            BufferedReader reader;
            BufferedWriter writer;
            try {
                reader = new BufferedReader(new FileReader(inputFile));
                writer = new BufferedWriter(new FileWriter(tempFile));
                String lineToRemove = "this part done | ￣︶￣|o";

                String line = reader.readLine();
                while (line != null) {
                    Log.d("SAVE_TEXT", line);
                    line = reader.readLine();
                    if (line == "wifi: false, globalPath: save_path") {
                        //save_path = previous save_path
                        //post MyFiles(previous savepath)
                        String trimmedLine = line.trim();

                        if (trimmedLine.equals(lineToRemove)) continue;
                        //tekrar kontrol et!!!!!!

                        writer.write(line + System.getProperty("line.separator"));

                    }
                    writer.close();
                    reader.close();

                    boolean successful = tempFile.renameTo(inputFile);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    final String paTH = "/mnt/sdcard/Record/Save/save.txt";
    public boolean pathChecker(String path){

        if (this.paTH.equals(path)){
            return true;
        }
        else return false;
    }





}
