package com.example.mainproject0606;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproject0606.RegionalMap;

public class BoardingPoint extends AppCompatActivity {

    // BoardingPoint Activity 에서 사용할 변수 선언
    private Button Btn_SeongNam, Btn_Dongcheon, Btn_Jukjeon, Btn_Ansan, Btn_Suwon, Btn_Incheon;

    // 팝업 화면의 버튼을 위한 변수 선언
    private Button Btn_FirstPrintArea, Btn_SecondPrintArea, Btn_ThirdPrintArea, Btn_FourthPrintArea, Btn_FifthPrintArea;

    // 팝업 화면을 위한 변수 선언
    Dialog Regional_Popup;

    public static String Send = null; // 사용자가 버튼을 눌렀을 때 값을 담을 변수 선언
    public static Context context; // 다른 클래스에서의 접근을 위함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding_point); // activity_boarding_point.xml에서 설정된 View들을 가져온다.

        // activity_boarding_point.xml에서 설정된 View들을 가져와서 정의
        Btn_SeongNam = findViewById(R.id.Btn_SeongNam); // 성남 버튼
        Btn_Dongcheon = findViewById(R.id.Btn_Dongcheon); // 동천역Bs 버튼
        Btn_Jukjeon = findViewById(R.id.Btn_Jukjeon); // 죽전 버튼
        Btn_Ansan = findViewById(R.id.Btn_Ansan); // 안산 버튼
        Btn_Suwon = findViewById(R.id.Btn_Suwon); // 수원 버튼
        Btn_Incheon = findViewById(R.id.Btn_Incheon); // 인천 버튼

        // 팝업 화면 정의
        Regional_Popup = new Dialog(BoardingPoint.this); // 팝업 화면을 현재 액티비티(BoardingActivity)에서 띄우겠다.
        Regional_Popup.requestWindowFeature(Window.FEATURE_NO_TITLE); // 팝업 화면의 제목을 지워준다.
        Regional_Popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 팝업 화면의 배경을 투명하게 만들어 준다.
        Regional_Popup.setContentView(R.layout.activity_regional_popup); // 팝업 화면의 정보는 activity_regional_popup.xml에서 가져온다.

        // 팝업 화면의 버튼 정의
        Btn_FirstPrintArea = Regional_Popup.findViewById(R.id.Btn_FirstPrintArea);
        Btn_SecondPrintArea = Regional_Popup.findViewById(R.id.Btn_SecondPrintArea);
        Btn_ThirdPrintArea = Regional_Popup.findViewById(R.id.Btn_ThirdPrintArea);
        Btn_FourthPrintArea = Regional_Popup.findViewById(R.id.Btn_FourthPrintArea);
        Btn_FifthPrintArea = Regional_Popup.findViewById(R.id.Btn_FifthPrintArea);

        // 성남 버튼 클릭 시
        Btn_SeongNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regional_Popup.show(); // 팝업 화면을 보여주겠다.

                Btn_FirstPrintArea.setVisibility(View.VISIBLE);
                Btn_FirstPrintArea.setText("모란역 8번 출구"); // 첫번째 버튼의 텍스트를 "모란역 8번 출구"로 함
                Btn_SecondPrintArea.setVisibility(View.VISIBLE);
                Btn_SecondPrintArea.setText("야탑역 하나은행 앞"); // 두번째 버튼의 텍스트를 "야탑역 하나은행 앞"로 함
                Btn_ThirdPrintArea.setVisibility(View.VISIBLE);
                Btn_ThirdPrintArea.setText("서현역에서 수내역방향"); // 세번째 버튼의 텍스트를 "서현역에서 수내역방향"으로 함
                Btn_FourthPrintArea.setVisibility(View.GONE); // 네번째 버튼 비활성화
                Btn_FifthPrintArea.setVisibility(View.GONE); // 다섯번째 버튼 비활성화

                // 첫번째 버튼(모란역 8번 출구) 클릭 시
                Btn_FirstPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "모란역"; // String 변수 Send에 "모란" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });

                // 두번째 버튼(야탑역 하나은행 앞) 클릭 시
                Btn_SecondPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "야탑역"; // String 변수 Send에 "야탑" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });

                // 세번째 버튼(서현역에서 수내역 방향) 클릭 시
                Btn_ThirdPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "서현역"; // String 변수 Send에 "서현역" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });

            }
        });

        // 동천역BS 버튼 클릭 시
        Btn_Dongcheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send = "동천역"; // String 변수 Send에 "동천역" 저장
                Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
            }
        });

        // 죽전 버튼 클릭 시
        Btn_Jukjeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send = "죽전"; // String 변수 Send에 "죽전" 저장
                Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
            }
        });

        // 안산 버튼 클릭 시
        Btn_Ansan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regional_Popup.show(); // 팝업 화면을 보여주겠다.

                Btn_FirstPrintArea.setVisibility(View.VISIBLE);
                Btn_FirstPrintArea.setText("중앙역 버스정류장"); // 첫번째 버튼의 텍스트를 "중앙역 버스정류장"으로 함
                Btn_SecondPrintArea.setVisibility(View.VISIBLE);
                Btn_SecondPrintArea.setText("상록수역"); // 두번째 버튼의 텍스트를 "상록수역"으로 함
                Btn_ThirdPrintArea.setVisibility(View.GONE); // 세번째 버튼 비활성화
                Btn_FourthPrintArea.setVisibility(View.GONE); // 네번째 버튼 비활성화
                Btn_FifthPrintArea.setVisibility(View.GONE); // 다섯번째 버튼 비활성화

                // 첫번째 버튼(중앙역 버스정류장) 클릭 시
                Btn_FirstPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "중앙역"; // String 변수 Send에 "중앙역" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });

                // 두번째 버튼(상록수역) 클릭 시
                Btn_SecondPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "상록수역"; // String 변수 Send에 "상록수역" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });
            }
        });

        // 수원 버튼 클릭 시
        Btn_Suwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regional_Popup.show(); // 팝업 화면을 보여주겠다.

                Btn_FirstPrintArea.setVisibility(View.VISIBLE);
                Btn_FirstPrintArea.setText("동수원병원 건너 횡단보도"); // 첫번째 버튼의 텍스트를 "동수원병원 건너 횡단보도"로 함
                Btn_SecondPrintArea.setVisibility(View.VISIBLE);
                Btn_SecondPrintArea.setText("아주대입구 우체국 앞"); // 두번째 버튼의 텍스트를 "아주대입구 우체국 앞"으로 함
                Btn_ThirdPrintArea.setVisibility(View.VISIBLE);
                Btn_ThirdPrintArea.setText("청명역(2번 출구)"); // 세번째 버튼의 텍스트를 "청명역(2번 출구)"로 함
                Btn_FourthPrintArea.setVisibility(View.VISIBLE);
                Btn_FourthPrintArea.setText("영통입구 버스정류장"); // 네번째 버튼의 텍스트를 "영통입구 버스정류장"으로 함
                Btn_FifthPrintArea.setVisibility(View.VISIBLE);
                Btn_FifthPrintArea.setText("수원IC 입구"); // 다섯번째 버튼의 텍스트를 "수원IC 입구"로 함

                // 첫번째 버튼(동수원병원 건너 횡단보도) 클릭 시
                Btn_FirstPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "동수원병원"; // String 변수 Send에 "동수원병원" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });

                // 두번째 버튼(아주대입구 우체국 앞) 클릭 시
                Btn_SecondPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "아주대입구"; // String 변수 Send에 "아주대입구" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });

                // 세번째 버튼(청명역(2번 출구)) 클릭 시
                Btn_ThirdPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "청명역"; // String 변수 Send에 "청명역" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });

                // 네번째 버튼(영통입구 버스정류장) 클릭 시
                Btn_FourthPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "영통입구"; // String 변수 Send에 "영통입구" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });

                // 다섯번째 버튼(수원IC 입구) 클릭 시
                Btn_FifthPrintArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Send = "수원IC"; // String 변수 Send에 "수원IC" 저장
                        Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                        startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
                        Regional_Popup.cancel();
                    }
                });
            }
        });

        // 인천 버튼 클릭 시
        Btn_Incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send = "인천"; // String 변수 Send에 "인천" 저장
                Intent intent = new Intent(BoardingPoint.this, RegionalMap.class); // 현재 액티비티(BoardiongPoint)에서 지역별맵 액티비티(RegionalMap)를 연결
                startActivity(intent); // 지역별 액티비티(RegionalMap)을 열어준다.
            }
        });
    }
}
