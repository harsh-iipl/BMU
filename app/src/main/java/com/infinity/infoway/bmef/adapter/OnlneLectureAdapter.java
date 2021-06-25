package com.infinity.infoway.bmef.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.model.JoinOnlineLecturePojo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OnlneLectureAdapter extends RecyclerView.Adapter<OnlneLectureAdapter.MyViewHolder> {

    private Context context;
    private JoinOnlineLecturePojo onlineLectureList;

    public OnlneLectureAdapter(Context context, JoinOnlineLecturePojo onlineLectureList) {
        this.context = context;
        this.onlineLectureList = onlineLectureList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.online_lecture_item_view,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder myViewHolder,final int i) {

        if (!TextUtils.isEmpty(onlineLectureList.getFaculty().get(i).getLecNo()+"")){
            myViewHolder.tvLectureNo.setText(onlineLectureList.getFaculty().get(i).getLecNo()+"");
        }

        if (!TextUtils.isEmpty(onlineLectureList.getFaculty().get(i).getEmpName()+"")){
            myViewHolder.tvFacultyName.setText(onlineLectureList.getFaculty().get(i).getEmpName()+"");
        }

        if (!TextUtils.isEmpty(onlineLectureList.getFaculty().get(i).getSubShortName()+"")){
            myViewHolder.tvSubject.setText(onlineLectureList.getFaculty().get(i).getSubShortName()+"");
        }



        if (!TextUtils.isEmpty(onlineLectureList.getFaculty().get(i).getDvmName()+"")){
            myViewHolder.tvDivision.setText(onlineLectureList.getFaculty().get(i).getDvmName()+"");
        }

        if (!TextUtils.isEmpty(onlineLectureList.getFaculty().get(i).getMeetingUrl()+"")){
            if (onlineLectureList.getFaculty().get(i).getMeetingUrl() != null){
                myViewHolder.tvJoinLecture.setVisibility(View.VISIBLE);
               // myViewHolder.tvJoinLecture.setText(onlineLectureList.getFaculty().get(i).getMeetingUrl()+"");
            }else{
                myViewHolder.tvJoinLecture.setVisibility(View.GONE);

            }

        }

        myViewHolder.tvJoinLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(onlineLectureList.getFaculty().get(i).getMeetingUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return onlineLectureList.getFaculty().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLectureNo,tvFacultyName,tvSubject,tvDivision,tvJoinLecture;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvFacultyName =itemView.findViewById(R.id.tvFacultyName);
            tvLectureNo =itemView.findViewById(R.id.tvLectureNo);
            tvSubject =itemView.findViewById(R.id.tvSubject);
           // tvLectureLink =itemView.findViewById(R.id.tvLectureLink);
            tvDivision =itemView.findViewById(R.id.tvDivision);
            tvJoinLecture =itemView.findViewById(R.id.tvJoinLecture);

        }
    }
}
