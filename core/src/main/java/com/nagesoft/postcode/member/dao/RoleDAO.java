package com.nagesoft.postcode.member.dao;

import com.nagesoft.postcode.member.model.Role;
import com.nagesoft.postcode.member.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Kimjuhyeon on 15. 1. 20..
 */
@Repository
public interface RoleDAO {

    /**
     * 권한 입력
     *
     * @param role 입력할 권한 정보
     */
    public void insert(Role role);

    /**
     * 권한 삭제
     *
     * @param sequence 권한 일련번호
     */
    public void delete(Long sequence);

    /**
     * 권한 일괄 삭제<br />
     * 사용자키에 해당하는 모든 권한 삭제
     *
     * @param user 삭제할 사용자
     */
    public void deleteByParent(User user);
}

