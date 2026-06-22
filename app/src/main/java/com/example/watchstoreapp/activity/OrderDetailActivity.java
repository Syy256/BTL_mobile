package com.example.watchstoreapp.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.adapter.ChiTietHoaDonAdapter;
import com.example.watchstoreapp.dao.HoaDonDAO;
import com.example.watchstoreapp.model.ChiTietHoaDon;
import com.example.watchstoreapp.model.HoaDon;
import com.example.watchstoreapp.utils.FormatUtils;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView tvMaHd, tvNgayDat, tvTrangThai, tvDiaChi, tvHinhThuc, tvTongTien, tvGhiChu;
    private RecyclerView rvChiTiet;
    private HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        hoaDonDAO = new HoaDonDAO(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chi tiết đơn hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvMaHd = findViewById(R.id.tvMaHd);
        tvNgayDat = findViewById(R.id.tvNgayDat);
        tvTrangThai = findViewById(R.id.tvTrangThai);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvHinhThuc = findViewById(R.id.tvHinhThuc);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvGhiChu = findViewById(R.id.tvGhiChu);
        rvChiTiet = findViewById(R.id.rvChiTiet);

        int id = getIntent().getIntExtra("hoa_don_id", -1);
        if (id == -1) { finish(); return; }

        HoaDon hd = hoaDonDAO.getById(id);
        List<ChiTietHoaDon> chiTietList = hoaDonDAO.getChiTiet(id);

        if (hd != null) bindData(hd, chiTietList);
    }

    private void bindData(HoaDon hd, List<ChiTietHoaDon> chiTietList) {
        tvMaHd.setText("Mã HĐ: " + hd.getMaHd());
        tvNgayDat.setText("Ngày đặt: " + FormatUtils.formatDateTime(hd.getNgayDat()));
        tvTrangThai.setText("Trạng thái: " + hd.getTrangThai());
        tvDiaChi.setText("Địa chỉ: " + (hd.getDiaChiGiao() != null ? hd.getDiaChiGiao() : ""));
        tvHinhThuc.setText("Thanh toán: " + (hd.getHinhThucTt() != null ? hd.getHinhThucTt() : ""));
        tvTongTien.setText("Tổng tiền: " + FormatUtils.formatPrice(hd.getThanhTien()));
        tvGhiChu.setText(hd.getGhiChu() != null ? "Ghi chú: " + hd.getGhiChu() : "");

        rvChiTiet.setLayoutManager(new LinearLayoutManager(this));
        rvChiTiet.setAdapter(new ChiTietHoaDonAdapter(this, chiTietList));
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
