package com.example.juansu_v2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private ImageButton btnEnviaFoto;
    String m = "";

    private static final int PHOTO_SENT = 1;

    private AdapterMensajes adapter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fotoPerfil = findViewById(R.id.fotoPerfil);
        nombre = findViewById(R.id.nombre);
        rvMensajes = findViewById(R.id.rvMensajes);
        txtMensaje = findViewById(R.id.txtMensaje);
        btnEnviar  = findViewById(R.id.btnEnviar);
        btnEnviaFoto = findViewById(R.id.btnEnviarFoto);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//Sala de chat
        storage = FirebaseStorage.getInstance();


        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes .setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar(txtMensaje.getText().toString(), nombre.getText().toString(), "", "1", ServerValue.TIMESTAMP));
                m = txtMensaje.getText().toString();
                esp();
                madre();
                txtMensaje.setText("");
            }
        });

        btnEnviaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una imagen: "), PHOTO_SENT);
            }
        });


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MensajeRecibir m = dataSnapshot.getValue(MensajeRecibir.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PHOTO_SENT && resultCode == RESULT_OK) {
                Uri u = data.getData();

                storageReference = storage.getReference("imagenes_mensajeria");
                final StorageReference referenciaFoto = storageReference.child("image "+u.getLastPathSegment());
                referenciaFoto.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {


                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        referenciaFoto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //Mensaje m = new Mensaje(nombre.getText().toString(), "Jonathan ha enviado una foto", uri.toString(), "2", "", "00:00");
                                //databaseReference.push().setValue(m);

                            }
                        });

                        // mensajito msj = new mensajito(nombre1.getText().toString(), "Josue ha enviado una foto", ur.getResult().toString(), "2", "", "00:00");
                        //databaseReference.push().setValue(msj);
                    }
                });
            }
        }catch (Exception ex){
            Toast.makeText(this, "Error " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void esp(){
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            String token ="cyeNVEM0TWCh9AacgPPPcP:APA91bEHDimMDDZdmaRRQpFtxcuib-KnwnGzXPLLZeJe778WdRw-vS_WEDZIyGR6ADxlNU4N2uZrNlqNQKU5DjQh0x8QupFs9PV1J2chCM9P6oweybwzfw3IWy66IDSnGDf_MYGZeWw-";
            json.put("to", token);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "Padre");
            notificacion.put("detalle", m+"");

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

    private void madre(){
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            String token = "eZDDAHhmSn671Bmi5QHt1W:APA91bEN1bQv3vPbIZfLDgCoG2ZCdOLUCHJFGKSh6oCnlTb3AtzS5b1s-R8TNS-ov8b_zKS2k2zdPYSj1DtcG5RzerqYO8hBphf29nXyO-T5WxLe5BWcEWT9qbrWeMvTHV79sexBXHC8";
            json.put("to", token);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "Padre");
            notificacion.put("detalle", m+"");

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

}
