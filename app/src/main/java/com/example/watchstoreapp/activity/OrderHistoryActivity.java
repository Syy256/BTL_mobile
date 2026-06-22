package com.example.watchstoreapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.adapter.HoaDonAdapter;
import com.example.watchstoreapp.dao.HoaDonDAO;
import com.example.watchstoreapp.model.HoaDon;
import com.example.watchstoreapp.utils.SessionManager;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView rvHoaDon;
    private TextView tvEmpty;
    private HoaDonDAO hoaDonDAO;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        hoaDonDAO = new HoaDonDAO(this);
        session = new SessionManager(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Lịch sử đơn hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvHoaDon = findViewById(R.id.rvHoaDon);
        tvEmpty = findViewById(R.id.tvEmpty);
        rvHoaDon.setLayoutManager(new LinearLayoutManager(this));

        List<HoaDon> list = hoaDonDAO.getByNguoiDung(session.getUserId());

        if (list.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvHoaDon.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            HoaDonAdapter adapter = new HoaDonAdapter(this, list, hd -> {
                Intent intent = new Intent(this, OrderDetailActivity.class);
                intent.putExtra("hoa_don_id", hd.getId());
                startActivity(intent);
            });
            rvHoaDon.setAdapter(adapter);
        }
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
