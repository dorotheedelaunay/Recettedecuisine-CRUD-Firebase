package com.example.android_crud;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    ImageButton androidImageButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        androidImageButton = (ImageButton) findViewById(R.id.Button_recetteLasagne);

        androidImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(HomePage.this, "it works", Toast.LENGTH_SHORT).show();

                openListActivity();

            }
        });
    }

    public void openListActivity(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
