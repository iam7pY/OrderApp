/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChoclate, String name) {
        String priceMessage = getString(R.string.naame) + name;
        priceMessage += "\n"+getString(R.string.whip) + addWhippedCream;
        priceMessage += "\n"+getString(R.string.addchocolate) + addChoclate;
        priceMessage = priceMessage + "\n"+getString(R.string.quantity) + quantity;
        priceMessage = priceMessage + "\n"+getString(R.string.total)+ price;
        priceMessage += getString(R.string.thank_you);
        return priceMessage;
    }

    public void submitOrder(View view) {
        if (quantity == 0) {


            Toast.makeText(getApplicationContext(), "Sorry,But you cannot order less than 1 cup of coffee",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Order Placed",
                    Toast.LENGTH_SHORT).show();
        }
        EditText text = (EditText) findViewById(R.id.name_edit_text);
        CheckBox ChoclateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        CheckBox WhippedcreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_text_view);
        boolean hasWhippedCream = WhippedcreamCheckBox.isChecked();
        String name = text.getText().toString();
        boolean hasChoclate = ChoclateCheckBox.isChecked();
        display(quantity);
        int price = calculatePrice(hasWhippedCream, hasChoclate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChoclate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        String[] to={"cafe_emailid@admin.com"};
        intent.putExtra(Intent.EXTRA_EMAIL,to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }


    public void reset(View view) {
        quantity = 0;
        display(quantity);
        CheckBox ChoclateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        CheckBox WhippedcreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_text_view);
       ChoclateCheckBox.setChecked(false);
        WhippedcreamCheckBox.setChecked(false);
        EditText text = (EditText) findViewById(R.id.name_edit_text);
        text.setText("");
    }


        /**
     * This method displays the given quantity value on the screen.
     */
    public void increment(View view) {
        quantity++;
        display(quantity);
    }

    public int calculatePrice(boolean hasWhipped, boolean hasChoco) {
        int price = quantity * 50;
        if (hasWhipped == true && hasChoco == true) {
            price += quantity * 3;
        } else if (hasWhipped == true) {
            price = quantity * 1;
        } else if (hasChoco == true) {
            price += quantity * 2;
        }
        return price;

    }


    public void decrement(View view) {
        quantity--;
        if (quantity <= 0) {
            quantity = 0;
        }
        display(quantity);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the given price on the screen.
     */


}