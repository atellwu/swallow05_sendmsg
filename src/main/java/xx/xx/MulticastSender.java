package xx.xx;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {

	public static void main(String[] args) throws Exception {

		InetAddress ia = null;

		int port = 0;

		// read the address from the command line

		ia = InetAddress.getByName("224.7.7.7");
		port = 5005;

		String msg = "Here's some multicast data\r\n";
		byte[] b = msg.getBytes("iso8859-1");
		byte[] data = new byte[b.length+1];
		data[0] = (byte)b.length;
		System.arraycopy(b, 0, data, 1, b.length);
		
		DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);

		MulticastSocket ms = new MulticastSocket();

		ms.joinGroup(ia);

		for (int i = 1; i < 10; i++) {

			ms.send(dp);

		}

		ms.leaveGroup(ia);

		ms.close();

	}

}