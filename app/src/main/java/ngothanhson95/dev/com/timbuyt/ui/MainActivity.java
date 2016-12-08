package ngothanhson95.dev.com.timbuyt.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ngothanhson95.dev.com.timbuyt.Constants;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.model.BusStopJSON;
import ngothanhson95.dev.com.timbuyt.model.Result;
import ngothanhson95.dev.com.timbuyt.network.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private List<Result> results;
    private List<ngothanhson95.dev.com.timbuyt.model.Location> busStopsLocation = new ArrayList<>();
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrentLocationMarker;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates;
    private boolean mAddressRequested;
    public String mAddressOutput;
    private LatLng BachKhoa = new LatLng(21.0042788, 105.8437013);
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ImageView btnMenu, btnSwap;
    private FloatingActionButton btnSearch, btnMyLocation;
    private LinearLayout layoutFrom, layoutTo;
    private RelativeLayout toolHeader;
    private Button btnFrom, btnTo;
    private boolean noSwap = true;
    int viewHeight;
    private static final int ANIMATION_DURATION = 200;
    private String mLastUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnMenu = (ImageView) findViewById(R.id.btnMenu);
        btnSwap = (ImageView) findViewById(R.id.btnSwap);
        layoutFrom = (LinearLayout) findViewById(R.id.layoutFrom);
        layoutTo = (LinearLayout) findViewById(R.id.layoutTo);
        toolHeader = (RelativeLayout) findViewById(R.id.toolHeader);
        btnFrom = (Button) findViewById(R.id.btnFrom);
        btnTo = (Button) findViewById(R.id.btnTo);
        btnSearch = (FloatingActionButton) findViewById(R.id.btnSearchPlace);
        btnMyLocation = (FloatingActionButton) findViewById(R.id.btnMyLocation);


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
                    if(mLastLocation!=null){
                        loadBusStopJson();
                        loadAllNearBusStation();
                    }
                } else if (id == R.id.nav_search) {

                } else if (id == R.id.nav_person) {

                } else if (id == R.id.nav_help) {

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
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
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // ViewTreeObserver used to register listeners that can be notified of global changes in the view tree
        ViewTreeObserver viewTreeObserver = btnFrom.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
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
                if (noSwap) {
                    TranslateAnimation ta1 = new TranslateAnimation(0, 0, 0, viewHeight);
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

                } else {
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
                    startActivityForResult(intent, Constants.START_PLACE_CODE);
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
                    startActivityForResult(intent, Constants.DESTINATION_PLACE_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentLocationMarker != null) {
                    mCurrentLocationMarker.remove();
                }
                if (mLastLocation != null) {
                    LatLng latlng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latlng);
                    markerOptions.title("You're here");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mCurrentLocationMarker = mMap.addMarker(markerOptions);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                } else {
                    Toast.makeText(getApplicationContext(), "Loading your current location...", Toast.LENGTH_LONG).show();
                }
            }
        });
          updateValueFromBundle(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.START_PLACE_CODE) {
            // get name of start point and mark it on Map
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                btnFrom.setText(place.getName().toString());
                mMap.addMarker(new MarkerOptions()
                        .position(place.getLatLng())
                        .title(place.getAddress().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constants.DESTINATION_PLACE_CODE) {
            // get name of destination point and mark it on Map
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                btnTo.setText(place.getName().toString());
                mMap.addMarker(new MarkerOptions()
                        .position(place.getLatLng())
                        .title(place.getAddress().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            }
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BachKhoa, 15));
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

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    protected void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    public void loadBusStopJson(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterfac= retrofit.create(RequestInterface.class);
        Call<BusStopJSON> call = requestInterfac.getAllBusStop(locationToString(mLastLocation));
        call.enqueue(new Callback<BusStopJSON>() {
            @Override
            public void onResponse(Call<BusStopJSON> call, Response<BusStopJSON> response) {
                BusStopJSON busStopJSON = response.body();
                results = new ArrayList<>(Arrays.asList(busStopJSON.getResults()).size());
                results.addAll(busStopJSON.getResults());
            }

            @Override
            public void onFailure(Call<BusStopJSON> call, Throwable t) {

            }
        });
    }

    private void loadAllNearBusStation(){
        if(results!=null){
            for (Result result : results){
                ngothanhson95.dev.com.timbuyt.model.Location location = result.getGeometry().getLocation();
                LatLng latLng = new LatLng(location.getLat(), location.getLng());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrentLocationMarker = mMap.addMarker(markerOptions);
            }

        }
    }

    private String locationToString(Location location){
        return location.getLatitude() + "," + location.getLongitude();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onPause() {
        stopLocationUpdate();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdate();
        }
    }

    //save the instance state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates );
        outState.putParcelable(Constants.LOCATION_KEY, mLastLocation);
        outState.putString(Constants.LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(outState);
    }

    private void updateValueFromBundle(Bundle savedInstanceState){
        if(savedInstanceState!= null){
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.
            if(savedInstanceState.keySet().contains(Constants.REQUESTING_LOCATION_UPDATES_KEY)){
                mRequestingLocationUpdates = savedInstanceState.getBoolean(Constants.REQUESTING_LOCATION_UPDATES_KEY);
            }
            if(savedInstanceState.keySet().contains(Constants.LOCATION_KEY)){
                mLastLocation = savedInstanceState.getParcelable(Constants.LOCATION_KEY);
            }
            if (savedInstanceState.keySet().contains(Constants.LAST_UPDATED_TIME_STRING_KEY)){
                mLastUpdateTime = savedInstanceState.getString(Constants.LAST_UPDATED_TIME_STRING_KEY);
            }
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

}
