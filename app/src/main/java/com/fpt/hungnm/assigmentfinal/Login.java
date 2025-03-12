//package com.fpt.hungnm.assigmentfinal;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class Login extends AppCompatActivity {
//
//    private Button btnLogin;
//    private EditText edtTk;
//    private EditText edtMk;
//
//    private TextView tvError;
//    void bindingAction(){
//        btnLogin.setOnClickListener(this::onLogin);
//    }
//
//    private void onLogin(View view) {
//        if(edtTk.getText().toString().equals("admin") && edtMk.getText().toString().equals("123")){
//            Intent i = new Intent(this, Home.class);
//            startActivity(i);
//        }else{
//            tvError.setVisibility(View.VISIBLE);
//        }
//    }
//
//    void binding(){
//        btnLogin = findViewById(R.id.btnLogin);
//        edtTk = findViewById(R.id.edtTk);
//        edtMk = findViewById(R.id.edtMk);
//        tvError = findViewById(R.id.tvError);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        binding();
//        bindingAction();
//    }
//}
//


package com.fpt.hungnm.assigmentfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private EditText edtTk, edtMk;
    private TextView tvError, tvRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Ánh xạ UI
        binding();
        bindingAction();
    }

    void binding() {
        btnLogin = findViewById(R.id.btnLogin);
        edtTk = findViewById(R.id.edtTk);
        edtMk = findViewById(R.id.edtMk);
        tvError = findViewById(R.id.tvError);
        tvRegister = findViewById(R.id.tvRegister);
    }

    void bindingAction() {
        btnLogin.setOnClickListener(this::onLogin);
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void onLogin(View view) {
        String email = edtTk.getText().toString().trim();
        String password = edtMk.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            tvError.setText("Vui lòng nhập đầy đủ thông tin!");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Intent i = new Intent(this, Home.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        // Đăng nhập thất bại
                        tvError.setText("Đăng nhập thất bại! Kiểm tra lại email/mật khẩu.");
                        tvError.setVisibility(View.VISIBLE);
                    }
                });
    }
}


