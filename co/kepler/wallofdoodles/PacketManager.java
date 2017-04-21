/*     */ package co.kepler.wallofdoodles;
/*     */ 
/*     */ import co.kepler.wallofdoodles.packets.Packet;
/*     */ import co.kepler.wallofdoodles.packets.PacketClientKick;
/*     */ import co.kepler.wallofdoodles.packets.PacketRequestRoom;
/*     */ import co.kepler.wallofdoodles.packets.PacketRequestStrings;
/*     */ import co.kepler.wallofdoodles.packets.PacketRoom;
/*     */ import co.kepler.wallofdoodles.packets.PacketShowMessage;
/*     */ import co.kepler.wallofdoodles.packets.PacketStringResponse;
/*     */ import co.kepler.wallofdoodles.packets.PacketUserRoomEnter;
/*     */ import co.kepler.wallofdoodles.packets.PacketUserRoomLeave;
/*     */ import co.kepler.wallofdoodles.packets.WallActionChar;
/*     */ import co.kepler.wallofdoodles.packets.WallActionPolyline;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.Socket;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PacketManager extends StreamUtil
/*     */ {
/*  22 */   private static final String[] names = {
/*  23 */     "PacketClientKick", 
/*  24 */     "PacketRequestRoom", 
/*  25 */     "PacketRequestStrings", 
/*  26 */     "PacketRoom", 
/*  27 */     "PacketShowMessage", 
/*  28 */     "PacketStringResponse", 
/*  29 */     "PacketUserRoomEnter", 
/*  30 */     "PacketUserRoomLeave", 
/*  31 */     "WallActionChar", 
/*  32 */     "WallActionPolyline" };
/*     */   
/*  34 */   private static final HashMap<String, Byte> ids = new HashMap();
/*  35 */   static { int id = 0; String[] arrayOfString; int j = (arrayOfString = names).length; for (int i = 0; i < j; i++) { String name = arrayOfString[i];ids.put(name, Byte.valueOf((byte)id++)); } }
/*     */   
/*  37 */   public static int getPacketID(Packet p) { return ((Byte)ids.get(p.getClass().getSimpleName())).byteValue(); }
/*     */   
/*  39 */   public static String getPacketType(int id) { return names[id]; }
/*     */   
/*     */   private InputStream reader;
/*     */   public PacketManager(Socket s)
/*     */     throws IOException
/*     */   {
/*  45 */     this.reader = s.getInputStream();
/*  46 */     this.writer = new BufferedOutputStream(s.getOutputStream());
/*     */   }
/*     */   
/*     */   public Packet readPacket() throws IOException {
/*  50 */     int id = this.reader.read();
/*  51 */     switch (id) {
/*  52 */     case 0:  return readClientKick();
/*  53 */     case 1:  return readRequestRoom();
/*  54 */     case 2:  return readRequestStrings();
/*  55 */     case 3:  return readRoom();
/*  56 */     case 4:  return readShowMessage();
/*  57 */     case 5:  return readStringResponse();
/*  58 */     case 6:  return readUserRoomEnter();
/*  59 */     case 7:  return readUserRoomLeave();
/*  60 */     case 8:  return readWallActionChar();
/*  61 */     case 9:  return readWallActionPolyline();
/*     */     }
/*  63 */     throw new IOException("Unknown packet ID: " + id);
/*     */   }
/*     */   
/*     */   public void writePacket(Packet... packets) throws IOException { Packet[] arrayOfPacket;
/*  67 */     int j = (arrayOfPacket = packets).length; for (int i = 0; i < j; i++) { Packet p = arrayOfPacket[i];
/*  68 */       int id = ((Byte)ids.get(p.getClass().getSimpleName())).byteValue();
/*  69 */       this.writer.write(id);
/*  70 */       switch (id) {
/*  71 */       case 0:  writeClientKick((PacketClientKick)p); break;
/*  72 */       case 1:  writeRequestRoom((PacketRequestRoom)p); break;
/*  73 */       case 2:  writeRequestStrings((PacketRequestStrings)p); break;
/*  74 */       case 3:  writeRoom((PacketRoom)p); break;
/*  75 */       case 4:  writeShowMessage((PacketShowMessage)p); break;
/*  76 */       case 5:  writeStringResponse((PacketStringResponse)p); break;
/*  77 */       case 6:  writeUserRoomEnter((PacketUserRoomEnter)p); break;
/*  78 */       case 7:  writeUserRoomLeave((PacketUserRoomLeave)p); break;
/*  79 */       case 8:  writeWallActionChar((WallActionChar)p); break;
/*  80 */       case 9:  writeWallActionPolyline((WallActionPolyline)p); break;
/*  81 */       default:  throw new IOException("Unknown packet ID: " + id);
/*     */       }
/*     */     }
/*  84 */     this.writer.flush();
/*     */   }
/*     */   
/*     */   private PacketClientKick readClientKick() throws IOException {
/*  88 */     return new PacketClientKick(readString(this.reader));
/*     */   }
/*     */   
/*  91 */   private void writeClientKick(PacketClientKick p) throws IOException { writeString(this.writer, p.getMessage()); }
/*     */   
/*     */   private PacketRequestRoom readRequestRoom() throws IOException
/*     */   {
/*  95 */     return new PacketRequestRoom(readString(this.reader));
/*     */   }
/*     */   
/*  98 */   private void writeRequestRoom(PacketRequestRoom p) throws IOException { writeString(this.writer, p.getRoomName()); }
/*     */   
/*     */   private PacketRequestStrings readRequestStrings() throws IOException
/*     */   {
/* 102 */     String title = readString(this.reader);
/* 103 */     int arrLen = this.reader.read();
/* 104 */     String[] input = new String[arrLen];
/* 105 */     String[] def = new String[arrLen];
/* 106 */     int[] minLen = new int[arrLen];
/* 107 */     int[] maxLen = new int[arrLen];
/* 108 */     int[] strType = new int[arrLen];
/*     */     
/* 110 */     for (int i = 0; i < arrLen; i++) {
/* 111 */       input[i] = readString(this.reader);
/* 112 */       def[i] = readString(this.reader);
/* 113 */       maxLen[i] = this.reader.read();
/* 114 */       minLen[i] = this.reader.read();
/* 115 */       strType[i] = this.reader.read();
/*     */     }
/* 117 */     return new PacketRequestStrings(title, input, def, minLen, maxLen, strType);
/*     */   }
/*     */   
/* 120 */   private void writeRequestStrings(PacketRequestStrings p) throws IOException { writeString(this.writer, p.getTitle());
/* 121 */     this.writer.write(p.getInput().length);
/* 122 */     for (int i = 0; i < p.getInput().length; i++) {
/* 123 */       writeString(this.writer, p.getInput()[i]);
/* 124 */       writeString(this.writer, p.getDefaults()[i]);
/* 125 */       this.writer.write(p.getMinLength()[i]);
/* 126 */       this.writer.write(p.getMaxLength()[i]);
/* 127 */       this.writer.write(p.getStrType()[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private PacketRoom readRoom() throws IOException {
/* 132 */     String roomName = readString(this.reader);
/* 133 */     Wall wall = readWall(this.reader);
/* 134 */     User[] users = readUsers(this.reader);
/* 135 */     return new PacketRoom(roomName, wall, users);
/*     */   }
/*     */   
/* 138 */   private void writeRoom(PacketRoom p) throws IOException { writeString(this.writer, p.getRoomName());
/* 139 */     writeWall(this.writer, p.getWall());
/* 140 */     writeUsers(this.writer, p.getUsers());
/*     */   }
/*     */   
/*     */   private PacketShowMessage readShowMessage() throws IOException {
/* 144 */     return new PacketShowMessage(readString(this.reader));
/*     */   }
/*     */   
/* 147 */   private void writeShowMessage(PacketShowMessage p) throws IOException { writeString(this.writer, p.getMessage()); }
/*     */   
/*     */   private BufferedOutputStream writer;
/*     */   private PacketStringResponse readStringResponse() throws IOException {
/* 151 */     return new PacketStringResponse(this.reader.read() == 1, readStrings(this.reader)); }
/*     */   
/*     */   private void writeStringResponse(PacketStringResponse p) throws IOException {
/* 154 */     this.writer.write(p.hitOkay() ? 1 : 0);
/* 155 */     writeStrings(this.writer, p.getStrings());
/*     */   }
/*     */   
/*     */   private PacketUserRoomEnter readUserRoomEnter() throws IOException {
/* 159 */     return new PacketUserRoomEnter(readUser(this.reader));
/*     */   }
/*     */   
/* 162 */   private void writeUserRoomEnter(PacketUserRoomEnter p) throws IOException { writeUser(this.writer, p.getUser()); }
/*     */   
/*     */   private PacketUserRoomLeave readUserRoomLeave() throws IOException
/*     */   {
/* 166 */     return new PacketUserRoomLeave(readUser(this.reader));
/*     */   }
/*     */   
/* 169 */   private void writeUserRoomLeave(PacketUserRoomLeave p) throws IOException { writeUser(this.writer, p.getUser()); }
/*     */   
/*     */   private WallActionChar readWallActionChar() throws IOException
/*     */   {
/* 173 */     return new WallActionChar(readInt(this.reader), readInt(this.reader), 
/* 174 */       readChar(this.reader), readColor(this.reader));
/*     */   }
/*     */   
/* 177 */   private void writeWallActionChar(WallActionChar a) throws IOException { writeInt(this.writer, a.getX());
/* 178 */     writeInt(this.writer, a.getY());
/* 179 */     writeChar(this.writer, a.getChar());
/* 180 */     writeColor(this.writer, a.getColor());
/*     */   }
/*     */   
/*     */   private WallActionPolyline readWallActionPolyline() throws IOException {
/* 184 */     int points = readInt(this.reader);
/* 185 */     int[] pointsX = new int[points];
/* 186 */     int[] pointsY = new int[points];
/* 187 */     for (int i = 0; i < pointsX.length; i++) {
/* 188 */       pointsX[i] = readInt(this.reader);
/* 189 */       pointsY[i] = readInt(this.reader);
/*     */     }
/* 191 */     return new WallActionPolyline(pointsX, pointsY, points, readColor(this.reader), this.reader.read());
/*     */   }
/*     */   
/* 194 */   private void writeWallActionPolyline(WallActionPolyline a) throws IOException { writeInt(this.writer, a.getPoints());
/* 195 */     for (int i = 0; i < a.getPoints(); i++) {
/* 196 */       writeInt(this.writer, a.getPointsX()[i]);
/* 197 */       writeInt(this.writer, a.getPointsY()[i]);
/*     */     }
/* 199 */     writeColor(this.writer, a.getColor());
/* 200 */     this.writer.write(a.getWidth());
/*     */   }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\PacketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */