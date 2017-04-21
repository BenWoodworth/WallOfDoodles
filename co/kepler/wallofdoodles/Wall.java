/*     */ package co.kepler.wallofdoodles;
/*     */ 
/*     */ import co.kepler.wallofdoodles.packets.WallAction;
/*     */ import co.kepler.wallofdoodles.packets.WallActionChar;
/*     */ import co.kepler.wallofdoodles.packets.WallActionPolyline;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ public class Wall
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5020732939799643046L;
/*  19 */   public int CELL_WIDTH = 12;
/*  20 */   public int CELL_HEIGHT = 20;
/*     */   
/*     */ 
/*  23 */   public static final Font FONT = new Font("Lucida Console", 0, 20);
/*     */   
/*     */   public static final int CHAR_OFF_X = 0;
/*     */   public static final int CHAR_OFF_Y = -4;
/*     */   char[][] text;
/*     */   int[][] textColor;
/*     */   BufferedImage bi;
/*     */   Graphics2D g;
/*     */   
/*     */   public Wall(int hCells, int vCells)
/*     */   {
/*  34 */     this.text = new char[hCells][vCells];
/*  35 */     this.textColor = new int[hCells][vCells];
/*  36 */     this.bi = new BufferedImage(
/*  37 */       hCells * this.CELL_WIDTH + 1, vCells * this.CELL_HEIGHT + 1, 
/*  38 */       1);
/*  39 */     this.g = setupGraphics(this.bi.createGraphics());
/*  40 */     this.g.clearRect(0, 0, this.bi.getWidth(), this.bi.getHeight());
/*     */   }
/*     */   
/*     */   public Wall(char[][] text, int[][] textColor, BufferedImage bi) {
/*  44 */     this.text = text;
/*  45 */     this.textColor = textColor;
/*  46 */     this.bi = bi;
/*  47 */     this.g = setupGraphics(bi.createGraphics());
/*     */   }
/*     */   
/*     */   public static Graphics2D setupGraphics(Graphics2D g) {
/*  51 */     g.setFont(FONT);
/*  52 */     g.setBackground(Color.WHITE);
/*  53 */     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  54 */     g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
/*  55 */     g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
/*  56 */     g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
/*  57 */     g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  58 */     return g;
/*     */   }
/*     */   
/*     */   public static BasicStroke getStroke(int width) {
/*  62 */     return new BasicStroke(width, 1, 1);
/*     */   }
/*     */   
/*  65 */   public char[][] getTextArr() { return this.text; }
/*  66 */   public int[][] getTextColorArr() { return this.textColor; }
/*     */   
/*  68 */   public int getWidth() { return this.bi.getWidth(); }
/*  69 */   public int getHeight() { return this.bi.getHeight(); }
/*     */   
/*  71 */   public int getTextWidth() { return this.text.length; }
/*  72 */   public int getTextHeight() { return this.text[0].length; }
/*     */   
/*  74 */   public char getChar(int x, int y) { return this.text[x][y]; }
/*  75 */   public int getCharColor(int x, int y) { return this.textColor[x][y]; }
/*     */   
/*  77 */   public char setChar(int x, int y, char c, int color) { if ((c <= ' ') || (c == '')) c = '\000';
/*  78 */     this.textColor[x][y] = color;
/*  79 */     return this.text[x][y] = c;
/*     */   }
/*     */   
/*  82 */   public void addText(int x, int y, String text, int color) { String[] lines = text.split("\n");
/*  83 */     for (int row = 0; (row < lines.length) && (
/*  84 */           row < getTextWidth()); row++) {
/*  85 */       for (int col = 0; (col < lines[row].length()) && (
/*  86 */             row < getTextWidth()); col++) {
/*  87 */         setChar(col + x, row + y, lines[row].charAt(col), color);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*  92 */   public BufferedImage getImage() { return this.bi; }
/*     */   
/*     */   public void drawAction(WallAction a, Graphics2D g) {
/*  95 */     if (g == null) g = this.g;
/*  96 */     if ((a instanceof WallActionPolyline)) {
/*  97 */       WallActionPolyline pl = (WallActionPolyline)a;
/*  98 */       drawPolyline(pl.getPointsX(), pl.getPointsY(), pl.getPoints(), pl.getColor(), pl.getWidth(), g);
/*  99 */     } else if ((a instanceof WallActionChar)) {
/* 100 */       WallActionChar c = (WallActionChar)a;
/* 101 */       setChar(c.getX(), c.getY(), c.getChar(), c.getColor());
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawPolyline(int[] pointsX, int[] pointsY, int points, int color, int width, Graphics2D g) {
/* 106 */     if (g == null) g = this.g;
/* 107 */     if (points == 2) {
/* 108 */       drawLine(pointsX[0], pointsY[0], pointsX[1], pointsY[1], color, width, g);
/*     */     } else {
/* 110 */       g.setColor(new Color(color));
/* 111 */       g.setStroke(getStroke(width));
/* 112 */       g.drawPolyline(pointsX, pointsY, points);
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawLine(int x1, int y1, int x2, int y2, int color, int width, Graphics2D g) {
/* 117 */     if (g == null) g = this.g;
/* 118 */     g.setColor(new Color(color));
/* 119 */     if ((x1 == x2) && (y1 == y2)) {
/* 120 */       g.fillOval(x1 - width / 2, y1 - width / 2, width, width);
/*     */     } else {
/* 122 */       g.setStroke(getStroke(width));
/* 123 */       g.drawLine(x1, y1, x2, y2);
/*     */     }
/*     */   }
/*     */   
/*     */   public Graphics2D createGraphics() {
/* 128 */     return setupGraphics(this.bi.createGraphics());
/*     */   }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\Wall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */