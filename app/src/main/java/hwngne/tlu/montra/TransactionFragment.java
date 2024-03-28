package hwngne.tlu.montra;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
}