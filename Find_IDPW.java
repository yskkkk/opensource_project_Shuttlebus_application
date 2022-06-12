package com.example.mainproject0606;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Find_IDPW extends AppCompatActivity {

    // Find_IDPW Activity에서 사용할 변수 선언
    private EditText ET_Phone, ET_Email, ET_Phonenum;
    private Button Btn_FindEmail, Btn_FindPassword;

    // 팝업 화면에서 사용할 변수 선언
    private TextView Guide_FindInfo, Read_FindInfo, TV_Check;

    // 입력된 값과 읽어올 값들을 저장할 String 변수 선언
    public static String Email, Phone, Phonenum, Read_Email, Read_Password;

    // Firebase와의 연결
    FirebaseDatabase database; // 데이터베이스
    DatabaseReference databaseRef; // 데이터베이스 참조

    // 팝업 화면
    Dialog FindInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_idpw); // activity_find_idpw.xml에서 설정된 View들을 가져옴

        // activity_find_idpw.xml에서 설정된 View들을 가져와서 정의
        ET_Phone = findViewById(R.id.ET_Phone);
        ET_Email = findViewById(R.id.ET_Email);
        ET_Phonenum = findViewById(R.id.ET_Phonenum);
        Btn_FindEmail = findViewById(R.id.Btn_FindEmail);
        Btn_FindPassword = findViewById(R.id.Btn_FindPassword);

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 정보를 가져온다.
        databaseRef = database.getReference("UserAccount"); // 데이터베이스 "UserAccount" 참조

        // 팝업 화면 정의
        FindInfo = new Dialog(Find_IDPW.this); // 팝업 화면을 현재 액티비티(Find_IDPW)에서 띄우겠다.
        FindInfo.requestWindowFeature(Window.FEATURE_NO_TITLE); // 팝업 화면의 제목을 지워준다.
        FindInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 팝업 화면의 배경을 투명하게 만들어 준다.
        FindInfo.setContentView(R.layout.activity_find_info_popup); // 팝업 화면의 정보는 activity_find_info_popup.xml에서 가져온다.

        // 팝업 화면에서 사용할 변수 정의
        Guide_FindInfo = FindInfo.findViewById(R.id.Guide_FindInfo);
        Read_FindInfo = FindInfo.findViewById(R.id.Read_FindInfo);
        TV_Check = FindInfo.findViewById(R.id.TV_Check);

        // 이메일 찾기(Btn_FindEmail) 클릭 시
        Btn_FindEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone = ET_Phone.getText().toString(); // ET_Phone의 값을 읽어와서 String 변수 Phone에 저장
                if (TextUtils.isEmpty(Phone)) { // ET_Phone이 비어있으면
                    Toast.makeText(Find_IDPW.this, "빈칸없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // firebase database에서 정보를 가져오기 위함
                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                            Boolean compare = true; // 불리언 compare(비교) 변수 선언 및 true 초기화
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                                if ((snapshot.getValue(UserAccount.class).getPhone()).equals(Phone)) { // 만약 읽어온 Phone의 값과 입력한 Phone의 값이 같으면
                                    Read_Email = snapshot.getValue(UserAccount.class).getEmail(); // Read_Email 변수에 불러온 이메일 값 저장
                                    compare = true;
                                    break;
                                } else {
                                    compare = false;
                                }
                            }
                            if (compare) { // true이면
                                showFindEmail(); // showFindEmail 메소드 호출
                            } else { // true가 아니면(즉, false)
                                Toast.makeText(Find_IDPW.this, "등록되지 않은 정보입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        // 비밀번호 찾기(Btn_FindPassword) 클릭 시
        Btn_FindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = ET_Email.getText().toString(); // ET_Email의 값을 읽어와서 String 변수 Email에 저장
                Phonenum = ET_Phonenum.getText().toString(); // ET_Phonenum의 값을 읽어와서 String 변수 Phonenum에 저장
                if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Phonenum)) { // ET_Email 또는 ET_Phonenum이 비어 있으면
                    Toast.makeText(Find_IDPW.this, "빈칸 없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // firebase database에서 정보를 가져오기 위함
                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                            Boolean compare = true; // 불리언 compare(비교) 변수 선언 및 true 초기화
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                                // 만약 읽어온 이메일, 전화번호 값과 입력한 이메일, 전화번호 값이 같으면
                                if ((snapshot.getValue(UserAccount.class).getEmail()).equals(Email) && (snapshot.getValue(UserAccount.class).getPhone()).equals(Phonenum)) {
                                    Read_Password = snapshot.getValue(UserAccount.class).getPassword(); // Read_Password 변수에 불러온 비밀번호 값 저장
                                    compare = true;
                                    break;
                                } else {
                                    compare = false;
                                }
                            }
                            if (compare) { // true이면
                                showFindPassword(); // showFindPassword 메소드 호출
                            } else { // true가 아니면(즉, false)
                                Toast.makeText(Find_IDPW.this, "등록되지 않은 정보입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    // 이메일 찾기 버튼을 눌렀을 때 사용될 메소드
    public void showFindEmail() {
        FindInfo.show(); // 팝업 화면을 보여줌

        Guide_FindInfo.setText("찾으시는 이메일"); // 상단에 사용자에게 출력될 Guide_FindInfo(텍스트 출력 영역)을 "찾으시는 이메일"로 한다.
        Read_FindInfo.setText(Read_Email); // Read_FindInfo(텍스트 출력 영역)을 Firebase에서 불러온 Email 값을 저장한 Read_Email로 한다.

        // 확인 버튼 클릭 시
        TV_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindInfo.cancel(); // 팝업 닫기
                ET_Phone.setText(null); // 전화번호 입력 영역 초기화

                // 로그인(CallbenMain) 화면으로 넘어감
                Intent intent = new Intent(Find_IDPW.this, CallbenMain.class);
                startActivity(intent);
            }
        });
    }

    // 비밀번호 찾기 버튼을 눌렀을 때 사용될 메소드드
    public void showFindPassword() {
        FindInfo.show(); // 팝업 화면을 보여줌

        Guide_FindInfo.setText("찾으시는 비밀번호"); // 상단에 사용자에게 출력될 Guide_FindInfo(텍스트 출력 영역)을 "찾으시는 비밀번호"로 한다.
        Read_FindInfo.setText(Read_Password); // Read_FindInfo(텍스트 출력 영역)을 Firebase에서 불러온 Password 값을 저장한 Read_Password로 한다.

        // 확인 버튼 클릭 시
        TV_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindInfo.cancel(); // 팝업 닫기
                ET_Email.setText(null); // 이메일 입력 영역 초기화
                ET_Phonenum.setText(null); // 전화번호 입력 영역 초기화

                // 로그인(CallbenMain) 화면으로 넘어감
                Intent intent = new Intent(Find_IDPW.this, CallbenMain.class);
                startActivity(intent);
            }
        });
    }
}
