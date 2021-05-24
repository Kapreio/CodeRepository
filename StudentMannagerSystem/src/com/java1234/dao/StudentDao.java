package com.java1234.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java1234.model.PageBean;
import com.java1234.model.Student;
import com.java1234.util.DateToStringUtil;
import com.java1234.util.StringUtil;

/**
 * ѧ�����ݿ�Ĳ�����
 * @author Weiguo Liu
 *
 */
public class StudentDao {

	//��ѯ����ѧ����,����search����
	public ResultSet studentList(Connection conn,PageBean pageBean,Student student,String bbirthday,String ebirthday) throws Exception{
		//�����select * from t_student s,t_grade g where s.gradeId=g.id�ǹ�����ѯ�����
		StringBuffer sb = new StringBuffer("select * from t_student s,t_grade g where s.gradeId=g.id");
		if(StringUtil.isNotEmpty(student.getStuNo())){
			//���� ��sql�����ģ����ѯ����˼
			sb.append(" and s.stuNo like '%" + student.getStuNo() + "%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			//���� ��sql�����ģ����ѯ����˼
			sb.append(" and s.stuName like '%" + student.getStuName() + "%'");
		}
		if(StringUtil.isNotEmpty(student.getSex())){
			//���� ��sql����Ǿ����ѯ������ģ����ѯ
			sb.append(" and s.sex='" + student.getSex() + "'");
		}
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId='" + student.getGradeId() + "'");
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			//���� ��sql����ǽ�����ת���ɾ������������������ڷ�Χ���жϣ�ȡ����
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('" + bbirthday + "')");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			//���� ��sql���Ҳ�ǽ�����ת���ɾ��������������з�Χ���жϣ�ȡ����
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('" + ebirthday + "')");
		}
		if(pageBean!=null){
			sb.append(" limit " + pageBean.getStartNum() + "," + pageBean.getRows());
		}
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	//��ѯ�������еļ�¼����,Ҳ�ǹ�����ѯ
	public int studentCount(Connection conn,Student student,String bbirthday,String ebirthday) throws Exception {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_student s,t_grade g where s.gradeId=g.id");
		
		if(StringUtil.isNotEmpty(student.getStuNo())){
			//���� ��sql�����ģ����ѯ����˼
			sb.append(" and s.stuNo like '%" + student.getStuNo() + "%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			//���� ��sql�����ģ����ѯ����˼
			sb.append(" and s.stuName like '%" + student.getStuName() + "%'");
		}
		if(StringUtil.isNotEmpty(student.getSex())){
			//���� ��sql����Ǿ����ѯ������ģ����ѯ
			sb.append(" and s.sex='" + student.getSex() + "'");
		}
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId='" + student.getGradeId() + "'");
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			//���� ��sql����ǽ�����ת���ɾ������������������ڷ�Χ���жϣ�ȡ����
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('" + bbirthday + "')");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			//���� ��sql���Ҳ�ǽ�����ת���ɾ��������������з�Χ���жϣ�ȡ����
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('" + ebirthday + "')");
		}
		
		//��and�滻��where
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){//����м�¼���ڵĻ�
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	//���ѧ����
	public int studentAdd(Connection conn,Student t) throws Exception{
		String sql = "insert into t_student values(null,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getStuName());
		pstmt.setString(2, t.getStuNo());
		pstmt.setString(3, t.getSex());
		//�����ڸ�ʽת��String��ʽ�Ž�ȥ
		pstmt.setString(4, DateToStringUtil.formatDate(t.getBirthday(),"yyyy-MM-dd"));
		pstmt.setInt(5, t.getGradeId());
		pstmt.setString(6, t.getEmail());
		pstmt.setString(7, t.getStuDesc());
		return pstmt.executeUpdate();
	}
	
	//ɾ��ָ��id��ѧ��,������ɾ��
	public int studentDelete(Connection conn,String delIds)throws Exception{
		String sql = "delete from t_student where stuId in (" + delIds + ")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	//�༭ָ��id��ѧ��
	public int studentModify(Connection conn,Student t) throws Exception{
		String sql = "update t_student set stuName=?,stuNo=?,sex=?,birthday=?,gradeId=?,email=?,stuDesc=? where stuId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getStuName());
		pstmt.setString(2, t.getStuNo());
		pstmt.setString(3, t.getSex());
		pstmt.setString(4, DateToStringUtil.formatDate(t.getBirthday(), "yyyy��MM��dd��"));
		pstmt.setInt(5, t.getGradeId());
		pstmt.setString(6, t.getEmail());
		pstmt.setString(7, t.getStuDesc());
		pstmt.setInt(8, t.getStuId());
		return pstmt.executeUpdate();
	}
	
	public boolean getStudentByGradeId(Connection conn,String gradeId) throws Exception{
		String sql = "select * from t_student where gradeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, gradeId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
}
