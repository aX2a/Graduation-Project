package com.example.aysenur.ahbab_game.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aysenur.ahbab_game.Adapter.MedicineAdapter;
import com.example.aysenur.ahbab_game.MainActivity;
import com.example.aysenur.ahbab_game.R;
import com.example.aysenur.ahbab_game.model.Medicine;
import com.example.aysenur.ahbab_game.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfilePageFragment extends Fragment {

    private DatabaseReference databaseReferenceMedicines, databaseReferencePatient;
    FirebaseAuth auth;
    MedicineAdapter medicineAdapter;

    View generalView;
    TextView txtPatientName, txtPatientAge, txtPatientPhone, txtRelativeName, txtRelativePhone;
    RecyclerView rvMedicineList;
    RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout relAddMedicine;
    private Medicine medicine;
    private String userID;

    List<Medicine> listMedicine = new ArrayList<>();
    List<User> listPatient = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_page, container, false);

        generalView = rootView;

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        txtPatientName = generalView.findViewById(R.id.txtPatientName);
        txtPatientAge = generalView.findViewById(R.id.txtPatientAge);
        txtPatientPhone = generalView.findViewById(R.id.txtPatientPhone);
        txtRelativeName = generalView.findViewById(R.id.txtRelativeName);
        txtRelativePhone = generalView.findViewById(R.id.txtRelativePhone);

        rvMedicineList = generalView.findViewById(R.id.rvMedicineList);
        relAddMedicine = generalView.findViewById(R.id.relAddMedicine);

        FirebaseDatabase mFirebaseInstance1 = FirebaseDatabase.getInstance();
        databaseReferencePatient = mFirebaseInstance1.getReference("user");

        databaseReferencePatient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPatient.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User u = postSnapshot.getValue(User.class);
                    listPatient.add(u);
                    Log.i("Id ", "userid1: "+u.getUserID());
                }

                for (int i=0; i<listPatient.size(); i++){
                    if(listPatient.get(i).getUserID().equals(userID)){
                        Log.i("Id ", "userid: "+listPatient.get(i).getUserID());
                        txtPatientName.setText(listPatient.get(i).getName());
                        txtPatientAge.setText(listPatient.get(i).getDate());
                        txtPatientPhone.setText(listPatient.get(i).getPhoneNum());
                        txtRelativeName.setText(listPatient.get(i).getRelativeName());
                        txtRelativePhone.setText(listPatient.get(i).getRelativePhoneNum());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        relAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMedicine();
            }
        });

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        databaseReferenceMedicines = mFirebaseInstance.getReference("medicine").child(userID);

        databaseReferenceMedicines.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMedicine.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Medicine m = postSnapshot.getValue(Medicine.class);
                    listMedicine.add(m);
                }

                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rvMedicineList.setLayoutManager(mLayoutManager);

                medicineAdapter = new MedicineAdapter(getContext(), listMedicine);
                rvMedicineList.setAdapter(medicineAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        container.removeAllViews();
        return rootView;
    }

    private void addMedicine() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialogView = inflater.inflate(R.layout.dialog_add_medicine, null );
        dialogBuilder.setView(dialogView);

        final EditText txtAddMedicineName = dialogView.findViewById(R.id.txtAddMedicineName);
        final EditText txtAddMedicineDate = dialogView.findViewById(R.id.txtAddMedicineDate);
        final EditText txtAddMedicineFrequency = dialogView.findViewById(R.id.txtAddMedicineFrequency);


        final AlertDialog a = dialogBuilder.create();
        a.show();

        RelativeLayout diaAddMedicine = dialogView.findViewById(R.id.diaAddMedicine);

        diaAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
                DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("medicine").child(userID);

                String medicineID = null;
                if (TextUtils.isEmpty(medicineID)) {
                    medicineID = mFirebaseDatabase.push().getKey();
                }

                medicine = new Medicine(medicineID, txtAddMedicineName.getText().toString(), txtAddMedicineDate.getText().toString(),
                        txtAddMedicineFrequency.getText().toString());

                mFirebaseDatabase.child(medicineID).setValue(medicine);

                a.dismiss();
            }
        });
    }

}
