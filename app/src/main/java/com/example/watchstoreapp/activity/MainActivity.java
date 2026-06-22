package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.watchstoreapp.R;
import com.example.watchstoreapp.adapter.SanPhamAdapter;
import com.example.watchstoreapp.dao.GioHangDAO;
import com.example.watchstoreapp.dao.HangDAO;
import com.example.watchstoreapp.dao.SanPhamDAO;
import com.example.watchstoreapp.model.Hang;
import com.example.watchstoreapp.model.SanPham;
import com.example.watchstoreapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvSanPham;
    private EditText edtSearch;
    private Spinner spnHang, spnGioiTinh;
    private SanPhamAdapter adapter;
    private SanPhamDAO sanPhamDAO;
    private HangDAO hangDAO;
    private GioHangDAO gioHangDAO;
    private SessionManager session;
    private List<Hang> hangList = new ArrayList<>();
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(this);
        sanPhamDAO = new SanPhamDAO(this);
        hangDAO = new HangDAO(this);
        gioHangDAO = new GioHangDAO(this);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("🕐 Watch Store");

        rvSanPham = findViewById(R.id.rvSanPham);
        edtSearch = findViewById(R.id.edtSearch);
        spnHang = findViewById(R.id.spnHang);
        spnGioiTinh = findViewById(R.id.spnGioiTinh);
        bottomNav = findViewById(R.id.bottomNav);

        setupRecyclerView();
        setupSpinners();
        setupSearch();
        setupBottomNav();
        loadProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
        updateCartBadge();
    }

    private void setupRecyclerView() {
        rvSanPham.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new SanPhamAdapter(this, new ArrayList<>(), sp -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("san_pham_id", sp.getId());
            startActivity(intent);
        });
        rvSanPham.setAdapter(adapter);
    }

    private void setupSpinners() {
        // Hang spinner
        hangList = hangDAO.getAll();
        List<String> hangNames = new ArrayList<>();
        hangNames.add("Tất cả hãng");
        for (Hang h : hangList) hangNames.add(h.getTen());
        ArrayAdapter<String> hangAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hangNames);
        hangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHang.setAdapter(hangAdapter);

        // GioiTinh spinner
        String[] gioiTinhArr = {"Tất cả", "Nam", "Nữ", "Unisex"};
        ArrayAdapter<String> gtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gioiTinhArr);
        gtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGioiTinh.setAdapter(gtAdapter);

        AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) { loadProducts(); }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        };
        spnHang.setOnItemSelectedListener(filterListener);
        spnGioiTinh.setOnItemSelectedListener(filterListener);
    }

    private void setupSearch() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { loadProducts(); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void setupBottomNav() {
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) return true;
            if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                return true;
            }
            if (id == R.id.nav_orders) {
                startActivity(new Intent(this, OrderHistoryActivity.class));
                return true;
            }
            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void loadProducts() {
        String keyword = edtSearch.getText().toString().trim();
        int hangPos = spnHang.getSelectedItemPosition();
        int hangId = hangPos > 0 ? hangList.get(hangPos - 1).getId() : 0;
        String gioiTinh = spnGioiTinh.getSelectedItem().toString();

        List<SanPham> list = sanPhamDAO.search(keyword, gioiTinh, hangId, null);
        adapter.updateData(list);
    }

    private void updateCartBadge() {
        int count = gioHangDAO.getCartCount(session.getUserId());
        BadgeDrawable badge = bottomNav.getOrCreateBadge(R.id.nav_cart);
        if (count > 0) {
            badge.setVisible(true);
            badge.setNumber(count);
        } else {
            badge.setVisible(false);
        }
    }
}
