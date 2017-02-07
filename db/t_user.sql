create table `t_user`(
 `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
 `login` varchar(20) NOT NULL,
 `email` varchar(50) NOT NULL,
 `password` varchar(100) NOT NULL,
 `type` smallint NOT NULL DEFAULT 1 COMMENT '1=NORMAL 2=ADMIN',
 `status` smallint NOT NULL DEFAULT 1 COMMENT '1=NORMAL 2=LOCK',
 `created_time` timestamp NOT NULL,
 `updated_time` timestamp NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;