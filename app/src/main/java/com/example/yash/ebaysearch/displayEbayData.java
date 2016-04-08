package com.example.yash.ebaysearch;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import java.io.UnsupportedEncodingException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;


public class displayEbayData extends ActionBarActivity {

    /**
     * Created by yash on 4/20/15.
     */
    public static class items {

        int item_id;
        String imageURL;
        String title;
        int price;
        String cost;
        String itemURL;
    }

    String keyword;
    String SetServerString = "";

    CustomAdapter itemsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView errormsg = new TextView(this);


        Intent intent = getIntent();

        keyword = intent.getStringExtra(form.KEYWORDS);
        String pricefrom = intent.getStringExtra(form.PRICEFROM);
        String priceto = intent.getStringExtra(form.PRICETO);
        String sorted = intent.getStringExtra(form.SORTBY);


        try {


            String keywords = URLEncoder.encode(keyword, "UTF-8");
            String pricestart = URLEncoder.encode(pricefrom, "UTF-8");
            String priceend = URLEncoder.encode(priceto, "UTF-8");
            String sortby = URLEncoder.encode(sorted, "UTF-8");

            // Create http client object to send request to server

            HttpClient Client = new DefaultHttpClient();

            // Create URL string

            String URL = "http://cs-server.usc.edu:28169/android_app/index.php?keywords=" + keywords + "&range1=" + pricestart + "&range2=" + priceend +
                    "&sort=" + sortby;

            //Log.i("httpget", URL);

            try {
                //String SetServerString = "";

                // Create Request to server and get response

                Log.d("url", URL);
                HttpGet httpget = new HttpGet(URL);
                //Log.d("httpget",httpget);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                SetServerString = Client.execute(httpget, responseHandler);

                Log.d("response", SetServerString);
                // Show response on activity
                errormsg.setText(SetServerString);

                setContentView(R.layout.activity_display_ebay_data);
                itemsListAdapter = new CustomAdapter();

                ListView myListView = (ListView)findViewById(R.id.listView1);
                myListView.setAdapter(itemsListAdapter);


              /* myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                     @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                        final items item = itemsListAdapter.getItem(arg2);
                        ImageView image = (ImageView)arg1.findViewById(R.id.imageView1);

                         image.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Intent intent = new Intent(Intent.ACTION_VIEW,
                                         Uri.parse());
                                 startActivity(intent);
                             }
                         });


                         Toast.makeText(displayEbayData.this, item.title.toString(), Toast.LENGTH_LONG).show();

                    }
                });
*/

            } catch (Exception ex) {
                //Log.d("error exception",ex.getMessage());
                System.out.println(ex);
                errormsg.setText("Fail exception!");
            }
        } catch (UnsupportedEncodingException ex) {
            errormsg.setText("Fail unsupported encoding");
        }

        // Create the text view
        //TextView textView = new TextView(this);
        //textView.setTextSize(40);
        //textView.setText(message);

        // Set the text view as the activity layout
        //setContentView(errormsg);
        // setContentView(R.layout.activity_display_ebay_data);

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


    class CustomAdapter extends BaseAdapter {
        List<items> itemsList = getDataForListView();

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public items getItem(int position) {
            return itemsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            if (arg1 == null) {
                LayoutInflater inflater = (LayoutInflater) displayEbayData.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                arg1 = inflater.inflate(R.layout.itemlist, arg2, false);
            }

            ImageView imageView = (ImageView)arg1.findViewById(R.id.imageView1);
            TextView title = (TextView)arg1.findViewById(R.id.textView1);
            TextView price = (TextView)arg1.findViewById(R.id.textView2);


            final items item = itemsList.get(arg0);

            //imageView.setImageURI(Uri.parse(item.imageURL));

            //ImageView image = (ImageView) findViewById(R.id.);
            Log.d("imageURL", item.imageURL);

            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 2;
            //Bitmap bMap = BitmapFactory.decodeFile(item.imageURL);
            //imageView.setImageBitmap(bMap);

            // Loader image - will be shown before loading image
            int loader = R.drawable.android_logo;

            // Imageview to show
            //ImageView image = (ImageView) findViewById(R.id.imageView1);

            // Image url
            String image_url = item.imageURL;

            // ImageLoader class instance
            ImageLoader imgLoader = new ImageLoader(getApplicationContext());

            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView
            imgLoader.DisplayImage(image_url, loader, imageView);


            title.setText(item.title);
            price.setText(item.cost);


            //Set on ImageView click
            //ImageView image = (ImageView)arg1.findViewById(R.id.imageView1);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(item.itemURL));
                    startActivity(intent);
                }
            });

            //Set TextView click
            //TextView textView = (ImageView)arg1.findViewById(R.id.imageView1);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(displayEbayData.this,itemDetails.class);

                    intent.putExtra("pictureURL",item.imageURL);
                    startActivity(intent);
                }
            });


            //title.setOnClickListener();
            return arg1;
        }
    }

    public List<items> getDataForListView() {
        JSONObject data = new JSONObject();
        List itemsList = new ArrayList();


        try {
            data = new JSONObject(SetServerString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String itemCount = data.getString("resultCount");
            //String  = data.getString("resultCount") ;

            Log.d("itemCount", itemCount);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 5; i++) {

            String itemid = "item" + i;
            //String item = new String{};
            JSONObject data1 = null;
            try {
                data1 = data.getJSONObject(itemid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject data2 = null;
            try {
                data2 = data1.getJSONObject("basicInfo");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //String city = subObj.getString("city");

            items item = new items();
            item.item_id = i;
            try {
                item.imageURL = data2.getString("galleryURL");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                item.title = data2.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                item.cost = data2.getString("convertedCurrentPrice");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                item.itemURL=data2.getString("viewItemURL");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            itemsList.add(item);
        }

        return itemsList;

    }
}