package ngothanhson95.dev.com.timbuyt.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ngothanhson95.dev.com.timbuyt.listener.RecyclerViewClickListener;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.model.TuyenBus;

/**
 * Created by sonnt on 12/25/16.
 */

public class BusResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvBusNo;
    TextView tvBusName;
    private RecyclerViewClickListener recyclerViewClickListener;

    public BusResultViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        tvBusNo = (TextView) itemView.findViewById(R.id.tvBusNo);
        tvBusName = (TextView) itemView.findViewById(R.id.tvBusName);
        this.recyclerViewClickListener = listener;
        itemView.setOnClickListener(this);
    }

    public void setupWith(TuyenBus bus){
        tvBusNo.setText(bus.getSoTuyen());
        tvBusName.setText(bus.getTenTuyen());
    }

    @Override
    public void onClick(View view) {
        if(recyclerViewClickListener != null){
            recyclerViewClickListener.onItemClick(view, getPosition());
        }
    }
}
