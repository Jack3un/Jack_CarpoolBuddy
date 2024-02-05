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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Spinner roleSpinner;
    private FirebaseFirestore firestore;
    private LinearLayout layout;
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText inSchoolTitleField;
    private EditText gradYearField;
    private String roleSelected;
    private EditText childrenUIDsField;
    private EditText parentUIDsField;

    private Button back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        layout = findViewById(R.id.createUserLayout);
        roleSpinner = findViewById(R.id.selectedRoleSpinner);
        back = this.findViewById(R.id.back);

        setupSpinner();

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(i);

            }
        });
    }


    private void setupSpinner(){
        String[] userTypes = {"Student", "Teacher", "Alumni", "Parent"};
        // add user types to spinner
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(SignUpActivity.this,
                android.R.layout.simple_spinner_item, userTypes);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(langArrAdapter);

        //triggered whenever user selects something different
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
            childrenUIDsField.setHint("Childrens UID");
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
        nameField = new EditText(this);
        nameField.setHint("Name");
        layout.addView(nameField);
        emailField = new EditText(this);
        emailField.setHint("Email");
        layout.addView(emailField);
        passwordField = new EditText(this);
        passwordField.setHint("Password");
        layout.addView(passwordField);
    }

    public void signUp(View V){
        String nameString = nameField.getText().toString();
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        String roleString = roleSelected;
        String gradYearInt = gradYearField.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("SIGN UP", "Successfully signed up");
                    Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    if(roleSelected.equals("Alumni")) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Alumni newUser = new Alumni(roleString, nameString, emailString, passwordString, 0, 0, 0, gradYearInt);
                        firestore.collection("users").document(user.getUid()).set(newUser);
                        updateUI(user);
                    }
                    if(roleSelected.equals("Teacher")) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        String inSchoolTitleString = inSchoolTitleField.getText().toString();
                        Teacher newUser = new Teacher(roleString, nameString, emailString, passwordString, 0, 0, 0, inSchoolTitleString);
                        firestore.collection("users").document(user.getUid()).set(newUser);
                        updateUI(user);

                    }
                    if(roleSelected.equals("Parent")) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String childrenUIDsString = childrenUIDsField.getText().toString();

                        ArrayList<String> childrenUIDs = new ArrayList<>();

                        // 假设子用户ID是以逗号分隔的，分割字符串并添加到列表中
                        String[] ids = childrenUIDsString.split(",");
                        for (String id : ids) {
                            childrenUIDs.add(id.trim()); // 添加到列表中，并去除可能的空格
                        }
                        Parent newUser = new Parent(roleString, nameString, emailString, passwordString, 0, 0, 0, childrenUIDs);
                        firestore.collection("users").document(user.getUid()).set(newUser);
                        updateUI(user);

                    }
                    if(roleSelected.equals("Student")){
                        FirebaseUser user = mAuth.getCurrentUser();

                        String parentUIDsString = parentUIDsField.getText().toString();
                        ArrayList<String> parentUIDs = new ArrayList<>();

                        // 分割字符串并添加到列表中
                        String[] ids = parentUIDsString.split(",");
                        for (String id : ids) {
                            parentUIDs.add(id.trim()); // 添加到列表中，并去除可能的空格
                        }
                        Student newUser = new Student(roleString, nameString, emailString, passwordString, 0, 0, 0, gradYearInt, parentUIDs);
                        firestore.collection("users").document(user.getUid()).set(newUser);
                        updateUI(user);

                    }
                }
                else{
                    Log.d("SIGN UP", "createUserWithEmail:failure", task.getException());
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