package letterMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.chengen.siamclassic.ActivityOrder;
import com.example.chengen.siamclassic.R;

import java.util.ArrayList;

import backups.TinyDB;
import databaseConnection.DatabaseConnection;

 public class PlaceholderFragment extends Fragment {

    private SwipeMenuListView letterList;
    private ArrayList<ProviderLetterMenu> providerLetterMenus;
    private static String subCategory;
    ArrayList<String> menuItemName;
    ArrayList<String> menuItemPrice;
    ArrayList<String> menuItemGroupID;
    ArrayList<String> menuItemDescription;
    ArrayList<String> menuGroupID;
    ArrayList<String> menuGroupName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sub_categories, container, false);
        letterList = (SwipeMenuListView) rootView.findViewById(R.id.sub_classes_list_view);
        Bundle bundle = getArguments();
        subCategory = bundle.getString("key");
        System.out.println(subCategory+"-------------------------------");
        createListView();
        letterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActivityOrder.class);
                ArrayList<String> strings = new ArrayList<>();
                strings.add(providerLetterMenus.get(position).getName());
                strings.add(providerLetterMenus.get(position).getImageUrl());
                strings.add(providerLetterMenus.get(position).getCategory());
                strings.add(providerLetterMenus.get(position).getPrice());
                strings.add(providerLetterMenus.get(position).getChooseSpicy());
                strings.add(providerLetterMenus.get(position).getOptions());
                strings.add(providerLetterMenus.get(position).getDescriptions());
                intent.putExtra("itemData", strings);
                intent.putExtra("here", "Menu");
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }
    private void createListView() {
        try {
            Thread t = new Thread() {
                @Override
                public void run() {
                    super.run();
                    initData();
                }
            };
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AdapterLetterMenu adapterLetterMenu = new AdapterLetterMenu(getActivity(), providerLetterMenus);
        letterList.setAdapter(adapterLetterMenu);
    }

    private void initData() {
        providerLetterMenus = new ArrayList<>();
        menuItemName = (ArrayList) DatabaseConnection.getMenuItemName().clone();
        menuItemPrice = (ArrayList) DatabaseConnection.getMenuItemPrice().clone();
        menuItemGroupID = (ArrayList) DatabaseConnection.getMenuItemGroupID().clone();
        menuItemDescription = (ArrayList) DatabaseConnection.getMenuItemDescription().clone();

        menuGroupID = (ArrayList) DatabaseConnection.getMenuGroupID().clone();
        menuGroupName = (ArrayList) DatabaseConnection.getMenuGroupName().clone();
        providerLetterMenus = new ArrayList<>();
        switch (subCategory) {
            case "APPETIZERS":
                subCategory = "Appetizers";
                getItemForLocal("LIST_APPETIZERS");
                break;
            case "SOUP":
                subCategory = "Soup";
                getItemForLocal("LIST_SOUP");
                break;
            case "SALAD":
                subCategory = "Salad";
                getItemForLocal("LIST_SALAD");
                break;
            case "NOODLES":
                subCategory = "Noodles";
                getItemForLocal("LIST_NOODLES");
                break;
            case "FRIED RICE":
                subCategory = "Fried Rice";
                getItemForLocal("LIST_FRIED RICE");
                break;
            case "ENTREES":
                subCategory = "Entrees";
                getItemForLocal("LIST_ENTREES");
                break;
            case "HOUSE SUGGESTIONS":
                subCategory = "House Suggestions";
                getItemForLocal("LIST_HOUSE SUGGESTIONS");
                break;
            case "VEGETARIAN":
                subCategory = "Vegetarian";
                getItemForLocal("LIST_VEGETARIAN");
                break;
            case "CURRY":
                subCategory = "Curry";
                getItemForLocal("LIST_CURRY");
                break;
            case "DESSERTS":
                subCategory = "Desserts";
                getItemForLocal("LIST_DESSERTS");
                break;
            case "ICE CREAM":
                subCategory = "Ice Cream";
                getItemForLocal("LIST_ICECRM");
                break;
            case "LUNCH SPECIAL SOUP":
                subCategory = "Lunch Special Soup";
                getItemForLocal("LIST_LUNCH SPECIAL SOUP");
                break;
            case "LUNCH SPECIAL SIDES":
                subCategory = "Lunch Special Sides";
                getItemForLocal("LIST_LUNCH SPECIAL SIDES");
                break;
            case "HOUSE WINE":
                subCategory = "House Wine";
                getItemForLocal("LIST_HOUSE WINE");
                break;
            case "FEATURED WINE":
                subCategory = "Featured Wine";
                getItemForLocal("LIST_FEATURED WINE");
                break;
            case "SPECIAL COLLECTION":
                subCategory = "Special Collection";
                getItemForLocal("LIST_SPECIAL COLLECTION");
                break;
            case "MIXED DRINKS":
                subCategory = "Mixed Drinks";
                getItemForLocal("LIST_MIXED DRINKS");
                break;
            case "BEER":
                subCategory = "Beer";
                getItemForLocal("LIST_BEER");
                break;
            case "SAKE":
                subCategory = "Sake";
                getItemForLocal("LIST_SAKE");
                break;
            case "SAUCE":
                subCategory = "Sauce";
                getItemForLocal("LIST_SAUCE");
                break;
            case "EXTRA":
                subCategory = "Extra";
                getItemForLocal("LIST_EXTRA");
                break;
            case "GIFT CARDS":
                subCategory = "Gift Cards";
                getItemForLocal("LIST_GIFT CARDS");
                break;
            case "SODA":
                subCategory = "Soda";
                getItemForLocal("LISTSODA");
                break;
            case "COFFEE & TEA":
                subCategory = "Coffee & Tea";
                getItemForLocal("LIST_COFE N TEA");
                break;
            case "ESPRESSO":
                subCategory = "Espresso";
                getItemForLocal("LIST_ESPRESSO");
                break;
            case "JUICE N MLK":
                subCategory = "Juice and Milk";
                getItemForLocal("LIST_JUICE N MLK");
                break;
            default:
                break;
        }
    }

    private void getItemForLocal(String key){
        TinyDB tinyDB = new TinyDB(getActivity());
        providerLetterMenus = tinyDB.getListObject3(key,ProviderLetterMenu.class);
    }
}
