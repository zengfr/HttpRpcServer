# HttpRpcServer
httpRpcServer use via Netty  always use remote control  in crawler or spider  send command le pppoestart pppoestop 

httpRpcServer-0.0.1.zip
appStart.cmd  
HttpRpcServer listening on port:8844...
post Url
http://127.0.0.1:8844/?
1、post body: token=1&type=exec&cmd=cmdstr

2、post body: token=1&type=cmd&cmd=dir

3、post body: token=1&type=pppoestart&args=userName userpwd

4、post body: token=1&type=pppoestop


http://127.0.0.1:8844/?token=1&type=http&cmd=get&args=http://news.baidu.com/

http://127.0.0.1:8844/?token=1&type=pppoestop
http://127.0.0.1:8844/?token=1&type=pppoestart

http://127.0.0.1:8844/?token=1&type=cmd&cmd=dir
http://127.0.0.1:8844/?token=1&type=exec&cmd=netstat -ano