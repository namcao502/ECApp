package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Variables;
import com.example.ecommerceapp.models.NewProductsModel;
import com.example.ecommerceapp.models.PopularModel;
import com.example.ecommerceapp.models.SeeAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    ImageView imageViewImage, imageViewAddItem, imageViewRemoveItem;
    TextView textViewName, textViewRatingNumber, textViewDescription, textViewPrice, textViewDetailQuantity;
    RatingBar ratingBar;
    Button buttonAddToCart, buttonBuyNow;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private NewProductsModel newProductsModel = null;
    private PopularModel popularModel = null;
    private SeeAllModel seeAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding();
        listener();
        getDataFromIntent();
        //getSupportActionBar().hide();
    }

    private void listener() {
        imageViewAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = textViewDetailQuantity.getText().toString();
                int number = Integer.parseInt(temp);
                number++;
                textViewDetailQuantity.setText(String.valueOf(number));
            }
        });
        imageViewRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = textViewDetailQuantity.getText().toString();
                int number = Integer.parseInt(temp);
                if (number <= 1){
                    number = 1;
                    textViewDetailQuantity.setText(String.valueOf(number));
                }
                else {
                    number--;
                    textViewDetailQuantity.setText(String.valueOf(number));
                }

            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                String currentTime, currentDate;
                SimpleDateFormat thisDay = new SimpleDateFormat("dd / MM / yyyy");
                currentDate = thisDay.format(calendar.getTime());
                SimpleDateFormat thisTime = new SimpleDateFormat("HH : mm : ss");
                currentTime = thisTime.format(calendar.getTime());

                //create a new collection
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", textViewName.getText().toString());
                cartMap.put("productPrice", textViewPrice.getText().toString());
                cartMap.put("currentTime", currentTime);
                cartMap.put("currentDate", currentDate);
                cartMap.put("quantity", textViewDetailQuantity.getText().toString());
                cartMap.put("totalPrice", String.valueOf(Integer.parseInt(textViewDetailQuantity.getText().toString()) * Integer.parseInt(textViewPrice.getText().toString())));

                firebaseFirestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });

        buttonBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddressActivity.class));
            }
        });
    }

    private void binding(){

        imageViewImage = findViewById(R.id.detail_img);
        imageViewAddItem = findViewById(R.id.detail_add_item);
        imageViewRemoveItem = findViewById(R.id.detail_remove_item);

        textViewName = findViewById(R.id.detail_name);
        textViewRatingNumber = findViewById(R.id.rating_number);
        textViewDescription = findViewById(R.id.detail_description);
        textViewPrice = findViewById(R.id.detail_price);
        textViewDetailQuantity = findViewById(R.id.detail_quantity);

        ratingBar = findViewById(R.id.rating_bar);

        buttonAddToCart = findViewById(R.id.detail_add_to_cart);
        buttonBuyNow = findViewById(R.id.detail_buy_now);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void getDataFromIntent(){
        Object objectDetail = getIntent().getSerializableExtra(Variables.DETAIL);
        if (objectDetail instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) objectDetail;
            if (newProductsModel != null){
                Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(imageViewImage);
                textViewName.setText(newProductsModel.getName());
                textViewRatingNumber.setText(newProductsModel.getRating());
                textViewDescription.setText(newProductsModel.getDescription());
                textViewPrice.setText(String.valueOf(newProductsModel.getPrice()));
                ratingBar.setRating(Float.valueOf(newProductsModel.getRating()));
            }
        }
        if (objectDetail instanceof PopularModel){
            popularModel = (PopularModel) objectDetail;
            if (popularModel != null){
                Glide.with(getApplicationContext()).load(popularModel.getImg_url()).into(imageViewImage);
                textViewName.setText(popularModel.getName());
                textViewRatingNumber.setText(popularModel.getRating());
                textViewDescription.setText(popularModel.getDescription());
                textViewPrice.setText(String.valueOf(popularModel.getPrice()));
                ratingBar.setRating(Float.valueOf(popularModel.getRating()));
            }
        }
        if (objectDetail instanceof SeeAllModel){
            seeAllModel = (SeeAllModel) objectDetail;
            if (seeAllModel != null){
                Glide.with(getApplicationContext()).load(seeAllModel.getImg_url()).into(imageViewImage);
                textViewName.setText(seeAllModel.getName());
                textViewRatingNumber.setText(seeAllModel.getRating());
                textViewDescription.setText(seeAllModel.getDescription());
                textViewPrice.setText(String.valueOf(seeAllModel.getPrice()));
                ratingBar.setRating(Float.valueOf(seeAllModel.getRating()));
            }
        }
    }
}