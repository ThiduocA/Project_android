package hwngne.tlu.montra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import hwngne.tlu.montra.DAO.DatabaseHelper;

public class HomeFragment extends Fragment {

    private PieChart pieChart;
    ArrayList<Transaction_lv> mylist;
    DatabaseHelper dbHelper;
    int userId;
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
        //cash_income.setText(dbHelper.showCashIncome(cash_income));
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
//        sharedPreferences.getInt("userId", -1);
        userId = requireActivity().getIntent().getIntExtra("userId", -1);
        System.out.println("userId trong fragment: " + userId);
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

        TextView cash_income = view.findViewById(R.id.cash_income);
        dbHelper = new DatabaseHelper(HomeFragment.this);
//        if(dbHelper.showCashIncome() == null){
//            cash_income.setText("123");
//        }else{
//            cash_income.setText(dbHelper.showCashIncome());
//        }
        cash_income.setText(String.valueOf(dbHelper.showCashIncome(userId)));
    }


}