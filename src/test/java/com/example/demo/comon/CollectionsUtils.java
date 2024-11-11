package com.example.demo.comon;

import jakarta.annotation.Nullable;

import java.util.List;

import static java.util.Collections.emptyList;

public class CollectionsUtils {

    public static List<String> toNotNullList(@Nullable List<String> nullableList) {
        return nullableList == null ? emptyList() : nullableList;
    }
}
