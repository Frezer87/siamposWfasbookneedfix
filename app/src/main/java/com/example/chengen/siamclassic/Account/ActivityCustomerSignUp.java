package com.example.chengen.siamclassic.Account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chengen.siamclassic.ActivityLaunch;
import com.example.chengen.siamclassic.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import databaseConnection.DBReferenceInfo;

public class ActivityCustomerSignUp extends AppCompatActivity implements View.OnClickListener {
    private String url = DBReferenceInfo.url;
    private String usr = DBReferenceInfo.usr;
    private String pwd = DBReferenceInfo.pwd;
    private Connection conn;

    private TextView user_id;
    private MaterialEditText user_pass;
    private MaterialEditText user_re_pass;
    private MaterialEditText user_email;
    private MaterialEditText first_name;
    private MaterialEditText last_name;
    private MaterialEditText phone_no;
    private Button sign_up_btn;

    private ArrayList<Integer> userIdlist = new ArrayList<Integer>();
    private int max;
    private String page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_customer);
        user_id = (TextView) findViewById(R.id.user_id);
        user_email = (MaterialEditText) findViewById(R.id.user_email);
        user_pass = (MaterialEditText) findViewById(R.id.user_pass);
        first_name = (MaterialEditText) findViewById(R.id.first_name);
        last_name = (MaterialEditText) findViewById(R.id.last_name);
        phone_no = (MaterialEditText) findViewById(R.id.phone_no);
        user_re_pass = (MaterialEditText) findViewById(R.id.user_re_pass);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable arrow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_left);
            getSupportActionBar().setHomeAsUpIndicator(arrow);
        }
        Intent intent = getIntent();
        page = intent.getStringExtra("page");
        inuserid();

    }

    public boolean isEmpty() {
        boolean isOk =true;
        if (first_name.getText().toString().equals("")) {
            first_name.setError("Please enter your first name");
            isOk = false;
        }
        if (last_name.getText().toString().equals("")) {
            last_name.setError("Please enter your last name");
            isOk = false;
        }
        if (phone_no.getText().toString().equals("")) {
            phone_no.setError("Please enter your phone number");
            isOk = false;
        }
        if(user_email.getText().toString().equals("")) {
            user_email.setError("Please enter your email as username");
            isOk = false;
        }
        if (user_pass.getText().toString().equals("")) {
            user_pass.setError("Please enter password");
            isOk = false;
        }
        if (user_pass.getText().toString().length()<4) {
            user_pass.setError("Password must longer than 4 characters");
            isOk = false;
        }
        if(!user_pass.getText().toString().equals(user_re_pass.getText().toString())){
            user_pass.setError("These passwords must be the same");
            user_re_pass.setError("These passwords must be the same");
            isOk = false;
        }
        return isOk;
    }

    public boolean errorchk() {
        if (max == 0) {
            Log.e("logic error", "max number of user_id is 0. please check database or codde");
            return false;
        } else {
            return true;
        }

    }

    public boolean gettf(Spinner temp) {
        if (temp.getSelectedItem().toString().equals("true"))
            return true;

        return false;
    }

    public void inuserid() {
        Thread t = new Thread() {
            public void run() {

                try {
                    Class.forName("org.postgresql.Driver");
                    // -- 1
                    System.out.println("b4 conneting");
                    conn = DriverManager.getConnection(url, usr, pwd);


                    //-------------------------------------------------------read from db
                    String sql = "select * from users";

                    PreparedStatement statement = conn.prepareStatement(sql);
                    ResultSet result = statement.executeQuery();

                    System.out.println("middle conneting");
                    while (result.next()) {
                        System.out.println(result.getInt("user_id"));
                        userIdlist.add(result.getInt("user_id"));
                    }

                    //-------------------------------------------------------read from db end
                    max = Collections.max(userIdlist);
                    System.out.println("max:" + max);
                    max += 1;
                    System.out.println("after connecting");
                    conn.close();

                } catch (ClassNotFoundException e) {
                    System.out.print("Error");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            t.join();
            user_id.setText(String.valueOf(max));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.interrupted();
    }

    @Override
    public void onClick(View v) {
        if(isEmpty()||errorchk()) {

            new Thread() {
                public void run() {


                    try {
                        Class.forName("org.postgresql.Driver");
                        // -- 1
                        System.out.println("b4 conneting");
                        conn = DriverManager.getConnection(url, usr, pwd);

                        //-------------------------------------------------------write to db
                        String sql = " insert into users (user_id, user_pass, first_name, last_name, phone_no, is_driver, available_for_delivery, n_user_type)"
                                + " values (?, ?, ?, ?, ?, ?, ?, ?)";

                        // create the mysql insert preparedstatement
                        PreparedStatement preparedStmt = conn.prepareStatement(sql);
                        preparedStmt.setInt(1, max);
                        preparedStmt.setString(2, user_pass.getText().toString());
                        preparedStmt.setString(3, first_name.getText().toString());
                        preparedStmt.setString(4, last_name.getText().toString());
                        preparedStmt.setString(5, phone_no.getText().toString());
                        preparedStmt.setInt(8, 4);

                        // execute the preparedstatement
                        preparedStmt.execute();

                        //-------------------------------------------------------write to db end

                        System.out.println("after connecting");
                        conn.close();

                    } catch (ClassNotFoundException e) {
                        System.out.print("Error");
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }.start();

            Thread.interrupted();
            Toast.makeText(ActivityCustomerSignUp.this, "You signed up successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivityCustomerSignUp.this, ActivityCustomerSignIn.class));
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                if(page.equals("signIn")) {
                    startActivity(new Intent(ActivityCustomerSignUp.this, ActivityCustomerSignIn.class));
                } else{
                    startActivity(new Intent(ActivityCustomerSignUp.this, ActivityLaunch.class));
                }
                finish();
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onBackPressed() {
        if(page.equals("signIn")) {
            startActivity(new Intent(ActivityCustomerSignUp.this, ActivityCustomerSignIn.class));
        } else{
            startActivity(new Intent(ActivityCustomerSignUp.this, ActivityLaunch.class));
        }
        finish();
    }
}
