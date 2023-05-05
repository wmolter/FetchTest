package com.molter.fetchtest;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.stream.Stream;

import javax.xml.transform.stream.StreamSource;

/**
 * Tests for a few of the unit-testable functions and methods
 */
public class UnitTests {
    @Test
    public void sameListCompare() {
        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem banana = new TestListItem(2, 1, "banana");

        assertTrue(apple.compareTo(banana) < 0);
        assertTrue(banana.compareTo(apple) > 0);

        TestListItem coconut = new TestListItem(3, 2, "coconut");
        TestListItem durian = new TestListItem(4, 2, "durian");


        assertTrue(coconut.compareTo(durian) < 0);
        assertTrue(durian.compareTo(coconut) > 0);
    }

    @Test
    public void nullIdCompare(){
        TestListItem null1  = new TestListItem(7, 1, null);
        TestListItem null2 = new TestListItem(8, 2, null);
        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem coconut = new TestListItem(3, 2, "coconut");

        assertTrue(null1.compareTo(apple) < 0);
        assertTrue(null1.compareTo(coconut) < 0);
        assertTrue(null1.compareTo(null2) < 0);

        assertTrue(apple.compareTo(null1) > 0);
        assertTrue(coconut.compareTo(null1) > 0);
        assertTrue(null2.compareTo(null1) > 0);
        assertEquals(0, null1.compareTo(null1));

        assertTrue(null2.compareTo(apple) > 0);
        assertTrue( null2.compareTo(coconut) < 0);
        assertTrue(apple.compareTo(null2) < 0);
        assertTrue(coconut.compareTo(null2) > 0);
    }

    @Test
    public void blankIdCompare(){
        TestListItem blank1 = new TestListItem(5, 1, "");
        TestListItem blank2 = new TestListItem(6, 2, "");
        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem coconut = new TestListItem(3, 2, "coconut");

        assertTrue(blank1.compareTo(apple) < 0);
        assertTrue(blank1.compareTo(coconut) < 0);
        assertTrue(blank1.compareTo(blank2) < 0);
        assertTrue(apple.compareTo(blank1) > 0);
        assertTrue(coconut.compareTo(blank1) > 0);
        assertTrue(blank2.compareTo(blank1) > 0);
        assertEquals(0, blank2.compareTo(blank2));

        assertTrue(blank2.compareTo(apple) > 0);
        assertTrue(blank2.compareTo(coconut) < 0);
        assertTrue(apple.compareTo(blank2) < 0);
        assertTrue(coconut.compareTo(blank2) > 0);

    }

    @Test
    public void diffListCompare(){

        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem banana = new TestListItem(2, 1, "banana");

        TestListItem coconut = new TestListItem(3, 2, "coconut");
        TestListItem durian = new TestListItem(4, 2, "durian");

        assertTrue(apple.compareTo(coconut) < 0);
        assertTrue(apple.compareTo(durian) < 0);
        assertTrue(banana.compareTo(coconut) < 0);
        assertTrue(banana.compareTo(durian) < 0);

        assertTrue(coconut.compareTo(apple) > 0);
        assertTrue(coconut.compareTo(banana) > 0);
        assertTrue(durian.compareTo(apple) > 0);
        assertTrue(durian.compareTo(banana) > 0);
    }

    @Test
    public void equalCompare(){

        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem apple2 = new TestListItem(1, 1, "apple");
        assertEquals(0, apple.compareTo(apple2));
        assertEquals(0, apple2.compareTo(apple));


        TestListItem banana = new TestListItem(2, 1, "banana");
        TestListItem diffId = new TestListItem(3, 1, "banana");
        assertEquals(0, banana.compareTo(diffId));
        assertEquals(0, diffId.compareTo(banana));
    }

    @Test
    public void nameBlankNull(){
        TestListItem null1  = new TestListItem(7, 1, null);
        TestListItem null2 = new TestListItem(8, 2, null);

        assertTrue(null1.isNameBlank());
        assertTrue(null2.isNameBlank());
    }

    @Test
    public void nameBlankEmpty(){
        TestListItem blank1 = new TestListItem(5, 1, "");
        TestListItem blank2 = new TestListItem(6, 2, "");
        assertTrue(blank1.isNameBlank());
        assertTrue(blank2.isNameBlank());
    }

    @Test
    public void nameBlankWhitespace(){
        TestListItem white1 = new TestListItem(9, 5, "    ");
        TestListItem white2 = new TestListItem(10, 7, "\n\t");
        assertTrue(white1.isNameBlank());
        assertTrue(white2.isNameBlank());
    }

    @Test
    public void nameBlankNot(){
        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem banana = new TestListItem(2, 1, "banana");
        assertFalse(apple.isNameBlank());
        assertFalse(banana.isNameBlank());
    }

    @Test
    public void groupCount(){
        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem banana = new TestListItem(2, 1, "banana");
        TestListItem coconut = new TestListItem(3, 2, "coconut");
        TestListItem durian = new TestListItem(4, 2, "durian");
        TestListItem elderberry = new TestListItem(5, 5, "elderberry");
        TestListItem fig = new TestListItem(6, 10, "fig");

        TestExpandableAdapter adapter = new TestExpandableAdapter();
        ArrayList<TestListItem> items = new ArrayList<>();

        items.add(coconut);
        adapter.replaceDataWith(items.stream());
        assertEquals(1, adapter.getGroupCount());

        items.add(banana);
        items.add(apple);
        items.add(durian);
        adapter.replaceDataWith(items.stream());
        assertEquals(2, adapter.getGroupCount());

        items.add(fig);
        items.add(elderberry);
        adapter.replaceDataWith(items.stream());
        assertEquals(4, adapter.getGroupCount());
    }

    @Test
    public void groupSort(){

        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem banana = new TestListItem(2, 1, "banana");
        TestListItem coconut = new TestListItem(3, 2, "coconut");
        TestListItem durian = new TestListItem(4, 2, "durian");
        TestListItem elderberry = new TestListItem(5, 5, "elderberry");
        TestListItem fig = new TestListItem(6, 10, "fig");

        TestExpandableAdapter adapter = new TestExpandableAdapter();
        ArrayList<TestListItem> items = new ArrayList<>();

        items.add(coconut);
        items.add(banana);
        items.add(apple);
        items.add(durian);
        adapter.replaceDataWith(items.stream());
        assertEquals(1, adapter.getGroupId(0));
        assertEquals(2, adapter.getGroupId(1));

        items.add(fig);
        items.add(elderberry);
        adapter.replaceDataWith(items.stream());
        assertEquals(1, adapter.getGroupId(0));
        assertEquals(2, adapter.getGroupId(1));
        assertEquals(5, adapter.getGroupId(2));
        assertEquals(10, adapter.getGroupId(3));
    }

    @Test
    public void childCount(){
        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem banana = new TestListItem(2, 1, "banana");
        TestListItem coconut = new TestListItem(3, 2, "coconut");
        TestListItem durian = new TestListItem(4, 2, "durian");
        TestListItem elderberry = new TestListItem(5, 5, "elderberry");
        TestListItem fig = new TestListItem(6, 10, "fig");
        TestListItem grape = new TestListItem(7, 2, "grape");


        TestExpandableAdapter adapter = new TestExpandableAdapter();
        ArrayList<TestListItem> items = new ArrayList<>();

        items.add(coconut);
        items.add(banana);
        items.add(apple);
        items.add(durian);
        items.add(fig);
        items.add(elderberry);
        items.add(grape);
        adapter.replaceDataWith(items.stream());
        assertEquals(2, adapter.getChildrenCount(0));
        assertEquals(3, adapter.getChildrenCount(1));
        assertEquals(1, adapter.getChildrenCount(2));
        assertEquals(1, adapter.getChildrenCount(3));
    }

    @Test
    public void childSort(){
        TestListItem apple = new TestListItem(1, 1, "apple");
        TestListItem banana = new TestListItem(2, 1, "banana");
        TestListItem coconut = new TestListItem(3, 2, "coconut");
        TestListItem durian = new TestListItem(4, 2, "durian");
        TestListItem grape = new TestListItem(7, 2, "grape");


        TestExpandableAdapter adapter = new TestExpandableAdapter();
        ArrayList<TestListItem> items = new ArrayList<>();

        items.add(coconut);
        items.add(banana);
        items.add(apple);
        items.add(durian);
        items.add(grape);
        adapter.replaceDataWith(items.stream());
        assertEquals(1, adapter.getChildId(0, 0));
        assertEquals(2, adapter.getChildId(0, 1));
        assertEquals(3, adapter.getChildId(1, 0));
        assertEquals(4, adapter.getChildId(1, 1));
        assertEquals(7, adapter.getChildId(1, 2));
    }
}