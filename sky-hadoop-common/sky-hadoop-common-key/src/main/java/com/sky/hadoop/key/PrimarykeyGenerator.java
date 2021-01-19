package com.sky.hadoop.key;


public interface PrimarykeyGenerator {
    Long generateId(String var1, String var2);

    String formKey(String var1, String var2);
}

