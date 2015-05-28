package com.nagesoft.postcode.member.model;

import com.nagesoft.core.model.NGDataChangeInfoForUser;
import com.nagesoft.core.spring.NGRole;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kimjuhyeon on 15. 1. 20..
 */
public class Role extends NGRole implements Serializable, NGDataChangeInfoForUser<User> {

    /**
     * 일련번호
     */
    @Setter
    @Getter
    private Long sequence;

    /**
     * 관리자
     */
    @Setter
    @Getter
    private User user;

    /**
     * 권한 타입
     */
    @Setter
    @Getter
    private RoleType roleType;

    /**
     * 등록자
     */
    private User regUser;

    /**
     * 수정자
     */
    private User modUser;

    /**
     * 등록일
     */
    @Setter
    @Getter
    private Date regDate;

    /**
     * 수정일
     */
    @Setter
    @Getter
    private Date modDate;

    @Override
    public User getRegUser() {
        return regUser;
    }

    @Override
    public void setRegUser(User regUser) {
        this.regUser = regUser;
    }

    @Override
    public User getModUser() {
        return modUser;
    }

    @Override
    public void setModUser(User modUser) {
        this.modUser = modUser;
    }
}
