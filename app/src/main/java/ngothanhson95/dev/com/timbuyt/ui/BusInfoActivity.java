package ngothanhson95.dev.com.timbuyt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ngothanhson95.dev.com.timbuyt.AppConstants;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.model.TuyenBus;

/**
 * Created by sonnt on 12/26/16.
 */

public class BusInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvName, tvFrequency, tvBusCost, tvBusiness, tvTimeWork,tvGoOn,tvComeBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName= (TextView) findViewById(R.id.tvBus);
        tvFrequency= (TextView) findViewById(R.id.tvFrequency);
        tvBusCost= (TextView) findViewById(R.id.tvBusCost);
        tvBusiness= (TextView) findViewById(R.id.tvBusiness);
        tvGoOn= (TextView) findViewById(R.id.tvGoOn);
        tvComeBack= (TextView) findViewById(R.id.tvComeBack);
        tvTimeWork= (TextView) findViewById(R.id.tvTimeWork);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.before));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusInfoActivity.this.finish();
            }
        });

        Bundle b = getIntent().getBundleExtra(AppConstants.BUS_KEY);
        if(b!=null) {
            TuyenBus bus = (TuyenBus) b.getSerializable(AppConstants.BUS_KEY);
            setBusInfo(bus);
            getSupportActionBar().setTitle("Tuyến " + bus.getSoTuyen());
        }
    }

    public void setBusInfo(TuyenBus bus){
        if(bus!=null) {
            tvName.setText(bus.getTenTuyen());
            tvFrequency.setText(bus.getTanSuat());
            tvBusCost.setText(bus.getGiaVe());
            tvBusiness.setText(bus.getXiNghiep());
            tvTimeWork.setText(bus.getThoiGianHoatDong());
            tvGoOn.setText(bus.getLoTrinhChieuDi());
            tvComeBack.setText(bus.getLoTrinhChieuVe());
        }
    }
}
