package com.yaquobi.arkitektura2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yaquobi.arkitektura2.R;
import com.yaquobi.arkitektura2.model.FacultyModel;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder> {

    private List<FacultyModel> facultyList;

    public FacultyAdapter(List<FacultyModel> facultyList) {
        this.facultyList = facultyList;
    }

    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faculty, parent, false);
        return new FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHolder holder, int position) {
        FacultyModel faculty = facultyList.get(position);
        holder.facultyImage.setImageResource(faculty.getImageResId());
        holder.facultyName.setText(faculty.getName());
        holder.facultyRole.setText(faculty.getRole());
    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }

    public static class FacultyViewHolder extends RecyclerView.ViewHolder {
        ImageView facultyImage;
        TextView facultyName, facultyRole;

        public FacultyViewHolder(@NonNull View itemView) {
            super(itemView);
            facultyImage = itemView.findViewById(R.id.facultyImage);
            facultyName = itemView.findViewById(R.id.facultyName);
            facultyRole = itemView.findViewById(R.id.facultyRole);
        }
    }
}
