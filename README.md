# jms-queue
JMS Application to send messages to Websphere MQ using Queue

1. Use command ```crtmqm``` to create a queue manager called QMA as follows:

```bash 
crtmqm QMA
```

2. Use ```strmqm``` command to start the queue manager QMA as follow:
```strmqm 
crtmqm QMA
```

3. Start MQSC to define a local queue called QUEUE1 then stop MQSC as follows:

```bash 
runmqsc QMA
DEFINE QLOCAL (QUEUE1)
end
```

4. Start MQSC to define and start a local listener called qmq.listener then stop MQSC as follows:

```bash
runmqsc QMA
DEFINE LISTENER(qmq.listener) TRPTYPE (TCP) PORT(60000)
start listener(qmq.listener)
end
```

5. Check if listener started as follows:

```bash
ps -ef | grep qmq.listener
```



