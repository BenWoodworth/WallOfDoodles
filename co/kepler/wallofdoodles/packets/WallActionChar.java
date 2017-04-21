/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ public class WallActionChar implements WallAction {
/*    */   private int x;
/*    */   private int y;
/*    */   
/*  7 */   public WallActionChar(int x, int y, char c, int color) { this.x = x;
/*  8 */     this.y = y;
/*  9 */     this.c = c;
/* 10 */     this.color = color;
/*    */   }
/*    */   
/* 13 */   public int getX() { return this.x; }
/* 14 */   public int getY() { return this.y; }
/* 15 */   public char getChar() { return this.c; }
/* 16 */   public int getColor() { return this.color; }
/*    */   
/*    */   private int color;
/*    */   private char c;
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\WallActionChar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */