package com.example.watchstoreapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.dao.NguoiDungDAO;
import com.example.watchstoreapp.model.NguoiDung;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtHoTen, edtSdt, edtEmail, edtMatKhau, edtXacNhan, edtDiaChi;
    private Button btnDangKy;
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nguoiDungDAO = new NguoiDungDAO(this);

        edtHoTen = findViewById(R.id.edtHoTen);
        edtSdt = findViewById(R.id.edtSdt);
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtXacNhan = findViewById(R.id.edtXacNhan);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        btnDangKy = findViewById(R.id.btnDangKy);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Đăng ký");
        btnDangKy.setOnClickListener(v -> doRegister());
    }

    private void doRegister() {
        String hoTen = edtHoTen.getText().toString().trim();
        String sdt = edtSdt.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();
        String xacNhan = edtXacNhan.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();

        if (hoTen.isEmpty()) { edtHoTen.setError("Nhập họ tên"); return; }
        if (sdt.isEmpty()) { edtSdt.setError("Nhập số điện thoại"); return; }
        if (email.isEmpty()) { edtEmail.setError("Nhập email"); return; }
        if (matKhau.isEmpty()) { edtMatKhau.setError("Nhập mật khẩu"); return; }
        if (matKhau.length() < 6) { edtMatKhau.setError("Mật khẩu ít nhất 6 ký tự"); return; }
        if (!matKhau.equals(xacNhan)) { edtXacNhan.setError("Mật khẩu không khớp"); return; }

        if (nguoiDungDAO.isEmailExists(email)) {
            edtEmail.setError("Email đã được sử dụng"); return;
        }
        if (nguoiDungDAO.isSdtExists(sdt)) {
            edtSdt.setError("Số điện thoại đã được sử dụng"); return;
        }

        NguoiDung nd = new NguoiDung(hoTen, sdt, email, matKhau, "user", diaChi);
        long id = nguoiDungDAO.insert(nd);
        if (id > 0) {
            Toast.makeText(this, "Đăng ký thành công! Hãy đăng nhập.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
