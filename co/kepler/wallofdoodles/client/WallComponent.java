/*     */ package co.kepler.wallofdoodles.client;
/*     */ 
/*     */ import co.kepler.wallofdoodles.Wall;
/*     */ import co.kepler.wallofdoodles.packets.WallAction;
/*     */ import co.kepler.wallofdoodles.packets.WallActionChar;
/*     */ import co.kepler.wallofdoodles.packets.WallActionPolyline;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.event.MouseWheelListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class WallComponent
/*     */   extends JComponent
/*     */   implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener
/*     */ {
/*     */   private static final long serialVersionUID = 5695156601765983165L;
/*     */   private static final int CARET_SCROLL_BUFFER = 60;
/*     */   private static final int MAX_LINE_WIDTH = 12;
/*  37 */   public final Color GRID_COLOR = new Color(245, 245, 245, 31);
/*  38 */   public final Color CARET_COLOR = new Color(255, 255, 31, 100);
/*     */   private Wall wall;
/*     */   int carX;
/*     */   int carY;
/*     */   int carEnterX;
/*     */   
/*  44 */   public WallComponent(Wall wall) { setSize(wall.getWidth(), wall.getHeight());
/*  45 */     setPreferredSize(getSize());
/*  46 */     setFocusable(true);
/*  47 */     this.carX = (wall.getTextWidth() / 2);
/*  48 */     this.carY = (wall.getTextHeight() / 2);
/*     */     
/*  50 */     this.wall = wall;
/*     */     
/*  52 */     addMouseListener(this);
/*  53 */     addKeyListener(this);
/*  54 */     addMouseMotionListener(this);
/*  55 */     addMouseWheelListener(this);
/*     */   }
/*     */   
/*     */   public void addNotify() {
/*  59 */     super.addNotify();
/*  60 */     int w = getVisibleRect().width;
/*  61 */     int h = getVisibleRect().height;
/*  62 */     scrollRectToVisible(new Rectangle(
/*  63 */       (getWidth() - w) / 2, (getHeight() - h) / 2, w, h));
/*     */   }
/*     */   
/*  66 */   public Wall getWall() { return this.wall; }
/*     */   
/*     */   public void fixCar() {
/*  69 */     if (this.carX >= this.wall.getTextWidth())
/*  70 */       this.carX = (this.wall.getTextWidth() - 1);
/*  71 */     if (this.carY >= this.wall.getTextHeight())
/*  72 */       this.carY = (this.wall.getTextHeight() - 1);
/*  73 */     if (this.carX < 0) this.carX = 0;
/*  74 */     if (this.carY < 0) this.carY = 0;
/*     */   }
/*     */   
/*  77 */   public void scrollCar() { scrollRectToVisible(new Rectangle(
/*  78 */       this.carX * this.wall.CELL_WIDTH - 60, 
/*  79 */       this.carY * this.wall.CELL_HEIGHT - 60, 
/*  80 */       this.wall.CELL_WIDTH + 120, 
/*  81 */       this.wall.CELL_HEIGHT + 120)); }
/*     */   
/*  83 */   public int getCarX() { return this.carX; }
/*  84 */   public int getCarY() { return this.carY; }
/*     */   
/*  86 */   public void setCar(int x, int y) { setCar(x, y, false, true); }
/*     */   
/*     */   private void setCar(int x, int y, boolean enter, boolean scroll) {
/*  89 */     this.carX = x;
/*  90 */     this.carY = y;
/*  91 */     fixCar();
/*  92 */     if (enter) {
/*  93 */       this.carEnterX = this.carX;
/*     */     }
/*  95 */     if (scroll) scrollCar();
/*  96 */     repaint();
/*     */   }
/*     */   
/*  99 */   private void setCarB(int x, int y, boolean enter, boolean scroll) { setCar(x / this.wall.CELL_WIDTH, y / this.wall.CELL_HEIGHT, enter, scroll); }
/*     */   
/*     */   public void paint(Graphics g_)
/*     */   {
/* 103 */     Graphics2D g = Wall.setupGraphics((Graphics2D)g_);
/*     */     
/* 105 */     BufferedImage wallImg = this.wall.getImage();
/* 106 */     Rectangle c = g.getClip().getBounds().intersection(
/* 107 */       new Rectangle(0, 0, wallImg.getWidth(), wallImg.getHeight()));
/* 108 */     BufferedImage bi = this.wall.getImage().getSubimage(c.x, c.y, c.width, c.height);
/* 109 */     g.drawImage(bi, c.x, c.y, null);
/*     */     
/* 111 */     if (this.drawingLine) {
/* 112 */       g.setColor(new Color(this.curColor));
/* 113 */       g.setStroke(Wall.getStroke(this.lineWidth));
/* 114 */       g.drawLine(this.curLine[0], this.curLine[1], this.curLine[2], this.curLine[3]);
/* 115 */     } else if ((this.drawMode == 2) || (this.drawMode == 1)) {
/* 116 */       Point p = getMousePosition();
/* 117 */       if (p != null) {
/* 118 */         int x = (int)(p.getX() - this.lineWidth / 2 + 1.0D);
/* 119 */         int y = (int)(p.getY() - this.lineWidth / 2 + 1.0D);
/*     */         
/* 121 */         g.setColor(new Color(this.curColor));
/* 122 */         g.fillOval(x, y, this.lineWidth - 2, this.lineWidth - 2);
/* 123 */         g.setColor(Color.BLACK);
/* 124 */         g.drawOval(x, y, this.lineWidth - 2, this.lineWidth - 2);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 129 */     int cw = this.wall.CELL_WIDTH;
/* 130 */     int ch = this.wall.CELL_HEIGHT;
/* 131 */     int startX = c.x / cw;
/* 132 */     int startY = c.y / ch;
/* 133 */     int endX = (int)Math.min(Math.floor((0.0D + c.x + c.width) / cw) + 1.0D, this.wall.getTextWidth());
/* 134 */     int endY = (int)Math.min(Math.floor((0.0D + c.y + c.height) / ch) + 1.0D, this.wall.getTextHeight());
/*     */     
/*     */ 
/* 137 */     for (int y = startY; y < endY; y++) {
/* 138 */       int drawY = y * ch;
/* 139 */       for (int x = startX; x < endX; x++) {
/* 140 */         int drawX = x * cw;
/* 141 */         char curChar = this.wall.getChar(x, y);
/* 142 */         if (curChar != 0) {
/* 143 */           g.setColor(new Color(this.wall.getCharColor(x, y)));
/* 144 */           g.drawString(Character.toString(curChar), 
/* 145 */             drawX + 0, drawY + ch + -4);
/*     */         }
/* 147 */         if ((this.drawMode == 0) && (x == this.carX) && (y == this.carY)) {
/* 148 */           g.setColor(this.CARET_COLOR);
/* 149 */           g.fillRect(drawX, drawY, cw, ch);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 154 */     g.setColor(this.GRID_COLOR);
/* 155 */     g.setStroke(new BasicStroke(1.0F));
/* 156 */     for (int x = startX * cw; x <= endX * cw; x += cw)
/* 157 */       g.drawLine(x, c.y, x, c.y + c.height);
/* 158 */     for (int y = startY * ch; y <= endY * ch; y += ch)
/* 159 */       g.drawLine(c.x, y, c.x + c.width, y);
/*     */   }
/*     */   
/* 162 */   List<WallActionListener> actionListeners = new ArrayList();
/* 163 */   public void addWallActionListener(WallActionListener al) { this.actionListeners.add(al); }
/* 164 */   public void removeWallActionListener(WallActionListener al) { this.actionListeners.remove(al); }
/*     */   
/* 166 */   private void triggerWallAction(WallAction a) { WallActionListener al; for (Iterator localIterator = this.actionListeners.iterator(); localIterator.hasNext(); al.onAction(a)) al = (WallActionListener)localIterator.next();
/*     */   }
/*     */   
/* 169 */   private void triggerCharAction(int x, int y, char c, int color) { c = this.wall.setChar(x, y, c, color);
/* 170 */     triggerWallAction(new WallActionChar(x, y, c, color));
/*     */   }
/*     */   
/*     */ 
/*     */   public static final int MODE_TEXT = 0;
/*     */   
/*     */   public static final int MODE_POLYLINE = 1;
/*     */   public static final int MODE_LINE = 2;
/* 178 */   private int drawMode = 0;
/* 179 */   int lineWidth = 3;
/* 180 */   private boolean drawingLine = false;
/* 181 */   private int[] curLine = new int[4];
/*     */   
/* 183 */   public int getDrawMode() { return this.drawMode; }
/*     */   
/* 185 */   public void setDrawMode(int drawMode) { if (this.drawMode == drawMode) return;
/* 186 */     this.drawMode = drawMode;
/* 187 */     repaint();
/*     */   }
/*     */   
/* 190 */   private int curColor = 0;
/* 191 */   public int getCurColor() { return this.curColor; }
/* 192 */   public void setCurColor(int color) { this.curColor = color; }
/*     */   
/*     */   public void keyPressed(KeyEvent e) {
/* 195 */     if (this.drawMode != 0) return;
/* 196 */     switch (e.getKeyCode()) {
/* 197 */     case 38:  setCar(this.carX, this.carY - 1, true, true); break;
/* 198 */     case 40:  setCar(this.carX, this.carY + 1, true, true); break;
/* 199 */     case 37:  setCar(this.carX - 1, this.carY, true, true); break;
/* 200 */     case 39:  setCar(this.carX + 1, this.carY, true, true); }
/*     */   }
/*     */   
/*     */   public void keyTyped(KeyEvent e) {
/* 204 */     if (this.drawMode != 0) return;
/* 205 */     int code = e.getKeyChar();
/* 206 */     if (code == 8) {
/* 207 */       if ((this.carX == this.wall.getTextWidth() - 1) && (this.wall.getChar(this.carX, this.carY) != 0)) {
/* 208 */         triggerCharAction(this.carX, this.carY, ' ', 0);
/* 209 */         repaint();
/*     */       } else {
/* 211 */         triggerCharAction(this.carX - 1, this.carY, ' ', 0);
/* 212 */         setCar(this.carX - 1, this.carY);
/*     */       }
/* 214 */       if (this.carX < this.carEnterX) this.carEnterX = this.carX;
/* 215 */     } else if ((code == 32) || (code == 127)) {
/* 216 */       triggerCharAction(this.carX, this.carY, ' ', 0);
/* 217 */       setCar(this.carX + 1, this.carY);
/* 218 */     } else if (code == 10) {
/* 219 */       setCar(Math.min(this.carX, this.carEnterX), this.carY + 1);
/* 220 */     } else if (code > 32) {
/* 221 */       triggerCharAction(this.carX, this.carY, e.getKeyChar(), this.curColor);
/* 222 */       setCar(this.carX + 1, this.carY); } }
/*     */   
/*     */   public void keyReleased(KeyEvent e) {}
/*     */   
/*     */   public void mouseClicked(MouseEvent e) {}
/*     */   
/*     */   public void mouseEntered(MouseEvent e) {}
/*     */   
/* 230 */   public void mouseExited(MouseEvent e) { repaint(); }
/*     */   
/*     */   public void mousePressed(MouseEvent e) {
/* 233 */     switch (this.drawMode) {
/*     */     case 0: 
/* 235 */       setCarB(e.getX(), e.getY(), true, false);
/* 236 */       break;
/*     */     case 1: 
/* 238 */       this.curLine[0] = e.getX();
/* 239 */       this.curLine[1] = e.getY();
/* 240 */       break;
/*     */     case 2: 
/* 242 */       this.curLine[0] = (this.curLine[2] = e.getX());
/* 243 */       this.curLine[1] = (this.curLine[3] = e.getY());
/* 244 */       this.drawingLine = true;
/* 245 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseDragged(MouseEvent e) {
/* 250 */     switch (this.drawMode) {
/*     */     case 0: 
/* 252 */       setCarB(e.getX(), e.getY(), true, false);
/* 253 */       break;
/*     */     case 1: 
/* 255 */       triggerWallAction(new WallActionPolyline(this.curLine[0], this.curLine[1], 
/* 256 */         e.getX(), e.getY(), this.curColor, this.lineWidth));
/* 257 */       this.wall.drawLine(this.curLine[0], this.curLine[1], e.getX(), e.getY(), this.curColor, this.lineWidth, null);
/* 258 */       this.curLine[0] = e.getX();
/* 259 */       this.curLine[1] = e.getY();
/* 260 */       repaint();
/* 261 */       break;
/*     */     case 2: 
/* 263 */       if (this.drawingLine) {
/* 264 */         this.curLine[2] = e.getX();
/* 265 */         this.curLine[3] = e.getY();
/* 266 */         repaint();
/*     */       }
/*     */       break; }
/*     */   }
/*     */   
/* 271 */   public void mouseReleased(MouseEvent e) { switch (this.drawMode) {
/*     */     case 0: 
/* 273 */       setCarB(e.getX(), e.getY(), true, true);
/* 274 */       if (e.getButton() == 3) {
/* 275 */         String cb = getClipboard();
/* 276 */         if ((cb != null) && (cb.length() <= 12)) {
/* 277 */           writeString(cb);
/*     */         }
/*     */       }
/* 280 */       break;
/*     */     case 1: 
/* 282 */       mouseDragged(e);
/* 283 */       break;
/*     */     case 2: 
/* 285 */       if (this.drawingLine) {
/* 286 */         this.drawingLine = false;
/* 287 */         this.wall.drawLine(this.curLine[0], this.curLine[1], this.curLine[2], this.curLine[3], this.curColor, this.lineWidth, null);
/* 288 */         triggerWallAction(new WallActionPolyline(this.curLine[0], this.curLine[1], 
/* 289 */           this.curLine[2], this.curLine[3], this.curColor, this.lineWidth));
/* 290 */         repaint();
/*     */       }
/*     */       break; }
/*     */   }
/*     */   
/* 295 */   public void mouseMoved(MouseEvent e) { repaint(); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseWheelMoved(MouseWheelEvent e)
/*     */   {
/* 303 */     this.lineWidth = ((int)(this.lineWidth - e.getPreciseWheelRotation()));
/* 304 */     this.lineWidth = Math.max(0, this.lineWidth);
/* 305 */     this.lineWidth = Math.min(12, this.lineWidth);
/* 306 */     repaint();
/*     */   }
/*     */   
/*     */   private String getClipboard() {
/*     */     try {
/* 311 */       return (String)Toolkit.getDefaultToolkit()
/* 312 */         .getSystemClipboard().getData(DataFlavor.stringFlavor);
/*     */     }
/*     */     catch (Exception localException) {}
/* 315 */     return null;
/*     */   }
/*     */   
/*     */   public void writeString(String s) {
/* 319 */     try { String[] lines = s.split("\n");
/* 320 */       int xi = this.carX;
/* 321 */       int yi = this.carY;
/* 322 */       for (int y = 0; y < lines.length; y++) {
/* 323 */         if (yi + y >= this.wall.getTextHeight()) break;
/* 324 */         for (int x = 0; x < lines[y].length(); x++) {
/* 325 */           if (xi + x >= this.wall.getTextWidth()) break;
/* 326 */           triggerCharAction(xi + x, yi + y, lines[y].charAt(x), this.curColor);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   public static abstract interface WallActionListener
/*     */   {
/*     */     public abstract void onAction(WallAction paramWallAction);
/*     */   }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\client\WallComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */