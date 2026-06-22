package com.example.watchstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.model.NguoiDung;

import java.util.List;

public class AdminNguoiDungAdapter extends RecyclerView.Adapter<AdminNguoiDungAdapter.ViewHolder> {
    private Context ctx;
    private List<NguoiDung> list;

    public AdminNguoiDungAdapter(Context ctx, List<NguoiDung> list) {
        this.ctx = ctx; this.list = list;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_admin_nguoi_dung, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        NguoiDung nd = list.get(pos);
        h.tvHoTen.setText(nd.getHoTen());
        h.tvEmail.setText(nd.getEmail() != null ? nd.getEmail() : "");
        h.tvSdt.setText(nd.getSdt() != null ? nd.getSdt() : "");
        h.tvVaiTro.setText(nd.getVaiTro() != null ? nd.getVaiTro().toUpperCase() : "");
    }

    @Override public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHoTen, tvEmail, tvSdt, tvVaiTro;
        ViewHolder(View v) {
            super(v);
            tvHoTen = v.findViewById(R.id.tvHoTen);
            tvEmail = v.findViewById(R.id.tvEmail);
            tvSdt = v.findViewById(R.id.tvSdt);
            tvVaiTro = v.findViewById(R.id.tvVaiTro);
        }
    }
}
