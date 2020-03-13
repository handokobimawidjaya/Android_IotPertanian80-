package com.example.hans.agrigo.MenuScanBarcode;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.example.hans.agrigo.BuildConfig;
import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.MenuLogin.Login;
import com.example.hans.agrigo.MenuRegister.Registerr;
import com.example.hans.agrigo.Network.InitRetrofit;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.Storage.SharedPrefManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDevice extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.scan)
    Button ScanBarcode;
    //    @BindView(R.id.adddevice)
//    Button Tambahdevice;
    @BindView(R.id.id_device)
    EditText IDDevice;
    @BindView(R.id.mac)
    EditText Mac;
    @BindView(R.id.namaDevice)
    EditText Namadevice;
    @BindView(R.id.main)
    RelativeLayout main;
    @BindView(R.id.cv)
    CardView formdevice;

    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;
    String Latitude,Longitude;
    private IntentIntegrator intentIntegrator;
    String Mac_Addres,mac,Device_Code,RandomCode,Type;

    String user = "iot_pertanian";
    String pass = "iotpertanian";
    String host = "167.205.7.226";
    String vhost = "/iotpertanian";
    ConnectionFactory factory = new ConnectionFactory();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ButterKnife.bind(this);
        ScanBarcode.setOnClickListener(this);
        sharedPrefManager = new SharedPrefManager(this);
        formdevice.setVisibility(View.GONE);
        ScanBarcode.setVisibility(View.VISIBLE);
        checkPermission();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Device tidak ditemukan, Ulangi Kembali ", Toast.LENGTH_SHORT).show();
            } else {
//                prefZona.saveSPString(SharePrefZona.SP_MAC, result.getContents());
                Mac_Addres=result.getContents();
//                String mac1=prefZona.getSP_Mac();
//                String mac2=prefZona.getSP_Mac2();
//                String mac3=prefZona.getSP_Mac3();
//                String mac4=prefZona.getSP_Mac4();
//                if (mac1.equals(result.getContents())){
//                    prefZona.saveSPString(SharePrefZona.SP_MAC, result.getContents());
//                }else {
//                    prefZona.saveSPString(SharePrefZona.SP_MAC2, result.getContents());
//                }
////                Toast.makeText(this, ""+prefZona.getSP_Mac(), Toast.LENGTH_SHORT).show();
                getMac();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        // inisialisasi IntentIntegrator(scanQR)
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }
    public void onBackPressed() {
        super.onBackPressed();
        goBackMenu();
    }

    public void goBackMenu() {
        startActivity(new Intent(AddDevice.this, Login.class));
        finish();
    }
    private void getMac(){
        String d_macA = Mac_Addres;
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().getmac(d_macA);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
//                        Toast.makeText(AddDevice.this, "Berhasil", Toast.LENGTH_SHORT).show();
//
                    try {
                        JSONArray jsonarray = new JSONArray(response.body().string());
                        Log.i("pesan", jsonarray.toString());
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            mac = jsonobject.getString("mac");
                            Type = jsonobject.getString("devicestype");
                            RegisterDevice();
//                                    Log.d("device", String.valueOf(jsonarray));
                            Log.d("isi:",mac+Type);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//
                } else {
                    Toast.makeText(AddDevice.this, "Device Tidak Terdaftar", Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Toast.makeText(AddDevice.this, "Server Tidak Merespon/Periksa koneksi Internet anda", Toast.LENGTH_SHORT).show();
                    Log.v("debug", "onFailure: ERROR > " + t.toString());
//                        Toast.makeText(AddDevice.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void RegisterDevice() {
        String userId=sharedPrefManager.getSpId();
        String guid=sharedPrefManager.getSpGuid();
        Log.i("giud",sharedPrefManager.getSpGuid());
//        Toast.makeText(this, ""+sharedPrefManager.getSpGuid(), Toast.LENGTH_SHORT).show();
        String d_mac =Mac_Addres;
        int code = (int) ((new Date().getTime() / 1000L)%Integer.MIN_VALUE);
        if(Type.equals("Sensor")||Type.equals("sensor")){
            RandomCode="SS"+code;
//                    Toast.makeText(this,"SS"+ Type, Toast.LENGTH_SHORT).show();
        }else {
            RandomCode="ACT"+code;
//                    Toast.makeText(this,"ACT"+Type, Toast.LENGTH_SHORT).show();
            Log.i("ini tipenya",Type);
        }
        String d_code = RandomCode;
        if (d_code.equals("")) {
            showSnackbar();
        } else if (d_mac.equals("")) {
            showSnackbar();
        } else {
            retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().Aktivasi_Device(userId,d_mac,d_code,guid);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
//                        loading.dismiss();
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            if (jsonRESULTS.getString("msg").equals("Berhasil")){
                                Toast.makeText(AddDevice.this, "Aktivasi Device Berhasil :)", Toast.LENGTH_SHORT).show();
//                                publishtrue();
                                Intent intent=new Intent(AddDevice.this,MenuUtama.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AddDevice.this, "Gagal,Ulangi Kembali :(", Toast.LENGTH_SHORT).show();
                                String error_message = jsonRESULTS.getString("msg");
                                Toast.makeText(AddDevice.this, error_message, Toast.LENGTH_SHORT).show();
                                Log.d("Pesan",error_message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
//                        } catch (NoSuchAlgorithmException e) {
//                            e.printStackTrace();
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        } catch (TimeoutException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (KeyManagementException e) {
//                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(AddDevice.this, "Gagal,Ulangi Kembali :(", Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    try {
                        Toast.makeText(AddDevice.this, "Gagal,Ulangi Kembali :(", Toast.LENGTH_SHORT).show();
                        Log.d("responsenya", t.toString());
                        Toast.makeText(AddDevice.this,t.toString(),Toast.LENGTH_LONG).show();
                        publishfalse();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void showSnackbar() {
        Snackbar snackbar = Snackbar.make(main, "Harus disi", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ulangi", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(main, "Silahkan ulangi", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        Namadevice.setFocusableInTouchMode(true);
                        IDDevice.setFocusableInTouchMode(true);
//                        jumlahbeli.setFocusableInTouchMode(true);
//                        Password.setFocusableInTouchMode(true);
                    }
                });
        snackbar.show();
    }
    public void publishtrue() throws InterruptedException, NoSuchAlgorithmException, KeyManagementException, TimeoutException, URISyntaxException, IOException {
        String pesan="1";
        setupConnectionFactory();
        publish(pesan);
    }

    public void publishfalse() throws InterruptedException, NoSuchAlgorithmException, KeyManagementException, TimeoutException, URISyntaxException, IOException {
        String pesan="0";
        setupConnectionFactory();
        publish(pesan);
    }
    public void setupConnectionFactory() {
        try {
            factory.setAutomaticRecoveryEnabled(false);
            factory.setUri("amqp://" + user + ":" + pass + "@" + host);
            factory.setVirtualHost(vhost);
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
    public  void  publish(String message) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
        String queue_name_publish ="mqtt-subscription-"+Mac_Addres+"qos0";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = factory.newConnection();
        Log.d("ConnectionRMQ", "publish: "+connection.isOpen());
        Channel channel = connection.createChannel();
        Log.d("ChannelRMQ", "publish: "+channel.isOpen());
        String messageOn = message ;
        channel.basicPublish("", queue_name_publish,null,messageOn.getBytes());
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void checkPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }
}