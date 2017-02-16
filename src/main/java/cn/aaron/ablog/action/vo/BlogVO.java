package cn.aaron.ablog.action.vo;

import cn.aaron.ablog.obj.BlogObj;

/**
*TODO
*@author Aaron
*@date 2017年2月15日
*/
public class BlogVO extends BlogObj{

	private int viewCount;
	private int commentCount;

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
}
