package com.interview.farm.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    @Test
    public void isEmpty_should_return_true_on_new_list() {
        var list = new LinkedList<>();

        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmpty_should_return_true_if_list_contains_elements_1() {
        var list = new LinkedList<>();

        list.add(new Object());

        assertFalse(list.isEmpty());
    }

    @Test
    public void isEmpty_should_return_true_if_list_contains_elements_2() {
        var list = new LinkedList<>();

        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());

        assertFalse(list.isEmpty());
    }

    @Test
    public void isEmpty_should_return_true_after_adding_and_then_deleting_element() {
        var list = new LinkedList<>();

        var el = new Object();
        list.add(el);
        list.delete(el);

        assertTrue(list.isEmpty());
    }


    @Test
    public void find_should_return_element_by_predicate() {
        var list = new LinkedList<Integer>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        int five = list.find(integer -> integer > 4);
        int one = list.find(integer -> integer < 2);
        int three = list.find(integer -> integer == 3);

        assertEquals(5, five);
        assertEquals(1, one);
        assertEquals(3, three);
    }

    @Test
    public void elements_should_be_placed_and_retrieved_in_order() {
        var intSource = new int[] {1, 2, 3, 4};

        var list = new LinkedList<Integer>();

        for(int i : intSource) list.add(i);

        int i = 0;
        for (var el : list) {
            assertEquals(intSource[i], el);
            i++;
        }
    }
}