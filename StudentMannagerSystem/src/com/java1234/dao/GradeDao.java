package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java1234.model.Grade;
import com.java1234.model.PageBean;
import com.java1234.util.StringUtil;

/**
 * �༶�����Ĺ�����
 * @author Weiguo Liu
 *
 */
public class GradeDao {

	//��ѯ���а༶,���ｫ�����͹������е�search�����ϵ�һ����,�����׷�����һ��Ҫע��ո񣡣���
	public ResultSet gradeList(Connection conn,PageBean pageBean,Grade grade) throws Exception{
		StringBuffer sb = new StringBuffer("select * from t_grade");
		if(grade!=null && StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and gradeName like '%" + grade.getGradeName()+"%'");
		}
		if(pageBean!=null){
			sb.append(" limit " + pageBean.getStartNum() + "," + pageBean.getRows());
		}
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	
	//ͳ�����ݱ��е��ܼ�¼��
	public int gradeCount(Connection conn,Grade grade) throws Exception {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_grade");
		if(StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		//��and�滻��where
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){//����м�¼���ڵĻ�
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	//����µİ༶�����ݱ�
	public int gradeAdd(Connection conn,Grade grade) throws Exception{
		String sql = "insert into t_grade values(null,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		return pstmt.executeUpdate();
	}
	
	//ɾ��ָ��id�İ༶��Ϣ,����������ɾ��
	public int gradeDelete(Connection conn,String delIds) throws Exception{
		String sql = "delete from t_grade where id in("+delIds+")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	//�༭ָ��id�İ༶��Ϣ
	public int gradeModeify(Connection conn,Grade grade) throws Exception{
		String sql = "update t_grade set gradeName=?,gradeDesc=? where id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		pstmt.setInt(3, grade.getId());
		return pstmt.executeUpdate();
	}
	
}
