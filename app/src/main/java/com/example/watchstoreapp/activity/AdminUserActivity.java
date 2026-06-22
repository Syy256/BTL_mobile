package com.example.watchstoreapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.adapter.AdminNguoiDungAdapter;
import com.example.watchstoreapp.dao.NguoiDungDAO;
import com.example.watchstoreapp.model.NguoiDung;

import java.util.List;

public class AdminUserActivity extends AppCompatActivity {
    private RecyclerView rvNguoiDung;
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        nguoiDungDAO = new NguoiDungDAO(this);
        if (getSupportActionBar() != null) { getSupportActionBar().setTitle("Quản lý người dùng"); getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        rvNguoiDung = findViewById(R.id.rvNguoiDung);
        rvNguoiDung.setLayoutManager(new LinearLayoutManager(this));
        List<NguoiDung> list = nguoiDungDAO.getAll();
        rvNguoiDung.setAdapter(new AdminNguoiDungAdapter(this, list));
    }

    @Override public boolean onSupportNavigateUp() { finish(); return true; }
}
