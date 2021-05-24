package com.example.baitap_lab09_crud_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView lvEmp;
    private ArrayList<Employees> arrayList;
    private AdapterList adapterList;
    private Button btnSearch;
    private ImageButton imageButton;
    private EditText edtSearch;
    String url = "https://60ab17a75a4de40017cc9514.mockapi.io/employees/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvEmp = findViewById(R.id.lvEmployee);
        btnSearch = findViewById(R.id.btnSearch);
        imageButton = findViewById(R.id.imgButAdd);
        edtSearch = findViewById(R.id.edtSearchID);

        arrayList = new ArrayList<>();
        GetArrayJson(url);
        adapterList = new AdapterList(R.layout.list_item,MainActivity.this,arrayList);
        lvEmp.setAdapter(adapterList);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Employees> list = new ArrayList<>();
                if(!edtSearch.getText().toString().equalsIgnoreCase("")){
                    for (Employees e: arrayList) {
                        if(e.getId() == Integer.parseInt(edtSearch.getText().toString().trim())){
                            list.add(e);
                            break;
                        }
                        else list = null;
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập ID", Toast.LENGTH_SHORT).show();
                    arrayList = new ArrayList<>();
                    GetArrayJson(url);
                    adapterList = new AdapterList(R.layout.list_item,MainActivity.this,arrayList);
                    lvEmp.setAdapter(adapterList);
                }
                if(list != null){
                    adapterList = new AdapterList(R.layout.list_item,MainActivity.this,list);
                    lvEmp.setAdapter(adapterList);
                    Log.e("ok", "ok");
                }
                else{
                    Toast.makeText(MainActivity.this, "Không tìm thấy thông tin!", Toast.LENGTH_SHORT).show();
                    arrayList = new ArrayList<>();
                    GetArrayJson(url);
                    adapterList = new AdapterList(R.layout.list_item,MainActivity.this,arrayList);
                    lvEmp.setAdapter(adapterList);
                }
            }
        });
        lvEmp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, arrayList.get(position).getId()+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,EmployeeInfo.class);
                Employees em = arrayList.get(position);
                intent.putExtra("emp",em);
                startActivity(intent);

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEmployee.class);
                startActivity(intent);
            }
        });
//        Employees e = new Employees(1,"Hùng","Nam",21,"mâmsa");
//        arrayList.add(e);
    }

        private void GetData(String url){
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                    tvDisplay.setText(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error make by API server!", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        private void GetJson(String url){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int id = Integer.parseInt(response.getString("ID"));
                                Toast.makeText(MainActivity.this, id+" ", Toast.LENGTH_SHORT).show();
                                String name = response.getString("employeeName");
                                String gender = response.getString("employeeGender");
                                int age = response.getInt("employeeAge");
                                String mail = response.getString("employeeMail");
                                Employees employees = new Employees(id,name,gender,age,mail);
                                Log.e("emp", employees.toString());
                                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                arrayList.add(employees);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error by get JsonObject...", Toast.LENGTH_SHORT).show();
                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
        private void GetArrayJson(String url){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject object = (JSONObject) response.get(i);
                            int id = Integer.parseInt(object.getString("id"));
                            String name = object.getString("employeeName");
                            String gender = object.getString("employeeGender");
                            int age = object.getInt("employeeAge");
                            String mail = object.getString("employeeMail");
                            Employees employees = new Employees(id,name,gender,age,mail);
                            Log.e("emp", id+"");
//                            Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
                            arrayList.add(employees);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }

        private void PostApi(String url){
            StringRequest stringRequest = new StringRequest( Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("FIRSTNAME", "Lâm");
                    params.put("LASTNAME", "Tấn Hào");
                    params.put("GENDER", "Male");
                    params.put("SALARY", "10000");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        private void PutApi(String url){
            StringRequest stringRequest = new StringRequest(
                    Request.Method.PUT, url + '/' + 28, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("FIRSTNAME", "Lâm");
                    params.put("LASTNAME", "Tấn Xu");
                    params.put("GENDER", "Male");
                    params.put("SALARY", "100000");
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        private void DeleteApi(String url){
            StringRequest stringRequest = new StringRequest(
                    Request.Method.DELETE, url + '/' + 37, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
}