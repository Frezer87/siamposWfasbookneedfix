package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseConnection {
    private static boolean go = true;

    private static ArrayList<String> menuItemName = new ArrayList<>();
    private static ArrayList<String> menuItemGroupID = new ArrayList<>();
    private static ArrayList<String> menuItemPrice = new ArrayList<>();
    private static ArrayList<String> menuItemDescription = new ArrayList<>();
    private static ArrayList<String> menuItemImage = new ArrayList<>();
    private static ArrayList<String> menuGroupName = new ArrayList<>();
    private static ArrayList<String> menuGroupID = new ArrayList<>();

    public static void databaseConnectionSetup(){
        Thread test = new Thread() {

            public void run() {

                String[] menuItemRows = {"name", "group_id", "price","description", "image"};
                ArrayList[] menuItemLists = {menuItemName, menuItemGroupID, menuItemPrice,
                        menuItemDescription};
                String[] menuGroupRows = {"name", "id"};
                ArrayList[] menuGroupLists = {menuGroupName, menuGroupID};
                String url = DBReferenceInfo.url;
                String usr = DBReferenceInfo.usr;
                String pwd = DBReferenceInfo.pwd;

                try {
                    Class.forName("org.postgresql.Driver");
                    System.out.println("b4 conneting");
                    Connection conn = DriverManager.getConnection(url, usr, pwd);

                    setStatement(conn, "menu_item", menuItemRows, menuItemLists);
                    setStatement(conn, "menu_group", menuGroupRows, menuGroupLists);

                    conn.close();
                    go = false;

                } catch (ClassNotFoundException e) {
                    System.out.print("ClassNotFound");
                    go = false;

                } catch (SQLException e) {
                    System.out.print("SQLException");
                    go = false;
                }
            }

            /**
             * @param conn
             * Always put through the Connection conn for access to the Database.
             *
             * @param tableName
             * Input the name of the table you want to access.
             *
             * @param rowNames
             * An array of the rows you want to access. The array size needs to be the same size as param: rowLists.
             * All of the contents of each rowName value will be inputted into the rowList of the same index value.
             *
             * @param rowLists
             * An array of the ArrayLists to save the contents of the current row. The array size needs to be the same size as param: rowNames.
             * All of the contents of each rowLists value will be inputted into the rowList of the same index value.
             */

            private void setStatement(Connection conn, String tableName, String[] rowNames, ArrayList[] rowLists) throws SQLException {
                String sql = "select * from " + tableName;
                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    for (int i = 0; i < rowLists.length; i++) {
                        rowLists[i].add(result.getString(rowNames[i]));
                    }
                }
                System.out.println("after connecting");
            }
        };
        test.start();

        Thread.interrupted();

        while (go) {
        }

        System.out.println(" ");
        System.out.println("Menu Item Table");
        for (int i = 0; i < menuItemName.size(); i++) {
            System.out.println(menuItemGroupID.get(i) + ": " + menuItemName.get(i) + " - "+menuItemPrice.get(i));
        }

        System.out.println(" ");
        System.out.println("Menu Group Table");
        for (int i = 0; i < menuGroupName.size(); i++) {
            System.out.println(menuGroupID.get(i) + ": " + menuGroupName.get(i));
        }
    }

    public static ArrayList<String> getMenuItemName(){return menuItemName;}
    public static ArrayList<String> getMenuItemGroupID(){return menuItemGroupID;}
    public static ArrayList<String> getMenuItemPrice(){return menuItemPrice;}
    public static ArrayList<String> getMenuItemDescription(){return menuItemDescription;}
    public static ArrayList<String> getMenuGroupName(){return menuGroupName;}
    public static ArrayList<String> getMenuGroupID(){return menuGroupID;}
}
