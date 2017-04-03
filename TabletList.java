package grocery.vajaralabs.com.grocery;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import common.SendMail;

public class TabletList extends AppCompatActivity {
    private ListView list_pharmacy;
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pharmacys");
        list_pharmacy = (ListView) findViewById(R.id.list_pharmacy);
        arrayList.add("Narcotics");
        arrayList.add("Caramiphen");
        arrayList.add("Carbetapentane ");
        arrayList.add("Caramiphen");
        arrayList.add("Dextromethorphan");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list_pharmacy.setAdapter(stringArrayAdapter);
        list_pharmacy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Dialog dialog = new Dialog(TabletList.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.buy);
                Button button = (Button) dialog.findViewById(R.id.btn_buy);
                TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
                final EditText editText=(EditText)dialog.findViewById(R.id.edt_pill);

                txt_title.setText(arrayList.get(position));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(editText.getText().toString().length() ==0)) {
                            new SendMail(TabletList.this,
                                    "ashokonline.1234@gmail.com",
                                    "Re: Order Summery",
                                    "Hi " + "Order List" + ":" + '\n' + '\n' +
                                            arrayList.get(position) + '\n' + '\n' + "Number Of Pills :" +
                                            editText.getText().toString() + '\n' + '\n').execute();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please Fill The Number Of Pills",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();

            }
        });
    }
}
