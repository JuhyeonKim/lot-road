package com.nagesoft.postcode.member.model;

import com.nagesoft.core.model.NGAbstractUser;
import com.nagesoft.core.model.NGDataChangeInfoForUser;
import com.nagesoft.core.mybatis.NGCountable;
import com.nagesoft.core.validator.*;
import com.nagesoft.postcode.code.model.CommonCode;
import com.nagesoft.postcode.member.BNJoinStep1;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;

/**
 * User
 * <p/>
 * 사용자 <br />
 * 일반사용자 및 파트너회원
 *
 * @author Juhyeon Kim
 */
@ToString
@NGCompareValue.TargetList(@NGCompareValue(first = "password",
                                           second = "confirmPassword",
                                           groups = {NGInsert.class, NGUpdate.class},
                                           message = "비밀번호가 일치하지 않습니다."))
public class User extends NGAbstractUser implements NGCountable, NGDataChangeInfoForUser<User> {

	// 공통 정보

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -6227261445062425480L;

	/**
	 * 페이징 번호
	 */
	private int positionIdx;

	/**
	 * 권한 목록
	 */
	private Set<Role> authorities;

	/**
	 * 권한 배열 - Form 데이터 전송용
	 */
	private RoleType[] roleList;

	/**
	 * 관리자 일반회원 구분<br />
	 * N(No - 일반회원) | Y(Yes - 관리자)
	 */
	@Setter
	@Getter
	@NGValidSelect(targets = {"Y", "N"},
				    groups = {NGInsert.class, NGUpdate.class},
				    message = "회원구분을 정확하게 입력하여 주십시오.")
	private String managerYN;

	/**
	 * 일련번호
	 */
	@Setter
	@Getter
	@NotNull(groups = {NGUpdate.class}, message = "일련번호는 필수 입니다.")
	private Long sequence;

	/**
	 * 계정
	 */
	@Setter
	@Getter
	@Pattern(regexp = "([0-9a-zA-Z]){4,12}",
	         groups = {NGInsert.class},
	         message = "아이디는 4~12자 이내 영문 또는 숫자 조합으로 띄어쓰기 없이 입력하여샤 합니다.")
	private String id;

	/**
	 * 비밀번호
	 */
	@Setter
	@Pattern(regexp = "(.(?![\\s\\t])){6,20}",
	         groups = {NGInsert.class},
	         message = "비밀번호는 6~20자 이내로 영문,숫자,특수문자를 사용해 주세요.")
	private String password;

	/**
	 * 비밀번호 확인
	 */
	@Getter
	@Setter
	@Pattern(regexp = "(.(?!\\s)){6,20}",
	         groups = {NGInsert.class},
	         message = "비밀번호는 6~20자 이내로 영문,숫자,특수문자를 사용해 주세요.")
	private String confirmPassword;

	/**
	 * 이름
	 */
	@Setter
	@Getter
	@NotEmpty(groups = {BNJoinStep1.class, NGInsert.class}, message = "이름은 필수 입니다.")
	private String name;

	/**
	 * 생년월일
	 */
	@Setter
	@Getter
	private Date birthday;

	/**
	 * 음력 여부
	 */
	@Getter
	@Setter
	@NGValidSelect(targets = {"Y", "N"},
	               groups = {NGInsert.class, NGUpdate.class},
	               message = "양력/음력 구분을 정확이 입력하여 주십시오.")
	private String lunarYN;

	/**
	 * 우편번호
	 */
	@Getter
	@Setter
    @Pattern(regexp = "([0-9]){3}(\\-){1}([0-9]){3}",
		    groups = {NGInsert.class, NGUpdate.class,},
		    message = "우편번호를 정확히 입력하여 주십시오.")
	private String postCode;

	/**
	 * 주소1
	 */
	@Getter
	@Setter
	@NotEmpty(groups = {NGInsert.class, NGUpdate.class}, message = "주소를 정확히 입력하여 주십시오.")
	private String address1;

	/**
	 * 주소2
	 */
	@Getter
	@Setter
	private String address2;

	/**
	 * 이메일
	 */
	@Getter
	@Setter
	@NGEmailCheck(groups = {NGInsert.class, NGUpdate.class}, message = "이메일 주소가 유효하지 않습니다.")
	private String email;

	/**
	 * 성경필사 다짐
	 */
	@Getter
	@Setter
	@NotEmpty(groups = {NGInsert.class, NGUpdate.class},
	               message = "성경필사 다짐을 입력하여 주십시오.")
	private String compaction;


	/**
	 * 그룹
	 */
	@Setter
	@Getter
	private CommonCode group;

	/**
	 * 소속교회 코드
	 */
	@Getter
	@Setter
	@NotNull(groups = {NGInsert.class, NGUpdate.class}, message = "소속 교회를 정확히 입력하여 주십시오.")
	private String church;

	/**
	 * 전화번호
	 */
	@Setter
	@Getter
	@NGTelCheck(groups = {NGInsert.class,}, message = "전화번호를 정확히 입력하여 주십시오.")
	private String tel;

    /**
     * 성별
     */
    @Setter
    @Getter
    @NGValidSelect(targets = {"M", "F"},
            groups = {NGInsert.class, NGUpdate.class},
            message = "성별을 정확하게 입력하여 주십시오.")
    private String gender;

    /**
     * 방문 회수
     */
    @Setter
    @Getter
    private Integer visitCount;

	private User regUser;

	/**
	 * 등록일
	 */
	@Setter
	@Getter
	private Date regDate;

	private User modUser;

	/**
	 * 수정일
	 */
	@Setter
	@Getter
	private Date modDate;

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return getId();
	}

	@Override
	public int getPositionIdx() {
		return positionIdx;
	}

	@Override
	public void setPositionIdx(int positionIdx) {
		this.positionIdx = positionIdx;
	}

	@Override
	public Object getUserKey() {
		return this.sequence;
	}

	@Override
	public void setUserKey(Object key) {
		this.sequence = (Long)key;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setAuthorities(Set authorities) {
		this.authorities = authorities;

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (authorities == null) {
			authorities = new HashSet<Role>();
		}

		return authorities;
	}

	@Override
	public User getRegUser() {
		return regUser;
	}

	@Override
	public void setRegUser(User user) {
		this.regUser = user;
	}

	@Override
	public User getModUser() {
		return modUser;
	}

	@Override
	public void setModUser(User user) {
		this.modUser = user;
	}

    /**
     * @return the roleList
     */
    public RoleType[] getRoleList() {
        return roleList;
    }

    /**
     * @param roleList the roleList to set
     */
    public void setRoleList(RoleType[] roleList) {
        this.roleList = roleList;
    }


	public String getTel1(){

		if(this.tel != null
				&& this.tel.contains("-")
				&& this.tel.split("-").length >=1 ){
			return this.tel.split("-")[0];
		}else{
			return null;

		}
	}

	public String getTel2(){

		if(this.tel != null
				&& this.tel.contains("-")
				&& this.tel.split("-").length >=2 ){
			return this.tel.split("-")[1];
		}else{
			return null;

		}
	}

	public String getTel3(){

		if(this.tel != null
				&& this.tel.contains("-")
				&& this.tel.split("-").length >=3 ){

			return this.tel.split("-")[2];
		}else{
			return null;

		}
	}

}
