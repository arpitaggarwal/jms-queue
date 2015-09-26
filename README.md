# jms-queue
JMS Application to send messages to Websphere MQ using Queue

Download [Websphere MQ V7.5 Trial][1]

[1]: https://www-01.ibm.com/marketing/iwm/iwm/web/reg/pick.do?source=ESD-WSMQ-EVAL&S_TACT=109J84RW&S_CMP=web_ibm_ws_xx_bt_wshome&lang=en_US

### Setup WebSphere MQ User and Group

Login to the system as user root then create user and group as follow:
```bash
[root@arpitaggarwal ~]# groupadd mqm
[root@arpitaggarwal ~]# useradd -g mqm mqm
[root@arpitaggarwal ~]# passwd mqm
Changing password for user mqm.
New UNIX password:
Retype new UNIX password:
passwd: all authentication tokens updated successfully.
[root@arpitaggarwal ~]#
```

### Kernel Configuration Parameters
Open the file "/etc/sysctl.conf" and add below content:
```bash
kernel.shmmni = 4096
kernel.shmall = 2097152
kernel.shmmax = 268435456
kernel.sem = 500 256000 250 1024
net.ipv4.tcp_keepalive_time = 300
```

### Load these sysctl values immediately, execute command "sysctl -p" as follows:
```bash
[root@arpitaggarwal ~]# sysctl -p
```

### Add the following information to the "/etc/security/limits.conf" file:
```bash
mqm              hard    nofile          10240
mqm              soft    nofile          10240
```

### Read and Accept License
Login as root set your current working directory to the installation files. Run the "./mqlicense.sh -accept" command as follows:
```bash
[root@arpitaggarwal MQ_7.5.0.2]# ./mqlicense.sh -accept
```

### Install WebSphere MQ Components
Log in as root and install below WebSphere MQ Components: 
   
    - MQSeriesRuntime
    - MQSeriesServer
    - MQSeriesClient
    - MQSeriesSDK
    - MQSeriesSample
    - MQSeriesJava
    - MQSeriesMan
    - MQSeriesJRE
    - MQSeriesExplorer
    
   
as follows:

```bash
[root@arpitaggarwal MQ_7.5.0.2]# rpm -ivh MQSeriesRuntime-7.5.0-2.x86_64.rpm
```

### Post Installations
Execute ```MQ_INSTALLATION_PATH/bin/setmqinst -i -p MQ_INSTALLATION_PATH``` where MQ_INSTALLATION_PATH represents the directory where WebSphere MQ is installed, as follows:

```bash
[root@arpitaggarwal ~]# /opt/mqm/bin/setmqinst -i -p /opt/mqm/
```

** Use ```setmqenv``` command to set various environment variables for a particular installation of WebSphere MQ**

```bash
[root@arpitaggarwal ~]# su - mqm
[mqm@arpitaggarwal ~]$ . /opt/mqm/bin/setmqenv -s
```

** Use ```dspmqver``` command to check that the environment is set up correctly using dspmqver command as follow:**

```bash
[mqm@arpitaggarwal ~]$ dspmqver
```

### Use command ```crtmqm``` to create a queue manager called QMA as follows:

```bash 
crtmqm QMA
```

** Use ```strmqm``` command to start the queue manager QMA as follow:** 
```bash
strmqm QMA
```

** Start MQSC to disable the **```CHLAUTH```** of a Queue Manager(QMGR) then stop MQSC as follows:** 

```bash 
runmqsc QMA
ALTER QMGR CHLAUTH(DISABLED)
end
```


** Start MQSC to define a local queue called QUEUE1 then stop MQSC as follows:** 

```bash 
runmqsc QMA
DEFINE QLOCAL (QUEUE1)
end
```

** Start MQSC to define and start a local listener called qmq.listener then stop MQSC as follows:** 

```bash
runmqsc QMA
DEFINE LISTENER(qmq.listener) TRPTYPE (TCP) PORT(60000)
start listener(qmq.listener)
end
```

** Check if listener started as follows:** 

```bash
ps -ef | grep qmq.listener
```

** To recieve the message, Change into the ```MQ_INSTALLATION_PATH/samp/bin``` directory and execute the commands:**

```bash
cd /opt/mqm/samp/bin/
./amqsget QUEUE1 QMA
```

**Source** : http://sadockobeth.blogspot.in/2014/03/how-to-install-ibm-websphere-mq-75-on.html

References : 

1. http://www.webspheretools.com/sites/webspheretools.nsf/docs/Create%20Listener%20WebSphere%20MQ!opendocument

2. http://www.webspheretools.com/sites/webspheretools.nsf/docs/WebSphere%20MQ%20queue%20manager%20not%20available!opendocument


3. https://www.ibm.com/developerworks/community/blogs/aimsupport/entry/blocked_by_chlauth_why?lang=en


