package com.bottle.backoffice.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bottle.backoffice.admin.vo.AdminRoleMapVO;


@Repository
public interface RoleDAO {
	public List<AdminRoleMapVO> getRoleListByAdminId(@Param("adminId") long adminId);
}
