package ngothanhson95.dev.com.timbuyt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ngothanhson on 11/10/16.
 */

public class SearchPlaceActivity extends Activity {

    public static final int PLACE_CODE = 12332;
    public static final String PLACE_KEY = "place";

    ImageView btnBack;
    SearchView etChoosePlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        etChoosePlace = (SearchView) findViewById(R.id.etChoosePlace);

        etChoosePlace.setIconifiedByDefault(false);

        etChoosePlace.setQueryHint(getIntent().getStringExtra(MainActivity.SEARCH_KEY));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchPlaceActivity.this.finish();
            }
        });

        etChoosePlace.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(SearchPlaceActivity.this, MainActivity.class);
                intent.putExtra(PLACE_KEY, query);
                setResult(Activity.RESULT_OK , intent);
                finish();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}

