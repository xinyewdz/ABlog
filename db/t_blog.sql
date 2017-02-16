create table `t_blog`(
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `tags` varchar(100) NOT NULL,
  `created_time` timestamp NOT NULL,
  `updated_time` timestamp NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;