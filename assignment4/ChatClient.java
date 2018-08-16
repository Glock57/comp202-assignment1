import java.net.*;
import java.io.*;
import java.util.*;
class ChatClientWorker extends Thread
{
    private MulticastSocket ms;
    public void run()
    {
    	try {
             while(true)
	     {
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));//read the input from the terminal
		byte[] b=buf.readLine().getBytes();//change the message to byte
		DatagramPacket send=new DatagramPacket(b,b.length,InetAddress.getByName("239.0.202.1"),40202);//use the datagrampackaet to send the message
		ms.send(send);
}
	} catch (IOException e) {
		System.err.println("Exception: " + e);
	}		
    }
    public ChatClientWorker(MulticastSocket s)
    {
	this.ms = s;
    }
}

class ChatClient
{
    public void start()
    {
	try {
	    	InetAddress group=InetAddress.getByName("239.0.202.1");
		MulticastSocket s=new MulticastSocket(40202);//create the multicastSocket with sepcfic port number
		s.joinGroup(group);//join the multicastSocket 
		ChatClientWorker worker = new ChatClientWorker(s);//create a worker extend from thread
		worker.start();//send the message when the user type in
	    while(true)
		{byte[] buf = new byte[1000];
		DatagramPacket recv = new DatagramPacket(buf, buf.length);//create a recv to receive the message
		s.receive(recv);//receive the pacakge
		String as=new String(recv.getData());//convert the byte array to string
	 	InetAddress rec=recv.getAddress(); //get the message writer address
		System.out.println(rec.getHostAddress()+as);//print out on terminal
	    }
	}
	catch(Exception e) {
	    System.err.println("Exception: " + e);
	}
    }

    public static void main(String args[])
    {
	ChatClient c = new ChatClient();//start a client
	c.start();
    }
}
