package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.adapter.AdminHoaDonAdapter;
import com.example.watchstoreapp.dao.HoaDonDAO;
import com.example.watchstoreapp.model.HoaDon;

import java.util.List;

public class AdminOrderActivity extends AppCompatActivity {
    private RecyclerView rvHoaDon;
    private HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);
        hoaDonDAO = new HoaDonDAO(this);
        if (getSupportActionBar() != null) { getSupportActionBar().setTitle("Quản lý đơn hàng"); getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        rvHoaDon = findViewById(R.id.rvHoaDon);
        rvHoaDon.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }

    @Override protected void onResume() { super.onResume(); loadData(); }

    private void loadData() {
        List<HoaDon> list = hoaDonDAO.getAll();
        AdminHoaDonAdapter adapter = new AdminHoaDonAdapter(this, list,
                hd -> { Intent i = new Intent(this, OrderDetailActivity.class); i.putExtra("hoa_don_id", hd.getId()); startActivity(i); },
                (hd, trangThai) -> { hoaDonDAO.updateTrangThai(hd.getId(), trangThai); loadData(); }
        );
        rvHoaDon.setAdapter(adapter);
    }

    @Override public boolean onSupportNavigateUp() { finish(); return true; }
}
