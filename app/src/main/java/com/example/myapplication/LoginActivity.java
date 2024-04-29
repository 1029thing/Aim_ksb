    package com.example.myapplication;

    import android.os.Bundle;

    import com.example.myapplication.databinding.LoginBinding;
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.auth.api.signin.GoogleSignInResult;
    import com.google.android.gms.common.SignInButton;
    import com.google.android.gms.common.api.ApiException;
    import com.google.android.gms.tasks.Task;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import android.content.Intent;
    import androidx.activity.result.ActivityResultLauncher;
    import androidx.activity.result.contract.ActivityResultContracts;
    import androidx.activity.result.ActivityResult;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;
    import androidx.navigation.ui.AppBarConfiguration;
    import androidx.navigation.ui.NavigationUI;
    import com.example.myapplication.databinding.ActivityMainBinding;
    import android.app.Activity;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.Toast;

    import com.google.android.gms.auth.api.signin.GoogleSignIn;
    import com.google.android.gms.auth.api.signin.GoogleSignInClient;
    import com.google.android.gms.common.api.Scope;
    import com.kakao.sdk.common.KakaoSdk;
    import com.kakao.sdk.user.UserApiClient;
    import com.kakao.sdk.user.model.User;

    public class LoginActivity extends AppCompatActivity {

        private LoginBinding binding;
        private GoogleSignInClient mGoogleSignInClient;
        private static final int RC_SIGN_IN=1;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = LoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            KakaoSdk.init(this, "b46d430bee1f7138ba963a99ab135dad");

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestProfile()
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
            ImageButton kakaoSignInButton = binding.kakaoSignInButton;
            SignInButton signInButton = binding.signInButton;

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            });
            kakaoSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, (token, error) -> {
                        if (error != null) {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        } else {
                            updateKakaoUI();
                        }
                        return null;
                    });
                }
            });

        }
        private void updateKakaoUI() {
            UserApiClient.getInstance().me((user, error) -> {
                if (error != null) {
                // 사용자 정보 요청 실패
                } else {
                // 사용자 정보 요청 성공
                    if (user != null) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("email", user.getKakaoAccount().getEmail());
                        intent.putExtra("name", user.getKakaoAccount().getProfile().getNickname());
                        startActivity(intent);
                    }
                }
                return null;
            });
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == RC_SIGN_IN){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }

        private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                updateUI(account);
            }catch (ApiException e){

            }

        }

        private void updateUI(GoogleSignInAccount account){
            if(account != null){
                String email = account.getEmail();
                String name = account.getDisplayName();

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        }

    }







