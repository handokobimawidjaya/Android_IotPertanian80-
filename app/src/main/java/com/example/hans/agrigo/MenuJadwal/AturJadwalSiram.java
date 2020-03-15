package com.example.hans.agrigo.MenuJadwal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hans.agrigo.SiramZona.GlobalVariablee.GlobalVariableJadwal;
import com.example.hans.agrigo.SiramZona.Helper.RMQ;
import com.example.hans.agrigo.LihatZona.LihatZona;
import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.Storage.SharedPrefManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

public class AturJadwalSiram extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    SharedPrefManager sharedPrefManager;
    TextView edtJam, edtMenit, txtUlang, txtUlang1, txtUlang2, txtUlang3, txtNama, txtNo, txtMac;
    CheckBox checkBox;

    private Spinner spinner1, spinner2, spinnerUlang, spinnerUlang1, spinnerUlang2;
    Button btnJadZona1, btnWaktu;
    GlobalVariableJadwal gb = new GlobalVariableJadwal();
    RMQ rmq = new RMQ();

    String[] times1={"59","58","57","56","55","54","53","52","51","50","49","48","47","46","45","44","43","42","41","40","39",
            "38","37","36","35","34"
            ,"33","32","31","30","29","28","27","26","25","24","23","22","21","20","19","18","17","16","15","14",
            "13","12","11","10","9","8","7","6","5","4","3","2","1","0"};

    String[] times2={"59","58","57","56","55","54","53","52","51","50","49","48","47","46","45","44","43","42","41","40","39",
            "38","37","36","35","34"
            ,"33","32","31","30","29","28","27","26","25","24","23","22","21","20","19","18","17","16","15","14",
            "13","12","11","10","9","8","7","6","5","4","3","2","1","0"};

    String[] repeat={"1 Jam","4 Jam","8 Jam","12 Jam"};

    String[] repeat2={"1 Jam","6 Jam","12 Jam","24 Jam"};

    String[] repeat3={"1","2","3","4","5","6","7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_jadwal_siram);
        sharedPrefManager = new SharedPrefManager(this);
        edtJam     = (TextView) findViewById( R.id.jamJadwal );
        edtMenit   = (TextView) findViewById( R.id.menitJadwal );

        checkBox    = (CheckBox)findViewById( R.id.ulang );
        txtUlang    = (TextView) findViewById( R.id.txtUlang );
        txtUlang.setVisibility( View.GONE );
        txtUlang1   = (TextView) findViewById( R.id.txtUlang1 );
        txtUlang2   = (TextView) findViewById( R.id.txtUlang2 );
        txtUlang2.setVisibility( View.GONE );
        txtUlang3   = (TextView) findViewById( R.id.txtUlang3 );
        txtNama     = (TextView) findViewById( R.id.namaZona );
        txtNo     = (TextView) findViewById( R.id.noZona );
        tampilLahan();
        txtMac     = (TextView) findViewById( R.id.macAddress );
        tampilMac();
        myCalendar = Calendar.getInstance();

        spinnerUlang = (Spinner) findViewById(R.id.spinnerUlang);
        spinnerUlang.setOnItemSelectedListener(this);
        ArrayAdapter a = new ArrayAdapter(this,android.R.layout.simple_spinner_item,repeat);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUlang.setAdapter(a);
        spinnerUlang.setVisibility( View.GONE );

        spinnerUlang1 = (Spinner) findViewById(R.id.spinnerUlang1);
        spinnerUlang1.setOnItemSelectedListener(this);
        ArrayAdapter b = new ArrayAdapter(this,android.R.layout.simple_spinner_item,repeat3);
        b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUlang1.setAdapter(b);

        spinnerUlang2 = (Spinner) findViewById(R.id.spinnerUlang2);
        spinnerUlang2.setOnItemSelectedListener(this);
        ArrayAdapter aaaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,repeat2);
        aaaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUlang2.setAdapter(aaaa);
        spinnerUlang2.setVisibility( View.GONE );

        spinner1 = (Spinner) findViewById(R.id.spinnerJadwal);
        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,times1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(aa);

        spinner2 = (Spinner) findViewById(R.id.spinnerJadwal2);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,times2);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(aaa);


        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        btnWaktu = (Button)findViewById( R.id.btnWaktu );
        btnWaktu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AturJadwalSiram.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        edtJam.setText( ""+hour );
                        edtMenit.setText( ""+minute );
                    }
                },            hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        } );

        btnJadZona1 = (Button) findViewById( R.id.btnJadZona1 );
        btnJadZona1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rmq.setupConnectionFactory();
                    zona1();
                    sukses();
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
        } );

        checkBox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    txtUlang.setVisibility( View.VISIBLE );
                    spinnerUlang.setVisibility( View.VISIBLE );
                    txtUlang2.setVisibility( View.VISIBLE );
                    spinnerUlang2.setVisibility( View.VISIBLE );
                } else {
                    txtUlang.setVisibility( View.GONE );
                    spinnerUlang.setVisibility( View.GONE );
                    txtUlang2.setVisibility( View.GONE );
                    spinnerUlang2.setVisibility( View.GONE );
                }
            }
        } );
    }

    private void tampilLahan(){
        String namaZona = getIntent().getStringExtra( "namaZona" );
        String nomor = getIntent().getStringExtra( "nomorZona" );
        txtNama.setText( namaZona );
        txtNo.setText(nomor);
    }

    private void tampilMac() {
        String mac = getIntent().getStringExtra( "MAC" );
        txtMac.setText( mac );
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        edtTanggal.setText(sdf.format(myCalendar.getTime()));
    }

    private void sukses() {
        Intent pindah = new Intent( AturJadwalSiram.this, MenuUtama.class );
        startActivity( pindah );
        finish();
    }

    private void zona1() throws InterruptedException, NoSuchAlgorithmException,
            KeyManagementException, TimeoutException, URISyntaxException, IOException{

        String waktu1 = spinner1.getSelectedItem().toString();
        String waktu2 = spinner2.getSelectedItem().toString();
        String nama   = txtNama.getText().toString();
        String mac = txtMac.getText().toString();
        String email  = sharedPrefManager.getSPEmail();

        String waktuUlang = spinnerUlang.getSelectedItem().toString();
        String waktuUlang1 = spinnerUlang1.getSelectedItem().toString();
        String waktuUlang2 = spinnerUlang2.getSelectedItem().toString();
        if (waktuUlang.equals( "1 Jam" ) && waktuUlang2.equals( "24 Jam" )){
            waktuUlang = "1";
            waktuUlang2 = "24";
        } else if (waktuUlang.equals( "1 Jam" ) && waktuUlang2.equals( "12 Jam" )){
            waktuUlang = "1";
            waktuUlang2 = "12";
        } else if (waktuUlang.equals( "1 Jam" ) && waktuUlang2.equals( "6 Jam" )){
            waktuUlang = "1";
            waktuUlang2 = "6";
        } else if (waktuUlang.equals( "1 Jam" ) && waktuUlang2.equals( "1 Jam" )){
            waktuUlang = "1";
            waktuUlang2 = "1";
        } else if (waktuUlang.equals( "4 Jam" ) && waktuUlang2.equals( "1 Jam" )){
            waktuUlang = "4";
            waktuUlang2 = "1";
        } else if (waktuUlang.equals( "4 Jam" ) && waktuUlang2.equals( "6 Jam" )){
            waktuUlang = "4";
            waktuUlang2 = "6";
        } else if (waktuUlang.equals( "4 Jam" ) && waktuUlang2.equals( "12 Jam" )){
            waktuUlang = "4";
            waktuUlang2 = "12";
        } else if (waktuUlang.equals( "4 Jam" ) && waktuUlang2.equals( "24 Jam" )){
            waktuUlang = "4";
            waktuUlang2 = "24";
        } else if (waktuUlang.equals( "8 Jam" ) && waktuUlang2.equals( "1 Jam" )){
            waktuUlang = "8";
            waktuUlang2 = "1";
        } else if (waktuUlang.equals( "8 Jam" ) && waktuUlang2.equals( "6 Jam" )){
            waktuUlang = "8";
            waktuUlang2 = "6";
        } else if (waktuUlang.equals( "8 Jam" ) && waktuUlang2.equals( "12 Jam" )){
            waktuUlang = "8";
            waktuUlang2 = "12";
        } else if (waktuUlang.equals( "8 Jam" ) && waktuUlang2.equals( "24 Jam" )){
            waktuUlang = "8";
            waktuUlang2 = "24";
        } else if (waktuUlang.equals( "12 Jam" ) && waktuUlang2.equals( "1 Jam" )){
            waktuUlang = "12";
            waktuUlang2 = "1";
        } else if (waktuUlang.equals( "12 Jam" ) && waktuUlang2.equals( "6 Jam" )){
            waktuUlang = "12";
            waktuUlang2 = "6";
        } else if (waktuUlang.equals( "12 Jam" ) && waktuUlang2.equals( "12 Jam" )){
            waktuUlang = "12";
            waktuUlang2 = "12";
        } else if (waktuUlang.equals( "12 Jam" ) && waktuUlang2.equals( "24 Jam" )){
            waktuUlang = "12";
            waktuUlang2 = "24";
        }

        String jamJadwal = edtJam.getText().toString();
        String menitJadwal = edtMenit.getText().toString();
        int c = Integer.parseInt(waktuUlang);
        int d = Integer.parseInt(waktuUlang2);
        int a = Integer.parseInt( waktu1 );
        int b = Integer.parseInt( waktu2 );
        int detik = b * 1000;
        int menit = a * 60000;
        int total = menit + detik;
        String hasil = Integer.toString( total );
//        String gfgg = getIntent().getStringExtra( "nomorZona" );
        String pesan = getIntent().getStringExtra( "nomorZona" );
        if(pesan.equals( "1" )){
            pesan = "0011";
        } else if (pesan.equals( "2" )){
            pesan = "0101";
        } else if (pesan.equals( "3" )){
            pesan = "0110";
        }

        if (times1.equals(null) || times2.equals(null) || c>d){
            Toast.makeText( getApplicationContext(),"Gagal mengatur jadwal"+"\n"
                    +"Lama waktu terlalu pendek", Toast.LENGTH_SHORT ).show();
        }else if (checkBox.isChecked()){
            rmq.setupConnectionFactory();
            rmq.publish( nama+email+"#"+mac+"#"+pesan+"#"+hasil+
                    "#"+waktuUlang1+"#"+waktuUlang2+"#"+waktuUlang+"#"+jamJadwal+"#"+menitJadwal+"#daily");
            Toast.makeText( getApplicationContext(),"Sukses Mengatur Penjadwalan Harian", Toast.LENGTH_SHORT ).show();
        } else {
            rmq.setupConnectionFactory();
            rmq.publish( nama+email+"#"+mac+"#"+pesan+"#"+hasil+
                    "#"+waktuUlang1+"#"+"0"+"#"+"0"+"#"+jamJadwal+"#"+menitJadwal+"#daily");
            Toast.makeText( getApplicationContext(),"Sukses Mengatur Penjadwalan", Toast.LENGTH_SHORT ).show();
        }
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
        Intent a = new Intent( AturJadwalSiram.this, MenuUtama.class );
        startActivity( a );
        finish();
    }
}