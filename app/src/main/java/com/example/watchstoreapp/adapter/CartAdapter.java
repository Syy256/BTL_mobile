package com.example.watchstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.model.GioHang;
import com.example.watchstoreapp.utils.FormatUtils;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<GioHang> list;
    private OnDelete onDelete;
    private OnQtyChange onQtyChange;

    public interface OnDelete { void onDelete(GioHang item); }
    public interface OnQtyChange { void onChange(GioHang item, int qty); }

    public CartAdapter(Context ctx, List<GioHang> list, OnDelete onDelete, OnQtyChange onQtyChange) {
        this.context = ctx; this.list = list; this.onDelete = onDelete; this.onQtyChange = onQtyChange;
    }

    public void updateData(List<GioHang> newList) { list = newList; notifyDataSetChanged(); }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        GioHang gh = list.get(pos);
        h.tvTen.setText(gh.getTenSanPham());
        h.tvGia.setText(FormatUtils.formatPrice(gh.getGiaHienTai()));
        h.tvSoLuong.setText(String.valueOf(gh.getSoLuong()));
        h.tvThanhTien.setText(FormatUtils.formatPrice(gh.getTongTien()));

        h.btnMinus.setOnClickListener(v -> {
            if (gh.getSoLuong() > 1) onQtyChange.onChange(gh, gh.getSoLuong() - 1);
        });
        h.btnPlus.setOnClickListener(v -> {
            if (gh.getSoLuong() < gh.getSoLuongTon()) onQtyChange.onChange(gh, gh.getSoLuong() + 1);
        });
        h.btnXoa.setOnClickListener(v -> onDelete.onDelete(gh));
    }

    @Override public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvGia, tvSoLuong, tvThanhTien;
        Button btnMinus, btnPlus, btnXoa;
        ViewHolder(View v) {
            super(v);
            tvTen = v.findViewById(R.id.tvTen);
            tvGia = v.findViewById(R.id.tvGia);
            tvSoLuong = v.findViewById(R.id.tvSoLuong);
            tvThanhTien = v.findViewById(R.id.tvThanhTien);
            btnMinus = v.findViewById(R.id.btnMinus);
            btnPlus = v.findViewById(R.id.btnPlus);
            btnXoa = v.findViewById(R.id.btnXoa);
        }
    }
}
