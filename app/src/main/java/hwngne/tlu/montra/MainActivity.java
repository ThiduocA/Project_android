package hwngne.tlu.montra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hwngne.tlu.montra.DAO.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private  EditText editTextPassword;

    private EditText forgotPassword;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                int userId = dbHelper.searchId(email, password);
                if(password.equals(dbHelper.searchPass(email))){

                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("userId", userId);
                    System.out.println("userId cua intent: " + userId);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", userId);
                    editor.apply();
                    System.out.println("userId cua editor: " + userId);
                    startActivity(intent);
                    finish();
                }
            }
        });
        TextView signup = findViewById(R.id.signupid);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
                finish();
            }
        });

        TextView forgot = findViewById(R.id.forgotPassword);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, forgot.class);
                startActivity(intent);
                finish();
            }
        });


    }
}