package hwngne.tlu.montra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    private CheckBox checkBox;
    private Button button;
    private boolean isCheckboxChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        checkBox = findViewById(R.id.checkBox);
        button = findViewById(R.id.button);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckboxChecked = isChecked;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheckboxChecked == true){
                    Toast.makeText(signup.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, MainActivity.class);
                }else{
                    Toast.makeText(signup.this, "Phải bấm checkbox trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}