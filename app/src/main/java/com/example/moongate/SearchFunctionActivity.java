package com.example.moongate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class SearchFunctionActivity extends AppCompatActivity {

    private ListView listSearch;
    private EditText editSearch;
    private ArrayList<Adapter> adapterArrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    // variables for pulling information from excel spreadsheets
    RecyclerView recyclerView;
    Adapter adapter;
    AsyncHttpClient client;
    Workbook workbook;
    List<String> storeName, plantName, plantPrice, plantURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);

        // connecting activity to xml components
        listSearch = findViewById(R.id.listSearch);
        editSearch = findViewById(R.id.editSearch);

        addData("");


        // setting array adapter, which sets the information displayed in the searchview
        arrayAdapter = new ArrayAdapter<>
                (this, R.layout.list_item, R.id.plantNameTextView, plantName);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            // as user types, plants are deleted if they don't contain correct letters
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // clear recyclerview
                recyclerView.setAdapter(null);
                // clear arraylists
                storeName.clear();
                plantName.clear();
                plantPrice.clear();
                plantURL.clear();
                addData(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
    private void addData(CharSequence charSequence) {
        // code for excel spreadsheet information
        String url = "https://github.com/brindamoudgalya/MoonGate/blob/master/MoonGateFinal.xls?raw=true";
        recyclerView = findViewById(R.id.recyclerView);

        storeName = new ArrayList<>();
        plantName = new ArrayList<>();
        plantPrice = new ArrayList<>();
        plantURL = new ArrayList<>();

        client = new AsyncHttpClient();
        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(SearchFunctionActivity.this, "Download Failed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                if (file != null) {
                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);
                        for (int i = 0; i < sheet.getRows(); i++) {
                            Cell[] row = sheet.getRow(i);
                            if (row[0].getContents().toLowerCase(Locale.ROOT).contains(charSequence)
                                    || row[1].getContents().toLowerCase(Locale.ROOT).contains(charSequence)
                                    || row[2].getContents().toLowerCase(Locale.ROOT).startsWith("$" + charSequence)) {
                                storeName.add(row[0].getContents());
                                plantName.add(row[1].getContents());
                                plantPrice.add(row[2].getContents());
                                plantURL.add(row[3].getContents());
                            }
                        }

                        showData();

                    } catch (IOException | BiffException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void showData() {
        adapter = new Adapter(this, storeName, plantName, plantPrice, plantURL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}