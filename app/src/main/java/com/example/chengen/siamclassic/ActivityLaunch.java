package com.example.chengen.siamclassic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.chengen.siamclassic.Account.ActivityCustomerSignIn;
import com.example.chengen.siamclassic.Account.ActivityCustomerSignUp;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
//import com.firebase.ui.auth.AuthUI;
//import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

import backups.TinyDB;
import databaseConnection.DatabaseConnection;
import dmax.dialog.SpotsDialog;
import letterMenu.ProviderLetterMenu;

public class ActivityLaunch extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_launch);
        TextView signIn = (TextView) findViewById(R.id.tv_launch_sign_in);
        TextView signUp = (TextView) findViewById(R.id.tv_launch_sign_up);
        TextView skip = (TextView) findViewById(R.id.tv_launch_skip);
        skip.setOnClickListener(this);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        DatabaseConnection.databaseConnectionSetup();

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
    public class LoginFacebook extends AppCompatActivity {
        LoginButton loginButton;
        TextView textView;
        CallbackManager callbackManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FacebookSdk.sdkInitialize(getApplicationContext());
            setContentView(R.layout.activity_launch);
            loginButton = (LoginButton)findViewById(R.id.login_button);
            textView = (TextView)findViewById(R.id.textView);
            callbackManager = CallbackManager.Factory.create();
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    textView.setText("Log in sucsses \n"+ loginResult.getAccessToken()
                    .getToken());

                }

                @Override
                public void onCancel() {
                    textView.setText(" Login Canseld");

                }

                @Override
                public void onError(FacebookException error) {

                }
            });



        }
        @Override
        protected void onActivityResult (int requestCode, int resultCode, Intent data) {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }


    }



            @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_launch_sign_in:
                startActivity(new Intent(ActivityLaunch.this, ActivityCustomerSignIn.class));
                finish();
                break;
            case R.id.tv_launch_sign_up:
                startActivity(new Intent(ActivityLaunch.this, ActivityCustomerSignUp.class)
                        .putExtra("page","Launch"));
                finish();
                break;
            case R.id.tv_launch_skip:
                new UpdateItemsFromDB().execute();
            default:
                break;
        }
    }

    private class UpdateItemsFromDB extends AsyncTask<Void, Void, Void>{
        AlertDialog dialog;
        String[] items = {"APPETIZERS","SOUP","SALAD","NOODLES","FRIED RICE",
                "ENTREES","HOUSE SUGGESTIONS","VEGETARIAN","CURRY","DESSERTS",
                "ICECRM","LUNCH SPECIAL SOUP","LUNCH SPECIAL SIDES","HOUSE WINE",
                "FEATURED WINE","SPECIAL COLLECTION","MIXED DRINKS","BEER","SAKE",
                "SAUCE","EXTRA","GIFT CARDS","SODA","COFE N TEA","ESPRESSO","JUICE N MLK","SPECIAL OFFERS"};
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new SpotsDialog(ActivityLaunch.this, R.style.Custom);
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
            startActivity(new Intent(ActivityLaunch.this, MainActivity.class));
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
        TinyDB tinyDB = new TinyDB(ActivityLaunch.this);
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
