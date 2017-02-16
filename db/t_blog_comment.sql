create table `t_blog_comment`(
  `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `blog_id` bigint(20) NOT NULL,
  `nick_name` varchar(50) NOT NULL,
  `email` varchar(50) null,
  `content` varchar(500) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
create index `idx_blog` on `t_blog_comment`(`blog_id`);