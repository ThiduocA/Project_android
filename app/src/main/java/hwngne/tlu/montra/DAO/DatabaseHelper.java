package hwngne.tlu.montra.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

import hwngne.tlu.montra.HomeFragment;
import hwngne.tlu.montra.Transaction_lv;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "montra.db";
    private static final int DATABSE_VERSION = 1;
    SQLiteDatabase db;
    SQLiteDatabase dbWrite;
    SQLiteDatabase dbRead;
    public DatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
        dbWrite = getWritableDatabase();
        dbRead = getReadableDatabase();
    }

    private static final String createTableUser = "CREATE TABLE IF NOT EXISTS user ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT, "
            + "email TEXT, "
            + "password text, "
            + "balance INT)";

    private static final String createTableIncome = "CREATE TABLE IF NOT EXISTS income("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "category TEXT, "
            + "description TEXT, "
            + "cash INT, "
            + "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
            + "id_user INT, "
            + "FOREIGN KEY (id_user) REFERENCES user(" + "id))";



    private static final String createTableExpenses = "CREATE TABLE IF NOT EXISTS expense("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "category TEXT, "
            + "description TEXT, "
            + "cash INT, "
            + "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
            + "id_user INT, "
            + "FOREIGN KEY (id_user) REFERENCES user(" + "id))";


    public DatabaseHelper(@Nullable Fragment homeFragment) {
        super(Objects.requireNonNull(homeFragment).requireContext(), DATABASE_NAME, null, DATABSE_VERSION);
        dbWrite = getWritableDatabase();
        dbRead = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTableUser);

        db.execSQL(createTableIncome);

        db.execSQL(createTableExpenses);

        this.db = db;
        db.execSQL("INSERT INTO user (name, email, password, balance) VALUES ('Xuan Hung', 'admin@gmail.com', '1', 650)");
        db.execSQL("INSERT INTO income (category, description, cash, id_user) VALUES('Shop', 'Buy some grocery', 1000, 1)");
        db.execSQL("INSERT INTO income (category, description, cash, id_user) VALUES('Shop', 'Buy some grocery 2', 50, 1)");
        db.execSQL("INSERT INTO income (category, description, cash, id_user) VALUES('Shop', 'Buy some grocery 3', 100, 1)");
        db.execSQL("INSERT INTO expense (category, description, cash, id_user) VALUES('Salary', 'Salary for July', 500, 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS income");
        db.execSQL("DROP TABLE IF EXISTS expense");

        onCreate(db);
        this.onCreate(db);
    }

    public String searchPass(String tk){
        db = this.getReadableDatabase();
        String query = "select email, password from user";
        Cursor cursor = db.rawQuery(query, null);

        String email, password = "akrjlwe";
        if(cursor.moveToFirst()){
            do{
                email = cursor.getString(0);
                if(email.equals(tk)){
                    password = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        db.close();
        return password;
    }
    public int searchId(String tk,String pass){
        db = this.getReadableDatabase();
        int userId = -1; 

        String query = "SELECT id FROM user WHERE email = ? AND password = ?";

        String email = tk;
        String password = pass;
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");

            userId = cursor.getInt(idIndex);
        }

        cursor.close();
        db.close();

        return userId;
    }
    @SuppressLint("Range")
    public String searchName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = null;

        // Tạo câu truy vấn để lấy tên dựa trên id
        String query = "SELECT name FROM user WHERE id = ?";

        // Thực thi câu truy vấn với tham số là id
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        // Kiểm tra xem có dữ liệu nào được trả về không
        if (cursor.moveToFirst()) {
            // Lấy tên từ cột name
            name = cursor.getString(cursor.getColumnIndex("name"));
        }

        // Đóng cursor và database
        cursor.close();
        db.close();

        // Trả về tên nếu tìm thấy, null nếu không tìm thấy
        return name;
    }

    public int showCashIncome(int id){
        int totalCash = 0;
        db = this.getReadableDatabase();
        System.out.println("chay duoc");
        String query = "SELECT SUM(cash) AS cash_income FROM income where id_user = '"+id+"'";
        Cursor cursor = db.rawQuery(query, null);
        int index = cursor.getColumnIndex("cash_income");
        if(cursor.moveToFirst()){
            totalCash = cursor.getInt(index);
            System.out.println("Tong tien income: " + totalCash);
        }
        else{
            System.out.println("Loi");
        }
        cursor.close();
        db.close();
        return totalCash;
    }
    public int showCashExpense(int id){
        int totalCash = 0;
        db = this.getReadableDatabase();
        System.out.println("chay duoc");
        String query = "SELECT SUM(cash) AS cash_expense FROM expense where id_user = '"+id+"'";
        Cursor cursor = db.rawQuery(query, null);
        int index = cursor.getColumnIndex("cash_expense");
        if(cursor.moveToFirst()){
            totalCash = cursor.getInt(index);
            System.out.println("Tong tien expense: " + totalCash);
        }
        else{
            System.out.println("Loi");
        }
        cursor.close();
        db.close();
        return totalCash;
    }
    public int showBalance(int id){
        int totalCash = 0;
        db = this.getReadableDatabase();
        System.out.println("chay duoc");
        String query = "SELECT balance FROM user where id = '"+id+"'";
        Cursor cursor = db.rawQuery(query, null);
        int index = cursor.getColumnIndex("balance");
        if(cursor.moveToFirst()){
            totalCash = cursor.getInt(index);
            System.out.println("Tong tien balance: " + totalCash);
        }
        else{
            System.out.println("Loi");
        }
        cursor.close();
        db.close();
        return totalCash;
    }


}
