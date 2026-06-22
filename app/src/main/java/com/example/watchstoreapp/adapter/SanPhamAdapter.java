package com.example.watchstoreapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.model.SanPham;
import com.example.watchstoreapp.utils.FormatUtils;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private Context context;
    private List<SanPham> list;
    private OnItemClick listener;

    public interface OnItemClick {
        void onClick(SanPham sp);
    }

    public SanPhamAdapter(Context context, List<SanPham> list, OnItemClick listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void updateData(List<SanPham> newList) {
        list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {

        SanPham sp = list.get(pos);

        h.tvTen.setText(sp.getTen());
        h.tvHang.setText(sp.getTenHang() != null ? sp.getTenHang() : "");
        h.tvGioiTinh.setText(sp.getGioiTinh() != null ? sp.getGioiTinh() : "");

        // ===== NEW INFO =====
        h.tvChatLieuVo.setText("Vỏ: " + safe(sp.getChatLieuVo()));
        h.tvChatLieuDay.setText("Dây: " + safe(sp.getChatLieuDay()));
        h.tvKinh.setText("Kính: " + safe(sp.getKinh()));
        h.tvBaoHanh.setText("BH: " + sp.getBaoHanh() + " tháng");

        // ===== PRICE =====
        if (sp.dangKhuyenMai()) {
            h.tvGiaKm.setText(FormatUtils.formatPrice(sp.getGiaKm()));
            h.tvGiaKm.setVisibility(View.VISIBLE);

            h.tvGiaBan.setText(FormatUtils.formatPrice(sp.getGiaBan()));
            h.tvGiaBan.setPaintFlags(
                    h.tvGiaBan.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );

            h.tvKm.setVisibility(View.VISIBLE);
        } else {
            h.tvGiaKm.setVisibility(View.GONE);
            h.tvKm.setVisibility(View.GONE);

            h.tvGiaBan.setText(FormatUtils.formatPrice(sp.getGiaBan()));
            h.tvGiaBan.setPaintFlags(
                    h.tvGiaBan.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)
            );
        }

        // ===== STOCK =====
        h.tvHetHang.setVisibility(
                sp.getSoLuong() <= 0 ? View.VISIBLE : View.GONE
        );

        // ===== IMAGE =====
        if (sp.getAnh() != null && !sp.getAnh().trim().isEmpty()) {
            int resId = context.getResources().getIdentifier(
                    sp.getAnh(),
                    "drawable",
                    context.getPackageName()
            );

            h.imgSanPham.setImageResource(
                    resId != 0 ? resId : android.R.drawable.ic_menu_gallery
            );
        } else {
            h.imgSanPham.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // ===== CLICK =====
        h.card.setOnClickListener(v -> listener.onClick(sp));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "Không có" : s;
    }

    // ================= VIEW HOLDER =================
    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        ImageView imgSanPham;

        TextView tvTen, tvHang, tvGioiTinh;
        TextView tvGiaBan, tvGiaKm, tvKm, tvHetHang;

        // NEW UI
        TextView tvChatLieuVo, tvChatLieuDay, tvKinh, tvBaoHanh;

        ViewHolder(View v) {
            super(v);

            card = v.findViewById(R.id.card);
            imgSanPham = v.findViewById(R.id.imgSanPham);

            tvTen = v.findViewById(R.id.tvTen);
            tvHang = v.findViewById(R.id.tvHang);
            tvGioiTinh = v.findViewById(R.id.tvGioiTinh);

            tvGiaBan = v.findViewById(R.id.tvGiaBan);
            tvGiaKm = v.findViewById(R.id.tvGiaKm);
            tvKm = v.findViewById(R.id.tvKm);
            tvHetHang = v.findViewById(R.id.tvHetHang);

            // NEW VIEW IDS
            tvChatLieuVo = v.findViewById(R.id.tvChatLieuVo);
            tvChatLieuDay = v.findViewById(R.id.tvChatLieuDay);
            tvKinh = v.findViewById(R.id.tvKinh);
            tvBaoHanh = v.findViewById(R.id.tvBaoHanh);
        }
    }
}