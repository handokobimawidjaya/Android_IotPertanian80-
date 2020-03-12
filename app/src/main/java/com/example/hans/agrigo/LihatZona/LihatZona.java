package com.example.hans.agrigo.LihatZona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hans.agrigo.Config.AdapterDevice;
import com.example.hans.agrigo.Config.Item_Device;
import com.example.hans.agrigo.Config.Response_Device;
import com.example.hans.agrigo.LihatZona.Support.AdapterZona;
import com.example.hans.agrigo.LihatZona.Support.Item_Zona;
import com.example.hans.agrigo.LihatZona.Support.ResponZona;
import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.MenuJadwal.AturJadwalSiram;
import com.example.hans.agrigo.Network.InitRetrofit;
import com.example.hans.agrigo.Network.NetworkService;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.SetZona.SetZona;
import com.example.hans.agrigo.Storage.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatZona extends AppCompatActivity {
    private RecyclerView recyclerView;
    SharedPrefManager sharedPrefManager;
    TextView txtTampil;
    String mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lihat_zona );

        sharedPrefManager = new SharedPrefManager(LihatZona.this);
        recyclerView = (RecyclerView) findViewById(R.id.list_zona);
        recyclerView.setHasFixedSize(true);
        //GRID 2 kolom
        GridLayoutManager llm=new GridLayoutManager(LihatZona.this,1);
        recyclerView.setLayoutManager(llm);
        txtTampil = (TextView) findViewById( R.id.tampilan );
        tampil();
        tampilMac();
//        kirim();

    }

    public void kirim(){
        Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("mac", mac);
                   startActivity(varIntent);
                   finish();
    }

    public void tampilMac() {
         mac = getIntent().getStringExtra( "mac" );
        txtTampil.setText( mac );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    private void back() {
        Intent back = new Intent( LihatZona.this, MenuUtama.class );
        startActivity( back );
        finish();
    }

    public void tampil(){
        String d_mail=sharedPrefManager.getSPEmail();
        String mac = "dc:4f:22:7e:3f:fd";
        NetworkService api = InitRetrofit.getInstance().getApi();
        Call<ResponZona> menuCall = api.TampilZona(d_mail, mac);
        menuCall.enqueue(new Callback<ResponZona>() {
            @Override
            public void onResponse(Call<ResponZona> call, Response<ResponZona> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    List<Item_Zona> data_menu= response.body().getMenu();
                    boolean status = response.body().isStatus();
                    if (status){
                        AdapterZona adapter = new AdapterZona(LihatZona.this,data_menu);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(LihatZona.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponZona> call, Throwable t) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }
}