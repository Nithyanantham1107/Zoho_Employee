package com.dbparser.schema;

public class ForeignKey {

  private  String references;
   private String column;

   public ForeignKey(String references, String column) {
       this.references = references;
       this.column = column;

   }
   public String getReferences() {
       return references;

   }
   public String getColumn() {
       return column;

   }
   public void setReferences(String references) {
       this.references = references;

   }
   public void setColumn(String column) {
       this.column = column;

   }
}
