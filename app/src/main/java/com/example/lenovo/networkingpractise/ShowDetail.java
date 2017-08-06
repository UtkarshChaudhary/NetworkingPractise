package com.example.lenovo.networkingpractise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowDetail extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        textView=(TextView)findViewById(R.id.textView);
        Intent i=getIntent();
        String key=i.getStringExtra("Key");
        textView.setText(key);
    }
}
