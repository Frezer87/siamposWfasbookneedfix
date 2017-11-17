package letterMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chengen.siamclassic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterLetterMenu extends BaseAdapter {

    private Context mContext;
    private List<ProviderLetterMenu> adapterList = new ArrayList<>();
   public AdapterLetterMenu(Context mContext, ArrayList<ProviderLetterMenu> adapterList) {
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
            convertView = inflater.inflate(R.layout.adapter_letter_menu,parent,false);
            handler = new Handler(convertView);
            convertView.setTag(handler);
        }else{
            handler=(Handler)convertView.getTag();
        }
        ProviderLetterMenu letterMenu;
        letterMenu = (ProviderLetterMenu)this.getItem(position);
        handler.cuisineName.setText(letterMenu.getName());
        String[] strs = letterMenu.getPrice().split(",");
        String displayString = "";
        for (String str : strs) {
            displayString += "$" + str + "\n";
        }
        handler.cuisinePrice.setText(displayString);
        String chooser = letterMenu.getChooseSpicy();
        if (chooser.equals("0")){
            handler.spicy.setVisibility(View.GONE);
        }
        String[] strs2 = letterMenu.getOptions().split(",");
        displayString = "";
        for (String str2 : strs2) {
            displayString +=  str2 + "\n" ;
        }
        handler.cuisineOptions.setText(displayString);
        handler.cuisineDescriptions.setText(letterMenu.getDescriptions());
        handler.cuisineCategory.setText(letterMenu.getCategory());
        handler.cuisineImageUrl.setText("https://www.dramafever.com/st/img/nowplay/4993_TenMilesOfPeachBlossomsEternalLove_Nowplay_Small.jpg");
        Picasso.with(mContext).load(R.drawable.icon_curry).fit().centerCrop().into(handler.cuisineImage);
        return convertView;
    }
    private static class Handler{
        TextView cuisineName;
        TextView cuisinePrice;
        ImageView spicy;
        ImageView cuisineImage;
        TextView cuisineDescriptions;
        TextView cuisineImageUrl;
        TextView cuisineCategory;
        TextView cuisineOptions;
        Handler(View row){
            cuisineName = (TextView) row.findViewById(R.id.tv_letter_menu_name);
            cuisinePrice = (TextView) row.findViewById(R.id.tv_letter_menu_price);
            spicy = (ImageView) row.findViewById(R.id.iv_letter_menu_spicy);
            cuisineImage = (ImageView) row.findViewById(R.id.iv_letter_menu_image);
            cuisineDescriptions = (TextView) row.findViewById(R.id.tv_letter_menu_description);
            cuisineImageUrl = (TextView) row.findViewById(R.id.tv_letter_menu_image_url);
            cuisineCategory = (TextView) row.findViewById(R.id.tv_letter_menu_category);
            cuisineOptions = (TextView) row.findViewById(R.id.tv_letter_menu_options);
        }
    }

}