/*     */ package co.kepler.wallofdoodles.client;
/*     */ 
/*     */ import co.kepler.wallofdoodles.PacketManager;
/*     */ import co.kepler.wallofdoodles.packets.Packet;
/*     */ import co.kepler.wallofdoodles.packets.PacketClientKick;
/*     */ import co.kepler.wallofdoodles.packets.PacketRequestStrings;
/*     */ import co.kepler.wallofdoodles.packets.PacketRoom;
/*     */ import co.kepler.wallofdoodles.packets.PacketShowMessage;
/*     */ import co.kepler.wallofdoodles.packets.PacketUserRoomEnter;
/*     */ import co.kepler.wallofdoodles.packets.PacketUserRoomLeave;
/*     */ import co.kepler.wallofdoodles.packets.WallAction;
/*     */ import java.io.IOException;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ public class Client
/*     */ {
/*     */   private static final String DEF_IP = "wallofdoodles.kepler.co";
/*     */   private static final int DEF_PORT = 31415;
/*     */   private String ip;
/*     */   private int port;
/*     */   private Window window;
/*     */   private java.net.Socket socket;
/*     */   private PacketManager pm;
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/*  29 */       javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (Exception localException) {}
/*  32 */     boolean first = true;
/*  33 */     String result = "wallofdoodles.kepler.co";
/*     */     for (;;) {
/*  35 */       if (!first) JOptionPane.showMessageDialog(null, "Invalid IP/Port!");
/*  36 */       first = false;
/*  37 */       result = co.kepler.wallofdoodles.InputDialog.showDialog(null, "Enter Server Address", 
/*  38 */         "IP:Port", "wallofdoodles.kepler.co", 1, -1, 6);
/*  39 */       if (result == null) return;
/*  40 */       String[] ipPort = result.split(":");
/*  41 */       if (ipPort.length <= 2) {
/*  42 */         if (ipPort.length != 2) break;
/*  43 */         try { Integer.parseInt(ipPort[1]);
/*     */         } catch (NumberFormatException localNumberFormatException) {}
/*     */       }
/*     */     }
/*     */     String[] ipPort;
/*  48 */     String ip = ipPort[0];
/*  49 */     int port = ipPort.length == 1 ? 31415 : Integer.parseInt(ipPort[1]);
/*     */     
/*  51 */     Client client = new Client(ip, port);
/*     */     try {
/*  53 */       client.start();
/*     */     } catch (IOException e) {
/*  55 */       JOptionPane.showMessageDialog(null, "Error connecting to server:\n" + e.getMessage());
/*  56 */       return;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Client(String ip, int port)
/*     */   {
/*  67 */     this.ip = ip;
/*  68 */     this.port = port;
/*     */   }
/*     */   
/*     */   public void start() throws java.net.UnknownHostException, IOException {
/*  72 */     this.window = new Window();
/*  73 */     this.window.setVisible(true);
/*  74 */     this.window.setTitle("Connecting to server...");
/*     */     
/*  76 */     this.socket = new java.net.Socket(this.ip, this.port);
/*  77 */     this.pm = new PacketManager(this.socket);
/*     */     
/*     */     try
/*     */     {
/*  81 */       java.awt.Graphics2D g = null;
/*  82 */       Packet packet; while ((packet = this.pm.readPacket()) != null) { Packet packet;
/*  83 */         if ((packet instanceof PacketClientKick)) {
/*  84 */           String msg = ((PacketClientKick)packet).getMessage();
/*  85 */           if (msg != null) JOptionPane.showMessageDialog(this.window, msg);
/*  86 */           System.exit(0);
/*  87 */         } else if ((packet instanceof PacketRequestStrings)) {
/*  88 */           PacketRequestStrings p = (PacketRequestStrings)packet;
/*  89 */           this.pm.writePacket(new Packet[] { co.kepler.wallofdoodles.InputDialog.showDialog(this.window, p) });
/*  90 */         } else if ((packet instanceof PacketRoom)) {
/*  91 */           PacketRoom p = (PacketRoom)packet;
/*  92 */           this.window.setRoom(p.getWall(), p.getUsers(), p.getRoomName());
/*  93 */           this.window.getWallComponent().addWallActionListener(new WallActionListener());
/*  94 */           g = this.window.getWallComponent().getWall().createGraphics();
/*  95 */         } else if ((packet instanceof PacketShowMessage)) {
/*  96 */           PacketShowMessage p = (PacketShowMessage)packet;
/*  97 */           JOptionPane.showMessageDialog(this.window, p.getMessage());
/*  98 */         } else if ((packet instanceof PacketUserRoomEnter)) {
/*  99 */           PacketUserRoomEnter p = (PacketUserRoomEnter)packet;
/* 100 */           this.window.addUser(p.getUser());
/* 101 */         } else if ((packet instanceof PacketUserRoomLeave)) {
/* 102 */           PacketUserRoomLeave p = (PacketUserRoomLeave)packet;
/* 103 */           this.window.removeUser(p.getUser());
/* 104 */         } else if ((packet instanceof WallAction)) {
/* 105 */           WallAction a = (WallAction)packet;
/* 106 */           this.window.sendWallAction(a, g);
/*     */         } else {
/* 108 */           System.out.println("Unhandled packet: " + packet.getClass().getSimpleName());
/*     */         }
/*     */       }
/*     */     } catch (IOException e) {
/* 112 */       e.printStackTrace();
/* 113 */       JOptionPane.showMessageDialog(this.window, "Fatal error: " + e.getMessage());
/*     */     }
/* 115 */     this.window.setVisible(false);
/* 116 */     System.exit(0);
/*     */   }
/*     */   
/*     */   class WallActionListener implements WallComponent.WallActionListener {
/*     */     WallActionListener() {}
/*     */     
/* 122 */     public void onAction(WallAction a) { try { Client.this.pm.writePacket(new Packet[] { a });
/*     */       } catch (IOException e) {
/* 124 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\client\Client.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */