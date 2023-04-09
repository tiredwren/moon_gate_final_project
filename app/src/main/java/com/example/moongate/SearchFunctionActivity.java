package com.example.moongate;

import android.os.Bundle;
import android.util.Log;
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

    private ListView listSearch;
    private EditText editSearch;
    //    private ArrayAdapter<String> tempArrayAdapter;
    private final ArrayList<String> temp = new ArrayList<>();

    // variables for pulling information from excel spreadsheets
    RecyclerView recyclerView;
    Adapter adapter;
    AsyncHttpClient client;
    Workbook workbook;
    List<String> storeName, plantName, plantPrice;

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
//    String [] namesOfStores = {
//            "Home Depot", "Lowes", "Amazon"
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);

        // connecting activity to xml components
        listSearch = findViewById(R.id.listSearch);
        editSearch = findViewById(R.id.editSearch);

        // code for excel spreadsheet information
        String url = "https://github.com/brindamoudgalya/MoonGate/blob/master/MoonGate.xls?raw=true";
        String urlT = "https://github.com/brindamoudgalya/MoonGate/blob/master/MoonGate.xlsx?raw=true";
        String urlT1 = "https://github.com/bikashthapa01/excel-reader-android-app/blob/master/story.xls?raw=true";
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
                        workbook = workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);
                        for (int i = 0; i < sheet.getRows(); i++) {
                            Cell[] row = sheet.getRow(i);
                            storeName.add(row[0].getContents());
                            plantName.add(row[1].getContents());
                            plantPrice.add(row[2].getContents());
                        }

                        showData();

                        Log.d("TAG", "onSuccess: " + plantName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        // adding each plant into temp:
//        for (String plant : homeDepotPlantsList) {
//            temp.add("Home Depot: " + plant);
//        }
//        for (String plant : lowesPlantsList) {
//            temp.add("Lowes: " + plant);
//        }
//        for (String plant : amazonPlantsList) {
//            temp.add("Amazon: " + plant);
//        }
//
//        // setting array adapter, which sets the information displayed in the searchview
//        tempArrayAdapter = new ArrayAdapter<>
//                (this, R.layout.list_item, R.id.textViewListItem, temp);
//        listSearch.setAdapter(tempArrayAdapter);
//
//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            // as user types, plants are deleted if they don't contain correct letters
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                SearchFunctionActivity.this.tempArrayAdapter.getFilter().filter(charSequence);
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {}
//        });
    }
    private void showData() {
        adapter = new Adapter(this, storeName, plantName, plantPrice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


//    public String webScraper (String url) {
//        ArrayList<String> arrayList = new ArrayList<>();
//        try {
//            Document doc = Jsoup.connect("https://www.lowes.com/search?searchTerm=plants")
//                    .userAgent("Mozilla/17.0")
//                    .get();
//            Elements temporary = doc.select("div.brand-description");
//            for (Element plantList : temporary) {
//                arrayList.add(plantList.getElementsByTag
//                        ("span.description-spn").first().text());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return arrayList.toString();
//    }
}