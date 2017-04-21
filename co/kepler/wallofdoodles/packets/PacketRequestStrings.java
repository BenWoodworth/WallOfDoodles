/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ public class PacketRequestStrings implements Packet {
/*    */   private String title;
/*    */   private String[] input;
/*    */   private String[] defaults;
/*    */   
/*  8 */   public PacketRequestStrings(String title, String[] input, String[] defaults, int[] minLength, int[] maxLength, int[] charType) { this.title = title;
/*  9 */     this.input = input;
/* 10 */     this.defaults = defaults;
/* 11 */     this.minLength = minLength;
/* 12 */     this.maxLength = maxLength;
/* 13 */     this.charType = charType; }
/*    */   
/*    */   private int[] minLength;
/*    */   
/* 17 */   public String getTitle() { return this.title; }
/*    */   
/*    */   private int[] maxLength;
/*    */   private int[] charType;
/* 21 */   public String[] getInput() { return this.input; }
/*    */   
/*    */   public String[] getDefaults()
/*    */   {
/* 25 */     return this.defaults;
/*    */   }
/*    */   
/*    */   public int[] getMinLength() {
/* 29 */     return this.minLength;
/*    */   }
/*    */   
/*    */   public int[] getMaxLength() {
/* 33 */     return this.maxLength;
/*    */   }
/*    */   
/*    */   public int[] getStrType() {
/* 37 */     return this.charType;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\PacketRequestStrings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */