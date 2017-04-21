/*     */ package co.kepler.wallofdoodles.server;
/*     */ 
/*     */ import co.kepler.wallofdoodles.PacketManager;
/*     */ import co.kepler.wallofdoodles.StreamUtil;
/*     */ import co.kepler.wallofdoodles.User;
/*     */ import co.kepler.wallofdoodles.Wall;
/*     */ import co.kepler.wallofdoodles.packets.Packet;
/*     */ import co.kepler.wallofdoodles.packets.PacketUserRoomEnter;
/*     */ import co.kepler.wallofdoodles.packets.PacketUserRoomLeave;
/*     */ import co.kepler.wallofdoodles.packets.WallAction;
/*     */ import java.awt.Graphics2D;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Room
/*     */ {
/*     */   private static final int DEF_WALL_WIDTH = 400;
/*     */   private static final int DEF_WALL_HEIGHT = 240;
/*  26 */   private static final File DIR = new File("walls/");
/*  27 */   private static final HashMap<String, Room> rooms = new HashMap();
/*  28 */   private static final HashMap<Integer, Room> userRooms = new HashMap();
/*  29 */   private static final HashMap<Integer, Graphics2D> graphics = new HashMap();
/*     */   
/*     */   private Wall wall;
/*     */   private File file;
/*     */   private String name;
/*  34 */   private List<ClientConnection> clients = new ArrayList();
/*     */   
/*     */   private Room(Wall wall, String name, File file) {
/*  37 */     this.wall = wall;
/*  38 */     this.name = name;
/*  39 */     this.file = file;
/*     */   }
/*     */   
/*     */   public static Collection<Room> getRooms() {
/*  43 */     return rooms.values();
/*     */   }
/*     */   
/*     */   public static Room getInstance(String name) throws IOException {
/*  47 */     name = name.toLowerCase();
/*  48 */     if (rooms.containsKey(name)) {
/*  49 */       return (Room)rooms.get(name);
/*     */     }
/*     */     
/*  52 */     File file = new File(DIR, name);
/*  53 */     Wall wall = null;
/*  54 */     if (file.exists()) {
/*  55 */       FileInputStream fis = new FileInputStream(file);
/*  56 */       wall = StreamUtil.readWall(fis);
/*  57 */       fis.close();
/*     */     } else {
/*  59 */       wall = new Wall(400, 240);
/*     */     }
/*  61 */     Room room = new Room(wall, name, file);
/*  62 */     rooms.put(name, room);
/*  63 */     return room;
/*     */   }
/*     */   
/*     */   public void save() throws IOException {
/*  67 */     if (!this.file.getParentFile().exists()) this.file.getParentFile().mkdirs();
/*  68 */     if (!this.file.exists()) this.file.createNewFile();
/*  69 */     FileOutputStream fw = new FileOutputStream(this.file);
/*  70 */     StreamUtil.writeWall(fw, this.wall);
/*  71 */     fw.close();
/*     */   }
/*     */   
/*     */   public static void saveAll() {
/*  75 */     for (Room r : rooms.values()) {
/*     */       try {
/*  77 */         r.save();
/*     */       } catch (IOException e) {
/*  79 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendPacket(Packet p, ClientConnection... exclude) {
/*  85 */     List<ClientConnection> ex = Arrays.asList(exclude);
/*  86 */     for (ClientConnection c : this.clients) {
/*  87 */       if (!ex.contains(c))
/*     */         try {
/*  89 */           c.getPacketManager().writePacket(new Packet[] { p });
/*     */         } catch (IOException e) {
/*  91 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */   }
/*     */   
/*     */   public void addClient(ClientConnection c) throws IOException {
/*  97 */     this.clients.add(c);
/*  98 */     userRooms.put(Integer.valueOf(c.getUser().getUUID()), this);
/*  99 */     graphics.put(Integer.valueOf(c.getUser().getUUID()), this.wall.createGraphics());
/* 100 */     sendPacket(new PacketUserRoomEnter(c.getUser()), new ClientConnection[0]);
/*     */   }
/*     */   
/*     */   public void removeClient(ClientConnection c) {
/* 104 */     sendPacket(new PacketUserRoomLeave(c.getUser()), new ClientConnection[] { c });
/* 105 */     userRooms.remove(Integer.valueOf(c.getUser().getUUID()));
/* 106 */     graphics.remove(Integer.valueOf(c.getUser().getUUID()));
/* 107 */     this.clients.remove(c);
/*     */   }
/*     */   
/*     */   public static Room getUserRoom(User u) {
/* 111 */     return (Room)userRooms.get(Integer.valueOf(u.getUUID()));
/*     */   }
/*     */   
/*     */   public void drawAction(WallAction a, User u) {
/* 115 */     Graphics2D g = (Graphics2D)graphics.get(Integer.valueOf(u.getUUID()));
/* 116 */     this.wall.drawAction(a, g);
/*     */   }
/*     */   
/* 119 */   public Wall getWall() { return this.wall; }
/* 120 */   public List<ClientConnection> getClients() { return this.clients; }
/* 121 */   public String getName() { return this.name; }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\server\Room.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */