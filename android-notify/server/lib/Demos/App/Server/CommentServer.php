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
class CommentServer extends Demos_App_Server
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
	 * > 接口说明：评论列表接口
	 * <code>
	 * URL地址：/comment/commentList
	 * 提交方式：GET
	 * 参数#1：activityId，类型：INT，必须：YES
	 * 参数#2：pageId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 评论列表接口
	 * @action /comment/commentList
	 * @params activityId 0 INT
	 * @params pageId 0 INT
	 * @method get
	 */
	public function commentListAction ()
	{
		$this->doAuth();
		
		$activityId = intval($this->param('activityId'));
		$pageId = intval($this->param('pageId'));
		
		$commentDao = $this->dao->load('Core_Comment');
		$commentList = $commentDao->getListByActivity($activityId, $pageId);

		if ($commentList) {
			$this->render('10000', 'Get comment list ok', array(
				'Comment.list' => $commentList
			));
		}
		$this->render('14010', 'Get comment list failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：发表评论接口
	 * <code>
	 * URL地址：/comment/commentCreate
	 * 提交方式：POST
	 * 参数#1：activityId，类型：INT，必须：YES
	 * 参数#2：content，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 发表评论接口
	 * @action /comment/commentCreate
	 * @params activityId 0 INT
	 * @params content '' STRING
	 * @method post
	 */
	public function commentCreateAction ()
	{
		$this->doAuth();
		
		$activityId = intval($this->param('activityId'));
		$content = $this->param('content');
		$personal = $this->customer['personal'];
		// check blog exist
		$blogDao = $this->dao->load('Core_Activity');
		if (!$blogDao->exist($activityId)) {
			$this->render('10009', 'Activity not exist');
		}
		
		if ($activityId && $content) {
			$commentDao = $this->dao->load('Core_Comment');
			$commentDao->create(array(
				'activityid'		=> $activityId,	
				'customerid'	=> $this->customer['id'],
				'personal'	=> $this->customer['personal'],
				'content'		=> $content
			));
			// add blog commentcount
			$blogDao->addCommentcount($activityId);
			$this->render('10000', 'Create comment ok');
		}
		$this->render('14011', 'Create comment failed');
	}
}