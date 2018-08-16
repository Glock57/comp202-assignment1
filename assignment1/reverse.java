import java.net.InetAddress;
import java.net.UnknownHostException;

public class reverse {
public static void main(String args[])
{{//this is just for the checking the usage	
   if(args.length==0){
      System.err.println("Usage:  java reverse <name1> <name2> ... <nameN>");
      return;
	}
    for(int i=0;i<args.length;i++)//loop every sting in the command line
	{
	String address=args[i];//take every sting store them into address
	try{
		
		InetAddress IP=InetAddress.getByName(address);//get all ip information from the given address
		//check the host address and the host name check if their same
		if(IP.getHostAddress().compareTo(IP.getHostName())!=0)
		{System.out.println(address+" : "+IP.getHostName());}//print out the message
		else{System.out.println(address+" : "+"no name");}
		}
	catch(UnknownHostException e){//catch the exception
			System.err.println(address+" : Unkonwn host");
	}
	}
	
	}
}
}

