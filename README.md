# UoWaikato-COMP202

## Assignment 1 
*This assignment is the introduction of Java sockets and resolve the internet address*
1.  resolving the IPv4 address(from host name to address)----resolve.java

```under the loop to get the name of the address,use Java Inetaddress resolve,then print out```

2.  reverse recolving (from address to host name)--- reverse.java

```use loop to extrat the address and convert the address to host name ,then print out```

3.  resolve all information of address---- resolveall.java

```the method call ```getAllByName``` use to extrat all info of address,then check the length to print out the info```

4.  this task is creat the networ server---- SimpleClient.java ,SimpleServer.java  

```for client: create a sotck connect to serversocket if connect to server then get the message from the server.```

```for server:get the ip info from the connected socket then send the welcome message to this client socket.```

## Assignment 2
*assignment is base on the HTTP server to receive the request,processing it and send reply(HTML file) for the request*
1. create http server and distribute session base on the connnect client(assign the client stocket to httpserversession) 
2. run the session will estract the file which client want to access
3. as for the respond ,sever will find the file and send the respond text first(bufferedoutputstream),then send file with (bufferedoutputstream)

## Assignment 3
*this assignment is going to explain the functional of the TFTP server imply the UDP datagram socket*
### for the TFTP server ----TFTP.java
1. create a TFTP server and start to wrok
2. sign random free port for udp,infinet loop to receive request
3. extract the filename from request then use a new datagram stocket to send file
4. check if the file exist,use fileInputstream to read data,here have two byte array which are used for different file size,if the file less than 521 byte then use `fis1.available()` to check the actual size then put datastream into packet to send
5. after send the first packet,wait and receive the ACK packet.if the received ACK packet has the same block number then read the data from the bytearray and send packets
6. if the request file dosen't exist,send error packet
7. if doesn't receive the ack in time,recent the data packet base on the data block
### for the TFTP client ----TFTPClient.java
the client side have the same structure as the server side,except the output which will use `DataOutputStream` receive the data ,and convert as bytearray(`ByteArrayOutputStream.toByteArray()`) then output the file(`FileOutputStream.write()`)

## Assignment 4



	
