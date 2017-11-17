package com.example.chengen.siamclassic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.chengen.siamclassic.R.id.lowest_price;


class RecomAdapter extends RecyclerView.Adapter<RecomAdapter.MyViewHolder> {
    private Context mContext;
    private java.util.List<CardProvider> List;
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView image;
        private TextView imageUrl;
        private TextView category;
        private TextView lowestPrice;
        private TextView price;
        private TextView description;
        private TextView chooseSpicy;
        private ImageView spicyLevel;
        private TextView options;
        private ImageButton detail;

        MyViewHolder(View view) {
            super(view);
            CardView cardView = (CardView) view.findViewById(R.id.card_view);
            cardView.setCardElevation(1);
            title = (TextView) view.findViewById(R.id.title);
            image = (ImageView) view.findViewById(R.id.card_image);
            category = (TextView) view.findViewById(R.id.category);
            lowestPrice = (TextView) view.findViewById(lowest_price);
            price = (TextView) view.findViewById(R.id.price);
            description = (TextView) view.findViewById(R.id.descriptions);
            chooseSpicy = (TextView) view.findViewById(R.id.spicy_lv);
            spicyLevel = (ImageView) view.findViewById(R.id.iv_spicy_level);
            imageUrl = (TextView) view.findViewById(R.id.image_url);
            options = (TextView) view.findViewById(R.id.options);
            detail = (ImageButton) view.findViewById(R.id.ib_show_detail);
            image.setOnClickListener(this);
            title.setOnClickListener(this);
            detail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_show_detail:
                    openDetailDialog();
                    break;
                case R.id.title:
                    sendDataToOrder();
                    break;
                case R.id.card_image:
                    sendDataToOrder();
                    break;
            }
        }

        private void openDetailDialog(){
            final Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.dialog_recom);
            dialog.setTitle(title.getText().toString());
            ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.ib_recom_dialog_close);
            TextView description = (TextView) dialog.findViewById(R.id.tv_recom_dialog_description);
            description.setText(this.description.getText().toString());
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        private void sendDataToOrder(){
            Intent intent = new Intent(mContext, ActivityOrder.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ArrayList<String> strings = new ArrayList<>();
            strings.add(title.getText().toString());
            strings.add(imageUrl.getText().toString());
            strings.add(category.getText().toString());
            strings.add(price.getText().toString());
            strings.add(chooseSpicy.getText().toString());
            strings.add(options.getText().toString());
            strings.add(description.getText().toString());
            intent.putExtra("itemData",strings);
            intent.putExtra("here", "Main");
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
        }
    }
    RecomAdapter(Context mContext, List<CardProvider> List) {
        this.mContext = mContext;
        this.List = List;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upload_cards, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CardProvider cards = List.get(position);
        holder.title.setText(cards.getName());
        holder.category.setText(cards.getCategory());
        Picasso.with(mContext).load(cards.getImage())
                .fit().centerCrop().into(holder.image);
        holder.imageUrl.setText(cards.getImage());
        String singlePrice = cards.getLowestPrice();
        holder.lowestPrice.setText(singlePrice);
        holder.price.setText(cards.getPrice());
        holder.description.setText(cards.getDescription());
        holder.chooseSpicy.setText(cards.getChooseSpicy());
        holder.options.setText(cards.getOptions());
        if(cards.getChooseSpicy().equals("0")){
            holder.spicyLevel.setVisibility(View.INVISIBLE);
        } else{
            Picasso.with(mContext).load(cards.getSpicyLevel())
                    .fit().centerCrop().into(holder.spicyLevel);
        }
    }
    @Override
    public int getItemCount() {
        return List.size();
    }

}
