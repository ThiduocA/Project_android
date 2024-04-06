package hwngne.tlu.montra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hwngne.tlu.montra.Fragment.ExpenseFragment;
import hwngne.tlu.montra.Fragment.HomeFragment;
import hwngne.tlu.montra.Fragment.IncomeFragment;
import hwngne.tlu.montra.Fragment.ProfileFragment;
import hwngne.tlu.montra.Fragment.TransactionFragment;
import hwngne.tlu.montra.R;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private int userId;
    private FirebaseAuth auth;

    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        userId = getIntent().getIntExtra("userId", -1);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.framelayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.navHome){

                    loadFragment(new HomeFragment(), false);
                }else if(itemId == R.id.navTransaction){
                    loadFragment(new TransactionFragment(), false);

                }else if(itemId == R.id.navIncome){
                    loadFragment(new IncomeFragment(), false);

                }else if(itemId == R.id.navExpense){
                    loadFragment(new ExpenseFragment(), false);

                }else if(itemId == R.id.navProfile){
                    loadFragment(new ProfileFragment(), false);

                }
                return true;

            }
        });

        loadFragment(new HomeFragment(), true);
        //return true;
    }
    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        fragment.setArguments(bundle);
        if(isAppInitialized){
            fragmentTransaction.add(R.id.framelayout, fragment);
        }else{
            fragmentTransaction.replace(R.id.framelayout, fragment);
        }
        fragmentTransaction.commit();
    }

}