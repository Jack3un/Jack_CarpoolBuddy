package com.jack.ridesharepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jack.ridesharepro.BaseClasses.Alumni;
import com.jack.ridesharepro.BaseClasses.Parent;
import com.jack.ridesharepro.BaseClasses.Student;
import com.jack.ridesharepro.BaseClasses.Teacher;
import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Spinner roleSpinner;
    private FirebaseFirestore firestore;
    private LinearLayout layout;
    private TextView nameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText inSchoolTitleField;
    private EditText gradYearField;
    private String roleSelected;
    private EditText childrenUIDsField;
    private EditText parentUIDsField;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        layout = findViewById(R.id.createUserLayout);
        roleSpinner = findViewById(R.id.selectedRoleSpinner);
        back = this.findViewById(R.id.back);

        setupSpinner();

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(UserProfileActivity.this, MenuActivity.class);
                startActivity(i);

            }
        });
    }

    private void setupSpinner(){
        String[] userTypes = {"Student", "Teacher", "Alumni", "Parent"};
        // add user types to spinner
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(UserProfileActivity.this,
                android.R.layout.simple_spinner_item, userTypes);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(langArrAdapter);

        // triggered whenever user selects something different
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                roleSelected = parent.getItemAtPosition(position).toString();
                addFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void addFields(){
        commonFields();
        if(roleSelected.equals("Alumni")){
            gradYearField = new EditText(this);
            gradYearField.setHint("Graduation Year");
            layout.addView(gradYearField);
        }
        if(roleSelected.equals("Teacher")){
            inSchoolTitleField = new EditText(this);
            inSchoolTitleField.setHint("School Title");
            layout.addView(inSchoolTitleField);
        }
        if(roleSelected.equals("Parent")){
            childrenUIDsField = new EditText(this);
            childrenUIDsField.setHint("Children's UID");
            layout.addView(childrenUIDsField);
        }
        if(roleSelected.equals("Student")){
            gradYearField = new EditText(this);
            gradYearField.setHint("Graduation Year");
            layout.addView(gradYearField);
            parentUIDsField = new EditText(this);
            parentUIDsField.setHint("Parent UID");
            layout.addView(parentUIDsField);
        }
    }

    public void commonFields(){
        layout.removeAllViewsInLayout();
        nameField = new TextView(this);
        nameField.setText("Uid:" + mAuth.getUid());
        layout.addView(nameField);
        emailField = new EditText(this);
        emailField.setHint("Email");
        layout.addView(emailField);
        passwordField = new EditText(this);
        passwordField.setHint("Password");
        layout.addView(passwordField);
    }

    public void OK(View V){
        Toast.makeText(UserProfileActivity.this, "Update success", Toast.LENGTH_SHORT).show();

        String nameString = nameField.getText().toString();
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        String roleString = roleSelected;
        String gradYearInt = gradYearField.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        // 获取当前用户的 userId
                        String userId = user.getUid();

                        // 创建用户对象或更新用户对象的数据
                        if(roleSelected.equals("Alumni")) {
                            Alumni newUser = new Alumni(roleString, nameString, emailString, passwordString, 0, 0, 0, gradYearInt);
                            updateUserData(userId, newUser);
                        }
                        if(roleSelected.equals("Teacher")) {
                            String inSchoolTitleString = inSchoolTitleField.getText().toString();
                            Teacher newUser = new Teacher(roleString, nameString, emailString, passwordString, 0, 0, 0, inSchoolTitleString);
                            updateUserData(userId, newUser);
                        }
                        if(roleSelected.equals("Parent")) {
                            String childrenUIDsString = childrenUIDsField.getText().toString();
                            ArrayList<String> childrenUIDs = new ArrayList<>();
                            String[] ids = childrenUIDsString.split(",");
                            for (String id : ids) {
                                childrenUIDs.add(id.trim());
                            }
                            Parent newUser = new Parent(roleString, nameString, emailString, passwordString, 0, 0, 0, childrenUIDs);
                            updateUserData(userId, newUser);
                        }
                        if(roleSelected.equals("Student")){
                            String parentUIDsString = parentUIDsField.getText().toString();
                            ArrayList<String> parentUIDs = new ArrayList<>();
                            String[] ids = parentUIDsString.split(",");
                            for (String id : ids) {
                                parentUIDs.add(id.trim());
                            }
                            Student newUser = new Student(roleString, nameString, emailString, passwordString, 0, 0, 0, gradYearInt, parentUIDs);
                            updateUserData(userId, newUser);
                        }
                    }
                }
                else{
                    Log.d("Modify", "failure", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    private void updateUserData(String userId, Object newUser) {
        firestore.collection("users").document(userId)
                .set(newUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateUI(mAuth.getCurrentUser());
                        } else {
                            Toast.makeText(UserProfileActivity.this, "failure", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
