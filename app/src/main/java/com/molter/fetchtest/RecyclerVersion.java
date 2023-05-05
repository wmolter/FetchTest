package com.molter.fetchtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Unused main Activity for the initial version which displays all the list items in one long,
 * sorted list.  The final version's main Activity is {@link ExpandableMain}.
 * To view this version change the main Activity in the manifest to this Activity.
 */
public class RecyclerVersion extends AppCompatActivity {
    public final String URL = "https://fetch-hiring.s3.amazonaws.com/";
    private RecyclerView listDisplay;

    private TestListAdapter listAdapter;
    private SortedList<TestListItem> dataList;
    private FetchWebData webGetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
        buildWebGetter();
        //populateTestList();
        populateWebList();
    }

    private void initRecycler() {
        listDisplay = findViewById(R.id.recycler);
        listDisplay.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new TestListAdapter();
        listDisplay.setAdapter(listAdapter);

        dataList = listAdapter.getDataList();
    }

    private void buildWebGetter(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webGetter = retrofit.create(FetchWebData.class);
    }

    private void populateWebList(){
        webGetter.GetFetchData().enqueue(new Callback<List<TestListItem>>(){

            @Override
            public void onResponse(Call<List<TestListItem>> call, Response<List<TestListItem>> response) {
                if(response.isSuccessful()) {
                    dataList.clear();
                    response.body().stream()
                            .filter(new Predicate<TestListItem>() {
                                        @Override
                                        public boolean test(TestListItem item) {
                                            return !item.isNameBlank();
                                        }
                                    })
                            .forEach(new Consumer<TestListItem>() {
                                @Override
                                public void accept(TestListItem item) {
                                    dataList.add(item);
                                }
                            });
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
    private void populateTestList(){
        dataList.add(new TestListItem(7, 1, "Test"));
        dataList.add(new TestListItem(4, 1, "Apple"));
        dataList.add(new TestListItem(3, 2, "Ithaca"));
        dataList.add(new TestListItem(18, 2, "Demonstration"));
    }
}