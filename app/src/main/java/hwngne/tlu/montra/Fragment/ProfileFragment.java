package hwngne.tlu.montra.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import hwngne.tlu.montra.DAO.DatabaseHelper;
import hwngne.tlu.montra.Activity.LoginActivity;
import hwngne.tlu.montra.R;


public class ProfileFragment extends Fragment {
    public String searchName(int id) {

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());

        String name = dbHelper.searchName(id);

        return name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int userId = bundle.getInt("userId", -1);

            String userName = searchName(userId);
            TextView userNameTextView = view.findViewById(R.id.user_name);
            userNameTextView.setText(userName);
        }
        TextView logoutTextView = view.findViewById(R.id.logout);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hộp thoại xác nhận trước khi đăng xuất
                showLogoutConfirmationDialog();
            }
        });

        return view;
    }

    // Hiển thị hộp thoại xác nhận trước khi đăng xuất
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nếu người dùng chọn "Có", thực hiện đăng xuất
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nếu người dùng chọn "Không", đóng hộp thoại
                        dialog.dismiss();
                    }
                });
        // Hiển thị hộp thoại
        builder.create().show();
    }



}
