package com.dbparser.schema;

public class ForeignKey {

  private  String references;
   private String column;
   private Constraint constraint;


   public ForeignKey(String references, String column) {
       this.references = references;
       this.column = column;

   }
   public Constraint getConstraint() {
       return constraint;
   }
   public String getReferences() {
       return references;

   }
   public String getColumn() {
       return column;

   }

}
