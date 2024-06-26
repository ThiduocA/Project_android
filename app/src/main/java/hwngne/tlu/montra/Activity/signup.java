package hwngne.tlu.montra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import hwngne.tlu.montra.DAO.DatabaseHelper;
import hwngne.tlu.montra.R;

public class signup extends AppCompatActivity {
    //hehe
    private CheckBox checkBox;
    private Button button;
    private FirebaseAuth mAuth;
    private boolean isCheckboxChecked = false;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        checkBox = findViewById(R.id.checkBox);
        button = findViewById(R.id.button);
        TextInputEditText inputName = findViewById(R.id.inputname);
        TextInputEditText inputEmail = findViewById(R.id.inputemail);
        TextInputEditText inputPass = findViewById(R.id.inputpassword);
        ImageView img = findViewById(R.id.btn_back);
        TextView tvlogin = findViewById(R.id.tvLogin);
        SpannableString content = new SpannableString("Login");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvlogin.setText(content);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckboxChecked = isChecked;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPass.getText().toString();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                boolean isValidEmail = pattern.matcher(email).matches();
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
                if(isCheckboxChecked == true){
                    if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                        Toast.makeText(signup.this, "Dữ liệu không được để trống!", Toast.LENGTH_LONG).show();
                    }
                    else if(password.length() < 8){
                        Toast.makeText(signup.this, "Mật khẩu phải từ 8 ký tự trở lên!", Toast.LENGTH_LONG).show();
                    }
                    else if(!isValidEmail){
                        Toast.makeText(signup.this, "Email không đúng định dạng!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                        try{
                            dbHelper.insertUser(name, email, password);
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(signup.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(signup.this, "Tài khoản đã tồn tại.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } catch (Exception e){
                            System.out.println("Lỗi trang signup: " + e.getMessage());
                            Toast.makeText(signup.this, "Đăng ký lỗi", Toast.LENGTH_LONG).show();
                        }

                    }
                }else{
                    Toast.makeText(signup.this, "Phải bấm checkbox trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}