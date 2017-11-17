package myOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chengen.siamclassic.R;

import java.util.ArrayList;
import java.util.List;

class AdapterOrdersDetail extends BaseAdapter {

    private Context mContext;
    private List<String> adapterNames = new ArrayList<>();
    private List<String> adapterAmounts = new ArrayList<>();
    private List<String> adapterPrices = new ArrayList<>();
    AdapterOrdersDetail (Context mContext, ArrayList<String> adapterNames,
                         ArrayList<String> adapterAmounts, ArrayList<String> adapterPrices) {
        this.mContext = mContext;
        this.adapterNames = adapterNames;
        this.adapterAmounts = adapterAmounts;
        this.adapterPrices = adapterPrices;
    }

    @Override
    public int getCount() {
        return adapterNames.size();
    }

    @Override
    public Object getItem(int position) {
        return adapterNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Handler handler;
        if(convertView==null){
            final LayoutInflater inflater=(LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_order_detail,parent,false);
            handler = new Handler(convertView);
            convertView.setTag(handler);
        }else{
            handler=(Handler)convertView.getTag();
        }
        handler.itemName.setText(adapterNames.get(position));
        handler.itemAmount.setText(adapterAmounts.get(position));
        handler.itemPrice.setText(adapterPrices.get(position));
        return convertView;
    }
    private static class Handler{
        TextView itemName;
        TextView itemAmount;
        TextView itemPrice;
        Handler(View row){
            itemName = (TextView) row.findViewById(R.id.tv_orders_detail_name);
            itemAmount = (TextView) row.findViewById(R.id.tv_orders_detail_amount);
            itemPrice = (TextView) row.findViewById(R.id.tv_orders_detail_price);
        }
    }

}
