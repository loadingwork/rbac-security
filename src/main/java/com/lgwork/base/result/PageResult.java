package com.lgwork.base.result;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lgwork.enums.ErrorCodeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 *  分页参数返回, 这个规定必然是成功的, 
 *  不允许失败时使用这个类
 * 
 * @author irays
 *
 * @param <T>
 */
@Getter
@Setter
public class PageResult<T> extends BaseResult<List<T>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6421925533698188566L;
	
	/**
	 * 页码
	 */
	public Integer pageNum;
	/**
	 * 分页大小
	 */
	public Integer pageSize;
	/**
	 * 总条数
	 */
	public Long totalElements;
	/**
	 * 总页数
	 */
	public Integer totalPages;
	
	/**
	 * 是否最后一条
	 */
	public boolean last;
	/**
	 * 是否第一条
	 */
	public boolean first;
	
	
	public  PageResult(Page<T>  page) {
		this((page.getNumber() + 1), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
		this.setData(page.getContent());
		
		// 设置返回状态
		this.setErrcode(ErrorCodeEnum.SUCCESS.getErrcode());
		this.setErrmsg(ErrorCodeEnum.SUCCESS.getErrmsg());
	}
	
	public PageResult(Integer pageNum, Integer pageSize, Long totalElements, List<T> data) {
		
		this(pageNum, pageSize, totalElements, 0, false, false);
		
		this.setData(data);
		
		int totalPages = totalElements == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) pageSize);
		
		this.setTotalPages(totalPages);
		
		if(pageNum >= totalPages) {
			this.setLast(true);
		}
		if(pageNum <= 1) {
			this.setFirst(true);
		}
		
		// 设置返回状态
		this.setErrcode(ErrorCodeEnum.SUCCESS.getErrcode());
		this.setErrmsg(ErrorCodeEnum.SUCCESS.getErrmsg());
	}
	
	private PageResult(Integer pageNum, Integer pageSize, Long totalElements, Integer totalPages, boolean last,
			boolean first) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
		this.first = first;
	}
	
	
	

}
