package com.example.ebazer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class Home_nav extends AppCompatActivity   {

    private static final String TAG =Home_nav.class.getSimpleName() ;
    private AppBarConfiguration mAppBarConfiguration;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
         fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



//        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
//                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        DrawerLayout    drawer = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_my_home,R.id.nav_my_orders,R.id.nav_my_rewards,
                R.id.nav_my_cart,R.id.nav_my_wishlist,R.id.nav_my_account,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // navigationView.setNavigationItemSelectedListener(this);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {


                switch (destination.getId()){
            case R.id.nav_my_home:{
                Intent i=new Intent(getApplicationContext(),signin.class);
                startActivity(i);
                fab.hide();
                Toast.makeText(getApplicationContext(),"home",Toast.LENGTH_LONG).show();
                break; }
            case R.id.nav_my_orders:{
                fab.hide();
                Toast.makeText(getApplicationContext(),"order",Toast.LENGTH_LONG).show();
                break;}
                case R.id.nav_my_rewards:
                Toast.makeText(getApplicationContext(),"you click on  :rewards",Toast.LENGTH_LONG).show();
                break;
                case R.id.nav_my_cart:
                Toast.makeText(getApplicationContext(),"you click on  :cart",Toast.LENGTH_LONG).show();
                break;
                case R.id.nav_my_wishlist:
                Toast.makeText(getApplicationContext(),"you click on  : wish",Toast.LENGTH_LONG).show();
                break;
                case R.id.nav_my_account:
                Toast.makeText(getApplicationContext(),"you click on  : account",Toast.LENGTH_LONG).show();
                break;
                case R.id.nav_logout:
                Toast.makeText(getApplicationContext(),"you click on  : logout",Toast.LENGTH_LONG).show();
                break;

                    default:
                        int s=destination.getId();
                        Toast.makeText(getApplicationContext(),"you click on  : "+s ,Toast.LENGTH_LONG).show();
                        fab.show();
                        break;
        }
        //end switch
            }
        });
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_nav, menu);
        return true;
    }



    @Override  //to handel home_nav.xml items(i.e; search, notification nd cart ) we have to override this
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();

        if(id==R.id.main_search_icon){
            //write code for search
            Toast.makeText(this,"you click on  :search",Toast.LENGTH_LONG).show();
//            Intent i=new Intent(getApplicationContext(),signin.class);
//            startActivity(i);
            return true;
        }
        else if(id==R.id.main_notification_icon){
            //write code for notification
            Toast.makeText(this,"you click on  :notification",Toast.LENGTH_LONG).show();
            return true;
        }
        else if(id==R.id.main_cart_icon){
            //write code for cart
            Toast.makeText(this,"you click on  :cart",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//
//    @Override
//    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination,
//                                     @Nullable Bundle arguments) {
//
//
//                switch (destination.getId()){
//            case R.id.nav_my_home:{
//                Intent i=new Intent(getApplicationContext(),signin.class);
//                startActivity(i);
//                fab.hide();
//                Toast.makeText(this,"you click on  :home",Toast.LENGTH_LONG).show();
//                break;}
//            case R.id.nav_my_orders:{
//                fab.hide();
//                Toast.makeText(this,"you click on  :order",Toast.LENGTH_LONG).show();
//                break;}
//                case R.id.nav_my_rewards:
//                Toast.makeText(this,"you click on  :rewards",Toast.LENGTH_LONG).show();
//                break;
//                case R.id.nav_my_cart:
//                Toast.makeText(this,"you click on  :cart",Toast.LENGTH_LONG).show();
//                break;
//                case R.id.nav_my_wishlist:
//                Toast.makeText(this,"you click on  : wish",Toast.LENGTH_LONG).show();
//                break;
//                case R.id.nav_my_account:
//                Toast.makeText(this,"you click on  : account",Toast.LENGTH_LONG).show();
//                break;
//                case R.id.nav_logout:
//                Toast.makeText(this,"you click on  : logout",Toast.LENGTH_LONG).show();
//                break;
//
//                    default:
//                        //Toast.makeText(this,"you click on  : fdgfr",Toast.LENGTH_LONG).show();
//                        fab.show();
//                        break;
//        }
//
//        //end switch
//    }
}
