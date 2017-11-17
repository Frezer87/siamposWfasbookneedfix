package com.example.chengen.siamclassic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class AdapterDialogOptions extends BaseAdapter {

    private Context mContext;
    private List<ProviderOptions> options = new ArrayList<>();
    AdapterDialogOptions(Context mContext, ArrayList<ProviderOptions> options) {
        this.mContext = mContext;
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_dialog_options,parent,false);
            handler = new Handler(convertView);
            convertView.setTag(handler);
        }else{
            handler=(Handler)convertView.getTag();
        }
        ProviderOptions letterMenu;
        letterMenu = (ProviderOptions)this.getItem(position);
        handler.cuisineOptions.setText(letterMenu.getOptions());
        return convertView;
    }
    private static class Handler{
        TextView cuisineOptions;
        Handler(View row){
            cuisineOptions = (TextView) row.findViewById(R.id.tv_dialog_option_name);

        }
    }

}
