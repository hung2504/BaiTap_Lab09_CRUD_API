package com.example.baitap_lab09_crud_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class AddEmployee extends AppCompatActivity {
    String url = "https://60ab17a75a4de40017cc9514.mockapi.io/employees/";
    private EditText tvId, tvName,tvGen,tvAge,tvMail;
    private Button btnSave,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        tvName = findViewById(R.id.edtName1);
        tvGen = findViewById(R.id.edtGender1);
        tvAge = findViewById(R.id.edtAge1);
        tvMail = findViewById(R.id.edtEmail1);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel1);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEmployee.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostApi(url);
                Intent intent = new Intent(AddEmployee.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void PostApi(String url){
        StringRequest stringRequest = new StringRequest( Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddEmployee.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddEmployee.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("employeeName", tvName.getText().toString());
                params.put("employeeGender", tvGen.getText().toString());
                params.put("employeeAge", tvAge.getText().toString());
                params.put("employeeMail", tvMail.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}