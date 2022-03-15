package com.example.ecommerceapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context context;
    List<AddressModel> list;
    selectedAddress selectedAddress;
    RadioButton radioButtonSelectedAddress;

    public AddressAdapter(Context context, List<AddressModel> list, AddressAdapter.selectedAddress selectedAddress) {
        this.context = context;
        this.list = list;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textViewAddressName.setText(list.get(position).getFullAddress());
        holder.radioButtonSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (AddressModel addressModel : list){
                    addressModel.setSelected(false);
                }
                list.get(position).setSelected(true);
                if (radioButtonSelectedAddress != null){
                    radioButtonSelectedAddress.setChecked(false);
                }
                radioButtonSelectedAddress = (RadioButton) view;
                radioButtonSelectedAddress.setChecked(true);
                selectedAddress.setAddress(list.get(position).getFullAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAddressName;
        RadioButton radioButtonSelectAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAddressName = itemView.findViewById(R.id.address_name);
            radioButtonSelectAddress = itemView.findViewById(R.id.select_address);
        }
    }

    public interface selectedAddress{
        void setAddress(String address);
    }
}
