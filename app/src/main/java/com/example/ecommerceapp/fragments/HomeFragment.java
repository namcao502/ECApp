package com.example.ecommerceapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.DetailActivity;
import com.example.ecommerceapp.activities.SeeAllActivity;
import com.example.ecommerceapp.adapters.CategoryAdapter;
import com.example.ecommerceapp.adapters.NewProductsAdapter;
import com.example.ecommerceapp.adapters.PopularAdapter;
import com.example.ecommerceapp.models.CategoryModel;
import com.example.ecommerceapp.models.NewProductsModel;
import com.example.ecommerceapp.models.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    TextView textViewNewProductsSeeAll, textViewPopularSeeAll;

    ProgressDialog progressDialog;
    LinearLayout linearLayoutHome;

    RecyclerView categoryRecyclerView, newProductsRecyclerView, popularRecyclerView;

    //Category Recyclerview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //NewProducts Recyclerview
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //Popular Recyclerview
    PopularAdapter popularAdapter;
    List<PopularModel> popularModelList;

    //database
    FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //binding
        categoryRecyclerView = view.findViewById(R.id.rec_category);
        newProductsRecyclerView = view.findViewById(R.id.new_product_rec);
        popularRecyclerView = view.findViewById(R.id.popular_rec);
        linearLayoutHome = view.findViewById(R.id.home_layout);
        textViewNewProductsSeeAll = view.findViewById(R.id.newProducts_see_all);
        textViewPopularSeeAll = view.findViewById(R.id.popular_see_all);
        progressDialog = new ProgressDialog(getActivity());

        linearLayoutHome.setVisibility(View.GONE);
        db = FirebaseFirestore.getInstance();

        //image slider
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner01, "Ưu đãi lớn", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner02, "Hàng chính hãng", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner03, "Giá tốt", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        //ProgressBar
        progressDialog.setTitle("Chào mừng bạn đến với cửa hàng wibu!");
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //Category
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        //read category data from db
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayoutHome.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        }
                    }
                });

        //NewProducts
        newProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductsRecyclerView.setAdapter(newProductsAdapter);

        //read newProducts data from db
        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Popular
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModelList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getContext(), popularModelList);
        popularRecyclerView.setAdapter(popularAdapter);

        //read allProduct from db
        db.collection("AllProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //see all listener
        textViewNewProductsSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailActivity();
            }
        });
        textViewPopularSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailActivity();
            }
        });



        return view;
    }

    private void goToDetailActivity(){
        startActivity(new Intent(getContext(), SeeAllActivity.class));
    }
}