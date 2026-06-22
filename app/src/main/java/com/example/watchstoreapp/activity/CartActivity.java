package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.adapter.CartAdapter;
import com.example.watchstoreapp.dao.GioHangDAO;
import com.example.watchstoreapp.dao.HoaDonDAO;
import com.example.watchstoreapp.model.ChiTietHoaDon;
import com.example.watchstoreapp.model.GioHang;
import com.example.watchstoreapp.model.HoaDon;
import com.example.watchstoreapp.utils.FormatUtils;
import com.example.watchstoreapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;
    private TextView tvTongTien, tvEmptyCart;
    private Button btnDatHang;
    private CartAdapter adapter;
    private GioHangDAO gioHangDAO;
    private HoaDonDAO hoaDonDAO;
    private SessionManager session;
    private List<GioHang> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        gioHangDAO = new GioHangDAO(this);
        hoaDonDAO = new HoaDonDAO(this);
        session = new SessionManager(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Giỏ hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvCart = findViewById(R.id.rvCart);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvEmptyCart = findViewById(R.id.tvEmptyCart);
        btnDatHang = findViewById(R.id.btnDatHang);

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(this, cartList,
                item -> {
                    gioHangDAO.delete(item.getId());
                    loadCart();
                },
                (item, qty) -> {
                    gioHangDAO.updateSoLuong(item.getId(), qty);
                    loadCart();
                });
        rvCart.setAdapter(adapter);

        btnDatHang.setOnClickListener(v -> showOrderDialog());
        loadCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCart();
    }

    private void loadCart() {
        cartList = gioHangDAO.getByNguoiDung(session.getUserId());
        adapter.updateData(cartList);

        double tong = 0;
        for (GioHang gh : cartList) tong += gh.getTongTien();
        tvTongTien.setText("Tổng: " + FormatUtils.formatPrice(tong));

        boolean empty = cartList.isEmpty();
        tvEmptyCart.setVisibility(empty ? View.VISIBLE : View.GONE);
        rvCart.setVisibility(empty ? View.GONE : View.VISIBLE);
        btnDatHang.setEnabled(!empty);
    }

    private void showOrderDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_order, null);
        EditText edtDiaChi = dialogView.findViewById(R.id.edtDiaChi);
        EditText edtGhiChu = dialogView.findViewById(R.id.edtGhiChu);
        RadioGroup rgHinhThuc = dialogView.findViewById(R.id.rgHinhThuc);

        edtDiaChi.setText(session.getUserAddress());

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận đặt hàng")
                .setView(dialogView)
                .setPositiveButton("Đặt hàng", (dialog, which) -> {
                    String diaChi = edtDiaChi.getText().toString().trim();
                    String ghiChu = edtGhiChu.getText().toString().trim();
                    if (diaChi.isEmpty()) {
                        Toast.makeText(this, "Nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int selectedId = rgHinhThuc.getCheckedRadioButtonId();
                    String hinhThuc = selectedId == R.id.rbChuyenKhoan ? "Chuyển khoản" : "Tiền mặt";
                    placeOrder(diaChi, ghiChu, hinhThuc);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void placeOrder(String diaChi, String ghiChu, String hinhThuc) {
        double tongTien = 0;
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();

        for (GioHang gh : cartList) {
            tongTien += gh.getTongTien();
            ChiTietHoaDon ct = new ChiTietHoaDon(0, gh.getSanPhamId(), gh.getSoLuong(), gh.getGiaHienTai());
            chiTietList.add(ct);
        }

        HoaDon hd = new HoaDon(
                hoaDonDAO.generateMaHd(),
                session.getUserId(),
                tongTien, 0, tongTien,
                hinhThuc,
                HoaDon.TRANG_THAI_CHO,
                diaChi, ghiChu
        );

        long hdId = hoaDonDAO.insert(hd, chiTietList);
        if (hdId > 0) {
            gioHangDAO.clearCart(session.getUserId());
            Toast.makeText(this, "Đặt hàng thành công! 🎉", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("hoa_don_id", (int) hdId);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
