package com.infinite.smartshoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    RecyclerView Trcv;
    myAdapter madapter;
    TextView codetext;

    DatabaseReference reff;
    DatabaseReference reff2;

    String price2,purl2,st2,title2;

    int maxid=0;

    FirebaseDatabase mDatabse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Trcv=findViewById(R.id.rcv);

        codetext=findViewById(R.id.codetext);
        Button btn=findViewById(R.id.btn);

        codetext.setText(getIntent().getStringExtra("code"));

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

        Trcv.setLayoutManager(mLinearLayoutManager);


        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                          .setQuery(FirebaseDatabase.getInstance().getReference().child("data").orderByKey().equalTo(codetext.getText().toString()), model.class)
                        // .setQuery(FirebaseDatabase.getInstance().getReference("Student_Record").orderByChild("like").equalTo("50000"), model.class)
                      //  .setQuery(FirebaseDatabase.getInstance().getReference("data"), model.class)
                        .build();


        madapter = new myAdapter(options);
        Trcv.setAdapter(madapter);

        final model model2=new model();


        reff=  FirebaseDatabase.getInstance().getReference().child("data").child(codetext.getText().toString());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              title2=snapshot.child("title").getValue().toString();
                price2=snapshot.child("price").getValue().toString();
               purl2=snapshot.child("purl").getValue().toString();
               st2=snapshot.child("st").getValue().toString();




                Toast.makeText(Dashboard.this, title2, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


// to get current user uid
        String currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();;

     //   Toast.makeText(this, currentFirebaseUser, Toast.LENGTH_SHORT).show();

        reff2=  FirebaseDatabase.getInstance().getReference().child(currentFirebaseUser);
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid= (int) snapshot.getChildrenCount();
                  //  Toast.makeText(Dashboard.this, "children is"+maxid, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                model2.setPrice(price2);
                model2.setPurl(purl2);
                model2.setSt(st2);
                model2.setTitle(title2);
                reff2.child(String.valueOf(maxid+2)).setValue(model2);
                Toast.makeText(Dashboard.this, "Added in cart", Toast.LENGTH_SHORT).show();




            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.scan:
                Intent intent=new Intent(Dashboard.this,ScanActivity.class);
                startActivity(intent);
                break;
            case R.id.inv:
                Intent intent2=new Intent(Dashboard.this,Inventory.class);
                startActivity(intent2);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        madapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        madapter.stopListening();
    }

}