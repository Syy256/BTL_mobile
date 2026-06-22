package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.dao.NguoiDungDAO;
import com.example.watchstoreapp.model.NguoiDung;
import com.example.watchstoreapp.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtMatKhau;
    private Button btnDangNhap;
    private TextView tvDangKy;
    private NguoiDungDAO nguoiDungDAO;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nguoiDungDAO = new NguoiDungDAO(this);
        session = new SessionManager(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvDangKy = findViewById(R.id.tvDangKy);

        btnDangNhap.setOnClickListener(v -> doLogin());
        tvDangKy.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void doLogin() {
        String email = edtEmail.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();

        if (email.isEmpty()) { edtEmail.setError("Nhập email"); return; }
        if (matKhau.isEmpty()) { edtMatKhau.setError("Nhập mật khẩu"); return; }

        NguoiDung nd = nguoiDungDAO.login(email, matKhau);
        if (nd != null) {
            session.saveSession(nd.getId(), nd.getHoTen(), nd.getEmail(), nd.getVaiTro(), nd.getDiaChi() != null ? nd.getDiaChi() : "");
            Toast.makeText(this, "Xin chào " + nd.getHoTen(), Toast.LENGTH_SHORT).show();
            Intent intent;
            if (nd.isAdmin()) {
                intent = new Intent(this, AdminDashboardActivity.class);
            } else {
                intent = new Intent(this, MainActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }
}
