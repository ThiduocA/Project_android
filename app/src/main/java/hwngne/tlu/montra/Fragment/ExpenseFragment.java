    package hwngne.tlu.montra.Fragment;

    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.Toast;

    import com.google.android.material.textfield.TextInputEditText;

    import hwngne.tlu.montra.DAO.DatabaseHelper;
    import hwngne.tlu.montra.R;


    public class ExpenseFragment extends Fragment {

        String[] item = {"Shop","Food","Subscription", "Transportation"};
        AutoCompleteTextView autoCompleteTextView;
        ArrayAdapter<String> adapterItem;
        int userId;

        DatabaseHelper dbHelper;
        String category;

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
                    category = selectedItem;
                    Toast.makeText(getContext(), "Item: " + selectedItem, Toast.LENGTH_SHORT).show(); // Sử dụng getContext() hoặc requireContext()
                }
            });
            ImageView btn_back = view.findViewById(R.id.btn_back);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(new HomeFragment(), false);
                }
            });

            TextInputEditText textInputEditText = view.findViewById(R.id.description);
            TextInputEditText textInputEditText1 = view.findViewById(R.id.money);
            userId = requireActivity().getIntent().getIntExtra("userId", -1);
            System.out.println("userId trong expense fragment: " + userId);
            Button btn = view.findViewById(R.id.button5);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String description = textInputEditText.getText().toString();
                    String cashStr = textInputEditText1.getText().toString();
                    dbHelper = new DatabaseHelper(ExpenseFragment.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if(description.isEmpty() || cashStr.isEmpty() || !autoCompleteTextView.enoughToFilter()){
                        Toast.makeText(requireContext(), "Dữ liệu không được để trống!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        try{
                            int cash = Integer.parseInt(cashStr);
                            dbHelper.insertExpense(db, category, description, cash, userId);
                            Toast.makeText(requireContext(), "Thêm dữ liệu thành công", Toast.LENGTH_LONG).show();
                            System.out.println("category expense" +category);
                            System.out.println("description expense:" + description);
                            System.out.println("cash expense: " + cash);
                        }catch (NumberFormatException e){
                            Toast.makeText(requireContext(), "Số tiền phải là số!", Toast.LENGTH_LONG).show();
                            System.out.println("Lỗi: " + e.getMessage());
                        }catch (Exception e){
                            Toast.makeText(requireContext(), "Thêm dữ liệu thất bại", Toast.LENGTH_LONG).show();
                            System.out.println("Lỗi: " + e.getMessage());
                        }
                    }
                }
            });
            return view;
        }
        private void loadFragment(Fragment fragment, boolean isAppInitialized){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(isAppInitialized == false){
                fragmentTransaction.replace(R.id.framelayout, fragment);
            }
            fragmentTransaction.commit();
        }
    }
