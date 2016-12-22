package ngothanhson95.dev.com.timbuyt.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ngothanhson95.dev.com.timbuyt.AppConstants;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.adapter.RouteResultAdapter;
import ngothanhson95.dev.com.timbuyt.model.direction.Route;
import ngothanhson95.dev.com.timbuyt.model.direction.Step;

/**
 * Created by sonnt on 12/19/16.
 */

public class BestRouteFragment extends Fragment {
    RecyclerView rvBestRoute;
    ArrayList<Route> routes = new ArrayList<>();
    RouteResultAdapter adapter;

    ArrayList<Step> steps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_best_route, container, false);
        rvBestRoute = (RecyclerView) view.findViewById(R.id.rvBestRoute);
        Bundle b = getArguments();
        if(b!=null){
            routes =  b.getParcelableArrayList(AppConstants.PARCELABLE_ROUTE_KEY);
            Collections.sort(routes, new DistanceComparator());
            adapter = new RouteResultAdapter(routes);
        }
        rvBestRoute.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        rvBestRoute.setAdapter(adapter);
        return view;
    }

    class DistanceComparator implements Comparator<Route>{

        @Override
        public int compare(Route route, Route t1) {
            return Integer.compare(route.getLegs().get(0).getDistance().getValue(), t1.getLegs().get(0).getDistance().getValue());
        }
    }
}
