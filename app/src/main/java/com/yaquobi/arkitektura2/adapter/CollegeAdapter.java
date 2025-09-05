package com.yaquobi.arkitektura2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yaquobi.arkitektura2.R;
import com.yaquobi.arkitektura2.model.CollegeModel;

import java.util.List;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.CollegeViewHolder> {

    private List<CollegeModel> collegeList;
    private OnItemClickListener listener;

    // Custom interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CollegeAdapter(List<CollegeModel> collegeList) {
        this.collegeList = collegeList;
    }

    @NonNull
    @Override
    public CollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_college, parent, false);
        return new CollegeViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeViewHolder holder, int position) {
        CollegeModel college = collegeList.get(position);
        holder.collegeImage.setImageResource(college.getImageRes());
        holder.collegeLogo.setImageResource(college.getLogoRes());
        holder.collegeName.setText(college.getName());
    }

    @Override
    public int getItemCount() {
        return collegeList.size();
    }

    static class CollegeViewHolder extends RecyclerView.ViewHolder {

        CardView collegeCard;
        ImageView collegeImage, collegeLogo;
        TextView collegeName;

        public CollegeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            collegeCard = itemView.findViewById(R.id.collegeCard);
            collegeImage = itemView.findViewById(R.id.collegeImage);
            collegeLogo = itemView.findViewById(R.id.collegeLogo);
            collegeName = itemView.findViewById(R.id.collegeName);

            collegeCard.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
