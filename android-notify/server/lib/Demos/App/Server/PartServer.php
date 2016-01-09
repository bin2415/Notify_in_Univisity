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
class PartServer extends Demos_App_Server
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
	 * > 接口说明：参与列表接口
	 * <code>
	 * URL地址：/part/partList
	 * 提交方式：GET
	 * 参数#1：activityId，类型：INT，必须：YES
	 * 参数#2：pageId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 参与列表接口
	 * @action /part/partList
	 * @params activityId 0 INT
	 * @params pageId 0 INT
	 * @method get
	 */
	public function partListAction ()
	{
		$this->doAuth();
		
		$activityId = intval($this->param('activityId'));
		$pageId = intval($this->param('pageId'));
		
		$commentDao = $this->dao->load('Core_Part');
		$commentList = $commentDao->getListByActivity($activityId, $pageId);

		if ($commentList) {
			$this->render('10000', 'Get comment list ok', array(
				'Part.list' => $commentList
			));
		}
		$this->render('14010', 'Get comment list failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：报名参与评论接口
	 * <code>
	 * URL地址：/part/partCreate
	 * 提交方式：POST
	 * 参数#1：blogId，类型：INT，必须：YES
	 * 参数#2：content，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 报名参与接口
	 * @action /part/partCreate
	 * @params activityId 0 INT
	 * @method post
	 */
	public function partCreateAction ()
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
			$commentDao = $this->dao->load('Core_Part');
			$res = $commentDao->hasParted($this->customer['id'],$activityId);
			if($res)
			{
				$this->render('14005', 'You have parted');
			}
			else 
			
			{$commentDao->create(array(
				'activityid'		=> $activityId,	
				'customerid'	=> $this->customer['id'],
				'personal'	=> $this->customer['personal']
			));
			// add blog commentcount
			$blogDao->addPartcount($activityId);
			$this->render('10000', 'Create part ok');
			}
		}
		$this->render('14011', 'Create part failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：报名参与评论接口
	 * <code>
	 * URL地址：/part/partPersonal
	 * 提交方式：POST
	 * 参数#1：pageId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 报名参与接口
	 * @action /part/partPersonal
	 * @params pageId 0 INT
	 * @method post
	 */
	
	public function partPersonalAction()
	{
		$this->doAuth();
		
		$pageId = intval($this->param('pageId'));
		$personal = $this->customer['personal'];
		$commentDao = $this->dao->load('Core_Part');
		$commentList = $commentDao->getListByCustomer($personal,$this->customer['id'], $pageId);
		
		if ($commentList) {
			$this->render('10000', 'Get customer list ok', array(
					'Activity.list' => $commentList
			));
		}
		$this->render('14010', 'Get comment list failed');
		}
		
	}
