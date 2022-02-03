package SaToCoT;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class NetSender {
    public void send_udp(String input_str, String ip_addr, String port) {
        try {
            DatagramSocket ds = new DatagramSocket();

            InetAddress ip = InetAddress.getByName(ip_addr);
            int udp_port = Integer.parseInt(port);
            byte buf[] = null;
            buf = input_str.getBytes();
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, udp_port);
            ds.send(DpSend);
            ds.close();
        } catch (IOException ex) {
            System.out.println("Local error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void send_ms_udp(String input_str, String ip_addr, String port, int ttl) {
        try{
            int udp_port = Integer.parseInt(port);
            Integer ttlByte = new Integer(ttl);
            MulticastSocket mcast_sock = new MulticastSocket();
            byte buf[] = null;
            buf = input_str.getBytes();
            DatagramPacket mc_Send = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip_addr), udp_port);
            mcast_sock.send(mc_Send, ttlByte.byteValue());
            mcast_sock.close();
        } catch (IOException ex) {
            System.out.println("Multicast error: " + ex.getMessage());
            ex.printStackTrace();
        }

    }


}
