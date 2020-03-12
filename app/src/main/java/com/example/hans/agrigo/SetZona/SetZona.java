package com.example.hans.agrigo.SetZona;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hans.agrigo.LihatZona.LihatZona;
import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.MenuJadwal.AturJadwalSiram;
import com.example.hans.agrigo.MenuLogin.Login;
import com.example.hans.agrigo.MenuRegister.Registerr;
import com.example.hans.agrigo.Network.InitRetrofit;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.Storage.SharedPrefManager;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetZona extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;
    Button btnSimpan;
    TextView macAdd;
    EditText namaLahan;
    private Spinner spinnerNomor;

    String[] nomor ={"1","2","3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_set_zona );
        sharedPrefManager = new SharedPrefManager( this );

        spinnerNomor = (Spinner) findViewById(R.id.noLahan);
        spinnerNomor.setOnItemSelectedListener(this);
        ArrayAdapter no = new ArrayAdapter(this,android.R.layout.simple_spinner_item,nomor);
        no.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNomor.setAdapter(no);

        namaLahan = (EditText) findViewById( R.id.namaLahan );
        macAdd    = (TextView) findViewById( R.id.macAdd);
        tampilMac();

        btnSimpan = (Button) findViewById( R.id.btnSimpan );
        btnSimpan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namaLahan.getText().toString().equals("") || spinnerNomor.getSelectedItem().toString().equals("")){
                    namaLahan.setFocusable(false);
                    spinnerNomor.setFocusable(false);
                } else {
                    loading = ProgressDialog.show(SetZona.this,"Loading.....",null,true);
                    suksesSimpan();
                }
            }
        } );
    }

    public void tampilMac(){
        String mac = getIntent().getStringExtra( "mac" );
        macAdd.setText( mac );
    }

    public void suksesSimpan(){
        String nama  = namaLahan.getText().toString();
        String nomor = spinnerNomor.getSelectedItem().toString();
        String id  = sharedPrefManager.getSPEmail();
        String mac = macAdd.getText().toString();

        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().regisZona(id,mac,nama,nomor);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("msg").equals("Berhasil Nambah Zona")) {
                            Toast.makeText( SetZona.this, " Berhasil Simpan", Toast.LENGTH_SHORT).show();
                            Log.d("responsenya", response.body().toString());
//                            Intent intent = new Intent(SetZona.this, LihatZona.class);
//                            startActivity(intent);
//                            finish();
                            Intent varIntent = new Intent(SetZona.this, AturJadwalSiram.class);
                            varIntent.putExtra("mac", mac);
                            startActivity(varIntent);
                            finish();
                        } else {
                            String error_message = jsonRESULTS.getString("msg");
                            Toast.makeText(SetZona.this, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }else{
                    Toast.makeText(SetZona.this, " Gagal", Toast.LENGTH_SHORT).show();
                    Log.d("responsenya", response.body().toString());
                    loading.dismiss();

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Log.d("responsenya", t.toString());
                    Toast.makeText(SetZona.this,t.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    private void back() {
        Intent back = new Intent( SetZona.this, MenuUtama.class );
        startActivity( back );
        finish();
    }
}
