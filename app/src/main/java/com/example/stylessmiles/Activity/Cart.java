package com.example.stylessmiles.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.adpater.CartProductAdapter;
import com.example.stylessmiles.adpater.CartServiceAdapter;
import com.example.stylessmiles.centralStore;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Cart extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

    @BindView(R.id.rv_services)
    RecyclerView serviceRecycle;

    @BindView(R.id.rv_product)
    RecyclerView productRecycle;

    @BindView(R.id.tv_totalPrice)
    TextView tv_totalPrice;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_saloonname)
    TextView tv_saloonname;

    DatePickerDialog datePickerDialog;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");
        loadData();
    }

    private void loadData() {
        LinearLayoutManager layoutManager;
        CartServiceAdapter adapter = new CartServiceAdapter(centralStore.cart.getServices(), this);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        serviceRecycle.setLayoutManager(layoutManager);
        serviceRecycle.setAdapter(adapter);
        LinearLayoutManager layoutManager2;
        CartProductAdapter adapter2 = new CartProductAdapter(centralStore.cart.getProducts(), this);
        layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        productRecycle.setLayoutManager(layoutManager2);
        productRecycle.setAdapter(adapter2);
        tv_totalPrice.setText("Rs. " + String.valueOf(centralStore.cart.getTotalPrice()));
        tv_address.setText("Address: " + centralStore.getUser().getAddress());
        tv_saloonname.setText("Salon: " + centralStore.cart.getSaloonname());
    }

    public void updateTotalPrice() {
        tv_totalPrice.setText("Rs. " + String.valueOf(centralStore.cart.getTotalPrice()));
    }

    public void placeOrder(View view) {
        PickDateAndTime();

    }

    private void PickDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog  = new DatePickerDialog(Cart.this, Cart.this,year, month,day);
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Cart.this, Cart.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
    }
}