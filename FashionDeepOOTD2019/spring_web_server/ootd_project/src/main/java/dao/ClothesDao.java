package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import dto.Clothes;
import dto.FashionSet;

public class ClothesDao {
	private JdbcTemplate jdbcTemplate;

	public ClothesDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// id와 dress_num에 따른 clothes 리턴 : 옷 하나 검색시 사용
	public Clothes selectByIdNDress_num(String id, String dress_num) {
		System.out.println("select * from clothes where id=\"" + id + "\" and dress_number=\"" + dress_num + "\"");
		List<Clothes> results = jdbcTemplate.query(
				"select * from clothes where id=\"" + id + "\" and dress_number=\"" + dress_num + "\"",
				new RowMapper<Clothes>() {
					@Override
					public Clothes mapRow(ResultSet rs, int rowNum) throws SQLException {
						Clothes fs = new Clothes(rs.getString("id"), rs.getString("dress_number"),
								rs.getString("category"), rs.getString("low_category"), rs.getString("color"),
								rs.getString("pattern"), rs.getString("lengthC"));
						return fs;
					}
				});
		return results.get(0);
	}

	// clothes 추가
	public int insertToClothes(String id, String dress_number, String category, String low_category, String color,
			String pattern, String lengthC) {
		// insert into clothes values("test", "20191103051213", "upper", "hood_T",
		// "blue", "no", "long");
		int result = 0;
		try {
			String query = "insert into clothes values(\"" + id + "\", \"" + dress_number + "\", \"" + category
							+ "\", \"" + low_category + "\", \"" + color + "\", \"" + pattern + "\", \"" + lengthC + "\" )";
			System.out.println(query);
			result = jdbcTemplate.update(query);
		} catch (Exception e) {
			System.out.println("insertToClothes 오류");
		}
		return result;
	}

	// clothes update
	public int updateClothes(String id, String dress_number, String category, String low_category, String color, String pattern, String lengthC) {
		// update clothes set category="upper", low_category="long_T", color="red", pattern="no", lengthC="long" where id="test" and dress_number="20190929181510";
		int result = 0;
		try {
			result = jdbcTemplate.update("update clothes set category=\"" + category + "\", low_category=\""
											+ low_category + "\", color=\"" + color + "\", pattern=\"" + pattern + "\", lengthC=\"" + lengthC
											+ "\" where id=\"" + id + "\" and dress_number=\"" + dress_number + "\"");
		} catch (Exception e) {
		}

		return result;
	}

	// id와 category, color에 따른 clothes 가져옴 : 옷추천시 색깔따라 보여줄 것.
	public List<Clothes> selectByIdNCategoryNColor(String id, String category, String color) {
		// select * from clothes where id="test" and category="upper" and color="black"
		System.out.println("\"select * from clothes where id=\"" + id + "\" and category=\"" + category + "\" and color=\"" + color + "\"");
		List<Clothes> results = jdbcTemplate.query("select * from clothes where id=\"" + id + "\" and category=\"" + category + "\" and color=\"" + color + "\"", new RowMapper<Clothes>() {
					@Override
					public Clothes mapRow(ResultSet rs, int rowNum) throws SQLException {
						Clothes fs = new Clothes(rs.getString("id"), rs.getString("dress_number"),
								rs.getString("category"), rs.getString("low_category"), rs.getString("color"),
								rs.getString("pattern"), rs.getString("lengthC"));
						return fs;
					}
				});
		return results;
	}

	// id와 category, pattern에 따른 clothes 가져옴 : 옷추천시 패턴따라 보여줄 것.
	public List<Clothes> selectByIdNCategoryNPattern(String id, String category, String pattern) {
		// select * from clothes where id="test" and category="upper" and color="black"
		System.out.println("\"select * from clothes where id=\"" + id + "\" and category=\"" + category + "\" and pattern=\"" + pattern + "\"");
		List<Clothes> results = jdbcTemplate.query("select * from clothes where id=\"" + id + "\" and category=\"" + category + "\" and pattern=\"" + pattern + "\"", new RowMapper<Clothes>() {
					@Override
					public Clothes mapRow(ResultSet rs, int rowNum) throws SQLException {
						Clothes fs = new Clothes(rs.getString("id"), rs.getString("dress_number"),
								rs.getString("category"), rs.getString("low_category"), rs.getString("color"),
								rs.getString("pattern"), rs.getString("lengthC"));
						return fs;
					}
				});
		return results;
	}
	
	// id와 category, pattern에 따른 clothes 가져옴 : 옷추천시 패턴따라 보여줄 것.
	public List<Clothes> selectByIdNCategoryNLength(String id, String category, String length) {
		// select * from clothes where id="test" and category="upper" and color="black"
		System.out.println("\"select * from clothes where id=\"" + id + "\" and category=\"" + category + "\" and lengthC=\"" + length + "\"");
		List<Clothes> results = jdbcTemplate.query("select * from clothes where id=\"" + id + "\" and category=\"" + category + "\" and lengthC=\"" + length + "\"", new RowMapper<Clothes>() {
					@Override
					public Clothes mapRow(ResultSet rs, int rowNum) throws SQLException {
						Clothes fs = new Clothes(rs.getString("id"), rs.getString("dress_number"),
								rs.getString("category"), rs.getString("low_category"), rs.getString("color"),
								rs.getString("pattern"), rs.getString("lengthC"));
						return fs;
					}
				});
		return results;
	}

	// id에 따른 upper, lower, outer, etc 개수 가져옴(상의, 하의, 아우터, 기타 개수) : 메인차트 보여줄 것(40,
	// 30, 20, 6) 등 네개 숫자 리턴
	public int[] selectCategoryTotalById(String id) {
		// select count(*) as total from clothes where id="test" and category="upper"
		String category[] = { "upper", "lower", "outer", "etc" };
		int result[] = new int[4];

		for (int i = 0; i < 4; i++) {
			System.out.println("select count(*) as total from clothes where id=\"" + id + "\" and category=\"" + category[i] + "\"");
			result[i] = jdbcTemplate.queryForObject("select count(*) as total from clothes where id=\"" + id + "\" and category=\"" + category[i] + "\"", Integer.class);
		}
		return result;
	}

	// id와 category에 따른 low category 개수 가져옴(후드티, 롱티, 폴라티, 셔츠티, ...) : 메인차트 눌렀을때 서브차트
	// 보여줄 것(각 카테고리 개수만큼 숫자리턴)
	public int[] selectLowCategoryTotalById(String id, String category) {
		// select count(*) as total from clothes where id="test" and low_category="cardigan"
		int result[];

		String[] outer = { "cardigan", "jacket", "padding", "coat", "jumper", "hood_zipup" };
		String[] upper = { "hood_T", "long_T", "pola", "shirt", "short_T", "sleeveless", "vest" };
		String[] lower = { "long_pants", "short_pants", "mini_skirt", "long_skirt" };
		String[] etc = { "bag", "cap", "shoes", "accessory" };

		String[] low_category = new String[10];
		if (category.equals("outer")) {
			low_category = outer;
		} else if (category.equals("upper")) {
			low_category = upper;
		} else if (category.equals("lower")) {
			low_category = lower;
		} else if (category.equals("etc")) {
			low_category = etc;
		}

		result = new int[low_category.length];

		for (int i = 0; i < low_category.length; i++) {
			if (low_category[i] != null) {
				System.out.println("select count(*) as total from clothes where id=\"" + id + "\" and low_category=\"" + low_category[i] + "\"");
				result[i] = jdbcTemplate.queryForObject("select count(*) as total from clothes where id=\"" + id 
														+ "\" and low_category=\"" + low_category[i] + "\"", Integer.class);
			}
		}
		return result;
	}

	// clothes update
	public int deleteClothes(String id, String dress_number) {
		// update clothes set category="upper", low_category="long_T", color="red", pattern="no", lengthC="long" where id="test" and dress_number="20190929181510";
		System.out.println("delete from clothes where id=\"" + id + "\" and dress_number=\"" + dress_number + "\"");
		int result = 0;
		try {
			result = jdbcTemplate.update("delete from clothes where id=\"" + id + "\" and dress_number=\"" + dress_number + "\"");
		} catch (Exception e) {
		}

		return result;
	}
}
