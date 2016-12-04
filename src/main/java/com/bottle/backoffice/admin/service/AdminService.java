package com.bottle.backoffice.admin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.api.common.constants.IWebServiceConstants;
import com.bottle.api.common.exception.MyAPIRuntimeException;
import com.bottle.backoffice.admin.dao.AdminRoleMapDAO;
import com.bottle.backoffice.admin.vo.AdminRoleMapVO;
import com.shishuo.cms.constant.SystemConstant;
import com.shishuo.cms.dao.AdminDao;
import com.shishuo.cms.entity.AdminVO;
import com.shishuo.cms.entity.vo.AdminVOExt;
import com.shishuo.cms.entity.vo.PageVo;
import com.shishuo.cms.exception.AuthException;
import com.shishuo.cms.util.AuthUtils;
import com.shishuo.cms.util.PropertyUtils;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private AdminRoleMapDAO adminRoleMapDAO;
	
	@Autowired
	private AdminRoleMapDAO adminAndRoleMapDAO;

	public AdminVO addAdmin(String name, String password)
			throws AuthException {
		Date now = new Date();
		AdminVO admin = new AdminVO();
		admin.setName(name);
		admin.setPassword(AuthUtils.getPassword(password));
		admin.setCreateTime(now);
		adminDao.addAdmin(admin);
		return admin;
	}

	public int deleteAdmin(long adminId) {
		return adminDao.deleteAdmin(adminId);
	}

	public void updateAdminByAmdinId(long adminId, String password)
			throws AuthException {
		String pwd = AuthUtils.getPassword(password);
		adminDao.updateAdminByadminId(adminId, pwd);
	}

	public void adminLogin(final AdminVO adminVO) {
		if (null == adminVO) {			
			throw new NullPointerException("adminVO is null");
		}
		
		AdminVO adminResult = adminDao.getAdminByName(adminVO.getName());
		if (adminResult == null) {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_AdminLogin_UserNotExisted, "adminLogin: admin is not existed. name:" + adminVO.getName());
		}

		if (true == adminVO.getPassword().equals(adminResult.getPassword())) {
			final List<AdminRoleMapVO> mapList = adminRoleMapDAO.getAdminRoleMapByAdminId(adminResult.getAdminId());
			final List<Long> roleList = new ArrayList<Long>();
			if (null != mapList) {
				for (AdminRoleMapVO mapVO : mapList) {
					roleList.add(mapVO.getRoleId());
				}
			}
		}
		else {
			throw new MyAPIRuntimeException(IWebServiceConstants.RestServiceExceptionEnum._RestService_Exception_AdminLogin_WrongPassword, 
												"adminLogin: wrong password. name:" + adminVO.getName() + "--password:" + adminVO.getPassword());
		}
	}
	
	public void adminLogin(String name, String password,
			HttpServletRequest request) throws AuthException,
			IOException {
		AdminVO adminResult = adminDao.getAdminByName(name);
		if (adminResult == null) {
			throw new AuthException("邮箱或密码错误");
		}

		if (password.equals(adminResult.getPassword())) {
			HttpSession session = request.getSession();
			adminResult.setPassword("");
			final List<AdminRoleMapVO> mapList = adminRoleMapDAO.getAdminRoleMapByAdminId(adminResult.getAdminId());
			final List<Long> roleList = new ArrayList<Long>();
			if (null != mapList) {
				for (AdminRoleMapVO mapVO : mapList) {
					roleList.add(mapVO.getRoleId());
				}
			}
			
			AdminVOExt adminExt = new AdminVOExt();
			adminExt.setCreateTime(adminResult.getCreateTime());
			adminExt.setAdminId(adminResult.getAdminId());
			adminExt.setName(adminResult.getName());
			adminExt.setPassword(adminResult.getPassword());
			
			if (roleList.contains(new Long(1))) {
				adminExt.setAdmin(true);
			}
			else {
				adminExt.setAdmin(false);
			}
			
			session.setAttribute(SystemConstant.SESSION_ADMIN, adminExt);
		} else {
			throw new AuthException("邮箱或密码错误");
		}
	}

	public AdminVO getAdminById(long adminId) {
		return adminDao.getAdminById(adminId);
	}

	public List<AdminVO> getAllList(long offset, long rows) {
		List<AdminVO> adminList = adminDao.getAllList(offset, rows);
		
		if (null == adminList) {
			adminList = new ArrayList<AdminVO>();
		}
		else {
			for (AdminVO admin : adminList) {
				final List<AdminRoleMapVO> adminRoleMapVOList = adminAndRoleMapDAO.getAdminRoleMapByAdminId(admin.getAdminId());
				final List<Long> roleIdList = new ArrayList<Long>();
				for (AdminRoleMapVO  map : adminRoleMapVOList) {
					roleIdList.add(map.getRoleId());
				}
				
				admin.setRoleList(roleIdList);
			}
		}
		
		return adminList;
	}

	public int getAllListCount() {
		return adminDao.getAllListCount();
	}

	public PageVo<AdminVO> getAllListPage(int pageNum) {
		PageVo<AdminVO> pageVo = new PageVo<AdminVO>(pageNum);
		pageVo.setRows(20);
		List<AdminVO> list = this.getAllList(pageVo.getOffset(),
				pageVo.getRows());
		pageVo.setList(list);
		pageVo.setCount(this.getAllListCount());
		return pageVo;
	}

	public AdminVO getAdminByName(String name) {
		return adminDao.getAdminByName(name);
	}

	public long getSuperAdminId() {
		AdminVO admin = getAdminByName(PropertyUtils
				.getValue("shishuocms.admin"));
		return admin.getAdminId();
	}
}
