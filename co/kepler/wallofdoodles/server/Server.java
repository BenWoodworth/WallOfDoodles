/*     */ package co.kepler.wallofdoodles.server;
/*     */ 
/*     */ import co.kepler.wallofdoodles.PacketManager;
/*     */ import co.kepler.wallofdoodles.packets.Packet;
/*     */ import co.kepler.wallofdoodles.packets.PacketRequestRoom;
/*     */ import co.kepler.wallofdoodles.packets.WallAction;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.ServerSocket;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.Timer;
/*     */ 
/*     */ public class Server implements ClientListener, Runnable
/*     */ {
/*     */   private static final int SAVE_INTERVAL = 300000;
/*     */   private ServerSocket ss;
/*     */   private BufferedWriter logWriter;
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/*  25 */     if (args.length > 1) {
/*  26 */       System.out.println("Too many arguments!");
/*  27 */       return;
/*     */     }
/*  29 */     int port = 31415;
/*  30 */     if (args.length == 1) {
/*     */       try {
/*  32 */         port = Integer.parseInt(args[0]);
/*     */       } catch (NumberFormatException e) {
/*  34 */         System.out.println("Invalid argument: Port");
/*  35 */         return;
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/*  40 */       final Server server = new Server(port);
/*  41 */       Timer timer = new Timer();
/*  42 */       timer.scheduleAtFixedRate(new java.util.TimerTask()
/*     */       {
/*     */         public void run() {}
/*     */ 
/*  46 */       }, 300000L, 300000L);
/*  47 */       Runtime.getRuntime().addShutdownHook(new Thread() {
/*     */         public void run() {
/*  49 */           System.out.println("\nStopping server...");
/*  50 */           Server.this.cancel();
/*  51 */           server.stop();
/*  52 */           System.out.println("\nServer closed!");
/*     */         }
/*  54 */       });
/*  55 */       server.run();
/*     */     } catch (IOException e) {
/*  57 */       System.out.println("Unable to start server: " + e.getMessage());
/*  58 */       return;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Server(int port)
/*     */     throws IOException
/*     */   {
/*  66 */     File logFile = new File("./log.txt");
/*  67 */     logFile.createNewFile();
/*  68 */     this.logWriter = new BufferedWriter(new java.io.FileWriter(logFile, true));
/*     */     
/*  70 */     this.ss = new ServerSocket(port);
/*  71 */     System.out.println("Listening on port: " + port + "\n");
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     try {
/*     */       for (;;) {
/*  77 */         java.net.Socket s = this.ss.accept();
/*  78 */         System.out.println("Connecting to client...");
/*  79 */         ClientConnection cc = new ClientConnection(this, s);
/*  80 */         cc.addPacketListener(this);
/*  81 */         cc.start();
/*     */       }
/*  83 */     } catch (IOException e) { System.out.println("Error connecting: " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public void packetReceived(ClientConnection c, Packet p)
/*     */   {
/*  89 */     if ((p instanceof PacketRequestRoom)) {
/*     */       try {
/*  91 */         Room oldRoom = Room.getUserRoom(c.getUser());
/*  92 */         PacketRequestRoom prr = (PacketRequestRoom)p;
/*  93 */         Room room = Room.getInstance(prr.getRoomName());
/*  94 */         oldRoom.removeClient(c);
/*  95 */         room.addClient(c);
/*     */       } catch (IOException e) {
/*     */         try {
/*  98 */           c.getPacketManager().writePacket(new Packet[] { new co.kepler.wallofdoodles.packets.PacketShowMessage("Error loading room!") });
/*     */         } catch (IOException e1) {
/* 100 */           e1.printStackTrace();
/*     */         }
/*     */       }
/* 103 */     } else if ((p instanceof WallAction)) {
/* 104 */       WallAction a = (WallAction)p;
/* 105 */       Room room = Room.getUserRoom(c.getUser());
/* 106 */       room.drawAction(a, c.getUser());
/* 107 */       room.sendPacket(a, new ClientConnection[] { c });
/*     */     }
/*     */   }
/*     */   
/*     */   public void stop() {
/* 112 */     Packet p = new co.kepler.wallofdoodles.packets.PacketClientKick("Server shutting down!");
/* 113 */     Iterator localIterator2; for (Iterator localIterator1 = Room.getRooms().iterator(); localIterator1.hasNext(); 
/* 114 */         localIterator2.hasNext())
/*     */     {
/* 113 */       Room r = (Room)localIterator1.next();
/* 114 */       localIterator2 = r.getClients().iterator(); continue;ClientConnection c = (ClientConnection)localIterator2.next();
/*     */       try {
/* 116 */         c.getPacketManager().writePacket(new Packet[] { p });
/*     */       } catch (IOException e) {
/* 118 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 122 */     Room.saveAll();
/*     */   }
/*     */   
/* 125 */   DateFormat format = new java.text.SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]\t");
/*     */   
/*     */   public void log(String s) {
/* 128 */     try { this.logWriter.write(this.format.format(Calendar.getInstance().getTime()) + s + "\n");
/* 129 */       this.logWriter.flush();
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\server\Server.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */