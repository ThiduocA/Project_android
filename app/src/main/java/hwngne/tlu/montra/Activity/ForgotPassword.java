package hwngne.tlu.montra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import hwngne.tlu.montra.DAO.DatabaseHelper;
import hwngne.tlu.montra.R;

public class ForgotPassword extends AppCompatActivity {

    DatabaseHelper dbHelper;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        dbHelper = new DatabaseHelper(ForgotPassword.this);
        Button buttonforgot = findViewById(R.id.buttonforgot);
        TextInputEditText editTextEmail = findViewById(R.id.textInputEditText);
        ImageView img = findViewById(R.id.btn_back);
        mAuth = FirebaseAuth.getInstance();
        buttonforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                boolean isValidEmail = pattern.matcher(email).matches();
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Dữ liệu không được để trống!", Toast.LENGTH_LONG).show();
                } else if (!isValidEmail) {
                    Toast.makeText(getApplicationContext(), "Email không hợp lệ!", Toast.LENGTH_LONG).show();
                } else {
                    if (!TextUtils.isEmpty(email)) {
                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Link khôi phục password đã được gửi đến email của bạn!", Toast.LENGTH_LONG).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error :"+ e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) ;
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Email không tồn tại trong csdl!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}