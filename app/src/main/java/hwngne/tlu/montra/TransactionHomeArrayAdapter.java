package hwngne.tlu.montra;

import android.annotation.SuppressLint;
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

public class TransactionHomeArrayAdapter extends ArrayAdapter<Transaction_lv> {

    private Context context;
    private ArrayList<Transaction_lv> transactions;

    public TransactionHomeArrayAdapter(Context context, ArrayList<Transaction_lv> transactions) {
        super(context, 0, transactions);
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_transaction_home, parent, false);
        }

        Transaction_lv transaction = transactions.get(position);

        ImageView imageView = convertView.findViewById(R.id.imagelv);
        TextView titleTextView = convertView.findViewById(R.id.title_lv);
        TextView descriptionTextView = convertView.findViewById(R.id.description_lv);
        TextView costTextView = convertView.findViewById(R.id.cost_lv);
        TextView timeTextView = convertView.findViewById(R.id.time_lv);

        imageView.setImageResource(transaction.getImage());
        titleTextView.setText(transaction.getTitle());
        descriptionTextView.setText(transaction.getDescription());
        costTextView.setText(transaction.getCost());
        timeTextView.setText(transaction.getTime());


        return convertView;
    }
}

