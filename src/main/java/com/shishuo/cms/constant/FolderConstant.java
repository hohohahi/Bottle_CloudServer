/*
 * 
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	 
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.shishuo.cms.constant;

/**
 * @author 目录属性常量
 * 
 */
public class FolderConstant {

	public static enum Status {
		hidden, display
	};

	/**
	 * @author Herbert
	 * 
	 */
	public static enum Rank {
		/**
		 * 任何人
		 */
		everyone, /**
		 * 登录用户
		 */
		login, /**
		 * vip用户
		 */
		vip, /**
		 * 管理员
		 */
		admin
	};

	/**
	 * @author Herbert
	 * 
	 */
	public static enum Owner {
		/**
		 * 系统创建
		 */
		system, /**
		 * 应用创建
		 */
		app
	};

	public static enum Type {
		/**
		 * 页面
		 */
		page,
		/**
		 * 列表
		 */
		list, /**
		 * 目录
		 */
		folder,
		/**
		 * 所有
		 */
		all
	};
}
