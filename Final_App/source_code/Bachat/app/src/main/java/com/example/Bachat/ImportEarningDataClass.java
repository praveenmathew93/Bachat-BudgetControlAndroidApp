package com.example.Bachat;

public class ImportEarningDataClass {
    private String importcategory;
    private String importamount;
    private String importdate;
    private String importnote;
    private String importcurrency;


    public ImportEarningDataClass(String importcategory, String importamount, String importdate, String importnote) {
        this.importcategory = importcategory;
        this.importamount = importamount;
        this.importdate = importdate;
    }

    public String getImportcategory() {
        return importcategory;
    }

    public void setImportcategory(String importcategory) {
        this.importcategory = importcategory;
    }

    public String getImportamount() {
        return importamount;
    }

    public void setImportamount(String importamount) {
        this.importamount = importamount;
    }

    public String getImportdate() {
        return importdate;
    }

    public void setImportdate(String importdate) {
        this.importdate = importdate;
    }

    public String getImportnote() { return importnote; }

    public void setImportnote(String importnote) { this.importnote = importnote; }

    public String getImportCurrency() { return importcurrency; }

    public void setImportCurrency(String importcurrency) { this.importcurrency = importcurrency; }
}
