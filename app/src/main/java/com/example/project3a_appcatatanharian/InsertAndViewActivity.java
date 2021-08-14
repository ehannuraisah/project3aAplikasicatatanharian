package com.example.project3a_appcatatanharian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

public class InsertAndViewActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_STORAGE =100;
    int eventID = 0;
    EditText editFileName, editContent;
    Button btnSimpan;
    String fileName ="", tempCatatan = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_and_view);

        editFileName = findViewById(R.id.editFilename);
        editContent = findViewById(R.id.editContent);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (Extras !=null) {
            getSupportActionBar().setTitle("Ubah Catatan");
            fileName = extras.getString( "filename");
            editFileName.setText(fileName);
            editFileName.setEnabled(false);
        }else{
            getSupportActionBar().setTitle("Tambah Catatan");
        }
        eventID = 1;
        if (Build.VERSION.SDK_INT >23) {
            if(periksaIzinPenyimpanan()){
                bacaFile();
            }
        }else {
            bacaFile();
        }
    }
}
@Override
public void onClick(View v) {
    switch (v.getId()) {
        case R.id.btnSimpan:
            eventID = 2;
            if (!tempCatatan.equals(editContent.getText().toString())) {
                if (periksaIzinPenyimpanan()) {
                    tampilkanDialogKonfirmasiPenyimpanan();
                }
            } else {
                tampilkanDialogKonfirmasiPenyimpanan();
            }
    }else{
        Toast.makeText(InsertAndViewActivity.this,
        "tidak ada perubahan yang dilakukan", Toast.LENGTH_SHORT).show();
    }
    break;
}

public boolean periksaIzinPenyimpanan(){
    if (Build.VERSION.SDK_INT >23){
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            AcivityCompat.requestPermissions(activity: this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
            REQUEST_CODE_STORAGE);
            return false;
        }
    } else {
        return true;
    }
        }

        @Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode){
        case REQUEST_CODE_STORAGE:
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (eventID == 1) {
                    bacaFile();
                } else {
                    tampilkanDialogKonfirmasiPenyimpanan();
                }
            }
            break;
    }
        }
        void bacaFile(){
    String path = getExternalFilesDir( null)+ "/catatan";
    File file = new File(path,editFileName.getText().toString());
    if (file.exists()) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String Line = br.readLine();
            while (line !=null){
                text.append(line);
                Line = br.readLine();
            }
            br.close():
            catch (IOException e){
                system.out.println("Eror"+ e.getMessage());
            }
            tempCatatan = text.toString();
            editContent.setText(text.toString());
        }
    }
    void buatDanUbah(){
    String state = Enviroment.getExternalStorageState();
    if (!Environment.MEDIA_MOUNTED.equals(state)){
    return:
    }
    String path = getExternalFilesDir(null) + "/catatan";
    File parent = new File(path);
    if (parent.exists()){
        File file = new File(path, editFileName.getText().toString());
        FileOutputStream outputStream = null;
        try{
            File.createNewFile();
            outputStream = new FileOutputStream(file);
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            streamWriter.append(editContent.getText());
            streamWriter.flush();
            streamWriter.close();
            streamWriter.flush();
            streamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }else {
        try {
            Log.i("PATH", "CTEARE DIR");

            parent.mkdirs();
            File file = new File(path, editFileName.getText().toString());
            FileOutputStream outputStream = null;
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(editContent.getText().toString().getBytes());
            outputStream.flush();
            outputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    this.finish();
            }
    void tampilkanDialogKonfirmasiPenyimpanan() {
        new AlertDialog.Builder(this)
                .setTitle("Simpan Catatan")
                .setMessage("Apakah Anda yakin ingin menyimpan Catatan ini?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        buatDanUbah();
                    }
                }) .setNegativeButton(android.R.string.no, null).show();
            }
    @Override
            public  void onBackPressed() {
        if (!tempCatatan.equals(editContent.getText().toString())) {
            tampilkanDialogKonfirmasiPenyimpanan();
        }
        super.onBackPressed();
            }
    @Override
            public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home){
        }
        return super.onOptionsItemSelected(item);
            }
        }
