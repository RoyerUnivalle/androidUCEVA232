package com.example.ucevaapp20232;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    TextView tv3;
    Integer contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle received = getIntent().getExtras();
        tv3 = findViewById(R.id.tv3);
        tv3.setText(received.getString("username") + " " +received.getString("passwd") );
        Toast.makeText(this,"onCreate 77777",Toast.LENGTH_LONG).show();
        //setHasOptionsMenu(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toast.makeText(this,"aqui pase",Toast.LENGTH_LONG).show();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.agree) {
            Toast.makeText(this, "agree", Toast.LENGTH_LONG).show();
        } else if (id == R.id.badThread) {
            Toast.makeText(this, "badThread", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    public void saludar(View d){
        // Toast.makeText(this,"Que hubo mor 2!!!!",Toast.LENGTH_LONG).show();
        Intent ir = new Intent(this,MainActivity2.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

    public void contar(View d){
        contador++;
        tv3.setText("Contador: "+ contador);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("contadorEjemplo",contador);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        contador = savedInstanceState.getInt("contadorEjemplo");
    }
}