import java.net.*;
import java.io.*;
import java.util.*;

public class TFTPClient {
	
	static int cport=0;
	static int sport=0;
	static String filename="test.txt";
	static InetAddress sADD;
	public static void main(String[] args) throws IOException{

		sADD=InetAddress.getByName(args[0]);//first input is address 
		sport=Integer.parseInt(args[1]);// the port number
		filename=args[2];//third input is filename
		DatagramSocket ds2 =new DatagramSocket(0);//datagram socket to send package and receive package
		Client c=new Client(ds2,sport,sADD);//create a client 
		c.sendRRQ(filename);//send request with file name
		ByteArrayOutputStream get=c.receiveDATA();
		FileOutputStream fs=new FileOutputStream(filename);
		fs.write(get.toByteArray(), 0, get.size());
	}
	
}


class Client
{
	private static final byte RRQ = 1;
    private static final byte DATA = 2;
    private static final byte ACK = 3;
    private static final byte ERROR = 4;
    DatagramSocket ds;
	DatagramPacket send=null;
	DatagramPacket receive=null;
	int sport;
	InetAddress sADD;
    public Client(DatagramSocket d,int s,InetAddress ad){
    	ds=d;
    	sport=s;
    	sADD=ad;
    }
public void sendRRQ(String s) throws IOException
{

	
	String filename=s;
	int length=1+filename.length();//the length of the byte array 
	byte[]rrq=new byte[length];
	rrq[0]=RRQ;//insert the rrq header to front
	for(int i=1;i<filename.length()+1;i++)
	{
		rrq[i]=(byte)filename.charAt(i-1); //put the file name to byte array
	}
	
	send=new DatagramPacket(rrq,rrq.length,sADD,sport);//create the request package
	
	ds.send(send);//send the package to server
	
	

}
public ByteArrayOutputStream receiveDATA()

{
	ByteArrayOutputStream outfile=new ByteArrayOutputStream();
	
	try{
		
		do{

	byte[]pp=new byte[514];
	receive=new DatagramPacket(pp,pp.length);//intial the receive package
	ds.receive(receive);	//receive the package
	System.out.println("length: "+receive.getLength());
	byte type=pp[0];
	if(type==ERROR)//if the file cant find
	{
	String emessage = new String(pp);//print the error message 
	System.out.println(emessage);
	System.exit(0);//close the program
	}
	else if(type==DATA)//if the data package has arrive
	{  
		byte block=pp[1];//extract the block number 
		System.out.println("# "+Byte.toString(block));
		DataOutputStream dos=new DataOutputStream(outfile);
		dos.write(receive.getData(),2,receive.getLength()-2);//write the data to the dataoutput stream
		sendACK(block);//send the acknowladge
	}
		}
		while(!checktheEnd(receive)==true);//check the package is the end
		
	return outfile;		
	}
	catch(Exception e)
	{System.err.println();}
	
	return outfile;
}

public Boolean checktheEnd(DatagramPacket receive)//this method is to check if the last package which is must less than 514 byte
{
	if(receive.getLength()<514)
		return true;
	else
		return false;
}


public void sendACK(int block) throws IOException//send the acknowladge
{byte[] ack=new byte[2];
ack[0]=ACK;
ack[1]=(byte)block;
DatagramPacket p=new DatagramPacket(ack,0,ack.length,sADD,receive.getPort());
ds.send(p);
	

}
}
