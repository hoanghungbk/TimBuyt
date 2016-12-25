package ngothanhson95.dev.com.timbuyt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ngothanhson95.dev.com.timbuyt.listener.RecyclerViewClickListener;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.adapter.viewholder.BusResultViewHolder;
import ngothanhson95.dev.com.timbuyt.model.TuyenBus;

/**
 * Created by sonnt on 12/25/16.
 */

public class BusResultAdapter extends RecyclerView.Adapter<BusResultViewHolder> {
    private ArrayList<TuyenBus> busList = new ArrayList<>();
    private RecyclerViewClickListener recyclerViewClickListener;

    public BusResultAdapter(ArrayList<TuyenBus> busList) {
        this.busList = busList;
    }

    @Override
    public BusResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_list, parent, false);
        return  new BusResultViewHolder(v, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(BusResultViewHolder holder, int position) {
        holder.setupWith(busList.get(position));
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public void setOnItemClickListener(RecyclerViewClickListener listener){
        this.recyclerViewClickListener = listener;
    }
}
