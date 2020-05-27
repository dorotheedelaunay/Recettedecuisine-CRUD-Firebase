package com.example.android_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // VIEws
    EditText mTitleEt ;
    Button mSaveBtn;

    //progress dialog
    ProgressDialog pd;

    //Firestore instance
    FirebaseFirestore db ;

    String pId, pTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // actionbar and its title





        // initialize views with its xml
        mTitleEt = findViewById(R.id.titleEt);
        mSaveBtn = findViewById(R.id.saveButton);
        // mListBtn = findViewById(R.id.listButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            // update data
            // actionBar.setTitle("Update");
            mSaveBtn.setText("Update");

            // get data
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");

            //set data
            mTitleEt.setText(pTitle);


        }
        else{
            // New data
            // actionBar.setTitle("Add Data");
            mSaveBtn.setText("Save");


        }

        //progress dialog
        pd = new ProgressDialog(this);

        // firestore
        db = FirebaseFirestore.getInstance();

        // click button to upload data
        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                finish();

                Bundle bundle = getIntent().getExtras();
                if (bundle != null){
                    // updating
                    //input data
                    String id = pId;
                    String title = mTitleEt.getText().toString().trim();

                    // function call to update data
                    updateData(id, title);




                }
                else{
                    // adding new
                    //input data
                    String title = mTitleEt.getText().toString().trim();

                    //function call to upload data
                    uploadData(title);

                }

            }

        });


    }

    private void updateData(String id, String title) {
        pd.setTitle("Update data ...");
        //show progress bar when user click save button
        pd.show();

        db.collection("documents").document(id)
                .update("title", title)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // called when update successfully
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "updated", Toast.LENGTH_SHORT).show();




                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // called when there is any error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void uploadData(String title) {
        pd.setTitle("Adding data to Firebase");
        //show progress bar when user click save button
        pd.show();
        //random if dot each data to be stored
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("title", title);

        // add this data
        db.collection("documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called when data is added successfully

                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "uploaded..", Toast.LENGTH_SHORT);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called if there is any eror while uploading

                        pd.dismiss();
                        //Get and show error message
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT);

                    }
                });

    }
}
