package com.interview.farm.util;

import com.interview.farm.domain.Cow;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeCowPrinterTest {

    @Test
    public void should_print_just_root_even_collection_is_empty() throws IOException {
        StringWriter writer = new StringWriter();
        var printer = new TreeCowPrinter(writer);

        printer.print(Collections.emptyList());

        assertEquals("Farm\n", writer.toString());
    }

    @Test
    public void should_print_tree_if_data_provided_1() throws IOException {
        StringWriter writer = new StringWriter();
        var printer = new TreeCowPrinter(writer);
        var data = Arrays.asList(
                new Cow().setId("1").setNickname("Cow 1"),
                new Cow().setId("2").setNickname("Cow 2")
        );

        printer.print(data);

        var expected =
                "Farm\n" +
                        "├── 1 Cow 1\n" +
                        "└── 2 Cow 2\n";

        assertEquals(expected, writer.toString());
    }

    @Test
    public void should_print_tree_if_data_provided_2() throws IOException {
        StringWriter writer = new StringWriter();

        var printer = new TreeCowPrinter(writer);
        var data = Arrays.asList(
                new Cow().setId("2").setNickname("Parent 2").addChild("20").addChild("21"),
                new Cow().setId("1").setNickname("Parent 1").addChild("10"),
                new Cow().setId("20").setNickname("Child of 2"),
                new Cow().setId("10").setNickname("Child of 1"),
                new Cow().setId("21").setNickname("Child of 2")

        );

        printer.print(data);

        var expected =
                "Farm\n" +
                        "├── 1 Parent 1\n" +
                        "│   └── 10 Child of 1\n" +
                        "└── 2 Parent 2\n" +
                        "    ├── 20 Child of 2\n" +
                        "    └── 21 Child of 2\n";


        assertEquals(expected, writer.toString());
    }
}
