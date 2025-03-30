package com.dbparser.schema;

import java.util.List;

public class Table {

    private String name;
    private List<Column> columns;

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }


}
