import java.net.InetAddress;
import java.net.UnknownHostException;
public class resolve {
	public static void main(String args[])
	{
	//this is just for the checking the usage	
   	if(args.length==0){
      System.err.println("Usage:  java resolve <name1> <name2> ... <nameN>");
      return;
	}
	//loop every sting in the command line
    for(int i=0;i<args.length;i++)
	{
	String address=args[i];//take every sting store them into address
	try{
		
		InetAddress IP=InetAddress.getByName(address);//get the ip imfomation through the method getbyname
		System.out.println(address+" : "+IP.getHostAddress());//print the address of the ip
		}
	
	catch(UnknownHostException e){//catch the exception
			System.err.println(address+" : unknown host");
	}
	}
	
	}
}

