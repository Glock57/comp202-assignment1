import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStreamReader;
import java.io.IOException;

public class SimpleClient {
public static void main(String args[])
{

	try{
		Socket me = new Socket(args[0],Integer.parseInt(args[1]));//connect to the socket to the sever
		PrintWriter writer = new PrintWriter(me.getOutputStream(), true);
		BufferedReader reader = new BufferedReader(new InputStreamReader(me.getInputStream()));//get the mesage from the server socket
		String response = reader.readLine();//read the message from server
		while(response!=null)//loop to read the message
		{
		System.out.println(response); 
		response = reader.readLine();//printout the message
		}
		
		
	} 
	
	catch(Exception e){//catch the exception
		System.err.println(e);
		}
	}
}


