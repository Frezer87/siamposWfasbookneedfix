package shoppingCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chengen.siamclassic.R;

import java.util.ArrayList;
import java.util.List;

class AdapterShoppingCart extends BaseAdapter {

    private Context mContext;
    private List<ProviderShoppingCart> adapterList = new ArrayList<>();
    AdapterShoppingCart (Context mContext, ArrayList<ProviderShoppingCart> adapterList) {
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
            convertView = inflater.inflate(R.layout.adapter_shopping_cart,parent,false);
            handler = new Handler(convertView);
            convertView.setTag(handler);
        }else{
            handler=(Handler)convertView.getTag();
        }
        ProviderShoppingCart letterMenu;
        String str;
        letterMenu = (ProviderShoppingCart)this.getItem(position);
        handler.cuisineName.setText(letterMenu.getName());
        str = "$"+ letterMenu.getPrice();
        handler.cuisinePrice.setText(str);
        str = "Order(s): "+ letterMenu.getAmount();
        handler.cuisineAmount.setText(str);
        handler.cuisineSpicy.setText(letterMenu.getSpicy());
        handler.cuisineOption.setText(letterMenu.getOption());
        handler.cuisineSpecial.setText(letterMenu.getSpecial());
        //Picasso.with(mContext).load(letterMenu.getImageUrl()).fit().into(handler.cuisineImage);
        str = "Spicy Lv. "+letterMenu.getSpicy();
        handler.cuisineSpicy.setText(str);
        handler.cuisineOption.setText(letterMenu.getOption());
        return convertView;
    }
    private static class Handler{
        TextView cuisineName;
        TextView cuisinePrice;
        ImageView cuisineImage;
        TextView cuisineSpecial;
        TextView cuisineAmount;
        TextView cuisineOption;
        TextView cuisineSpicy;
        Handler(View row){
            cuisineName = (TextView) row.findViewById(R.id.tv_cart_name);
            cuisinePrice = (TextView) row.findViewById(R.id.tv_cart_price);
            cuisineImage = (ImageView) row.findViewById(R.id.iv_letter_menu_spicy);
            cuisineAmount = (TextView) row.findViewById(R.id.tv_cart_amount);
            cuisineSpecial = (TextView) row.findViewById(R.id.tv_cart_special);
            cuisineOption = (TextView) row.findViewById(R.id.tv_cart_option);
            cuisineSpicy = (TextView) row.findViewById(R.id.tv_cart_spicy);
        }
    }

}
