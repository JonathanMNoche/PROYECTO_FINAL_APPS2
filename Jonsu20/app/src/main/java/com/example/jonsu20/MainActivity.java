package com.example.jonsu20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button especifico;
    private ImageButton btnEat;
    private ImageButton btnParty;
    private ImageButton btnAngry;
    private ImageButton btnSleep;
    private ImageButton btnBath;
    private ImageButton btnPers;
    private EditText edtTextM;
    private Button btnEnv;
    LinearLayout Linear;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEat = findViewById(R.id.btnPizza);
        btnParty = findViewById(R.id.btnBurger);
        btnAngry = findViewById(R.id.btnFruit);
        btnSleep = findViewById(R.id.btnVegetables);
        btnBath = findViewById(R.id.btnCookies);
        btnPers = findViewById(R.id.btnDrink);
        edtTextM = findViewById(R.id.edtTextM);
        btnEnv = findViewById(R.id.btnEnv);
        Linear = this.findViewById(R.id.LinearPers);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//Sala de chat


    }

    /*
    btnEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar("Tengo hambre", "Irman", "", "1", ServerValue.TIMESTAMP));
            }
        });

        btnParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar("Quiero Jugar un poco", "Irman", "", "1", ServerValue.TIMESTAMP));
            }
        });

        btnAngry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar("No me siento bien", "Irman", "", "1", ServerValue.TIMESTAMP));
            }
        });

        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar("Tengo Sueño", "Irman", "", "1", ServerValue.TIMESTAMP));
            }
        });

        btnBath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar("Quiero ir al baño", "Irman", "", "1", ServerValue.TIMESTAMP));
            }
        });

        btnPers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPers.setVisibility(View.GONE);
                Linear.setVisibility(LinearLayout.VISIBLE);
            }
        });


        especifico = findViewById(R.id.btnEsp);
        especifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarespecifico();
            }
        });
     */

    public void Send(View v){
        String Send;
        Send = edtTextM.getText().toString();
        databaseReference.push().setValue(new MensajeEnviar(Send, "Hijo", "",
                "1", ServerValue.TIMESTAMP));
        Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
        edtTextM.setText("");
    }

    public void Cancelar(View v){
        edtTextM.setText("");
        btnPers.setVisibility(View.VISIBLE);
        Linear.setVisibility(View.GONE);
    }


    /*
    private void llamarespecifico(){
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            String token ="fpEdQCFER8qgUPLVAOOA7J:APA91bEmaKWnzPFiAZKB1aMn4RAw_3gtzEFL7d6A2b_UuEdhORX5z4cvn4oz7koNPHYtcKK_4vReB1a_q2MoNsnjgnilnN5ZK4kuyeG_lXbWc5ZpAEAtepefFLP1V3OyBVg9QqStfiUX";
            json.put("to", token);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "Hijo");
            notificacion.put("detalle", "Necesito ayuda!");

            json.put("data", notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json, null, null){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAA8rzsQg4:APA91bEkdwi7w697zGrJy-koBOtoDIyrvkGQUwKzJifnD4aURl6Dz3UmwBGeM6N9GO2F_bhIhjdzOC1N8VXss3HHONdmslbE5xxE9-nb8M3_dz1oJVwXrJJh0zxI_yQLZEb0NFEUCsvZ");
                    return header;
                }
            };
            myrequest.add(request);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    */

    private void llamarespecifico(){
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            String token ="fpEdQCFER8qgUPLVAOOA7J:APA91bEmaKWnzPFiAZKB1aMn4RAw_3gtzEFL7d6A2b_UuEdhORX5z4cvn4oz7koNPHYtcKK_4vReB1a_q2MoNsnjgnilnN5ZK4kuyeG_lXbWc5ZpAEAtepefFLP1V3OyBVg9QqStfiUX";
            json.put("to", token);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "Hijo");
            notificacion.put("detalle", "Necesito ayuda!");

            json.put("data", notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json, null, null){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAA8rzsQg4:APA91bEkdwi7w697zGrJy-koBOtoDIyrvkGQUwKzJifnD4aURl6Dz3UmwBGeM6N9GO2F_bhIhjdzOC1N8VXss3HHONdmslbE5xxE9-nb8M3_dz1oJVwXrJJh0zxI_yQLZEb0NFEUCsvZ");
                    return header;
                }
            };
            myrequest.add(request);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void Pizza(){
        databaseReference.push().setValue(new MensajeEnviar("Quiero pizza!", "Irman", "", "1", ServerValue.TIMESTAMP));
    }

    public void Burger(){
        databaseReference.push().setValue(new MensajeEnviar("Quiero una hamburguesa!", "Irman", "", "1", ServerValue.TIMESTAMP));

    }

    public void Fruit(){
        databaseReference.push().setValue(new MensajeEnviar("Quiero algo de fruta!", "Irman", "", "1", ServerValue.TIMESTAMP));
    }

    public void Veg(){
        databaseReference.push().setValue(new MensajeEnviar("Quiero algo de verdura!", "Irman", "", "1", ServerValue.TIMESTAMP));
    }

    public void Drink(){
        databaseReference.push().setValue(new MensajeEnviar("Quiero algo de beber!", "Irman", "", "1", ServerValue.TIMESTAMP));
    }

    public void Cookies(){
        databaseReference.push().setValue(new MensajeEnviar("Quiero galletas!", "Irman", "", "1", ServerValue.TIMESTAMP));
    }

}
