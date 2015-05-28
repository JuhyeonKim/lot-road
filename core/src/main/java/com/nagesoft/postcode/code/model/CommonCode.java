package com.nagesoft.postcode.code.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nagesoft.core.model.NGDataChangeInfoForUser;
import com.nagesoft.postcode.member.model.User;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

import com.nagesoft.core.validator.NGInsert;
import com.nagesoft.core.validator.NGUpdate;

/**
 * CommonCode
 * <p/>
 * 공통코드 DTO
 *
 * @author Juhyeon Kim
 */
public class CommonCode implements NGDataChangeInfoForUser<User>, Serializable {

	/**
	 * 공통코드
	 */
	@Getter
	@Setter
	@NotEmpty(groups = {NGUpdate.class}, message = "코드는 필수 입니다.")
	private String code;

	/**
	 * 코드명
	 */
	@Getter
	@Setter
	@NotEmpty(groups = {NGInsert.class, NGUpdate.class}, message = "코드명은 필수 입니다.")
	@Size(max = 100, groups = {NGInsert.class, NGUpdate.class}, message = "코드명은 100자 이내여야 합니다.")
	private String name;

	/**
	 * 코드 설명
	 */
	@Getter
	@Setter
	@NotEmpty(groups = {NGInsert.class, NGUpdate.class}, message = "코드설명은 필수 입니다.")
	@Size(max = 100, groups = {NGInsert.class, NGUpdate.class}, message = "코드설명은 100자 이내여야 합니다.")
	private String description;

	/**
	 * 사용 여부
	 */
	@Getter
	@Setter
	@NotNull(groups = {NGInsert.class, NGUpdate.class}, message = "사용여부는 필수 입니다.")
	private String useYN;

	/**
	 * depth
	 */
	@Getter
	@Setter
	private Integer level;

	/**
	 * 부모 코드
	 */
	@Getter
	@Setter
	private CommonCode parent;

	/**
	 * 별칭
	 */
	@Getter
	@Setter
	private String alias;

	/**
	 * 정렬 순서
	 */
	@Getter
	@Setter
	private Integer sortOrder;

	@Getter
	@Setter
	private Integer maxSortOrder;

	/**
	 * 등록자
	 */
	private User regUser;

	/**
	 * 등록일
	 */
	@Getter
	@Setter
	private Date regDate;

	/**
	 * 수정자
	 */
	private User modUser;

	/**
	 * 수정일
	 */
	@Getter
	@Setter
	private Date modDate;

	public CommonCode() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param code 코드
	 */
	public CommonCode(String code) {
		this.code = code;
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
}
