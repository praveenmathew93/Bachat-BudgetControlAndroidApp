package com.example.Bachat;

public class ImportDataClass {
    private String importcategory;
    private String importsubcategory;
    private String importmodeofpayment; //added string value for mode of payment for class ImportData
    private String importamount;
    private String importdate;
    private String importnote;

    public ImportDataClass(String importcategory, String importsubcategory, String importmodeofpayment, String importamount, String importdate, String importnote) {
        this.importcategory = importcategory;
        this.importsubcategory = importsubcategory;
        this.importmodeofpayment = importmodeofpayment;
        this.importamount = importamount;
        this.importdate = importdate;
        this.importnote = importnote;
    }

    public String getImportcategory() {
        return importcategory;
    }

    public void setImportcategory(String importcategory) {
        this.importcategory = importcategory;
    }

    public String getImportsubcategory() {
        return importsubcategory;
    }

    public void setImportsubcategory(String importsubcategory) {
        this.importsubcategory = importsubcategory;
    }

    public String getImportmodeofpayment() {
        return importmodeofpayment;
    }

    public void setImportmodeofpayment(String importmodeofpayment) {
        this.importsubcategory = importmodeofpayment;
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
}
