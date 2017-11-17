package letterMenu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.chengen.siamclassic.MainActivity;
import com.example.chengen.siamclassic.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import shoppingCart.ActivityShoppingCart;

public class ActivitySubCategories extends AppCompatActivity implements View.OnClickListener {

    private static String name;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SmartTabLayout tabLayout;
    private BottomSheetDialog bottomSheetDialog;
    private FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        toolbar = (Toolbar) findViewById(R.id.tb_subs);
        Intent intent = getIntent();
        name = intent.getStringExtra("category");
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.background_light));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable arrow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_left);
            getSupportActionBar().setHomeAsUpIndicator(arrow);
        }
        viewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (SmartTabLayout) findViewById(R.id.tabs);

        setUpMenu();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_sub_classes);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySubCategories.this, ActivityShoppingCart.class)
                        .putExtra("where", "ActivitySubCategories").putExtra("category", name).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }

    private void setUpMenu() {
        toolbar.setTitle(name);
        setUpList();
        viewPager.setAdapter(adapter);
        if (name.equals("Appetizers") || name.equals("Entrees") || name.equals("House Suggestions")
                || name.equals("Vegetarian") || name.equals("Curry")) {
            tabLayout.setVisibility(View.GONE);
            tabLayout.setViewPager(viewPager);
        } else {
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.setViewPager(viewPager);
        }
    }

    private void setUpList() {
        String str = name.toUpperCase();
        switch (str) {
            case "BEVERAGE & DRINKS":
                adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add("Soda", PlaceholderFragment.class, new Bundler().putString("key", "SODA").get())
                        .add("Coffee & Tea", PlaceholderFragment.class, new Bundler().putString("key", "COFE N TEA").get())
                        .add("Espresso", PlaceholderFragment.class, new Bundler().putString("key", "ESPRESSO").get())
                        .add("Juice & Milk", PlaceholderFragment.class, new Bundler().putString("key", "JUICE N MLk").get())
                        .create());
                break;
            case "MISC":
                adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add("Sauce", PlaceholderFragment.class, new Bundler().putString("key", "SAUCE").get())
                        .add("Extra", PlaceholderFragment.class, new Bundler().putString("key", "EXTRA").get())
                        .add("Gift Cards", PlaceholderFragment.class, new Bundler().putString("key", "GIFT CARDS").get())
                        .create());
                break;
            case "SOUP & SALAD":
                System.out.println("hahahshdsah2141241414141");
                adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add("Soup", PlaceholderFragment.class, new Bundler().putString("key", "SOUP").get())
                        .add("Salad", PlaceholderFragment.class, new Bundler().putString("key", "SALAD").get())
                        .create());
                break;
            case "NOODLES & FRIED RICE":
                adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add("Noodles", PlaceholderFragment.class, new Bundler().putString("key", "NOODLES").get())
                        .add("Fried Rice", PlaceholderFragment.class, new Bundler().putString("key", "FRIED RICE").get())
                        .create());
                break;
            case "DESSERTS & ICE CREAM":
                adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add("Desserts", PlaceholderFragment.class, new Bundler().putString("key", "DESSERTS").get())
                        .add("Ice Cream", PlaceholderFragment.class, new Bundler().putString("key", "ICECRM").get())
                        .create());
                break;
            case "LUNCH SPECIAL SIDES":
                adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add("Lunch Special Soup", PlaceholderFragment.class, new Bundler().putString("key", "LUNCH SPECIAL SOUP").get())
                        .add("Lunch Special Sides", PlaceholderFragment.class, new Bundler().putString("key", "LUNCH SPECIAL SIDES").get())
                        .create());
                break;
            case "BEER & WINE":
                adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add("House Wine", PlaceholderFragment.class, new Bundler().putString("key", "HOUSE WINE").get())
                        .add("Featured Wine", PlaceholderFragment.class, new Bundler().putString("key", "FEATURED WINE").get())
                        .add("Special Collection", PlaceholderFragment.class, new Bundler().putString("key", "SPECIAL COLLECTION").get())
                        .add("Mixed Drinks", PlaceholderFragment.class, new Bundler().putString("key", "MIXED DRINKS").get())
                        .add("Beer", PlaceholderFragment.class, new Bundler().putString("key", "BEER").get())
                        .add("Sake", PlaceholderFragment.class, new Bundler().putString("key", "SAKE").get())
                        .create());
                break;
            default:
                adapter = new FragmentPagerItemAdapter(
                        getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add(name, PlaceholderFragment.class, new Bundler().putString("key", name.toUpperCase()).get())
                        .create());
        }
    }

    private void changeCategory() {
        bottomSheetDialog = new BottomSheetDialog(ActivitySubCategories.this);
        View parentView = getLayoutInflater().inflate(R.layout.dialog_category, null);
        bottomSheetDialog.setContentView(parentView);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
        bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                500, getResources().getDisplayMetrics()));
        LinearLayout appetizer = (LinearLayout) parentView.findViewById(R.id.ll_dialog_appetizer);
        LinearLayout soupSalad = (LinearLayout) parentView.findViewById(R.id.ll_dialog_soup);
        LinearLayout noodleRice = (LinearLayout) parentView.findViewById(R.id.ll_dialog_noodle);
        LinearLayout entrees = (LinearLayout) parentView.findViewById(R.id.ll_dialog_entree);
        LinearLayout house = (LinearLayout) parentView.findViewById(R.id.ll_dialog_house);
        LinearLayout veg = (LinearLayout) parentView.findViewById(R.id.ll_dialog_vegetarian);
        LinearLayout curry = (LinearLayout) parentView.findViewById(R.id.ll_dialog_curry);
        LinearLayout dessert = (LinearLayout) parentView.findViewById(R.id.ll_dialog_dessert);
        LinearLayout drink = (LinearLayout) parentView.findViewById(R.id.ll_dialog_drink);
        LinearLayout wine = (LinearLayout) parentView.findViewById(R.id.ll_dialog_wine);
        LinearLayout lunchSides = (LinearLayout) parentView.findViewById(R.id.ll_dialog_lunch_sides);
        LinearLayout misc = (LinearLayout) parentView.findViewById(R.id.ll_dialog_misc);
        CircleImageView appetizer1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_appetizer);
        CircleImageView soup1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_soup);
        CircleImageView noodles1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_noodle);
        CircleImageView entree1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_entree);
        CircleImageView house1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_house);
        CircleImageView veg1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_vegetarian);
        CircleImageView curry1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_curry);
        CircleImageView dessert1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_dessert);
        CircleImageView drink1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_drink);
        CircleImageView wine1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_wine);
        CircleImageView lunchSides1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_lunch_sides);
        CircleImageView misc1 = (CircleImageView) parentView.findViewById(R.id.ib_dialog_misc);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_appetizers).fit().centerCrop().into(appetizer1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_soups).fit().centerCrop().into(soup1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_noodles).fit().centerCrop().into(noodles1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_entree).fit().centerCrop().into(entree1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_home).fit().centerCrop().into(house1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_veg).fit().centerCrop().into(veg1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_curry).fit().centerCrop().into(curry1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_dessert).fit().centerCrop().into(dessert1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_drinks).fit().centerCrop().into(drink1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_wines).fit().centerCrop().into(wine1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_lunch_sides).fit().centerCrop().into(lunchSides1);
        Picasso.with(ActivitySubCategories.this).load(R.drawable.icon_misc).fit().centerCrop().into(misc1);
        appetizer.setOnClickListener(this);
        soupSalad.setOnClickListener(this);
        noodleRice.setOnClickListener(this);
        entrees.setOnClickListener(this);
        house.setOnClickListener(this);
        veg.setOnClickListener(this);
        curry.setOnClickListener(this);
        dessert.setOnClickListener(this);
        drink.setOnClickListener(this);
        wine.setOnClickListener(this);
        lunchSides.setOnClickListener(this);
        misc.setOnClickListener(this);
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_dialog_appetizer:
                name = "Appetizers";
                break;
            case R.id.ll_dialog_soup:
                name = "Soup & Salad";
                break;
            case R.id.ll_dialog_noodle:
                name = "Noodles & Fried Rice";
                break;
            case R.id.ll_dialog_entree:
                name = "Entrees";
                break;
            case R.id.ll_dialog_house:
                name = "House Suggestions";
                break;
            case R.id.ll_dialog_vegetarian:
                name = "Vegetarian";
                break;
            case R.id.ll_dialog_curry:
                name = "Curry";
                break;
            case R.id.ll_dialog_dessert:
                name = "Desserts & Ice cream";
                break;
            case R.id.ll_dialog_drink:
                name = "Beverage & Drinks";
                break;
            case R.id.ll_dialog_wine:
                name = "Beer & Wine";
                break;
            case R.id.ll_dialog_lunch_sides:
                name = "Lunch Special Sides";
                break;
            case R.id.ll_dialog_misc:
                name = "MISC";
                break;
            default:
                break;
        }
        tabLayout.removeView(viewPager);
        viewPager.removeAllViews();
        viewPager.setAdapter(null);
        setUpMenu();
        bottomSheetDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_show_cate:
                changeCategory();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public static String getName() {
        return name;
    }
}
