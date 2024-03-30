    package hwngne.tlu.montra;

    import android.os.Bundle;

    import androidx.fragment.app.Fragment;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.Toast;


    public class ExpenseFragment extends Fragment {

        String[] item = {"shop","abc","def"};
        AutoCompleteTextView autoCompleteTextView;
        ArrayAdapter<String> adapterItem;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_expense, container, false);

            autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
            adapterItem = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, item);

            autoCompleteTextView.setAdapter(adapterItem);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getContext(), "Item: " + selectedItem, Toast.LENGTH_SHORT).show(); // Sử dụng getContext() hoặc requireContext()
                }
            });

            return view;
        }
    }
