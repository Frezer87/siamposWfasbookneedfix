package com.example.chengen.siamclassic;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import letterMenu.ActivitySubCategories;
import shoppingCart.ActivityShoppingCart;

import static android.view.View.GONE;

public class ActivityOrder extends AppCompatActivity implements View.OnClickListener {
    private String here;
    private TextView foodName;
    private EditText foodSpecial;
    private TextView foodSpicyLevel;
    private TextView foodAmount;
    private TextView foodOptions;
    private TextView foodPrice;
    private String where;
    private String position;
    private String singlePrices;
    private double currentPrice;
    private String options;
    private String imageUrl;
    private String category;
    private String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_order);
        toolbar.setTitle("Order Menu");
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.background_light));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable arrow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_left);
            getSupportActionBar().setHomeAsUpIndicator(arrow);
        }
        Intent intent = getIntent();
        here = intent.getStringExtra("here");
        ArrayList<String> strings = intent.getStringArrayListExtra("itemData");
        setData(strings);
    }

    private void setData(ArrayList<String> strings) {
        foodName = (TextView) findViewById(R.id.tv_order_name);
        foodPrice = (TextView) findViewById(R.id.tv_order_price);
        ImageView foodImage = (ImageView) findViewById(R.id.iv_order);
        foodAmount = (TextView) findViewById(R.id.tv_order_quantity);
        foodAmount.setOnClickListener(this);
        foodSpicyLevel = (TextView) findViewById(R.id.tv_order_spicy_level);
        foodSpicyLevel.setOnClickListener(this);
        foodOptions = (TextView) findViewById(R.id.tv_order_options);
        foodOptions.setOnClickListener(this);
        foodSpecial = (EditText) findViewById(R.id.et_order_special);

        LinearLayout optionLayout = (LinearLayout) findViewById(R.id.ll_options);
        LinearLayout spicyLayout = (LinearLayout) findViewById(R.id.ll_spicy);
        position = -10 + "";
        String totalPrice;
        switch (here) {
            case "ActivityShoppingCart": {
                foodName.setText(strings.get(0));
                totalPrice = strings.get(1);
                foodPrice.setText(totalPrice);
                foodOptions.setText(strings.get(2));
                imageUrl = strings.get(3);
                //Picasso.with(this).load(strings.get(3)).fit().centerCrop().into(foodImage);
                String canChoose = strings.get(4);
                if (canChoose.equals("-1")) {
                    spicyLayout.setVisibility(View.GONE);
                    foodSpicyLevel.setText("-1");
                }
                foodSpicyLevel.setText(strings.get(4));
                foodAmount.setText(strings.get(5));
                foodSpecial.setText(strings.get(6));
                category = strings.get(7);
                singlePrices = strings.get(8);
                options = strings.get(9);
                description = strings.get(10);
                where = strings.get(11);
                position = strings.get(12);

                break;
            }
            case "Menu": {
                foodName.setText(strings.get(0));
                totalPrice = "0.00";
                foodPrice.setText(totalPrice);
                Picasso.with(this).load(strings.get(1)).fit().centerCrop().into(foodImage);
                imageUrl = strings.get(1);
                category = strings.get(2);
                singlePrices = strings.get(3);
                String canChoose = strings.get(4);
                if (canChoose.equals("0")) {
                    spicyLayout.setVisibility(GONE);
                    foodSpicyLevel.setText("-1");
                }
                options = strings.get(5);
                if(!options.contains(",")){
                    optionLayout.setVisibility(GONE);
                    foodOptions.setText("");
                    String[] prices = singlePrices.split(",");
                    foodPrice.setText(prices[0]);
                    foodAmount.setText("1");
                    currentPrice =Double.parseDouble(prices[0]);
                }
                description = strings.get(6);
                where = "ActivitySubCategories";
                break;
            }
            case "Main": {
                foodName.setText(strings.get(0));
                totalPrice = "0.00";
                foodPrice.setText(totalPrice);
                Picasso.with(this).load(strings.get(1)).fit().centerCrop().into(foodImage);
                imageUrl = strings.get(1);
                category = strings.get(2);
                singlePrices = strings.get(3);
                String canChoose = strings.get(4);
                if (canChoose.equals("0")) {
                    spicyLayout.setVisibility(GONE);
                    foodSpicyLevel.setText("-1");
                }
                options = strings.get(5);
                if(!options.contains(",")){
                    optionLayout.setVisibility(GONE);
                    foodOptions.setText("");
                    String[] prices = singlePrices.split(",");
                    foodPrice.setText(prices[0]);
                    foodAmount.setText("1");
                    currentPrice =Double.parseDouble(prices[0]);
                }
                description = strings.get(6);
                where = "ActivitySubCategories";
                break;
            }
        }
        Button confirm = (Button) findViewById(R.id.btn_order_confirm);
        confirm.setOnClickListener(this);
    }


    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                switch (here) {
                    case "ActivityShoppingCart":
                        intent = new Intent(this, ActivityShoppingCart.class);
                        break;
                    case "Menu":
                        intent = new Intent(this, ActivitySubCategories.class);
                        intent.putExtra("category",category);
                        break;
                    default:
                        intent = new Intent(this, ActivitySubCategories.class);
                        intent.putExtra("category",category);
                        break;
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        switch (here) {
            case "ActivityShoppingCart":
                intent = new Intent(this, ActivityShoppingCart.class);
                break;
            case "Menu":
                intent = new Intent(this, ActivitySubCategories.class);
                intent.putExtra("category",category);
                break;
            default:
                intent = new Intent(this, ActivitySubCategories.class);
                intent.putExtra("category",category);
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_order_confirm:
                if (foodOptions.isShown()&&foodOptions.getText().toString().equals("")){
                    Toast.makeText(ActivityOrder.this, "Please Choose the Option.", Toast.LENGTH_LONG).show();
                }else
                if(foodAmount.getText().toString().equals("")){
                    Toast.makeText(ActivityOrder.this, "Please Indicate the Amount of Orders.", Toast.LENGTH_LONG).show();
                }else
                if(foodSpicyLevel.getText().toString().equals("")){
                    Toast.makeText(ActivityOrder.this, "Please Select the Spicy Level.", Toast.LENGTH_LONG).show();
                }else{
                    sendToShoppingCart();
                }
                break;
            case R.id.tv_order_options:
                itemOptionChooser();
                break;
            case R.id.tv_order_quantity:
                itemAmountChooser();
                break;
            case R.id.tv_order_spicy_level:
                itemSpicyLevelChooser();
                break;
            default:
                break;
        }
    }

    private void sendToShoppingCart(){
        Intent intent = new Intent(this, ActivityShoppingCart.class);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(foodName.getText().toString());
        strings.add(imageUrl);
        strings.add(category);
        strings.add(foodPrice.getText().toString());//total price
        strings.add(foodSpicyLevel.getText().toString());
        strings.add(foodOptions.getText().toString());
        strings.add(description);
        strings.add(foodAmount.getText().toString());
        strings.add(foodSpecial.getText().toString());
        strings.add(where);
        strings.add(position+"");
        strings.add(singlePrices);
        strings.add(options);
        intent.putExtra("itemData", strings);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void itemOptionChooser(){
        String[] strings = options.split(",");
        int count = strings.length;
        final ArrayList<ProviderOptions> list = new ArrayList<>();
        for (int i=0; i<count; i++){
            ProviderOptions providerOptions = new ProviderOptions();
            providerOptions.setOptions(strings[i]);
            list.add(providerOptions);
        }
        AdapterDialogOptions adapterDialogOptions = new AdapterDialogOptions(ActivityOrder.this,list);
        final Dialog dialog = new Dialog(ActivityOrder.this);
        dialog.setContentView(R.layout.dialog_options);
        dialog.setTitle("Options");
        ListView listView = (ListView) dialog.findViewById(R.id.lv_options);
        dialog.setCanceledOnTouchOutside(true);
        listView.setAdapter(adapterDialogOptions);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] strings1 = singlePrices.split(",");
                currentPrice = Double.parseDouble(strings1[position]);
                if(foodAmount.getText().toString().equals("")) {
                    foodAmount.setText("1");
                    foodPrice.setText(currentPrice+"");
                }else{
                    double amount = Double.parseDouble(foodAmount.getText().toString());
                    String str = (currentPrice*amount)+"";
                    foodPrice.setText(str);
                }
                dialog.dismiss();
                foodOptions.setText(list.get(position).getOptions());

            }
        });
        dialog.show();
    }

    private void itemAmountChooser(){
        final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(ActivityOrder.this)
                .minValue(1)
                .maxValue(100)
                .defaultValue(1)
                .backgroundColor(Color.WHITE)
                .separatorColor(Color.TRANSPARENT)
                .textColor(Color.BLACK)
                .textSize(20)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build();
        new AlertDialog.Builder(this)
                .setTitle("How many order(s)?")
                .setView(numberPicker)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str;
                        str = numberPicker.getValue()+"";
                        foodAmount.setText(str);
                        String str2 = (currentPrice*numberPicker.getValue())+"";
                        foodPrice.setText(str2);
                    }
                })
                .show();
    }


    private void itemSpicyLevelChooser(){
        final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(ActivityOrder.this)
                .minValue(0)
                .maxValue(10)
                .defaultValue(1)
                .backgroundColor(Color.WHITE)
                .separatorColor(Color.TRANSPARENT)
                .textColor(Color.BLACK)
                .textSize(20)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build();
        new AlertDialog.Builder(this)
                .setTitle("Choose Spicy Level")
                .setView(numberPicker)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str;
                        str = numberPicker.getValue()+"";
                        foodSpicyLevel.setText(str);
                    }
                })
                .show();
    }
}
