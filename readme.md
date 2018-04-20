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