package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminManageOrders extends AppCompatActivity {

    RecyclerView rview;
    fOrderAdapter adapter;
    Button btn_return, btn_confirmed, btn_rejected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_orders);

        rview = findViewById(R.id.rview_admin_order);
        btn_return = findViewById(R.id.btn_m_orders_return);
        btn_confirmed = findViewById(R.id.btn_confirmed_orders);
        btn_rejected = findViewById(R.id.btn_rejected_orders);

        rview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<AdminAddItemsModel> options =
                new FirebaseRecyclerOptions.Builder<AdminAddItemsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("orders"), AdminAddItemsModel.class)
                        .build();

        adapter = new fOrderAdapter(options);

        rview.setAdapter(adapter);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminManageOrders.this, AdminMainActivity.class));
            }
        });

        btn_confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminManageOrders.this, AdminConfirmedOrders.class));
            }
        });

        btn_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminManageOrders.this, AdminRejectedOrders.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}