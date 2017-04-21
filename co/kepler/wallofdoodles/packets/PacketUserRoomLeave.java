/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ import co.kepler.wallofdoodles.User;
/*    */ 
/*    */ public class PacketUserRoomLeave implements Packet {
/*    */   private User user;
/*    */   
/*  8 */   public PacketUserRoomLeave(User user) { this.user = user; }
/*    */   
/*    */   public User getUser()
/*    */   {
/* 12 */     return this.user;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\PacketUserRoomLeave.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */