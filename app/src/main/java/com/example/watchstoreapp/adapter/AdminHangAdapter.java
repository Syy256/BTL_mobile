package com.example.watchstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchstoreapp.R;
import com.example.watchstoreapp.model.Hang;

import java.util.List;

public class AdminHangAdapter extends RecyclerView.Adapter<AdminHangAdapter.ViewHolder> {

    private Context ctx;
    private List<Hang> list;
    private OnEdit onEdit;
    private OnDelete onDelete;

    public interface OnEdit {
        void onEdit(Hang h);
    }

    public interface OnDelete {
        void onDelete(Hang h);
    }

    public AdminHangAdapter(Context ctx,
                            List<Hang> list,
                            OnEdit onEdit,
                            OnDelete onDelete) {
        this.ctx = ctx;
        this.list = list;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.item_admin_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        Hang hang = list.get(position);

        h.tvTen.setText(hang.getTen());

        h.tvXuatXu.setText(
                hang.getXuatXu() != null
                        ? hang.getXuatXu()
                        : ""
        );

        h.tvMoTa.setText(
                hang.getMoTa() != null
                        ? hang.getMoTa()
                        : ""
        );

        // Hiển thị logo
        String logo = hang.getLogo();

        if (logo != null && !logo.trim().isEmpty()) {

            int resId = ctx.getResources().getIdentifier(
                    logo,
                    "drawable",
                    ctx.getPackageName()
            );

            if (resId != 0) {
                h.imgLogo.setImageResource(resId);
            } else {
                h.imgLogo.setImageResource(R.drawable.no_image);
            }

        } else {
            h.imgLogo.setImageResource(R.drawable.no_image);
        }

        h.btnSua.setOnClickListener(v -> {
            if (onEdit != null) {
                onEdit.onEdit(hang);
            }
        });

        h.btnXoa.setOnClickListener(v -> {
            if (onDelete != null) {
                onDelete.onDelete(hang);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLogo;
        TextView tvTen, tvXuatXu, tvMoTa;
        ImageButton btnSua, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvXuatXu = itemView.findViewById(R.id.tvXuatXu);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);

            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}