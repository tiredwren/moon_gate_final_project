package com.example.moongate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<String> storeName, plantName, plantPrice;

    public Adapter(Context context, List<String> storeName, List<String> plantName, List<String> plantPrice){
        this.inflater= LayoutInflater.from(context);
        this.storeName = storeName;
        this.plantName = plantName;
        this.plantPrice = plantPrice;

        Log.d("TAG", "Adapter: " + plantName);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String store = storeName.get(position);
        String plant = plantName.get(position);
        String price = plantPrice.get(position);

        holder.store.setText(store);
        holder.plant.setText(plant);
        holder.price.setText(price);
    }

    @Override
    public int getItemCount() {
        return storeName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView store, plant, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            store = itemView.findViewById(R.id.plantStoreTextView);
            plant = itemView.findViewById(R.id.plantNameTextView);
            price = itemView.findViewById(R.id.plantPriceTextView);
        }
    }
}