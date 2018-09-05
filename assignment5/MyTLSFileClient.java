
import java.net.*;
import java.security.cert.X509Certificate;
import java.io.*;
import java.util.*;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
public class MyTLSFileClient{
	String hostname;
	int port;
	String filename;
	public MyTLSFileClient(String host,int port,String filename)
	{//set the instance variables
		hostname=host;
		this.port=port;
		this.filename=filename;
	}
	public void stratClient()
	{
		try{
		//use 	SSLSocketFactory to create the sslSocket with the hostname and port
		SSLSocketFactory factory =(SSLSocketFactory)SSLSocketFactory.getDefault();
		SSLSocket socket =(SSLSocket)factory.createSocket(hostname, port);
			/*
		* set HTTPS-style checking of HostName
		* before the handshake commences
		*/
		

		SSLParameters params = new SSLParameters();
		params.setEndpointIdentificationAlgorithm("HTTPS");
		socket.setSSLParameters(params);
		//set the protocol for the socket
		String EnabledProtocols[] ={"TLSv1.2", "TLSv1.1"};
		socket.setEnabledProtocols(EnabledProtocols);
		socket.startHandshake();
		/*
		* at this point, can getInputStream and
		* getOutputStream as you would a regular Socket
		*/
		//send the filename request to server
		PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
		writer.println(filename);

		/* get the X509Certificate for this session */
		SSLSession sesh = socket.getSession();
		X509Certificate cert = (X509Certificate)sesh.getPeerCertificates()[0];
		/* extract the CommonName, and then compare */
		String cn=getCommonName(cert);
		System.out.println(cn); 
		
		//write the file with the inputstream from the sever socket
		BufferedInputStream bis=new BufferedInputStream(socket.getInputStream());
		File file=new File(filename+"_");
		FileOutputStream fs=new FileOutputStream(file);
				
		int count;
		byte[]array=new byte[1024];
		while((count=bis.read(array))!=-1)
		{
		fs.write(array,0,count);//write the data to the dataoutput stream		
		}
		fs.flush();		
		writer.close();
		fs.close();
		}
		catch(Exception e)
		{System.err.println(e);
		}	
		
		
	}
	public String getCommonName(X509Certificate cert) throws Exception//get the common name from the certificate
	{
		String name =cert.getSubjectX500Principal().getName();
		LdapName ln = new LdapName(name);
		String cn = null;
		for(Rdn rdn : ln.getRdns())
			if(hostname.equalsIgnoreCase(rdn.getType()))
				cn = rdn.getValue().toString();
		return cn;
	}

public static void main(String args[])
{
	MyTLSFileClient client=new MyTLSFileClient(args[0],Integer.parseInt(args[1]),args[2]);
	client.stratClient();
}
 
}
