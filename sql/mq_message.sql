CREATE TABLE mq_message(
  `message_id` char(32) NOT NULL,
  `content` text  COMMENT 'json',
  `to_exchange` varchar(255) DEFAULT NULL,
  `routing_key` varchar(255) DEFAULT NULL,
  `class_type` varchar(255) DEFAULT NULL COMMENT '消息体class类型',
  `message_status` int(1) DEFAULT '0' COMMENT '0-新建 1-已发送 2-错误抵达 3-已抵达',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`message_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='mq消息日志记录';