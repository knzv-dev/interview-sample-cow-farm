package com.interview.farm.util;

import java.io.IOException;

public interface Printer<T> {

    void print(T data) throws IOException;
    void print(String text) throws IOException;
}
