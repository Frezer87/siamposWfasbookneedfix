package com.example.chengen.siamclassic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yyydjk.library.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import backups.PicassoImageLoader;
import backups.TinyDB;
import de.hdodenhof.circleimageview.CircleImageView;
import letterMenu.ActivitySubCategories;
import letterMenu.ProviderLetterMenu;
import shoppingCart.ActivityShoppingCart;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private List<CardProvider> listOfRecom;
    private RecomAdapter recomAdapter;
    private boolean doubleBackToExitPressedOnce = false;

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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityShoppingCart.class)
                        .putExtra("where","MainActivity").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setBanner();
        setImageButtons();
        prepareRecommend();
        prepareRecommendCards();
    }

    private void setBanner() {
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner);
        final List<String> urls = new ArrayList<>();
        urls.add("https://www.dramafever.com/st/img/nowplay/4993_TenMilesOfPeachBlossomsEternalLove_Nowplay_Small.jpg");
        urls.add("https://2.bp.blogspot.com/-sjGOyn23nmE/WKpO4sBy1yI/AAAAAAAAEB8/ixHPWIV-ZB44x7qBW1k85K04iAek0LupACLcB/s1600/three-lives-three-worlds-ten-miles-of-peach-blossoms-chinese-2017.jpg");
        urls.add("https://2.bp.blogspot.com/-B3TAKVamlfg/Vy2iA7flAWI/AAAAAAAAC9U/r8uZsJiJXIc_rN1oHUJMI8Oly1xn8oH_ACLcB/s640/Three%2BLives%2BThree%2BWorlds%2B20.jpg");
        bannerLayout.setImageLoader(new PicassoImageLoader());
        bannerLayout.setViewUrls(urls);
    }

    private void setImageButtons() {
        CircleImageView appetizer = (CircleImageView) findViewById(R.id.ib_main_appetizer);
        CircleImageView soup = (CircleImageView) findViewById(R.id.ib_main_soup);
        CircleImageView noodles = (CircleImageView) findViewById(R.id.ib_main_noodle);
        CircleImageView entree = (CircleImageView) findViewById(R.id.ib_main_entree);
        CircleImageView house = (CircleImageView) findViewById(R.id.ib_main_house);
        CircleImageView veg = (CircleImageView) findViewById(R.id.ib_main_vegetarian);
        CircleImageView curry = (CircleImageView) findViewById(R.id.ib_main_curry);
        CircleImageView dessert = (CircleImageView) findViewById(R.id.ib_main_dessert);
        CircleImageView drink = (CircleImageView) findViewById(R.id.ib_main_drink);
        CircleImageView wine = (CircleImageView) findViewById(R.id.ib_main_wine);
        CircleImageView lunchSides = (CircleImageView) findViewById(R.id.ib_main_lunch_sides);
        CircleImageView misc = (CircleImageView) findViewById(R.id.ib_main_others);
        Picasso.with(MainActivity.this).load(R.drawable.icon_appetizers).fit().centerCrop().into(appetizer);
        Picasso.with(MainActivity.this).load(R.drawable.icon_soups).fit().centerCrop().into(soup);
        Picasso.with(MainActivity.this).load(R.drawable.icon_noodles).fit().centerCrop().into(noodles);
        Picasso.with(MainActivity.this).load(R.drawable.icon_entree).fit().centerCrop().into(entree);
        Picasso.with(MainActivity.this).load(R.drawable.icon_home).fit().centerCrop().into(house);
        Picasso.with(MainActivity.this).load(R.drawable.icon_veg).fit().centerCrop().into(veg);
        Picasso.with(MainActivity.this).load(R.drawable.icon_curry).fit().centerCrop().into(curry);
        Picasso.with(MainActivity.this).load(R.drawable.icon_dessert).fit().centerCrop().into(dessert);
        Picasso.with(MainActivity.this).load(R.drawable.icon_drinks).fit().centerCrop().into(drink);
        Picasso.with(MainActivity.this).load(R.drawable.icon_wines).fit().centerCrop().into(wine);
        Picasso.with(MainActivity.this).load(R.drawable.icon_lunch_sides).fit().centerCrop().into(lunchSides);
        Picasso.with(MainActivity.this).load(R.drawable.icon_misc).fit().centerCrop().into(misc);
        appetizer.setOnClickListener(this);
        soup.setOnClickListener(this);
        noodles.setOnClickListener(this);
        entree.setOnClickListener(this);
        house.setOnClickListener(this);
        veg.setOnClickListener(this);
        curry.setOnClickListener(this);
        dessert.setOnClickListener(this);
        drink.setOnClickListener(this);
        wine.setOnClickListener(this);
        lunchSides.setOnClickListener(this);
        misc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ib_main_appetizer:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Appetizers");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_soup:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Soup & Salad");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_noodle:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Noodles & Fried Rice");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_entree:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Entrees");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_house:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "House Suggestions");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_vegetarian:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Vegetarian");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_curry:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Curry");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_dessert:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Desserts & Ice cream");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_drink:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Beverage & Drinks");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_wine:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Beer & Wine");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_lunch_sides:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "Lunch Special Sides");
                startActivity(intent);
                finish();
                break;
            case R.id.ib_main_others:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "MISC");
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void prepareRecommend() {
        listOfRecom = new ArrayList<>();
        recomAdapter = new RecomAdapter(MainActivity.this, listOfRecom);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_services);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(8), false));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recomAdapter);
    }
    //preparing for the special offer menu in the main activity, get the list from db.
    private void prepareRecommendCards() {
        ArrayList<String> cuisine = new ArrayList<>();
        cuisine.add("http://static.asiawebdirect.com/m/bangkok/portals/bangkok-com/homepage/food-top10/allParagr0aphs/01/top10Set/01/image/red-curry.jpg");
        cuisine.add("http://static.asiawebdirect.com/m/bangkok/portals/bangkok-com/homepage/food-top10/allParagraphs/01/top10Set/04/image/khao-pad.jpg");
        cuisine.add("http://static.asiawebdirect.com/m/bangkok/portals/bangkok-com/homepage/food-top10/allParagraphs/01/top10Set/03/image/somtam.jpg");
        cuisine.add("http://static.asiawebdirect.com/m/phuket/portals/phuket-com/homepage/cuisine/toptenfood/allParagraphs/0/top10Set/00/image/padthai.jpg");
        cuisine.add("http://static.asiawebdirect.com/m/phuket/portals/phuket-com/homepage/cuisine/toptenfood/allParagraphs/0/top10Set/01/image/noodle-soup.jpg");
        cuisine.add("http://static.asiawebdirect.com/m/bangkok/portals/bangkok-com/homepage/food-top10/allParagraphs/01/top10Set/04/image/khao-pad.jpg");
        cuisine.add("https://asianinspirations.com.au/wp-content/uploads/2016/04/Crab-Fried-Rice.jpg");

        TinyDB tinyDB = new TinyDB(MainActivity.this);
        ArrayList<ProviderLetterMenu> providerLetterMenus = tinyDB.
                getListObject3("LIST_SPECIAL OFFERS", ProviderLetterMenu.class);

        for (int i = 0; i<providerLetterMenus.size(); i++){
            CardProvider a = new CardProvider(providerLetterMenus.get(i).getName(),
                    cuisine.get(4),
                    providerLetterMenus.get(i).getCategory(),
                    providerLetterMenus.get(i).getPrice(),
                    "5.06,4.65,7.00",
                    "Chicken,Pork,Beef",
                    providerLetterMenus.get(i).getDescriptions(),
                    "1",
                    R.drawable.icon_spicy);
            listOfRecom.add(a);
        }

        recomAdapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                System.exit(0);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press Back Button Again to Exit!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_information);
                Button dialogButton = (Button) dialog.findViewById(R.id.btn_okay);
                TextView phoneNum = (TextView) dialog.findViewById(R.id.tv_dialog_phone);
                TextView email = (TextView) dialog.findViewById(R.id.tv_dialog_website);
                Linkify.addLinks(phoneNum, Linkify.PHONE_NUMBERS);
                Linkify.addLinks(email, Linkify.WEB_URLS);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            startActivity(new Intent(MainActivity.this, ActivityOrder.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout){
            startActivity(new Intent(MainActivity.this, ActivityLaunch.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
}
