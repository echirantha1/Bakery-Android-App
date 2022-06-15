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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberCart extends AppCompatActivity {

    private RecyclerView rView;
    private TextView txt_total;
    private ClientAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private ArrayList<AdminAddItemsModel> list;
    private ArrayList<AdminAddItemsModel> tlist;
    private ProgressBar pbar;
    private Button btn_return;
    private ImageButton btn_exit, btn_delete, btn_refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_cart);

        rView = findViewById(R.id.rview_cart_client);

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);

        btn_return = findViewById(R.id.btn_cart_client_return);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MemberCart.this, MemberMain.class));
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();

        String userid = FirebaseAuth.getInstance().getUid();

        db = firebaseDatabase.getReference("orders");
        db.keepSynced(true);

        list = new ArrayList<>();
        tlist = new ArrayList<AdminAddItemsModel>();

        adapter = new ClientAdapter(this, list);

        rView.setAdapter(adapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot datasnap : snapshot.getChildren())
                {

                    AdminAddItemsModel sm = datasnap.getValue(AdminAddItemsModel.class);
                    list.add(sm);

                    adapter.notifyDataSetChanged();

                    if (!snapshot.exists())
                    {
                        Toast.makeText(getApplicationContext(), "No DATA", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}