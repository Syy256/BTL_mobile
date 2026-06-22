package com.example.watchstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.model.HoaDon;
import com.example.watchstoreapp.utils.FormatUtils;

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private Context ctx;
    private List<HoaDon> list;
    private OnClick listener;
    public interface OnClick { void onClick(HoaDon hd); }

    public HoaDonAdapter(Context ctx, List<HoaDon> list, OnClick listener) {
        this.ctx = ctx; this.list = list; this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_hoa_don, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        HoaDon hd = list.get(pos);
        h.tvMaHd.setText(hd.getMaHd());
        h.tvNgayDat.setText(FormatUtils.formatDateTime(hd.getNgayDat()));
        h.tvTrangThai.setText(hd.getTrangThai());
        h.tvThanhTien.setText(FormatUtils.formatPrice(hd.getThanhTien()));
        h.card.setOnClickListener(v -> listener.onClick(hd));
    }

    @Override public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card; TextView tvMaHd, tvNgayDat, tvTrangThai, tvThanhTien;
        ViewHolder(View v) {
            super(v);
            card = v.findViewById(R.id.card);
            tvMaHd = v.findViewById(R.id.tvMaHd);
            tvNgayDat = v.findViewById(R.id.tvNgayDat);
            tvTrangThai = v.findViewById(R.id.tvTrangThai);
            tvThanhTien = v.findViewById(R.id.tvThanhTien);
        }
    }
}
