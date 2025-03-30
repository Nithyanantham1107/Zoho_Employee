package com.dbparser;

import com.dbparser.schema.Column;
import com.dbparser.schema.DataBaseSchema;
import com.dbparser.schema.Table;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.swing.event.ListDataEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TableParser {

    public static List<String> tableParser(String filePath) throws FileNotFoundException {

        System.out.println("TableParser" + filePath);
        List<String> tables = new ArrayList<>();
        Gson gson = new Gson();


        JsonReader reader = new JsonReader(new FileReader(filePath));


        DataBaseSchema schema = gson.fromJson(reader, DataBaseSchema.class);

        System.out.println("Tables found: " + schema.getTables().size());
        for (Table table : schema.getTables()) {
            System.out.println("Table: " + table.getName());

            tables.add(createTableQuery(table));

        }
        return tables;


    }

    private static String createTableQuery(Table table) {
        StringBuilder query = new StringBuilder("CREATE TABLE  IF NOT EXISTS  " + table.getName() + " (\n");
        String primaryKey = null;
        StringBuilder foreignKeys = new StringBuilder();
        List<Column> columns = table.getColumns();
        for (int i = 0; i < columns.size(); i++) {

            String columnDef = columns.get(i).getName() + " " + sqlTypeConversion(columns.get(i));
//            System.out.println("Here teh column name is" + columnDef + "check for unique column" + column.isUnique());
            if (Boolean.TRUE.equals(columns.get(i).getPrimarykey())) {
                primaryKey = columns.get(i).getName();
            }

            if (Boolean.TRUE.equals(columns.get(i).isUnique())) {
                columnDef += " UNIQUE";
            }

            if (columns.get(i).getForeignkey() != null) {
                foreignKeys.append(",\n  FOREIGN KEY (").append(columns.get(i).getName()).append(") REFERENCES ")
                        .append(columns.get(i).getForeignkey().getReferences()).append("(").append(columns.get(i).getForeignkey().getColumn()).append(")");
            }

            query.append("  ").append(columnDef);
            if (i < columns.size() - 1) {
                query.append(",\n");
            }
        }

        if (primaryKey != null) {
            query.append(",\n  PRIMARY KEY (").append(primaryKey).append(")");
        }
//        System.out.println("foreign keys: " + foreignKeys.toString());
        if (!foreignKeys.toString().isEmpty()) {
            query.append(foreignKeys);
        }

        query.append("\n);\n");


        System.out.println(query.toString());

        return query.toString();
    }

    private static String sqlTypeConversion(Column column) {
        switch (column.getType().toLowerCase()) {
            case "varchar":
                return "VARCHAR(" + column.getLength() + ")";
            case "long":
                return "BIGINT";
            case "int":
                return "INT";
            case "auto":
                return "bigserial";

            default:
                return column.getType().toUpperCase();
        }
    }
}





