package hwngne.tlu.montra;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import hwngne.tlu.montra.DAO.Connect;
import hwngne.tlu.montra.DAO.DatabaseHelper;


public class FinancialFragment extends Fragment {

    private PieChart pieChart;
    ArrayList<Transaction_lv> mylist;
    ArrayList<Integer> listCash;
    Connect connect;
    DatabaseHelper dbHelper;
    int userId;

    public FinancialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_financial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView_expense = view.findViewById(R.id.btn_expense);
        TextView textView_income = view.findViewById(R.id.btn_income);
        userId = requireActivity().getIntent().getIntExtra("userId", -1);
        System.out.println("userId trong financial fragment: " + userId);

        ImageView btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TransactionFragment(), false);
            }
        });
        textView_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect = new Connect(requireContext(), "montra.db", null, 1);
                TextView total = view.findViewById(R.id.total);
                dbHelper = new DatabaseHelper(FinancialFragment.this);
                total.setText(String.valueOf(dbHelper.showCashExpense(userId)));
                pieChart = view.findViewById(R.id.pie_chart);
                listCash = getEachCashExpense();
                ArrayList<PieEntry> entries = new ArrayList<>();
                for(int i = 0; i < listCash.size(); i++){
                    float temp = (float) listCash.get(i);
                    System.out.println("ptu: " + listCash.get(i));
                    entries.add(new PieEntry(temp, ""));
                }

//                entries.add(new PieEntry(1150f, ""));
//                entries.add(new PieEntry(500f, ""));
//                entries.add(new PieEntry(50f, ""));

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                PieData data = new PieData(dataSet);
                pieChart.getDescription().setEnabled(false);
                pieChart.setData(data);
                pieChart.invalidate();


                ListView listView = view.findViewById(R.id.list_view_transaction);
                mylist = new ArrayList<>();
                TransactionHomeArrayAdapter adapter = new TransactionHomeArrayAdapter(requireActivity(), R.layout.layout_transaction_home, mylist);
                listView.setAdapter(adapter);
                getExpense();
            }
        });
        textView_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect = new Connect(requireContext(), "montra.db", null, 1);
                TextView total = view.findViewById(R.id.total);
                dbHelper = new DatabaseHelper(FinancialFragment.this);
                total.setText(String.valueOf(dbHelper.showCashIncome(userId)));
                pieChart = view.findViewById(R.id.pie_chart);
                listCash = getEachCashIncome();
                ArrayList<PieEntry> entries = new ArrayList<>();
                for(int i = 0; i < listCash.size(); i++){
                    float temp = (float) listCash.get(i);
                    System.out.println("ptu income: " + listCash.get(i));
                    entries.add(new PieEntry(temp, ""));
                }
//                entries.add(new PieEntry(1150f, ""));
//                entries.add(new PieEntry(500f, ""));
//                entries.add(new PieEntry(50f, ""));

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                PieData data = new PieData(dataSet);
                pieChart.getDescription().setEnabled(false);
                pieChart.setData(data);
                pieChart.invalidate();

                ListView listView = view.findViewById(R.id.list_view_transaction);
                mylist = new ArrayList<>();
                TransactionHomeArrayAdapter adapter = new TransactionHomeArrayAdapter(requireActivity(), R.layout.layout_transaction_home, mylist);
                listView.setAdapter(adapter);
                getIncome();
            }
        });
    }

    public void getExpense(){
        Cursor data = connect.getData("Select category, description, cash, time from expense");
        mylist.clear();
        while (data.moveToNext()){
            String category = data.getString(0);
            String description = data.getString(1);
            int cash = data.getInt(2);
            String time = data.getString(3);
            mylist.add(new Transaction_lv(category, description, cash, time));
        }
    }
    public void getIncome(){
        Cursor data = connect.getData("Select category, description, cash, time from income");
        mylist.clear();
        while (data.moveToNext()){
            String category = data.getString(0);
            String description = data.getString(1);
            int cash = data.getInt(2);
            String time = data.getString(3);
            mylist.add(new Transaction_lv(category, description, cash, time));
        }
    }
    public ArrayList<Integer> getEachCashExpense(){
        Cursor data = connect.getData("Select cash from expense");
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (data.moveToNext()){
            int cash = data.getInt(0);
            arrayList.add(cash);
            System.out.println("cash: " + cash);
        }
        return arrayList;
    }
    public ArrayList<Integer> getEachCashIncome(){
        Cursor data = connect.getData("Select cash from income");
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (data.moveToNext()){
            int cash = data.getInt(0);
            arrayList.add(cash);
            System.out.println("cash income @@@@@: " + cash);
        }
        return arrayList;
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