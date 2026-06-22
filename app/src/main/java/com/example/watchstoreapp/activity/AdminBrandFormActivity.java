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
import com.example.watchstoreapp.model.Hang;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class AdminBrandFormActivity extends AppCompatActivity {

    private EditText edtTen, edtXuatXu, edtMoTa;
    private Spinner spnLogo;
    private ImageView imgPreview;
    private Button btnLuu;

    private HangDAO hangDAO;
    private Hang editHang = null;

    private ArrayList<String> dsLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_brand_form);

        hangDAO = new HangDAO(this);

        initView();
        loadLogoDrawable();
        setupSpinner();

        int id = getIntent().getIntExtra("hang_id", -1);

        if (id != -1) {
            editHang = hangDAO.getById(id);

            if (editHang != null) {
                getSupportActionBar().setTitle("Sửa hãng");
                bindData();
            }
        } else {
            getSupportActionBar().setTitle("Thêm hãng");
        }

        btnLuu.setOnClickListener(v -> save());
    }

    private void initView() {
        edtTen = findViewById(R.id.edtTen);
        edtXuatXu = findViewById(R.id.edtXuatXu);
        edtMoTa = findViewById(R.id.edtMoTa);

        spnLogo = findViewById(R.id.spnLogo);
        imgPreview = findViewById(R.id.imgPreview);
        btnLuu = findViewById(R.id.btnLuu);
    }

    private void loadLogoDrawable() {
        dsLogo = new ArrayList<>();

        for (Field f : R.drawable.class.getFields()) {
            String name = f.getName();

            if (!name.startsWith("ic_")
                    && !name.startsWith("btn_")
                    && !name.startsWith("abc_")
                    && !name.startsWith("mtrl_")) {

                dsLogo.add(name);
            }
        }

        Collections.sort(dsLogo);
    }

    private void setupSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                dsLogo
        );

        spnLogo.setAdapter(adapter);

        spnLogo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int resId = getResources().getIdentifier(
                        dsLogo.get(position),
                        "drawable",
                        getPackageName()
                );

                if (resId != 0) {
                    imgPreview.setImageResource(resId);
                } else {
                    imgPreview.setImageResource(android.R.drawable.ic_menu_gallery);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void bindData() {

        edtTen.setText(editHang.getTen());
        edtXuatXu.setText(editHang.getXuatXu());
        edtMoTa.setText(editHang.getMoTa());

        // chọn logo đúng vị trí
        for (int i = 0; i < dsLogo.size(); i++) {
            if (dsLogo.get(i).equals(editHang.getLogo())) {
                spnLogo.setSelection(i);
                break;
            }
        }
    }

    private void save() {

        String ten = edtTen.getText().toString().trim();
        String xuatXu = edtXuatXu.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();
        String logo = dsLogo.get(spnLogo.getSelectedItemPosition());

        if (ten.isEmpty()) {
            edtTen.setError("Nhập tên hãng");
            return;
        }

        if (editHang == null) {

            long id = hangDAO.insert(new Hang(ten, xuatXu, moTa, logo));

            if (id > 0) {
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

        } else {

            editHang.setTen(ten);
            editHang.setXuatXu(xuatXu);
            editHang.setMoTa(moTa);
            editHang.setLogo(logo);

            int rows = hangDAO.update(editHang);

            if (rows > 0) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}