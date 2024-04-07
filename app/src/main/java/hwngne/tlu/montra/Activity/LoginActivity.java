package hwngne.tlu.montra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import hwngne.tlu.montra.DAO.DatabaseHelper;
import hwngne.tlu.montra.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private EditText forgotPassword;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);


        // them gach chan cho chu SignUp
        TextView tvsignup = findViewById(R.id.signupid);
        SpannableString content = new SpannableString("Sign Up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvsignup.setText(content);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                boolean isValidEmail = pattern.matcher(email).matches();
                int userId = dbHelper.searchId(email, password);
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Dữ liệu không được để trống!", Toast.LENGTH_LONG).show();
                } else if (!isValidEmail) {
                    Toast.makeText(getApplicationContext(), "Email không đúng định dạng!", Toast.LENGTH_LONG).show();
                } else {

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        user = mAuth.getCurrentUser();
                                        dbHelper.updatePasswordwithEmail(email,password);
                                        int userId = dbHelper.searchId(email, password);
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent.putExtra("userId", userId);
                                        System.out.println("userId cua intent: " + userId);
                                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putInt("userId", userId);
                                        editor.apply();
                                        System.out.println("userId cua editor: " + userId);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        if (password.equals(dbHelper.searchPass(email))) {
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            intent.putExtra("userId", userId);
                                            System.out.println("userId cua intent: " + userId);
                                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putInt("userId", userId);
                                            editor.apply();
                                            System.out.println("userId cua editor: " + userId);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Mật khẩu không đúng, vui lòng nhập lại!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });
        TextView signup = findViewById(R.id.signupid);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, signup.class);
                startActivity(intent);
                finish();
            }
        });

        TextView forgot = findViewById(R.id.forgotPassword);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });


    }
}