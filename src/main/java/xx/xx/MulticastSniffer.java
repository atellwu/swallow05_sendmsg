package xx.xx;

import java.net.*;

import java.io.*;

public class MulticastSniffer {

	public static void main(String[] args) throws Exception {

		InetAddress group = null;

		int port = 0;


			group = InetAddress.getByName("224.7.7.7");

			port = 5005;


		MulticastSocket ms = null;

		try {

			ms = new MulticastSocket(port);

			ms.joinGroup(group);

			byte[] buffer = new byte[8192];

			while (true) {

				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);

				ms.receive(dp);

				byte[] rdata = dp.getData();
				byte[] msgB = new byte[rdata[0]];
				System.arraycopy(rdata, 1, msgB, 0, msgB.length);
				String s = new String(msgB, "iso8859-1");

				System.out.println(s);

			}

		}

		catch (IOException ex) {

			ex.printStackTrace();

		}

		finally {

			if (ms != null) {

				try {

					ms.leaveGroup(group);

					ms.close();

				}

				catch (IOException ex) {
				}

			}

		}

	}

}
