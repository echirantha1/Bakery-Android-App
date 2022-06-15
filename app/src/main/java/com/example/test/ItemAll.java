package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemAll extends AppCompatActivity {

    private RecyclerView rView;
    ProductAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private ArrayList<AdminAddItemsModel> list;
    private ProgressBar pbar;
    private ImageButton btn_exit, btn_delete, btn_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_all);

        rView = findViewById(R.id.admin_view_items);
        btn_exit = findViewById(R.id.btn_return);
        btn_refresh = findViewById(R.id.btn_refresh);


        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference("cupcakes").child("classic");
        db.keepSynced(true);

        list = new ArrayList<>();
        adapter = new ProductAdapter(this, list);

        rView.setAdapter(adapter);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ItemAll.this, AdminMainActivity.class));
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ItemAll.this, ItemAll.class));
            }
        });

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot datasnap : snapshot.getChildren())
                {

                    AdminAddItemsModel sm = datasnap.getValue(AdminAddItemsModel.class);
                    list.add(sm);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ItemAll.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}