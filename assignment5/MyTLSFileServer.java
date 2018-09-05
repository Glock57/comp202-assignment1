import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.io.*;
import java.util.*;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
public class MyTLSFileServer{
	private static ServerSocketFactory getSSF() throws NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException
	{
	/*
	* Get an SSL Context that speaks some version
	* of TLS, a KeyManager that can hold certs in
	* X.509 format, and a JavaKeyStore (JKS)
	* instance
	*/
	SSLContext ctx =SSLContext.getInstance("TLS");
	KeyManagerFactory kmf =KeyManagerFactory.getInstance("SunX509");
	KeyStore ks =KeyStore.getInstance("JKS");
	/*
	* store the passphrase to unlock the JKS file.
	* completely insecure, there are better ways.
	* DON’T DO THIS!
	*/
	char[] passphrase = "741852".toCharArray();
	/*
	* load the keystore file. The passhrase is
	* an optional parameter to allow for integrity
	* checking of the keystore. Could be null
	*/
	ks.load(new FileInputStream("server.jks"),passphrase);
	/*
	* init the KeyManagerFactory with a source
	* of key material. The passphrase is necessary
	* to unlock the private key contained.
	*/
	kmf.init(ks, passphrase);
	/*
	* initialise the SSL context with the keys.
	*/
	ctx.init(kmf.getKeyManagers(), null, null);
	/*
	* get the factory we will use to create
	* our SSLServerSocket
	*/
	SSLServerSocketFactory ssf =ctx.getServerSocketFactory();
	return ssf;
	}
	public static void main(String args[]) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException
	{
	/*
	* use the getSSF method to get a
	* SSLServerSocketFactory and create our
	* SSLServerSocket, bound to specified port
	*/
	ServerSocketFactory ssf = getSSF();
	int port=40202;
	SSLServerSocket ss =(SSLServerSocket) ssf.createServerSocket(port);
	//enable the protocols toe the server socket
	String EnabledProtocols[] ={"TLSv1.2", "TLSv1.1"};
	ss.setEnabledProtocols(EnabledProtocols);

	SSLSocket s = (SSLSocket)ss.accept();//once the user is accepted by the server
	BufferedReader reader=new BufferedReader(new InputStreamReader(s.getInputStream()));
	BufferedOutputStream writer=new BufferedOutputStream(s.getOutputStream());
	String line=reader.readLine();//bufferreader will read the file name send from the client
	System.out.println(line);

	//create the file to be accessed
	try{	
		byte [] bytearray  = new byte [1024];//creat a byte arrary to hold data
		File file=new File(line);
		FileInputStream fis=new FileInputStream(file);//read the file as byte
		int re=0;
		while((re=fis.read(bytearray))!=-1)//read the file as byte then 
		{
		writer.write(bytearray,0,re);//send the file to the web broswer
		}
		writer.flush();
		fis.close();//close the fileinputstream
	}catch(FileNotFoundException ex){
	System.err.println("fileNOTfound");}
	writer.flush();
	writer.close();//close the BufferedReader
	reader.close();//close the BufferedOutputStream
	s.close();
	}
	
	
	
}


