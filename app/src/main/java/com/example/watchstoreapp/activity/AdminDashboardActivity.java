package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.dao.HoaDonDAO;
import com.example.watchstoreapp.dao.NguoiDungDAO;
import com.example.watchstoreapp.dao.SanPhamDAO;
import com.example.watchstoreapp.utils.FormatUtils;
import com.example.watchstoreapp.utils.SessionManager;

import java.util.Calendar;

public class AdminDashboardActivity extends AppCompatActivity {
    private TextView tvDoanhThu, tvSoSanPham, tvSoKhach, tvChaoAdmin;
    private Button btnSanPham, btnHang, btnDonHang, btnNguoiDung, btnDangXuat;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        session = new SessionManager(this);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Admin Dashboard");

        tvChaoAdmin = findViewById(R.id.tvChaoAdmin);
        tvDoanhThu = findViewById(R.id.tvDoanhThu);
        tvSoSanPham = findViewById(R.id.tvSoSanPham);
        tvSoKhach = findViewById(R.id.tvSoKhach);
        btnSanPham = findViewById(R.id.btnSanPham);
        btnHang = findViewById(R.id.btnHang);
        btnDonHang = findViewById(R.id.btnDonHang);
        btnNguoiDung = findViewById(R.id.btnNguoiDung);
        btnDangXuat = findViewById(R.id.btnDangXuat);

        tvChaoAdmin.setText("Xin chào, " + session.getUserName() + "!");

        loadStats();

        btnSanPham.setOnClickListener(v -> startActivity(new Intent(this, AdminProductActivity.class)));
        btnHang.setOnClickListener(v -> startActivity(new Intent(this, AdminBrandActivity.class)));
        btnDonHang.setOnClickListener(v -> startActivity(new Intent(this, AdminOrderActivity.class)));
        btnNguoiDung.setOnClickListener(v -> startActivity(new Intent(this, AdminUserActivity.class)));

        btnDangXuat.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Đăng xuất")
                        .setMessage("Xác nhận đăng xuất?")
                        .setPositiveButton("Đăng xuất", (d, w) -> {
                            session.logout();
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        })
                        .setNegativeButton("Hủy", null).show()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStats();
    }

    private void loadStats() {
        SanPhamDAO spDAO = new SanPhamDAO(this);
        NguoiDungDAO ndDAO = new NguoiDungDAO(this);
        HoaDonDAO hdDAO = new HoaDonDAO(this);

        int soSp = spDAO.getAll().size();
        int soKhach = ndDAO.getAll().size();
        Calendar cal = Calendar.getInstance();
        double doanhThu = hdDAO.getDoanhThuThang(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

        tvSoSanPham.setText("Sản phẩm: " + soSp);
        tvSoKhach.setText("Người dùng: " + soKhach);
        tvDoanhThu.setText("Doanh thu tháng này:\n" + FormatUtils.formatPrice(doanhThu));
    }
}
