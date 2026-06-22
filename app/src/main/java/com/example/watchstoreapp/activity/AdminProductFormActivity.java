package com.example.watchstoreapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.dao.HangDAO;
import com.example.watchstoreapp.dao.SanPhamDAO;
import com.example.watchstoreapp.model.Hang;
import com.example.watchstoreapp.model.SanPham;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminProductFormActivity extends AppCompatActivity {

    private EditText edtTen, edtGiaBan, edtGiaKm, edtSoLuong, edtMoTa;

    // NEW FIELDS
    private EditText edtChatLieuVo, edtChatLieuDay, edtKinh, edtBaoHanh;

    private Spinner spnHang, spnGioiTinh, spnLoaiMay, spnPhanKhuc, spnAnh;
    private ImageView imgPreview;
    private Button btnLuu;

    private SanPhamDAO sanPhamDAO;
    private HangDAO hangDAO;

    private List<Hang> hangList;
    private List<String> dsAnh;

    private SanPham editSanPham = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_form);

        sanPhamDAO = new SanPhamDAO(this);
        hangDAO = new HangDAO(this);

        initView();
        setupSpinners();

        int id = getIntent().getIntExtra("san_pham_id", -1);

        if (id != -1) {
            editSanPham = sanPhamDAO.getById(id);
            if (editSanPham != null) {
                bindEditData();
            }
        }

        btnLuu.setOnClickListener(v -> save());
    }

    private void initView() {
        edtTen = findViewById(R.id.edtTen);
        edtGiaBan = findViewById(R.id.edtGiaBan);
        edtGiaKm = findViewById(R.id.edtGiaKm);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtMoTa = findViewById(R.id.edtMoTa);

        edtChatLieuVo = findViewById(R.id.edtChatLieuVo);
        edtChatLieuDay = findViewById(R.id.edtChatLieuDay);
        edtKinh = findViewById(R.id.edtKinh);
        edtBaoHanh = findViewById(R.id.edtBaoHanh);

        spnHang = findViewById(R.id.spnHang);
        spnGioiTinh = findViewById(R.id.spnGioiTinh);
        spnLoaiMay = findViewById(R.id.spnLoaiMay);
        spnPhanKhuc = findViewById(R.id.spnPhanKhuc);
        spnAnh = findViewById(R.id.spnAnh);

        imgPreview = findViewById(R.id.imgPreview);
        btnLuu = findViewById(R.id.btnLuu);
    }

    private void setupSpinners() {

        hangList = hangDAO.getAll();
        List<String> hangNames = new ArrayList<>();

        for (Hang h : hangList) hangNames.add(h.getTen());

        spnHang.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                hangNames
        ));

        String[] gioiTinh = {"Nam", "Nữ", "Unisex"};
        spnGioiTinh.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                gioiTinh
        ));

        String[] loaiMay = {"Pin", "Cơ", "Solar", "Smart"};
        spnLoaiMay.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                loaiMay
        ));

        String[] phanKhuc = {"Bình dân", "Tầm trung", "Cao cấp"};
        spnPhanKhuc.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                phanKhuc
        ));

        dsAnh = loadDrawableImages();

        spnAnh.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                dsAnh
        ));

        spnAnh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int resId = getResources().getIdentifier(
                        dsAnh.get(position),
                        "drawable",
                        getPackageName()
                );

                imgPreview.setImageResource(resId != 0
                        ? resId
                        : android.R.drawable.ic_menu_gallery);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void bindEditData() {

        edtTen.setText(editSanPham.getTen());
        edtGiaBan.setText(String.valueOf((long) editSanPham.getGiaBan()));
        edtGiaKm.setText(String.valueOf((long) editSanPham.getGiaKm()));
        edtSoLuong.setText(String.valueOf(editSanPham.getSoLuong()));
        edtMoTa.setText(editSanPham.getMoTa());

        // NEW FIELDS
        edtChatLieuVo.setText(editSanPham.getChatLieuVo());
        edtChatLieuDay.setText(editSanPham.getChatLieuDay());
        edtKinh.setText(editSanPham.getKinh());
        edtBaoHanh.setText(String.valueOf(editSanPham.getBaoHanh()));

        for (int i = 0; i < hangList.size(); i++) {
            if (hangList.get(i).getId() == editSanPham.getHangId()) {
                spnHang.setSelection(i);
                break;
            }
        }
    }

    private void save() {

        String ten = edtTen.getText().toString().trim();
        String giaBan = edtGiaBan.getText().toString().trim();
        String giaKm = edtGiaKm.getText().toString().trim();
        String soLuong = edtSoLuong.getText().toString().trim();

        String chatLieuVo = edtChatLieuVo.getText().toString().trim();
        String chatLieuDay = edtChatLieuDay.getText().toString().trim();
        String kinh = edtKinh.getText().toString().trim();
        String baoHanh = edtBaoHanh.getText().toString().trim();

        if (ten.isEmpty()) {
            edtTen.setError("Bắt buộc");
            return;
        }

        int hangId = hangList.get(spnHang.getSelectedItemPosition()).getId();

        String anh = dsAnh.get(spnAnh.getSelectedItemPosition());

        if (editSanPham == null) {

            SanPham sp = new SanPham(
                    sanPhamDAO.generateMaSp(),
                    ten,
                    hangId,
                    spnGioiTinh.getSelectedItem().toString(),
                    spnLoaiMay.getSelectedItem().toString(),
                    spnPhanKhuc.getSelectedItem().toString(),
                    chatLieuVo,
                    chatLieuDay,
                    kinh,
                    Integer.parseInt(baoHanh.isEmpty() ? "0" : baoHanh),
                    Double.parseDouble(giaBan),
                    Double.parseDouble(giaKm.isEmpty() ? "0" : giaKm),
                    null,
                    Integer.parseInt(soLuong),
                    anh,
                    edtMoTa.getText().toString(),
                    1
            );

            sanPhamDAO.insert(sp);
            finish();

        } else {

            editSanPham.setTen(ten);
            editSanPham.setHangId(hangId);
            editSanPham.setGioiTinh(spnGioiTinh.getSelectedItem().toString());
            editSanPham.setLoaiMay(spnLoaiMay.getSelectedItem().toString());
            editSanPham.setPhanKhuc(spnPhanKhuc.getSelectedItem().toString());

            editSanPham.setChatLieuVo(chatLieuVo);
            editSanPham.setChatLieuDay(chatLieuDay);
            editSanPham.setKinh(kinh);
            editSanPham.setBaoHanh(Integer.parseInt(baoHanh.isEmpty() ? "0" : baoHanh));

            editSanPham.setGiaBan(Double.parseDouble(giaBan));
            editSanPham.setGiaKm(Double.parseDouble(giaKm.isEmpty() ? "0" : giaKm));
            editSanPham.setSoLuong(Integer.parseInt(soLuong));

            editSanPham.setAnh(anh);
            editSanPham.setMoTa(edtMoTa.getText().toString());

            sanPhamDAO.update(editSanPham);
            finish();
        }
    }

    private ArrayList<String> loadDrawableImages() {

        ArrayList<String> list = new ArrayList<>();

        for (Field f : R.drawable.class.getFields()) {
            String name = f.getName();

            if (!name.startsWith("ic_")
                    && !name.startsWith("btn_")
                    && !name.startsWith("abc_")) {
                list.add(name);
            }
        }

        Collections.sort(list);
        return list;
    }
}