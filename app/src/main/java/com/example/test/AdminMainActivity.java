package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    private Button btn_add, btn_logout, btn_orders;
    private Button btn_all;
    private RecyclerView rview;
    private TextView txt_total;
    private OrderAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db, ref;
    private ArrayList<AdminAddItemsModel> list;
    private ArrayList<AdminAddItemsModel> tlist;
    private ProgressBar pbar;
    private Button btn_return;
    private ImageButton btn_exit, btn_delete, btn_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        btn_add = findViewById(R.id.dash_btn_add_products);
        btn_all = findViewById(R.id.btn_admin_view_all);
        btn_logout = findViewById(R.id.admin_btn_logout);
        rview = findViewById(R.id.admin_dash_rview);
        btn_orders = findViewById(R.id.btn_admin_orders);
        txt_total = findViewById(R.id.txt_admin_order_total);

        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminMainActivity.this, AdminManageOrders.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminMainActivity.this, MainLogin.class));

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminMainActivity.this, AdminAddItems.class));
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminMainActivity.this, ItemAll.class));
            }
        });

        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();

        String userid = FirebaseAuth.getInstance().getUid();

        db = firebaseDatabase.getReference("orders");
        db.keepSynced(true);

        list = new ArrayList<>();
        tlist = new ArrayList<AdminAddItemsModel>();

        adapter = new OrderAdapter(this, list);

        rview.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference("orders");
        ref.keepSynced(true);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                int count = 0;

                if(snapshot.exists())
                {
                    count = (int) snapshot.getChildrenCount();
                    txt_total.setText(Integer.toString(count) + "");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                txt_total.setText("ERROR 404");
            }
        });

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                AdminAddItemsModel sm = snapshot.getValue(AdminAddItemsModel.class);

                list.add(sm);

                adapter.notifyDataSetChanged();

                if (!snapshot.exists())
                {
                    Toast.makeText(getApplicationContext(), "No DATA", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}