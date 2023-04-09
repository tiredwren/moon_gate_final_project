package com.example.moongate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFunctionActivity extends AppCompatActivity {



    // inside of each of these, we pull information from each website about which plants they have.
    String[] homeDepotPlantsList = {
            "Rose", "Blueberry plant", "Tulip", "Lily", "Carnation", "Peach", "Cherry",
            "Cherry Blossom", "Nightshade", "Lavender", "Lilac", "Magnolia"
    };
    String[] lowesPlantsList = {
            "Rose", "Tulip", "Carnation", "Sweet Pea", "Sweet William", "Pine", "Apricot", "Peach"
    };
    String[] amazonPlantsList = {
            "Rhododendron", "Chamomile", "Parsley", "Thyme", "Sage", "Rosemary", "Sweet Pea",
            "Peach", "Rose", "Pear", "Apple"
    };

    private ListView listSearch;
    private EditText editSearch;
    private ArrayAdapter<String> tempArrayAdapter;
    private final ArrayList<String> temp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);setContentView(R.layout.activity_search_function);
        View v = LayoutInflater.from(this).inflate(R.layout.list_item, null);

        List<String> favorites = new ArrayList<>();

        CheckBox like = (CheckBox) v.findViewById(R.id.favHeart);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.isChecked()) {
                    favorites.add(String.valueOf(R.id.textViewListItem));
                    System.out.println(favorites);
                }

            }
        });

        // connecting activity to xml components
        listSearch = findViewById(R.id.listSearch);
        editSearch = findViewById(R.id.editSearch);

        // adding each plant into temp:
        for (String plant : homeDepotPlantsList) {
            temp.add("Home Depot: " + plant);
        }
        for (String plant : lowesPlantsList) {
            temp.add("Lowes: " + plant);
        }
        for (String plant : amazonPlantsList) {
            temp.add("Amazon: " + plant);
        }

        // setting array adapter, which sets the information displayed in the searchview
        tempArrayAdapter = new ArrayAdapter<>
                (this, R.layout.list_item, R.id.textViewListItem, temp);
        listSearch.setAdapter(tempArrayAdapter);



        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            // as user types, plants are deleted if they don't contain correct letters
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SearchFunctionActivity.this.tempArrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });




    }
}