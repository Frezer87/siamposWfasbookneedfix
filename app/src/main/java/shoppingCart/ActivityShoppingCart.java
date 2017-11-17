package shoppingCart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.chengen.siamclassic.ActivityOrder;
import com.example.chengen.siamclassic.MainActivity;
import com.example.chengen.siamclassic.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import backups.TinyDB;
import letterMenu.ActivitySubCategories;
import myOrders.ProviderMyOrders;

import static android.view.View.GONE;

public class ActivityShoppingCart extends AppCompatActivity implements SwipeMenuListView.OnMenuItemClickListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private AdapterShoppingCart adapterShoppingCart;
    private ArrayList<ProviderShoppingCart> shoppingList;
    private SwipeMenuListView listView;
    private String where;
    private String category, fullAddress;
    private Double total, tax, all, deliverFee;
    private String chosenTable;
    private Button tableOne;
    private Button tableTwo;
    private Button tableThree;
    private Button tableFour;
    private Button tableFive;
    private Button tableSix;
    private Button tableSeven;
    private Button tableEight;
    private Button tableNine;
    private Button tableTen;
    private Button tableEleven;
    private Button tableTwelve;
    private Button tableThirteen;
    private Button tableFourteen;

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
        setContentView(R.layout.activity_shopping_cart);
        listView = (SwipeMenuListView) findViewById(R.id.lv_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_cart);
        toolbar.setTitle("Shopping Cart");
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.background_light));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable arrow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_left);
            getSupportActionBar().setHomeAsUpIndicator(arrow);
        }
        TinyDB tinyDB = new TinyDB(this);
        shoppingList = tinyDB.getListObject("shoppingList", ProviderShoppingCart.class);
        createListView();
        getDataFromOthers();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_cart);
        FloatingActionButton more = (FloatingActionButton) findViewById(R.id.fab_more);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityShoppingCart.this);
                builder1.setTitle("Confirm Orders");
                builder1.setMessage("Are you sure to make these orders?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                goToVerifyPage();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityShoppingCart.this, MainActivity.class));
                finish();
            }
        });
        chosenTable = "0";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }


    private void goToVerifyPage() {
        final Dialog dialog = new Dialog(ActivityShoppingCart.this);
        dialog.setContentView(R.layout.dialog_verify_submit);
        dialog.setTitle("Enter Your Information");
        final MaterialEditText firstName = (MaterialEditText) dialog.findViewById(R.id.et_verify_first_name);
        final MaterialEditText lastName = (MaterialEditText) dialog.findViewById(R.id.et_verify_last_name);
        final MaterialEditText phoneNum = (MaterialEditText) dialog.findViewById(R.id.et_verify_phone_number);
        final MaterialEditText street = (MaterialEditText) dialog.findViewById(R.id.et_verify_address);
        final MaterialEditText city = (MaterialEditText) dialog.findViewById(R.id.et_verify_city);
        final MaterialEditText state = (MaterialEditText) dialog.findViewById(R.id.et_verify_state);
        final TextView time = (TextView) dialog.findViewById(R.id.tv_prox_time);
        final LinearLayout llDinning = (LinearLayout) dialog.findViewById(R.id.ll_dining_in);
        final LinearLayout llDeliver = (LinearLayout) dialog.findViewById(R.id.ll_deliver);
        Button go = (Button) dialog.findViewById(R.id.btn_verify_go);
        final ToggleButton dinningIn = (ToggleButton) dialog.findViewById(R.id.tb_verify_din_in);
        final ToggleButton pickup = (ToggleButton) dialog.findViewById(R.id.tb_verify_pick);
        final ToggleButton deliver = (ToggleButton) dialog.findViewById(R.id.tb_verify_deliver);

        tableOne = (Button) dialog.findViewById(R.id.table_one);
        tableTwo = (Button) dialog.findViewById(R.id.table_two);
        tableThree = (Button) dialog.findViewById(R.id.table_three);
        tableFour = (Button) dialog.findViewById(R.id.table_four);
        tableFive = (Button) dialog.findViewById(R.id.table_five);
        tableSix = (Button) dialog.findViewById(R.id.table_six);
        tableSeven = (Button) dialog.findViewById(R.id.table_seven);
        tableEight = (Button) dialog.findViewById(R.id.table_eight);
        tableNine = (Button) dialog.findViewById(R.id.table_nine);
        tableTen = (Button) dialog.findViewById(R.id.table_ten);
        tableEleven = (Button) dialog.findViewById(R.id.table_eleven);
        tableTwelve = (Button) dialog.findViewById(R.id.table_twelve);
        tableThirteen = (Button) dialog.findViewById(R.id.table_thirteen);
        tableFourteen = (Button) dialog.findViewById(R.id.table_fourteen);

        //set the table button background to red when is not able and not clickable.
        tableOne.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_red_dark));
        tableOne.setClickable(false);
        //-------------------------------------------------------
        tableOne.setOnClickListener(this);
        tableTwo.setOnClickListener(this);
        tableThree.setOnClickListener(this);
        tableFour.setOnClickListener(this);
        tableFive.setOnClickListener(this);
        tableSix.setOnClickListener(this);
        tableSeven.setOnClickListener(this);
        tableEight.setOnClickListener(this);
        tableNine.setOnClickListener(this);
        tableTen.setOnClickListener(this);
        tableEleven.setOnClickListener(this);
        tableTwelve.setOnClickListener(this);
        tableThirteen.setOnClickListener(this);
        tableFourteen.setOnClickListener(this);
        dinningIn.setChecked(true);
        llDinning.setVisibility(View.VISIBLE);
        dinningIn.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.background_light));

        dinningIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinningIn.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.background_light));
                pickup.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, R.color.bg_gray));
                deliver.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, R.color.bg_gray));
                dinningIn.setChecked(true);
                deliver.setChecked(false);
                pickup.setChecked(false);
                llDeliver.setVisibility(GONE);
                llDinning.setVisibility(View.VISIBLE);
            }
        });

        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinningIn.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, R.color.bg_gray));
                pickup.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.background_light));
                deliver.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, R.color.bg_gray));
                pickup.setChecked(true);
                dinningIn.setChecked(false);
                deliver.setChecked(false);
                llDinning.setVisibility(GONE);
                llDeliver.setVisibility(View.VISIBLE);
                street.setVisibility(GONE);
                city.setVisibility(GONE);
                state.setVisibility(GONE);
                String str = "Your order will be ready in 15 mins";
                time.setText(str);
            }
        });
        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinningIn.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, R.color.bg_gray));
                pickup.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, R.color.bg_gray));
                deliver.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.background_light));
                deliver.setChecked(true);
                dinningIn.setChecked(false);
                pickup.setChecked(false);
                llDinning.setVisibility(GONE);
                llDeliver.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                city.setVisibility(View.VISIBLE);
                state.setVisibility(View.VISIBLE);
                String str = "Your order will be arrive in 45 mins";
                time.setText(str);
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOk = true;
                if (llDeliver.isShown()) {
                    if (firstName.getText().toString().equals("")) {
                        firstName.setError("please enter your first name");
                        isOk = false;
                    }
                    if (lastName.getText().toString().equals("")) {
                        lastName.setError("please enter your last name");
                        isOk = false;
                    }
                    if (phoneNum.getText().toString().equals("")) {
                        phoneNum.setError("please enter your phone number");
                        isOk = false;
                    }
                    if (street.isShown() && street.getText().toString().equals("")) {
                        street.setError("please enter street/apt. name");
                        isOk = false;
                    }
                    if (city.isShown() && city.getText().toString().equals("")) {
                        city.setError("Please enter your city");
                        isOk = false;
                    }
                    if (state.isShown() && state.getText().toString().equals("")) {
                        state.setError("Please enter your state");
                        isOk = false;
                    }

                } else if (llDinning.isShown() && chosenTable.equals("0")) {
                    isOk = false;
                }
                if (isOk) {
                    ArrayList<String> list = new ArrayList<>();
                    if (llDeliver.isShown()) {
                        list.add(firstName.getText().toString());
                        list.add(lastName.getText().toString());
                        list.add(phoneNum.getText().toString());
                        if (deliver.isChecked()) {
                            fullAddress = street.getText().toString() + " ," +
                                    city.getText().toString() + " ," + state.getText().toString();
                            list.add(fullAddress);
                            calculateDeliverFee();
                        } else {
                            list.add("");
                        }
                    } else {
                        list.add(chosenTable);
                    }
                    MyTaskParams taskParams = new MyTaskParams(shoppingList, list);
                    dialog.dismiss();
                    new UpLoadOrders().execute(taskParams);
                }
            }
        });
        dialog.show();
    }

    private void getDataFromOthers() {
        Intent intent = getIntent();
        where = intent.getStringExtra("where");
        category = intent.getStringExtra("category");
        ArrayList<String> strings = intent.getStringArrayListExtra("itemData");
        if (strings != null) {
            ProviderShoppingCart providerShoppingCart = new ProviderShoppingCart();
            providerShoppingCart.setName(strings.get(0));
            providerShoppingCart.setImageUrl(strings.get(1));
            category = strings.get(2);
            providerShoppingCart.setPrice(strings.get(3));
            providerShoppingCart.setSpicy(strings.get(4));
            providerShoppingCart.setOption(strings.get(5));
            providerShoppingCart.setDescription(strings.get(6));
            providerShoppingCart.setAmount(strings.get(7));
            providerShoppingCart.setSpecial(strings.get(8));
            where = strings.get(9);
            if (!strings.get(10).equals("-10")) {
                shoppingList.remove(Integer.parseInt(strings.get(10)));
                shoppingList.add(Integer.parseInt(strings.get(10)), providerShoppingCart);
            } else {
                if (shoppingList.size() == 0) {
                    shoppingList = new ArrayList<>();
                }
                shoppingList.add(providerShoppingCart);
            }
            providerShoppingCart.setSinglePrices(strings.get(11));
            providerShoppingCart.setOptionsList(strings.get(12));
        }
        createListView();
        changePrices();
        adapterShoppingCart.notifyDataSetChanged();
        TinyDB tinyDB = new TinyDB(this);
        tinyDB.remove("shoppingList");
        tinyDB.putListObject("shoppingList", shoppingList);

    }

    private void changePrices(){
        total = 0.0;
        for (int i = 0; i < shoppingList.size(); i++) {
            total += Double.parseDouble(shoppingList.get(i).getPrice());
        }
        tax = total * 0.1;
        all = total + tax;
    }

    private void createListView() {

        if (shoppingList != null) {
            adapterShoppingCart = new AdapterShoppingCart(ActivityShoppingCart.this, shoppingList);
            listView.setAdapter(adapterShoppingCart);
            listView.setOnMenuItemClickListener(this);
            createSwipe();
        }
    }


    private void createSwipe() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth(dp2px(55));
                openItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(openItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProviderShoppingCart data = shoppingList.get(position);
        Intent intent = new Intent(ActivityShoppingCart.this, ActivityOrder.class);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(data.getName());
        strings.add(data.getPrice());//totalPrice
        strings.add(data.getOption());
        strings.add(data.getImageUrl());
        strings.add(data.getSpicy());
        strings.add(data.getAmount());
        strings.add(data.getSpecial());
        strings.add(category);
        strings.add(data.getSinglePrices());
        strings.add(data.getOptionsList());
        strings.add(data.getDescription());
        strings.add(where);
        strings.add(position + "");
        TinyDB tinyDB = new TinyDB(this);
        tinyDB.remove("shoppingList");
        tinyDB.putListObject("shoppingList", shoppingList);
        intent.putExtra("itemData", strings);
        intent.putExtra("here", "ActivityShoppingCart");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        if (index == 0) {
            shoppingList.remove(position);
            adapterShoppingCart.notifyDataSetChanged();
            changePrices();
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.remove("shoppingList");
            tinyDB.putListObject("shoppingList", shoppingList);
        }
        return false;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
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
        System.out.println(where + "------------------------------" + category);
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                switch (where) {
                    case "MainActivity":
                        intent = new Intent(this, MainActivity.class);
                        break;
                    case "SubClasses":
                        intent = new Intent(this, ActivitySubCategories.class);
                        intent.putExtra("category", category);
                        break;
                    default:
                        intent = new Intent(this, MainActivity.class);
                        break;
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                TinyDB tinyDB = new TinyDB(this);
                tinyDB.remove("shoppingList");
                tinyDB.putListObject("shoppingList", shoppingList);
                finish();
                return true;
            case R.id.action_money:
                if (shoppingList.size() != 0) {
                    final Dialog dialog = new Dialog(ActivityShoppingCart.this);
                    dialog.setContentView(R.layout.dialog_prices);
                    dialog.setTitle("Receipt");
                    TextView subPrice = (TextView) dialog.findViewById(R.id.tv_sub_price);
                    TextView tax = (TextView) dialog.findViewById(R.id.tv_tax);
                    TextView deliverFee = (TextView) dialog.findViewById(R.id.tv_deliver_fee);
                    TextView totalPrice = (TextView) dialog.findViewById(R.id.tv_total_fee);
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    System.out.println(total + "---------------------------------------");
                    String totalString = formatter.format(total);
                    String taxString = formatter.format(this.tax);
                    String allString = formatter.format(all);
                    subPrice.setText(totalString);
                    tax.setText(taxString);
                    String str = "$1/mile";
                    deliverFee.setText(str);
                    totalPrice.setText(allString);
                    dialog.show();
                } else {
                    Toast.makeText(ActivityShoppingCart.this,
                            "You haven't add anything to your shopping cart.", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        switch (where) {
            case "MainActivity":
                intent = new Intent(this, MainActivity.class);
                break;
            case "SubClasses":
                intent = new Intent(this, ActivitySubCategories.class);
                intent.putExtra("category", category);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        TinyDB tinyDB = new TinyDB(this);
        tinyDB.remove("shoppingList");
        tinyDB.putListObject("shoppingList", shoppingList);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.table_one:
                chosenTable = "1";
                tableOne.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_two:
                chosenTable = "2";
                tableTwo.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_three:
                chosenTable = "3";
                tableThree.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_four:
                chosenTable = "4";
                tableFour.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_five:
                chosenTable = "5";
                tableFive.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_six:
                chosenTable = "6";
                tableSix.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_seven:
                chosenTable = "7";
                tableSeven.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_eight:
                chosenTable = "8";
                tableEight.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_nine:
                chosenTable = "9";
                tableNine.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_ten:
                chosenTable = "10";
                tableTen.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_eleven:
                chosenTable = "11";
                tableEleven.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_twelve:
                chosenTable = "12";
                tableTwelve.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_thirteen:
                chosenTable = "13";
                tableThirteen.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            case R.id.table_fourteen:
                chosenTable = "14";
                tableFourteen.setBackgroundColor(ContextCompat.getColor(ActivityShoppingCart.this, android.R.color.holo_orange_light));
                break;
            default:
                chosenTable = "0";
                break;
        }
    }

    private static class MyTaskParams {
        ArrayList<ProviderShoppingCart> shoppingList;
        ArrayList<String> customerInfo;

        MyTaskParams(ArrayList<ProviderShoppingCart> shoppingList, ArrayList<String> customerInfo) {
            this.shoppingList = shoppingList;
            this.customerInfo = customerInfo;
        }
    }

    private class UpLoadOrders extends AsyncTask<MyTaskParams, Void, Void> {
        private ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(MyTaskParams... params) {
            ArrayList<ProviderShoppingCart> shoppingList = params[0].shoppingList;
            ArrayList<String> customerInfo = params[0].customerInfo; //if info length equals to 1 that mean it is dining in

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ActivityShoppingCart.this, "", "Uploading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            ProviderMyOrders orders = new ProviderMyOrders();
            TinyDB tinyDB = new TinyDB(ActivityShoppingCart.this);
            ArrayList<ProviderMyOrders> list = tinyDB.getListObject2("Orders", ProviderMyOrders.class);
            if (list == null) {
                list = new ArrayList<>();
            }
            ArrayList<String> itemNames = new ArrayList<>();
            ArrayList<String> itemAmounts = new ArrayList<>();
            ArrayList<String> itemPrices = new ArrayList<>();
            orders.setSubTotal(total + "");
            orders.setTax(tax + "");
            int fee = (int) Math.round(deliverFee);
            orders.setDeliverFee(fee + "");
            orders.setTotal(all + "");
            orders.setTime(getCurrentTimeStamp());
            for (int i = 0; i < shoppingList.size(); i++) {
                itemNames.add(shoppingList.get(i).getName());
                itemAmounts.add(shoppingList.get(i).getAmount());
                itemPrices.add(shoppingList.get(i).getPrice());
            }
            orders.setItemNames(itemNames);
            orders.setItemAmounts(itemAmounts);
            orders.setItemPrices(itemPrices);
            list.add(orders);
            tinyDB.remove("Orders");
            tinyDB.putListObject2("Orders", list);
            final Dialog dialog = new Dialog(ActivityShoppingCart.this);
            dialog.setContentView(R.layout.dialog_success);
            dialog.setCancelable(false);
            TextView home = (TextView) dialog.findViewById(R.id.tv_success_home);
            TextView subscribe = (TextView) dialog.findViewById(R.id.tv_success_sub);
            TextView call = (TextView) dialog.findViewById(R.id.tv_cancel_order);
            Linkify.addLinks(call, Linkify.PHONE_NUMBERS);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startActivity(new Intent(ActivityShoppingCart.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    TinyDB tinyDB = new TinyDB(ActivityShoppingCart.this);
                    tinyDB.remove("shoppingList");
                    finish();
                }
            });
            subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    TinyDB tinyDB = new TinyDB(ActivityShoppingCart.this);
                    tinyDB.remove("shoppingList");
                    startActivity(new Intent(ActivityShoppingCart.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    Uri uriUrl = Uri.parse("https://www.yelp.com/biz/siam-classic-manassas");
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
            dialog.show();
            //else show the toast to info the user the order hasn't upload successfully.
            Toast.makeText(ActivityShoppingCart.this, "Cannot Upload, Try it Later", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private void calculateDeliverFee() {
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                String point = getLatLong(getLocationInfo(fullAddress));
                if (point != null) {
                    String[] pins = point.split(",");
                    double lat1 = Double.parseDouble(pins[0]);
                    double lon1 = Double.parseDouble(pins[1]);
                    Location loc1 = new Location("");
                    loc1.setLatitude(lat1);
                    loc1.setLongitude(lon1);

                    Location loc2 = new Location("");
                    loc2.setLatitude(38.7517314);
                    loc2.setLongitude(-77.4727505);
                    float distanceInMeters = loc1.distanceTo(loc2);
                    deliverFee = 0.621371 * (distanceInMeters / 1000);

                } else {
                    deliverFee = 0.0;
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            address = address.replaceAll(" ", "%20");

            HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();

            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    private String getLatLong(JSONObject jsonObject) {
        Double longitude, latitude;
        try {

            longitude = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            latitude = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (JSONException e) {
            return null;

        }

        return latitude + "," + longitude;
    }
}
