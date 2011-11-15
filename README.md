evmsg
=====

* Java 1.6+
* event driven TCP/UDP messaging.
* '\n' termination.
* ascii only, no binary.


Synopsis
========

    import org.shokai.evmsg.*;


TCP Server
----------

    TcpServer server = new TcpServer(5000);
    
    // add event handler, response to client
    final TcpServer that_server = server;
    server.addEventHandler(new TcpServerEventHandler(){
            public void onMessage(int client_id, String line){
                System.out.println("* <"+client_id+"> "+ line);
                that_server.getClient(client_id).send("echo : <"+client_id+"> "+line);
            }
            public void onAccept(int client_id){
                System.out.println("* <"+client_id+"> connection accepted");
            }
            public void onClose(int client_id){
                System.out.println("* <"+client_id+"> closed");
            }
        });
    
    server.listen();


TCP Client
----------

    TcpClient sock = new TcpClient("localhost", 5000);
    
    // add handler
    final TcpClient that_sock = sock;
    sock.addEventHandler(new TcpClientEventHandler(){
            public void onMessage(String line){
                System.out.println(" > "+line);
            }
            public void onOpen(){
                System.out.println("* socket connected");
            }
            public void onClose(){
                System.out.println("* socket closed");
            }
        });
    
    sock.send("hello!!");


UDP
---

    Udp sock = new Udp("localhost", 5001);

    sock.addEventHandler(new UdpEventHandler(){
            public void onMessage(String host, int port, String line){
                System.out.println("<"+host+":"+port+"> "+line);
            }
        });

    sock.send("hello!!");


Run Samples
===========

build
-----

    % javac org/shokai/evmsg/*.java
    % javac samples/*.java

TCP
---

    % java samples.SampleTcpServer
    % java samples.SampleTcpClient
    % ruby tools/multi-clients.rb

UDP
---

    % java samples.SampleUdp
    % ruby tools/udp-echo.rb
    

LICENSE:
========

(The MIT License)

Copyright (c) 2011 Sho Hashimoto

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
'Software'), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
