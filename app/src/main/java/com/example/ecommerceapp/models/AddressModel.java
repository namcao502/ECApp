package com.example.ecommerceapp.models;

public class AddressModel {
    String fullAddress;
    boolean isSelected;

    public AddressModel(String userAddress, boolean isSelected) {
        this.fullAddress = userAddress;
        this.isSelected = isSelected;
    }

    public AddressModel() {
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
