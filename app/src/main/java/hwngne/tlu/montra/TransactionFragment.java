package hwngne.tlu.montra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class TransactionFragment extends Fragment {

    ArrayList<Transaction_lv> mylist;
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
        String title = "Shopping";
        String description = "Buy some grocery";
        String cost = "- $120";
        String time = "12:00 AM";
        ListView listView = view.findViewById(R.id.list_view_transaction);
        mylist = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            mylist.add(new Transaction_lv(img, title, description, cost, time));
        }
        TransactionHomeArrayAdapter adapter = new TransactionHomeArrayAdapter(requireActivity(), mylist);
        listView.setAdapter(adapter);
    }
}