package cn.aaron.ablog.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.util.HttpRequestUtil;
import org.util.NoneUtil;
import org.util.Pagination;
import org.util.RequestParameterException;

import cn.aaron.ablog.action.vo.BlogVO;
import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.obj.BlogObj;
import cn.aaron.ablog.obj.BlogStatisticsObj;
import cn.aaron.ablog.service.BlogService;

/**
*TODO
*@author Aaron
*@date 2017年2月3日
*/
@Controller
public class IndexAction extends BasePage {

	private static Logger log = Logger.getLogger(IndexAction.class);
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/")
	public String index(HttpServletRequest request) throws RequestParameterException{
		int _rowStart = HttpRequestUtil.getInt(request, "_rowStart",0);
		Pagination pagination = new Pagination();
		pagination.setSortName("updated_time").setSortOrder(Pagination.SORT_ORDER_DESC);
		pagination.setRowStart(_rowStart).setPageSize(10);
		List<BlogObj> list = blogService.listBlog(pagination,true);
		List<BlogVO> blogList = null;
		if(list!=null){
			int len = list.size();
			blogList = new ArrayList<BlogVO>(len);
			Long[] blogIds = new Long[len];
			for(int i=0;i<len;i++){
				BlogObj obj = list.get(i);
				blogIds[i] = obj.getId();
			}
			Map<Long, BlogStatisticsObj> statisticsMap = new HashMap<Long, BlogStatisticsObj>(len);
			List<BlogStatisticsObj> statisticsList = blogService.listStatistics(blogIds);
			if(!NoneUtil.isEmpty(statisticsList)){
				for(int i=0,statisticsLen=statisticsList.size();i<statisticsLen;i++){
					BlogStatisticsObj statisticsObj = statisticsList.get(i);
					statisticsMap.put(statisticsObj.getBlogId(), statisticsObj);
				}
			}
			for(int i=0;i<len;i++){
				BlogObj obj = list.get(i);
				BlogVO vo = new BlogVO();
				vo.setId(obj.getId());
				vo.setTitle(obj.getTitle());
				vo.setTags(obj.getTags());
				vo.setContent(obj.getContent());
				vo.setCreatedTime(obj.getCreatedTime());
				vo.setUpdatedTime(obj.getUpdatedTime());
				BlogStatisticsObj statisticsObj = statisticsMap.get(obj.getId());
				if(statisticsObj!=null){
					vo.setViewCount(statisticsObj.getViewCount());
				}
				blogList.add(vo);
			}
		}
		long count = blogService.findCount();
		pagination.setTotalRow(count);
		request.setAttribute("pagination", pagination);
		request.setAttribute("blogList", blogList);
		return "/index";
	}
	
}
