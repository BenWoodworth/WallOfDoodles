/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ public class PacketRequestRoom implements Packet {
/*    */   private String roomName;
/*    */   
/*  6 */   public PacketRequestRoom(String roomName) { this.roomName = roomName; }
/*    */   
/*    */   public String getRoomName()
/*    */   {
/* 10 */     return this.roomName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\PacketRequestRoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */