package com.example.hans.agrigo.LihatZona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import com.example.hans.agrigo.SiramZona.SiramZona;
import com.example.hans.agrigo.Storage.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatZona extends AppCompatActivity {
    private ListView recyclerView;
    SharedPrefManager sharedPrefManager;
    TextView txtTampil;
    private ListView listview;
    TextView Zonanumber,Zonanama,Zonanumber2,Zonanama2,Zonanumber3,Zonanama3,Zonanumber4,Zonanama4,ff;
    Button btnJadwal1, btnJadwal2, btnJadwal3, btnJadwal4, btnSiram1,
            btnSiram2, btnSiram3, btnSiram4;

    LinearLayout satu, dua, tiga, empat, lima;
    String Mac_Address,a,Nama,Nomor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lihat_zona );
        sharedPrefManager = new SharedPrefManager(LihatZona.this);

        satu = (LinearLayout) findViewById(R.id.satu);
        satu.setVisibility(View.GONE);
        dua = (LinearLayout) findViewById(R.id.dua);
        dua.setVisibility(View.GONE);
        tiga = (LinearLayout) findViewById(R.id.tiga);
        tiga.setVisibility(View.GONE);
        empat = (LinearLayout) findViewById(R.id.empat);
        empat.setVisibility(View.GONE);
        lima = (LinearLayout) findViewById(R.id.lima);
        lima.setVisibility(View.GONE);

        Zonanumber  = (TextView)findViewById(R.id.zonanumber);
        Zonanama    = (TextView)findViewById(R.id.zonaname);
        Zonanumber.setVisibility(View.VISIBLE);
        Zonanama.setVisibility(View.VISIBLE);

        Zonanumber2 = (TextView)findViewById(R.id.zonanumber2);
        Zonanama2   = (TextView)findViewById(R.id.zonaname2);
        Zonanumber2.setVisibility(View.VISIBLE);
        Zonanama2.setVisibility(View.VISIBLE);

        Zonanumber3 = (TextView)findViewById(R.id.zonanumber3);
        Zonanama3   = (TextView)findViewById(R.id.zonaname3);
        Zonanumber3.setVisibility(View.VISIBLE);
        Zonanama3.setVisibility(View.VISIBLE);

        Zonanumber4 = (TextView)findViewById(R.id.zonanumber4);
        Zonanama4   = (TextView)findViewById(R.id.zonaname4);
        Zonanumber4.setVisibility(View.VISIBLE);
        Zonanama4.setVisibility(View.VISIBLE);

        txtTampil   = (TextView) findViewById( R.id.tampilan );

        btnJadwal1 = (Button) findViewById(R.id.jadwal1) ;
        btnJadwal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama.getText().toString();
                String nomor = Zonanumber.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
                varIntent.putExtra("MAC", a);
                varIntent.putExtra("namaZona", nama);
                varIntent.putExtra("nomorZona", nomor);
                startActivity(varIntent);
                finish();
            }
        });
        btnJadwal2 = (Button) findViewById(R.id.jadwal2) ;
        btnJadwal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
            }
        });
        btnJadwal3 = (Button) findViewById(R.id.jadwal3) ;
        btnJadwal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
            }
        });
        btnJadwal4 = (Button) findViewById(R.id.jadwal4);
        btnJadwal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
            }
        });

        btnSiram1 = (Button) findViewById(R.id.siram1) ;
        btnSiram1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama.getText().toString();
                String nomor = Zonanumber.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                varIntent.putExtra("mAC",a);
                varIntent.putExtra("NamaZona", nama);
                varIntent.putExtra("NomorZona", nomor);
                startActivity(varIntent);
                finish();
            }
        });
        btnSiram2 = (Button) findViewById(R.id.siram2) ;
        btnSiram2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama2.getText().toString();
                String nomor = Zonanumber2.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                varIntent.putExtra("mAC",a);
                varIntent.putExtra("NamaZona", nama);
                varIntent.putExtra("NomorZona", nomor);
                startActivity(varIntent);
                finish();
            }
        });
        btnSiram3 = (Button) findViewById(R.id.siram3) ;
        btnSiram3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim2();
                String Mac = getIntent().getStringExtra( "mac");
                String nama = Zonanama3.getText().toString();
                String nomor = Zonanumber3.getText().toString();
                Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
                varIntent.putExtra("mAC",a);
                varIntent.putExtra("NamaZona", nama);
                varIntent.putExtra("NomorZona", nomor);
                startActivity(varIntent);
                finish();
            }
        });
        btnSiram4 = (Button) findViewById(R.id.siram4);
        btnSiram4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim2();
            }
        });

//        String Mac = getIntent().getStringExtra( "mac" );
//        Toast.makeText(this, ""+Mac_Address, Toast.LENGTH_SHORT).show();
        tampil();
        tampilMac();

    }

    public void kirim(){
        Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
        startActivity(varIntent);
        finish();
    }
    public void kirim2(){
        Intent varIntent = new Intent(LihatZona.this, SiramZona.class);
        startActivity(varIntent);
        finish();
    }

    public void tampilMac() {
        String Mac = getIntent().getStringExtra( "mac" );
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
        String Mac = getIntent().getStringExtra( "mac");
//        String nama = Zonanama.getText().toString();
//        Intent varIntent = new Intent(LihatZona.this, AturJadwalSiram.class);
//        varIntent.putExtra("mac", Mac);
//        varIntent.putExtra("namaZona", nama);
//        startActivity(varIntent);
//        finish();

        NetworkService api = InitRetrofit.getInstance().getApi();
        Call<ResponseBody> menuCall = api.TampilZona(Mac);
        menuCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        Log.i("pesan", obj.toString());
                        String device = obj.getString("device");
                        Log.i("pesan",  obj.getString("device"));
                        JSONArray array=new JSONArray(device);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobject = array.getJSONObject(i);
                            String mac = jsonobject.getString("mac");
                            String zona = jsonobject.getString("zona");
                            Mac_Address=jsonobject.getString("mac");
                            txtTampil.setText(Mac_Address);
                             a = txtTampil.getText().toString();
                            Log.d("mac",Mac_Address);
                            Log.d("isi:",mac);
                            Log.d("zona:",zona);
                            JSONArray ray=new JSONArray(zona);
                            Log.d("response api", response.body().toString());


                            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
                            for (int j = 0; j < ray.length(); j++) {
                                JSONObject obb = ray.getJSONObject(0);
                                String zona_number =obb.getString("zona_number");
                                String zona_name =obb.getString("zona_name");
                                Zonanama.setText(zona_name);
                                Zonanumber.setText("Nomor Zona"+zona_number);
                                Nomor=obb.getString("zona_number");
                                if (Zonanama.equals("")|| Zonanumber.equals("")) {
                                    satu.setVisibility(View.GONE);
                                }else {
                                    satu.setVisibility(View.VISIBLE);
                                }

                                JSONObject obb1 = ray.getJSONObject(1);
                                String zona_number1 =obb1.getString("zona_number");
                                String zona_name1 =obb1.getString("zona_name");
                                Zonanama2.setText(zona_name1);
                                Zonanumber2.setText("Nomor Zona = "+zona_number1);
                                if (Zonanama2.equals("")|| Zonanumber2.equals("")) {
                                    dua.setVisibility(View.GONE);
                                }else {
                                    dua.setVisibility(View.VISIBLE);
                                }
                                JSONObject obb2 = ray.getJSONObject(1);
                                String zona_number2 =obb1.getString("zona_number");
                                String zona_name2 =obb1.getString("zona_name");
                                Zonanama3.setText(zona_name2);
                                Zonanumber3.setText("Nomor Zona= "+zona_number2);
                                Log.d("zona",zona_number);
                                Log.d("zona:",zona_name);
                                Log.d("zona:",zona_number1);
                                Log.d("zona:",zona_name1);
                                if (Zonanama3.equals("")|| Zonanumber3.equals("")) {
                                    tiga.setVisibility(View.GONE);
                                }else {
                                    tiga.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }
}