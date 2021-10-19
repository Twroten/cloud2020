# Docker应用

## 一、基于Docker安装MySQL

### 1、建立镜像

#### 拉取官方镜像

（我们这里选择5.7，如果不写后面的版本号则会自动拉取最新版）

```shell
docker pull mysql:5.7   # 拉取 mysql 5.7
docker pull mysql       # 拉取最新版mysql镜像
```



#### 检查是否拉取成功

```
$ sudo docker images
```

#### 创建容器(不需要建立目录映射)

```bash
sudo docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=123456 --restart=always -d mysql
```

- –name：容器名，此处命名为`mysql`
- -e：配置信息，此处配置mysql的root用户的登陆密码
- -p：端口映射，此处映射 主机3306端口 到 容器的3306端口
- -d：后台运行容器，保证在退出终端后容器继续运行

#### 创建容器(如果要建立目录映射)

```shell
sudo docker run -p 3306:3306 --name mysql \
--restart=always \
-v /usr/local/docker/mysql/conf:/etc/mysql \
-v /usr/local/docker/mysql/logs:/var/log/mysql \
-v /usr/local/docker/mysql/data:/var/lib/mysql \
-v /usr/local/docker/mysql/mysql-files:/var/lib/mysql-files \
-e MYSQL_ROOT_PASSWORD=123456 \
-d mysql:8.0.25
```

- -v：主机和容器的目录映射关系，":"前为主机目录，之后为容器目录

#### 检查容器是否正确运行

```shell
docker container ls
```

- 可以看到容器ID，容器的源镜像，启动命令，创建时间，状态，端口映射信息，容器名字

### 2、连接mysql

#### 进入docker本地连接mysql客户端

```shell
sudo docker exec -it mysql bash
mysql -uroot -p123456
```

#### 使用远程连接软件时要注意一个问题

我们在创建容器的时候已经将容器的3306端口和主机的3306端口映射到一起，所以我们应该访问：

```
host: 127.0.0.1
port: 3306
user: root
password: 123456
```

#### 如果你的容器运行正常，但是无法访问到MySQL，一般有以下几个可能的原因：

- 防火墙阻拦

  ```shell
  # 开放端口：
  $ systemctl status firewalld
  $ firewall-cmd  --zone=public --add-port=3306/tcp -permanent
  $ firewall-cmd  --reload
  # 关闭防火墙：
  $ sudo systemctl stop firewalld
  ```

- 需要进入docker本地客户端设置远程访问账号

  ```shell
  $ sudo docker exec -it mysql bash
  $ mysql -uroot -p123456
  mysql> grant all privileges on *.* to root@'%' identified by "password";
  ```

  原理：

  ```shell
  # mysql使用mysql数据库中的user表来管理权限，修改user表就可以修改权限（只有root账号可以修改）
  
  mysql> use mysql;
  Database changed
  
  mysql> select host,user,password from user;
  +--------------+------+-------------------------------------------+
  | host                    | user      | password                                                                 |
  +--------------+------+-------------------------------------------+
  | localhost              | root     | *A731AEBFB621E354CD41BAF207D884A609E81F5E      |
  | 192.168.1.1            | root     | *A731AEBFB621E354CD41BAF207D884A609E81F5E      |
  +--------------+------+-------------------------------------------+
  2 rows in set (0.00 sec)
  
  mysql> grant all privileges  on *.* to root@'%' identified by "password";
  Query OK, 0 rows affected (0.00 sec)
  
  mysql> flush privileges;
  Query OK, 0 rows affected (0.00 sec)
  
  mysql> select host,user,password from user;
  +--------------+------+-------------------------------------------+
  | host                    | user      | password                                                                 |
  +--------------+------+-------------------------------------------+
  | localhost              | root      | *A731AEBFB621E354CD41BAF207D884A609E81F5E     |
  | 192.168.1.1            | root      | *A731AEBFB621E354CD41BAF207D884A609E81F5E     |
  | %                       | root      | *A731AEBFB621E354CD41BAF207D884A609E81F5E     |
  +--------------+------+-------------------------------------------+
  3 rows in set (0.00 sec)
  ```





## 二、基于Docker安装zookeeper

### 1、单节点安装

#### 查询zookeeper镜像

语法：

```bash
sudo docker search zookeeper 
NAME                                DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
zookeeper                           Apache ZooKeeper is an open-source server wh…   433                 [OK] 
```



#### 拉取zookeeper镜像

```bash
sudo docker pull zookeeper 

```





#### 启动zookeeper容器实例

语法：docker run --name zookeeper --restart always -d zookeeper
--restart always：始终重新启动zookeeper

```bash
sudo docker run --name zookeeper --privileged=true -p 2181:2181 --restart=always -d zookeeper
```





#### 查看当前zookeeper容器实例的进程信息

语法：docker top zookeeper
zookeeper：表示当前zookeeper实例的名称，通过--name指定，也可以使用容器ID来替代

```bash
sudo docker top zookeeper
UID                 PID                 PPID                C                   STIME               TTY                 TIME                CMD
1000                6737                6715                0                   13:49               ?                   00:00:01            /usr/lib/jvm/java-1.8-openjdk/jre/bin/java -Dzookeeper.log.dir=. -Dzookeeper.root.logger=INFO,CONSOLE -cp /zookeeper-3.4.13/bin/../build/classes:/zookeeper-3.4.13/bin/../build/lib/*.jar:/zookeeper-3.4.13/bin/../lib/slf4j-log4j12-1.7.25.jar:/zookeeper-3.4.13/bin/../lib/slf4j-api-1.7.25.jar:/zookeeper-3.4.13/bin/../lib/netty-3.10.6.Final.jar:/zookeeper-3.4.13/bin/../lib/log4j-1.2.17.jar:/zookeeper-3.4.13/bin/../lib/jline-0.9.94.jar:/zookeeper-3.4.13/bin/../lib/audience-annotations-0.5.0.jar:/zookeeper-3.4.13/bin/../zookeeper-3.4.13.jar:/zookeeper-3.4.13/bin/../src/java/lib/*.jar:/conf: -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.local.only=false org.apache.zookeeper.server.quorum.QuorumPeerMain /conf/zoo.cfg
```




也可以通过ps -ef | grep zookeeper来查看进程信息，示例如下：

```bash
ps -ef | grep zookeeper 
1000      6737  6715  0 13:49 ?        00:00:01 /usr/lib/jvm/java-1.8-openjdk/jre/bin/java -Dzookeeper.log.dir=. -Dzookeeper.root.logger=INFO,CONSOLE -cp /zookeeper-3.4.13/bin/../build/classes:/zookeeper-3.4.13/bin/../build/lib/*.jar:/zookeeper-3.4.13/bin/../lib/slf4j-log4j12-1.7.25.jar:/zookeeper-3.4.13/bin/../lib/slf4j-api-1.7.25.jar:/zookeeper-3.4.13/bin/../lib/netty-3.10.6.Final.jar:/zookeeper-3.4.13/bin/../lib/log4j-1.2.17.jar:/zookeeper-3.4.13/bin/../lib/jline-0.9.94.jar:/zookeeper-3.4.13/bin/../lib/audience-annotations-0.5.0.jar:/zookeeper-3.4.13/bin/../zookeeper-3.4.13.jar:/zookeeper-3.4.13/bin/../src/java/lib/*.jar:/conf: -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.local.only=false org.apache.zookeeper.server.quorum.QuorumPeerMain /conf/zoo.cfg
root      7301  6175  0 13:58 pts/0    00:00:00 grep --color=auto zookeeper

```



#### 停止zookeeper实例进程

语法：docker stop zookeeper

```bash
sudo docker stop zookeeper 
```



#### 启动、重启zookeeper实例进程

启动语法：docker start zookeeper
重启语法：docker restart zookeeper

```bash
sudo  docker start zookeeper
sudo docker restart zookeeper 
```



#### 查看zookeeper进程日志

语法：docker logs -f zookeeper

```bash
sudo docker logs -f zookeeper 
```



#### 使用zk 命令行客户端连接zk

语法：docker run -it --rm --link zookeeper:zookeeper zookeeper zkCli.sh -server zookeeper
说明：-server zookeeper是启动zkCli.sh的参数

```bash
sudo docker run -it --rm --link zookeeper:zookeeper zookeeper zkCli.sh -server zookeeper
```



#### 杀死zookeeper实例进程

语法：docker kill -s KILL zookeeper

```bash
sudo docker kill -s KILL zookeeper 
```



#### 移除zookeeper实例

语法：docker rm -f -v zookeeper

```bash
sudo docker rm -f -v zookeeper 
```



### 2、集群方式安装







## 三、基于Docker安装RabbitMQ

#### 1、查询镜像文件

```bash
 docker search rabbitmq
```



#### 2、拉取的镜像文件

```bash
docker pull rabbitmq:3-management
```

#### 3、查找下载的镜像

```bash
docker images
```

#### 4、下载后执行启动指令

```bash
docker run -it -d --hostname my-rabbit \
--name rabbit \
--restart=always \
-e RABBITMQ_DEFAULT_USER=admin \
-e RABBITMQ_DEFAULT_PASS=admin -p 15672:15672 -p 5672:5672 rabbitmq:3-management
 
-- RABBITMQ_DEFAULT_USER：账号
-- RABBITMQ_DEFAULT_PASS：密码
-- 如果RABBITMQ_DEFAULT_USER和RABBITMQ_DEFAULT_PASS没填写，默认用户guest 密码guest
-- 15672：控制台端口
-- 5672： AMQP端口
```

#### 5、安装完后查看安装状态

```bash
 docker ps -a
```

#### 6、在网页输入自己的IP和15672映射的端口号



## 四、基于Docker安装Redis

#### 1、拉取redis镜像

```bash
sudo docker pull redis:6.2.5
```



#### 2、使用redis镜像创建容器

```bash
sudo docker run --hostname my-redis --name redis -p 6379:6379 \
-v /usr/local/docker/redis/conf/redis.conf:/etc/redis/redis.conf \
-v /usr/local/docker/redis/data:/data \
-d redis:6.2.5 redis-server /etc/redis/redis.conf \
--restart=always \
--appendonly yes
```



#### 3、新建redis.conf文件

​	在主机/usr/local/docker/redis/conf/目录上新建redis.conf文件

```bash
vim /usr/local/docker/redis/conf/redis.conf
```



#### 4、配置redis持久化

```bash
echo "appendonly yes"  >> /usr/local/docker/redis/conf/redis.conf

# 重启生效
docker restart <CONTAINER ID>
```



#### 5、测试 redis-cli连接上来

```bash
docker exec -it <CONTAINER ID> redis-cli
```



#### 6、测试持久化文件生成

```bash
docker exec -it <CONTAINER ID> bash
root@my-redis:/data# pwd
/data
root@my-redis:/data# ls
appendonly.aof
```



## 五、基于Docker安装ES



#### 1、下载镜像文件 

Docker安装Elasticsearch、Kibana 

```bash
# 存储和检索数据
docker pull elasticsearch:7.4.2

# 可视化检索数据
docker pull kibana:7.4.2
```

#### 2. 配置挂载数据文件夹 

```bash
# 创建配置文件目录
mkdir -p /mydata/elasticsearch/config

# 创建数据目录
mkdir -p /mydata/elasticsearch/data

# 将/mydata/elasticsearch/文件夹中文件都可读可写
chmod -R 777 /mydata/elasticsearch/

# 配置任意机器可以访问 elasticsearch
echo "http.host: 0.0.0.0" >/mydata/elasticsearch/config/elasticsearch.yml
```

#### 3. 启动Elasticsearch 

命令后面的 \是换行符，注意前面有空格

```bash
docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
-e  "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms64m -Xmx512m" \
-v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /mydata/elasticsearch/data:/usr/share/elasticsearch/data \
-v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.4.2 
```

●-p 9200:9200 -p 9300:9300：向外暴露两个端口，9200用于HTTP REST API请求，9300 ES 在分布式集群状态下 ES 之间的通信端口；

●-e  "discovery.type=single-node"：es 以单节点运行

●-e ES_JAVA_OPTS="-Xms64m -Xmx512m"：设置启动占用内存，不设置可能会占用当前系统所有内存

●-v：挂载容器中的配置文件、数据文件、插件数据到本机的文件夹；

●-d elasticsearch:7.6.2：指定要启动的镜像

访问 IP:9200 看到返回的 json 数据说明启动成功。



#### 4. Elasticsearch自启动 

```bash
# 当前 Docker 开机自启，所以 ES 现在也是开机自启
docker update elasticsearch --restart=always
```

#### 5. 启动可视化Kibana 

```bash
docker run --name kibana \
-e ELASTICSEARCH_HOSTS=http://192.168.163.131:9200 \
-p 5601:5601 \
-d kibana:7.4.2
```

`-e ELASTICSEARCH_HOSTS=http://192.168.163.131:9200`: 这里要设置成自己的虚拟机IP地址



#### 6.  Kibana 自启动 

```bash
# 当前 Docker 开机自启，所以 kibana 现在也是开机自启
docker update kibana --restart=always
```



## 六、基于Docker安装nacos



#### 1、下载镜像文件 

```bash
sudo docker search nacos

sudo docker pull nacos/nacos-server:2.0.2
```

#### 2. 配置挂载数据文件夹 

```bash
# 创建配置文件目录
mkdir -p /usr/local/docker/nacos/logs
mkdir -p /usr/local/docker/nacos/init.d
mkdir -p /usr/local/docker/nacos/conf
```



#### 3. 配置内容 

文件：`vim /usr/local/docker/nacos/conf/application.properties`

```properties
server.contextPath=/nacos
server.servlet.contextPath=/nacos
server.port=8848

spring.datasource.platform=mysql

db.num=1
db.url.0=jdbc:mysql://47.106.210.155:3306/nacos_config?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
db.user=root
db.password=******


nacos.cmdb.dumpTaskInterval=3600
nacos.cmdb.eventTaskInterval=10
nacos.cmdb.labelTaskInterval=300
nacos.cmdb.loadDataAtStart=false

management.metrics.export.elastic.enabled=false

management.metrics.export.influx.enabled=false


server.tomcat.accesslog.enabled=true
server.tomcat.accesslog.pattern=%h %l %u %t "%r" %s %b %D %{User-Agent}i


nacos.security.ignore.urls=/,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/v1/auth/login,/v1/console/health/**,/v1/cs/**,/v1/ns/**,/v1/cmdb/**,/actuator/**,/v1/console/server/**
nacos.naming.distro.taskDispatchThreadCount=1
nacos.naming.distro.taskDispatchPeriod=200
nacos.naming.distro.batchSyncKeyCount=1000
nacos.naming.distro.initDataRatio=0.9
nacos.naming.distro.syncRetryDelay=5000
nacos.naming.data.warmup=true
nacos.naming.expireInstance=true

```



#### 4. 启动nacos

命令后面的 \是换行符，注意前面有空格

```bash
sudo docker run \
--name nacos -d \
-p 8848:8848 \
--privileged=true \
--restart=always \
-e JVM_XMS=256m \
-e JVM_XMX=256m \
-e MODE=standalone \
-e PREFER_HOST_MODE=hostname \
-v /usr/local/docker/nacos/logs:/home/nacos/logs \
-v /usr/local/docker/nacos/init.d:/home/nacos/init.d \
-v /usr/local/docker/nacos/conf/application.properties:/home/nacos/conf/application.properties \
nacos/nacos-server:2.0.2

sudo docker exec -it <CONTAINER ID> bash
```

访问 IP:8848  nacos/ncaos



## 七、基于Docker安装Nginx



#### 1、下载镜像文件 

```bash
sudo docker search nginx

sudo docker pull nginx:1.20
```

#### 2. 配置挂载数据文件夹 

```bash
# 创建配置文件目录
mkdir -p /usr/local/docker/nginx/conf/conf.d
mkdir -p /usr/local/docker/nginx/html
mkdir -p /usr/local/docker/nginx/logs
```



#### 3. 配置内容 

文件：`vim /usr/local/docker/nginx/conf/nginx.conf`

注意：一定先创建文件，否则挂载是会把nginx.conf当作文件目录和容器内的配置文件关联

```txt

user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;

    include /etc/nginx/conf.d/*.conf;
}
```



#### 4. 启动nginx

命令后面的 \是换行符，注意前面有空格

```bash
sudo docker run \
-p 80:80 \
--name my-nginx \
--restart=always \
-v /usr/local/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf:ro \
-d nginx:1.20

sudo docker exec -it <CONTAINER ID> bash

########################################
sudo docker run \
-p 80:80 \
--name my-nginx \
--restart=always \
-v /usr/local/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf:ro \
# -v /usr/local/docker/nginx/conf/conf.d:/etc/nginx/conf.d \
# -v /usr/local/docker/nginx/html:/usr/share/nginx/html \
# -v /usr/local/docker/nginx/logs:/var/log/nginx \
-d nginx:1.20

# Q:如果增加挂载目录运行容器，启动后无法访问
curl localhost:80
curl: (56) Recv failure: Connection reset by peer


```

访问 IP:80 



#### 5.修改nginx.conf配置

Q:修改nginx.conf配置后要重启容器

```bash
sudo docker restart <CONTAINER ID>
```



## 八、基于Docker安装seata-server



#### 1、下载镜像文件 

```bash
sudo docker search seata

sudo docker pull docker pull seataio/seata-server:1.3.0
```

#### 2. 配置挂载配置文件夹 

```bash
# 创建配置文件目录
mkdir -p /usr/local/docker/seata/config
```



#### 3. 配置内容 





#### 4. 启动seata-server

命令后面的 \是换行符，注意前面有空格

```bash
sudo docker run --name seata-server \
-p 8091:8091 \
-e SEATA_CONFIG_NAME=file:/root/seata-config/registry \
-v /usr/local/docker/seata/config:/root/seata-config  \
seataio/seata-server

```



#### 5.指定 file.conf配置

如果需要同时指定 `file.conf` 配置文件，则需要在 `registry.conf` 文件中将 `config` 配置改为以下内容，`name` 的值为容器中对应的路径

```conf
config {
  type = "file"

  file {
    name = "file:/root/seata-config/file.conf"
  }
}
```



