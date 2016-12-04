package com.shishuo.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.shishuo.cms.entity.AdminVO;


@Repository
public interface AdminDao {
	public int addAdmin(AdminVO admin);
	public int deleteAdmin(@Param("adminId") long adminId);
	public void updateAdminByadminId(@Param("adminId") long adminId, @Param("password") String password);
	public List<AdminVO> getAllList(@Param("offset") long offset, @Param("rows") long rows);
	public int getAllListCount();
	public AdminVO getAdminById(@Param("adminId") long adminId);
	public AdminVO getAdminByName(@Param("name") String name);
}
