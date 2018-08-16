import java.net.*;
import java.io.*;
import java.util.*;



public class TFTP {

	 public static void main(String args[])
	    {
		TftpServer d = new TftpServer();
		d.start_server();
	    }

}


class TftpServerWorker extends Thread
{
    private DatagramPacket req;
    private DatagramPacket data;
    private DatagramPacket datas;
    private DatagramPacket error;
    private static final byte RRQ = 1;
    private static final byte DATA = 2;
    private static final byte ACK = 3;
    private static final byte ERROR = 4;
    //private InetAddress cip;
    private int cport;
    private byte block=1;
    
    private void sendfile(String filename,DatagramSocket ds) throws IOException 
    {
        int count=0;
    	try {
    		
    		File file = new File (filename);//create the file to be accessed
    		System.out.println(file.exists()+" File check");
    		if(file.exists())
        	{
    		FileInputStream fis=new FileInputStream(file);//read the file as byte
        	byte[] bytearray=new byte[514];//byte array for size over 514 byte
		FileInputStream fis1=new FileInputStream(file);
		byte[] sbytearray=new byte[fis1.available()+2];
		fis1.read(sbytearray,2,fis1.available());//byte array for size less than 514 byte
			
			if(sbytearray.length<512)//if the file less than 512 byte then use the second byte array
			{
				sbytearray[0]=DATA;
				sbytearray[1]=block;
				
				datas=new DatagramPacket(sbytearray,sbytearray.length,req.getAddress(),req.getPort());
				ds.send(datas);
				return;
			}
				/*send the first data package */		
			bytearray[0]=DATA;
			bytearray[1]=block;
			fis.read(bytearray, 2, 512);
			data=new DatagramPacket(bytearray,bytearray.length,req.getAddress(),req.getPort());
			ds.send(data);
			
			byte[]ack=new byte[2];
			DatagramPacket packet=new DatagramPacket(ack,ack.length);//create a package for acknowladge 
			
			while(true)
			{
				ds.receive(packet);
				String test=new String(ack);
				System.out.println("# "+Byte.toString(ack[1]));//print the acknoladge number
				if(ack[0]==ACK&&ack[1]==block)//if ack number machthe block number
				{
					block++;
					bytearray[0]=DATA;
					bytearray[1]=block;
					
					count=fis.read(bytearray, 2, 512); //read the file into block 
					if(count == -1)
					break;
					else
					data=new DatagramPacket(bytearray,count+2,req.getAddress(),req.getPort());//create the package for it
					ds.send(data);//send the package
					
				
				}
				
				
			}
			
        	}
    		else{
    			
    			byte[] buffer=(" Sorry! we can't find "+ filename).getBytes(); // send error message + name 
    			buffer[0]= ERROR;
    			error=new DatagramPacket(buffer,buffer.length,req.getAddress(),req.getPort());
    			ds.send(error);
    			
    		}
    	}catch(SocketException a ){//time out and resend the block of data
    		File file = new File (filename);
    		FileInputStream fis=new FileInputStream(file);
        	byte[] bytearray=new byte[514];
    		count=fis.read(bytearray, 2, 512); //
			data=new DatagramPacket(bytearray,count+2,req.getAddress(),req.getPort());
			ds.send(data);
    		
    	
			
		} catch (Exception e) {
			System.err.println(e);
		}
    	ds.close();
	return;
    }
    
    public void run()
    {
        try{/*
         * parse the request packet, ensuring that it is a RRQ
         * and then call sendfile
         */
    
		DatagramSocket ds1=new DatagramSocket(0);//create a new socket to send file
		
		cport=ds1.getLocalPort();
    	byte[] pack;
    	pack=req.getData();
    	String filename="";
        if(pack[0]==RRQ)
        {
        	/*get the file name and convert it to string*/
        	for(int i=1;i<pack.length;i++)
        	{
        		if(pack[i]==0)
        			break;
        		filename+=(char)pack[i];
        	}
        	
        }
        System.out.println(filename);//print out the string
        sendfile(filename,ds1);//parse the file name and the DatagramSocket to send file
        }
        catch(Exception e)
        {
        	System.err.println("Exception: " + e);
        }
        
	return;
    }

    public TftpServerWorker(DatagramPacket req)
    {
	this.req = req;
    }
}

class TftpServer
{
    public void start_server()
    {
	try {
		
	    DatagramSocket ds = new DatagramSocket(0);
	    
	    System.out.println("TftpServer on port " + ds.getLocalPort());

	    for(;;) {
		byte[] buf = new byte[514];
		DatagramPacket p = new DatagramPacket(buf, 514);//create a datapackage to receive the request
		ds.receive(p);//receive the request pagckage
		
		TftpServerWorker worker = new TftpServerWorker(p);
		worker.start();
	    }
	}
	catch(Exception e) {
	    System.err.println("Exception: " + e);
	}

	return;
    }

   
}

