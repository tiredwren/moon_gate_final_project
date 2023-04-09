package com.example.moongate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<String> storeName, plantName, plantPrice, plantURL;

    public Adapter(Context context, List<String> storeName, List<String> plantName,
                   List<String> plantPrice, List<String> plantURL){
        this.inflater= LayoutInflater.from(context);
        this.storeName = storeName;
        this.plantName = plantName;
        this.plantPrice = plantPrice;
        this.plantURL = plantURL;

//        Log.d("TAG", "Adapter: " + plantName);
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
        String plantUrl = plantURL.get(position);

        // load text into individual textViews
        holder.store.setText(store);
        holder.plant.setText(plant);
        holder.price.setText(price);

        // load picture into imageView for the picture
//        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.image);
        Picasso.get().load(plantUrl).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return storeName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView store, plant, price;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            store = itemView.findViewById(R.id.plantStoreTextView);
            plant = itemView.findViewById(R.id.plantNameTextView);
            price = itemView.findViewById(R.id.plantPriceTextView);
            image = itemView.findViewById(R.id.pictureOfPlant);
        }
    }
}