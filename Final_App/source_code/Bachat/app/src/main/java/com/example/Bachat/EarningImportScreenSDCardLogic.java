package com.example.Bachat;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.util.Log.d;

public class EarningImportScreenSDCardLogic extends AppCompatActivity {
    private static final String TAG = "EarningImportScreen";
    DatabaseHelper myDb;

    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;

    Button btnUpDirectory,btnSDCard;

    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;

    ArrayList<ImportEarningDataClass> uploadData;

    ListView lvInternalStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_screen);
        lvInternalStorage = (ListView) findViewById(R.id.lvInternalStorage);
        //btnUpDirectory = (Button) findViewById(R.id.btnUpDirectory);
        btnSDCard = (Button) findViewById(R.id.btnViewSDCard);
        uploadData = new ArrayList<>();
        TextView toolbar_title_set=(TextView) findViewById(R.id.toolbar_title);
        toolbar_title_set.setText("Import Income");

        //need to check the permissions
        checkFilePermissions();

        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);
                    toastMessage("Selected a file for upload");

                    //Execute method for reading the excel data.
                    readExcelData(lastDirectory);

                }else
                {
                    count++;
                    pathHistory.add(count,(String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });

        //Goes up one directory level
        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count == 0){
                    d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                }else{
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });

        //Opens the SDCard or phone memory
        btnSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });

        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EarningImportScreenSDCardLogic.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarningImportScreenSDCardLogic.this, MainActivity.class);
                intent.putExtra("Import_Export_Screen", "Import Export Screen");
                startActivity(intent);
            }
        });

    }

    /**
     *reads the excel file columns then rows. Stores data as ExcelUploadData object
     * @return
     */
    private void readExcelData(String filePath) {
        d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);

        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            //outter loop, loops through rows
            for (int r = 1; r < rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (int c = 0; c < cellsCount; c++) {
                    //handles if there are to many columns on the excel sheet.
                    if(c>4){
                        Log.e(TAG, "readExcelData: ERROR. Excel File Format is incorrect! " );
                        toastMessage("ERROR: Excel File Format is incorrect!");
                        break;
                    }else{
                        String value = getCellAsString(row, c, formulaEvaluator);
                        String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                        d(TAG, "readExcelData: Data from row: " + cellInfo);
                        sb.append(value + ", ");
                    }
                }
                sb.append(":");
            }
            d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());

            parseStringBuilder(sb);

        }catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
        }
    }

    /**
     * Method for parsing imported data and storing in ArrayList<ImportEarningData>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder){
        d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split(":");

        //Add to the ArrayList<ImportEarningData> row by row
        for(int i=0; i<rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");

            //use try catch to make sure there are no "" that try to parse into doubles.
            try{
                String importcategory = String.valueOf((columns[0]));
                String importmop = String.valueOf((columns[1]));
                String importamount = String.valueOf((columns[2]));
                String importdate = String.valueOf((columns[3]));
                String importnote = String.valueOf((columns[4]));
                String importcurrency = String.valueOf((columns[4]));
                myDb.insertDataEarning(importcategory,importmop,importamount,importdate, importnote, importcurrency);

                String cellInfo = "(importcategory,importamount,importdate,importnote,importcurrency): (" + importcategory + "," + importmop + "," + importamount + "," + importdate + "," + importnote + "," + importcurrency + ")";
                d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);

                //add the the uploadData ArrayList
                uploadData.add(new ImportEarningDataClass(importcategory,importamount,importdate,importnote));

            }catch (NumberFormatException e){
                toastMessage("Data NOT Inserted");

                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());

            }
        }
        toastMessage("Data Inserted ");
        printDataToLog();
    }

    private void printDataToLog() {
        d(TAG, "printDataToLog: Printing data to log...");

        for(int i = 0; i< uploadData.size(); i++){
            String importcategory = uploadData.get(i).getImportcategory();
            String importmop = uploadData.get(i).getImportamount();
            String importamount = uploadData.get(i).getImportamount();
            String importdate = uploadData.get(i).getImportdate();
            String importnote = uploadData.get(i).getImportnote();
            d(TAG, "(importcategory,importamount,importdate,importnote): (" + importcategory + "," + importmop + "," + importamount + "," + importdate + "," + importnote +")");
        }
    }

    /**
     * Returns the cell as a string from the excel file
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {

        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            d(TAG, "getCellAsString: cellvalue type is "+cellValue.getCellType());
            switch (cellValue.getCellType()) {

                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("yyyy/mm/dd");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;
    }

    private void checkInternalStorage() {
        d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];
            d(TAG, "no of files is "+ listFile.length+ "!");

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();

            }

            for (int i = 0; i < listFile.length; i++)
            {
                d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }

    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


}


