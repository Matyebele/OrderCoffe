package com.example.android.ordercoffe;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int numOfCoffee = 1;
    int price = 0;
    EditText Name;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.editText);

    }

    public void increment(View v) {
        if (numOfCoffee == 100){
            Toast.makeText(this, "You can not order more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        numOfCoffee = numOfCoffee + 1;
        Display(numOfCoffee);

    }

    public void decrement(View v) {
        if (numOfCoffee == 1){
            Toast.makeText(this, "You can not order less than 1 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        numOfCoffee = numOfCoffee - 1;
        Display(numOfCoffee);

    }

   /* private void isWhippedCreamChck(View v) {
        CheckBox WhippedCream = (CheckBox) v;
        if (WhippedCream.isChecked()) {
            hasWhippedCream = true;
        } else {
            hasWhippedCream = false;
        }
    }

    private void ISChocolateChck(View vv) {
        CheckBox Chocolate = (CheckBox) vv;
        if (Chocolate.isChecked()) {
            hasChocolate = true;
        } else {
            hasChocolate = false;
        }
    }*/

    public void SubmitOrder(View v) {

        CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.chkWhippedCream);
        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chkChocolate);

        checkBoxWhippedCream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hasWhippedCream = true;
                } else {
                    hasWhippedCream = false;
                }
            }
        });
        checkBoxChocolate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hasChocolate = true;

                } else {
                    hasChocolate = false;

                }
            }
        });

        Display(numOfCoffee);
        name = Name.getText().toString();
        price = calculatePrice(hasWhippedCream, hasChocolate);
        String msg = name + "\n" + OrderSumary(price);
        msg += "\n has whipped cream? " + hasWhippedCream + "\n has Chocolate " + hasChocolate;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("Mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, "Coffee order for " + name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);


        }
    }


    private int calculatePrice(boolean addWhippedcrm,boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedcrm){
            basePrice = basePrice + 1;
        }
        if (addChocolate){
            basePrice = basePrice + 2;
        }

        return numOfCoffee * basePrice;
    }

    private String OrderSumary(int price) {
        String priceMsg;
        priceMsg = " number of coffee " + numOfCoffee;
        priceMsg = priceMsg + "\n Total : $" + price;
        priceMsg = priceMsg + "\n Thank you come back again";

        return priceMsg;
    }

    public void Display(int number) {
        TextView tv = (TextView) findViewById(R.id.Qnty);
        tv.setText(String.valueOf(number));

    }



}
