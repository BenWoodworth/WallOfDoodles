/*    */ package co.kepler.wallofdoodles.server;
/*    */ 
/*    */ import co.kepler.wallofdoodles.PacketManager;
/*    */ import co.kepler.wallofdoodles.User;
/*    */ import co.kepler.wallofdoodles.packets.Packet;
/*    */ import co.kepler.wallofdoodles.packets.PacketClientKick;
/*    */ import co.kepler.wallofdoodles.packets.PacketRequestStrings;
/*    */ import co.kepler.wallofdoodles.packets.PacketStringResponse;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.net.Socket;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ClientConnection implements Runnable
/*    */ {
/*    */   private Socket socket;
/*    */   private User user;
/*    */   private PacketManager pm;
/*    */   Thread thread;
/*    */   private Server server;
/*    */   
/*    */   public ClientConnection(Server server, Socket socket) throws IOException
/*    */   {
/* 26 */     this.socket = socket;
/* 27 */     this.pm = new PacketManager(socket);
/* 28 */     this.server = server;
/*    */   }
/*    */   
/* 31 */   private List<ClientListener> listeners = new ArrayList();
/* 32 */   public void addPacketListener(ClientListener l) { this.listeners.add(l); }
/* 33 */   public void removePacketListener(ClientListener l) { this.listeners.remove(l); }
/*    */   
/* 35 */   private void triggerPacketReceived(Packet p) { ClientListener cl; for (Iterator localIterator = this.listeners.iterator(); localIterator.hasNext(); cl.packetReceived(this, p)) cl = (ClientListener)localIterator.next();
/*    */   }
/*    */   
/* 38 */   public void start() { new Thread(this).start(); }
/*    */   
/*    */   public PacketManager getPacketManager()
/*    */   {
/* 42 */     return this.pm;
/*    */   }
/*    */   
/*    */   private void disconnect() {
/* 46 */     if (this.user != null)
/* 47 */       Room.getUserRoom(this.user).removeClient(this);
/* 48 */     System.out.println("Client disconnected");
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     try {
/* 53 */       PacketRequestStrings request = new PacketRequestStrings("Connect to Server", 
/* 54 */         new String[] { "Name", "Room" }, new String[] { "", "main" }, 
/* 55 */         new int[] { 16, 16 }, new int[] { 1, 1 }, 
/* 56 */         new int[] { 4, 4 });
/* 57 */       this.pm.writePacket(new Packet[] { request });
/* 58 */       PacketStringResponse response = (PacketStringResponse)this.pm.readPacket();
/* 59 */       if (!response.hitOkay()) {
/* 60 */         this.pm.writePacket(new Packet[] { new PacketClientKick(null) });
/* 61 */         disconnect();
/* 62 */         return;
/*    */       }
/* 64 */       this.user = new User(response.getStrings()[0]);
/* 65 */       String s = "New connection: " + this.user + 
/* 66 */         " (" + this.socket.getInetAddress().getHostAddress() + ")";
/* 67 */       System.out.println(s);
/* 68 */       this.server.log(s);
/* 69 */       Room room = Room.getInstance(response.getStrings()[1]);
/* 70 */       this.pm.writePacket(new Packet[] { new co.kepler.wallofdoodles.packets.PacketRoom(room) });
/* 71 */       room.addClient(this);
/* 72 */       listen();
/*    */     } catch (IOException localIOException) {}
/* 74 */     disconnect();
/*    */   }
/*    */   
/*    */   public void listen() throws IOException {
/*    */     Packet packet;
/* 79 */     while ((packet = this.pm.readPacket()) != null) { Packet packet;
/* 80 */       triggerPacketReceived(packet);
/*    */     }
/*    */   }
/*    */   
/* 84 */   public Socket getSocket() { return this.socket; }
/* 85 */   public User getUser() { return this.user; }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\server\ClientConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */