package grocery.vajaralabs.com.grocery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PharmacyList extends AppCompatActivity {
    private ListView list_pharmacy;
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pharmacys");
        list_pharmacy = (ListView) findViewById(R.id.list_pharmacy);
        arrayList.add("Apollo Pharmacy");
        arrayList.add("Pasumai Pharmacy");
        arrayList.add("The Arya Vaidya Pharmacy ");
        arrayList.add("Healthcare Pharmacy");
        arrayList.add("Thulasi Pharmacy");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list_pharmacy.setAdapter(stringArrayAdapter);
        list_pharmacy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mapintent = new Intent(PharmacyList.this,TabletList.class);
                startActivity(mapintent);

            }
        });


    }
}
