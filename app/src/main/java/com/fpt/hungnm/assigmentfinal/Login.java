package com.fpt.hungnm.assigmentfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private EditText edtTk;
    private EditText edtMk;

    private TextView tvError;
    void bindingAction(){
        btnLogin.setOnClickListener(this::onLogin);
    }

    private void onLogin(View view) {
        if(edtTk.getText().toString().equals("admin") && edtMk.getText().toString().equals("123")){
            Intent i = new Intent(this, Home.class);
            startActivity(i);
        }else{
            tvError.setVisibility(View.VISIBLE);
        }
    }

    void binding(){
        btnLogin = findViewById(R.id.btnLogin);
        edtTk = findViewById(R.id.edtTk);
        edtMk = findViewById(R.id.edtMk);
        tvError = findViewById(R.id.tvError);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();
        bindingAction();
    }
}