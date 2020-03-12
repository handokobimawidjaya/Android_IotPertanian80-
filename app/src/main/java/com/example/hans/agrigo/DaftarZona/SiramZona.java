package com.example.hans.agrigo.DaftarZona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hans.agrigo.DaftarZona.GlobalVariablee.GlobalVariable;
import com.example.hans.agrigo.DaftarZona.Helper.RabbitMq;
import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.R;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

public class SiramZona extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText edtTanggal, edtJam;

    private Spinner spinner1, spinner2;
    Button btnZona1, btnZona2, btnZona3;
    GlobalVariable gb = new GlobalVariable();
    RabbitMq rmq = new RabbitMq();

    String[] times1={"59","58","57","56","55","54","53","52","51","50","49","48","47","46","45","44","43","42","41","40","39",
            "38","37","36","35","34"
            ,"33","32","31","30","29","28","27","26","25","24","23","22","21","20","19","18","17","16","15","14",
            "13","12","11","10","9","8","7","6","5","4","3","2","1","0"};

    String[] times2={"59","58","57","56","55","54","53","52","51","50","49","48","47","46","45","44","43","42","41","40","39",
            "38","37","36","35","34"
            ,"33","32","31","30","29","28","27","26","25","24","23","22","21","20","19","18","17","16","15","14",
            "13","12","11","10","9","8","7","6","5","4","3","2","1","0"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siram_zona);
//        edtTanggal = (EditText)findViewById( R.id.tanggalZona );
//        edtJam     = (EditText)findViewById( R.id.jamZona );
        myCalendar = Calendar.getInstance();
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

//        edtTanggal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog( SiramZona.this, date, myCalendar
//                        .get( Calendar.YEAR ), myCalendar.get( Calendar.MONTH ),
//                        myCalendar.get( Calendar.DAY_OF_MONTH ) ).show();
//            }
//        });
//
//        edtJam.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(SiramZona.this, new TimePickerDialog.OnTimeSetListener() {
//
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        edtJam.setText(selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//            }
//        } );

        btnZona1 = (Button) findViewById( R.id.btnZona1 );
        btnZona1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rmq.setupConnectionFactory();
                    zona1();
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

        btnZona2 = (Button) findViewById( R.id.btnZona2 );
        btnZona2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rmq.setupConnectionFactory();
                    zona2();
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

        btnZona3 = (Button) findViewById( R.id.btnZona3 );
        btnZona3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    rmq.setupConnectionFactory();
                    zona3();
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

        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,times1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(aa);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,times2);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(aaa);

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtTanggal.setText(sdf.format(myCalendar.getTime()));
    }

    private void zona1() throws InterruptedException, NoSuchAlgorithmException,
            KeyManagementException, TimeoutException, URISyntaxException, IOException{
        String waktu1 = spinner1.getSelectedItem().toString();
        String waktu2 = spinner2.getSelectedItem().toString();

        int a = Integer.parseInt( waktu1 );
        int b = Integer.parseInt( waktu2 );
        int detik = b * 1000;
        int menit = a * 60000;
        int total = menit + detik;


        if (times1.equals(null)){
            Toast.makeText( getApplicationContext(),"gagal", Toast.LENGTH_SHORT ).show();
        } else {
            Toast.makeText( getApplicationContext(),"Sukses", Toast.LENGTH_SHORT ).show();
        }

        String pesan = "0011";
        rmq.publish( pesan+"#"+total);
    }
    private void zona2() throws InterruptedException, NoSuchAlgorithmException,
            KeyManagementException, TimeoutException, URISyntaxException, IOException{
        String waktu1 = spinner1.getSelectedItem().toString();
        String waktu2 = spinner2.getSelectedItem().toString();

        int a = Integer.parseInt( waktu1 );
        int b = Integer.parseInt( waktu2 );
        int detik = b * 1000;
        int menit = a * 1000;
        int total = menit + detik;


        if (times1.equals(null)){
            Toast.makeText( getApplicationContext(),"gagal", Toast.LENGTH_SHORT ).show();
        } else {
            Toast.makeText( getApplicationContext(),"Sukses", Toast.LENGTH_SHORT ).show();
        }

        String pesan = "0101";
        rmq.publish( pesan+"#"+total);
    }
    private void zona3() throws InterruptedException, NoSuchAlgorithmException,
            KeyManagementException, TimeoutException, URISyntaxException, IOException{
        String waktu1 = spinner1.getSelectedItem().toString();
        String waktu2 = spinner2.getSelectedItem().toString();

        int a = Integer.parseInt( waktu1 );
        int b = Integer.parseInt( waktu2 );
        int detik = b * 1000;
        int menit = a * 1000;
        int total = menit + detik;


        if (times1.equals(null)){
            Toast.makeText( getApplicationContext(),"gagal", Toast.LENGTH_SHORT ).show();
        } else {
            Toast.makeText( getApplicationContext(),"Sukses", Toast.LENGTH_SHORT ).show();
        }

        String pesan = "0110";
        rmq.publish( pesan+"#"+total);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(), times3[position]+times2[position]+times[position], Toast.LENGTH_LONG).show();
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
        Intent a = new Intent( SiramZona.this, MenuUtama.class );
        startActivity( a );
        finish();
    }
}