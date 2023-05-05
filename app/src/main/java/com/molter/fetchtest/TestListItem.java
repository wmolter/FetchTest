package com.molter.fetchtest;

import androidx.annotation.NonNull;

public class TestListItem implements Comparable<TestListItem>{

    private final int id;
    private final int listId;

    private final String name;

    public TestListItem(int id, int listId, String name){
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public boolean isNameBlank(){
        return name == null || name.isEmpty() || name.isBlank();
    }

    /**
     * Compare the two objects first based on {@link TestListItem#listId} ascending, and then
     * {@link TestListItem#name}, alphabetically
     * @param other the object to be compared.
     * @return a negative number if this object should be sorted before the parameter object,
     * and a positive number otherwise
     */
    public int compareTo(TestListItem other){
        if(listId - other.listId != 0)
            return listId - other.listId;
        if(name == null && other.name == null)
            return 0;
        if(name == null)
            return -1;
        if(other.name == null)
            return 1;
        return name.compareTo(other.name);
    }

    /**
     *
     * @param otherObject the object to compare
     * @return true if all instance fields of both objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object otherObject){
        if(!(otherObject instanceof TestListItem))
            return false;
        TestListItem other = (TestListItem)otherObject;
        return listId == other.listId && id == other.id && name.equals(other.name);
    }

    @NonNull
    @Override
    public String toString() {
        return id + " " + name + " " + listId;
    }
}
