package com.example.filertester;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    Button btnClick,btnClick1;
    File file;
    MyFile myFile;

    public void initialValues(){
        String FEATURE_FILE_PATH = Environment.getExternalStorageDirectory() + "/Record/"+"Save";
        File filestring = new File(FEATURE_FILE_PATH);
        if (filestring.exists()) {
        } else {
            filestring.mkdirs();
        }
        try {
            file = new File("/mnt/sdcard/Record/Save/save.txt");
            myFile = new MyFile("/mnt/sdcard/Record/Save/save.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFile4(String str) throws IOException {
        File fout = new File("/mnt/sdcard/Record/Save/save.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        OutputStreamWriter osw = new OutputStreamWriter(fos);


        osw.write(str);
        osw.flush();


        osw.close();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            MyFile mf = new MyFile("/mnt/sdcard/Record/Save/save.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        btnClick1 = findViewById(R.id.button);
        btnClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageToastEvent("birinci tiklandi!!!")); // saver!
            }
        });
        btnClick = findViewById(R.id.button2);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call eventBus when click button
                initialValues();
//                try {
//                    myFile.Write("wifi: false, globalPath: save_path");
//                    myFile.Close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                try {
                    writeFile4("wifi: false, globalPath: save_path");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //EventBus.getDefault().post(new Saver(file.getAbsolutePath(), file));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventToast(MessageToastEvent messageToastEvent) {
        Toast.makeText(MainActivity.this, messageToastEvent.message, Toast.LENGTH_LONG).show();
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void eventToast(String wdwa) {

        Toast.makeText(MainActivity.this, wdwa, Toast.LENGTH_LONG).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSaver(Saver saver) {
        saver.ReaderLineByLine(file);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    class MyFile {
        FileOutputStream fout;
        String fName = "";

        public MyFile(String fileName) throws FileNotFoundException {
            this.fName = fileName;
            fout = new FileOutputStream(fileName, false);
        }

        public void Write(String str) throws IOException {
            byte[] bytes = str.getBytes();
            fout.write(bytes);
        }

        public void Close() throws IOException {
            fout.close();
            fout.flush();
        }
    }
}