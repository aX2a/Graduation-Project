package com.example.aysenur.ahbab_game.model;

public class Medicine {

    private String medicineID;
    private String medicineName;
    private String medicineDate;
    private String medicineFrequency;
    private String medicineContinuity;

    public Medicine(){ }

    public Medicine(String medicineID, String medicineName, String medicineDate, String medicineFrequency) {
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.medicineDate = medicineDate;
        this.medicineFrequency = medicineFrequency;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineDate() {
        return medicineDate;
    }

    public void setMedicineDate(String medicineDate) {
        this.medicineDate = medicineDate;
    }

    public String getMedicineFrequency() {
        return medicineFrequency;
    }

    public void setMedicineFrequency(String medicineFrequency) {
        this.medicineFrequency = medicineFrequency;
    }

    public String isMedicineContinuity() {
        return medicineContinuity;
    }

    public void setMedicineContinuity(String medicineContinuity) {
        this.medicineContinuity = medicineContinuity;
    }
}
