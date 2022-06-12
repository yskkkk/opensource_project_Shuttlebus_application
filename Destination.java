package com.example.mainproject0606;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Destination extends AppCompatActivity {

    // Destination Activity에서 사용할 변수 선언
    private Button Btn_GoSeongNam, Btn_GoAnsan;

    public static String Send_Destination = null; // 사용자가 버튼을 눌렀을 때의 값을 담기 위해 변수 선언
    public static Context context; // 다른 클래스에서의 접근을 위함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination); // activity_destination.xml에서 설정된 View들을 가져옴

        // activity_destination.xml에서 설정된 View들을 가져와서 정의
        Btn_GoSeongNam = findViewById(R.id.Btn_GoSeongnam);
        Btn_GoAnsan = findViewById(R.id.Btn_GoAnsan);

        // 성남 버튼 클릭 시
        Btn_GoSeongNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send_Destination = "성남행";
                Intent intent = new Intent(Destination.this, RegionalMap_Destination.class); // 현재 액티비티(Destination)에서 지역별맵 액티비티(RegionalMap_Destination)를 연결
                startActivity(intent); // 지역별_목적지 액티비티(Regional_Destination)를 열어준다.
            }
        });

        // 안산 버튼 클릭 시
        Btn_GoAnsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send_Destination = "안산행"; // String 변수 Send_Destination에 "안산" 저장
                Intent intent = new Intent(Destination.this, RegionalMap_Destination.class); // 현재 액티비티(Destination)에서 지역별맵 액티비티(RegionalMap_Destination)를 연결
                startActivity(intent); // 지역별_목적지 액티비티(Regional_Destination)를 열어준다.
            }
        });
    }
}
