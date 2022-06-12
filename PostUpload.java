package com.example.mainproject0606;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostUpload extends AppCompatActivity {

    // Friebase 연결
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    // Post 객체 참조
    private Post post = new Post();

    // PostUpload Activity에서 사용할 변수 선언
    private EditText ET_BoardingPoint, ET_Destination, ET_Time, ET_Personnel;
    private Button Btn_Upload;

    public static String BoardingPoint, Destination, Time, Personnel, InputTime, Total;

    // 다른 클래스에서의 접근을 위함
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upload); // activity_post_upload.xml에서 설정된 View들을 가져옴

        // activity_post_upload.xml에서 설정된 View들을 정의함
        ET_BoardingPoint = findViewById(R.id.ET_BoardingPoint);
        ET_Destination = findViewById(R.id.ET_Destination);
        ET_Time = findViewById(R.id.ET_Time);
        ET_Personnel = findViewById(R.id.ET_Personnel);

        Btn_Upload = findViewById(R.id.Btn_Upload);

        // Firebase와의 연결
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("Post");

        Btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardingPoint = ET_BoardingPoint.getText().toString(); // String 변수 BoardingPoint에 사용자가 입력한 "탑승지(출발지)" 값 저장
                Destination = ET_Destination.getText().toString(); // String 변수 Destination에 사용자가 입력한 "목적지" 값 저장
                Time = ET_Time.getText().toString(); // String 변수 Time에 사용자가 입력한 "탑승 시간" 값 저장
                Personnel = ET_Personnel.getText().toString(); // String 변수 Personnel에 사용자가 입력한 "인원" 값 저장

                if(TextUtils.isEmpty(BoardingPoint) || TextUtils.isEmpty(Destination) || TextUtils.isEmpty(Time) || TextUtils.isEmpty(Personnel)){
                    Toast.makeText(PostUpload.this , "빈칸없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    InputTime = getDateTime(); // String 변수 InputTime에 사용자가 게시글을 "업로드한 시간" 값 저장

                    Total = InputTime + BoardingPoint + Destination + Time + Personnel; // Total은 게시글의 고유 값이 될 것임. 게시글 정보로 이루어져 있다.

                    post.setEmail(((CallbenMain)CallbenMain.context).userEmail);
                    post.setBoardingPoint(BoardingPoint);
                    post.setDestination(Destination);
                    post.setTime(Time);
                    post.setPersonnel(Personnel);
                    post.setInputTime(InputTime);

                    databaseRef.child(Total).setValue(post); // RealtimeDatabase에 부모 키 값을 Total로 하고 그 자식으로 post객체에 담긴 값을 저장한다.

                    // 게시글 목록 화면으로 넘어감
                    Intent intent = new Intent(PostUpload.this, PostList.class);
                    startActivity(intent);

                    Toast.makeText(PostUpload.this, "게시글이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getDateTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년MM월dd일 HH시 mm분");
        String getDateTime = dateFormat.format(date);

        return getDateTime;
    }
}
