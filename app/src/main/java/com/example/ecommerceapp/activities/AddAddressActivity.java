package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.AddressAdapter;
import com.example.ecommerceapp.models.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText editTextName, editTextAddress, editTextCity, editTextCode, editTextPhone;
    Button buttonAddAddress;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        editTextName = findViewById(R.id.ad_name);
        editTextAddress = findViewById(R.id.ad_address);
        editTextCity = findViewById(R.id.ad_city);
        editTextCode = findViewById(R.id.ad_code);
        editTextPhone = findViewById(R.id.ad_phone);
        buttonAddAddress = findViewById(R.id.buttonAddAddress);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        buttonAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String address = editTextAddress.getText().toString();
                String city = editTextCity.getText().toString();
                String code = editTextCode.getText().toString();
                String phone = editTextPhone.getText().toString();

                if (name.isEmpty()){
                    Toast.makeText(AddAddressActivity.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (address.isEmpty()){
                    Toast.makeText(AddAddressActivity.this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (city.isEmpty()){
                    Toast.makeText(AddAddressActivity.this, "Vui lòng nhập thành phố!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (code.isEmpty()){
                    Toast.makeText(AddAddressActivity.this, "Vui lòng nhập mã bưu điện!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()){
                    Toast.makeText(AddAddressActivity.this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fullAddress = name + " " + address + " " + city + " " + code + " " + phone;
                Map<String, String> map = new HashMap<>();
                map.put("fullAddress", fullAddress);

                firebaseFirestore.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddAddressActivity.this, "Đã thêm địa chỉ!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AddressActivity.class));
                        }
                        else {
                            Toast.makeText(AddAddressActivity.this, "Đã có lỗi xảy ra, vui lòng kiểm tra lại!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}