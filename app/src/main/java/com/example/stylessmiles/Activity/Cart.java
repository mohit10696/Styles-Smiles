package com.example.stylessmiles.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.adpater.CartProductAdapter;
import com.example.stylessmiles.adpater.CartServiceAdapter;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.CartModel;
import com.example.stylessmiles.model.OrderModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Cart extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

    @BindView(R.id.tv_datetime)
    TextView tv_datetime;

    @BindView(R.id.tv_selectdate)
    Button tv_selectdate;

    DatePickerDialog datePickerDialog;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    boolean setTime= false;
    private DatabaseReference mDatabase;
    FirebaseDatabase rootnode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");
        rootnode = FirebaseDatabase.getInstance();
        mDatabase = rootnode.getReference();
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
        tv_saloonname.setText("Salon: " + centralStore.cart.getSaloonname());
    }

    public void placeOrder(View view) throws ParseException {
        if(centralStore.cart.getSaloonname().equals("")){
            Toast.makeText(this, "Please select salon or your cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!setTime){
            Toast.makeText(this, "Please select date & time", Toast.LENGTH_SHORT).show();
            PickDateAndTime();
            return;
        }

        Calendar appoitmentdate = Calendar.getInstance();
        appoitmentdate.set(myYear,myMonth,myday,myHour,myMinute);

        OrderModel orderModel = new OrderModel(centralStore.getInstance().cart,centralStore.getUser().getEmail(),centralStore.getStringDate(Calendar.getInstance().getTime()),centralStore.getStringDate(appoitmentdate.getTime()),"Order Placed");
        mDatabase.child("Order").push().setValue(orderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Order successfully placed",Toast.LENGTH_SHORT).show();
                clearCart(getCurrentFocus());
            }
        });
//        Log.e(appoitmentdate.getTime().toString(), "placeOrder: "+Calendar.getInstance().getTime().toString() );
//        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
//        Date date = appoitmentdate.getInstance().getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strDate = dateFormat.format(date);
//        Log.e(strDate, "placeOrder: " );
//        Date date1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(strDate);
    }

    private void PickDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(Cart.this, Cart.this, year, month, day);
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
        setTime = true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;

        tv_datetime.setText(String.valueOf(myHour) + ":" + String.valueOf(myMinute) + " " + String.valueOf(myday) + "/" + String.valueOf(myMonth) + "/" + String.valueOf(myYear));
        tv_selectdate.setText("Change Date & Time");

    }

    public void clearCart(View view) {
        centralStore.getInstance().cart = new CartModel();
        centralStore.getInstance().synccart();
        loadData();
    }

    public void selectdate(View view) {
        PickDateAndTime();
    }
}