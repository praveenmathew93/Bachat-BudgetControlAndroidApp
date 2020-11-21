package com.example.Bachat;

public class ListItem {
    private String Id;
    private String Category;
    private String Subcategory;
    private String Modeofpayment;
    private String Amount;
    private String Date;
    private String Note;
    private String Currency;
    private String Name;
    private String Phone;
    private String Email;

    public ListItem(String id, String category, String subcategory, String modeofpayment, String amount, String date, String note, String curr, String name, String phone, String email) {
        Id = id;
        Category = category;
        Subcategory = subcategory;
        Modeofpayment = modeofpayment;
        Amount = amount;
        Date = date;
        Note = note;
        Currency = curr;
        Name = name;
        Phone = phone;
        Email = email;
    }
    public ListItem(String id, String category, String subcategory, String modeofpayment, String amount, String date, String note, String curr, String name) {
        Id = id;
        Category = category;
        Subcategory = subcategory;
        Modeofpayment = modeofpayment;
        Amount = amount;
        Date = date;
        Note = note;
        Currency = curr;
        Name = name;
    }

    public ListItem(String id, String category, String subcategory, String modeofpayment, String amount, String date) {
        Id = id;
        Category = category;
        Subcategory = subcategory;
        Modeofpayment = modeofpayment;
        Amount = amount;
        Date = date;
    }
    public ListItem( String category, String amount) {
        Id = null;
        Category = category;
        Subcategory = null;
        Amount = amount;
        Date = null;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSubcategory() {
        return Subcategory;
    }

    public void setSubcategory(String subcategory) {
        Subcategory = subcategory;
    }

    public String getModeofpayment() {
        return Modeofpayment;
    }

    public void setModeofpayment(String modeofpayment) {
        Modeofpayment = modeofpayment;
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

    public void setNote(String note) {
        Date = note;
    }

    public String getCurrency() { return Currency; }

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
