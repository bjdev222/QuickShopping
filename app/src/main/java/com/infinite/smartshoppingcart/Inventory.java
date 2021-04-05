package com.infinite.smartshoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Inventory extends AppCompatActivity {


    DatabaseReference reff;
    int maxid=0;
    RecyclerView Trcv;
    myAdapter madapter;
    TextView total,txt;
    DatabaseReference reff2;
    FirebaseDatabase database;
    int sum=0;
    ProgressDialog pd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

         pd = new ProgressDialog(Inventory.this);
        pd.setMessage("Wait we are fetching data..");
        pd.show();




        Trcv=findViewById(R.id.trcv);

        total=findViewById(R.id.total);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

        Trcv.setLayoutManager(mLinearLayoutManager);


// to get current user uid
        String currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid() ;

        final String temp2=currentFirebaseUser;
        //   Toast.makeText(this, currentFirebaseUser, Toast.LENGTH_SHORT).show();

        final FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(currentFirebaseUser), model.class)
                        // .setQuery(FirebaseDatabase.getInstance().getReference("Student_Record").orderByChild("like").equalTo("50000"), model.class)
                        //  .setQuery(FirebaseDatabase.getInstance().getReference("data"), model.class)
                        .build();


        madapter = new myAdapter(options){
            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull model model) {


                holder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Inventory.this, getRef(position).getKey(), Toast.LENGTH_SHORT).show();
                        String abc=getRef(position).getKey();






                    }
                });
                super.onBindViewHolder(holder, position, model);
            }
        };

        Trcv.setAdapter(madapter);




        // to get current user uid
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
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

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child(currentFirebaseUser);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String desk = ds.child("price").getValue(String.class);

                    assert desk != null;
                    sum=sum + Integer.parseInt(desk);

                    float y=desk.length();
                    Log.e("TAG", String.valueOf(y));

                }
                total.setText(String.valueOf(sum));
                pd.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);






    }

    private void collectPhoneNumbers(Map<String,Object> users) {

        ArrayList<Long> phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((Long) singleUser.get("price"));

        }

        System.out.println(phoneNumbers.toString());
        Toast.makeText(this, phoneNumbers.toString(), Toast.LENGTH_LONG).show();
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