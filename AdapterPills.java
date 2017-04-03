package grocery.vajaralabs.com.grocery;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterPills extends BaseAdapter {


    ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<HashMap<String, String>>();

    private Context context;

    LayoutInflater inflater;

    private String fragment;
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

    private String str_currency = "";

    public AdapterPills(Context context, ArrayList<HashMap<String, String>> hashMapArrayList, String fragment) {
        this.hashMapArrayList = hashMapArrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return hashMapArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return hashMapArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return hashMapArrayList.size();
    }

    @Override
    public View getView(final int posistion, View convertView, ViewGroup viewGroup) {

        MyViewHolder mViewHolder;
//
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.pill_list, viewGroup, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.txt_pill.setText(hashMapArrayList.get(posistion).get("pill"));
        mViewHolder.txt_purpose.setText(hashMapArrayList.get(posistion).get("purpose"));
        mViewHolder.txt_pres.setText(hashMapArrayList.get(posistion).get("prescription"));
        mViewHolder.txt_date.setText(hashMapArrayList.get(posistion).get("date"));
        mViewHolder.txt_count.setText(hashMapArrayList.get(posistion).get("number"));
//        mViewHolder.btn_availablity.setText(hashMapArrayList.get(posistion).get("availability"));
        mViewHolder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewPrescrption.class);

                intent.putExtra("pill", hashMapArrayList.get(posistion).get(("pill")));
                intent.putExtra("purpose", hashMapArrayList.get(posistion).get("purpose"));
                intent.putExtra("number", hashMapArrayList.get(posistion).get("number"));
                intent.putExtra("prescription", hashMapArrayList.get(posistion).get("prescription"));

                intent.putExtra("date", hashMapArrayList.get(posistion).get("date"));

                intent.putExtra("image_path", hashMapArrayList.get(posistion).get("image_path"));






                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(hashMapArrayList.get(posistion).get("image_path")).into(mViewHolder.img_path);


//        }
        return convertView;
    }

    private class MyViewHolder {
        private final TextView txt_date;
        private final TextView txt_pill;
        private final TextView txt_purpose;
        private final TextView txt_pres,txt_count;
        private Button  btn_view;
        private ImageView img_path;

        public MyViewHolder(View item) {
            txt_date = (TextView) item.findViewById(R.id.txt_date);
            txt_pill = (TextView) item.findViewById(R.id.txt_pill);
            txt_purpose = (TextView) item.findViewById(R.id.txt_purpose);
            txt_pres = (TextView) item.findViewById(R.id.txt_pres);
            txt_count = (TextView) item.findViewById(R.id.txt_count);
//            btn_appointment = (Button) item.findViewById(R.id.btn_view_status);
            btn_view = (Button) item.findViewById(R.id.btn_view);
            img_path=(ImageView) item.findViewById(R.id.img_path);


        }
    }


}
