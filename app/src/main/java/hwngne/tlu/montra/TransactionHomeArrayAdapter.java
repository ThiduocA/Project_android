package hwngne.tlu.montra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionHomeArrayAdapter extends ArrayAdapter<Transaction_lv> {

    private Context context;
    private ArrayList<Transaction_lv> transactions;

    private int layout;

    public class ViewHolder{
        TextView category, description, cash, time;
        ImageView img;
    }
    public TransactionHomeArrayAdapter(Context context, int layout, ArrayList<Transaction_lv> transactions) {
        super(context, 0, transactions);
        this.context = context;
        this.layout = layout;
        this.transactions = transactions;
    }

    public TransactionHomeArrayAdapter(Context context, ArrayList<Transaction_lv> transactions) {
        super(context, 0, transactions);
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.layout_transaction_home, parent, false);
//        }
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.imagelv);
            viewHolder.category = (TextView) convertView.findViewById(R.id.title_lv);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description_lv);
            viewHolder.cash = (TextView) convertView.findViewById(R.id.cost_lv);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time_lv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Transaction_lv transaction = transactions.get(position);
        viewHolder.img.setImageResource(R.drawable.cart);
        viewHolder.category.setText(transaction.getTitle());
        if(transaction.getTitle().equals("Shop")){
            viewHolder.cash.setTextColor(ContextCompat.getColor(context, R.color.red));
            viewHolder.cash.setText("- $" + String.valueOf(transaction.getCost()));
        }
        if(transaction.getTitle().equals("Salary")){

            viewHolder.cash.setTextColor(ContextCompat.getColor(context, R.color.green));
            viewHolder.cash.setText("+ " + String.valueOf(transaction.getCost()));
        }
        viewHolder.description.setText(transaction.getDescription());
        viewHolder.time.setText(transaction.getTime());


//        ImageView imageView = convertView.findViewById(R.id.imagelv);
//        TextView titleTextView = convertView.findViewById(R.id.title_lv);
//        TextView descriptionTextView = convertView.findViewById(R.id.description_lv);
//        TextView costTextView = convertView.findViewById(R.id.cost_lv);
//        TextView timeTextView = convertView.findViewById(R.id.time_lv);
//
//        imageView.setImageResource(R.drawable.cart);
//        titleTextView.setText(transaction.getTitle());
//        descriptionTextView.setText(transaction.getDescription());
//        costTextView.setText(String.valueOf(transaction.getCost()));
//        timeTextView.setText(transaction.getTime());

        System.out.println("category: " + transaction.getTitle());

        return convertView;
    }
}

