package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import dto.Clothes;
import dto.Member;

public class MemberDao {
	private JdbcTemplate jdbcTemplate;

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// id에 따른 member 정보 리턴 : 로그인 여부 확인 위함
	public Member selectMemberById(String id) {
		System.out.println("select * from member where id=\"" + id + "\"");
		List<Member> results = jdbcTemplate.query("select * from member where id=\"" + id + "\"",
				new RowMapper<Member>() {
					@Override
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
						Member fs = new Member(rs.getString("id"), rs.getString("password"), rs.getString("nickname"));
						return fs;
					}
				});
		return results.get(0);
	}

	// member 추가 : 회원가입
	public int insertToMember(String id, String password, String nickname) {
		int result = 0;
		try {
			System.out.println("insert into member values(\"" + id + "\", \"" + password + "\", \"" + nickname + "\")");
			result = jdbcTemplate.update("insert into member values(\"" + id + "\", \"" + password + "\", \"" + nickname + "\")");
		} catch (Exception e) {
			System.out.println("insertToMembers 오류");
		}
		return result;
	}
}
