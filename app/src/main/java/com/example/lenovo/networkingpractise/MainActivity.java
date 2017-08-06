package com.example.lenovo.networkingpractise;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements onDownloadCompleteListener {
   ArrayList<Container> containers;
    ListView containerListView;
    ArrayList<String> title;
    ContainerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containers=new ArrayList<>();
        containerListView=(ListView)findViewById(R.id.containerListView);
        title=new ArrayList<>();
        adapter=new ContainerAdapter(this,containers);
   containerListView.setAdapter(adapter);
        fetchContainer();
        containerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(MainActivity.this,ShowDetail.class);
                i.putExtra("Key",containers.get(position).body);
                startActivity(i);
            }
        });
    }

    private void fetchContainer() {
        String urlString="https://jsonplaceholder.typicode.com/posts";
        ContainerAsyncTask coursesAsynTask=new ContainerAsyncTask();
        coursesAsynTask.execute(urlString);//execute func. starts another thread
        coursesAsynTask.setOnDownloadCompleteListener(this);
    }
    public  void onDownloadComplete(ArrayList<Container> courseList){
        containers.clear();
        containers.addAll(courseList);
        for(int i=0;i<containers.size();i++){
      //      String str=Integer.toString(containers.get(i).id);   for showing id
            title.add(containers.get(i).title);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.searchViaId){
            AlertDialog.Builder mbuilder=new AlertDialog.Builder(this);
            mbuilder.setTitle("Enter ID");
            View v=getLayoutInflater().inflate(R.layout.dialog_view,null);
           final EditText editText=(EditText)v.findViewById(R.id.dialogViewEditText);
            mbuilder.setView(v);
            mbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String id=editText.getText().toString();
                  String urlString="https://jsonplaceholder.typicode.com/posts?userId="+id;
                    ContainerAsyncTask containerAsyncTask=new ContainerAsyncTask();
                    containerAsyncTask.execute(urlString);
                    containerAsyncTask.setOnDownloadCompleteListener(MainActivity.this);
                }
            });
            mbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            mbuilder.create().show();
        }
        return true;
    }
}

