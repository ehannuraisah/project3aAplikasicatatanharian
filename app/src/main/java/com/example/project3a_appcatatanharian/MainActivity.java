package com.example.project3a_appcatatanharian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    public static final int REQUEST_CODE_STORAGE =100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Aplikasi Catatan Harian");
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.ListView);
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(packageContext:
                MainActivity.this, insertAndViewActivity.class);
                Map<String, Object> data = (Map<String, Object>) parent.getAdapter().getItem(position);
                intent.putEkstra(name:"filename", data.get("name").toString());
                Toast.makeText(context:MainActivity.this, text:
                "You clicked" + data.get("name"), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?>parent, View view, int position, long I){
                Map<String, object> data = (Map<String. Object>) parent.getAdapter().getItem(position);
                tampilkanDialogKonfirmasiHapusCatatan(data.get("name").toString());
                return true;
            }
        });
    }
@Override
protected void onResume(){
    super.onResume();
    if (Build.VERSION.SDK_INT>=23){
      if (periksaIzinPenyimpanan(){
      mengambilListFilePadaFolder();}
    }
}else {
        mengambilListPadaFolder();
    }
}
public boolean periksaIzinPenyimpanan(){
    if(Build.VERSION.SDK_INT>=23){
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            return true;
            ActivityCompat.requestPermissions(activity:this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_STORAGE),
            return false;
        }
    }else{
        return true;
    }
}}
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[]permissions, @NonNull int[]grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch(requestcode){
        case REQUEST_CODE_STORAGE;
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
        mengambilListFilePadaFolder();
        }
        break;

        }
        }
        void mengambilListFilePadaFolder(){
    String path = getEksternalFileDir(type: null)+ "/catatan";
    File directory = new File(path);

    if (directory.exists()){
        File[] files = directory.listfiles();
        String[] filesnames = new String[files.lenght];
        String[] dateCreated =new String[files.lenght];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat (pattern:"add MMM YYYY :mm:ss");
        ArrayList<map<String, Object>> itemDataList = new ArrayList<Map<String,Object>>();

        for (int i = 0; i < files.lenght; i++){
            filenames[i] = files[i].getName();
            Date lastModDate = new Date(files[i].lastmodified()); //importjava.until

            Map<String, Object> listItemMap = new HashMap<>();
            listItemMap.put("name", filesnames[i]);
            listItemMap.put("date", dateCreated[i]);
            itemDataList.add(listItemMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter( context: this, itemDataList, android.R.layout.simple_list_item_2,
                     new String[]{"name", "date"}, new int []{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        }
        }

    @Override
public boolean onOptionsItemSelected(@NonNull MenuItem item){
    switch (item.getItemId()){
        case R.id.action_tambah :
            Intent i = new Intent( packageContext: this, InsertAndViewAcivity.class);
            startActivity(i);
            break;
        }

        return super.onOptionsItemSelected(item);
        }

        void tampilkanDialogKonfirmasiHapusCatatan(final String filename)
        {
            new AlertDialog.Builder( context: this).setTitle("Hapus Catatan ini?")
            .setMessage("Apakah Anda yakin ingin menghapus catatan" +filename+"?")
            .setIcon(android.R.drawble.ic_dialog_alert)
            .setPositiveButton(android.R.string.yess, new DialogInterface.OnclickListener(){
                public void onClick(DialogInterface dialog, int whichButton){
                    hspusFile(filename);
        }
        }).setNegativeButton(android.R.string.no, listener: null).show();
        }
        void hapusfile(String filename){
    string path = getExternalFilesDir(type: null)+"/catatan";
    File file = new File(path, filename);
    if (file.exists()){
        file.delete();
        }
    mengambilListFIlePadaFolder();
        }
        }