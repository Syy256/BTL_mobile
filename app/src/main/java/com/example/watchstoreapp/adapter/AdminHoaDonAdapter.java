package com.example.watchstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.model.HoaDon;
import com.example.watchstoreapp.utils.FormatUtils;

import java.util.List;

public class AdminHoaDonAdapter extends RecyclerView.Adapter<AdminHoaDonAdapter.ViewHolder> {
    private Context ctx;
    private List<HoaDon> list;
    private OnClick onClick;
    private OnStatusChange onStatusChange;
    public interface OnClick { void onClick(HoaDon hd); }
    public interface OnStatusChange { void onChange(HoaDon hd, String trangThai); }

    public AdminHoaDonAdapter(Context ctx, List<HoaDon> list, OnClick onClick, OnStatusChange onStatusChange) {
        this.ctx = ctx; this.list = list; this.onClick = onClick; this.onStatusChange = onStatusChange;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_admin_hoa_don, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        HoaDon hd = list.get(pos);
        h.tvMaHd.setText(hd.getMaHd());
        h.tvKhach.setText(hd.getTenNguoiDung() != null ? hd.getTenNguoiDung() : "");
        h.tvNgay.setText(FormatUtils.formatDateTime(hd.getNgayDat()));
        h.tvTrangThai.setText(hd.getTrangThai());
        h.tvThanhTien.setText(FormatUtils.formatPrice(hd.getThanhTien()));
        h.btnXem.setOnClickListener(v -> onClick.onClick(hd));
        h.btnDoiTT.setOnClickListener(v -> {
            String[] options = {HoaDon.TRANG_THAI_CHO, HoaDon.TRANG_THAI_DANG_GIAO,
                    HoaDon.TRANG_THAI_HOAN_THANH, HoaDon.TRANG_THAI_HUY};
            new AlertDialog.Builder(ctx)
                    .setTitle("Đổi trạng thái")
                    .setItems(options, (d, which) -> onStatusChange.onChange(hd, options[which]))
                    .show();
        });
    }

    @Override public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHd, tvKhach, tvNgay, tvTrangThai, tvThanhTien;
        Button btnXem, btnDoiTT;
        ViewHolder(View v) {
            super(v);
            tvMaHd = v.findViewById(R.id.tvMaHd);
            tvKhach = v.findViewById(R.id.tvKhach);
            tvNgay = v.findViewById(R.id.tvNgay);
            tvTrangThai = v.findViewById(R.id.tvTrangThai);
            tvThanhTien = v.findViewById(R.id.tvThanhTien);
            btnXem = v.findViewById(R.id.btnXem);
            btnDoiTT = v.findViewById(R.id.btnDoiTT);
        }
    }
}
