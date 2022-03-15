package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<CartModel> list;
    int money = 0;

    public CartAdapter(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewCurrentDate.setText(list.get(position).getCurrentDate());
        holder.textViewcurrentTime.setText(list.get(position).getCurrentTime());
        holder.textViewProductName.setText(list.get(position).getProductName());
        holder.textViewProductPrice.setText(list.get(position).getProductPrice());
        holder.textViewQuantity.setText(list.get(position).getQuantity());
        holder.textViewTotalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));

        money += Integer.parseInt(list.get(position).getTotalPrice());
        Intent intent = new Intent("TotalMoney");
        intent.putExtra("totalMoney", String.valueOf(money));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewcurrentTime, textViewCurrentDate, textViewProductName, textViewProductPrice, textViewQuantity, textViewTotalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCurrentDate = itemView.findViewById(R.id.current_date);
            textViewcurrentTime = itemView.findViewById(R.id.current_time);
            textViewProductName = itemView.findViewById(R.id.product_name);
            textViewProductPrice = itemView.findViewById(R.id.product_price);
            textViewQuantity = itemView.findViewById(R.id.quantity);
            textViewTotalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}
