package com.example.mainproject0606;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    // Firebase와의 연결
    private FirebaseAuth firebaseAuth; // 인증
    private FirebaseDatabase database; // 데이터베이스
    private DatabaseReference databaseRef; // 데이터베이스 접근

    // RegisterActivity 에서 사용할 변수 선언
    private EditText ET_Email, ET_Password, ET_CheckPassword, ET_Phone, ET_Nickname;
    private Button Btn_Register;

    public static UserAccount account = new UserAccount(); // UserAccount 객체 선언

    public static Context context; // 다른 클래스에서의 접근을 위함
    public static String idToken; // 회원가입 하는 유저의 UID를 저장할 String 변수 선언

    public static String Email, Password, CheckPassword, Phone, Nickname; // 입력된 값들을 저장할 String 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // activity_register.xml에서 설정된 View들을 가져옴

        // activity_register.xml에서 설정된 View들을 가져와서 정의
        ET_Email = findViewById(R.id.ET_Email);
        ET_Password = findViewById(R.id.ET_Password);
        ET_CheckPassword = findViewById(R.id.ET_Checkpassword);
        ET_Phone = findViewById(R.id.ET_Phone);
        ET_Nickname = findViewById(R.id.ET_Nickname);
        Btn_Register = findViewById(R.id.Btn_Register);

        firebaseAuth = FirebaseAuth.getInstance(); // 파이어베이스에서 인증 정보를 가져온다.
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스의 정보를 가져온다.
        databaseRef = database.getReference("UserAccount"); // 데이터베이스의 "UserAccount" 참조

        Btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = ET_Email.getText().toString(); // ET_Email에 입력된 값을 가져와서 String 변수 Email에 저장
                Password = ET_Password.getText().toString(); // ET_Password에 입력된 값을 가져와서 String 변수 Password에 저장
                CheckPassword = ET_CheckPassword.getText().toString(); // ET_CheckPassword에 입력된 값을 가져와서 String 변수 CheckPassword에 저장
                Phone = ET_Phone.getText().toString(); // ET_Phone에 입력된 값을 가져와서 String 변수 Phone에 저장
                Nickname = ET_Nickname.getText().toString(); // ET_Nickname에 입력된 값을 가져와서 String 변수 Nickname에 저장

                // 만약 빈칸이 있을 시
                if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(CheckPassword) || TextUtils.isEmpty(Phone) || TextUtils.isEmpty(Nickname)){
                    Toast.makeText(RegisterActivity.this, "빈칸없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else { // 빈칸 없이 입력된 경우
                    databaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Boolean compare = false; // 불리언 compare(비교) 변수 선언 및 false 초기화
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // for문을 돌려가며 데이터베이스에 저장되어 있는 값을 가져오는 것
                                String checkPhone = snapshot.getValue(UserAccount.class).getPhone();
                                if(checkPhone.equals(Phone)){ // 받아온 전화번호 중에 사용자가 입력한 전화번호와 일치하는 것이 있을 시
                                    compare = true;
                                    break;
                                } else {
                                    compare = false;
                                }
                            }
                            if(compare){
                                return;
                            } else { // 회원가입 진행
                                // Firebase와 인증을 진행하여 똑같은 Email을 가진 사람이 있는지 비교
                                firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(Password.equals(CheckPassword)){ // 입력된 두 개의 비밀번호가 일치하면
                                            if(task.isSuccessful()) { // 회원가입 성공시
                                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser(); // 현재 인증된 유저

                                                account.setIdToken(firebaseUser.getUid()); // 유저의 고유 아이디 값을 UserAccount 클래스의 IdToken에 저장
                                                account.setEmail(Email); // 유저의 이메일 값을 UserAccount 클래스의 Email에 저장
                                                account.setPassword(Password); // 유저의 비밀번호 값을 UserAccount 클래스의 Password에 저장
                                                account.setPhone(Phone); // 유저의 전화번호 값을 UserAccount 클래스의 Phone에 저장
                                                account.setNickname(Nickname); // 유저의 닉네임 값을 UserAccount 클래스의 Nickname에 저장

                                                idToken = firebaseUser.getUid(); // 유저의 고유 아이디 값을 idToken에 저장

                                                databaseRef.child(firebaseUser.getUid()).setValue(account); // 데이터베이스에 접근해서 자식노드로 UID를 한 다음 account에 들어있는 값들을 저장한다.

                                                Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                                                // 로그인 화면으로 넘어감
                                                Intent intent = new Intent(RegisterActivity.this, CallbenMain.class);
                                                startActivity(intent);


                                                ET_Email.setText(null); // 이메일 입력 영역 초기화
                                                ET_Password.setText(null); // 비밀번호 입력 영역 초기화
                                                ET_CheckPassword.setText(null); // 비밀번호 체크 입력 영역 초기화
                                                ET_Phone.setText(null); // 전화번호 입력 영역 초기화
                                                ET_Nickname.setText(null); // 닉네임 입력 영역 초기화
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
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
}
