**主从复制：**

通过redis-server -h可以了解到，命令，配置文件都可以配置主从。

redis-server --port 6379

redis-server --port 6380 --slaveof localhost 6379

或者在先执行 redis-server --port 6380

然后进入redis-cli -p 6380界面中执行 slaveof  localhost 6379

解除关系, 在从库执行 slaveof no one

主库负责写，从库负责读并且是写不成功的，数据永远和主库一致，如果主库宕机后，把从库设置为主库，可能会导致数据丢失。

但是主库不会自动重启，这就会导致主库宕机后数据不可用



为了解决上述的问题，就引入了sentinel

**sentinel 高可用**

```xml
1.配置（sentinel.conf）：
    port 5000
    sentinel monitor mymaster 127.0.0.1 6379 2
    sentinel down-after-milliseconds mymaster 5000
    sentinel failover-timeout mymaster 60000
    sentinel parallel-syncs mymaster 1
 2.启动哨兵服务命令（windows）
    redis-server.exe sentinel.conf --sentinel /redis-sentinel sentinel.conf
```

如果 sentinel.conf 找不到，那就只有sentinel起来了，它不知道master节点

添加 sentinel monitor mymaster 127.0.0.1 6379 2到配置文件

启动redis-server --port 6379, 会发现 +sdown表示master挂了 -sdown 表示master起来了



启动slave redis-server --port 6380 --slaveof 127.0.0.1 6379

启动slave的sentinel redis-sentinel redis6380.conf 

slave中添加 sentinel monitor mymaster 127.0.0.1 6379 2到配置文件

```
sentinel monitor <master-group-name> <ip> <port> <quorum>
```