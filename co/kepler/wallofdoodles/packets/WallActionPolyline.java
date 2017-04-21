/*    */ package co.kepler.wallofdoodles.packets;
/*    */ 
/*    */ public class WallActionPolyline implements WallAction { int[] pointsX;
/*    */   int[] pointsY;
/*    */   int points;
/*    */   int color;
/*    */   int width;
/*    */   
/*  9 */   public WallActionPolyline(int x1, int y1, int x2, int y2, int color, int width) { this(new int[] { x1, x2 }, new int[] { y1, y2 }, 2, color, width); }
/*    */   
/*    */ 
/*    */   public WallActionPolyline(int[] pointsX, int[] pointsY, int points, int color, int width)
/*    */   {
/* 14 */     this.pointsX = pointsX;
/* 15 */     this.pointsY = pointsY;
/* 16 */     this.points = points;
/* 17 */     this.color = color;
/* 18 */     this.width = width;
/*    */   }
/*    */   
/* 21 */   public int[] getPointsX() { return this.pointsX; }
/* 22 */   public int[] getPointsY() { return this.pointsY; }
/* 23 */   public int getPoints() { return this.points; }
/* 24 */   public int getColor() { return this.color; }
/* 25 */   public int getWidth() { return this.width; }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\packets\WallActionPolyline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */