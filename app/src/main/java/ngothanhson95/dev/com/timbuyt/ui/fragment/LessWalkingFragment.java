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

public class LessWalkingFragment extends Fragment {
    RecyclerView rvLessWalking;
    ArrayList<Route> routes = new ArrayList<>();
    RouteResultAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_less_walking, container, false);
        rvLessWalking = (RecyclerView) v.findViewById(R.id.rvLessWalking);
        Bundle b = getArguments();
        if(b!=null){
            routes =  b.getParcelableArrayList(AppConstants.PARCELABLE_ROUTE_KEY);
            Collections.sort(routes, new LessWalkingComparator());
            adapter = new RouteResultAdapter(routes);
        }
        rvLessWalking.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        rvLessWalking.setAdapter(adapter);
        return v;
    }

    class LessWalkingComparator implements Comparator<Route> {

        @Override
        public int compare(Route route, Route t1) {
            int w1 = 0, w2 = 0;
            for(Step step: route.getLegs().get(0).getSteps()){
                if(step.travelMode.equals("WALKING")){
                    w1 += step.distance.value;
                }
            }
            for(Step step: t1.getLegs().get(0).getSteps()){
                if(step.travelMode.equals("WALKING")){
                    w2   += step.distance.value;
                }
            }
            return Integer.compare(w1,w2);
        }
    }

}
