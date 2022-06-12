package com.example.mainproject0606;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    // CommentAdapter에서 사용할 변수 선언
    private ArrayList<Comment> arrayList;
    private Context context;

    // Firebase와 연결을 위한 변수
    FirebaseDatabase database;
    DatabaseReference databaseRef;

    public CommentAdapter() { }

    public CommentAdapter(ArrayList<Comment> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 리사이클러뷰가 어댑터에 연결된 다음에 여기서 최초로 Viewholder를 만들어냄
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list, parent, false);
        CommentViewHolder holder = new CommentViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // 배열에는 댓글 목록들이 들어갈건데 거기서 맞는 것을 찾아오는 것임
        holder.Read_userNickname.setText(arrayList.get(position).getUserNickname());
        holder.Read_userComment.setText(arrayList.get(position).getUserComment());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자자 / if문이라고 생각하면 됨, if(arrayList != null) 참이면 arrayList.size()이고 거짓이면 0
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        // itemView에서 사용할 변수 선언
        TextView Read_userNickname, Read_userComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.Read_userNickname = itemView.findViewById(R.id.Read_Nickname);
            this.Read_userComment = itemView.findViewById(R.id.Read_Comment);

            // FirebaseDatabase 연결
            database = FirebaseDatabase.getInstance();
            databaseRef = database.getReference("댓글");

            // itemView(댓글)이 클릭되었을 때
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
