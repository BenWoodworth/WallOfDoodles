/*     */ package co.kepler.wallofdoodles;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamUtil
/*     */ {
/*  12 */   public static char readChar(InputStream reader) throws IOException { return (char)(reader.read() << 8 | reader.read()); }
/*     */   
/*     */   public static void writeChar(OutputStream writer, char c) throws IOException {
/*  15 */     writer.write(c >>> '\b');
/*  16 */     writer.write(c);
/*     */   }
/*     */   
/*     */   public static String readString(InputStream reader) throws IOException {
/*  20 */     StringBuilder sb = new StringBuilder();
/*     */     char c;
/*  22 */     while ((c = readChar(reader)) != '\003') { char c;
/*  23 */       if (c == 0) return null;
/*  24 */       sb.append(c);
/*     */     }
/*  26 */     return sb.toString();
/*     */   }
/*     */   
/*  29 */   public static void writeString(OutputStream writer, String s) throws IOException { if (s == null) {
/*  30 */       writeChar(writer, '\000');
/*     */     } else { char[] arrayOfChar;
/*  32 */       int j = (arrayOfChar = s.toCharArray()).length; for (int i = 0; i < j; i++) { char c = arrayOfChar[i];
/*  33 */         writeChar(writer, c); }
/*  34 */       writeChar(writer, '\003');
/*     */     }
/*     */   }
/*     */   
/*     */   public static String[] readStrings(InputStream reader) throws IOException {
/*  39 */     java.util.List<String> res = new java.util.ArrayList();
/*     */     String cur;
/*  41 */     while ((cur = readString(reader)) != null) { String cur;
/*  42 */       res.add(cur); }
/*  43 */     return (String[])res.toArray(new String[0]); }
/*     */   
/*     */   public static void writeStrings(OutputStream writer, String... strings) throws IOException { String[] arrayOfString;
/*  46 */     int j = (arrayOfString = strings).length; for (int i = 0; i < j; i++) { String s = arrayOfString[i];
/*  47 */       writeString(writer, s); }
/*  48 */     writeString(writer, null);
/*     */   }
/*     */   
/*     */   public static int readInt(InputStream reader) throws IOException {
/*  52 */     return reader.read() << 24 | reader.read() << 16 | 
/*  53 */       reader.read() << 8 | reader.read();
/*     */   }
/*     */   
/*  56 */   public static void writeInt(OutputStream writer, int num) throws IOException { writer.write(num >>> 24);writer.write(num >>> 16);
/*  57 */     writer.write(num >>> 8);writer.write(num);
/*     */   }
/*     */   
/*     */   public static int readColor(InputStream reader) throws IOException {
/*  61 */     return reader.read() << 16 | 
/*  62 */       reader.read() << 8 | reader.read();
/*     */   }
/*     */   
/*  65 */   public static void writeColor(OutputStream writer, int color) throws IOException { writer.write(color >>> 16);
/*  66 */     writer.write(color >>> 8);
/*  67 */     writer.write(color);
/*     */   }
/*     */   
/*     */   public static BufferedImage readImage(InputStream reader) throws IOException {
/*  71 */     int width = PacketManager.readInt(reader);
/*  72 */     int height = PacketManager.readInt(reader);
/*  73 */     BufferedImage bi = new BufferedImage(width, height, 1);
/*  74 */     int num = 0;int color = 0;
/*  75 */     for (int y = 0; y < height; y++) {
/*  76 */       for (int x = 0; x < width; x++) {
/*  77 */         if (num == 0) {
/*  78 */           num = reader.read() << 8 | reader.read();
/*  79 */           color = reader.read() << 16 | reader.read() << 8 | reader.read();
/*     */         }
/*  81 */         bi.setRGB(x, y, color);
/*  82 */         num--;
/*     */       }
/*     */     }
/*  85 */     return bi;
/*     */   }
/*     */   
/*  88 */   public static void writeImage(OutputStream writer, BufferedImage bi) throws IOException { PacketManager.writeInt(writer, bi.getWidth());
/*  89 */     PacketManager.writeInt(writer, bi.getHeight());
/*     */     
/*     */ 
/*  92 */     int colorCount = 0;int color = 0;int curColor = bi.getRGB(0, 0);
/*  93 */     int width = bi.getWidth();int height = bi.getHeight();
/*  94 */     int num = width * height;
/*     */     
/*  96 */     for (int i = 0; i <= num; i++) {
/*  97 */       if (i < num) {
/*  98 */         int x = i % height;
/*  99 */         int y = i / width;
/* 100 */         color = bi.getRGB(x, y);
/* 101 */         colorCount++;
/*     */       }
/* 103 */       if ((i == num) || (color != curColor) || (colorCount == 65535)) {
/* 104 */         writer.write(colorCount >>> 8);
/* 105 */         writer.write(colorCount);
/* 106 */         writer.write(curColor >>> 16);
/* 107 */         writer.write(curColor >>> 8);
/* 108 */         writer.write(curColor);
/* 109 */         colorCount = 0;
/* 110 */         curColor = color;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static Wall readWall(InputStream reader) throws IOException {
/* 116 */     int width = readInt(reader);
/* 117 */     int height = readInt(reader);
/* 118 */     char[][] text = new char[width][height];
/* 119 */     int[][] color = new int[width][height];
/* 120 */     for (int x = 0; x < width; x++) {
/* 121 */       for (int y = 0; y < height; y++) {
/* 122 */         text[x][y] = readChar(reader);
/* 123 */         color[x][y] = readColor(reader);
/*     */       }
/*     */     }
/* 126 */     BufferedImage bi = readImage(reader);
/* 127 */     return new Wall(text, color, bi);
/*     */   }
/*     */   
/* 130 */   public static void writeWall(OutputStream writer, Wall wall) throws IOException { writeInt(writer, wall.getTextWidth());
/* 131 */     writeInt(writer, wall.getTextHeight());
/* 132 */     char[][] text = wall.getTextArr();
/* 133 */     int[][] color = wall.getTextColorArr();
/* 134 */     for (int x = 0; x < wall.getTextWidth(); x++) {
/* 135 */       for (int y = 0; y < wall.getTextHeight(); y++) {
/* 136 */         writeChar(writer, text[x][y]);
/* 137 */         writeColor(writer, color[x][y]);
/*     */       }
/*     */     }
/* 140 */     writeImage(writer, wall.getImage());
/*     */   }
/*     */   
/*     */   public static User readUser(InputStream reader) throws IOException {
/* 144 */     int uuid = readInt(reader);
/* 145 */     return new User(readString(reader), uuid);
/*     */   }
/*     */   
/* 148 */   public static void writeUser(OutputStream writer, User user) throws IOException { writeInt(writer, user.getUUID());
/* 149 */     writeString(writer, user.getUsername());
/*     */   }
/*     */   
/*     */   public static User[] readUsers(InputStream reader) throws IOException {
/* 153 */     java.util.List<User> res = new java.util.ArrayList();
/* 154 */     int[] uuid = new int[4];
/* 155 */     while ((uuid[0] = reader.read()) != 0) {
/* 156 */       for (int i = 1; i < 4; i++) uuid[i] = reader.read();
/* 157 */       int id = uuid[0] << 24 | uuid[1] << 16 | uuid[2] << 8 | uuid[3];
/* 158 */       res.add(new User(readString(reader), id));
/*     */     }
/* 160 */     return (User[])res.toArray(new User[0]); }
/*     */   
/*     */   public static void writeUsers(OutputStream writer, User... users) throws IOException { User[] arrayOfUser;
/* 163 */     int j = (arrayOfUser = users).length; for (int i = 0; i < j; i++) { User u = arrayOfUser[i];
/* 164 */       writeInt(writer, u.getUUID());
/* 165 */       writeString(writer, u.getUsername());
/*     */     }
/* 167 */     writer.write(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\StreamUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */