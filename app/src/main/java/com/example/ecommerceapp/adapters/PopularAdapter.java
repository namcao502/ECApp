package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Variables;
import com.example.ecommerceapp.activities.DetailActivity;
import com.example.ecommerceapp.models.PopularModel;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    Context context;
    List<PopularModel> list;

    public PopularAdapter(Context context, List<PopularModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageViewPopularImage);
        holder.textViewPopularName.setText(list.get(position).getName());
        holder.textViewPopularPrice.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Variables.DETAIL, list.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewPopularImage;
        TextView textViewPopularName, textViewPopularPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPopularImage = itemView.findViewById(R.id.all_img);
            textViewPopularName = itemView.findViewById(R.id.all_product_name);
            textViewPopularPrice = itemView.findViewById(R.id.all_price);

        }
    }
}
