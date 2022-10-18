package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import test1018.Professor;

public interface ProfessorMapper2 {

	@Select({ "<script>",
		"select * from professor",
		"<trim prefix='where' prefixOverrides='AND||OR'>",
		"<if test='deptno != null'>and deptno=#{deptno}</if>",
		"<if test='profno != null'>and profno=#{profno}</if>",
		"<if test='position != null'>and position=#{position}</if>",
		"<if test='datas !=null '>and deptno in "
				+ "<foreach collection='datas' item='d' separator=',' " 
				+ "open='(' close=')'>#{d}</foreach></if>",
		"</trim>",
        "</script>" })	
	List<Professor> select(Map<String, Object> map);

}
