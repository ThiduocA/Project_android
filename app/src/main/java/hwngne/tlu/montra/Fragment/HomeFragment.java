package hwngne.tlu.montra.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;

import hwngne.tlu.montra.DAO.Connect;
import hwngne.tlu.montra.DAO.DatabaseHelper;
import hwngne.tlu.montra.R;
import hwngne.tlu.montra.TransactionHomeArrayAdapter;
import hwngne.tlu.montra.Transaction_lv;

public class HomeFragment extends Fragment {

    private PieChart pieChart;
    ArrayList<Transaction_lv> mylist;
    DatabaseHelper dbHelper;
    Connect connect;
    TransactionHomeArrayAdapter adapter;
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

//        int money_income = 0, money_expense = 0;
//        money_income = dbHelper.showCashIncome(userId);
//        money_expense = dbHelper.showCashExpense(userId);
//        pieChart = view.findViewById(R.id.pie_chart);
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(30f, ""));
//        entries.add(new PieEntry(20f, ""));
//        entries.add(new PieEntry(50f, ""));
//
//        PieDataSet dataSet = new PieDataSet(entries, "Sample Chart");
//        dataSet.setColors(Color.RED, Color.GREEN, Color.BLUE);
//
//        PieData data = new PieData(dataSet);
//
//        pieChart.setData(data);
//        pieChart.invalidate();

        connect = new Connect(requireContext(), "montra.db", null, 1);
        ListView listView = view.findViewById(R.id.lv);
        mylist = new ArrayList<>();
        TransactionHomeArrayAdapter adapter = new TransactionHomeArrayAdapter(requireActivity(), R.layout.layout_transaction_home, mylist);
        listView.setAdapter(adapter);

        TextView cash_income = view.findViewById(R.id.cash_income);
        dbHelper = new DatabaseHelper(HomeFragment.this);
        cash_income.setText(String.valueOf(dbHelper.showCashIncome(userId)));
        System.out.println("Tong tien: " + dbHelper.showCashIncome(userId));
        TextView cash_expense = view.findViewById(R.id.cash_expense);
        dbHelper = new DatabaseHelper(HomeFragment.this);
        cash_expense.setText(String.valueOf(dbHelper.showCashExpense(userId)));
        dbHelper = new DatabaseHelper(HomeFragment.this);
        dbHelper.updateBalance(userId);
        TextView balance = view.findViewById(R.id.balance);
        dbHelper = new DatabaseHelper(HomeFragment.this);
        int totalcash = dbHelper.showBalance(userId);
        System.out.println("Tong tien trang home: " +totalcash);
        if(totalcash < 0){
            Toast.makeText(requireContext(), "Bạn đã vượt quá chi tiêu, vui lòng nạp thêm tiền!", Toast.LENGTH_LONG).show();
            dbHelper = new DatabaseHelper(HomeFragment.this);
            //dbHelper.updateBalance1(userId);
        }
        balance.setText(String.valueOf(dbHelper.showBalance(userId)));
        actionGetData(userId);
    }

    public void actionGetData(int id){
        Cursor data = connect.getData("SELECT category, description, cash, time " +
                "FROM (" +
                "    SELECT category, description, cash, time FROM income where id_user = '"+id+"'" +
                "    UNION ALL " +
                "    SELECT category, description, cash, time FROM expense where id_user = '"+id+"'" +
                ") AS combined " +
                "ORDER BY time DESC;"
);
        mylist.clear();
        while (data.moveToNext()){
            String category = data.getString(0);
            String description = data.getString(1);
            int cash = data.getInt(2);
            String time = data.getString(3);
            mylist.add(new Transaction_lv(category, description, cash, time));
        }
        if(mylist.isEmpty()){
            Toast.makeText(requireContext(), "Bạn phải thêm dữ liệu!", Toast.LENGTH_LONG).show();
        }
    }
}