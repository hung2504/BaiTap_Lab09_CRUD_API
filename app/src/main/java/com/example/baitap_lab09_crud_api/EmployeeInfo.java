package com.example.baitap_lab09_crud_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class EmployeeInfo extends AppCompatActivity {
    String url = "https://60ab17a75a4de40017cc9514.mockapi.io/employees/";
    private EditText edtID,edtName,edtMail,edtGen,edtAge;
    private Button btnUpdate, btnDelete;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);

        Intent intent = getIntent();
        Employees employees = (Employees) intent.getSerializableExtra("emp");

        edtID = findViewById(R.id.edtId2);
        edtName = findViewById(R.id.edtName2);
        edtGen = findViewById(R.id.edtGender2);
        edtMail = findViewById(R.id.edtEmail2);
        edtAge = findViewById(R.id.edtAge2);

        edtID.setText(String.valueOf(employees.getId()));
        edtName.setText(employees.getName());
        edtGen.setText(employees.getGender());
        edtMail.setText(employees.getMail());
        edtAge.setText(String.valueOf(employees.getAge()));

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        imageButton = findViewById(R.id.imgBack);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteApi(url);
                Intent intent = new Intent(EmployeeInfo.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutApi(url);
                Intent intent = new Intent(EmployeeInfo.this,MainActivity.class);
                startActivity(intent);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeInfo.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void PutApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url + '/' + Integer.parseInt(edtID.getText().toString().trim()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EmployeeInfo.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EmployeeInfo.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("employeeName", edtName.getText().toString());
                params.put("employeeGender", edtGen.getText().toString());
                params.put("employeeAge", edtAge.getText().toString());
                params.put("employeeMail", edtMail.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void DeleteApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + '/' + Integer.parseInt(edtID.getText().toString().trim()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EmployeeInfo.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EmployeeInfo.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}