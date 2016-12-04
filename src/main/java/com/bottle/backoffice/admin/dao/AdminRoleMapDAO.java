package com.bottle.backoffice.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bottle.backoffice.admin.vo.AdminRoleMapVO;


@Repository
public interface AdminRoleMapDAO {
	public List<AdminRoleMapVO> getAdminRoleMapByAdminId(@Param("adminId") long adminId);
}
