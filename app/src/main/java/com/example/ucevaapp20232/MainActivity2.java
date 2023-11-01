package com.example.ucevaapp20232;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    Button btn2, btn3, btnPintar;
    EditText tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // enlazamiento
        btn2 = findViewById(R.id.btnLogIn2);
        btn3 = findViewById(R.id.btnLogIn3);
        btnPintar = findViewById(R.id.btnPintar);

        // enlazamiento
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment bf1 = new BlankFragment();
                openFragment(bf1);
                // fragmentTransaction.replace(R.id.contenedorFragmentos,bf1);
                // fragmentTransaction.addToBackStack(null);
                // fragmentTransaction.commit();
            }
        });
        btn3.setOnClickListener(this);
    }

    public void saludar(View d){
       // Toast.makeText(this,"Que hubo mor 2!!!!",Toast.LENGTH_LONG).show();
        Intent ir = new Intent(this,MainActivity3.class);
        Intent ir2 = new Intent(this,MainActivity.class);
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
                    startActivity(ir2);
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
        BlankFragment2 bf2 = new BlankFragment2();
        openFragment(bf2);
        //fragmentTransaction.replace(R.id.contenedorFragmentos,bf2);
        //fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commit();
    }

    private void openFragment(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contenedorFragmentos, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public  void consultarHttp(View f){
        // OBLIGADO A CREAR UN HILO INDEPENDIENTE
       GetHttp newHttp = new GetHttp();
       newHttp.execute();
    }

    public  void consultarVolley(View f){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://invessoft.com/api/eventos";
        // Request a string response from the provided URL.
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //VolleyLog.v("Response:%n %s",response.getInt("count"));
                            Log.d("", "Respuesta: "+response.toString());
                            JSONArray jsonArray = response.getJSONArray("eventos");
                            Log.d("", "Respuesta: "+jsonArray.toString());
                            String eventos = "";
                            tv1.setText(jsonArray.toString().substring(0,500));
                            tv2.setText(response.getString("count"));
                            //cantidadRegistros=response.getInt("count");
                            //dataAgenda = response.getJSONArray("agenda"); // for foreach
                            //System.out.println("cantidadRegistros1: "+cantidadRegistros);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        queue.add(req);
    }

    public  class GetHttp extends AsyncTask<Void,String,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL urlApi = new URL("https://api.invessoft.app/api/alternativas/eventos");
                HttpURLConnection urlConnection = (HttpURLConnection) urlApi.openConnection();
                int code = urlConnection.getResponseCode();
                if(code == HttpURLConnection.HTTP_OK){
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader bR = new BufferedReader(  new InputStreamReader(in));
                    String line = "";
                    StringBuilder responseStrBuilder = new StringBuilder();
                    while((line =  bR.readLine()) != null){
                        responseStrBuilder.append(line);
                        tv2.setText("SUCCESS: "+code); // accediento al hilo de UI
                    }
                    in.close();
                    publishProgress(responseStrBuilder.toString());
                }else{
                    publishProgress("ERROR "+code);
                }
                //tv1.setText(responseStrBuilder.toString()); // accediento al hilo de UI
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            tv1.setText(values[0]); // accediento al hilo de UI
            //Toast.makeText(getApplicationContext(),values[0],Toast.LENGTH_LONG).show();

        }
    }

    public void pintar(View s){
        /*for (int i = 0; i < 9; i++) {
            btnPintar.setBackgroundColor(Color.rgb(aleatorio(),aleatorio(),aleatorio()));
            btnPintar.setText("Iterator: "+i);
            try {
                Thread.sleep(1000);/// Este hilo es el hilo  de la UI
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
        Pintar obj = new Pintar();
        obj.execute(true);
    }

    public int aleatorio(){
        return  (int)(Math.random() * 255) + 1;
    }

    public class Pintar extends AsyncTask<Boolean,Integer,Void>{
        @Override
        protected Void doInBackground(Boolean... flag) {
            for (int i = 0; i < 9; i++) {
                // btnPintar.setBackgroundColor(Color.rgb(aleatorio(),aleatorio(),aleatorio()));
                // btnPintar.setText("Iterator: "+i);
                if(flag[0]){
                    publishProgress(i);
                }
             else {
                    AlertDialog.Builder notifier = new AlertDialog.Builder(getApplicationContext());
                    notifier.setMessage("No se autorizo el hilo")
                            .setTitle("Advertencia mor");
                    notifier.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // notifier.setNegativeButton()
                    notifier.show();
                }
                try {
                    Thread.sleep(1000);/// Este hilo es el hilo  de la UI
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btnPintar.setBackgroundColor(Color.rgb(aleatorio(),aleatorio(),aleatorio()));
            btnPintar.setText("Iterator: "+values[0]);
        }

    }
}