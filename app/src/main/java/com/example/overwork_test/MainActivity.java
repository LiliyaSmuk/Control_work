package com.example.overwork_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button next_Btn;
    Button write_Btn;
    EditText value1;
    EditText value2;
    RequestQueue my_Request;
    public static String answer = "пусто";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next_Btn = (Button) findViewById(R.id.next_Btn);
        write_Btn = (Button) findViewById(R.id.write_Btn);
        value1 = (EditText) findViewById(R.id.value_1);
        value2 = (EditText) findViewById(R.id.value_2);
        my_Request = Volley.newRequestQueue(this);

        next_Btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        intent.putExtra("name", answer);
                        startActivity(intent);
                    }
                }
        );
        write_Btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String URL = "http://abashin.ru/cgi-bin/ru/tests/burnout";
                        //формирование запроса
                        StringRequest str_Request = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String feedback) {
                                        Toast.makeText(getApplicationContext(), "Запрос отправлен.", Toast.LENGTH_LONG).show();
                                        answer = feedback;
                                    }
                                },

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                        answer = error.toString();
                                    }

                                }) {
                            // отправка данных
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("m2", value2.getText().toString());
                                params.put("m1", value1.getText().toString());
                                params.put("sex", "1");
                                params.put("year", "1990");
                                params.put("month", "12");
                                params.put("day", "15");

                                return params;
                            }

                            //заполенение заголовков и отправка
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                Integer l = 40 + value1.length() + value2.length();
                                params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
                                params.put("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                params.put("Content-Length", l.toString());
                                return params;
                            }

                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse feedback) {
                                String string_utf = null;
                                try {
                                    string_utf = new String(feedback.data, "UTF-8");
                                    return Response.success(string_utf, HttpHeaderParser.parseCacheHeaders(feedback));
                                } catch (UnsupportedEncodingException e) {
                                    return Response.error(new ParseError(e));
                                }
                            }
                        };
                        //добавление в очередь запросов
                        my_Request.add(str_Request);
                    }
                }
        );
    }

}
