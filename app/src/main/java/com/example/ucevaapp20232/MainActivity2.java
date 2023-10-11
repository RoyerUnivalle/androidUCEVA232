package com.example.ucevaapp20232;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.BufferUnderflowException;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    Button btn2, btn3;
    EditText tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //enlazamiento
        btn2 = findViewById(R.id.btnLogIn2);
        btn3 = findViewById(R.id.btnLogIn3);
        //enlazamiento
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Que hubo mor 1!!!!",Toast.LENGTH_LONG).show();
            }
        });
        btn3.setOnClickListener(this);
    }

    public void saludar(View d){
       // Toast.makeText(this,"Que hubo mor 2!!!!",Toast.LENGTH_LONG).show();
        Intent ir = new Intent(this,MainActivity3.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle data = new Bundle();
        data.putString("username",tv1.getText().toString());
        data.putString("passwd",tv2.getText().toString());
        ir.putExtras(data);
        if(tv1.getText().toString().matches("") || tv2.getText().toString().matches("")){
            AlertDialog.Builder notifier = new AlertDialog.Builder(this);
            notifier.setMessage("debe diligenciar la tupla usuario contrasena")
                    .setTitle("Advertencia mor");
            notifier.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            notifier.setNegativeButton("Ver terminos de referencia", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(ir);
                }
            });
            // notifier.setNegativeButton()
            notifier.show();
        }else{
            startActivity(ir);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this,"Que hubo mor 3!!!!",Toast.LENGTH_SHORT).show();
    }
}