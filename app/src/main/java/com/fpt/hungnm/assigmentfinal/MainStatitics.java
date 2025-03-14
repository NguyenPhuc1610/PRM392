package com.fpt.hungnm.assigmentfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fpt.hungnm.assigmentfinal.Dal.MyDbContext;
import com.fpt.hungnm.assigmentfinal.Model.Category;
import com.fpt.hungnm.assigmentfinal.Model.Transaction;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainStatitics extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Phuc";
    private final int REQUEST_CODE = 4;

    private PieChart pieChart;
    private Spinner spMonth;
    private BarChart barChart;

    private ImageView btnHome;
    private ImageView btnTransaction;

    private ImageView btnStatistic;

    private Button btnLogout;

    private MyDbContext dbContext;
    private int month;

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_statitics);

        Log.d(TAG, "onCreate started");

        bindingView();           // 1. ánh xạ view trước
        bindingAction();         // 2. gán sự kiện sau
        initData();              // 3. load dữ liệu sau cùng

        Log.d(TAG, "onCreate finished");
    }

    private void bindingView() {
        try {
            Log.d(TAG, "bindingAction called");
            listView = findViewById(R.id.listview);
            pieChart = findViewById(R.id.piechart);
            spMonth = findViewById(R.id.sp_statistic_month);
            barChart = findViewById(R.id.bar_chart);

            btnHome = findViewById(R.id.img_statistic_btnHome);
            if (btnHome == null) Log.e(TAG, "btnHome is NULL");

            btnTransaction = findViewById(R.id.img_statistic_btnTransaction);
            if (btnTransaction == null) Log.e(TAG, "btnTransaction is NULL");

            btnStatistic = findViewById(R.id.img_statistic_btnAccount);
            if (btnStatistic == null) Log.e(TAG, "btnStatistic is NULL");
            btnLogout = findViewById(R.id.btnLogout);
        } catch (Exception ex) {
            Log.e(TAG, "MainStatistics - bindingView - " + ex.getMessage());
        }
    }

    private void bindingAction() {
        Log.d(TAG, "bindingAction started!");  // Kiểm tra có log hay không
        try {
            dbContext = new MyDbContext(this);

            btnHome.setOnClickListener(this::goToHome);
            btnTransaction.setOnClickListener(this::goToTransaction);
            btnStatistic.setOnClickListener(this::btnStatistic);

            if (btnLogout == null) {
                Log.e(TAG, "btnLogout is NULL inside bindingAction");
            } else {
                Log.d(TAG, "btnLogout is OK in bindingAction");
                btnLogout.setOnClickListener(this::logout);
            }

            spMonth.setOnItemSelectedListener(this);
        } catch (Exception ex) {
            Log.e(TAG, "MainStatitics - bindingAction - " + ex.getMessage());
        }
    }



    private void initData() {
        try {
            Date currentDate = new Date();
            month = currentDate.getMonth() + 1;

            spMonth.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.month)));

            spMonth.setSelection(month - 1);

            getData();
        } catch (Exception ex) {
            Log.e(TAG, "MainStatitics - initData - " + ex.getMessage());
        }
    }

    private void getData() {
        try {
            List<Transaction> transactions = dbContext.getTransactionByMonth(month);

            List<Transaction> transactionIncomes = new ArrayList<>();
            List<Transaction> transactionExpenses = new ArrayList<>();

            for (Transaction item : transactions) {
                if (item.getIsIncome().equals("EXPENSE")) {
                    transactionExpenses.add(item);
                } else {
                    transactionIncomes.add(item);
                }
            }

            List<Category> categories = dbContext.getAllCategory();
            List<Category> categoryIncome = new ArrayList<>();
            List<Category> categoryExpense = new ArrayList<>();

            for (Category item : categories) {
                for (Transaction tr : transactions) {
                    if (Integer.parseInt(tr.getCategory()) == item.getId() &&
                            tr.getPrice() != null && !tr.getPrice().isEmpty()) {

                        long total = item.getTotal() + Long.parseLong(tr.getPrice());
                        item.setTotal(total);
                    }
                }

                if (item.getIsIncome().equals("EXPENSE")) {
                    categoryExpense.add(item);
                } else {
                    categoryIncome.add(item);
                }
            }

            setupPieChart(categoryExpense);
            setupBarChart(categoryIncome);

            ArrayList<String> dataList = new ArrayList<>();
            for (Category item : categoryIncome) {
                String s = "ID: " + item.getId() + " Name: " + item.getTitle();
                dataList.add(s);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, dataList);
            listView.setAdapter(adapter);

        } catch (Exception ex) {
            Log.e(TAG, "MainStatitics - getData - " + ex.getMessage());
        }
    }
    public static int[] generateRandomColors(int count) {
        int[] colors = new int[count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);

            int color = (255 << 24) | (r << 16) | (g << 8) | b;

            colors[i] = color;
        }

        return colors;
    }

    private void setupPieChart(List<Category> categoryExpense) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Category item : categoryExpense) {
            entries.add(new PieEntry(item.getTotal(), item.getTitle()));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "Expenses");
        pieDataSet.setColors(generateRandomColors(entries.size()));

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void setupBarChart(List<Category> categoryIncome) {
        ArrayList<BarEntry> visitors = new ArrayList<>();
        for (Category item : categoryIncome) {
            visitors.add(new BarEntry(item.getId(), item.getTotal()));
        }

        BarDataSet barDataSet = new BarDataSet(visitors, "Income");
        barDataSet.setColors(generateRandomColors(5));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(2000);
    }

    private void logout(View view) {
        try {
            // Tạo Dialog xác nhận
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Xác nhận đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Nếu nhấn "Có", thực hiện Logout
                        Logout.performLogout(MainStatitics.this);
                        Toast.makeText(MainStatitics.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        // Nếu nhấn "Không", đóng dialog
                        dialog.dismiss();
                    })
                    .show();

        } catch (Exception ex) {
            Log.e(TAG, "MainStatitics - logout - " + ex.getMessage());
        }
    }




    private void goToHome(View view) {
        try {
            goIntent();
            Intent i = new Intent(this, Home.class);
            startActivityForResult(i, REQUEST_CODE);
        } catch (Exception ex) {
            Log.e(TAG, "MainStatistic - goToHome - " + ex.getMessage());
        }
    }

    private void btnStatistic(View view) {
        try {
            goIntent();
            Intent i = new Intent(this, MainStatitics.class);
            startActivityForResult(i, REQUEST_CODE);
        } catch (Exception ex) {
            Log.e(TAG, "MainStatistic - btnStatistic - " + ex.getMessage());
        }
    }

    private void goToTransaction(View view) {
        try {
            goIntent();
            Intent i = new Intent(this, MainTransaction.class);
            startActivityForResult(i, REQUEST_CODE);
        } catch (Exception ex) {
            Log.e(TAG, "MainStatistic - goToTransaction - " + ex.getMessage());
        }
    }

    private void goToBudget(View view) {
        try {
            goIntent();
            Intent i = new Intent(this, MainBudget.class);
            startActivityForResult(i, REQUEST_CODE);
        } catch (Exception ex) {
            Log.e(TAG, "MainStatistic - goToBudget - " + ex.getMessage());
        }
    }

    private void goIntent() {
        try {
            btnStatistic.setColorFilter(ContextCompat.getColor(this, R.color.xam), PorterDuff.Mode.SRC_IN);
        } catch (Exception ex) {
            Log.e(TAG, "MainStatistic - goIntent - " + ex.getMessage());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        month = position + 1;
        getData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Không cần xử lý
    }
}
