package com.dbparser.schema;

public class Column {

    private String name;
    private String type;
    private Integer length;
    private Boolean primarykey;
    private Boolean unique;
    private ForeignKey foreignkey;


    public String getName() {
        return name;

    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Boolean isUnique() {
        return unique;
    }

    public void setForeignkey(ForeignKey foreignkey) {
        this.foreignkey = foreignkey;
    }

    public ForeignKey getForeignkey() {
        return foreignkey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;

    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean getPrimarykey() {
        return primarykey;

    }

    public void setPrimarykey(Boolean primarykey) {
        this.primarykey = primarykey;
    }
}
