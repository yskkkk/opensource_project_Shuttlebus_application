package com.example.mainproject0606;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostScreen extends AppCompatActivity {

    // PostAdapter에서 게시글 정보를 가져오기 위함
    PostAdapter postAdapter = new PostAdapter();

    // PostScreen Activity에서 사용할 변수 선언
    private TextView Read_InputTime, Read_BoardingPoint, Read_Destination, Read_Time, Read_Personnel;
    private EditText ET_Comment;
    private ImageView Iv_Back, Iv_DeletePost;
    private Button Btn_Send;
    private String Total, Email, InputTime, BoardingPoint, Destination, Time, Personnel, Commentmsg;

    // Firebase와의 연결
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    // RecyclerView를 사용하기 위한 변수 선언
    private RecyclerView CommentrecyclerView;
    private RecyclerView.Adapter Commentadapter;
    private ArrayList<Comment> CommentarrayList;
    private Comment comment = new Comment();

    public static String CommentInputTime;

    // 다른 클래스에서의 접근을 위함
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_screen); // activity_post_screen.xml에서 설정된 View들을 가져옴

        // activity_post_screen.xml에서 설정된 View들을 가져와서 정의
        Read_InputTime = findViewById(R.id.Read_InputTime);
        Read_BoardingPoint = findViewById(R.id.Read_BoardingPoint);
        Read_Destination = findViewById(R.id.Read_Destination);
        Read_Time = findViewById(R.id.Read_Time);
        Read_Personnel = findViewById(R.id.Read_Personnel);

        ET_Comment = findViewById(R.id.ET_Comment);

        Iv_Back = findViewById(R.id.Iv_Back);
        Iv_DeletePost = findViewById(R.id.Iv_DeletePost);

        Btn_Send = findViewById(R.id.Btn_Send);

        // Firebase와의 연결
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();

        // RecyclerView를 사용하기 위한 정의
        CommentrecyclerView = findViewById(R.id.CommentRecyclerView);
        CommentrecyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        LinearLayoutManager CommentLayoutManager = new LinearLayoutManager(this);
        CommentrecyclerView.setLayoutManager(CommentLayoutManager);
        CommentarrayList = new ArrayList<>();

        // 게시글 정보 출력 영역 설정
        Read_InputTime.setText(postAdapter.InputTime);
        Read_BoardingPoint.setText(postAdapter.BoardingPoint);
        Read_Destination.setText(postAdapter.Destination);
        Read_Time.setText(postAdapter.Time);
        Read_Personnel.setText(postAdapter.Personnel + "명");

        // RealtimeDatabase에서 부모 노드를 참조하기 위해 Total에 게시글 정보를 합친 값을 담아준다. (게시글을 업로드 할때 부모 노드를 게시글 정보를 합친 값으로 하였기 때문)
        Total = postAdapter.InputTime + postAdapter.BoardingPoint + postAdapter.Destination + postAdapter.Time + postAdapter.Personnel;

        // Back버튼 클릭 시(뒤로가기)
        Iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 게시글 목록 화면으로 넘어감
                Intent intent = new Intent(PostScreen.this, PostList.class);
                startActivity(intent);
            }
        });

        // 삭제 버튼 클릭 시
        Iv_DeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // RealtimeDatabase의 Post 참조
                databaseRef.child("Post").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean compare = true; // 불리언 compare(비교) 변수 선언 및 true 초기화
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                            Email = snapshot.getValue(Post.class).getEmail();
                            InputTime = snapshot.getValue(Post.class).getInputTime();
                            BoardingPoint = snapshot.getValue(Post.class).getBoardingPoint();
                            Destination = snapshot.getValue(Post.class).getDestination();
                            Time = snapshot.getValue(Post.class).getTime();
                            Personnel = snapshot.getValue(Post.class).getPersonnel();

                            if(Email.equals(((CallbenMain)CallbenMain.context).userEmail)){ // 만약 Post에서 가져온 Email과 현재 로그인한 사용자의 Email이 같을 시
                                if(InputTime.equals(postAdapter.InputTime) && BoardingPoint.equals(postAdapter.BoardingPoint) && Destination.equals(postAdapter.Destination)
                                        && Time.equals(postAdapter.Time) && Personnel.equals(postAdapter.Personnel)){ // itemView를 눌렀을 때의 itemView에 담겨있는 값과 현재 게시글 값이 일치하면
                                    compare = true;
                                    break;
                                }
                            } else {
                                compare = false;
                            }
                        }
                        if(compare){ // 참이면 (즉, 게시글을 등록한 Email과 로그인한 사용자의 Email이 일치할 시)
                            databaseRef.child("Post").child(Total).removeValue(); // RealtimeDatabase에서 게시글 정보를 찾아 삭제
                            databaseRef.child("댓글").child(Total).removeValue(); // RealtimeDatabase에서 이 게시글에 해당하는 댓글 정보를 찾아 삭제
                            // 게시글 목록 화면으로 전환
                            Intent intent = new Intent(PostScreen.this, PostList.class);
                            startActivity(intent);
                        } else { // 거짓이면 (즉, 게시글을 등록한 Email과 로그인한 사용자의 Email이 일치하지 않을 시)
                            Toast.makeText(PostScreen.this, "게시글 삭제 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        // 전송 버튼 클릭 시
        Btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commentmsg = ET_Comment.getText().toString(); // String 변수 Commentmsg에 사용자가 입력한 "댓글" 값 저장

                comment.setUserEmail(((PostList)PostList.context).Email);
                comment.setUserNickname(((PostList)PostList.context).Nickname);
                comment.setUserComment(Commentmsg);

                if(TextUtils.isEmpty(Commentmsg)){ // 만약 댓글을 입력하지 않았을 시
                    Toast.makeText(PostScreen.this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    databaseRef.child("댓글").child(Total).push().setValue(comment); // RealtimeDatabase에 "댓글"이라는 부모 키를 두고 그 아래에 값을 저장함
                    ET_Comment.setText(null);
                }

                // RealtimeDatabase의 "댓글"이라는 부모 키 아래에 게시글 고유 아이디를 찾아가서 참조 // Send버튼을 눌렀을 때 RecyclerView가 바로 갱신될 수 있도록 넣어줌
                databaseRef.child("댓글").child(Total).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        CommentarrayList.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                            Comment comment = snapshot.getValue(Comment.class);
                            CommentarrayList.add(comment);
                        }
                        Commentadapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        // RecyclerView에 댓글을 보여줄 코드
        databaseRef.child("댓글").child(Total).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CommentarrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                    Comment comment = snapshot.getValue(Comment.class);
                    CommentarrayList.add(comment);
                }
                Commentadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Commentadapter = new CommentAdapter(CommentarrayList, this);
        CommentrecyclerView.setAdapter(Commentadapter);
    }
}
