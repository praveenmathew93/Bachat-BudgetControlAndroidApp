package com.example.Bachat;

public class Category_Recommendation {
    private String category;
    private int iconURL;
    private String transactions;

    public Category_Recommendation(String category,int iconURL,String transactions) {
        this.category = category;
        this.iconURL = iconURL;
        this.transactions = transactions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIconURL() {
        return iconURL;
    }

    public void setIconURL(int iconURL) {
        this.iconURL = iconURL;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }

}