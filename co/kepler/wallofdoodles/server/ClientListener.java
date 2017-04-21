package co.kepler.wallofdoodles.server;

import co.kepler.wallofdoodles.packets.Packet;

abstract interface ClientListener
{
  public abstract void packetReceived(ClientConnection paramClientConnection, Packet paramPacket);
}


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\server\ClientListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */