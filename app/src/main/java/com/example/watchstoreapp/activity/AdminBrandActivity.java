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
import com.example.watchstoreapp.adapter.AdminHangAdapter;
import com.example.watchstoreapp.dao.HangDAO;
import com.example.watchstoreapp.model.Hang;

import java.util.List;

public class AdminBrandActivity extends AppCompatActivity {
    private RecyclerView rvHang;
    private EditText edtSearch;
    private FloatingActionButton fabAdd;
    private HangDAO hangDAO;
    private AdminHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_brand);
        hangDAO = new HangDAO(this);
        if (getSupportActionBar() != null) { getSupportActionBar().setTitle("Quản lý hãng"); getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        rvHang = findViewById(R.id.rvHang);
        edtSearch = findViewById(R.id.edtSearch);
        fabAdd = findViewById(R.id.fabAdd);
        rvHang.setLayoutManager(new LinearLayoutManager(this));
        fabAdd.setOnClickListener(v -> startActivity(new Intent(this, AdminBrandFormActivity.class)));
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) { loadData(); }
            @Override public void afterTextChanged(Editable s) {}
        });
        loadData();
    }

    @Override protected void onResume() { super.onResume(); loadData(); }

    private void loadData() {
        String kw = edtSearch.getText().toString().trim();
        List<Hang> list = kw.isEmpty() ? hangDAO.getAll() : hangDAO.search(kw);
        adapter = new AdminHangAdapter(this, list,
                h -> { Intent i = new Intent(this, AdminBrandFormActivity.class); i.putExtra("hang_id", h.getId()); startActivity(i); },
                h -> new AlertDialog.Builder(this).setTitle("Xóa hãng").setMessage("Xóa \"" + h.getTen() + "\"?")
                        .setPositiveButton("Xóa", (d, w) -> { hangDAO.delete(h.getId()); loadData(); })
                        .setNegativeButton("Hủy", null).show()
        );
        rvHang.setAdapter(adapter);
    }

    @Override public boolean onSupportNavigateUp() { finish(); return true; }
}
