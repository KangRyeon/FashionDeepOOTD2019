package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import dto.FashionSet;

public class FashionSetDao {
	private JdbcTemplate jdbcTemplate;

	public FashionSetDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// id에 따른 fashion set 전체 리턴 : fashion set 리스트 화면 보여주는것.
	public List<FashionSet> selectById(String id) {
		// select * from fashionSet where id="test"
		List<FashionSet> results = jdbcTemplate.query("select * from fashionSet where id=\""+id+"\"", new RowMapper<FashionSet>() {
			@Override
			public FashionSet mapRow(ResultSet rs, int rowNum) throws SQLException {
				FashionSet fs = new FashionSet(rs.getString("id")
														, rs.getString("set_name")
														, rs.getString("outerC")
														, rs.getString("upperC")
														, rs.getString("lowerC")
														, rs.getString("cap")
														, rs.getString("bag")
														, rs.getString("shoes")
														, rs.getString("accessory1")
														, rs.getString("accessory2")
														, rs.getString("accessory3"));
				return fs;
			}
		});
		return results;
	}
		
	// id와 fashion set_name에 따라서 가져옴 : fashion set 하나의 화면 보여주기 위함
	public List<FashionSet> selectByIdNSet_name(String id, String set_name) {
		// select * from fashionSet where id="test" and set_name="캐쥬얼1"
		List<FashionSet> results = jdbcTemplate.query("select * from fashionSet where id=\""+id+"\" and set_name=\""+set_name+"\"", new RowMapper<FashionSet>() {
			@Override
			public FashionSet mapRow(ResultSet rs, int rowNum) throws SQLException {
				FashionSet fs = new FashionSet(rs.getString("id")
														, rs.getString("set_name")
														, rs.getString("outerC")
														, rs.getString("upperC")
														, rs.getString("lowerC")
														, rs.getString("cap")
														, rs.getString("bag")
														, rs.getString("shoes")
														, rs.getString("accessory1")
														, rs.getString("accessory2")
														, rs.getString("accessory3"));
				return fs;
			}
		});
		return results;
	}
	
	// fashion set 추가
	public int insertToFashionSet(String id, String set_name, String outer, String upper, String lower, String cap, String bag, String shoes, String accessory1, String accessory2, String accessory3) {
		//insert into fashion_set values('test', '캐주얼1', 'files/outer/coat/coat_12345.jpg', 'upper/short_T/short_T.12345.jpg', 'lower/long_pants/long_pants_12345.jpg', 'files/cap/cap/cap_12345.jpg', 'bag/bag/bag_123456.jpg', 'shoes/shoes/shoes_12345.jpg', 'accessory/accessory/accessory_12345.jpg');   
		int result = 0;
		try {
			String query = "insert into fashionSet values(\""+id+"\", \""+set_name+"\", \""+outer+"\", \""+upper+"\", \""+lower+"\", \""
															+cap+"\", \""+bag+"\", \""+shoes+"\", \""+accessory1+"\", \""+accessory2+"\", \""+accessory3+"\" )";
			System.out.println(query);
			result = jdbcTemplate.update(query);
		} catch(Exception e) {
			System.out.println("insertToFashionSet 오류");
		}
		return result;
	}
}
