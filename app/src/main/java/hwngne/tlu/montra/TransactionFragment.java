package hwngne.tlu.montra;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hwngne.tlu.montra.DAO.Connect;


public class TransactionFragment extends Fragment {

    ArrayList<Transaction_lv> mylist;
    Connect connect;
    public TransactionFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int img = R.drawable.cart;


        connect = new Connect(requireContext(), "montra.db", null, 1);
        ListView listView = view.findViewById(R.id.list_view_transaction);
        mylist = new ArrayList<>();
        TransactionHomeArrayAdapter adapter = new TransactionHomeArrayAdapter(requireActivity(), R.layout.layout_transaction_home, mylist);
        listView.setAdapter(adapter);
        actionGetData();

        TextView btn_see = view.findViewById(R.id.btn_see);
        btn_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new FinancialFragment(), false);
            }
        });
    }

    public void actionGetData(){
        Cursor data = connect.getData("SELECT category, description, cash, time " +
                "FROM (" +
                "    SELECT category, description, cash, time FROM income " +
                "    UNION ALL " +
                "    SELECT category, description, cash, time FROM expense " +
                ") AS combined " +
                "ORDER BY time DESC;");
        mylist.clear();
        while (data.moveToNext()){
            String category = data.getString(0);
            String description = data.getString(1);
            int cash = data.getInt(2);
            String time = data.getString(3);
            mylist.add(new Transaction_lv(category, description, cash, time));
        }

    }
    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isAppInitialized){
            fragmentTransaction.add(R.id.framelayout, fragment);
        }else{
            fragmentTransaction.replace(R.id.framelayout, fragment);
        }
        fragmentTransaction.commit();
    }
}