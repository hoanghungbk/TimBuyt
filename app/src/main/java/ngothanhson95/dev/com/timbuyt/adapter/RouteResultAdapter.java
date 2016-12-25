package ngothanhson95.dev.com.timbuyt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ngothanhson95.dev.com.timbuyt.listener.RecyclerViewClickListener;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.adapter.viewholder.RouteResultVIewHolder;
import ngothanhson95.dev.com.timbuyt.model.direction.Route;

/**
 * Created by sonnt on 12/21/16.
 */

public class RouteResultAdapter extends RecyclerView.Adapter<RouteResultVIewHolder> {

    public ArrayList<Route> routes = new ArrayList<>();

    private RecyclerViewClickListener recyclerViewClickListener;

    public RouteResultAdapter(ArrayList<Route> routes) {
        this.routes = routes;
    }

    @Override
    public RouteResultVIewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route_result, parent, false);
        RouteResultVIewHolder routeResultVIewHolder = new RouteResultVIewHolder(view, recyclerViewClickListener);
        return routeResultVIewHolder;
    }

    @Override
    public void onBindViewHolder(RouteResultVIewHolder holder, int position) {
        holder.setupWith(routes.get(position));
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public void setOnItemClickListener(RecyclerViewClickListener listener){
        this.recyclerViewClickListener = listener;
    }
}
