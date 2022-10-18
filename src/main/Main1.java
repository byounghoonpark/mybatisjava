package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mapper.StudentMapper;

public class Main1 {
	private static SqlSessionFactory sqlMap;
	static {	//static 초기화 블럭 
		//바이트형 입력 스트림. 
		InputStream input = null;
		try {
			input = Resources.getResourceAsStream
					            ("mapper/mybatis-config.xml");	
		} catch(IOException e) {
			e.printStackTrace();
		}
		//mybatis 설정 객체 
		sqlMap = new SqlSessionFactoryBuilder().build(input);
	}
	private static Class<StudentMapper> cls = StudentMapper.class;
	public static void main(String[] args) {
		//session :sqlMap 저장된 db로 접속
		SqlSession session = sqlMap.openSession();
		System.out.println("모든 학생 정보 조회하기");
		List<Student> list = session.getMapper(cls).select();
		for(Student s : list) System.out.println(s);
		System.out.println("1학년 학생 정보 조회하기");
		list = session.getMapper(cls).select_grade(1);
		for(Student s : list) System.out.println(s);
		System.out.println("2학년 학생 정보 조회하기");
		list = session.getMapper(cls).select_grade(2);
		for(Student s : list) System.out.println(s);
		System.out.println("학번으로 학생 정보 조회하기");
		list = session.getMapper(cls).select_studno("9711");
		for(Student s : list) System.out.println(s);
		System.out.println("이름에 김자가 들어가는 학생 정보 조회하기");
		list = session.getMapper(cls).select_like("김");
		for(Student s : list) System.out.println(s);
		System.out.println("이름에 김자가 들어가는 학생 정보 조회하기2");
		list = session.getMapper(cls).select_like2("%김%");
		for(Student s : list) System.out.println(s);


	}
}
