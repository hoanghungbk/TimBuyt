package ngothanhson95.dev.com.timbuyt.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ngothanhson95.dev.com.timbuyt.AppConstants;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.model.bus.BusStopJSON;
import ngothanhson95.dev.com.timbuyt.model.bus.Result;
import ngothanhson95.dev.com.timbuyt.model.direction.Route;
import ngothanhson95.dev.com.timbuyt.model.direction.Step;
import ngothanhson95.dev.com.timbuyt.network.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private List<Result> results;
    private List<ngothanhson95.dev.com.timbuyt.model.bus.Location> busStopsLocation = new ArrayList<>();
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrentLocationMarker;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates;
    private boolean mAddressRequested;
    public String mAddressOutput;
    public Place origin, destination;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ImageView btnMenu, btnGo;
    private FloatingActionButton btnSearch, btnMyLocation;
    private LinearLayout layoutFrom, layoutTo;
    private RelativeLayout toolHeader;
    private Button btnFrom, btnTo;
    private View mapView;
    private String mLastUpdateTime;
    private Bitmap smallMarker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();

        initView();

        updateValueFromBundle(savedInstanceState);
    }

    private void initView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnMenu = (ImageView) findViewById(R.id.btnMenu);
        btnGo = (ImageView) findViewById(R.id.btnGo);
        layoutFrom = (LinearLayout) findViewById(R.id.layoutFrom);
        layoutTo = (LinearLayout) findViewById(R.id.layoutTo);
        toolHeader = (RelativeLayout) findViewById(R.id.toolHeader);
        btnFrom = (Button) findViewById(R.id.btnFrom);
        btnTo = (Button) findViewById(R.id.btnTo);
        toolHeader = (RelativeLayout) findViewById(R.id.toolHeader);

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.bus_hanoi);
        Bitmap b=bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);

        //direction button is default set
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_direction);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_direction) {
                    mMap.clear();
                } else if (id == R.id.nav_bus_stop) {
                    if(mLastLocation!=null){
                        loadBusStopJson();
                        loadAllNearBusStation();
                    }
                } else if (id == R.id.nav_search) {
                    Intent intent = new Intent(MainActivity.this, BusResultActivity.class);
                    startActivity(intent);
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

    }

    public void onBtnMenuClick(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            return;
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void onBtnGoClick(View view){
        Intent i = new Intent(this, RouteResultActivity.class);
        if(destination!=null) {
            if(origin==null){
                i.putExtra(AppConstants.ORIGIN_KEY, locationToString(mLastLocation));
            } else {
                i.putExtra(AppConstants.ORIGIN_KEY, placeToString(origin));
            }
            i.putExtra(AppConstants.DESTINATION_KEY, placeToString(destination));
            i.putExtra(AppConstants.ORIGIN_NAME_KEY, btnFrom.getText());
            i.putExtra(AppConstants.DESTINATION_NAME_KEY, btnTo.getText());
            startActivity(i);
        } else {
            Toast.makeText(this, "Nhập địa điểm bạn muốn đến", Toast.LENGTH_LONG).show();
        }
    }

    public void onBtnFromClick(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(MainActivity.this);
            startActivityForResult(intent, AppConstants.START_PLACE_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    public void onBtnToClick(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(MainActivity.this);
            startActivityForResult(intent, AppConstants.DESTINATION_PLACE_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.START_PLACE_CODE) {
            // get name of start point and mark it on Map
            if (resultCode == RESULT_OK) {
                origin = PlaceAutocomplete.getPlace(this, data);
                btnFrom.setText(origin.getName().toString());
                mMap.addMarker(new MarkerOptions()
                        .position(origin.getLatLng())
                        .title(origin.getAddress().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(origin.getLatLng()));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == AppConstants.DESTINATION_PLACE_CODE) {
            // get name of destination point and mark it on Map
            if (resultCode == RESULT_OK) {
                destination = PlaceAutocomplete.getPlace(this, data);

                btnTo.setText(destination.getName().toString());
                mMap.addMarker(new MarkerOptions()
                        .position(destination.getLatLng())
                        .title(destination.getAddress().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(destination.getLatLng()));
            }
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //icon marker

        // get route serializable from route result aactivity
        Bundle bundle = new Bundle();
        bundle = getIntent().getBundleExtra(AppConstants.ROUT_KEY);
        if(bundle!=null){
            List<LatLng> listLatLng = new ArrayList<>();
            toolHeader.setVisibility(View.INVISIBLE);
            Route route = (Route) bundle.getSerializable(AppConstants.ROUT_KEY);
            List<Step> steps = route.getLegs().get(0).getSteps();
            for(int i = 0; i< route.getLegs().get(0).getSteps().size(); i++){
                String polyline = "";
                PolylineOptions polylineOptions = new PolylineOptions();
                polyline = steps.get(i).getPolyline().getPoints();
                listLatLng = decodePoly(polyline);
                polylineOptions.addAll(listLatLng);
                polylineOptions.width(10);

                if(steps.get(i).getTravelMode().contains("WALKING")) {
                    polylineOptions.color(Color.GREEN);
                } else {
                    polylineOptions.color(Color.RED);
                    String busNumber = steps.get(i).getTransitDetails().line.name;

                    Marker arrive = mMap.addMarker(new MarkerOptions()
                            .position(listLatLng.get(0))
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .title("Bắt xe: ")
                            .snippet(busNumber));
                    arrive.showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrive.getPosition(), 13));
                }
                mMap.addPolyline(polylineOptions);
            }

        }

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

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }

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
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    //init location request, request locationupdate
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


    //load all near bus station with radius = 1000
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
                ngothanhson95.dev.com.timbuyt.model.bus.Location location = result.getGeometry().getLocation();
                LatLng latLng = new LatLng(location.getLat(), location.getLng());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                mCurrentLocationMarker = mMap.addMarker(markerOptions);
            }

        }
    }

    //convert Location to String "lat, long"
    private String locationToString(Location location){
        return location.getLatitude() + "," + location.getLongitude();
    }

    private String placeToString(Place place){
        return String.valueOf(place.getLatLng().latitude) + "," + String.valueOf(place.getLatLng().longitude);
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

    private List<LatLng> decodePoly(String encoded){
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while(index<len){
            int b, shift =0, result =0 ;
            do{
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

    //save the instance state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(AppConstants.REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates );
        outState.putParcelable(AppConstants.LOCATION_KEY, mLastLocation);
        outState.putString(AppConstants.LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(outState);
    }

    //restore instance state
    private void updateValueFromBundle(Bundle savedInstanceState){
        if(savedInstanceState!= null){
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.
            if(savedInstanceState.keySet().contains(AppConstants.REQUESTING_LOCATION_UPDATES_KEY)){
                mRequestingLocationUpdates = savedInstanceState.getBoolean(AppConstants.REQUESTING_LOCATION_UPDATES_KEY);
            }
            if(savedInstanceState.keySet().contains(AppConstants.LOCATION_KEY)){
                mLastLocation = savedInstanceState.getParcelable(AppConstants.LOCATION_KEY);
            }
            if (savedInstanceState.keySet().contains(AppConstants.LAST_UPDATED_TIME_STRING_KEY)){
                mLastUpdateTime = savedInstanceState.getString(AppConstants.LAST_UPDATED_TIME_STRING_KEY);
            }
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


}
