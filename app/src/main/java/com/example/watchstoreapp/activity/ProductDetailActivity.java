package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.watchstoreapp.model.HoaDon;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.dao.GioHangDAO;
import com.example.watchstoreapp.dao.HoaDonDAO;
import com.example.watchstoreapp.dao.SanPhamDAO;
import com.example.watchstoreapp.model.ChiTietHoaDon;
import com.example.watchstoreapp.model.SanPham;
import com.example.watchstoreapp.utils.FormatUtils;
import com.example.watchstoreapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView imgSanPham;

    private TextView tvTen, tvHang, tvGiaBan, tvGiaKm,
            tvGioiTinh, tvLoaiMay, tvPhanKhuc,
            tvSoLuong, tvMoTa, tvSoLuongChon;

    private TextView tvChatLieuVo, tvChatLieuDay, tvKinh, tvBaoHanh;

    private Button btnThemGio, btnMinus, btnPlus, btnMuaNgay;

    private int soLuongChon = 1;

    private SanPham sanPham;
    private SanPhamDAO sanPhamDAO;
    private GioHangDAO gioHangDAO;
    private HoaDonDAO hoaDonDAO;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        sanPhamDAO = new SanPhamDAO(this);
        gioHangDAO = new GioHangDAO(this);
        hoaDonDAO = new HoaDonDAO(this);
        session = new SessionManager(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chi tiết sản phẩm");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bindView();

        int id = getIntent().getIntExtra("san_pham_id", -1);
        if (id == -1) {
            finish();
            return;
        }

        sanPham = sanPhamDAO.getById(id);
        if (sanPham == null) {
            finish();
            return;
        }

        bindData();
        setupActions();
    }

    // ================= BIND VIEW =================
    private void bindView() {

        imgSanPham = findViewById(R.id.imgSanPham);

        tvTen = findViewById(R.id.tvTen);
        tvHang = findViewById(R.id.tvHang);
        tvGiaBan = findViewById(R.id.tvGiaBan);
        tvGiaKm = findViewById(R.id.tvGiaKm);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvLoaiMay = findViewById(R.id.tvLoaiMay);
        tvPhanKhuc = findViewById(R.id.tvPhanKhuc);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvMoTa = findViewById(R.id.tvMoTa);

        tvSoLuongChon = findViewById(R.id.tvSoLuongChon);

        btnThemGio = findViewById(R.id.btnThemGio);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);

        btnMuaNgay = findViewById(R.id.btnMuaNgay);

        tvChatLieuVo = findViewById(R.id.tvChatLieuVo);
        tvChatLieuDay = findViewById(R.id.tvChatLieuDay);
        tvKinh = findViewById(R.id.tvKinh);
        tvBaoHanh = findViewById(R.id.tvBaoHanh);
    }

    // ================= DATA =================
    private void bindData() {

        tvTen.setText(sanPham.getTen());
        tvHang.setText(safe(sanPham.getTenHang()));

        tvGioiTinh.setText("Giới tính: " + safe(sanPham.getGioiTinh()));
        tvLoaiMay.setText("Loại máy: " + safe(sanPham.getLoaiMay()));
        tvPhanKhuc.setText("Phân khúc: " + safe(sanPham.getPhanKhuc()));

        tvSoLuong.setText("Còn lại: " + sanPham.getSoLuong() + " chiếc");
        tvMoTa.setText(safe(sanPham.getMoTa()));

        if (sanPham.dangKhuyenMai()) {

            tvGiaKm.setVisibility(View.VISIBLE);
            tvGiaKm.setText(FormatUtils.formatPrice(sanPham.getGiaKm()));

            tvGiaBan.setText(FormatUtils.formatPrice(sanPham.getGiaBan()));
            tvGiaBan.setPaintFlags(
                    tvGiaBan.getPaintFlags()
                            | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            );

        } else {
            tvGiaKm.setVisibility(View.GONE);
            tvGiaBan.setText(FormatUtils.formatPrice(sanPham.getGiaBan()));
        }

        if (sanPham.getAnh() != null && !sanPham.getAnh().trim().isEmpty()) {

            int resId = getResources().getIdentifier(
                    sanPham.getAnh(),
                    "drawable",
                    getPackageName()
            );

            imgSanPham.setImageResource(
                    resId != 0
                            ? resId
                            : android.R.drawable.ic_menu_gallery
            );

        } else {
            imgSanPham.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        tvChatLieuVo.setText("Vỏ: " + safe(sanPham.getChatLieuVo()));
        tvChatLieuDay.setText("Dây: " + safe(sanPham.getChatLieuDay()));
        tvKinh.setText("Kính: " + safe(sanPham.getKinh()));
        tvBaoHanh.setText("Bảo hành: " + sanPham.getBaoHanh() + " tháng");

        tvSoLuongChon.setText(String.valueOf(soLuongChon));

        if (sanPham.getSoLuong() <= 0) {
            btnThemGio.setText("Hết hàng");
            btnThemGio.setEnabled(false);
            btnMuaNgay.setEnabled(false);
        }
    }

    // ================= ACTIONS =================
    private void setupActions() {

        btnMinus.setOnClickListener(v -> {
            if (soLuongChon > 1) {
                soLuongChon--;
                tvSoLuongChon.setText(String.valueOf(soLuongChon));
            }
        });

        btnPlus.setOnClickListener(v -> {
            if (sanPham.getSoLuong() <= 0) {
                Toast.makeText(this, "Hết hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            if (soLuongChon < sanPham.getSoLuong()) {
                soLuongChon++;
                tvSoLuongChon.setText(String.valueOf(soLuongChon));
            } else {
                Toast.makeText(this,
                        "Không đủ hàng trong kho",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // ===== THÊM GIỎ =====
        btnThemGio.setOnClickListener(v -> {

            if (sanPham.getSoLuong() <= 0) {
                Toast.makeText(this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = gioHangDAO.addToCart(
                    session.getUserId(),
                    sanPham.getId(),
                    soLuongChon
            );

            if (result > 0) {
                Toast.makeText(this, "Đã thêm vào giỏ hàng ✓", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });

        // ===== MUA NGAY =====
        btnMuaNgay.setOnClickListener(v -> showBuyNowDialog());
    }

    // ================= MUA NGAY DIALOG =================
    private void showBuyNowDialog() {

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_order, null);

        EditText edtDiaChi = dialogView.findViewById(R.id.edtDiaChi);
        EditText edtGhiChu = dialogView.findViewById(R.id.edtGhiChu);
        RadioGroup rgHinhThuc = dialogView.findViewById(R.id.rgHinhThuc);

        edtDiaChi.setText(session.getUserAddress());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Mua ngay")
                .setView(dialogView)
                .setPositiveButton("Đặt hàng", null)
                .setNegativeButton("Hủy", null)
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(v -> {

                    String diaChi = edtDiaChi.getText().toString().trim();
                    String ghiChu = edtGhiChu.getText().toString().trim();

                    if (diaChi.isEmpty()) {
                        edtDiaChi.setError("Nhập địa chỉ giao hàng");
                        return;
                    }

                    int selectedId = rgHinhThuc.getCheckedRadioButtonId();
                    if (selectedId == -1) {
                        Toast.makeText(this,
                                "Chọn hình thức thanh toán",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String hinhThuc = (selectedId == R.id.rbChuyenKhoan)
                            ? "Chuyển khoản"
                            : "Tiền mặt";

                    dialog.dismiss();
                    placeBuyNowOrder(diaChi, ghiChu, hinhThuc);
                });
    }

    // ================= TẠO ĐƠN MUA NGAY =================
    private void placeBuyNowOrder(String diaChi, String ghiChu, String hinhThuc) {

        double gia = sanPham.dangKhuyenMai()
                ? sanPham.getGiaKm()
                : sanPham.getGiaBan();

        double tongTien = gia * soLuongChon;

        List<ChiTietHoaDon> chiTietList = new ArrayList<>();

        chiTietList.add(new ChiTietHoaDon(
                0,
                sanPham.getId(),
                soLuongChon,
                gia
        ));

        HoaDon hd = new HoaDon(
                hoaDonDAO.generateMaHd(),
                session.getUserId(),
                tongTien,
                0,
                tongTien,
                hinhThuc,
                HoaDon.TRANG_THAI_CHO,
                diaChi,
                ghiChu
        );

        long hdId = hoaDonDAO.insert(hd, chiTietList);

        if (hdId > 0) {

            Toast.makeText(this, "Đặt hàng thành công 🎉", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("hoa_don_id", (int) hdId);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    // ================= UTIL =================
    private String safe(String s) {
        return s == null ? "" : s;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}