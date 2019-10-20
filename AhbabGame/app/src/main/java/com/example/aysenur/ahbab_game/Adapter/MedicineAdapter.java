package com.example.aysenur.ahbab_game.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aysenur.ahbab_game.R;
import com.example.aysenur.ahbab_game.model.Medicine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    List<Medicine> medicineList;
    Context context;
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();;

    public MedicineAdapter(Context context, List<Medicine> medicineList){
        this.medicineList = medicineList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_medicine, viewGroup, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.txtMedicineName.setText(medicineList.get(i).getMedicineName());
        viewHolder.txtMedicineDate.setText(medicineList.get(i).getMedicineDate());
        viewHolder.txtMedicineFrequency.setText(medicineList.get(i).getMedicineFrequency());

        viewHolder.imgEditMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMedicine(medicineList.get(i));
            }
        });

        viewHolder.imgDeleteMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMedicine(medicineList.get(i).getMedicineID());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtMedicineName,txtMedicineDate,txtMedicineFrequency;
        public ImageView imgEditMedicine, imgDeleteMedicine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMedicineName = itemView.findViewById(R.id.txtMedicineName);
            txtMedicineDate = itemView.findViewById(R.id.txtMedicineDate);
            txtMedicineFrequency = itemView.findViewById(R.id.txtMedicineFrequency);

            imgEditMedicine = itemView.findViewById(R.id.imgEditMedicine);
            imgDeleteMedicine = itemView.findViewById(R.id.imgDeleteMedicine);

        }

        @Override
        public void onClick(View v) {
        }

    }

    private Medicine updateMedicine;
    private void updateMedicine(Medicine medicine){

        updateMedicine = medicine;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialogView = inflater.inflate(R.layout.dialog_add_medicine, null );
        dialogBuilder.setView(dialogView);

        final EditText txtAddMedicineName = dialogView.findViewById(R.id.txtAddMedicineName);
        final EditText txtAddMedicineDate = dialogView.findViewById(R.id.txtAddMedicineDate);
        final EditText txtAddMedicineFrequency = dialogView.findViewById(R.id.txtAddMedicineFrequency);

        txtAddMedicineName.setText(updateMedicine.getMedicineName());
        txtAddMedicineDate.setText(updateMedicine.getMedicineDate());
        txtAddMedicineFrequency.setText(updateMedicine.getMedicineFrequency());

        final AlertDialog b = dialogBuilder.create();
        b.show();

        RelativeLayout diaAddMedicine = dialogView.findViewById(R.id.diaAddMedicine);
        diaAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtAddMedicineName.getText().length() > 0 ){
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("medicine").child(userID).child(updateMedicine.getMedicineID());
                    Medicine m = new Medicine(updateMedicine.getMedicineID(), txtAddMedicineName.getText().toString(), txtAddMedicineDate.getText().toString(),
                            txtAddMedicineFrequency.getText().toString());
                    dR.setValue(m);
                    b.dismiss();
                    Toast.makeText(context, "Drug Updated.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteMedicine(String medicineID) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("medicine").child(userID).child(medicineID);
        dR.removeValue();
        Toast.makeText(context, "Drug Deleted.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }


}
