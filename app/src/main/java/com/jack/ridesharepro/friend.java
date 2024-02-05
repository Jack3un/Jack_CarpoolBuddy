package com.jack.ridesharepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class friend extends AppCompatActivity {

    private Button search;
    private EditText UID;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        search = findViewById(R.id.search);
        UID = findViewById(R.id.friendID);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        back = findViewById(R.id.back2);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 获取用户输入的名称
                String friendName = UID.getText().toString();
                if (!friendName.isEmpty()) {
                    Intent i = new Intent(friend.this, displayFriend.class);
                    i.putExtra("cleanName", friendName);
                    startActivity(i);
                } else {
                    // 提示用户输入名称
                    Toast.makeText(friend.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(friend.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
