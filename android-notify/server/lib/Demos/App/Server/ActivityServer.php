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
class ActivityServer extends Demos_App_Server
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
	 * > 接口说明：活动列表接口
	 * <code>
	 * URL地址：/activity/activityList
	 * 提交方式：GET
	 * 参数#1：personal，类型：INT，必须：YES
	 * 参数#2：pageId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 活动列表接口
	 * @action /Activity/activityList
	 * @params pageId 0 INT
	 * @params personal 0 INT
	 * @method get
	 */
	public function activityListAction ()
	{
		$this->doAuth();
		
		$personal = intval($this->param('personal'));
		//$personal = $this->customer['personal'];
		$pageId = intval($this->param('pageId'));
		
		//$ActivityList = array();
		
		
		$activityDao = $this->dao->load('Core_Activity');
		$ActivityList = $activityDao->getListByPage($personal,$pageId);
		
		if ($ActivityList) {
			$this->render('10000', 'Get Activity list ok', array(
				'Activity.list' => $ActivityList
			));
		}
		$this->render('14008', 'Get Activity list failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：活动列表接口
	 * <code>
	 * URL地址：/activity/activityListPersonal
	 * 提交方式：GET
	 * 参数#1：pageId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 活动列表接口
	 * @action /Activity/activityList
	 * @params pageId 0 INT
	 * @method get
	 */
	public function activityListPersonalAction()
	{
		$this->doAuth();
		
		$personal = $this->customer['personal'];
		$pageId = intval($this->param('pageId'));
		
		//$ActivityList = array();
		
		
		$activityDao = $this->dao->load('Core_Activity');
		$ActivityList = $activityDao->getListByCustomer($personal,$this->customer['id'],$pageId);
		
		if ($ActivityList) {
			$this->render('10000', 'Get Activity list ok', array(
					'Activity.list' => $ActivityList
			));
		}
		$this->render('14008', 'Get Activity list failed');
	}
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：查看活动正文接口
	 * <code>
	 * URL地址：/activity/activityView
	 * 提交方式：POST
	 * 参数#1：activityId，类型：INT，必须：YES，示例：1
	 *  参数#2：personal，类型：INT，必须：YES，示例：1
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 查看活动正文接口
	 * @action /activity/activityView
	 * @params activityId 1 INT
	 * @params personal 0 INT
	 * @method post
	 */
	public function activityViewAction ()
	{
		$this->doAuth();
		
		$blogId = intval($this->param('activityId'));
		$personal = intval($this->param('personal'));
		
		//$personal = $this->customer['personal'];
		
		$blogDao = $this->dao->load('Core_Activity');
		$blogItem = $blogDao->read($blogId);
		if($blogItem['picture']==null)
		{
			$picture_url = '';
		}
		else 
		{
			$picture_url =  __PICTURE_URL .$blogItem['picture'];
		}
		$arrayActivity = array(
								'id'		=> $blogItem['id'],
								'title'		=> $blogItem['title'],
								'picture'		=> $picture_url,
								'content'	=> $blogItem['content'],
								'comment'	=> '评论('.$blogItem['commentcount'].')',
								'like'		=>'赞('.$blogItem['likecount'].')',
								'part'		=> '参与('.$blogItem['partcount'].')',
								'uptime'	=> $blogItem['uptime'],
		);
		if ($personal == 0)
		{$customerDao = $this->dao->load('Core_CustomerPerson');
		$customerItem = $customerDao->getById($blogItem['customerid']);
		if($customerItem['face'] != '')
			$customerItem['face'] =  __FACE_URL . $customerItem['face'];
		$this->render('10000', 'Get activity ok', array(
				'CustomerPerson' => $customerItem,
				'Activity' => $arrayActivity
		));
		}
		else 
		{
			$customerDao = $this->dao->load('Core_customerClub');
			$customerItem = $customerDao->getById($blogItem['customerid']);
		    if($customerItem['face'] != '')
			$customerItem['face'] =  __FACE_URL . $customerItem['face'];
			$this->render('10000', 'Get activity ok', array(
					'CustomerClub' => $customerItem,
					'Activity' => $arrayActivity
			));
		}
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：发表活动接口
	 * <code>
	 * URL地址：/activity/activityCreate
	 * 提交方式：POST
	 * 参数#1：content，类型：STRING，必须：YES
	 * 参数#2：picture，类型：STRING，必须：YES
	 * 参数#3：title，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 发表activity列表接口
	 * @action /activity/activityCreate
	 * @params content print STRING
	 * @method post
	 */
	public function activityCreateAction ()
	{
		$this->doAuth();
		$content = $this->param('content');
		$personal = $this->customer['personal'];
		if ($content) {
			$upload_file_url = '';
			$upload_err = $_FILES['file0']['error'];
			$upload_file = $_FILES['file0']['tmp_name'];
			$upload_file_name = $_FILES['file0']['name'];
		    if ($upload_file_name) {
				$upload_file_ext = pathinfo($upload_file_name, PATHINFO_EXTENSION);
				if ($upload_err == 0) {
					$upload_face_dir = __PICTURE_DIR . '/';
					$upload_file_name = md5(time().rand(123456,999999));
					$upload_file_path = $upload_face_dir . $upload_file_name . '.' . $upload_file_ext;
					if (!move_uploaded_file($upload_file, $upload_file_path)) {
						$this->render('14010', 'Create activity failed');
					} else {
						$upload_file_url =  $upload_file_name . '.' . $upload_file_ext;
					}
				} else {
					$this->render('14011', 'Create activity failed');
				}
			}
			
			$activityDao = $this->dao->load('Core_Activity');
			$activityDao->create(array(
				'personal'		=> $personal,
				'customerid'	=> $this->customer['id'],
				'title'			=> '',
				'content'		=> $content,
				'picture'		=> $upload_file_url
			));
			// add customer blogcount
			if ($personal == 0)
			{$customerDao = $this->dao->load('Core_CustomerPerson');
			}
			else 
			{
				$customerDao = $this->dao->load('Core_CustomerClub');
			}
			$customerDao->addActivitycount($this->customer['id']);
			$this->render('10000', 'Create Activity ok');
		}
		$this->render('14009', 'Create Activity failed');
	}
	
}