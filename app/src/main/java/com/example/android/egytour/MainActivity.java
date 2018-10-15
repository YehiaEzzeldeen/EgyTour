package com.example.android.egytour;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    public static final String LOGINURL="http://192.168.1.3/login.php";
    public static final String KEY_email="email";
    public static final String KEY_password="password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        email=(EditText) findViewById(R.id.emailText);
        password=(EditText) findViewById(R.id.passwordText);
    }
    public void signup(View view){
        Intent x= new Intent(getBaseContext(),SignUpActivity.class);
        startActivity(x);


    }
    public void login(View view){
        final String EMAIL= email.getText().toString().trim();
        final String PASS= password.getText().toString().trim();
        if(EMAIL.equals("")||PASS.equals("")){
            Toast.makeText(MainActivity.this,"Some info is missing",Toast.LENGTH_SHORT).show();

        }
        else{
            StringRequest sr= new StringRequest(Request.Method.POST, LOGINURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {


                        JSONObject jsonObject = new JSONObject(response);
                        boolean responsestatus = jsonObject.getBoolean("success");
                        if(responsestatus){
                            String nn= jsonObject.getString("name");
                            Intent myintent= new Intent(MainActivity.this,WelcomeActivity.class);
                            myintent.putExtra("fullname",nn);
                            myintent.putExtra("e-mail",EMAIL);
                            startActivity(myintent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
                        }
                    }catch(JSONException e){
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MyActivity","kek");
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params= new HashMap<String, String>();
                    params.put(KEY_email,EMAIL);
                    params.put(KEY_password,PASS);


                    return params;
                }

            };
            RequestQueue rq= Volley.newRequestQueue(this);
            rq.add(sr);

        }
    }
}
