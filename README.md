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



	
