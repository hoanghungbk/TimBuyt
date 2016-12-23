package ngothanhson95.dev.com.timbuyt.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.model.direction.Route;
import ngothanhson95.dev.com.timbuyt.model.direction.Step;

/**
 * Created by sonnt on 12/21/16.
 */

public class RouteResultVIewHolder extends RecyclerView.ViewHolder {

    TextView tvDistance, tvWalking, tvBusChange, tvTimeDuration, tvCost;
    ImageView img0, img1, img2, img3, img4, img5, img6, img7, img8;
    int busChange = 0, distanceWalking =0;

    public RouteResultVIewHolder(View itemView) {
        super(itemView);
        tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
        tvBusChange = (TextView) itemView.findViewById(R.id.tvBusChange);
        tvWalking = (TextView) itemView.findViewById(R.id.tvWalking);
        tvTimeDuration = (TextView) itemView.findViewById(R.id.tvTimeDuration);
        tvCost = (TextView) itemView.findViewById(R.id.tvCost);

        img0 = (ImageView) itemView.findViewById(R.id.img0);
        img1 = (ImageView) itemView.findViewById(R.id.img1);
        img2 = (ImageView) itemView.findViewById(R.id.img2);
        img3 = (ImageView) itemView.findViewById(R.id.img3);
        img4 = (ImageView) itemView.findViewById(R.id.img4);
        img5 = (ImageView) itemView.findViewById(R.id.img5);
        img6 = (ImageView) itemView.findViewById(R.id.img6);
        img7 = (ImageView) itemView.findViewById(R.id.img7);
        img8 = (ImageView) itemView.findViewById(R.id.img8);
    }

    public void setupWith(Route route){
        if(route!=null) {
            tvDistance.setText(route.legs.get(0).distance.text);
            tvTimeDuration.setText(route.getLegs().get(0).duration.text);
            tvCost.setText(route.getFare().value);

            List<Step> steps = route.legs.get(0).getSteps();

            // display route steps icon
            if(steps.size()>0){
                if (steps.get(0).travelMode.equals("WALKING")){
                    img0.setImageResource(R.drawable.person_walking);
                } else {
                    img0.setImageResource(R.drawable.bus_white);
                }
            }

            if(steps.size()>1){
                if (steps.get(1).travelMode.equals("WALKING")){
                    img2.setImageResource(R.drawable.person_walking);
                } else {
                    img2.setImageResource(R.drawable.bus_white);
                }
                img1.setVisibility(View.VISIBLE);
            }

            if(steps.size()>2){
                if (steps.get(2).travelMode.equals("WALKING")){
                    img4.setImageResource(R.drawable.person_walking);
                } else {
                    img4.setImageResource(R.drawable.bus_white);
                }
                img3.setVisibility(View.VISIBLE);
            }

            if(steps.size()>3){
                if (steps.get(3).travelMode.equals("WALKING")){
                    img6.setImageResource(R.drawable.person_walking);
                } else {
                    img6.setImageResource(R.drawable.bus_white);
                }
                img5.setVisibility(View.VISIBLE);
            }

            if(steps.size()>4){
                if (steps.get(4).travelMode.equals("WALKING")){
                    img8.setImageResource(R.drawable.person_walking);
                } else {
                    img8.setImageResource(R.drawable.bus_white);
                }
                img7.setVisibility(View.VISIBLE);
            }


            //get walking direction
            for ( Step step : steps) {
                if(step.travelMode.equals("WALKING")){
                    distanceWalking += step.distance.value;
                }
                if(step.travelMode.equals("TRANSIT")){
                    busChange +=1;
                }
            }

            tvWalking.setText(String.valueOf(distanceWalking) + " m");
            tvBusChange.setText(String.valueOf(busChange));

        }

    }
}
