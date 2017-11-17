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

class AdapterMyOrders extends BaseAdapter {

    private Context mContext;
    private List<ProviderMyOrders> adapterList = new ArrayList<>();
    AdapterMyOrders (Context mContext, ArrayList<ProviderMyOrders> adapterList) {
        this.mContext = mContext;
        this.adapterList = adapterList;
    }

    @Override
    public int getCount() {
        return adapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return adapterList.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_my_orders,parent,false);
            handler = new Handler(convertView);
            convertView.setTag(handler);
        }else{
            handler=(Handler)convertView.getTag();
        }
        ProviderMyOrders letterMenu;
        letterMenu = (ProviderMyOrders)this.getItem(position);
        handler.subTotal.setText(letterMenu.getSubTotal());
        handler.tax.setText(letterMenu.getTax());
        handler.total.setText(letterMenu.getTotal());
        handler.time.setText(letterMenu.getTime());
        handler.fee.setText(letterMenu.getDeliverFee());
        return convertView;
    }
    private static class Handler{
        TextView subTotal;
        TextView tax;
        TextView total;
        TextView fee;
        TextView time;
        Handler(View row){
            subTotal = (TextView) row.findViewById(R.id.tv_orders_sub_total);
            tax = (TextView) row.findViewById(R.id.tv_orders_tax);
            total = (TextView) row.findViewById(R.id.tv_orders_total);
            fee = (TextView) row.findViewById(R.id.tv_orders_fee);
            time = (TextView) row.findViewById(R.id.tv_orders_time);
        }
    }

}
