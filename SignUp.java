package grocery.vajaralabs.com.grocery;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import java.util.Random;



public class SignUp extends AppCompatActivity  {

    private AutoCompleteTextView edt_first_name;
    private AutoCompleteTextView edt_last_name;
    private AutoCompleteTextView edt_mobile;
    private AutoCompleteTextView edt_email;
    private AutoCompleteTextView edt_pwd;
    private Button btn_submit;
    private AutoCompleteTextView edt_confirmpwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edt_first_name = (AutoCompleteTextView) findViewById(R.id.edt_first_name);
        edt_last_name = (AutoCompleteTextView) findViewById(R.id.edt_last_name);
        edt_mobile = (AutoCompleteTextView) findViewById(R.id.edt_mobile);
        edt_email = (AutoCompleteTextView) findViewById(R.id.edt_email);
        edt_pwd = (AutoCompleteTextView) findViewById(R.id.edt_pwd);
        edt_confirmpwd = (AutoCompleteTextView) findViewById(R.id.edt_confirmpwd);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!signUpValidation()) {
                    Random rnd = new Random();
                    int n = 100000 + rnd.nextInt(900000);


                }
            }
        });

    }

    private boolean signUpValidation() {
        if (edt_first_name.getText().toString().equals("")) {
            edt_first_name.requestFocus();

            Toast.makeText(getApplicationContext(), "Kindly Fill Your First Name", Toast.LENGTH_SHORT).show();
            return true;

        }
        if (edt_last_name.getText().toString().equals("")) {
            edt_last_name.requestFocus();
            Toast.makeText(getApplicationContext(), "Kindly Fill Your Last Name", Toast.LENGTH_SHORT).show();
            return true;

        }
        if (edt_email.getText().toString().equals("")) {
            edt_email.requestFocus();
            Toast.makeText(getApplicationContext(), "Kindly Fill Your Email Detail", Toast.LENGTH_SHORT).show();
            return true;

        }
        if (!Config.isValidEmaillId(edt_email.getText().toString().trim())) {
            edt_email.requestFocus();
            Toast.makeText(getApplicationContext(), "enter valid email address", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (edt_mobile.getText().toString().equals("")) {
            edt_mobile.requestFocus();
            Toast.makeText(getApplicationContext(), "Mobile Number Should Not Empty.", Toast.LENGTH_SHORT).show();
            return true;

        }
        if (edt_pwd.getText().toString().equals("")) {
            edt_pwd.requestFocus();
            Toast.makeText(getApplicationContext(), "Password Should Not Empty.", Toast.LENGTH_SHORT).show();
            return true;

        }
        if (edt_confirmpwd.getText().toString().equals("")) {
            edt_confirmpwd.requestFocus();
            Toast.makeText(getApplicationContext(), "Confirm Your Password.", Toast.LENGTH_SHORT).show();
            return true;

        }
        if (!edt_confirmpwd.getText().toString().equals(edt_pwd.getText().toString())) {
            edt_pwd.requestFocus();
            Toast.makeText(getApplicationContext(), "Password Doesn't Match With Confirm Password.", Toast.LENGTH_SHORT).show();
            return true;

        }
        return false;
    }


}
