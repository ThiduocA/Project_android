package hwngne.tlu.montra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Homelv extends Fragment {

    ArrayList<Transaction_lv> mylist;
    ListView listView;
    TransactionHomeArrayAdapter transactionHomeArrayAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_transaction_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] img = {R.drawable.cart, R.drawable.cart, R.drawable.cart};
        String[] title = {"Shopping", "Shopping", "Shopping"};
        String[] description = {"Buy some gracevy", "Buy some gracevy", "Buy some gracevy"};
        String[] cost = {"- $120", "- $120", "- $120"};
        String[] time = {"12:00 AM", "12:00 AM", "12:00 AM"};
        ListView listView = view.findViewById(R.id.lv);
        mylist = new ArrayList<>();
        for(int i = 0; i < title.length; i++){
            mylist.add(new Transaction_lv(img[i], title[i], description[i], cost[i], time[i]));
        }
        ArrayAdapter<Transaction_lv> adapter = new ArrayAdapter<>(requireActivity(), R.layout.layout_transaction_home, mylist);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, title);
        listView.setAdapter(adapter);

    }


}
