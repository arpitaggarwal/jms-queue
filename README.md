# jms-queue
JMS Application to send messages to Websphere MQ using Queue

Use command ```crtmqm``` to create a queue manager called QMA as follows:

```bash 
crtmqm QMA
```

Use ```strmqm``` command to start the queue manager QMA as follow:
```bash
strmqm QMA
```

Start MQSC to define a local queue called QUEUE1 then stop MQSC as follows:

```bash 
runmqsc QMA
DEFINE QLOCAL (QUEUE1)
end
```

Start MQSC to define and start a local listener called qmq.listener then stop MQSC as follows:

```bash
runmqsc QMA
DEFINE LISTENER(qmq.listener) TRPTYPE (TCP) PORT(60000)
start listener(qmq.listener)
end
```

Check if listener started as follows:

```bash
ps -ef | grep qmq.listener
```


Reference : http://sadockobeth.blogspot.in/2014/03/how-to-install-ibm-websphere-mq-75-on.html
http://www.webspheretools.com/sites/webspheretools.nsf/docs/Create%20Listener%20WebSphere%20MQ!opendocument
