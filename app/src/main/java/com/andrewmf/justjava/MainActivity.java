package com.andrewmf.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int price = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if(quantity>=100){
            //toast message too many
        } else {
            quantity += 1;
        }
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity -= 1;
        } else {
            // toast message too few

        }
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int cost = calculatePrice(quantity, price);
        boolean whip = hasWhippedCream();
        boolean chocolate = hasChocolate();
        String name = getName();
        String subject = "JustJava order for " + name;
        String summary = createOrderSummary(cost, whip, chocolate, name);
        composeEmail(subject, summary);
    }

    public String createOrderSummary(int cost, boolean whip, boolean chocolate, String name){
        return "Name: " + name +
                "\nAdd whipped cream? " + whip +
                "\nAdd chocolate? " + chocolate +
                "\nQuantity: " + quantity +
                "\nTotal: $" + cost +
                "\nThank you!";
    }

    public void composeEmail( String subject, String summary) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    
    private String getName(){
        EditText text = (EditText) findViewById(R.id.name);
        return text.getText().toString();
    }

    private boolean hasWhippedCream(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.whippedcream);
        if(checkBox.isChecked()){
            return true;
        }
        return false;
    }

    private boolean hasChocolate(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate);
        if(checkBox.isChecked()){
            return true;
        }
        return false;
    }

    private int calculatePrice(int quantity, int price){
        int cost = price;
        if(hasChocolate()){
            cost += 2;
        }
        if(hasWhippedCream()){
            cost ++;
        }
        return cost * quantity;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}