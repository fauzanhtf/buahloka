package com.calcatz.buahloka;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListBuahActivity extends AppCompatActivity {

    //Firebase
    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseDaerah = mydatabase.getReference();

    //UI
    private Spinner spinner_daerah, spinner_sortby;
    private ImageButton img_logoBuah;
    private TextView tv_provinsiAsal;
    private GridView gv_daftarKebun;

    //Data
    private String pilihanBuah, pilihanProvinsi;

    private DaftarKebun daftarKebun;
    private NamaProvinsi namaProvinsi;

    private List<DaftarKebun> kebunList = new ArrayList<DaftarKebun>();
    private List<NamaProvinsi> nameprofinsi = new ArrayList<NamaProvinsi>();

    private DaftarKebunViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_buah);

        headerView();
        init();
        dataoffline();

        //Action
        tv_provinsiAsal.setText(pilihanProvinsi);

        switch (pilihanBuah){
            case "Jeruk":
                img_logoBuah.setImageResource(R.drawable.img_jeruk);
                break;
            case "Pisang":
                img_logoBuah.setImageResource(R.drawable.img_pisang);
                break;
            case "Apel":
                img_logoBuah.setImageResource(R.drawable.img_apel);
                break;
        }

        int sum = kebunList.size();

        for (int a = 0 ; a < sum ; a++){
            adapter = new DaftarKebunViewAdapter(ListBuahActivity.this,kebunList);
            gv_daftarKebun.setAdapter(adapter);

        }

    }

    private void dataoffline() {
        //Toko
        daftarKebun.setName("Toko Joko");
        daftarKebun.setPrice("100000");
        kebunList.add(daftarKebun);

        daftarKebun.setName("Toko Joni");
        daftarKebun.setPrice("50000");
        kebunList.add(daftarKebun);

        daftarKebun.setName("Toko Samid");
        daftarKebun.setPrice("60000");
        kebunList.add(daftarKebun);

        daftarKebun.setName("Toko Ojan");
        daftarKebun.setPrice("30000");
        kebunList.add(daftarKebun);

        daftarKebun.setName("Toko Putin");
        daftarKebun.setPrice("1500000");
        kebunList.add(daftarKebun);
    }

    private void init() {
        spinner_daerah = (Spinner)findViewById(R.id.spinnerDaerah);
        spinner_sortby = (Spinner)findViewById(R.id.spinnerSortby);

        img_logoBuah = (ImageButton)findViewById(R.id.img_logoBuah);

        tv_provinsiAsal = (TextView)findViewById(R.id.tx_provinsi);

        gv_daftarKebun = (GridView)findViewById(R.id.gv_data);
    }

    private void headerView() {
        //LoadBundle
        Bundle bundle;
        bundle = getIntent().getExtras();
        pilihanBuah = bundle.getString("jenisbuah");
        pilihanProvinsi = bundle.getString("pilihanprovinsi");


    }
}



class DaftarKebun{
    String name, price;

    public DaftarKebun() {
    }

    public DaftarKebun(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

class DaftarKebunViewAdapter extends BaseAdapter {

    private Context mContext;
    private Activity activity;

    private List<DaftarKebun> kebunList;

    private ArrayAdapter<DaftarKebun> arraylist;
    private LayoutInflater inflater;

    public DaftarKebunViewAdapter(Activity activity, List<DaftarKebun> kebunList) {
        this.activity = activity;
        this.kebunList = kebunList;
    }

    @Override
    public int getCount() {
        return kebunList.size();
    }

    @Override
    public Object getItem(int i) {
        return kebunList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater  = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.items_list_buah, null);

        TextView tx_nameShop = (TextView)itemView.findViewById(R.id.tx_nameShop);
        TextView tx_priceItem = (TextView)itemView.findViewById(R.id.tx_price);

        tx_nameShop.setText(kebunList.get(i).getName());
        tx_priceItem.setText(kebunList.get(i).getPrice());

        return itemView;
    }
}