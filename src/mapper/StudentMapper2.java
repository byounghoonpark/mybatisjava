package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import main.Student;

/*
 * 같은 이름을 가진 메서드가 여러개 존재 불가 : Mybatis에서 실행시 오류 발생
 * 오버로딩 불가함. 
 * 메서드이름 : sql 구문을 구분할 수 있는 이름.
 */
public interface StudentMapper2 {

	// 동적 sql 문장
	@Select({ "<script>", "select * from student",
			"<choose>" + "<when test='grade != null and height != null'>"
					+ " where grade = #{grade} and height >= #{height}</when>"
					+ "<when test='grade != null'>where grade=#{grade}</when>"
					+ "<when test= 'height != null'> where height >= #{height}</when>" + "</choose>",
			"</script>" })
	List<Student> select(Map<String, Object> map);

	/*
	 * 1. <when test='grade !=null and height !=null'> : map 객체에 grade, height 키값이
	 * 둘다 존재 {"grade":1, "height":175}
	 * 
	 * select * from student where grade = 1 and height >= 175
	 * 
	 * 2. map : {"height":175} <when test= 'height !=null'>where height >=
	 * #{height}</when> select * from student where height >= 175
	 * 
	 * 3. map : {"grade":2} <when test= 'grade ! = null'> where grade =
	 * #{grade}</when>" select * from student where grade = 2
	 * 
	 * 4. map : null select * from student => 모든 데이터 조회
	 */
	@Select({ "<script>", "select * from student", "<trim prefix='where' prefixOverrides='AND||or'>",
			"<if test='grade !=null '>and grade = #{grade}</if>",
			"<if test='height !=null '>and height = #{height}</if>", "</trim>", "</script>" })
	List<Student> select2(Map<String, Object> map);
	/*
	 * map : grade:1, height:175
	 * 
	 * select * from student where grade =#{grade} and height = #{height}
	 * 
	 * map : height:175 * select * from student where height >= #{height}
	 * 
	 * map : grade:2 select * from student where grade = #{grade}
	 * 
	 * map : null select * from student
	 */

	// grade,height 중 한개만 입력될때 사용되는 동적 sql 구문
	@Select({ "<script>", "select * from student", "<if test='grade !=null '>where grade = #{grade}</if>",
			"<if test='height !=null '>where height = #{height}</if>", "</script>" })
	List<Student> select3(Map<String, Object> map);

	@Select({
			"<script>", "select * from student", "<if test='datas !=null '>where ${columns} in "
					+ "<foreach collection='datas' item='d' separator=',' " 
					+ "open='(' close=')'>#{d}</foreach></if>",
			"</script>" })
	List<Student> select4(Map<String, Object> map);
	/*
	 * datas :[101,201,301] columns : "deptno1"
	 * 
	 * select * from student where deptno1 in (101,201,301)
	 * 
	 * datas :["이서진", "구유미", "일지매"] columns : "name" select * from student where
	 * deptno1 in ('이서진', '구유미', '일지매')
	 */
}