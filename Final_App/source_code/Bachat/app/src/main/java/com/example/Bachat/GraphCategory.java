package com.example.Bachat;

public class GraphCategory {
    private String categoryName;
    private int iconURL;
    private String percentage;
    private String transactions;


    public GraphCategory(String categoryName,int iconURL,String percentage,String transactions) {
        this.categoryName = categoryName;
        this.iconURL = iconURL;
        this.percentage = percentage;
        this.transactions = transactions;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getIconURL() { return iconURL; }

    public void setIconURL(Integer iconURL) { this.iconURL = iconURL; }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) { this.percentage = percentage; }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) { this.transactions = transactions; }

}