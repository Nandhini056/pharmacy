package grocery.vajaralabs.com.grocery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PharmacySearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pharmacy-Search");
    }
}
