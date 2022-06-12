package com.example.mainproject0606;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostList extends AppCompatActivity {

    // RecyclerView를 사용하기 위한 변수 선언
    private RecyclerView PostrecyclerView;
    private RecyclerView.Adapter Postadapter;
    private ArrayList<Post> PostarrayList;

    // Firebase와의 연결을 위한 변수 선언
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    // Drawer 화면을 위한 변수 선언
    private DrawerLayout drawerLayout;
    private View drawerView;
    private TextView Read_Email, Read_Nickname, Read_Phone;
    private Button Btn_ChangePassword, Btn_ChangeNickname, Btn_ChangePhone, Btn_Logout;
    public static String Email, Password, Nickname, Phone, UID, InputInfo, CurrentPassword, InputPassword;

    // PostList Activity에서 사용하기 위한 변수 선언
    private ImageView Open_Menubar, Make_Post;

    // 다른 클래스에서의 접근을 위함
    public static Context context;

    // 팝업 화면
    Dialog ChangeNicknamePhone, ChangePassword;

    // 팝업 화면에서 사용할 변수 선언
    private TextView Guide_ChangeInfo, TV_Check, TV_PasswordCheck;
    private EditText ET_ChangeInfo, ET_CurrentPassword, ET_ChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list); // activity_post_list.xml에서 설정된 View들을 가져옴

        // activity_post_list.xml에서 설정된 View들을 가져와서 정의
        Open_Menubar = findViewById(R.id.Open_Menubar); // 메뉴바 이미지
        Make_Post = findViewById(R.id.Make_Post); // 게시글 생성 이미지

        // Drawer 화면에서 사용하기 위한 정의
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);

        drawerLayout.setDrawerListener(listener);

        Read_Email = findViewById(R.id.Read_Email); // 이메일 출력란
        Read_Nickname = findViewById(R.id.Read_Nickname); // 닉네임 출력란
        Read_Phone = findViewById(R.id.Read_Phone); // 전화번호 출력란

        Btn_ChangePassword = findViewById(R.id.Btn_ChangePassword); // 비밀번호 변경
        Btn_ChangeNickname = findViewById(R.id.Btn_ChangeNickname); // 닉네임 변경
        Btn_ChangePhone = findViewById(R.id.Btn_ChangePhone); // 전화번호 변경
        Btn_Logout = findViewById(R.id.Btn_Logout); // 로그아웃

        // RecyclerView를 사용하기 위한 정의
        PostrecyclerView = findViewById(R.id.PostrecyclerView);
        PostrecyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        LinearLayoutManager PostlayoutManager = new LinearLayoutManager(this);
        PostlayoutManager.setReverseLayout(true); // 역순으로 출력하기 위함
        PostlayoutManager.setStackFromEnd(true); // 역순으로 출력하기 위함
        PostrecyclerView.setLayoutManager(PostlayoutManager);
        PostarrayList = new ArrayList<>();

        // Firebase와의 연결
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();

        // 팝업 정의
        ChangeNicknamePhone = new Dialog(PostList.this); // 팝업 화면을 현재 액티비티(MainActivity)에서 띄우겠다.
        ChangeNicknamePhone.requestWindowFeature(Window.FEATURE_NO_TITLE); // 팝업 화면의 제목을 지워준다.
        ChangeNicknamePhone.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 팝업 화면의 배경을 투명하게 만들어 준다.
        ChangeNicknamePhone.setContentView(R.layout.activity_change_user_info_popup); // 팝업 화면의 정보는 activity_regional_popup.xml에서 가져온다.

        ChangePassword = new Dialog(PostList.this); // 팝업 화면을 현재 액티비티(MainActivity)에서 띄우겠다.
        ChangePassword.requestWindowFeature(Window.FEATURE_NO_TITLE); // 팝업 화면의 제목을 지워준다.
        ChangePassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 팝업 화면의 배경을 투명하게 만들어 준다.
        ChangePassword.setContentView(R.layout.activity_change_user_password_popup); // 팝업 화면의 정보는 activity_regional_popup.xml에서 가져온다.

        // 팝업 화면에서 사용할 변수 정의
        Guide_ChangeInfo = ChangeNicknamePhone.findViewById(R.id.Guide_ChangeInfo);
        ET_ChangeInfo = ChangeNicknamePhone.findViewById(R.id.ET_ChangeInfo);
        TV_Check = ChangeNicknamePhone.findViewById(R.id.TV_Check);

        ET_CurrentPassword = ChangePassword.findViewById(R.id.ET_CurrentInfo);
        ET_ChangePassword = ChangePassword.findViewById(R.id.ET_ChangePassword);
        TV_PasswordCheck = ChangePassword.findViewById(R.id.TV_PasswordCheck);

        // 문자열에 값 저장 , CallbenMain 클래스에 접근해서 값을 가져온다.
        Email = ((CallbenMain)CallbenMain.context).userEmail;
        Password = ((CallbenMain) CallbenMain.context).userPassword;
        Nickname = ((CallbenMain)CallbenMain.context).userNickname;
        Phone = ((CallbenMain)CallbenMain.context).userPhone;
        UID = ((CallbenMain)CallbenMain.context).userIdtoken;

        Read_Email.setText(((CallbenMain)CallbenMain.context).googleEmail);
        Read_Nickname.setText(((CallbenMain)CallbenMain.context).googleNickname);
        Read_Phone.setText("미등록 상태");

        // 메뉴 이미지 클릭 시
        Open_Menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // drawer이 열린다.
                drawerLayout.openDrawer(drawerView);
            }
        });

        // 게시글 작성 이미지 클릭 시
        Make_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 게시글 작성 화면으로 넘어감
                Intent intent = new Intent(PostList.this, PostUpload.class);
                startActivity(intent);
            }
        });

        // 좌측에서 화면을 끌어당기면 drawer화면이 나옴
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{

                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                return true;
            }
        });

        // Drawer 화면에 로그인한 사용자의 정보를 출력해주는 코드
        databaseRef.child("UserAccount").addValueEventListener(new ValueEventListener() { // FirebaseDatabase의 UserAccount라는 부모노드에 접근하여 참조
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean compare = true; // 불리언 compare(비교) 변수 선언 및 true 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                    if((snapshot.getValue(UserAccount.class).getEmail()).equals(Email)){ // 만약 가져온 Email 중에 사용자의 Email과 일치하는 값이 있으면
                        Nickname = snapshot.getValue(UserAccount.class).getNickname();
                        Phone = snapshot.getValue(UserAccount.class).getPhone();
                        compare = true;
                        break;
                    } else {
                        compare = false;
                    }
                }
                if(compare){ // compare이 true 이면
                    Read_Email.setText(Email);
                    Read_Nickname.setText(Nickname);
                    Read_Phone.setText(Phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // RecyclerView에 게시글 목록을 보여줄 코드
        databaseRef.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PostarrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // for문을 돌려가며 데이터베이스에서 상위 키 값에 대한 자식들 값(Value)을 가져옴
                    Post post = snapshot.getValue(Post.class);
                    PostarrayList.add(post);
                }
                Postadapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Postadapter = new PostAdapter(PostarrayList, this);
        PostrecyclerView.setAdapter(Postadapter);

        // 비밀번호 변경 버튼 클릭 시
        Btn_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePassword(); // showChangePassword 메소드 호출
            }
        });

        // 닉네임 변경 버튼 클릭 시
        Btn_ChangeNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeNickname(); // showChangeNickname 메소드 호출
            }
        });

        // 전화번호 변경 버튼 클릭 시
        Btn_ChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePhone(); // showChangePhone 메소드 호출
            }
        });

        // 로그아웃 버튼 클릭 시
        Btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 화면으로 넘어감
                Intent intent = new Intent(PostList.this, CallbenMain.class);
                startActivity(intent);
            }
        });
    }

    // DrawerLayout에서 상태값을 받아올 때 사용 (이 코드에서는 사용하지 않음)
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    // 비밀번호 변경 클릭 시 호출될 메소드
    public void showChangePassword(){
        ChangePassword.show(); // 팝업 화면을 보여줌

        // 확인 버튼 클릭 시
        TV_PasswordCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPassword = ET_CurrentPassword.getText().toString(); // String 변수 CurrentPassword에 사용자가 입력한 정보 저장
                InputPassword = ET_ChangePassword.getText().toString(); // String 변수 InputPassword에 사용자가 입력한 정보 저장
                if(TextUtils.isEmpty(CurrentPassword) || TextUtils.isEmpty(InputPassword)){ // 만약 빈칸이 있을 시
                    Toast.makeText(PostList.this, "빈칸없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(CurrentPassword.equals(Password)){ // 현재 비밀번호를 입력한 것과 현재 로그인한 사용자의 비밀번호가 일치하면
                    firebaseAuth.getCurrentUser().updatePassword(InputPassword).addOnSuccessListener(new OnSuccessListener<Void>() { // 비밀번호 변경 성공
                        @Override
                        public void onSuccess(Void unused) {
                            databaseRef.child("UserAccount").child(UID).child("password").setValue(InputPassword); // RealtimeDatabase에 접근해서 사용자의 password 값을 바꾼다.
                            ET_CurrentPassword.setText("");
                            ET_ChangePassword.setText("");
                            ChangePassword.cancel(); // 팝업 닫기
                            Toast.makeText(PostList.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() { // 비밀번호 변경 실패
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Toast.makeText(PostList.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 닉네임 변경 클릭 시 호출될 메소드
    public void showChangeNickname(){
        ChangeNicknamePhone.show(); // 팝업 화면을 보여줌

        Guide_ChangeInfo.setText("닉네임 변경");
        ET_ChangeInfo.setHint("변경할 닉네임을 입력해주세요.");
        ET_ChangeInfo.setInputType(InputType.TYPE_CLASS_TEXT);

        // 확인 클릭 시
        TV_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputInfo = ET_ChangeInfo.getText().toString(); // String 변수 InputInfo에 사용자가 입력한 정보 저장
                if(TextUtils.isEmpty(InputInfo)){ // 만약 빈칸이 있을 경우
                    Toast.makeText(PostList.this, "빈칸 없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    databaseRef.child("UserAccount").child(UID).child("nickname").setValue(InputInfo); // RealtimeDatabase에 접근해서 사용자의 nickname 값을 바꾼다.
                    ET_ChangeInfo.setText("");
                    ChangeNicknamePhone.cancel(); // 팝업 닫기
                    Toast.makeText(PostList.this, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 전화번호 변경 클릭 시 호출될 메소드
    public void showChangePhone(){
        ChangeNicknamePhone.show(); // 팝업 화면을 보여줌

        Guide_ChangeInfo.setText("전화변경 변경");
        ET_ChangeInfo.setHint("변경할 전화번호를 입력해주세요.");
        ET_ChangeInfo.setInputType(InputType.TYPE_CLASS_PHONE); // 입력 속성을 전화번호로 한다

        // 확인 클릭 시
        TV_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputInfo = ET_ChangeInfo.getText().toString(); // String 변수 InputInfo에 사용자가 입력한 정보 저장
                if(TextUtils.isEmpty(InputInfo)){ // 만약 빈칸이 있을 경우
                    Toast.makeText(PostList.this, "빈칸 없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    databaseRef.child("UserAccount").child(UID).child("phone").setValue(InputInfo); // RealtimeDatabase에 접근해서 사용자의 phone 값을 바꾼다.
                    ET_ChangeInfo.setText("");
                    ChangeNicknamePhone.cancel(); // 팝업 닫기
                    Toast.makeText(PostList.this, "전화번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
