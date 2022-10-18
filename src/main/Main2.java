package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mapper.StudentMapper2;

public class Main2 {
	private static SqlSessionFactory sqlMap;
	static { // static 초기화 블럭
		// 바이트형 입력 스트림.
		InputStream input = null;
		try {
			input = Resources.getResourceAsStream("mapper/mybatis-config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlMap = new SqlSessionFactoryBuilder().build(input);
	}
	private static Class<StudentMapper2> cls = StudentMapper2.class;
	private static Map<String, Object> map = new HashMap<>();

	public static void main(String[] args) {
		SqlSession session = sqlMap.openSession();

		System.out.println("1학년 학생 중 키가 175 이상 학생 정보");
		map.clear();
		map.put("grade", 1);
		map.put("height", 175);
//		List<Student> list = session.getMapper(cls).select(map);
		List<Student> list = session.getMapper(cls).select2(map);
		// 오류발생
//		List<Student> list = session.getMapper(cls).select3(map);

		for (Student s : list)
			System.out.println(s);

		System.out.println("키가 175 이상 학생 정보");
		map.clear();
		map.put("height", 175);
//		list = session.getMapper(cls).select(map);
//		list = session.getMapper(cls).select2(map);
		list = session.getMapper(cls).select3(map);
		for (Student s : list)
			System.out.println(s);

		System.out.println("2학년 학생 정보");
		map.put("grade", 2);
		list = session.getMapper(cls).select(map);
//		list = session.getMapper(cls).select2(map);
//		list = session.getMapper(cls).select3(map);
		for (Student s : list)
			System.out.println(s);

		System.out.println("모든 학생 정보");
//		list = session.getMapper(cls).select(null);
//		list = session.getMapper(cls).select2(null);
		list = session.getMapper(cls).select3(null);
		for (Student s : list)
			System.out.println(s);
		// ====================================
		System.out.println("101,201,301 학과에 속한 학생의 정보");
		// select * from student where deptno1 in(101,201,301)
		map.clear();
		List<Integer> depts = Arrays.asList(101, 201, 301);
		map.put("datas", depts);
		map.put("columns", "deptno1");
		list = session.getMapper(cls).select4(map);
		for (Student s : list)
			System.out.println(s);

		System.out.println("학과2가 101,201,301 학과에 속한 학생의 정보");
		map.clear();
		depts = Arrays.asList(101, 201, 301);
		map.put("datas", depts);
		map.put("columns", "deptno2");
		list = session.getMapper(cls).select4(map);
		for (Student s : list)
			System.out.println(s);

		System.out.println("학생의 이름이 이서진,구유미,일지매인 학생의 정보");
		map.clear();
		List<String> name = Arrays.asList("이서진", "구유미", "일지매");
		map.put("datas", name);
		map.put("columns", "name");
		list = session.getMapper(cls).select4(map);
		for (Student s : list)
			System.out.println(s);
	}
}
