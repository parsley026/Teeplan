package com.example.teeplan.coupon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.R;

import java.util.ArrayList;

public class CouponRecyclerViewAdapter extends RecyclerView.Adapter<CouponRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CouponModel> couponModels;

    public CouponRecyclerViewAdapter(Context context, ArrayList<CouponModel> couponModels) {
        this.context = context;
        this.couponModels = couponModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new CouponRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(couponModels.get(position).getName());
        holder.tvDescription.setText(couponModels.get(position).getDescription());
        holder.tvCode.setText(couponModels.get(position).getCode());
    }

    @Override
    public int getItemCount() {
        return couponModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDescription, tvCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.couponName);
            tvDescription = itemView.findViewById(R.id.couponDescription);
            tvCode = itemView.findViewById(R.id.couponCode);
        }
    }
}
