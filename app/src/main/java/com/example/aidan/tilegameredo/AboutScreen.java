package com.example.aidan.tilegameredo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class AboutScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutscreen);
        TextView tv=(TextView)findViewById(R.id.textView);
        tv.setText(Html.fromHtml(getString(R.string.aboutText)));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void back(View view){
        Intent i = new Intent(this,HomeScreen.class);
        this.startActivity(i);
    }
}
