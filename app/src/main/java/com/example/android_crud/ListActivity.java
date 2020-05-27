package com.example.android_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;

    // layout manager for recyclerview
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;

    //firestone instance
    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // actionbar and its title
        // ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("list data");


        // init firestone
        db = FirebaseFirestore.getInstance();

        // initialize views
        mRecyclerView = findViewById(R.id.recycler_view);
        mAddBtn = findViewById(R.id.addBtn);

        // set recycler views properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // init progress dialog
        pd = new ProgressDialog(this);

        // show data in recycler view
        showData();

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, MainActivity.class));
                finish();
            }
        });
    }


    private void showData() {

        // set title of progress dialog
        pd.setTitle("Loading data");
        // show progress dialog
        pd.show();

        db.collection("documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();

                        // called when data is retrieverd
                        pd.dismiss();

                        // show data
                        for (DocumentSnapshot doc: task.getResult()){
                            Model model = new Model(doc.getString("id"),
                                    doc.getString("title"));
                            modelList.add(model);
                        }
                        // adapter
                        adapter = new CustomAdapter(ListActivity.this, modelList);

                        // set adapter to recycler view
                        mRecyclerView.setAdapter(adapter);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // called when there is any error while retireving
                        pd.dismiss();

                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void deleteData(int index){
        // set title of progress dialog
        pd.setTitle("Deleting data");
        // show progress dialog
        pd.show();

        db.collection("documents").document(modelList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // called when delete successfully
                        Toast.makeText(ListActivity.this, "deleted", Toast.LENGTH_SHORT).show();

                        // update data
                        showData();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // called when there is any error
                        pd.dismiss();
                        // get and show error message

                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });

    }
}
