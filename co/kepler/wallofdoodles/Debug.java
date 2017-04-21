/*   */ package co.kepler.wallofdoodles;
/*   */ 
/*   */ import java.io.IOException;
/*   */ 
/*   */ public class Debug
/*   */ {
/*   */   public static void main(String[] args) throws java.net.UnknownHostException, IOException
/*   */   {
/* 9 */     Runtime.getRuntime().addShutdownHook(new Thread() {
/*   */       public void run() {
/* ; */         System.out.println("AAA");
/*   */       }
/*   */     });
/*   */   }
/*   */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\Debug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */