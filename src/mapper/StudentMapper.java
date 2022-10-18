package mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import main.Student;
public interface StudentMapper {
	/*
	 * db의 컬럼명과 Student의 프로퍼티를 비교하여 같은 이름의 데이터를 저장
	 */
	@Select("select*from student")
	public List<Student> select();
	
	@Select("select*from student where grade = #{value}")
	public List<Student> select_grade(int grade);
	
	@Select("select*from student where studno = #{value}")
	public List<Student> select_studno(String studno);
	
	@Select("select*from student where name like '%${value}%'")
	public List<Student> select_like(String name);
	
	@Select("select*from student where name like #{value}") 
//												'%김%'
	public List<Student> select_like2(String name);
	/*
	 *  #{value} : 매개변수의 값 + 자료형인식 (보안에 좋음)
	 *              where name=#{value} => where name = '홍길동'
	 *              where name like %#{value}%
	 *                => where name like '%'김'%'
	 *              where name like '%#{value}%'
	 *                => where name like '%'김'%'
	 *  ${value} : 매개변수의 값 => 값만 치환 기능
	 *              where name=${value} => where name = 홍길동
	 *              where name like '%${value}%'
	 *                => where name like '%김%'
	 */
}
