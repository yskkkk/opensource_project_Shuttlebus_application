package com.example.mainproject0606;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CallbenNumber extends AppCompatActivity {

    // CallbenNumber Activity에서 사용할 변수 선언
    private ImageView iv_Callben1, iv_Callben2, iv_Callben3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callben_number); // activity_callben_number.xml에서 설정된 View들을 가져옴

        // activity_callben_number.xml에서 설정된 View들을 가져와서 정의
        iv_Callben1 = findViewById(R.id.iv_callben1);
        iv_Callben2 = findViewById(R.id.iv_callben2);
        iv_Callben3 = findViewById(R.id.iv_callben3);

        // 이미지뷰 iv_Callben1 클릭 시
        iv_Callben1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:041-567-3322")); // 전화번호 041-567-3322와 연결
                startActivity(intent); // 전화 화면으로 넘어감
            }
        });

        // 이미지뷰 iv_Callben2 클릭 시
        iv_Callben2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:041-564-7777")); // 전화번호 041-564-7777와 연결
                startActivity(intent); // 전화 화면으로 넘어감
            }
        });

        // 이미지뷰 iv_Callben3 클릭 시
        iv_Callben3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:041-564-8888")); // 전화번호 041-564-8888와 연결
                startActivity(intent); // 전화 화면으로 넘어감
            }
        });
    }
}
