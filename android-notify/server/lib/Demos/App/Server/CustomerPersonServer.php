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
class CustomerPersonServer extends Demos_App_Server
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
	 * > 接口说明：用户Person列表接口
	 * <code>
	 * URL地址：/customerPerson/customerPersonList
	 * 提交方式：GET
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 用户Person列表接口
	 * @action /customerPerson/customerPersonList
	 * @method get
	 */
	public function customerPersonListAction ()
	{
		$this->doAuth();
		
		$customerDao = $this->dao->load('Core_CustomerPerson');
		$customerList = $customerDao->getListByPage();
		$this->render('10000', 'Get customer list ok', array(
			'CustomerPerson.list' => $customerList
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：查看用户Person信息接口
	 * <code>
	 * URL地址：/customerPerson/customerPersonView
	 * 提交方式：POST
	 * 参数#1：customerId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 查看用户Person信息接口
	 * @action /customerPerson/customerPersonView
	 * @params customerId 1 INT
	 * @method post
	 */
	public function customerPersonViewAction ()
	{
		$this->doAuth();
		
		$customerId = $this->param('customerId');
		
		// get extra customer info
		$customerDao = $this->dao->load('Core_CustomerPerson');
		if ($customerDao->exist($customerId)) {
			$customerItem = $customerDao->getById($customerId);
			$this->render('10000', 'View customer ok', array(
				'Customer' => $customerItem
			));
		}
		$this->render('14002', 'View customer failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：更新用户Person信息接口
	 * <code>
	 * URL地址：/customerPerson/customerPersonEdit
	 * 提交方式：POST
	 * 参数#1：key，类型：STRING，必须：YES
	 * 参数#2：val，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 更新用户Person信息接口
	 * @action /customerPerson/customerPersonEdit
	 * @params key '' STRING
	 * @params val '' STRING
	 * @method post
	 */
	public function customerPersonEditAction ()
	{
		$this->doAuth();
		
		$key = $this->param('key');
		$val = $this->param('val');
		if ($key) {
			$upload_file_url = '';
			$upload_err = $_FILES['file0']['error'];
			$upload_file = $_FILES['file0']['tmp_name'];
			$upload_file_name = $_FILES['file0']['name'];
			if ($upload_file_name) {
				$upload_file_ext = pathinfo($upload_file_name, PATHINFO_EXTENSION);
				if ($upload_err == 0) {
					$upload_face_dir = __FACE_DIR . '/';
					$upload_file_name = md5(time().rand(123456,999999));
					$upload_file_path = $upload_face_dir . $upload_file_name . '.' . $upload_file_ext;
					if (!move_uploaded_file($upload_file, $upload_file_path)) {
						$this->render('14010', 'Change face failed');
					} else {
						$upload_file_url =  $upload_file_name . '.' . $upload_file_ext;
					}
				} else {
					$this->render('14011', 'Change face failed');
				}
			}
			
			$customerDao = $this->dao->load('Core_CustomerPerson');
			try {
				$customerDao->update(array(
					$key	=> $upload_file_url,
				),'id = '.$this->customer['id']);
			} catch (Exception $e) {
				$this->render('14003', 'Update customer failed');
			}
			$this->render('10000', 'Update customer ok');
		}
		$this->render('14004', 'Update customer failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：新建用户Preson接口
	 * <code>
	 * URL地址：/customerPerson/customerPersonCreate
	 * 提交方式：POST
	 * 参数#1：name，类型：STRING，必须：YES
	 * 参数#2：pass，类型：STRING，必须：YES
	 * 参数#3：contact，类型：STRING，必须：YES
	 * 参数#4：school，类型：STRING，必须：YES
	 * 参数#5：sid，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 新建用户Person接口
	 * @action /customerPerson/customerPersonCreate
	 * @params studentid '' STRING
	 * @params name '' STRING
	 * @params pass '' STRING
	 * @params contact '' STRING
	 * @params school '' STRING
	 * @method post
	 */
	public function customerPersonCreateAction ()
	{
//		$this->doAuth();
		$sid = $this->param('studentid');
		$name = $this->param('name');
		$pass = $this->param('pass');
		$contact = $this->param('contact');
		$school = $this->param('school');
		$customerDao = $this->dao->load('Core_CustomerPerson');
		$user = $customerDao->existPerson($sid);
		if($user)
			$this->render('14006','The sid exists');
		
		else 
		if ($name && $pass && $sid) {
			
			$customerDao->create(array(
				'studentid'	=> $sid,
				'name'	=> $name,
				'pass'	=> $pass,
				'contact'	=> $contact,
				'school'	=> $school,
				'activitycount' => 0
			));
			$this->render('10000', 'Create customer ok');
		}
		$this->render('14005', 'Create customer failed');
	}
	
	/*/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：添加粉丝接口
	 * <code>
	 * URL地址：/customer/fansAdd
	 * 提交方式：POST
	 * 参数#1：customerId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 添加粉丝接口
	 * @action /customer/fansAdd
	 * @params customerId '' INT
	 * @method post
	 */
	/*public function fansAddAction ()
	{
		$this->doAuth();
		
		$customerId = $this->param('customerId');
		if ($customerId) {
			$fansDao = $this->dao->load('Core_CustomerFans');
			if (!$fansDao->exist($customerId, $this->customer['id'])) {
				$fansDao->create(array(
					'customerid'	=> $customerId,
					'fansid'		=> $this->customer['id']
				));
				// add customer blogcount
				$customerDao = $this->dao->load('Core_Customer');
				$customerDao->addFanscount($customerId);
				// add into notice
				$noticeDao = $this->dao->load('Core_Notice');
				$noticeDao->addFanscount($customerId);
				$this->render('10000', 'Add fans ok');
			}
		}
		$this->render('14006', 'Add fans failed');
	}
	*/
	/*/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：删除粉丝接口
	 * <code>
	 * URL地址：/customer/fansDel
	 * 提交方式：POST
	 * 参数#1：customerId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 删除粉丝接口
	 * @action /customer/fansDel
	 * @params customerId '' INT
	 * @method post
	 */
	
	/*public function fansDelAction ()
	{
		$this->doAuth();
		
		$customerId = $this->param('customerId');
		if ($customerId) {
			$fansDao = $this->dao->load('Core_CustomerFans');
			if ($fansDao->exist($customerId, $this->customer['id'])) {
				$fansDao->delete($customerId, $this->customer['id']);
				$this->render('10000', 'Delete fans ok');
			}
		}
		$this->render('14007', 'Delete fans failed');
	}*/
}