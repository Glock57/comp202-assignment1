import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStreamReader;
import java.io.IOException;

public class SimpleServer {
public static void main(String args[])
{
	try{
	ServerSocket ss = new ServerSocket(0);//bind the first free port and get the free port
	System.out.println("free port:"+ss.getLocalPort());//printout
	while(true) {
	Socket client = ss.accept();//onece the client has access the server port
	 PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
	 BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));//get the mesage from the user socket
	 InetAddress clientIP=client.getInetAddress();//get the ip for client
	 String host=clientIP.getHostAddress();//get the host
	 writer.println("Hello,"+clientIP.getHostName());
	 writer.println("Your IP address is: "+host);//print out the message
	 client.close();
	} 
	}
	catch(Exception e){//catch the exception
		System.err.println(e);
		}
	}
}


