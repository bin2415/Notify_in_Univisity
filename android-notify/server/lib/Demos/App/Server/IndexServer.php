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
class IndexServer extends Demos_App_Server
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
	 * > 接口说明：测试Person接口
	 * <code>
	 * URL地址：/index/indexPerson
	 * 提交方式：POST
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试Preson接口
	 * @action /index/indexPerson
	 * @method get
	 */
	public function indexPersonAction ()
	{
		$this->doAuth();
		
		// get extra customer info
		$customerDao = $this->dao->load('Core_CustomerPerson');
		$customerItem = $customerDao->getById($this->customer['id']);
		if ($customerItem['face'] == '')
			$customerItem['faceUrl'] = '';
		else 
		{
			$customerItem['faceUrl'] = __FACE_URL . $customerItem['face'];
		}
		$this->render('10000', 'Load index ok', array(
			'CustomerPerson' => $customerItem
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：测试Club接口
	 * <code>
	 * URL地址：/index/indexClub
	 * 提交方式：POST
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试Club接口
	 * @action /index/indexClub
	 * @method get
	 */
	public function indexClubAction ()
	{
		$this->doAuth();
	
		// get extra customer info
		$customerDao = $this->dao->load('Core_CustomerClub');
		$customerItem = $customerDao->getById($this->customer['id']);
		if ($customerItem['face'] == '')
			$customerItem['faceUrl'] = '';
		else
		{
			$customerItem['faceUrl'] = __FACE_URL . $customerItem['face'];
		}
		$this->render('10000', 'Load index ok', array(
				'CustomerClub' => $customerItem
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：用户Preson登录接口
	 * <code>
	 * URL地址：/index/loginPerson
	 * 提交方式：POST
	 * 参数#1：sid，类型：STRING，必须：YES，示例：admin
	 * 参数#2：pass，类型：STRING，必须：YES，示例：admin
	 * 参数#3：school，类型：STRING，必须：YES，示例：山东大学
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 用户Person登录接口
	 * @action /index/loginPerson
	 * @params studentid 201300130068 STRING
	 * @params pass 123 STRING
	 * @params school 山东大学 STRING
	 * @method post
	 */
public function loginPersonAction ()
	{
		// return login customer
		$sid = $this->param('studentid');
		$pass = $this->param('pass');
		$school = $this->param('school');
		if ($sid && $pass && $school) {
			$customerDao = $this->dao->load('Core_CustomerPerson');
			$customer = $customerDao->doAuth($school,$sid, $pass);
			if ($customer) {
				$customer['sid'] = session_id();
				$customer['faceUrl'] = Demos_Util_Image::getFaceUrl($customer['face']);
				$customer['personal'] = 0;
				$_SESSION['customer'] = $customer;
				$this->render('10000', 'Login ok', array(
					'CustomerPerson' => $customer
				));
				
			}
		}
		// return sid only for client
		$customer = array('sid' => session_id());
		$this->render('14001', 'Login failed', array(
			'CustomerPerson' => $customer
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：用户Club登录接口
	 * <code>
	 * URL地址：/index/loginClub
	 * 提交方式：POST
	 * 参数#1：name，类型：STRING，必须：YES，示例：admin
	 * 参数#2：pass，类型：STRING，必须：YES，示例：admin
	 * 参数#3：school，类型：STRING，必须：YES，示例：山东大学
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 用户Club登录接口
	 * @action /index/loginClub
	 * @params school 山东大学 STRING
	 * @params name 计算机学院' STRING
	 * @params pass 123 STRING
	 * @method post
	 */
public function loginClubAction ()
	{
		// return login customer
		$school = $this->param('school');
		$name = $this->param('name');
		$pass = $this->param('pass');
		if ($name && $pass) {
			$customerDao = $this->dao->load('Core_CustomerClub');
			$customer = $customerDao->doAuth($school,$name, $pass);
			if ($customer) {
				$customer['sid'] = session_id();
				$customer['personal'] = 1;
				$customer['faceUrl'] = Demos_Util_Image::getFaceUrl($customer['face']);
				$_SESSION['customer'] = $customer;
				$this->render('10000', 'Login ok', array(
						'CustomerClub' => $customer
				));
			}
		}
		// return sid only for client
		$customer = array('sid' => session_id());
		$this->render('14001', 'Login failed', array(
				'CustomerClub' => $customer
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：用户登出接口
	 * <code>
	 * URL地址：/index/logout
	 * 提交方式：POST
	 * 参数#1：sid，类型：STRING，必须：YES，示例：
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 用户登出接口
	 * @action /index/logout
	 * @method post
	 */
	public function logoutAction ()
	{
		$_SESSION['customer'] = null;
		$this->render('10000', 'Logout ok');
	}
}