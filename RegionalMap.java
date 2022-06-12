package com.example.mainproject0606;

import android.app.FragmentManager;
import android.os.Bundle;
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

public class RegionalMap extends AppCompatActivity implements OnMapReadyCallback {

    // 구글맵 API를 보여주기 위한 변수 선언
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    // RegionalMap에서 사용할 변수 선언
    private TextView Guide_RegionalName, Guide_MonThu, Guide_Fri;
    private Button Print_MonThuTime, Print_FriTime;

    // BoardingPoint의 변수를 읽어서 저장할 String 변수 선언
    public String Regional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regional_map); // activity_regional_map.xml에서 설정된 View들을 가져옴

        // activity_regional_map.xml에서 설정된 View들을 가져와서 정의
        Guide_RegionalName = findViewById(R.id.Guide_RegionalName);
        Guide_MonThu = findViewById(R.id.Guide_MontoThu);
        Guide_Fri = findViewById(R.id.Guide_Fri);
        Print_MonThuTime = findViewById(R.id.Print_MonThuTime);
        Print_FriTime = findViewById(R.id.Print_FriTime);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        // Send는 BoardingPoint에서 사용자가 버튼을 눌렀을 때 그 버튼의 값을 저장한 변수이다.
        // 사용자가 누른 버튼의 값을 Regional에 저장한다.
        Regional = ((BoardingPoint)BoardingPoint.context).Send;

        if(Regional.equals("모란역")){ // 사용자가 누른 버튼의 값이 "모란역"일 경우

            LatLng location = new LatLng(37.43308685037233, 127.12703901312061); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("모란역 통학버스"); // 마커의 이름을 "모란역 통학버스"라 한다.
            markerOptions.snippet("모란역 8번 출구 앞"); // 마커의 세부 내용을 "모란역 8번 출구 앞"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("모란역 8번출구 앞"); // 상단 제목을 "모란역 8번출구 앞"으로 함
            Print_MonThuTime.setText("07:20"); // 월~목의 시간을 "07:20"으로 함
            Print_FriTime.setText("07:20"); // 금의 시간을 "07:20"으로 함

        } else if(Regional.equals("야탑역")){ // 사용자가 누른 버튼의 값이 "야탑역"일 경우

            LatLng location = new LatLng(37.41135043363349, 127.12854132440532); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("야탑역 통학버스"); // 마커의 이름을 "야탑역 통학버스"라 한다.
            markerOptions.snippet("하나은행 앞"); // 마커의 세부 내용을 "하나은행 앞"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("야탑역 하나은행 앞"); // 상단 제목을 "야탑역 하나은행 앞"으로 함
            Print_MonThuTime.setText("07:30"); // 월~목의 시간을 "07:30"으로 함
            Print_FriTime.setText("07:30"); // 금의 시간을 "07:30"으로 함

        } else if(Regional.equals("서현역")){ // 사용자가 누른 버튼의 값이 "서현역"일 경우

            LatLng location = new LatLng(37.384297299456954, 127.12430313973277); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("서현역 통학버스"); // 마커의 이름을 "서현역 통학버스"라 한다.
            markerOptions.snippet("공항버스 전방 10m지점"); // 마커의 세부 내용을 "공항버스 전방 10m지점"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("서현역에서 수내역방향"); // 상단 제목을 "서현역에서 수내역방향"으로 함
            Print_MonThuTime.setText("07:35"); // 월~목의 시간을 "07:35"으로 함
            Print_FriTime.setText("07:35"); // 금의 시간을 "07:35"으로 함

        } else if(Regional.equals("동천역")){ // 사용자가 누른 버튼의 값이 "동천역"일 경우

            LatLng location = new LatLng(37.338745930869585, 127.10317636011447); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("동천역BS 통학버스"); // 마커의 이름을 "동천역BS 통학버스"라 한다.
            markerOptions.snippet("경부고속도로 간이정류장"); // 마커의 세부 내용을 "경부고속도로 간이정류장"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("동천역BS"); // 상단 제목을 "동천역BS"로 함
            Print_MonThuTime.setText("07:50"); // 월~목의 시간을 "07:50"으로 함
            Print_FriTime.setText("07:50"); // 금의 시간을 "07:50"으로 함

        } else if(Regional.equals("죽전")){ // 사용자가 누른 버튼의 값이 "죽전"일 경우

            LatLng location = new LatLng(37.32620355033618, 127.10320433995459); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("죽전 통학버스"); // 마커의 이름을 "죽전 통학버스"라 한다.
            markerOptions.snippet("경부고속도로 간이정류장"); // 마커의 세부 내용을 "경부고속도로 간이정류장"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("죽전"); // 상단 제목을 "죽전"으로 함
            Print_MonThuTime.setText("07:50"); // 월~목의 시간을 "07:50"으로 함
            Print_FriTime.setText("07:50"); // 금의 시간을 "07:50"으로 함

        } else if(Regional.equals("중앙역")){ // 사용자가 누른 버튼의 값이 "중앙역"일 경우

            LatLng location = new LatLng(37.31632424925622, 126.83879467780739); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("안산 통학버스"); // 마커의 이름을 "안산 통학버스"라 한다.
            markerOptions.snippet("중앙역 버스정류장"); // 마커의 세부 내용을 "중앙역 버스정류장"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("중앙역 버스정류장"); // 상단 제목을 "중앙역 버스정류장"으로 함
            Print_MonThuTime.setText("07:20"); // 월~목의 시간을 "07:20"으로 함
            Print_FriTime.setText("07:20"); // 금의 시간을 "07:20"으로 함

        } else if(Regional.equals("상록수역")){ // 사용자가 누른 버튼의 값이 "상록수역"일 경우

            LatLng location = new LatLng(37.30155760976564, 126.86505027303669); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("안산 통학버스"); // 마커의 이름을 "안산 통학버스"라 한다.
            markerOptions.snippet("상록수역"); // 마커의 세부 내용을 "상록수역"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("상록수역"); // 상단 제목을 "상록수역"으로 함
            Print_MonThuTime.setText("07:25"); // 월~목의 시간을 "07:25"으로 함
            Print_FriTime.setText("07:25"); // 금의 시간을 "07:25"으로 함

        } else if(Regional.equals("동수원병원")){ // 사용자가 누른 버튼의 값이 "동수원병원"일 경우

            LatLng location = new LatLng(37.27731713679248, 127.03490068610118); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("수원 통학버스"); // 마커의 이름을 "수원 통학버스"라 한다.
            markerOptions.snippet("동수원병원 건너 횡단보도"); // 마커의 세부 내용을 "동수원병원 건너 횡단보도"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("동수원병원 건너 횡단보도"); // 상단 제목을 "동수원병원 건너 횡단보도"으로 함
            Print_MonThuTime.setText("07:20"); // 월~목의 시간을 "07:20"으로 함
            Print_FriTime.setText("07:20"); // 금의 시간을 "07:20"으로 함

        } else if(Regional.equals("아주대입구")){ // 사용자가 누른 버튼의 값이 "아주대입구"일 경우

            LatLng location = new LatLng(37.27514554639691, 127.04208482931422); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("수원 통학버스"); // 마커의 이름을 "수원 통학버스"라 한다.
            markerOptions.snippet("아주대입구 우체국 앞"); // 마커의 세부 내용을 "아주대입구 우체국 앞"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("아주대입구 우체국 앞"); // 상단 제목을 "아주대입구 우체국 앞"으로 함
            Print_MonThuTime.setText("07:25"); // 월~목의 시간을 "07:25"으로 함
            Print_FriTime.setText("07:25"); // 금의 시간을 "07:25"으로 함

        } else if(Regional.equals("청명역")){ // 사용자가 누른 버튼의 값이 "청명역"일 경우

            LatLng location = new LatLng(37.25947313637236, 127.07928231779414); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("수원 통학버스"); // 마커의 이름을 "수원 통학버스"라 한다.
            markerOptions.snippet("청명역(2번 출구)"); // 마커의 세부 내용을 "청명역(2번 출구)"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("청명역(2번 출구)"); // 상단 제목을 "청명역(2번 출구)"으로 함
            Print_MonThuTime.setText("07:30"); // 월~목의 시간을 "07:30"으로 함
            Print_FriTime.setText("07:30"); // 금의 시간을 "07:30"으로 함

        } else if(Regional.equals("영통입구")){ // 사용자가 누른 버튼의 값이 "영통입구"일 경우

            LatLng location = new LatLng(37.268703988947486, 127.0830378729845); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("수원 통학버스"); // 마커의 이름을 "수원 통학버스"라 한다.
            markerOptions.snippet("영통입구 버스정류장"); // 마커의 세부 내용을 "영통입구 버스정류장"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("영통입구 버스정류장"); // 상단 제목을 "영통입구 버스정류장"으로 함
            Print_MonThuTime.setText("07:35"); // 월~목의 시간을 "07:35"으로 함
            Print_FriTime.setText("07:35"); // 금의 시간을 "07:35"으로 함

        } else if(Regional.equals("수원IC")){ // 사용자가 누른 버튼의 값이 "수원IC"일 경우

            LatLng location = new LatLng(37.26991188056862, 127.09718300321123); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("수원 통학버스"); // 마커의 이름을 "수원 통학버스"라 한다.
            markerOptions.snippet("수원IC 입구"); // 마커의 세부 내용을 "수원IC 입구"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("수원IC 입구"); // 상단 제목을 "수원IC 입구"으로 함
            Print_MonThuTime.setText("07:40"); // 월~목의 시간을 "07:40"으로 함
            Print_FriTime.setText("07:40"); // 금의 시간을 "07:40"으로 함

        } else if(Regional.equals("인천")){ // 사용자가 누른 버튼의 값이 "인천"일 경우

            LatLng location = new LatLng(37.49111308662967, 126.7262201249565); // location에 위도와 경도값을 저장한다.
            MarkerOptions markerOptions = new MarkerOptions(); // 마커를 사용하기 위해 객체 선언
            markerOptions.title("인천 통학버스"); // 마커의 이름을 "인천 통학버스"라 한다.
            markerOptions.snippet("구올담 치과병원 앞"); // 마커의 세부 내용을 "구올담 치과병원 앞"이라 한다.
            markerOptions.position(location); // location에 저장되어 있는 위도와 경도값에 마커를 놓겠다.
            googleMap.addMarker(markerOptions); // 구글맵에 위에서 입력한 마커값을 토대로 마커를 배치한다.

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 맵의 확대 정도를 결정함

            Guide_RegionalName.setText("구올담 치과병원 앞"); // 상단 제목을 "구올담 치과병원 앞"으로 함
            Guide_MonThu.setText("월");
            Guide_Fri.setText("화~목");
            Print_MonThuTime.setText("06:45"); // 월~목의 시간을 "06:45"으로 함
            Print_FriTime.setText("06:45"); // 금의 시간을 "06:45"으로 함

        }
    }
}
