/*    */ package co.kepler.wallofdoodles;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class User
/*    */   implements java.io.Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1024106104591907134L;
/*    */   private String username;
/*    */   int uuid;
/*    */   
/* 12 */   public User(String username) { this(username, new Random().nextInt() | 0x80000000); }
/*    */   
/*    */   public User(String username, int uuid) {
/* 15 */     this.username = username;
/* 16 */     this.uuid = uuid;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 20 */     return this.username;
/*    */   }
/*    */   
/*    */   public int getUUID() {
/* 24 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 28 */     return ((o instanceof User)) && (((User)o).getUUID() == this.uuid);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     return this.username;
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\User.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */