package ngothanhson95.dev.com.timbuyt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    public static final int START_PLACE_CODE = 12;
    public static final int DESTINATION_PLACE_CODE = 10;
    public static final String SEARCH_KEY = "search";

    private GoogleMap mMap;
    private GoogleApiClient mgoogleApiClient;
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

        mgoogleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

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
                    Toast.makeText(MainActivity.this, "direction", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_bus_stop) {
                    Toast.makeText(MainActivity.this, "stop", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_search) {

                } else if (id == R.id.nav_person) {

                } else if (id == R.id.nav_help){
                    /*
                    Fragment_Help help = new Fragment_Help();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.fragment_container,help,help.getTag()).commit();
                    */
                    Intent intent = new Intent(MainActivity.this, Help_Fragment.class);
                    startActivity(intent);


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
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(MainActivity.this);
                    startActivityForResult(intent, START_PLACE_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(MainActivity.this);
                    startActivityForResult(intent, DESTINATION_PLACE_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == START_PLACE_CODE){
            if(resultCode == RESULT_OK){
                Place place = PlaceAutocomplete.getPlace(this, data);
                btnFrom.setText(place.getName().toString());
            } else if(resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Toast.makeText(this, status.getStatusMessage() , Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == DESTINATION_PLACE_CODE){
            if(resultCode == RESULT_OK){
                Place place = PlaceAutocomplete.getPlace(this, data);
                btnTo.setText(place.getName().toString());
            } } else if(resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            Toast.makeText(this, status.getStatusMessage() , Toast.LENGTH_SHORT).show();
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}