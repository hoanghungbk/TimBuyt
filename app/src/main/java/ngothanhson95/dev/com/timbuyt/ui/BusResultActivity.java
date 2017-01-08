package ngothanhson95.dev.com.timbuyt.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ngothanhson95.dev.com.timbuyt.AppConstants;
import ngothanhson95.dev.com.timbuyt.BusDBHandle;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.adapter.BusResultAdapter;
import ngothanhson95.dev.com.timbuyt.listener.RecyclerViewClickListener;
import ngothanhson95.dev.com.timbuyt.model.TuyenBus;
import ngothanhson95.dev.com.timbuyt.model.TuyenBusJSON;
import ngothanhson95.dev.com.timbuyt.model.direction.DirectionJSON;
import ngothanhson95.dev.com.timbuyt.network.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sonnt on 12/24/16.
 */

public class BusResultActivity extends AppCompatActivity implements RecyclerViewClickListener{
    BusDBHandle busdb ;
    Toolbar toolbar;
    RecyclerView rvBustList;
    TextView tvNodata;
    ArrayList<TuyenBus> buses = new ArrayList<>();
    BusResultAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        rvBustList = (RecyclerView) findViewById(R.id.rvBusList);
        tvNodata = (TextView) findViewById(R.id.tvNoData);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tra cứu các tuyến Bus");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.before));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusResultActivity.this.finish();
            }
        });

        busdb = new BusDBHandle(this);
        rvBustList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        loadData();
    }

    private void loadData(){
        buses = busdb.getAllBus();
        if(buses.size()>0){
            tvNodata.setVisibility(View.INVISIBLE);
            adapter = new BusResultAdapter(buses);
            this.adapter.setOnItemClickListener(this);
            rvBustList.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, BusInfoActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(AppConstants.BUS_KEY, buses.get(position));
        intent.putExtra(AppConstants.BUS_KEY, b);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bus_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.dataLoad:
                busdb.updateTable();
               // JSOUP
//                new CrawlAsyncTask().execute();
//                loadData();

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Đang tải dữ liệu");
                progressDialog.show();
                loadTuyenBusJSON();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadTuyenBusJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://glacial-sea-31753.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterfac= retrofit.create(RequestInterface.class);
        Call<TuyenBusJSON> call = requestInterfac.getAllTuyenBus();
        call.enqueue(new Callback<TuyenBusJSON>() {
            @Override
            public void onResponse(Call<TuyenBusJSON> call, Response<TuyenBusJSON> response) {
                TuyenBusJSON tuyenBusJSON = response.body();
                buses = new ArrayList<>(tuyenBusJSON.getResult().size());
                buses.addAll(tuyenBusJSON.getResult());
                for(TuyenBus bus: buses){
                    busdb.addBus(bus);
                }
                progressDialog.dismiss();
                loadData();
            }

            @Override
            public void onFailure(Call<TuyenBusJSON> call, Throwable t) {

            }
        });
    }

//    public class CrawlAsyncTask extends AsyncTask<Void, Void, Void> {
//        ProgressDialog pb = new ProgressDialog(BusResultActivity.this);
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pb.setMessage("Đang tải dữ liệu..");
//            pb.setCancelable(false);
//            pb.show();
//        }
//
//        protected Void doInBackground(Void... params){
//            List<String> title = new ArrayList<>();
//            List<String> content = new ArrayList<>();
//            TuyenBus b;
//            try{
//                Document doc = Jsoup.connect("http://timbus.vn/fleets.aspx").data("query","Java").userAgent("Mozilla").cookie("auth","token").timeout(5000).post();
//                Elements elements = doc.getElementsByTag("span");
//                for (Element e:elements){
//                    if (e.hasAttr("id")){
//                        if (e.attr("id").trim().equals("lbFleetList")){
//                            Element table=e.child(0);
//                            Element tbody = table.child(0);
//                            Elements tbodyChild = tbody.children();
//                            for (Element _e: tbodyChild){
//                                if (_e.tagName().trim().equals("tr")){
//                                    Elements td = _e.children();
//                                    for (Element t:td){
//                                        if (t.hasAttr("class")){
//                                            if(t.attr("class").trim().equals("m-fleet-title")){
//                                                title.add(t.ownText());
//                                            }
//                                            else if (t.attr("class").trim().equals("m-fleet-item-content")){
//                                                content.add(t.ownText());
//                                            }
//                                        }
//
//                                    }
//
//                                }
//                            }
//
//                        }
//
//                    }
//                }
//
//                for(int i =0; i< title.size(); i++){
//                    int x = title.get(i).indexOf("[");
//                    int y = title.get(i).indexOf("]");
//                    TuyenBus newBus = new TuyenBus(
//                                                title.get(i).substring(x+1,y),
//                                                title.get(i).substring(y+2),
//                                                content.get(i*6),
//                                                content.get(i*6+1),
//                                                content.get(i*6+2),
//                                                content.get(i*6+3),
//                                                content.get(i*6+4),
//                                                content.get(i*6+5));
//                    busdb.addBus(newBus);
//                    buses.add(newBus);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            pb.dismiss();
//            loadData();
//        }
//    }
}
