/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ public class PacketClientKick implements Packet {
/*    */   String message;
/*    */   
/*  6 */   public PacketClientKick(String message) { this.message = message; }
/*    */   
/*    */   public String getMessage()
/*    */   {
/* 10 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\PacketClientKick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */