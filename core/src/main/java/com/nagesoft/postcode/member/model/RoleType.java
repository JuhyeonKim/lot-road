package com.nagesoft.postcode.member.model;

/**
 * RoleType
 * <br />
 * 권한 enum
 *
 * @author Juhyeon Kim
 */
public enum RoleType {

    /**
     * root 권한
     */
    ROLE_ROOT("ROLE_ROOT", "전체", ""),

    /**
     * 일반 회원
     */
    ROLE_GENERAL("ROLE_GENERAL", "일반회원", "일반회원"),

    /**
     * 운영관리
     */
    ROLE_MANAGE("ROLE_MANAGE", "운영관리", "운영관리");

    /**
     * 권한 코드
     */
    private String code;

    /**
     * 권한 코드 이름
     */
    private String codeName;

    /**
     * 권한 그룹 코드
     */
    private String groupCodeName;

    /**
     * RoleType Generator
     *
     * @param code 권한 코드
     * @param name 코드 이름
     * @param groupCodeName 권한 그룹코드
     */
    RoleType(String code, String name, String groupCodeName) {
        this.code = code;
        this.codeName = name;
        this.groupCodeName = groupCodeName;
    }


    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getGroupCodeName() {
        return groupCodeName;
    }

    public static String[][] getAllRoleNames() {
        RoleType[] roleTypes = RoleType.values();
        int length = roleTypes.length;
        String[][] names = new String[length][2];

        for (int i = 0; i < length; i++) {
            names[i][0] = roleTypes[i].getCode();
            names[i][1] = roleTypes[i].getCodeName();
        }

        return names;
    }

    public static String[][] getRoleNamesWithoutRoot() {
        RoleType[] roleTypes = RoleType.values();
        int length = roleTypes.length;
        String[][] names = new String[length - 1][2];

        for (int i = 1; i < length; i++) {
            names[i - 1][0] = roleTypes[i].getCode();
            names[i - 1][1] = roleTypes[i].getCodeName();
        }

        return names;
    }

    public static RoleType getRoleType(String code) {
        RoleType[] roleTypes = RoleType.values();
        RoleType result = null;

        for (RoleType type : roleTypes) {
            if (type.getCode().equals(code)) {
                result = type;
            }
        }

        return result;
    }

    public int getRoleCountByGroup() {
        String[] splitData = code.split("_");
        String prefix = splitData[0] + "_" + splitData[1] + "_";
        RoleType[] roleTypes = RoleType.values();
        int count = 0;

        for (RoleType type : roleTypes) {
            if (type.getCode().contains(prefix) && !RoleType.isSuperAuthority(type)) {
                count++;
            }
        }

        return count;
    }

    public static Boolean isSuperAuthority(RoleType roleType) {
        return roleType == RoleType.ROLE_MANAGE;
    }
}
