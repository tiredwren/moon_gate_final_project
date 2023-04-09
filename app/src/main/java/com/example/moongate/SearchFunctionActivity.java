package com.example.moongate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
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

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class SearchFunctionActivity extends AppCompatActivity {

    //    private ArrayAdapter<String> tempArrayAdapter;
    private final ArrayList<String> temp = new ArrayList<>();

    // variables for pulling information from excel spreadsheets
    RecyclerView recyclerView;
    Adapter adapter;
    AsyncHttpClient client;
    Workbook workbook;
    List<String> storeName, plantName, plantPrice;

    CheckBox like;

    // inside of each of these, we pull information from each website about which plants they have.
//    String [] homeDepotPlantsList = {
//            "Rose", "Blueberry plant", "Tulip", "Lily", "Carnation", "Peach", "Cherry",
//            "Cherry Blossom", "Nightshade", "Lavender", "Lilac", "Magnolia"
//    };
//    String [] lowesPlantsList = {
//            "Rose", "Tulip", "Carnation", "Sweet Pea", "Sweet William", "Pine", "Apricot", "Peach"
//    };
//    String [] amazonPlantsList = {
//            "Rhododendron", "Chamomile", "Parsley", "Thyme", "Sage", "Rosemary", "Sweet Pea",
//            "Peach", "Rose", "Pear", "Apple"
//    };
//
    String[] liked = {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);
        View v = LayoutInflater.from(this).inflate(R.layout.list_item, null);

        CheckBox like = v.findViewById(R.id.favHeart);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.isChecked()){
                    fileList();
                }
            }
        });

        // connecting activity to xml components
        ListView listSearch = findViewById(R.id.listSearch);
        EditText editSearch = findViewById(R.id.editSearch);


        // code for excel spreadsheet information
        String url = "https://github.com/brindamoudgalya/MoonGate/blob/master/MoonGate.xls?raw=true";

        recyclerView = findViewById(R.id.recyclerView);

        storeName = new ArrayList<>();
        plantName = new ArrayList<>();
        plantPrice = new ArrayList<>();

        client = new AsyncHttpClient();
        client.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(SearchFunctionActivity.this, "Download Failed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                Toast.makeText(SearchFunctionActivity.this, "Download Successful.", Toast.LENGTH_SHORT).show();
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                if (file != null) {
                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);
                        for (int i = 0; i < sheet.getRows(); i++) {
                            Cell[] row = sheet.getRow(i);
                            storeName.add(row[0].getContents());
                            plantName.add(row[1].getContents());
                            plantPrice.add(row[2].getContents());
                        }

                        showData();

                        Log.d("TAG", "onSuccess: " + plantName);
                    } catch (IOException | BiffException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void showData() {
        adapter = new Adapter(this, storeName, plantName, plantPrice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}