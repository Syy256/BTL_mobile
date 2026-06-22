package com.example.watchstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.model.SanPham;
import com.example.watchstoreapp.utils.FormatUtils;

import java.util.List;

public class AdminSanPhamAdapter extends RecyclerView.Adapter<AdminSanPhamAdapter.ViewHolder> {

    private Context ctx;
    private List<SanPham> list;

    private OnEdit onEdit;
    private OnDelete onDelete;

    public interface OnEdit { void onEdit(SanPham sp); }
    public interface OnDelete { void onDelete(SanPham sp); }

    public AdminSanPhamAdapter(Context ctx, List<SanPham> list, OnEdit onEdit, OnDelete onDelete) {
        this.ctx = ctx;
        this.list = list;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx)
                .inflate(R.layout.item_admin_san_pham, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {

        SanPham sp = list.get(pos);

        h.tvTen.setText(sp.getTen());
        h.tvHang.setText(sp.getTenHang() != null ? sp.getTenHang() : "");

        h.tvGia.setText(FormatUtils.formatPrice(sp.getGiaBan()));
        h.tvSoLuong.setText("Tồn: " + sp.getSoLuong());

        h.tvTrangThai.setText(
                sp.getTrangThai() == 1 ? "Đang bán" : "Ngừng bán"
        );

        // ===== NEW WATCH INFO =====
        h.tvChatLieuVo.setText("Vỏ: " + safe(sp.getChatLieuVo()));
        h.tvChatLieuDay.setText("Dây: " + safe(sp.getChatLieuDay()));
        h.tvKinh.setText("Kính: " + safe(sp.getKinh()));
        h.tvBaoHanh.setText("BH: " + sp.getBaoHanh() + " tháng");

        // ===== IMAGE =====
        if (sp.getAnh() != null && !sp.getAnh().trim().isEmpty()) {
            int resId = ctx.getResources().getIdentifier(
                    sp.getAnh(),
                    "drawable",
                    ctx.getPackageName()
            );

            h.imgSanPham.setImageResource(
                    resId != 0 ? resId : android.R.drawable.ic_menu_gallery
            );
        } else {
            h.imgSanPham.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // ===== SAFE CLICK =====
        h.btnSua.setOnClickListener(v -> {
            if (onEdit != null) onEdit.onEdit(sp);
        });

        h.btnXoa.setOnClickListener(v -> {
            if (onDelete != null) onDelete.onDelete(sp);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    private String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "Không có" : s;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSanPham;
        TextView tvTen, tvHang, tvGia, tvSoLuong, tvTrangThai;

        // NEW FIELDS
        TextView tvChatLieuVo, tvChatLieuDay, tvKinh, tvBaoHanh;

        ImageButton btnSua, btnXoa;

        ViewHolder(View v) {
            super(v);

            imgSanPham = v.findViewById(R.id.imgSanPham);
            tvTen = v.findViewById(R.id.tvTen);
            tvHang = v.findViewById(R.id.tvHang);
            tvGia = v.findViewById(R.id.tvGia);
            tvSoLuong = v.findViewById(R.id.tvSoLuong);
            tvTrangThai = v.findViewById(R.id.tvTrangThai);

            // NEW
            tvChatLieuVo = v.findViewById(R.id.tvChatLieuVo);
            tvChatLieuDay = v.findViewById(R.id.tvChatLieuDay);
            tvKinh = v.findViewById(R.id.tvKinh);
            tvBaoHanh = v.findViewById(R.id.tvBaoHanh);

            btnSua = v.findViewById(R.id.btnSua);
            btnXoa = v.findViewById(R.id.btnXoa);
        }
    }
}