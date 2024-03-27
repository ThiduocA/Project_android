package hwngne.tlu.montra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class IncomeArrayAdapter extends ArrayAdapter<Income> {
    private Context context;
    private ArrayList<Income> incomes;

    public IncomeArrayAdapter(Context context, ArrayList<Income> incomes) {
        super(context, 0, incomes);
        this.context = context;
        this.incomes = incomes;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_transaction_home, parent, false);
        }

        Income income = incomes.get(position);
        TextView cashTextView = convertView.findViewById(R.id.cash_income);
        cashTextView.setText(income.getCash());
//        Transaction_lv transaction = transactions.get(position);
//
//        ImageView imageView = convertView.findViewById(R.id.imagelv);
//        TextView titleTextView = convertView.findViewById(R.id.title_lv);
//        TextView descriptionTextView = convertView.findViewById(R.id.description_lv);
//        TextView costTextView = convertView.findViewById(R.id.cost_lv);
//        TextView timeTextView = convertView.findViewById(R.id.time_lv);
//
//        imageView.setImageResource(transaction.getImage());
//        titleTextView.setText(transaction.getTitle());
//        descriptionTextView.setText(transaction.getDescription());
//        costTextView.setText(transaction.getCost());
//        timeTextView.setText(transaction.getTime());

        return convertView;
    }
}
