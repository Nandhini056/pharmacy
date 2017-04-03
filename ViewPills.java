package grocery.vajaralabs.com.grocery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.AsyncPOST;
import common.Response;

public class ViewPills extends AppCompatActivity implements Response {
    private AsyncPOST asyncPOST;
    private ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<HashMap<String, String>>();
    private AdapterPills adapterPills;
    private ListView list_appointment;
    private List<NameValuePair> nameValuePairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pills);
        list_appointment = (ListView) findViewById(R.id.list_appointment);
    }

        @Override
        protected void onResume() {
            super.onResume();
            nameValuePairs=new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("pid",getIntent().getStringExtra("pid")));
                asyncPOST=new AsyncPOST(nameValuePairs,ViewPills.this, common.Configg.MAIN_URL+"pills_pharmacy.php",ViewPills.this);
                asyncPOST.execute();


        }

        @Override
        public void processFinish(String output) {
            if (!(hashMapArrayList.size() == 0)) {
                hashMapArrayList.clear();
            }
            try {

                JSONArray jsonArray = new JSONArray(output);
                if (jsonArray.length()==0){

                        Toast.makeText(getApplicationContext(),"Theres No Pill List.",Toast.LENGTH_SHORT).show();

                }
                HashMap<String, String> stringHashMap;
                for (int i = 0; i < jsonArray.length(); i++) {
                    stringHashMap = new HashMap<String, String>();
                    stringHashMap.put("pill", jsonArray.getJSONObject(i).getString("pill"));
                    stringHashMap.put("purpose", jsonArray.getJSONObject(i).getString("purpose"));
                    stringHashMap.put("prescription", jsonArray.getJSONObject(i).getString("prescrption"));
                    stringHashMap.put("image_path", jsonArray.getJSONObject(i).getString("image_path"));
                    stringHashMap.put("date", jsonArray.getJSONObject(i).getString("date"));
                    stringHashMap.put("number", jsonArray.getJSONObject(i).getString("number"));

                    hashMapArrayList.add(stringHashMap);

                }
                adapterPills = new AdapterPills(ViewPills.this, hashMapArrayList, "");
                list_appointment.setAdapter(adapterPills);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }