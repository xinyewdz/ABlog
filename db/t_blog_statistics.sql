create table `t_blog_statistics`(
  `blog_id` bigint(20) PRIMARY KEY NOT NULL,
  `view_count` int NOT NULL,
  `comment_count` int NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;