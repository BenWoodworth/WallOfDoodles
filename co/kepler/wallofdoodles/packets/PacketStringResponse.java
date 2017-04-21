/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ public class PacketStringResponse implements Packet {
/*    */   private boolean hitOkay;
/*    */   private String[] strings;
/*    */   
/*  7 */   public PacketStringResponse(boolean hitOkay, String... strings) { this.hitOkay = hitOkay;
/*  8 */     this.strings = strings;
/*    */   }
/*    */   
/*    */   public boolean hitOkay() {
/* 12 */     return this.hitOkay;
/*    */   }
/*    */   
/*    */   public String[] getStrings() {
/* 16 */     return this.strings;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\PacketStringResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */