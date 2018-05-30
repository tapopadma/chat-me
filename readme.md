(i) Deploying html and angularjs resources to tomcat server in spring and angularjs
------------------------------------------------------------
#Spring SERVLET
---------------
Spring provides a DispatcherServlet that helps create a servlet container in tomcat server. Inside the servlet mapper the desired resources can be 
referred as mvc resources. The servlet mapper also helps create beans for useful backend resources.

#Spring MVC
-----------
spring mvc framework supports creation of endpoints in the backend

#Spring JDBC
-----------
this library helps interact with persistence layer or db engine from java application layer without need to worry about the creating and destroying sql connection using a driver like mysql-connector.

(ii) Web session management in spring and angularjs
---------------------------------------------------
#Spring SECURITY
----------------
this framework provides built-in web-session management tool. this just requires the developer to add a spring configuration file to define the endpoints for login/logout and an authentication provider 
class to define the criteria for authenticating a client request.

(iii) Web socket Management in spring and angularjs
---------------------------------------------------
#polling (traditional method) (no socket needed)
------------------------------------------------
When A sends a text to B following happens
1 - a request is sent from A to server
2 - server saves to DB
3 - server sends back a OK to A but B is not informed of it.
4 - Hence everytime B keeps sending requests to check for a new message arrival at server and updates its UI accordingly. 
    This inefficient method is known as polling.

#web-socket (alternate solution to polling) (popular method using spring-web-socket , sockjs & stompjs)
--------------------------------------------------------------------------------------------------------
this is another important module from spring framework that supports a full-duplex web-socket protocol i.e Server-client can interact in both ways. 
Hence server can broadcast a message to each client whenever necessary without the client concerning about sending request to server every second.
Developer just needs to add a spring configuration to configure the endpoint and application destination. At client side just need to connect to the 
server at the socket url and send message to the desired end point and get reply from the corresponding desitnation url.
This is efficient since in this environment client doesn't bother about updates from the server.

(iv) Displaying user's current loggedin status and message typing status
------------------------------------------------------------------------
#Message typing status
----------------------
Every time user types something this information should be broadcasted through web-socket to all relevant users. so spring-web-socket is enough here. every time
the text area is changed an event has to be triggered that will send signal to the server through socket and the server will broadcast it through the some socket-endpoint
in no time. In this way the other users dont have to send separate requests each to the server to get the current user's typing status. so socket conncection is a must here.
#User loggedin status
---------------------
a) Sending requests every second to server to get the latest active user list in current session is super inefficient.
Again socket has to be the need. Every time a user logs in or logs out the server should broadcast the updated session status of that particular user through socket-endpoint.
This updates 1 user status per broadcast and hence efficient. 
b) Spring security framework will trigger an event each time a user logs in / out(for this developer needs to define login/outSucessHandler). So a Event Handler class implementing HttpSessionBindingListener will be good enough to update 
our activeUserList. Developer has to maintain a bean for the activeuserlist which is nothing but a list of usernames. as soon as this list is updated it can be broadcasted over
socket.

ER Diagram
----------
https://www.lucidchart.com/invitations/accept/970a96a8-719e-4533-8e8e-2f72cf63101c
