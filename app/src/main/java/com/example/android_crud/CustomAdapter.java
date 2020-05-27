package com.example.android_crud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder>{

    ListActivity listActivity;
    List<Model> modelList;
    Context context;

    public CustomAdapter(ListActivity listActivity, List<Model> modelList){
        this.listActivity = listActivity;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        // handle item click here

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // this will be called when user click item


                // show data in toast clicking
                String title = modelList.get(position).getTile();

            }

            @Override
            public void onItemLongClick(View view, final int position) {
                // this will be called when user long click item


                // creating alertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);
                //options to display in dialog
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            // update is clicked
                            //get data
                            String id = modelList.get(position).getId();
                            String title = modelList.get(position).getTile();



                            //intent to start activity
                            Intent intent = new Intent(listActivity, MainActivity.class);
                            // put data in intent
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);

                            // start activity
                            listActivity.startActivity(intent);

                        }
                        if (which == 1){
                            // delete is clicked
                            listActivity.deleteData(position);

                        }

                    }
                }).create().show();
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // bond views / set data
        viewHolder.mTitleTv.setText(modelList.get(i).getTile());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
