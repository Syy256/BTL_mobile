package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.dao.NguoiDungDAO;
import com.example.watchstoreapp.model.NguoiDung;
import com.example.watchstoreapp.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvHoTen, tvEmail, tvSdt;
    private Button btnSua, btnDangXuat;
    private SessionManager session;
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(this);
        nguoiDungDAO = new NguoiDungDAO(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tài khoản");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvHoTen = findViewById(R.id.tvHoTen);
        tvEmail = findViewById(R.id.tvEmail);
        tvSdt = findViewById(R.id.tvSdt);
        btnSua = findViewById(R.id.btnSua);
        btnDangXuat = findViewById(R.id.btnDangXuat);

        loadProfile();

        btnSua.setOnClickListener(v -> showEditDialog());
        btnDangXuat.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc muốn đăng xuất?")
                    .setPositiveButton("Đăng xuất", (d, w) -> {
                        session.logout();
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    private void loadProfile() {
        NguoiDung nd = nguoiDungDAO.getById(session.getUserId());
        if (nd != null) {
            tvHoTen.setText(nd.getHoTen());
            tvEmail.setText(nd.getEmail());
            tvSdt.setText(nd.getSdt() != null ? nd.getSdt() : "Chưa cập nhật");
        }
    }

    private void showEditDialog() {
        NguoiDung nd = nguoiDungDAO.getById(session.getUserId());
        if (nd == null) return;

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);
        EditText edtHoTen = dialogView.findViewById(R.id.edtHoTen);
        EditText edtSdt = dialogView.findViewById(R.id.edtSdt);
        EditText edtDiaChi = dialogView.findViewById(R.id.edtDiaChi);
        EditText edtMatKhau = dialogView.findViewById(R.id.edtMatKhau);

        edtHoTen.setText(nd.getHoTen());
        edtSdt.setText(nd.getSdt());
        edtDiaChi.setText(nd.getDiaChi());

        new AlertDialog.Builder(this)
                .setTitle("Cập nhật thông tin")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    nd.setHoTen(edtHoTen.getText().toString().trim());
                    nd.setSdt(edtSdt.getText().toString().trim());
                    nd.setDiaChi(edtDiaChi.getText().toString().trim());
                    String mk = edtMatKhau.getText().toString().trim();
                    if (!mk.isEmpty()) nd.setMatKhau(mk);

                    nguoiDungDAO.update(nd);
                    session.saveSession(nd.getId(), nd.getHoTen(), nd.getEmail(), nd.getVaiTro(), nd.getDiaChi() != null ? nd.getDiaChi() : "");
                    loadProfile();
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
