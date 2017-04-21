/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ import co.kepler.wallofdoodles.User;
/*    */ 
/*    */ public class PacketRoom implements Packet
/*    */ {
/*    */   String roomName;
/*    */   co.kepler.wallofdoodles.Wall wall;
/*    */   User[] users;
/*    */   
/*    */   public PacketRoom(String roomName, co.kepler.wallofdoodles.Wall wall, User[] users) {
/* 12 */     this.roomName = roomName;
/* 13 */     this.wall = wall;
/* 14 */     this.users = users;
/*    */   }
/*    */   
/* 17 */   public PacketRoom(co.kepler.wallofdoodles.server.Room room) { User[] users = new User[room.getClients().size()];
/* 18 */     for (int i = 0; i < users.length; i++) {
/* 19 */       users[i] = ((co.kepler.wallofdoodles.server.ClientConnection)room.getClients().get(i)).getUser();
/*    */     }
/* 21 */     this.roomName = room.getName();
/* 22 */     this.wall = room.getWall();
/* 23 */     this.users = users;
/*    */   }
/*    */   
/*    */   public String getRoomName() {
/* 27 */     return this.roomName;
/*    */   }
/*    */   
/*    */   public co.kepler.wallofdoodles.Wall getWall() {
/* 31 */     return this.wall;
/*    */   }
/*    */   
/*    */   public User[] getUsers() {
/* 35 */     return this.users;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\PacketRoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */