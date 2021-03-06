学习笔记

1. 100W的数据，尝试了很多方法，选择的一种PreparedStatement 的批量提交，最终11秒左右完成的，在考虑到还有多线程的情况下，是否能更快，使用线程池的时候，却依旧只有一个线程在跑，还要研究一下多线程的方案，怎么分配100W的数据，才不会重复。<br/>

   [100W]: https://github.com/EncinLi/JAVA-01/tree/main/Week_07/sqlbatchinsertdemo/src/main/java/encin/batchinsertdemo/SqlBatchInsertDemo.java

   

2. Springboot实现多数据源切换，读写分离，网上很多资料但是都是断断续续，动手实现下来，springboot的方案需要配置很多，整个流程很繁琐，不过也得出读写分离spring 实现对于老一套的框架更为友好，<br/>

   [1.0]: https://github.com/EncinLi/JAVA-01/tree/main/Week_07/datasource/switchdemo/src/main/java/encin/datasource/switchdemo/SwitchdemoApplication.java

   

3. ShardingSphere的实现简化了很多，只需要配置好config文件基本上就能使用，只要配置好分配算法就能找到对应的db和table，但是还有很多需要研究的参数和方案，希望可以学有所成<br/>

   [2.0]: https://github.com/EncinLi/JAVA-01/tree/main/Week_07/ss/shardingspheredemo/src/main/java/encin/ss/shardingspheredemo/ShardingspheredemoApplication.java

   