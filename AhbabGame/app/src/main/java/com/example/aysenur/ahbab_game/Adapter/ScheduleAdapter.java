package com.example.aysenur.ahbab_game.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aysenur.ahbab_game.R;
import com.example.aysenur.ahbab_game.Schedule.AddNote;
import com.example.aysenur.ahbab_game.model.Schedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    List<Schedule> scheduleList;
    Context context;

    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public ScheduleAdapter(List<Schedule> scheduleList, Context context) {
        this.scheduleList = scheduleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_todo, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.txtDate.setText(scheduleList.get(i).getDate());
        viewHolder.txtTime.setText(scheduleList.get(i).getTime());
        viewHolder.txtNote.setText(scheduleList.get(i).getNote());

        viewHolder.todoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(scheduleList.get(i));
            }
        });

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to delete?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                delete(scheduleList.get(i).getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtDate, txtNote, txtTime;
        public ImageView imgDelete;

        public LinearLayout todoList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtNote = itemView.findViewById(R.id.txtNote);
            txtTime = itemView.findViewById(R.id.txtTime);

            imgDelete = itemView.findViewById(R.id.imgDelete);

            todoList = itemView.findViewById(R.id.todoList);
        }

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public int getItemCount() { return scheduleList.size(); }

    private void update(Schedule schedule) {

        Intent intent = new Intent(context, AddNote.class);
        intent.putExtra("Schedule", schedule);
        context.startActivity(intent);

    }

    private void delete(String ID) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("schedule").child(userID).child(ID);
        dR.removeValue();

        Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();

    }

}
