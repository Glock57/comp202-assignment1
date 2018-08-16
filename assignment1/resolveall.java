import java.net.InetAddress;
import java.net.UnknownHostException;
public class resolveall {
public static void main(String args[])
{//this is just for the checking the usage	
   if(args.length==0){
      System.err.println("Usage:  java resolve <name1> <name2> ... <nameN>");
      return;
	}
    for(int i=0;i<args.length;i++)//loop every sting in the command line
	{
	
	String address=args[i];//take every sting store them into address
	try{
		InetAddress[] IP=InetAddress.getAllByName(address);//get all ip information from the given address
		System.out.println(address+" : ");//print all the ipv4,ipv6,and dns
		System.out.println("DNS : "+IP[0].getHostName());//print the DNS

		String[] dout=IP[0].getHostAddress().toString().split(".");//split the first ip address  to check if the address is a ipv4 or ipv6
		if(dout.length<=4)
		{
			if(IP.length==1)//check if the length of ip to print the ipv4 address
			System.out.println("IPv4 : "+IP[0].getHostAddress());
			if(IP.length==2)//if the ip has ipv6 or two ipv4 address
			{
			System.out.println("IPv4 : "+IP[0].getHostAddress());
			if(IP[1].getHostAddress().toString().length()>=15)//check if the ip has the ipv4 or ipv6
			System.out.println("IPv6 : "+IP[1].getHostAddress());//print the ipv6 address
			else
			{
			System.out.println("IPv4 : "+IP[1].getHostAddress());}//print another ipv4 address
			}		
		}
		else
		{
			System.out.println("IPv6 : "+IP[0].getHostAddress());//if just one ipv6 address then print out
					}
	}
	
	catch(UnknownHostException e){//catch the exception
			System.err.println(address+" : unknown host");
	}


	}
	
	}
}

