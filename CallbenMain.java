package com.example.mainproject0606;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproject0606.Find_IDPW;
import com.example.mainproject0606.PostList;
import com.example.mainproject0606.RegisterActivity;
import com.example.mainproject0606.UserAccount;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CallbenMain extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {

    // CallbenMain Activity에서 사용할 변수 선언
    private EditText ET_Email, ET_Password;
    private Button Btn_Register, Btn_Login;
    private TextView TL_idpw;

    public static Context context; // 다른 클래스에서의 접근을 위함

    // Firebase와의 연결
    private FirebaseAuth firebaseAuth; // 인증
    private FirebaseDatabase database; // 데이터베이스
    private DatabaseReference databaseRef; // 데이터베이스 접근

    private String Email, Password; // 입력된 텍스트 값을 저장할 String 변수 선언
    public static String userEmail, userPassword, userNickname, userPhone, userIdtoken, googleEmail, googleNickname; // 로그인 성공시 유저의 정보를 담아둘 String 변수 선언

    // 구글 로그인 API를 사용하기 위함
    private SignInButton Btn_GoogleLogin;
    private GoogleApiClient googleApiClient; // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callben_main); // activity_callben_main.xml에서 설정된 View들을 가져옴

        // activity_callben_main.xml에서 설정된 View들을 가져와서 정의
        ET_Email = findViewById(R.id.ET_Email);
        ET_Password = findViewById(R.id.ET_Password);
        Btn_GoogleLogin = findViewById(R.id.Btn_GoogleLogin);
        Btn_Register = findViewById(R.id.Btn_Register);
        Btn_Login = findViewById(R.id.Btn_Login);
        TL_idpw = findViewById(R.id.TL_idpw);

        firebaseAuth = FirebaseAuth.getInstance(); // 파이어베이스에서 인증 정보를 가져온다.
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스의 정보를 가져온다.
        databaseRef = database.getReference("UserAccount"); // 데이터베이스의 "UserAccount" 참조


        // 구글 로그인 API를 위한 코드 구현
        GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN);
        builder.requestIdToken(getString(R.string.default_web_client_id));
        builder.requestEmail();
        GoogleSignInOptions googleSignInOptions = builder
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        // 구글 로그인 버튼 클릭 시
        Btn_GoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient); // 구글 API 클라이언트를 가지고 와라
                startActivityForResult(intent, REQ_SIGN_GOOGLE); // 로그인 인증을 하는 절차의 화면으로 넘어갔다가 다시 돌아올 것이다.
            }
        });


        // 이메일/비밀번호 찾기(TL_idpw) 클릭 시
        TL_idpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이메일/비밀번호 찾기 화면으로 넘어감
                Intent intent = new Intent(CallbenMain.this, Find_IDPW.class);
                startActivity(intent);
            }
        });

        // 회원가입 버튼 클릭 시
        Btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 넘어감
                Intent intent = new Intent(CallbenMain.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼 클릭 시
        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email = ET_Email.getText().toString(); // ET_Email에 입력된 값을 가져와서 String 변수 Email에 저장
                Password = ET_Password.getText().toString(); // ET_Password에 입력된 값을 가져와서 String 변수 Password에 저장

                if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){ // Email 또는 Password가 빈 값이면
                    Toast.makeText(CallbenMain.this, "빈칸없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 이메일과 비밀번호를 Firebase와 비교하여 로그인 처리
                firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(CallbenMain.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // firebase database에서 정보를 가져오기 위함
                            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                                        if((snapshot.getValue(UserAccount.class).getEmail()).equals(Email)){ // 만약 가져온 Email들 중에 로그인할 때 입력한 Email과 같은 값이 있으면
                                            userEmail = snapshot.getValue(UserAccount.class).getEmail();
                                            userPassword = snapshot.getValue(UserAccount.class).getPassword();
                                            userNickname = snapshot.getValue(UserAccount.class).getNickname();
                                            userPhone = snapshot.getValue(UserAccount.class).getPhone();
                                            userIdtoken = snapshot.getValue(UserAccount.class).getIdToken();

                                            // 게시글 목록을 보여주는 화면으로 넘어감
                                            Intent intent = new Intent(CallbenMain.this, PostList.class);
                                            startActivity(intent);

                                            ET_Email.setText(null); // 이메일 입력 영역 초기화
                                            ET_Password.setText(null); // 비밀번호 입력 영역 초기화

                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(CallbenMain.this, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    // 구글 로그인 인증을 요청했을 때 결과 값을 되돌려 받는 곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE){ // 되돌려준 코드가 REQ_SIGN_GOOGLE과 값이 같은지
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){ // 결과값이 성공이면
                GoogleSignInAccount account = result.getSignInAccount(); // accout 라는 데이터는 구글 로그인 정보를 담고있다. (닉네임, 이메일 주소 등)
                resultLogin(account);
            }
        }
    }

    // 로그인 결과값 출력 수행하라는 메소드
    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null); // 현재 ID에 대한 token값을 가지고 와라
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ // 로그인이 성공했으면
                            Toast.makeText(CallbenMain.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), PostList.class);
                            googleEmail = account.getEmail();
                            googleNickname = account.getDisplayName();
                            startActivity(intent);
                        } else { // 로그인 실패 시
                            Toast.makeText(CallbenMain.this, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
