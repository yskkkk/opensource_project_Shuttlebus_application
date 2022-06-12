package com.example.mainproject0606;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RegionalMap_Destination extends AppCompatActivity implements OnMapReadyCallback {

    // 구글맵 API를 보여주기 위한 변수 선언
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    // Destination의 변수를 읽어서 저장할 String 변수 선언
    String Send_Destination;

    // RegionalMap_Destination에서 사용할 변수 선언
    private TextView Guide_BoardingPoint, Guide_MonThu, Guide_Fri;
    private Button Print_MonThuTime1, Print_MonThuTime2, Print_FriTime1, Print_FriTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regional_map_destination); // activity_regional_map_destination.xml에서 설정된 View들을 가져와서 정의

        // activity_regional_map_destination.xml에서 설정된 View들을 가져와서 정의
        Guide_BoardingPoint = findViewById(R.id.Guide_BoardingPoint);
        Guide_MonThu = findViewById(R.id.Guide_MontoThu);
        Guide_Fri = findViewById(R.id.Guide_Fri);
        Print_MonThuTime1 = findViewById(R.id.Print_MonThuTime1);
        Print_MonThuTime2 = findViewById(R.id.Print_MonThuTime2);
        Print_FriTime1 = findViewById(R.id.Print_FriTime1);
        Print_FriTime2 = findViewById(R.id.Print_FriTime2);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        Send_Destination = ((Destination)Destination.context).Send_Destination;

        if(Send_Destination.equals("성남행")){

            LatLng location = new LatLng(36.800453507607294, 127.07128511047112); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("성남행 통학버스"); // 마커의 이름을 "성남행 통학버스"라 한다.
            markerOptions.snippet("선문대학교 버스정류장"); // 마커의 세부 내용을 "선문대학교 버스정류장"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_BoardingPoint.setText("탑승지(선문대학교 버스정류장)"); // 상단 제목을 "모란역 8번출구 앞"으로 함
            Print_MonThuTime1.setText("15:40"); // 월~목의 첫번째 시간을 "15:40"으로 한다.
            Print_MonThuTime2.setText("17:40"); // 월~목의 두번째 시간을 "17:40"으로 한다.
            Print_FriTime1.setText("14:40"); // 금의 첫번째 시간을 "14:40"으로 한다.
            Print_FriTime2.setText("15:40"); // 금의 두번째 시간을 "15:40"으로 한다.

        } else if(Send_Destination.equals("안산행")){

            Log.d("태그", "성공");
            LatLng location = new LatLng(36.800453507607294, 127.07128511047112); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("안산행 통학버스"); // 마커의 이름을 "인천 통학버스"라 한다.
            markerOptions.snippet("선문대학교 버스정류장"); // 마커의 세부 내용을 "구올담 치과병원 앞"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_BoardingPoint.setText("탑승지(선문대학교 버스정류장)"); // 상단 제목을 "탑승지(선문대학교 버스정류장)"으로 함
            Print_MonThuTime1.setText("16:40"); // 월~목의 시간을 "16:40"으로 함
            Print_MonThuTime2.setVisibility(View.GONE);
            Print_FriTime1.setText("15:40"); // 금의 시간을 "15:40"으로 함
            Print_FriTime2.setVisibility(View.GONE);
        }
    }
}
