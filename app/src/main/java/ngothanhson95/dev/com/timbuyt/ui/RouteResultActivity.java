package ngothanhson95.dev.com.timbuyt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ngothanhson95.dev.com.timbuyt.AppConstants;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.adapter.RouteResultTabAdapter;
import ngothanhson95.dev.com.timbuyt.model.direction.DirectionJSON;
import ngothanhson95.dev.com.timbuyt.model.direction.Route;
import ngothanhson95.dev.com.timbuyt.network.RequestInterface;
import ngothanhson95.dev.com.timbuyt.ui.fragment.BestRouteFragment;
import ngothanhson95.dev.com.timbuyt.ui.fragment.LessChangeBusFragment;
import ngothanhson95.dev.com.timbuyt.ui.fragment.LessWalkingFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sonnt on 12/19/16.
 */

public class RouteResultActivity extends AppCompatActivity {
    ViewPager vpRouteResult;
    TabLayout tabRouteResult;
    Button btnOrigin, btnDestination;
    RouteResultTabAdapter adapter;
    String origin, destination;
    String originName, destinationName;
    ArrayList<Route> routes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_result);
        vpRouteResult = (ViewPager) findViewById(R.id.vpRouResult);
        tabRouteResult = (TabLayout) findViewById(R.id.tabsRouteResult);
        btnOrigin = (Button) findViewById(R.id.btnOrigin);
        btnDestination = (Button) findViewById(R.id.btnDestination);

        origin = getIntent().getStringExtra(AppConstants.ORIGIN_KEY);
        destination = getIntent().getStringExtra(AppConstants.DESTINATION_KEY);
        originName = getIntent().getStringExtra(AppConstants.ORIGIN_NAME_KEY);
        destinationName = getIntent().getStringExtra(AppConstants.DESTINATION_NAME_KEY);

        btnOrigin.setText(originName);
        btnDestination.setText(destinationName);

        loadRouteJson();
    }

    public void onBtnBeforeClick(View view){
        this.finish();
    }

        public void loadRouteJson(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RequestInterface requestInterfac= retrofit.create(RequestInterface.class);
            Call<DirectionJSON> call = requestInterfac.getAllRoute(origin, destination);
            call.enqueue(new Callback<DirectionJSON>() {
                @Override
                public void onResponse(Call<DirectionJSON> call, Response<DirectionJSON> response) {
                    DirectionJSON directionJSON = response.body();
                    routes = new ArrayList<>(directionJSON.getRoutes().size());
                    routes.addAll(directionJSON.getRoutes());

                    adapter = new RouteResultTabAdapter(getSupportFragmentManager());
                    BestRouteFragment bestRouteFragment = new BestRouteFragment();
                    LessChangeBusFragment lessChangeBusFragment = new LessChangeBusFragment();
                    LessWalkingFragment lessWalkingFragment = new LessWalkingFragment();
                    adapter.addFragment(bestRouteFragment, "Quãng đường ngắn nhất");
                    adapter.addFragment(lessChangeBusFragment, "Chuyển tuyến ít nhất");
                    adapter.addFragment(lessWalkingFragment, "Đi bộ ít nhất");

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(AppConstants.PARCELABLE_ROUTE_KEY, routes);
                    bestRouteFragment.setArguments(bundle);
                    lessWalkingFragment.setArguments(bundle);
                    lessChangeBusFragment.setArguments(bundle);

                    vpRouteResult.setAdapter(adapter);
                    tabRouteResult.setupWithViewPager(vpRouteResult);
                }
                @Override
                public void onFailure(Call<DirectionJSON> call, Throwable t) {
                }
            });
        }
}
