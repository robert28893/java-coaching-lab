package vn.unigap.java.common.enums;

public enum RoleEnum {
	USER("USER");

	private final String roleName;

	RoleEnum(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}
}
