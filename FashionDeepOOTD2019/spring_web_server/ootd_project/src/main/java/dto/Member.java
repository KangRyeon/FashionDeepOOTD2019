package dto;

public class Member {
	private String id;
	private String password;
	private String nickname;
	
	public Member(String id, String password, String nickname) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
	}
	
	public String getId() { return id; }
	public String getPassword() { return password; }
	public String getNickname() { return nickname; }
	
	public void setId(String id) { this.id = id; }
	public void setPassword(String password) { this.password = password; }
	public void setNickname(String nickname) { this.nickname = nickname; }
	
}
