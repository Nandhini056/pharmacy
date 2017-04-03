package grocery.vajaralabs.com.grocery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewPrescrption extends AppCompatActivity {
    TextView txt_pill, txt_purpose, txt_count, txt_pres, txt_date;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescrption);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Prescrption");
        txt_pill = (TextView) findViewById(R.id.txt_pill);
        imageView = (ImageView) findViewById(R.id.imageView);
        txt_purpose = (TextView) findViewById(R.id.txt_purpose);
        txt_count = (TextView) findViewById(R.id.txt_count);
        txt_pres = (TextView) findViewById(R.id.txt_pres);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_pill = (TextView) findViewById(R.id.txt_pill);
        txt_pill = (TextView) findViewById(R.id.txt_pill);
        txt_pill.setText(getIntent().getStringExtra("pill"));
        txt_purpose.setText(getIntent().getStringExtra("purpose"));
        txt_count.setText(getIntent().getStringExtra("number"));
        txt_pres.setText(getIntent().getStringExtra("prescription"));
        txt_date.setText(getIntent().getStringExtra("date"));
        Picasso.with(this).load(getIntent().getStringExtra("image_path")).into(imageView);

    }
}
