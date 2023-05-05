package com.molter.fetchtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Main Activity for the final version with expandable lists to show the different listIds in the
 * data. {@link RecyclerVersion} is an unused activity for the initial version that showed all
 * of them in one long list.
 */
public class ExpandableMain extends AppCompatActivity {
    public final String URL = "https://fetch-hiring.s3.amazonaws.com/";

    private ExpandableListView expandableDisplay;
    private TestExpandableAdapter expandableAdapter;
    private FetchWebData webGetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initExpandable();
        buildWebGetter();
        //populateTestList();
        populateWebList();
    }


    private void initExpandable(){
        expandableDisplay = findViewById(R.id.expandable);
        expandableAdapter = new TestExpandableAdapter();
        expandableDisplay.setAdapter(expandableAdapter);
    }

    /**
     * Initializes the library Retrofit to call to the Fetch data website and to convert that
     * JSON data directly into a Java object ({@link TestListItem})
     */
    private void buildWebGetter(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webGetter = retrofit.create(FetchWebData.class);
    }

    /**
     * Asynchronously queries the URL of the data, filters out the blank name fields,
     * and puts it into the {@link TestExpandableAdapter} for display in an
     * {@link ExpandableListView}
     */
    private void populateWebList(){
        webGetter.GetFetchData().enqueue(new Callback<List<TestListItem>>(){

            @Override
            public void onResponse(Call<List<TestListItem>> call, Response<List<TestListItem>> response) {
                if(response.isSuccessful()) {
                    expandableAdapter.replaceDataWith(
                            response.body().stream()
                                    .filter(new Predicate<TestListItem>() {
                                        @Override
                                        public boolean test(TestListItem item) {
                                            return !item.isNameBlank();
                                        }
                                    }));
                    expandableAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.list_error, Toast.LENGTH_SHORT).show();
                    Log.e("test", "Failed to parse objects");
                }
            }

            @Override
            public void onFailure(Call<List<TestListItem>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                Log.e("test", "Failed to connect.");
            }
        });
    }

}