package hwngne.tlu.montra.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import hwngne.tlu.montra.DAO.DatabaseHelper;
import hwngne.tlu.montra.R;

public class ResetPassword extends AppCompatActivity {

    DatabaseHelper dbHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        dbHelper = new DatabaseHelper(ResetPassword.this);
        Button backtologin = findViewById(R.id.backtologin);
        TextInputEditText editNewPassword = findViewById(R.id.newpass);
        TextInputEditText editRetypePassword = findViewById(R.id.retypepass);
        ImageView img = findViewById(R.id.btn_back);
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editNewPassword.getText().toString();
                String retypePassword = editRetypePassword.getText().toString();
                System.out.println("New password: " + newPassword);
                System.out.println("Retype: " + retypePassword);
                if (newPassword.isEmpty() || retypePassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Dữ liệu không được để trống!", Toast.LENGTH_LONG).show();
                } else if (newPassword.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải lớn hơn 8 ký tự!", Toast.LENGTH_LONG).show();
                } else if (!newPassword.equals(retypePassword)) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu mới và nhập lại mật khẩu mới phải giống nhau", Toast.LENGTH_LONG).show();
                } else {
                    userId = getIntent().getIntExtra("userId", -1);
                    System.out.println("userId của trang ResetPassword: " + userId);
                    dbHelper = new DatabaseHelper(ResetPassword.this);
                    dbHelper.updatePassword(userId, newPassword);
                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }
}