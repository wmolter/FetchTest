package com.molter.fetchtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TestExpandableAdapter extends BaseExpandableListAdapter {

    /**
     * Holds data used in labeling the headings of the groups, i.e. what is displayed when
     * the lists are collapsed
     */
    public class GroupInfo{
        /**
         * Id of the list this object describes.  Note that this can be different than this group's
         * position in the adapter.
         */
        public int listId;
        /**
         * Index of the first element in {@link #dataList} that has listId {@link #listId}
         */
        public int startIndex;
        /**
         * Number of elements in {@link #dataList} that have listId {@link #listId}
         */
        public int count;
    }

    /**
     * Holds all data elements to be displayed in the expandable view (pulled from the JSON file)
     */
    private ArrayList<TestListItem> dataList;
    /**
     * Holds information used for the headings of each list
     */
    private ArrayList<GroupInfo> groupData;

    public TestExpandableAdapter(){
        dataList = new ArrayList<TestListItem>();
        groupData = new ArrayList<GroupInfo>();

    }

    /**
     * Clears data from the adapter and reinitializes with the new data.
     * @param items the new data to display in the expandable view
     */
    public void replaceDataWith(Stream<TestListItem> items){
        dataList.clear();
        items.forEach(new Consumer<TestListItem>() {
            @Override
            public void accept(TestListItem item) {
                dataList.add(item);
            }
        });
        dataList.sort(new Comparator<TestListItem>() {
            @Override
            public int compare(TestListItem a, TestListItem b) {
                return a.compareTo(b);
            }
        });
        countGroups();
    }

    /**
     * Takes the sorted {@link TestExpandableAdapter#dataList}, counts how many items with each
     * listId, and stores that information in {@link TestExpandableAdapter#groupData}
     */
    private void countGroups(){
        groupData.clear();
        int listStartIndex = 0;
        for(int i = 0; i < dataList.size()-1; i++){
            int currentList = dataList.get(i).getListId();
            if(dataList.get(i+1).getListId() != currentList){
                GroupInfo thisGroup = new GroupInfo();
                thisGroup.listId = currentList;
                thisGroup.startIndex = listStartIndex;
                thisGroup.count = i+1 - listStartIndex;
                groupData.add(thisGroup);
                listStartIndex = i+1;
            }
        }
        GroupInfo lastGroup = new GroupInfo();
        lastGroup.listId = dataList.get(listStartIndex).getListId();
        lastGroup.startIndex = listStartIndex;
        lastGroup.count = dataList.size() - listStartIndex;
        groupData.add(lastGroup);
    }
    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupData.get(groupPosition).count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        int startIndex = groupData.get(groupPosition).startIndex;
        return dataList.get(startIndex+childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ((GroupInfo)getGroup(groupPosition)).listId;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ((TestListItem)getChild(groupPosition, childPosition)).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null || convertView.getId() != R.id.list_heading){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_heading, parent, false);
        }
        Context context = convertView.getContext();
        GroupInfo toDisplay = (GroupInfo)getGroup((groupPosition));
        TextView listNameView = convertView.findViewById(R.id.list_name);
        listNameView.setText(context.getString(R.string.list_header, toDisplay.listId));
        TextView listCountView = convertView.findViewById(R.id.list_count);
        listCountView.setText(context.getString(R.string.list_count, toDisplay.count));
        Log.d("expand", "Got a group view for " + groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null || convertView.getId() != R.id.list_item_display){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_display, parent, false);
        }
        TestListItem toDisplay = (TestListItem)getChild(groupPosition, childPosition);
        TextView nameView = convertView.findViewById(R.id.name);
        TextView idView = convertView.findViewById(R.id.idField);
        nameView.setText(toDisplay.getName());
        idView.setText(idView.getContext().getString(R.string.id_field, toDisplay.getId()));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
