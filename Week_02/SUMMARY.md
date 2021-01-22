* 通过题一的各种GC日志分析，可以得出，堆内存并不是越大性能越好
  
  [GC 日志]: https://github.com/EncinLi/JAVA-01/tree/main/Week_02/day1/01
  
  1. 合理的内存分配很重要，因为堆内存过大，使用率过低，意味着浪费资源。
  2. 过低的内存，容易导致频繁YGC和FGC，最终可能造成OOM的现象
  3. 不同的GC性能各不相同，而且差异较大，如serialGC，不管是做YGC还是FGC，都会STW，
  可以根据日志得知，小内存的时候，serialGC基本上有一半时间是在GC的，对于性能影响是
  非常之大
  4. 合理的内存，使用合理的GC策略，可以使性能更加高效，资源合理利用，不会造成浪费
  综上所述，JVM性能调优，如何使用GC，分配多少堆内存，这些都是需要通过测试和分析后才能得出最佳的GC使用
  方案，所谓脱离场景谈性能就是耍流氓<br><br> 
  
* 通过测压 gateway-server，去测试不同对大小的性能<br>
  
  1. -Xms1g  -Xmx1g的测压情况
``` 
PS C:\Users\Encin.Li> sb -u http://localhost:8088/api/hello -c 10 -N 60
Starting at 2021/1/21 22:49:17
[Press C to stop the test]
346815  (RPS: 5368.1)
---------------Finished!----------------
Finished at 2021/1/21 22:50:22 (took 00:01:04.7321123)
Status 200:    346816

RPS: 5671.3 (requests/second)
Max: 58ms
Min: 0ms
Avg: 0ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 0ms
  98%   below 1ms
  99%   below 2ms
99.9%   below 3ms
```

```
PS C:\Users\Encin.Li> sb -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2021/1/21 22:45:58
[Press C to stop the test]
286109  (RPS: 4416.3)
---------------Finished!----------------
Finished at 2021/1/21 22:47:03 (took 00:01:04.9899568)
Status 200:    286109

RPS: 4675.1 (requests/second)
Max: 86ms
Min: 0ms
Avg: 0.2ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 1ms
  98%   below 3ms
  99%   below 4ms
99.9%   below 11ms
```

```
PS C:\Users\Encin.Li> sb -u http://localhost:8088/api/hello -c 30 -N 60
Starting at 2021/1/21 22:53:17
[Press C to stop the test]
272232  (RPS: 4179.2)
---------------Finished!----------------
Finished at 2021/1/21 22:54:22 (took 00:01:05.3218677)
Status 200:    272249

RPS: 4449.3 (requests/second)
Max: 103ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 4ms
  98%   below 7ms
  99%   below 9ms
99.9%   below 18ms
```

2. -Xms512m -Xmx512m的测压情况

```
PS C:\Users\Encin.Li> sb -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2021/1/21 23:39:56
[Press C to stop the test]
281977  (RPS: 4353.5)
---------------Finished!----------------
Finished at 2021/1/21 23:41:01 (took 00:01:04.9109532)
Status 200:    281978

RPS: 4611.8 (requests/second)
Max: 254ms
Min: 0ms
Avg: 0.2ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 1ms
  98%   below 3ms
  99%   below 4ms
99.9%   below 10ms
```

3. -Xms2g -Xmx2g的测压情况

```
PS C:\Users\Encin.Li> sb -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2021/1/21 23:43:24
[Press C to stop the test]
290176  (RPS: 4486.9)
---------------Finished!----------------
Finished at 2021/1/21 23:44:29 (took 00:01:04.8937428)
Status 200:    290180

RPS: 4739.1 (requests/second)
Max: 265ms
Min: 0ms
Avg: 0.2ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 1ms
  98%   below 3ms
  99%   below 4ms
99.9%   below 11ms
```

综上，

1. 在同等堆大小的情况，测压线程越少，RPS越高，最大的请求耗时也会较低，可以得知平均耗时，在测压线程越高情况，低延迟情况出现越少，对于整体性能来说，并不是好似，所以当出现峰值情况的，需要找到问题的原因，并且考虑如何把压力分开，使服务能正常运行
2. 在不同堆大小的情况下，同等测压线程，增大堆内存并没有提升RPS，且最大请求耗时也会有所不同，这里结合GC，可以看到STW导致请求耗时增加，但是整体的RPS相差不大，所以不是堆内存越大性能越好
3. 使用不同GC进行测压，不同的GC会导致RPS发生较大的改变，并且最大耗时也相差较远，根据GC日志一同分析，可以知道FGC发生的时候，请求响应会增加，但是都是可以接收到范围，但是不同GC的请求响应相差较大，其中serial GC的性能可以明显知道要比其他GC的要差，所以选择合适的GC很重要

以上，合适的GC和合适的堆大小都是需要通过测试后获得，这样可以使服务最优化，脱离场景的性能是没有意义的，JVM的调优就需要根据不同场景进行测试后才能选择到最合理的，在日常发现性能出现波动，通过GC日志分析后，选择合适的在进行测压，这样的调优才是有意义的