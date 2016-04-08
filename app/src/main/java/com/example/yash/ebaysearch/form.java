package com.example.yash.ebaysearch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;
import android.util.Log;

import android.content.Intent;


public class form extends ActionBarActivity {
    public EditText keyword;
    public final static String KEYWORDS = "com.mycompany.myfirstapp.KEYWORDS";
    public final static String PRICEFROM = "com.mycompany.myfirstapp.PRICEFROM";
    public final static String PRICETO = "com.mycompany.myfirstapp.PRICETO";
    public final static String SORTBY = "com.mycompany.myfirstapp.SORTBY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Spinner spinner = (Spinner) findViewById(R.id.sortby);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortby_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds com.example.yash.ebaysearch.form.items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearData(View view) {
        EditText keyword = (EditText) findViewById(R.id.keyword);
        keyword.setText("");

        EditText pricefrom = (EditText) findViewById(R.id.pricefrom);
        pricefrom.setText("");

        EditText priceto = (EditText) findViewById(R.id.priceto);
        priceto.setText("");

        Spinner sortby = (Spinner) findViewById(R.id.sortby);
        sortby.setSelection(0);
    }

    public void verify(View view) {
        EditText keyword = (EditText) findViewById(R.id.keyword);
        EditText pricefrom = (EditText) findViewById(R.id.pricefrom);
        EditText priceto = (EditText) findViewById(R.id.priceto);

        TextView errormsg = (TextView) findViewById(R.id.errormsg);

        Log.d("msg", "in verify");

        int keyflag = 0;
        int pricetoflag = 0;
        int pricefromflag = 0;
        int comparisonflag = 0;


        String searchstring = keyword.getText().toString();
        if (TextUtils.isEmpty(keyword.getText())) {

            errormsg.setText("Please enter a keyword");
            //Toast toast=Toast.makeText(this,keyword.getText().toString(),Toast.LENGTH_LONG);
            //toast.show();
            keyflag = 1;
        } else {
            //Toast toast=Toast.makeText(this,keyword.getText().toString(),Toast.LENGTH_LONG);
            //toast.show();
            errormsg.setText("");
            keyflag = 0;

        }

        String text = pricefrom.getText().toString();
        if(!TextUtils.isEmpty(pricefrom.getText())) {
            try {
                pricefromflag = 0;
                int num = Integer.parseInt(text);
                Log.d("price from ", num + " is a number");
            }
            catch (NumberFormatException e) {
                pricefromflag = 1;
                errormsg.setText("Please enter a valid price from");
                Log.d("price to ", text + " is not a number");
            }
        }
        else
        {
            pricefromflag=2;
        }


        String text1 = priceto.getText().toString();
        if(!TextUtils.isEmpty(priceto.getText())) {
            try {
                pricetoflag = 0;
                int num1 = Integer.parseInt(text1);
                Log.d("", num1 + " is a number");
            }
            catch (NumberFormatException e) {
                pricetoflag = 1;
                errormsg.setText("Please enter a valid price to");
                Log.d("", text1 + " is not a number");

            }
        }
        else
        {
            pricetoflag=2;
        }

        if (pricefromflag == 0 && pricetoflag == 0) {
            int startprice = Integer.parseInt(text);
            int endprice = Integer.parseInt(text1);

            if (startprice > endprice) {
                comparisonflag = 1;
                errormsg.setText("Please enter price from less than price to");
            } else
                comparisonflag = 0;
        }
            if (keyflag == 0 && pricefromflag != 1 && pricetoflag != 1 && comparisonflag == 0)
                submitdata();

    }

    public void submitdata() {
        Log.d("msg", "in submitdata");

        EditText keyword = (EditText) findViewById(R.id.keyword);
        EditText pricefrom = (EditText) findViewById(R.id.pricefrom);
        EditText priceto = (EditText) findViewById(R.id.priceto);
        TextView errormsg = (TextView) findViewById(R.id.errormsg);

        Spinner sortedby = (Spinner)findViewById(R.id.sortby);



        //try {

            // URLEncode user defined data

            String keywords = keyword.getText().toString();
            String pricestart = pricefrom.getText().toString();
            String priceend = priceto.getText().toString();
            String sortby= sortedby.getSelectedItem().toString();


            //Start a new activity

            Intent intent = new Intent(this,displayEbayData.class);
            intent.putExtra(KEYWORDS,keywords);
            intent.putExtra(PRICEFROM,pricestart);
            intent.putExtra(PRICETO,priceend);
            intent.putExtra(SORTBY,sortby);

            startActivity(intent);


    }


}





