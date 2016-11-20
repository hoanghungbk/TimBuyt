package ngothanhson95.dev.com.timbuyt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final int START_PLACE_CODE = 12;
    public static final int DESTINATION_PLACE_CODE = 10;
    public static final String SEARCH_KEY = "search";

    private GoogleMap mMap;
    private LatLng BachKhoa = new LatLng(21.0042788,105.8437013);
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ImageView btnMenu, btnSwap;
    private LinearLayout layoutFrom, layoutTo;
    private Button btnFrom, btnTo;
    private boolean noSwap = true;
    int viewHeight;
    private static final int ANIMATION_DURATION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnMenu = (ImageView) findViewById(R.id.btnMenu);
        btnSwap = (ImageView) findViewById(R.id.btnSwap);
        layoutFrom = (LinearLayout) findViewById(R.id.layoutFrom);
        layoutTo = (LinearLayout) findViewById(R.id.layoutTo);
        btnFrom = (Button) findViewById(R.id.btnFrom);
        btnTo = (Button) findViewById(R.id.btnTo);

        //direction button is default set
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_direction);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_direction) {

                } else if (id == R.id.nav_bus_stop) {

                } else if (id == R.id.nav_search) {

                } else if (id == R.id.nav_person) {

                } else if (id == R.id.nav_help){

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    return;
                } else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // ViewTreeObserver used to register listeners that can be notified of global changes in the view tree
        ViewTreeObserver viewTreeObserver = btnFrom.getViewTreeObserver();
        if(viewTreeObserver.isAlive()){
            //Register a callback to be invoked when the global layout state or the visibility of views within the view tree changes
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    btnFrom.getViewTreeObserver().addOnGlobalLayoutListener(this);
                    viewHeight = layoutFrom.getHeight();
                    btnTo.getLayoutParams();
                }
            });
        }

        //swap starting point and direction point when click button swap
        btnSwap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(noSwap){
                    TranslateAnimation ta1 = new TranslateAnimation(0,0,0,viewHeight);
                    ta1.setDuration(ANIMATION_DURATION);
                    ta1.setFillAfter(true);
                    layoutFrom.startAnimation(ta1);
                    layoutFrom.bringToFront();

                    TranslateAnimation ta2 = new TranslateAnimation(0, 0, 0, -viewHeight);
                    ta2.setDuration(ANIMATION_DURATION);
                    ta2.setFillAfter(true);
                    layoutTo.startAnimation(ta2);
                    layoutTo.bringToFront();

                    noSwap = false;

                }else {
                    TranslateAnimation ta1 = new TranslateAnimation(0, 0, viewHeight, 0);
                    ta1.setDuration(ANIMATION_DURATION);
                    ta1.setFillAfter(true);
                    layoutFrom.startAnimation(ta1);
                    layoutFrom.bringToFront();

                    TranslateAnimation ta2 = new TranslateAnimation(0, 0, -viewHeight, 0);
                    ta2.setDuration(ANIMATION_DURATION);
                    ta2.setFillAfter(true);
                    layoutTo.startAnimation(ta2);
                    layoutTo.bringToFront();

                    noSwap = true;
                }
            }
        });

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchPlaceActivity.class);
                intent.putExtra(SEARCH_KEY, btnFrom.getText().toString());
                startActivityForResult(intent, START_PLACE_CODE);
            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchPlaceActivity.class);
                intent.putExtra(SEARCH_KEY, btnTo.getText().toString());
                startActivityForResult(intent, DESTINATION_PLACE_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == START_PLACE_CODE){
            if(resultCode == RESULT_OK){
                btnFrom.setText(data.getExtras().getString(SearchPlaceActivity.PLACE_KEY));
            }
        } else if(requestCode == DESTINATION_PLACE_CODE){
            if(resultCode == RESULT_OK){
                btnTo.setText(data.getExtras().getString(SearchPlaceActivity.PLACE_KEY));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( BachKhoa, 15));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
