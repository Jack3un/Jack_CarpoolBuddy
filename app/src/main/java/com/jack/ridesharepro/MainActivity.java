package com.jack.ridesharepro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button signIn, signUp, googleAuth;
    private EditText email, name, password;
    private Spinner userType;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
        setupGoogleSignIn();
        setupButtonListeners();
    }

    private void initializeUI() {
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);
        googleAuth = findViewById(R.id.btnGoogleLogin);
        email = findViewById(R.id.Email);
        name = findViewById(R.id.Name);
        password = findViewById(R.id.password);
        userType = findViewById(R.id.userType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.userTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        db.setFirestoreSettings(settings);

    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setupButtonListeners() {
        googleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignIn();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignUp();
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void performSignIn() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            showToast("Please fill in all required fields.");
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("Sign In", "Successfully signed in");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w("Sign in", "signInWithEmail:failure", task.getException());
                        showToast("Sign in failed.");
                        updateUI(null);
                    }
                });
    }

    private void performSignUp() {
//        String userName = name.getText().toString();
//        String userEmail = email.getText().toString();
//        String userPassword = password.getText().toString();
//        String userTypeData = userType.getSelectedItem().toString();
//
//        if (userEmail.isEmpty() || userName.isEmpty() || userPassword.isEmpty() || userTypeData.isEmpty()) {
//            showToast("Please fill in all required fields.");
//            return;
//        }
//
//        if (!userEmail.contains("cis.edu.hk")) {
//            showToast("Email does not belong to our school organization.");
//            return;
//        }
//
//        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, task -> {
//            Exception exception = task.getException();
//            Log.w("Sign up", "createUserWithEmail:failure", exception);
//                    if (task.isSuccessful()) {
//                        Log.d("SIGN UP", "createUserWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        User newUser = new User(userTypeData, userName, userEmail, userPassword, 0, 0, 0);
//                        db.collection("users").document(user.getUid()).set(newUser);
//                        updateUI(user);
//                    } else {
//                        Log.w("Sign up", "createUserWithEmail:failure", task.getException());
//                        showToast("Authentication failed.");
//                        updateUI(null);
//                    }
//                }
//                );
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                showToast("Google sign-in failed: " + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("Google Sign In", "signInWithCredential:success");
                        showToast("Google Sign-In successful!"); // 显示登录成功的提示

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);

                        // 在这里写入用户信息到 Firestore
                        if (user != null) {
                            String userId = user.getUid(); // 获取用户 UID
                            String userEmail = user.getEmail(); // 获取用户 Email
                            String userName = user.getDisplayName(); // 获取用户姓名

                            // 创建一个包含用户信息的 Map
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("uid", userId);
                            userInfo.put("email", userEmail);
                            userInfo.put("name", userName); // 添加用户姓名

                            // 将用户信息写入 Firestore
                            db.collection("users").document(userId).set(userInfo)
                                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "User info successfully written!"))
                                    .addOnFailureListener(e -> Log.w("Firestore", "Error writing user info", e));
                        }
                    } else {
                        Log.w("Google Sign In", "signInWithCredential:failure", task.getException());
                        showToast("Authentication failed."); // 显示登录失败的提示
                        updateUI(null);
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Redirect to next activity or update your UI here
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }
}
