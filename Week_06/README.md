学习笔记

### 基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交DDL的SQL文件到Github（后面2周的作业依然要是用到这个表结构）

```sql
--User
CREATE TABLE `j_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `mobile_phone` char(14) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `created_time` timestamp NOT NULL ,
  `updated_time` timestamp NOT NULL ,
  `status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '0 is normal type, -1 can not use',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本表';

--goods
CREATE TABLE `j_goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `image_path` varchar(255) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `created_time` timestamp NOT NULL ,
  `updated_time` timestamp NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性';

--presonal receive address
CREATE TABLE `j_presonal_addr` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `provinces` varchar(10) NOT NULL,
  `city` varchar(10) NOT NULL,
  `area` varchar(10) NOT NULL,
  `detail` varchar(255) NOT NULL,
  `created_time` timestamp NOT NULL ,
  `updated_time` timestamp NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='个人收货地址';

--goods Stock Keeping Unit
CREATE TABLE `j_goods_sku` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NOT NULL,
  `stock` bigint(20) NOT NULL,
  `created_time` timestamp NOT NULL ,
  `updated_time` timestamp NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品库存';

--order table
CREATE TABLE `j_orders` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `order_status` int(1) NOT NULL COMMENT '0未付款,-1关闭,1已付款,2已发货,3已签收,4已退款',
  `created_time` timestamp NOT NULL ,
  `updated_time` timestamp NOT NULL ,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单';

--order detail message
CREATE TABLE `j_order_details` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `goods_id` bigint(20) NOT NULL,
  `sku_id` bigint(20) NOT NULL,
  `amout` int(11) NOT NULL,
  `created_time` timestamp NOT NULL ,
  `updated_time` timestamp NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详细';
```

