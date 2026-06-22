package com.example.watchstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.model.ChiTietHoaDon;
import com.example.watchstoreapp.utils.FormatUtils;

import java.util.List;

public class ChiTietHoaDonAdapter extends RecyclerView.Adapter<ChiTietHoaDonAdapter.ViewHolder> {
    private Context ctx;
    private List<ChiTietHoaDon> list;

    public ChiTietHoaDonAdapter(Context ctx, List<ChiTietHoaDon> list) {
        this.ctx = ctx; this.list = list;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_chi_tiet_hd, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        ChiTietHoaDon ct = list.get(pos);
        h.tvTen.setText(ct.getTenSanPham());
        h.tvHang.setText(ct.getTenHang() != null ? ct.getTenHang() : "");
        h.tvGia.setText(FormatUtils.formatPrice(ct.getGiaBan()) + " x " + ct.getSoLuong());
        h.tvThanhTien.setText(FormatUtils.formatPrice(ct.getThanhTien()));
    }

    @Override public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvHang, tvGia, tvThanhTien;
        ViewHolder(View v) {
            super(v);
            tvTen = v.findViewById(R.id.tvTen);
            tvHang = v.findViewById(R.id.tvHang);
            tvGia = v.findViewById(R.id.tvGia);
            tvThanhTien = v.findViewById(R.id.tvThanhTien);
        }
    }
}
