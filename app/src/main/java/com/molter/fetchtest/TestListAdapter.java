package com.molter.fetchtest;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;

/**
 * Class only used in the {@link RecyclerVersion} Activity which uses {@link SortedList} to easily
 * store, sort, and display the list data
 */
public class TestListAdapter extends RecyclerView.Adapter<ListItemDisplay>{
    private SortedList<TestListItem> dataList;


    public TestListAdapter() {
        this.dataList = new SortedList<> (TestListItem.class, new SortedListAdapterCallback<TestListItem>(this) {
            @Override
            public int compare(TestListItem a, TestListItem b) {
                return a.compareTo(b);
            }

            @Override
            public boolean areContentsTheSame(TestListItem a, TestListItem b) {
                return a.equals(b);
            }

            @Override
            public boolean areItemsTheSame(TestListItem a, TestListItem b) {
                return a == b;
            }
        });

    }

    @Override
    public ListItemDisplay onCreateViewHolder(ViewGroup parent, int viewType) {
        return ListItemDisplay.create(parent);
    }

    @Override
    public void onBindViewHolder(ListItemDisplay holder, int position) {
        holder.SetDisplay(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public SortedList<TestListItem> getDataList() {
        return dataList;
    }
}
