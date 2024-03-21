package hwngne.tlu.montra;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

public class HomeFragment extends Fragment {

    private PieChart pieChart;
    ArrayList<Transaction_lv> mylist;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieChart = view.findViewById(R.id.pie_chart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30f, ""));
        entries.add(new PieEntry(20f, ""));
        entries.add(new PieEntry(50f, ""));

        PieDataSet dataSet = new PieDataSet(entries, "Sample Chart");
        dataSet.setColors(Color.RED, Color.GREEN, Color.BLUE);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.invalidate();
        int img = R.drawable.cart;
        String title = "Shopping";
        String  description = "Buy some grocery";
        String cost = "- $120";
        String time = "12:00 AM";

        ListView listView = view.findViewById(R.id.lv);
        mylist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mylist.add(new Transaction_lv(img, title, description, cost, time));
        }

        TransactionHomeArrayAdapter adapter = new TransactionHomeArrayAdapter(requireActivity(), mylist);
        listView.setAdapter(adapter);
    }


}