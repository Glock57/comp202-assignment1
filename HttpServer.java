import java.net.*;
import java.io.*;
import java.util.*;

class HttpServer
{
	public static void main(String args[])
	{
	//create the httpserver
	HttpServer server=new HttpServer();
	server.server_start();//start the httpserver
	}
	public void server_start()
	{
	try {
		ServerSocket ss = new ServerSocket(8888);//create a serversocket and give a port number 8888
	    while(true) {//infinite loop to chatch the client socktet
		Socket client = ss.accept();//when the client has been accepted by the serversocket
		System.out.println("the connection is accepted!");//print a connection successful message
		HttpServerSession thread =new HttpServerSession(client);//create a thread allow other user to access this server
		thread.start();//start the thread
	   	 }
	}
	catch(Exception e) {System.err.println("Exception: " + e);}//print the exception message
	}
}
class HttpServerSession extends Thread//httpseversession extend from thread class 
{
    private Socket client;//declear the socket to take the parameter from httpserver
    BufferedReader reader;//to read the message from the http request
    BufferedOutputStream writer;//write the response to the http server
    String response1="HTTP/1.1 404";//respones
    String response="HTTP/1.1 200 OK";//respones
    public void run()
    {
	try {
	InetAddress clientIP=client.getInetAddress();//get the ip for client
	String host=clientIP.getHostAddress();//get the host
	System.out.println("connection has been accepted by "+host);//print out the message
	String line=reader.readLine();//read the first line of request 
	System.out.println(line);//display it in the command line
	String filename="";
	while(true){
		if(line == null) {
		System.err.println("Don't have request");
		}
		if(line.compareTo("") == 0)
		break;
		String parts[] = line.split(" ");//split the command
		if(parts.length==3&&parts[0].compareTo("GET")==0)
		{

		//part[1]- /ChatServer.java ---substring method will take the string after number of char 
		filename= parts[1].substring(1);//get the file name from the command
		}
		line=reader.readLine();//loop the request
	}

	if(filename.isEmpty())//if the file is not exist of not request for the file then jump to the index page
	filename="Index.html";
	 File file = new File ("/home/sa227/comp202/comp202Assignment2/"+filename);//create the file to be accessed
	if(file.exists())//if the file is exist at local
	{
		println(writer, response);//send the respones
		println(writer, "");
		byte [] bytearray  = new byte [1024];//creat a byte arrary to hold data
		FileInputStream fis=new FileInputStream(file);//read the file as byte
		int re=0;
		while((re=fis.read(bytearray))!=-1)//read the file as byte then 
		{
		
		writer.write(bytearray,0,re);//send the file to the web broswer
		sleep(1000);//set a delay
		}

		writer.flush();
		fis.close();//close the fileinputstream
	}
	else
	{	
	
		println(writer, response1);//send the respones
		println(writer, "");
		File file1 = new File ("/home/sa227/comp202/comp202Assignment2/404.html");//create the error html to be accessed
		byte [] bytearray  = new byte [1024];//creat a byte arrary to hold data
		FileInputStream fis=new FileInputStream(file1);//read the file as byte
		int re=0;
		while((re=fis.read(bytearray))!=-1)//read the file as byte then 
		{
		writer.write(bytearray,0,re);//send the file to the web broswer
		}

		writer.flush();
		fis.close();//close the fileinputstream

	}
	writer.close();//close the BufferedReader
	reader.close();//close the BufferedOutputStream
	client.close();//close the sockte
	}

	catch(Exception e) {
	    System.err.println("Exception: " + e);
	}
    }
    public HttpServerSession(Socket b) throws Exception//the construction of HTTPserversesssion with the paramater socket
    {
	client = b;
	reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
	writer=new BufferedOutputStream(client.getOutputStream());
    }
    private void println(BufferedOutputStream bos, String s)throws IOException//print the message and loop every text add end tag
	{
	String news = s + "\r\n";
	byte[] array = news.getBytes();
	for(int i=0; i<array.length; i++)
	bos.write(array[i]);
	return;
	}
}
