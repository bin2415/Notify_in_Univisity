<?php
/**
 * Demos App
 *
 * @category   Demos
 * @package    Demos_App_Server
 * @author     James.Huang <huangjuanshi@163.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

require_once 'Demos/App/Server.php';

/**
 * @package Demos_App_Server
 */
class LikeServer extends Demos_App_Server
{
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 全局设置：
	 * <code>
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 */
	public function __init ()
	{
		parent::__init();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// service api methods
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：赞列表接口
	 * <code>
	 * URL地址：/like/likeList
	 * 提交方式：GET
	 * 参数#1：blogId，类型：INT，必须：YES
	 * 参数#2：pageId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 赞列表接口
	 * @action /like/likeList
	 * @params activityId 0 INT
	 * @params pageId 0 INT
	 * @method get
	 */
	public function likeListAction ()
	{
		$this->doAuth();
		
		$activityId = intval($this->param('activityId'));
		$pageId = intval($this->param('pageId'));
		
		$commentDao = $this->dao->load('Core_Like');
		$commentList = $commentDao->getListByActivity($activityId, $pageId);

		if ($commentList) {
			$this->render('10000', 'Get comment list ok', array(
				'Like.list' => $commentList
			));
		}
		$this->render('14010', 'Get comment list failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：赞评论接口
	 * <code>
	 * URL地址：/like/likeCreate
	 * 提交方式：POST
	 * 参数#1：blogId，类型：INT，必须：YES
	 * 参数#2：content，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 点赞评论接口
	 * @action /like/likeCreate
	 * @params activityId 0 INT
	 * @method post
	 */
	public function likeCreateAction ()
	{
		$this->doAuth();
		
		$activityId = intval($this->param('activityId'));
		$personal = $this->customer['personal'];
		// check blog exist
		$blogDao = $this->dao->load('Core_Activity');
		if (!$blogDao->exist($activityId)) {
			$this->render('10009', 'Activity not exist');
		}
		
		if ($activityId) {
			$commentDao = $this->dao->load('Core_Like');
			$commentDao->create(array(
				'activityid'		=> $activityId,	
				'customerid'	=> $this->customer['id'],
				'personal'	=> $this->customer['personal']
			));
			// add blog commentcount
			$blogDao->addLikecount($activityId);
			$this->render('10000', 'Create comment ok');
		}
		$this->render('14011', 'Create comment failed');
	}
}