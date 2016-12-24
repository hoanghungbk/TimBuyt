package ngothanhson95.dev.com.timbuyt;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 12/23/2016.
 */

public class CustomAdapter extends ArrayAdapter<TuyenBus> {
    private Activity activity;
    private int idLayout;
    private ArrayList<TuyenBus> busList;

    public CustomAdapter(Activity activity, int idLayout, ArrayList<TuyenBus> busList){
        super(activity, idLayout, busList);
        this.activity = activity;
        this.idLayout = idLayout;
        this.busList = busList;
    }
    public View getView(int position, View convertView, ViewGroup parent ){
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(idLayout, null);
        TextView tenBus = (TextView) convertView.findViewById(R.id.tenBus);
        TextView busInfo = (TextView) convertView.findViewById(R.id.busInfo);
        tenBus.setText(busList.get(position).getTenTuyen());

        busInfo.setText("Xí nghiệp: "+busList.get(position).getXiNghiep()+"\n"
        + "Tần xuất: "+busList.get(position).getTanSuat()+"\n"
        + "Thời gian hoạt động: "+busList.get(position).getThoiGianHoatDong()+"\n"
        + "Giá vé: "+busList.get(position).getGiaVe()+"\n"
        + "Lộ trình chiều đi: "+busList.get(position).getLoTrinhChieuDi()+"\n"
        + "Lộ trình chiều về: "+busList.get(position).getLoTrinhChieuVe());

        return convertView;
    }
}
