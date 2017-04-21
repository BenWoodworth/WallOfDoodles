/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ public class PacketShowMessage implements Packet {
/*    */   private String message;
/*    */   
/*  6 */   public PacketShowMessage(String message) { this.message = message; }
/*    */   
/*    */   public String getMessage()
/*    */   {
/* 10 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\PacketShowMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */