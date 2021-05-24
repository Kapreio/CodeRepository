package com.java1234.model;

/**
 * ��ҳ��ʾ�ķ�װ
 * @author Weiguo Liu
 *
 */
public class PageBean {

	private int page; // �ڼ�ҳ
	private int rows; // ÿҳ�ļ�¼��
	private int startNum; // ��ʼҳ
	
	public PageBean(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public int getStartNum() {
		return (page-1)*rows;
	}
	
}
