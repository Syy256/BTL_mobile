package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.watchstoreapp.R;
import com.example.watchstoreapp.adapter.AdminSanPhamAdapter;
import com.example.watchstoreapp.dao.SanPhamDAO;
import com.example.watchstoreapp.model.SanPham;

import java.util.List;

public class AdminProductActivity extends AppCompatActivity {
    private RecyclerView rvSanPham;
    private EditText edtSearch;
    private FloatingActionButton fabAdd;
    private SanPhamDAO sanPhamDAO;
    private AdminSanPhamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        sanPhamDAO = new SanPhamDAO(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý sản phẩm");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvSanPham = findViewById(R.id.rvSanPham);
        edtSearch = findViewById(R.id.edtSearch);
        fabAdd = findViewById(R.id.fabAdd);

        rvSanPham.setLayoutManager(new LinearLayoutManager(this));

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminProductFormActivity.class));
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) { loadData(); }
            @Override public void afterTextChanged(Editable s) {}
        });

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        String kw = edtSearch.getText().toString().trim();
        List<SanPham> list = sanPhamDAO.search(kw, null, 0, null);
        adapter = new AdminSanPhamAdapter(this, list,
                sp -> {
                    Intent intent = new Intent(this, AdminProductFormActivity.class);
                    intent.putExtra("san_pham_id", sp.getId());
                    startActivity(intent);
                },
                sp -> new AlertDialog.Builder(this)
                        .setTitle("Xóa sản phẩm")
                        .setMessage("Xóa \"" + sp.getTen() + "\"?")
                        .setPositiveButton("Xóa", (d, w) -> {
                            sanPhamDAO.delete(sp.getId());
                            loadData();
                        })
                        .setNegativeButton("Hủy", null).show()
        );
        rvSanPham.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
