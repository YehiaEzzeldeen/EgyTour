package com.example.android.egytour;


import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.Constants;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText name,email,password,age,nationality,phone;
    String URL="http://192.168.1.3/register.php";
    public static final String NAME="name";
    public static final String EMAIL="email";
    public static final String PASS="password";
    public static final String AGE="age";
    public static final String NATION="nationality";
    public static final String PHONE="phone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        name=(EditText)findViewById(R.id.nameText);
        email= (EditText) findViewById(R.id.emailText);
        password= (EditText) findViewById(R.id.passwordText);
        age= (EditText) findViewById(R.id.ageText);
        nationality= (EditText) findViewById(R.id.countryText);
        phone= (EditText) findViewById(R.id.phoneText);

    }
    public void moveBack(View view){
        Intent x= new Intent(getBaseContext(),MainActivity.class);
        startActivity(x);
    }
    public void tryregister(View view){
        Log.d("MyActivity","hi");
        final String n =name.getText().toString().trim();
        final String e =email.getText().toString().trim();
        final String p =password.getText().toString().trim();
        final String a =age.getText().toString().trim();
        final String na =nationality.getText().toString().trim();
        final String ph =phone.getText().toString().trim();
        if(n.equals("")|| e.equals("")|| p.equals("")|| a.equals("")|| na.equals("")|| ph.equals("")){
            Toast.makeText(SignUpActivity.this, "Some info is missing",Toast.LENGTH_SHORT).show();

        }
        else{
            RequestQueue rq= Volley.newRequestQueue(this);
            StringRequest sr= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("MyActivity","hi33");
                    Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MyActivity","kek");
                    Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params= new HashMap<String, String>();
                    params.put(NAME,n);
                    params.put(EMAIL,e);
                    params.put(PASS,p);
                    params.put(AGE,a);
                    params.put(NATION,na);
                    params.put(PHONE,ph);

                    return params;
                }

            };
            rq.add(sr);
            Intent myintent= new Intent(getBaseContext(),WelcomeActivity.class);
            myintent.putExtra("fullname",n);
            myintent.putExtra("e-mail",e);
            startActivity(myintent);
        }

    }
}
