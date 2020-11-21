package com.example.Bachat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Expense.db";
    public static final String TABLE_NAME = "expense_table";
    public static final String TABLE_NAME_EARNING = "earn_table";
    public static final String TABLE_NAME_CATEGORY_THRESHOLD = "threshold_table";
    public static final String TABLE_NAME_USER_PROFILE = "user_profile_table";
    public static final String TABLE_NAME_USER = "user_table";
    public static final String TABLE_NAME_EXPENSE_CONTACTS = "expense_contacts_table";
    public static final String TABLE_NAME_EARNING_CONTACTS = "earning_contacts_table";
    public static final String TABLE_NAME_CURRENCY = "currency_table";
    public static final String Eid = "ID";
    public static final String Emode = "Mode";
    public static final String Emop = "Modeofpayment";
    public static final String EAmount = "Amount";
    public static final String ENote = "Note";
    public static final String EDate = "Date";
    public static final String id = "ID";
    public static final String Category = "Category";
    public static final String Modeofpayment = "Modeofpayment";
    public static final String Subcategory = "SubCategory";
    public static final String Amount = "Amount";
    public static final String Date = "Date";
    public static final String Note = "Note";
    public static final String Currency = "Currency";
    public static final String ContactName = "CONTACTNAME";
    public static final String ContactPhone = "Phone";
    public static final String ContactEmail = "Email";
    public static final String Threshold = "Threshold";
    public static final String ThresholdCategory = "Category";
    public static final String Name = "Name";
    public static final String Mail = "Mail";
    public static final String Password = "Password";
    public static final String Question = "Question";
    public static final String Answer = "Answer";
    public static final String Month_start ="Month_Start";
    public static final String Budget ="Budget";
    public static final String Profile ="Profile";
    public static final String DefaultCurrency ="DEFAULTCURRENCY";
    public static final String ConvertedAmount ="CONVERTEDAMOUNT";
    public static final String EProfile ="Profile";
    public static final String IsCurrent ="IsCurrent";
    public static final String Month ="Month";


    private static final String TAG = "DatabaseHelper";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME_EARNING +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,MODE TEXT,MODEOFPAYMENT TEXT,AMOUNT INTEGER,DATE DATE,NOTE TEXT,CURRENCY TEXT,CONTACTNAME TEXT,PHONE TEXT,EMAIL TEXT,CONVERTEDAMOUNT INTEGER, PROFILE TEXT, DEFAULTCURRENCY TEXT)");
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY TEXT,SUBCATEGORY TEXT,MODEOFPAYMENT TEXT,AMOUNT INTEGER,DATE DATE,NOTE TEXT,CURRENCY TEXT,CONTACTNAME TEXT,PHONE TEXT,EMAIL TEXT,CONVERTEDAMOUNT INTEGER, PROFILE TEXT, DEFAULTCURRENCY TEXT)");
        db.execSQL("create table " + TABLE_NAME_CATEGORY_THRESHOLD+" (CATEGORY TEXT,THRESHOLD TEXT,MONTH INTEGER, PROFILE TEXT)");
        db.execSQL("create table " + TABLE_NAME_USER+" (NAME TEXT,PASSWORD TEXT,MAIL VARCHAR,MONTH_START INTEGER,BUDGET INETEGR,QUESTION TEXT, ANSWER TEXT)");
        db.execSQL("create table " + TABLE_NAME_USER_PROFILE+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,PROFILE TEXT,ISCURRENT TEXT)");
        db.execSQL("create table " + TABLE_NAME_CURRENCY+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CURRENCY TEXT,ISCURRENT TEXT,OFFLINERATE INTEGER)");
        db.execSQL("create table " + TABLE_NAME_EXPENSE_CONTACTS+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PHONE TEXT,EMAIL TEXT)");
        db.execSQL("create table " + TABLE_NAME_EARNING_CONTACTS+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PHONE TEXT,EMAIL TEXT)");
        Log.d(TAG, "onCreate: created all tables");
        db.execSQL("INSERT INTO user_profile_table (ID,Profile,IsCurrent) values('"+ "1" + "','"+  "Default"+ "','"+  "True"+ "')");
        db.execSQL("INSERT INTO currency_table (ID,Currency,IsCurrent,OFFLINERATE) values('"+ "1" + "','"+  "EUR €"+ "','"+  "True"+ "','" +  "0.00" + "')");
        db.execSQL("INSERT INTO currency_table (ID,Currency,IsCurrent,OFFLINERATE) values('"+ "2" + "','"+  "USD $"+ "','"+  "False"+ "','" +  "1.12" + "')");
        db.execSQL("INSERT INTO currency_table (ID,Currency,IsCurrent,OFFLINERATE) values('"+ "3" + "','"+  "INR ₹"+ "','"+  "False"+ "','" +  "84.04" + "')");
        db.execSQL("INSERT INTO currency_table (ID,Currency,IsCurrent,OFFLINERATE) values('"+ "4" + "','"+  "GBP £"+ "','"+  "False"+ "','" +  "0.90" + "')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EARNING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY_THRESHOLD);
        onCreate(db);
    }

    public boolean createNewEntryThreshold(String month, String profile){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Budget" + "','"+  "Not Set"+ "','"+   month + "','"+  profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Health" + "','"+  "Not Set"+"','" + month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Donations" + "','"+  "Not Set"+ "','"+ month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Bills" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Shopping" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Dining out" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Entertainment" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Groceries" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Pet Care" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Transportation" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Loans" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Personal Care" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        db.execSQL("INSERT INTO threshold_table (Category,Threshold,Month,Profile) values('"+ "Miscellaneous" + "','"+  "Not Set"+ "','"+  month + "','"+ profile+"')");
        return true;
    }

    public boolean checkEntryThreshold(String passedmonth, String passedprofile){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select category from " + TABLE_NAME_CATEGORY_THRESHOLD + " where profile like " + "'" + passedprofile +"'" + " and month like " + "'" + passedmonth + "'", null);
        boolean toreturn;
        if(res.moveToPosition(5)){
            toreturn = true;
        }
        else{
            toreturn = false;
        }
        return toreturn;
    }
    //Offline currency conversion methods
    public void insertConversionRates(String curr, String rate)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "insertConversionRates: rate and currency are: " + rate + " " + curr);
        db.execSQL("UPDATE currency_table SET OFFLINERATE = '" + rate + "' WHERE Currency like " + "'" + curr + "%'" );
    }
    public Cursor getOfflineRate(String selectedCurrency){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select OFFLINERATE from " + TABLE_NAME_CURRENCY + " where Currency like " + "'" + selectedCurrency + "%'" , null);
        return res;
    }

    //For recommendations


    public Cursor getEntryThreshold(String passedmonth, String passedprofile){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_CATEGORY_THRESHOLD + " where profile like " + "'" + passedprofile +"'" + " and month like " + "'" + passedmonth + "'" , null);

       return res;
    }
    public Cursor getBudgetForMonth(int month, String category, String profile){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Threshold from " + TABLE_NAME_CATEGORY_THRESHOLD + " where category like " + "'" + category +"'" + " and profile like " + "'" + profile +"'" + " and month like " + "'" + month + "'" , null);

        return res;
    }

    public boolean updateThreshold(String category, String amount,String month, String profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Threshold, amount);
        contentValues.put(Month, month);
        contentValues.put(Profile, profile);
        //contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        db.execSQL("UPDATE threshold_table SET THRESHOLD = '" + amount + "' WHERE CATEGORY = '"+ category + "'" + " AND MONTH = " + month + " AND PROFILE = '"  + profile + "'");
        return true;
    }

    public Cursor getAllDataThreshold(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME_CATEGORY_THRESHOLD, null);
        return res;
    }

    //The following two NEW functions are exclusively for the initial data insert when a transaction is added.
    //For Earning storage
    public boolean storeEarningTransaction(String category, String modeofpayment, String amount, String date, String note, String curr, String name, String phone, String email, Double cAmount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Emode, category);
        contentValues.put(Emop, modeofpayment);
        contentValues.put(EAmount, amount);
        contentValues.put(EDate, date);
        contentValues.put(ENote, note);
        contentValues.put(Currency, curr);
        contentValues.put(ContactName, name);
        contentValues.put(ContactPhone, phone);
        contentValues.put(ContactEmail, email);
        contentValues.put(ConvertedAmount, String.valueOf(cAmount));
        contentValues.put(EProfile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        long result = db.insert(TABLE_NAME_EARNING,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //For Expense storage
    public boolean storeExpenseTransaction(String category, String subcategory, String modeofpayment, String amount, String date, String note, String curr, String name, String phone, String email,Double cAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Category, category);
        contentValues.put(Subcategory, subcategory);
        contentValues.put(Modeofpayment, modeofpayment);
        contentValues.put(Amount, amount);
        contentValues.put(Date, date);
        contentValues.put(Note, note);
        contentValues.put(Currency, curr);
        contentValues.put(ContactName, name);
        contentValues.put(ContactPhone, phone);
        contentValues.put(ContactEmail, email);
        contentValues.put(ConvertedAmount, String.valueOf(cAmount));
        contentValues.put(EProfile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData(String category, String subcategory, String modeofpayment, String amount, String date, String note, String curr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Category, category.trim());
        contentValues.put(Subcategory, subcategory.trim());
        contentValues.put(Modeofpayment, modeofpayment.trim());
        contentValues.put(Amount, amount.trim());
        contentValues.put(Date, date.trim());
        contentValues.put(Note, note.trim());
        if(curr.trim().equals("INR")){
            curr = "INR ₹";
        }
        else if(curr.trim().equals("EUR")){
            curr = "EUR €";
        }else if(curr.trim().equals("USD")){
            curr = "USD $";
        }else if(curr.trim().equals("GBP")){
            curr = "GBP £";
        }

        contentValues.put(Currency, curr.trim());
        contentValues.put(Profile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);

        contentValues.put(ContactName, "");
        contentValues.put(ContactPhone, "");
        contentValues.put(ContactEmail, "");

        contentValues.put(EProfile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        String convertedAmount;

        String rate="0";

        if(HomeFragment.default_currency.contains(curr.trim())){
            Log.d(TAG, "insertData: if");
            rate = "1";
        }else{
            Log.d(TAG, "insertData: else" + HomeFragment.default_currency + " " + curr);
            Cursor curRes = getOfflineRate(curr.trim());
            while (curRes.moveToNext())
            {
                rate = curRes.getString(0);
            }
        }
        convertedAmount = String.valueOf(Float.parseFloat(amount)/Float.parseFloat(rate));
        Log.d(TAG, "insertData: converted " +convertedAmount+ " rate is "+ rate );
        contentValues.put(ConvertedAmount, convertedAmount);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //for repeat functionality
    public boolean repeatData(String category, String subcategory, String modeofpayment, String amount, String date, String note, String curr, String name, String phone, String email, Double cAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Category, category);
        contentValues.put(Subcategory, subcategory);
        contentValues.put(Modeofpayment, modeofpayment);
        contentValues.put(Amount, amount);
        contentValues.put(Date, date);
        contentValues.put(Note, note);
        contentValues.put(Currency, curr);
        contentValues.put(ContactName, name);
        contentValues.put(ContactPhone, phone);
        contentValues.put(ContactEmail, email);
        contentValues.put(ConvertedAmount, String.valueOf(cAmount));
        contentValues.put(Profile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //For recommendations - Expenses
    public Cursor getTopThreeExpenseOfMonth(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select category, count(*) as count from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile +"'" + " and date like " + "'" + "%/" + (month + 1) + "/%'" + " group by category order by count desc limit 3", null);
        return res;
    }

    //For recommendations - Expenses
    public Cursor getTopThreeEarningsOfMonth(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select mode, count(*) as count from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile +"'" + " and date like " + "'" + "%/" + (month + 1) + "/%'" + " group by mode order by count desc limit 3", null);
        return res;
    }

    //For contacts - Expense Contacts storage
    public void storeExpenseContacts(String name, String phone, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME + " (CONTACTNAME ,PHONE ,EMAIL ) values ('"+ name + "','"+  phone + "','"+ email + "')");
    }


    //For graphs - Expense Category
    public Cursor getExpenseAmountByCategory(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select category, sum(CONVERTEDAMOUNT) as amount from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile +"'" + " and date between " + "'" + startDate + "' and '" + endDate + "'"  + " group by category", null);
        return res;
    }

    // For graphs - List View
    public Cursor getExpenseAmountByCategory2(String startDate, String endDate,String Category){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(CONVERTEDAMOUNT) as amount from " + TABLE_NAME + " where profile like " + "'" +
                HomeFragment.current_profile +"'" + " and date between " + "'" + startDate + "' and '" +
                endDate + "'"  + "and category='" + Category + "' group by category", null);
        return res;
    }

    //For graphs - Earning Category
    public Cursor getEarningAmountByCategory(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select mode, sum(CONVERTEDAMOUNT) as amount from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile +"'" + " and date between " + "'" + startDate + "' and '" + endDate + "'"  + " group by mode", null);
        return res;
    }
    //For graphs - Expense Amount
    public Cursor getExpenseAmountMain(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(CONVERTEDAMOUNT) as amount from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile +"'" + " and date like " + "'" + year + "/" + (month + 1) + "/%'", null);
        return res;
    }

    //For graphs - Earning Amount
    public Cursor getEarningAmountMain(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(CONVERTEDAMOUNT) as amount from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile +"'" + " and date like " + "'" + year + "/" + (month + 1) + "/%'", null);
        return res;
    }

    //For graphs - Amounts per day
    public Cursor getExpensesperday(String category, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select sum(CONVERTEDAMOUNT) as amount, date from " + TABLE_NAME +
                " where profile like " + "'" + HomeFragment.current_profile + "'" + " " +
                "and category = '" + category + "' and date between " + "'" + startDate + "' and '" + endDate + "'"+ " group by date", null);
        return res;
    }

    //For graphs - Transactions from previous month
    public Cursor getTransactionsOfPrevMonth(int month) {
        SQLiteDatabase db = this.getWritableDatabase();
        month = month - 1;
        Cursor res = db.rawQuery("select count(*) from " + TABLE_NAME + " where profile like " + "'" +
                HomeFragment.current_profile + "'" + " and date like '%/" + month + "/%'", null);
        return res;
    }
    public Cursor getTransactionsOfPrevMonth_c(int month,String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        month = month - 1;
        Cursor res = db.rawQuery("select sum(CONVERTEDAMOUNT) as amount from " + TABLE_NAME + " where profile like " + "'" +
                HomeFragment.current_profile + "'" + "and category = '" + category + "' and date like '%/" + month + "/%'", null);
        return res;
    }

    //For graphs - Transactions from previous month
    public Cursor getTransactionsOfCurrentMonth(String  startDate, String endDate, String Category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count(*) from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile
                +"'" + " and date between " + "'" + startDate + "' and '" +
                endDate + "'"  + "and category='" + Category + "' group by category", null);
        return res;
    }

    //For graphs - Thresholds set for category
    public Cursor getThresholdPerCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select THRESHOLD from " + TABLE_NAME_CATEGORY_THRESHOLD + " where profile like " + "'" + HomeFragment.current_profile + "'" + " and category = '" + category + "'", null);
        return res;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME+ " where profile like " + "'" + HomeFragment.current_profile +"'" + " order by date desc", null);
        Log.d(TAG, "inside getAllData: " + res);
        return res;
    }
    public Cursor getNote(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select note from " +TABLE_NAME+ " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getCurrencyEntered(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select currency from " +TABLE_NAME+ " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getEarningCurrencyEntered(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select currency from " +TABLE_NAME_EARNING + " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getEarningMop(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Modeofpayment from " +TABLE_NAME_EARNING + " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getContactName(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ContactName from " +TABLE_NAME+ " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getEarningContactName(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ContactName from " +TABLE_NAME_EARNING+ " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getContactPhone(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Phone from " +TABLE_NAME+ " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getEarningContactPhone(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Phone from " +TABLE_NAME_EARNING + " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getContactEmail(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Email from " +TABLE_NAME+ " where id like " + "'" + id +"'", null);
        return res;
    }

    public Cursor getEarningContactEmail(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Email from " +TABLE_NAME_EARNING+ " where id like " + "'" + id +"'", null);
        return res;
    }

    public boolean updateData(String id,String category, String subcategory, String modeofpayment, String amount, String date, String note, String curr, String name, String phone, String email, Double cAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Category, category);
        contentValues.put(Subcategory, subcategory);
        contentValues.put(Modeofpayment, modeofpayment);
        contentValues.put(Amount, amount);
        contentValues.put(Date, date);
        contentValues.put(Note, note);
        contentValues.put(Currency, curr);
        contentValues.put(ContactName, name);
        contentValues.put(ContactPhone, phone);
        contentValues.put(ContactEmail, email);
        contentValues.put(ConvertedAmount, String.valueOf(cAmount));
        contentValues.put(Profile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }


    public boolean insertDataEarning(String emode, String emop, String eamount, String edate, String enote, String curr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Emode, emode.trim());
        contentValues.put(Emop, emop.trim());
        contentValues.put(EAmount, eamount.trim());
        contentValues.put(EDate, edate.trim());
        contentValues.put(ENote, enote.trim());
        if(curr.trim().equals("INR")){
            curr = "INR ₹";
        }
        else if(curr.trim().equals("EUR")){
            curr = "EUR €";
        }else if(curr.trim().equals("USD")){
            curr = "USD $";
        }else if(curr.trim().equals("GBP")){
            curr = "GBP £";
        }
        contentValues.put(Currency, curr.trim());
        contentValues.put(ContactName, "");
        contentValues.put(ContactPhone, "");
        contentValues.put(ContactEmail, "");

        contentValues.put(EProfile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        String convertedAmount;

        String rate="0";

        if(HomeFragment.default_currency.contains(curr.trim())){
            Log.d(TAG, "insertDataEarning:if");
            rate = "1";
        }else{
            Log.d(TAG, "insertData: else" + HomeFragment.default_currency + " " + curr);
            Cursor curRes = getOfflineRate(curr.trim());
            while (curRes.moveToNext())
            {
                rate = curRes.getString(0);
            }
        }
        convertedAmount = String.valueOf(Float.parseFloat(eamount)/Float.parseFloat(rate));
        Log.d(TAG, "insertData: converted " +convertedAmount+ " rate is "+ rate );

        contentValues.put(ConvertedAmount, convertedAmount);

        long result = db.insert(TABLE_NAME_EARNING,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //For Earning edit Update
    public boolean repeatDataEarning(String emode, String emop, String eamount, String edate, String enote, String curr, String name, String phone, String email, Double cAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Emode, emode);
        contentValues.put(Emop, emop);
        contentValues.put(EAmount, eamount);
        contentValues.put(EDate, edate);
        contentValues.put(ENote, enote);
        contentValues.put(Currency, curr);
        contentValues.put(ContactName, name);
        contentValues.put(ContactPhone, phone);
        contentValues.put(ContactEmail, email);
        contentValues.put(ConvertedAmount, String.valueOf(cAmount));
        contentValues.put(EProfile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        long result = db.insert(TABLE_NAME_EARNING,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataEarning(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " +TABLE_NAME_EARNING+ " where profile like " + "'" + HomeFragment.current_profile +"'"  + " order by date desc", null);

        return res;
    }

    //To get income categories for the income category spinner
    public Cursor getAllIncomeCategory () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select DISTINCT MODE from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and MODE != 'null'", null);
        return res;
    }

    //To get expense for the filtering function
    public Cursor getAllIncomeModeOfPayment () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select DISTINCT MODEOFPAYMENT from " + TABLE_NAME_EARNING, null);
        return res;
    }

    public Cursor getNoteEarning(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select note from " +TABLE_NAME_EARNING+ " where id like " + "'" + id +"'", null);
        return res;
    }
    public boolean updateDataEarning(String id,String emode, String modeofpayment, String eamount, String edate, String enote, String curr, String name, String phone, String email, Double cAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Emode, emode);
        contentValues.put(Modeofpayment, modeofpayment);
        contentValues.put(EAmount, eamount);
        contentValues.put(EDate, edate);
        contentValues.put(ENote, enote);
        contentValues.put(Currency, curr);
        contentValues.put(ContactName, name);
        contentValues.put(ContactPhone, phone);
        contentValues.put(ContactEmail, email);
        contentValues.put(ConvertedAmount, String.valueOf(cAmount));
        contentValues.put(EProfile, HomeFragment.current_profile);
        contentValues.put(DefaultCurrency, HomeFragment.default_currency);
        db.update(TABLE_NAME_EARNING, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteDataEarning(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_EARNING, "ID = ?",new String[] {id});
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }



    //To get expense for the filtering function
    public Cursor getAllExpenseCategory () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select DISTINCT CATEGORY from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and CATEGORY != 'null'", null);
        return res;
    }

    //To get expense for the filtering function
    public Cursor getAllModeOfPayments () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select DISTINCT Modeofpayment from " + TABLE_NAME + " where Modeofpayment != 'null'", null);
        return res;
    }
    //The filter function
    public Cursor getExpenseFiltered(String category, String mop, String firstDate, String secondDate){
        SQLiteDatabase db = this.getWritableDatabase();
        //Only category
        if (!category.equals("") && mop.equals("") && firstDate.equals("") && secondDate.equals("")){
            if (category.equals("All")){
                Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' order by date desc",null);
                return res;
            }
            Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and category = '" + category + "'" + " order by date desc",null);
            return res;
        }
        //Only category and mop
        else if(!category.equals("") && !mop.equals("") && firstDate.equals("") && secondDate.equals("") ){
            if (category.equals("All")){
                Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " order by date desc",null);
                return res;
            }
            Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and category = '" + category + "' and modeofpayment = '" + mop + "'" + " order by date desc",null);
            return res;
        }
        //Only category and date
        else if(!category.equals("") && mop.equals("") && !firstDate.equals("") && !secondDate.equals("")){
            if (category.equals("All")){
                Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
                return res;
            }
            else {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and category = '" + category + "'" + " and date between " + "'" + firstDate + "' and '" + secondDate + "'"  + " order by date desc", null);
                return res;
            }
        }
        //Only mop and date
        else if(category.equals("") && !mop.equals("") && !firstDate.equals("") && !secondDate.equals("") ){
            Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
            return res;
        }
        //Only mop
        else if(category.equals("") && !mop.equals("") && firstDate.equals("") && secondDate.equals("") ){
            Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " order by date desc", null);
            return res;
        }
        //Only date
        else if(category.equals("") && mop.equals("") && !firstDate.equals("") && !secondDate.equals("")){
            Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
            return res;
        }
        //Category, mop, and year
        else if(!category.equals("") && !mop.equals("") && !firstDate.equals("") && !secondDate.equals("") ){
            if (category == "All"){
                Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
                return res;
            }
            else {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " and category = '" + category + "'" + " and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
                return res;
            }
        }
        else {
            Cursor res = db.rawQuery("select * from " + TABLE_NAME  + " where profile like " + "'" + HomeFragment.current_profile + "' order by date desc" , null);
            return res;
        }
    }

    //The filter function for Earnings
    public Cursor getEarningFiltered(String category, String mop, String firstDate, String secondDate){
        SQLiteDatabase db = this.getWritableDatabase();
        //Only category
        if (!category.equals("") && mop.equals("") && firstDate.equals("") && secondDate.equals("")){
            if (category.equals("All")){
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile +"'"  + " order by date desc",null);
                return res;
            }
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and mode = '" + category + "'" + " order by date desc",null);
            return res;
        }
        //Only category and mop
        else if(!category.equals("") && !mop.equals("") && firstDate.equals("") && secondDate.equals("") ){
            if (category.equals("All")){
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and mode = '" + mop + "'" + " order by date desc",null);
                return res;
            }
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and mode = '" + category + "' and modeofpayment = '" + mop + "'" + " order by date desc",null);
            return res;
        }
        //Only category and date
        else if(!category.equals("") && mop.equals("") && !firstDate.equals("") && !secondDate.equals("")){
            if (category.equals("All")){
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
                return res;
            }
            else {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and mode = '" + category + "'" + " and date between " + "'" + firstDate + "' and '" + secondDate + "'"  + " order by date desc", null);
                return res;
            }
        }
        //Only mop and date
        else if(category.equals("") && !mop.equals("") && !firstDate.equals("") && !secondDate.equals("") ){
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
            return res;
        }
        //Only mop
        else if(category.equals("") && !mop.equals("") && firstDate.equals("") && secondDate.equals("") ){
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " order by date desc", null);
            return res;
        }
        //Only date
        else if(category.equals("") && mop.equals("") && !firstDate.equals("") && !secondDate.equals("")){
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
            return res;
        }
        //Category, mop, and year
        else if(!category.equals("") && !mop.equals("") && !firstDate.equals("") && !secondDate.equals("") ){
            if (category == "All"){
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
                return res;
            }
            else {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and modeofpayment = '" + mop + "'" + " and mode = '" + category + "'" + " and date between " + "'" + firstDate + "' and '" + secondDate + "'" + " order by date desc", null);
                return res;
            }
        }
        else {
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING  + " where profile like " + "'" + HomeFragment.current_profile + "' order by date desc" , null);
            return res;
        }
    }
    //To get date for the filtering function for Earning
    public Cursor getEarningAllDate(int yearInput, int monthInput) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (monthInput == 0) {
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and date like " + "'" + yearInput + "/%'", null);
            return res;
        } else {
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and date like " + "'" + yearInput + "/" + monthInput + "/%'", null);
            return res;
        }
    }

    //To get date for the filtering function with day input
    public Cursor getEarningAllDateWithDay(int yearInput, int monthInput, int dayInput) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (monthInput == 0) {
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and date like " + "'" + yearInput + "/%/" + dayInput + "'", null);
            return res;
        } else {
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and date = " + "'" + yearInput + "/" + monthInput + "/" + dayInput + "'", null);
            return res;
        }
    }

    //To get date for the filtering function with day input
    public Cursor getEarningModeAndDateWithDay(int yearInput, int monthInput, int dayInput, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (category == "All") {
            if (monthInput == 0) {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where profile like " + "'" + HomeFragment.current_profile + "' and mode = '" + category + "'" + " and date like " + "'" + yearInput + "/%/" + dayInput + "'", null);
                return res;
            } else {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where mode = '" + category + "'" + " and date = " + "'" + yearInput + "/" + monthInput + "/" + dayInput + "'", null);
                return res;
            }
        } else {
            if (monthInput == 0) {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where mode = '" + category + "'" + " and date like " + "'" + yearInput + "/%/" + dayInput + "'", null);
                return res;
            } else {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where mode = '" + category + "'" + " and date = " + "'" + yearInput + "/" + monthInput + "/" + dayInput + "'", null);
                return res;
            }
        }
    }

    //To filter based on mode
    public Cursor getEarningMode(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        if (category == "All"){
            Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING,null);
            return res;
        }
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where mode = '" + category + "'",null);
        return res;
    }

    //To filter based on category and date
    public Cursor getEarningModeAndDate(int yearInput, int monthInput, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (category == "All"){
            if (monthInput == 0) {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where date like " + "'" + yearInput + "/%'", null);
                return res;
            } else {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where date like " + "'" + yearInput + "/" + monthInput + "/%'", null);
                return res;
            }
        }
        else {
            if (monthInput == 0) {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where mode = '" + category + "'" + " and date like " + "'" + yearInput + "/%'", null);
                return res;
            } else {
                Cursor res = db.rawQuery("select * from " + TABLE_NAME_EARNING + " where mode = '" + category + "'" + " and date like " + "'" + yearInput + "/" + monthInput + "/%'", null);
                return res;
            }
        }
    }

    //method for getting currency
    public Cursor getDefaultCurrency(){
        SQLiteDatabase db = this.getWritableDatabase();
        String isCurrent = "True";
        Cursor res = db.rawQuery("select " + Currency + " from" + " " +TABLE_NAME_CURRENCY+ " where ISCURRENT = '" + isCurrent + "'", null);
        return res;
    }

    //method for currency setting
    public boolean setDefaultCurrency(String curr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 =new ContentValues();
        String isCurrent = "True";
        contentValues1.put(IsCurrent,"False");
        long result = db.update(TABLE_NAME_CURRENCY, contentValues1, "ISCURRENT = ?",new String[] { "True" });
        if (result == -1)
            return false;
        else {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(IsCurrent,"True");
            Log.d(TAG, "setDefaultCurrency: value of currency is: "+ curr);
            db.execSQL("UPDATE currency_table SET ISCURRENT = '" + isCurrent + "' WHERE Currency = '"+ curr + "'" );
            long result2 = db.update(TABLE_NAME_CURRENCY, contentValues2, "Currency = ?",new String[] { curr });//;£ GBP
            if (result2 == -1){
                Log.d(TAG, "is setDefaultCurrency loop " );
                return false;
            }
            else
                return true;
        }
    }

    public boolean insertDataUser(String name, String password, String mail, String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name, name);
        contentValues.put(Mail, mail);
        contentValues.put(Password, password);
        contentValues.put(Question, question);
        contentValues.put(Answer, answer);
        long result = db.insert(TABLE_NAME_USER,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public int checkLogin(String name, String password) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor res = db.rawQuery("select " + Password + " from" + " " +TABLE_NAME_USER+ " where name = '" + name + "'", null);
        if (res.getCount() == 0) {
            result = -100;
        }
        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append(res.getString(0) + "\n");
            String[] bufferdata  = buffer.toString().split("\n");
            Log.d(TAG, "checkLogin: "+ bufferdata[0]);
            if (bufferdata[0].equals(password)){
                result =1;
            }
            else result = -1;
        }
        return result;
    }
    public boolean updateDataUser(String oirginalname, String name, String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name, name);
        contentValues.put(Mail, mail);
        long result = db.update(TABLE_NAME_USER, contentValues, "NAME = ?",new String[] { oirginalname });
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean updateDateUser(String name,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Month_start, date);
        long result = db.update(TABLE_NAME_USER, contentValues, "NAME = ?",new String[] { name });
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean updateBudgetUser(String name,String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Budget, amount);
        long result = db.update(TABLE_NAME_USER, contentValues, "NAME = ?",new String[] { name });
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean updatePasswordUser(String password, String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Password, password);
        long result = db.update(TABLE_NAME_USER, contentValues, "NAME = ?",new String[] { name });
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean updateQAUser(String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String name = HomeFragment.username;
        contentValues.put(Question, question);
        contentValues.put(Answer,answer);
        long result = db.update(TABLE_NAME_USER, contentValues, "NAME = ?",new String[] { name });
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllDataUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME_USER, null);
        return res;
    }
    public boolean insertProfile(String profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Profile, profile);
        contentValues.put(IsCurrent, "False");
        long result = db.insert(TABLE_NAME_USER_PROFILE,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public String deleteProfile(String profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        String returnstring;
        Cursor res= getCurrentProfiles();
        res.moveToFirst();
        if (!(res.getString(0)).equalsIgnoreCase(profile)){
        //code to delete
            int a =db.delete(TABLE_NAME_USER_PROFILE, "PROFILE = ?",new String[] {profile});
            if(a>0){
                returnstring = "deleted";
            }
            else{
                returnstring ="error in deletion";
            }
        }
        else{

            returnstring = "cannot delete current";
        }

        return returnstring;
    }


    public boolean updateProfile(String originalprofile,String updatedprofile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Profile, updatedprofile);
        long result = db.update(TABLE_NAME_USER_PROFILE, contentValues, "PROFILE = ?",new String[] { originalprofile });
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllProfiles(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME_USER_PROFILE, null);
        return res;
    }
    public Cursor getCurrentProfiles(){
        SQLiteDatabase db = this.getWritableDatabase();
        String iscurrent = "True";
        Cursor res = db.rawQuery("select " + Profile + " from" + " " +TABLE_NAME_USER_PROFILE+ " where ISCURRENT = '" + iscurrent + "'", null);
        return res;
    }
    public boolean setCurrentProfiles(String profile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(IsCurrent,"False");
        long result = db.update(TABLE_NAME_USER_PROFILE, contentValues, "ISCURRENT = ?",new String[] { "True" });
        if (result == -1)
            return false;
        else {
            ContentValues cv1 = new ContentValues();
            cv1.put(IsCurrent,"True");
            long result2 = db.update(TABLE_NAME_USER_PROFILE, cv1, "PROFILE = ?",new String[] { profile });;
            if (result2 == -1)
                return false;
            else
                return true;
        }
    }

}

