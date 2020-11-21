package com.example.Bachat;

public class EarningListItem {
    private String Id;
    private String Mode;
    private String ModeOfPayment;
    private String Amount;
    private String Date;
    private String Note;
    private String Currency;
    private String Name;
    private String Phone;
    private String Email;

    public EarningListItem( String id,String mode, String modeOfPayment, String amount, String date, String note, String curr, String name) {

        Id = id;
        Mode = mode;
        Amount = amount;
        ModeOfPayment = modeOfPayment;
        Date = date;
        Note= note;
        Currency = curr;
        Name = name;
    }

    public EarningListItem(String id, String category, String modeofpayment, String amount, String date, String note, String curr, String name, String phone, String email) {
        Id = id;
        Mode = category;
        ModeOfPayment = modeofpayment;
        Amount = amount;
        Date = date;
        Note = note;
        Currency = curr;
        Name = name;
        Phone = phone;
        Email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getModeOfPayment() {
        return ModeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        ModeOfPayment = modeOfPayment;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) { Note = note; }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String curr) { Currency = curr; }

    public String getContactName() {
        return Name;
    }

    public void setContactName(String name) { Name = name; }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) { Phone = phone; }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) { Email = email; }
}
