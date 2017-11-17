package myOrders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.chengen.siamclassic.R;

import java.util.ArrayList;

import backups.TinyDB;
import shoppingCart.ProviderShoppingCart;

import static android.view.View.GONE;

public class ActivityMyOrders extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SwipeMenuListView orders;
    ListView ordersDetail;
    ArrayList<ProviderMyOrders> myOrdersArrayList;
    AdapterMyOrders adapterMyOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        orders = (SwipeMenuListView) findViewById(R.id.lv_my_orders);
        ordersDetail = (ListView) findViewById(R.id.lv_orders_detail);
        TinyDB tinyDB = new TinyDB(ActivityMyOrders.this);
        myOrdersArrayList = tinyDB.getListObject2("Orders", ProviderShoppingCart.class);
        if (myOrdersArrayList == null || myOrdersArrayList.size() == 0) {
            orders.setVisibility(GONE);
        } else {
            adapterMyOrders = new AdapterMyOrders(ActivityMyOrders.this, myOrdersArrayList);
            createListSwipeMenu();
            orders.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    myOrdersArrayList.remove(position);
                    adapterMyOrders.notifyDataSetChanged();
                    TinyDB tinyDB = new TinyDB(ActivityMyOrders.this);
                    tinyDB.remove("Orders");
                    tinyDB.putListObject2("Orders", myOrdersArrayList);
                    return false;
                }
            });
            orders.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            orders.setAdapter(adapterMyOrders);
            orders.setOnItemClickListener(this);
        }
    }

    private void createListSwipeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        orders.setMenuCreator(creator);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setUpDetailPanel(myOrdersArrayList.get(position), position);
    }

    private void setUpDetailPanel(ProviderMyOrders myOrder, int index){
        ArrayList<String> itemNames = myOrder.getItemNames();
        ArrayList<String> itemAmounts = myOrder.getItemAmounts();
        ArrayList<String> itemPrices = myOrder.getItemPrices();
        AdapterOrdersDetail adapterOrdersDetail = new AdapterOrdersDetail(ActivityMyOrders.this, itemNames,
                itemAmounts, itemPrices);
        ordersDetail.setAdapter(adapterOrdersDetail);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.orders_detail_footer, null);  // i have open a webview on the listview footer
        TextView sub = (TextView) view.findViewById(R.id.tv_orders_detail_sub_total);
        TextView tax = (TextView) view.findViewById(R.id.tv_orders_detail_tax);
        TextView deliverFee = (TextView) view.findViewById(R.id.tv_orders_detail_deliver_fee);
        TextView total = (TextView) view.findViewById(R.id.tv_orders_detail_total);
        TextView time = (TextView) view.findViewById(R.id.tv_orders_detail_time);
        sub.setText("Sub Total: $"+myOrdersArrayList.get(index).getSubTotal());
        tax.setText("Tax: $"+myOrdersArrayList.get(index).getTax());
        deliverFee.setText("Deliver Fee: $"+myOrdersArrayList.get(index).getDeliverFee());
        total.setText("Total Price: $"+ myOrdersArrayList.get(index).getTotal());
        time.setText(myOrdersArrayList.get(index).getTime());
        ordersDetail.addFooterView(view);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
