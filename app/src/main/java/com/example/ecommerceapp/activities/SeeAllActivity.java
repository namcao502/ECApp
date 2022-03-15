package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Variables;
import com.example.ecommerceapp.adapters.SeeAllAdapter;
import com.example.ecommerceapp.models.CategoryModel;
import com.example.ecommerceapp.models.SeeAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SeeAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //Toolbar seeAllToolbar;
    SeeAllAdapter seeAllAdapter;
    List<SeeAllModel> seeAllModelList;
    List<CategoryModel> categoryModelList;
    List<String> list;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

//        seeAllToolbar = findViewById(R.id.see_all_toolbar);
//        setSupportActionBar(seeAllToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getSupportActionBar().hide();

        String type = getIntent().getStringExtra(Variables.TYPE);
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        seeAllModelList = new ArrayList<>();
        seeAllAdapter = new SeeAllAdapter(this, seeAllModelList);
        recyclerView.setAdapter(seeAllAdapter);

        categoryModelList = new ArrayList<>();
        list = new ArrayList<>();

        //store category name in categoryModelList
//        db.collection("Category")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot  document : task.getResult()) {
//                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
//                                categoryModelList.add(categoryModel);
//                            }
//                        }
//                    }
//                });

        list.add("gaming");
        list.add("learning");
        list.add("luxury");
        list.add("lightweight");
        list.add("macbook");
        list.add("technology");


        for (int i=0; i<list.size(); i++){
            if (type != null && type.equalsIgnoreCase(list.get(i))){
                db.collection("SeeAll").whereEqualTo(Variables.TYPE, list.get(i))
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                SeeAllModel seeAllModel = doc.toObject(SeeAllModel.class);
                                seeAllModelList.add(seeAllModel);
                                seeAllAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        }

        if (type == null || type.isEmpty()){
            db.collection("SeeAll")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot doc : task.getResult().getDocuments()){
                            SeeAllModel seeAllModel = doc.toObject(SeeAllModel.class);
                            seeAllModelList.add(seeAllModel);
                            seeAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }


    }
}