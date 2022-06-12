package com.example.mainproject0606;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    // PostAdapter 에서 사용할 변수 선언
    private ArrayList<Post> arrayList;
    private Context context;
    public static String BoardingPoint, Destination, Time, Personnel, InputTime;

    public PostAdapter () {}

    public PostAdapter(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 리사이클러뷰가 어댑터에 연결된 다음에 여기서 최초로 Viewholder를 만들어냄
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list,parent,false); // post_list.xml을 View로 연결
        PostViewHolder holder = new PostViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        // 배열에는 게시글 목록들이 들어갈건데 거기서 맞는 것을 찾아오는 것임
        holder.Read_BoardingPoint.setText(arrayList.get(position).getBoardingPoint()); // Read_BoardingPoint에 탑승지 출력
        holder.Read_Destination.setText(arrayList.get(position).getDestination()); // Read_Destination에 목적지 출력
        holder.Read_Time.setText(arrayList.get(position).getTime()); // Read_Time에 탑승 시간 출력
        holder.Read_Personnel.setText(arrayList.get(position).getPersonnel() + "명"); // Read_Personnel에 모집 인원 출력
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자자 / if문이라고 생각하면 됨, if(arrayList != null) 참이면 arrayList.size()이고 거짓이면 0
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        // 아이템 뷰에서 사용할 변수 선언
        TextView Read_BoardingPoint, Read_Destination, Read_Time, Read_Personnel;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.Read_BoardingPoint = itemView.findViewById(R.id.Read_BoardingPoint);
            this.Read_Destination = itemView.findViewById(R.id.Read_Destination);
            this.Read_Time = itemView.findViewById(R.id.Read_Time);
            this.Read_Personnel = itemView.findViewById(R.id.Read_Personnel);

            // itemview(게시글)이 클릭됐을 경우
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPos = getAbsoluteAdapterPosition(); // PostAdapter로부터 클릭된 포지션을 포지션으로 가져온다.
                    // 배열의 0번째 1번째 2번째... 처럼 그 배열값을 가지고 오는 것임
                    Post post = arrayList.get(currentPos); // 클릭된 포지션에 있는 아이템을 가지고 온 것임
                    // 그러면 Post 안에는 클릭된 아이템 하나의 뭉치가 담겨 있을 것이다.
                    Intent intent = new Intent(view.getContext(), PostScreen.class);
                    context.startActivity(intent);

                    InputTime = post.getInputTime();
                    BoardingPoint = post.getBoardingPoint();
                    Destination = post.getDestination();
                    Time = post.getTime();
                    Personnel = post.getPersonnel();
                }
            });
        }
    }
}
