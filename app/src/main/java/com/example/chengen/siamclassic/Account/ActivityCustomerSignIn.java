package com.example.chengen.siamclassic.Account;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chengen.siamclassic.ActivityLaunch;
import com.example.chengen.siamclassic.MainActivity;
import com.example.chengen.siamclassic.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

import backups.TinyDB;
import databaseConnection.DatabaseConnection;
import dmax.dialog.SpotsDialog;
import letterMenu.ProviderLetterMenu;

public class ActivityCustomerSignIn extends AppCompatActivity implements View.OnClickListener {
    private MaterialEditText username;
    private MaterialEditText password;
    private Button login;
    private TextView goToRegister;
    private TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custtomer_sign_in);
        username = (MaterialEditText) findViewById(R.id.et_login_username);
        password = (MaterialEditText) findViewById(R.id.et_login_password);
        username.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
        password.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
        login = (Button) findViewById(R.id.btn_login);
        goToRegister = (TextView) findViewById(R.id.tv_login_new);
        skip = (TextView) findViewById(R.id.tv_login_skip);
        login.setOnClickListener(this);
        goToRegister.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                loginProcess();
                break;
            case R.id.tv_login_new:
                startActivity(new Intent(ActivityCustomerSignIn.this, ActivityCustomerSignUp.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("page","SignIn"));
                finish();
                break;
            case R.id.tv_login_skip:
                startActivity(new Intent(ActivityCustomerSignIn.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }
    }

    private void loginProcess(){
        if(username.getText().toString().equals("")) {
            username.setError("please enter your username");
        }
        else
        if(password.getText().toString().equals("")){
            password.setError("please enter your password");
        }else{
            //sign in  starts.
            new UpdateItemsFromDB().execute();

        }
    }

    private class UpdateItemsFromDB extends AsyncTask<Void, Void, Void> {
        AlertDialog dialog;
        String[] items = {"APPETIZERS","SOUP","SALAD","NOODLES","FRIED RICE",
                "ENTREES","HOUSE SUGGESTIONS","VEGETARIAN","CURRY","DESSERTS",
                "ICECRM","LUNCH SPECIAL SOUP","LUNCH SPECIAL SIDES","HOUSE WINE",
                "FEATURED WINE","SPECIAL COLLECTION","MIXED DRINKS","BEER","SAKE",
                "SAUCE","EXTRA","GIFT CARDS","SODA","COFE N TEA","ESPRESSO","JUICE N MLK","SPECIAL OFFERS"};
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new SpotsDialog(ActivityCustomerSignIn.this, R.style.Custom);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for(int i =0; i<items.length; i++){
                getItemForDB(items[i]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(ActivityCustomerSignIn.this, MainActivity.class));
            dialog.dismiss();
            finish();
        }
    }
    private void getItemForDB(String key){
        ArrayList<String> menuItemName = (ArrayList) DatabaseConnection.getMenuItemName().clone();
        ArrayList<String> menuItemPrice = (ArrayList) DatabaseConnection.getMenuItemPrice().clone();
        ArrayList<String> menuItemGroupID = (ArrayList) DatabaseConnection.getMenuItemGroupID().clone();
        ArrayList<String> menuItemDescription = (ArrayList) DatabaseConnection.getMenuItemDescription().clone();

        ArrayList<String> menuGroupID = (ArrayList) DatabaseConnection.getMenuGroupID().clone();
        ArrayList<String> menuGroupName = (ArrayList) DatabaseConnection.getMenuGroupName().clone();
        NumberFormat formatter = new DecimalFormat("#0.00");

        ArrayList<ProviderLetterMenu> providerLetterMenus = new ArrayList<>();

        int categoryID = menuGroupName.indexOf(key);
        if (categoryID == -1) {
            for (int i = 0; i < 10; i++) {
                ProviderLetterMenu providerLetterMenu = new ProviderLetterMenu();
                providerLetterMenu.setPrice("3.97,8.90,7.09");
                providerLetterMenu.setName("Thai Foods");
                providerLetterMenu.setImageUrl("http://static.asiawebdirect.com/m/phuket/portals/phuket-com/homepage/cuisine/toptenfood/allParagraphs/0/top10Set/00/image/padthai.jpg");
                providerLetterMenu.setCategory("Entrees");
                providerLetterMenu.setChooseSpicy("1");
                providerLetterMenu.setOptions("Pork,Beef,Chicken");
                providerLetterMenu.setDescriptions("Lightly grilled dumplings filled with mixed vegetables. Served with sweet soy vinaigrette");
                //-----------------------------------------------------------------------------------
                providerLetterMenus.add(providerLetterMenu);
            }
        } else {
            for (int i = 0; i < menuItemGroupID.size(); i++) {
                if (Objects.equals(menuItemGroupID.get(i), menuGroupID.get(categoryID))) {
                    ProviderLetterMenu providerLetterMenu = new ProviderLetterMenu();
                    providerLetterMenu.setPrice(formatter.format(Double.valueOf(menuItemPrice.get(i))));
                    providerLetterMenu.setName(UppercaseFirstLetters(menuItemName.get(i).toLowerCase()));
                    providerLetterMenu.setImageUrl("http://static.asiawebdirect.com/m/bangkok/portals/bangkok-com/homepage/food-top10/allParagraphs/01/top10Set/04/image/khao-pad.jpg");
                    providerLetterMenu.setCategory(key);
                    providerLetterMenu.setChooseSpicy("1");
                    providerLetterMenu.setOptions("Pork,Beef,Chicken");
                    providerLetterMenu.setDescriptions(menuItemDescription.get(i));
                    //-----------------------------------------------------------------------------------
                    providerLetterMenus.add(providerLetterMenu);
                }
            }
        }
        TinyDB tinyDB = new TinyDB(ActivityCustomerSignIn.this);
        tinyDB.remove("LIST_"+key);
        tinyDB.putListObject3("LIST_"+key, providerLetterMenus);
    }
    private  String UppercaseFirstLetters(String str)
    {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }
}
