package ngothanhson95.dev.com.timbuyt;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Asus on 12/24/2016.
 */

public class Help_Fragment extends AppCompatActivity {

    TabLayout tabHelp;
    BusDBHandle busdb ;
    ListView listView;
    private ArrayList<TuyenBus> bus_list;
    private CustomAdapter adapter = null;
    private ArrayAdapter<TuyenBus> listViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        listView = (ListView) findViewById(R.id.listView);
        busdb = new BusDBHandle(this);
        //tabHelp = (TabLayout) findViewById(R.id.tabsHelpResult);

    }
    public  void show(){
        bus_list = busdb.getAllBus();
        adapter = new CustomAdapter(this, R.layout.list_bus, bus_list);
        listView.setAdapter(adapter);
        Toast.makeText(this, "Đã cập nhật dữ liệu mới", Toast.LENGTH_SHORT).show();
    }

    public  void onBtnBeforeClick1(View view){
        this.finish();
    }
    public void onBtnCrawl(View view){
        Toast.makeText(this,"crawl",Toast.LENGTH_SHORT).show();


        busdb.updateTable();
        new dolt().execute();
    }
    public void onBtnShow(View view){

        Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
        bus_list = busdb.getAllBus();
        adapter = new CustomAdapter(this, R.layout.list_bus, bus_list);
        listView.setAdapter(adapter);
        Toast.makeText(this, "Đã cập nhật dữ liệu mới", Toast.LENGTH_SHORT).show();

    }
    public class dolt extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params){
            Vector bus = new Vector();
            TuyenBus b;
            try{
                Document doc = Jsoup.connect("http://timbus.vn/fleets.aspx").data("query","Java").userAgent("Mozilla").cookie("auth","token").timeout(5000).post();
                Elements elements = doc.getElementsByTag("span");
                for (Element e:elements){
                    if (e.hasAttr("id")){
                        if (e.attr("id").trim().equals("lbFleetList")){
                            Element table=e.child(0);
                            Element tbody = table.child(0);
                            Elements tbodyChild = tbody.children();
                            for (Element _e: tbodyChild){
                                if (_e.tagName().trim().equals("tr")){
                                    Elements td = _e.children();
                                    for (Element t:td){
                                        if (t.hasAttr("class")){
                                            if(t.attr("class").trim().equals("m-fleet-title")){
                                                if(bus.size()==7){
                                                    System.out.print("over here --------> " + bus);
                                                    b = new TuyenBus(bus.get(0).toString(),bus.get(1).toString(),bus.get(2).toString(),bus.get(3).toString(),bus.get(4).toString(),bus.get(5).toString(),bus.get(6).toString());
                                                    busdb.addBus(b);
                                                    bus=new Vector();

                                                }
                                                bus.addElement(t.ownText().substring(7));

                                            }
                                            else if (t.attr("class").trim().equals("m-fleet-item-content")){
                                                bus.addElement(t.ownText());
                                                System.out.println(t.ownText());
                                                // System.out.print(bus);
                                            }

                                        }

                                    }

                                }
                            }

                        }

                    }

                }
                System.out.print(bus);
                b = new TuyenBus(bus.get(0).toString(),bus.get(1).toString(),bus.get(2).toString(),bus.get(3).toString(),bus.get(4).toString(),bus.get(5).toString(),bus.get(6).toString());
                busdb.addBus(b);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //textView.setText(words);
        }
    }

    }
