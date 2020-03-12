package com.example.hans.agrigo.Config;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hans.agrigo.DaftarZona.SiramZona;
import com.example.hans.agrigo.LihatZona.LihatZona;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.SetZona.SetZona;

import java.util.List;

public class AdapterDevice extends RecyclerView.Adapter<AdapterDevice.MyViewHolder> {
    Context context;
    List<Item_Device> menu;

    public AdapterDevice(Context context, List<Item_Device> data_menu) {
        this.context = context;
        this.menu= data_menu;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_device, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // Set widget
        holder.nama.setText(menu.get(position).getNama());
        holder.harga.setText(menu.get(position).getMac());
//        final String urlGambar = InitRetrofit.BASE_URL+"../Images/" + menu.get(position).getFoto();
//        Picasso.with(context).load(urlGambar).into(holder.gambarmenu);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Intent varIntent = new Intent(context, SetZona.class);
                varIntent.putExtra("mac", menu.get(position).getMac());
                context.startActivity(varIntent);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Intent varIntent = new Intent(context, LihatZona.class);
                varIntent.putExtra("mac", menu.get(position).getMac());
                context.startActivity(varIntent);
            }
        });

        holder.siram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Intent varIntent = new Intent(context, SiramZona.class);
                varIntent.putExtra("mac", menu.get(position).getMac());
                context.startActivity(varIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama,harga;
        Button add, view, siram;

        public MyViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.Nama);
            harga = (TextView) itemView.findViewById(R.id.Mac_);
            add = (Button) itemView.findViewById( R.id.AddZona );
            view = (Button) itemView.findViewById( R.id.tampilanZona );
            siram = (Button) itemView.findViewById( R.id.siram);

        }
    }
}