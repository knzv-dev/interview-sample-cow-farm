package com.interview.farm.domain;

import com.interview.farm.util.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractFarmTest {

    @Spy
    AbstractFarm farm;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void giveBirth_should_save_even_if_no_parent_id_provided() {
        farm.giveBirth(null, "1", "N");

        Mockito.verify(farm).saveCow(new Cow().setId("1").setNickname("N"));
    }

    @Test
    public void giveBirth_should_set_new_child_to_parent_if_parent_id_is_provided_and_save_new_cow() {
        Cow parentCow = new Cow().setId("1").setNickname("P");
        Mockito.when(farm.findCowById("1")).thenReturn(parentCow);

        farm.giveBirth("1", "2", "N");

        Mockito.verify(farm).saveCow(new Cow().setId("2").setNickname("N"));
        assertEquals(List.of("2"), parentCow.getChildren());
    }

    @Test
    public void giveBirth_should_throw_exception_if_parent_cow_id_is_blank() {
        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth("", "1", "N");
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth("   ", "1", "N");
        });
    }

    @Test
    public void giveBirth_should_throw_exception_if_child_cow_id_is_empty_or_null() {
        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth(null, null, "N");
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth(null, "", "N");
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth(null, "   ", "N");
        });
    }

    @Test
    public void giveBirth_should_throw_exception_if_nickname_is_empty_or_null() {
        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth(null, "1", null);
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth(null, "2", "");
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth(null, "3", "    ");
        });
    }

    @Test
    public void giveBirth_should_throw_exception_if_parent_id_equals_to_child_id() {
        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth("1", "1", "N");
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth("100", "100", "N");
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.giveBirth("ABC", "ABC", "N");
        });
    }

    @Test
    public void giveBirth_should_throw_exception_if_cow_with_requested_parent_id_does_not_exists() {
        Mockito.when(farm.findCowById("1")).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> {
            farm.giveBirth("1", "2", "N");
        });
    }

    @Test
    public void giveBirth_should_throw_exception_if_parent_already_have_child_with_requested_id() {
        Mockito.when(farm.findCowById("1")).thenReturn(
                new Cow()
                        .setId("1")
                        .addChild("2"));

        assertThrows(IllegalStateException.class, () -> {
            farm.giveBirth("1", "2", "N");
        });
    }

    @Test
    public void endLifeSpan_should_delete_cow_by_id() {
        Cow cow = new Cow();
        Mockito.when(farm.findCowById("1")).thenReturn(cow);

        farm.endLifeSpan("1");

        Mockito.verify(farm).deleteCow(cow);
    }

    @Test
    public void endLifeSpan_should_remove_child_cow_from_living_parent() {
        Cow cow = new Cow().setId("1");
        Cow parent = new Cow().setId("0").addChild("1").addChild("3");

        Mockito.when(farm.findCowById("1")).thenReturn(cow);
        Mockito.when(farm.findParentByChildId("1")).thenReturn(parent);

        farm.endLifeSpan("1");

        assertEquals(1, parent.getChildren().size());
    }

    @Test
    public void endLifeSpan_should_rebase_children_to_nearest_grandparent() {
        Cow child3 = new Cow().setId("3");
        Cow child2 = new Cow().setId("2");
        Cow parent = new Cow().setId("1").addChild("2").addChild("3");
        Cow grandparent = new Cow().setId("0").addChild("1");

        Mockito.when(farm.findCowById("1")).thenReturn(parent);
        Mockito.when(farm.findParentByChildId("1")).thenReturn(grandparent);

        farm.endLifeSpan("1");

        assertEquals(2, parent.getChildren().size());
        assertTrue(grandparent.getChildren().contains(child2.getId()));
        assertTrue(grandparent.getChildren().contains(child3.getId()));
    }

    @Test
    public void endLifeSpan_should_throw_exception_if_cow_with_provided_id_does_not_exists() {
        Mockito.when(farm.findCowById("1")).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> {
            farm.endLifeSpan("1");
        });
    }

    @Test
    public void endLifeSpan_should_throw_exception_if_cow_id_is_null_or_empty() {
        assertThrows(InvalidParameterException.class, () -> {
            farm.endLifeSpan(null);
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.endLifeSpan("");
        });

        assertThrows(InvalidParameterException.class, () -> {
            farm.endLifeSpan("   ");
        });
    }

    @Test
    public void printFarmDate_should_delegate_work_to_printer_object() throws IOException {
        Printer<Iterable<Cow>> printer = Mockito.mock(Printer.class);

        Mockito.when(farm.isStorageEmpty()).thenReturn(false);
        Mockito.when(farm.getPrinter()).thenReturn(printer);

        List<Cow> cows = List.of(new Cow(), new Cow());
        Mockito.when(farm.storageIterable()).thenReturn(cows);

        farm.printFarmData();

        Mockito.verify(printer).print(cows);
    }

    @Test
    public void printFarmData_should_print_another_message_if_there_is_no_data() throws IOException {
        Printer<Iterable<Cow>> printer = Mockito.mock(Printer.class);

        Mockito.when(farm.isStorageEmpty()).thenReturn(true);
        Mockito.when(farm.getPrinter()).thenReturn(printer);

        farm.printFarmData();

        Mockito.verify(printer).print("No data");
    }
}