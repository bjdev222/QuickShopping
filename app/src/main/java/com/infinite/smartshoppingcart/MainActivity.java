package com.infinite.smartshoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=findViewById(R.id.start);
        runTask();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ScanActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUser currentU = FirebaseAuth.getInstance().getCurrentUser();
        String uId = currentU.getUid();
        Log.e("TAG", uId);



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
                Intent intent=new Intent(MainActivity.this,ScanActivity.class);
                startActivity(intent);
                break;
            case R.id.inv:
                Intent intent2=new Intent(MainActivity.this,Inventory.class);
                startActivity(intent2);
                break;



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //No Activity
    }

    private boolean checkInternet() {

        boolean y = true;
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            y=false;

        }

        return y;
    }
    public void runTask () {
        if(checkInternet())
        {
            //Toast.makeText(this, "Network available!", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Online...",Toast.LENGTH_SHORT).show();
        } else {
            Internetdialog();
        }
    }

    private void Internetdialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please check your connection!!");
        dialog.setTitle("No internet !!");
        dialog.setPositiveButton("Retry",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //  Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                        runTask();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
    }
}