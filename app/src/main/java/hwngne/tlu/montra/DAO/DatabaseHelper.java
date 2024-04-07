package hwngne.tlu.montra.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

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

        return userId;
    }
    @SuppressLint("Range")
    public String searchName(int id) {
        db = this.getReadableDatabase();
        String name = null;
        String query = "SELECT name FROM user WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
        db.close();
        return name;
    }
    public String searchEmail(String email){
        db = this.getReadableDatabase();
        String email1 = "";
        String query = "select email from user where email = '"+email+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            email1 = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return email1;
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
    public void insertExpense(SQLiteDatabase db, String category, String description, int cash, int id_user){
        String query = "insert into expense(category, description, cash, id_user) values('"+category+"', '"+description+"', '"+cash+"', '"+id_user+"')";
        db.execSQL(query);
        db.close();
    }
    public void insertIncome(String category, String description, int cash, int id_user){
        db = dbWrite;
        String query = "insert into income(category, description, cash, id_user) values('"+category+"', '"+description+"', '"+cash+"', '"+id_user+"')";
        db.execSQL(query);
        db.close();
    }
    public void insertUser(String name, String email, String password){
        //String query = "insert into user(name, email, password, balance) values('"+name+"', '"+email+"', '"+password+"', 0)";
        db = dbWrite;
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("password", password);
        values.put("balance", 0);
        db.insert("user", null, values);
        db.close();
    }
    public void updateBalance(int id){
        db = dbWrite;
        String query = "UPDATE user " +
                "SET balance = " +
                "CASE " +
                "WHEN (SELECT COALESCE(SUM(cash), 0) FROM income WHERE id_user = '"+id+"') IS NULL THEN " +
                "0 - (SELECT COALESCE(SUM(cash), 0) FROM expense WHERE id_user = '"+id+"') " +
                "WHEN (SELECT COALESCE(SUM(cash), 0) FROM expense WHERE id_user = '"+id+"') IS NULL THEN " +
                "(SELECT COALESCE(SUM(cash), 0) FROM income WHERE id_user = '"+id+"') " +
                "ELSE " +
                "(SELECT COALESCE(SUM(cash), 0) FROM income WHERE id_user = '"+id+"') - (SELECT COALESCE(SUM(cash), 0) FROM expense WHERE id_user = '"+id+"') " +
                "END " +
                "WHERE id = '"+id+"'";
        db.execSQL(query);
        db.close();
    }
    public int searchIdForReset(String email){
        db = this.getReadableDatabase();
        int id = 0;
        String query = "select id from user where email = '"+email+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }
    public void updatePassword(int id, String pass){
        db = dbWrite;
        String query = "update user set password = '"+pass+"' where id = '"+id+"'";
        db.execSQL(query);
        db.close();
    }
    public void updatePasswordwithEmail(String email, String pass){
        db = dbWrite;
        String query = "update user set password = '"+pass+"' where email = '"+email+"'";
        db.execSQL(query);
        db.close();
    }
}
