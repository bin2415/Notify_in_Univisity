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
class TestServer extends Demos_App_Server
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
	
	 * @title 测试接口
	 * @action /test/index
	 * @method get
	 */
	public function indexAction ()
	{
		echo "My First Api";
	}
	
	/**
	 *  * ---------------------------------------------------------------------------------------------
	 * > 接口说明：测试接口
	 * <code>
	 * URL地址：/test/test
	 * 提交方式：POST
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试数据库
	 * @action /test/test
	 * @method post
	 */
	public function testAction()
	{		
		$this->doAuth();
		$pageId = 0;
		$commentDao =  $this->dao->load('Core_Part');
		$commentList = $commentDao->getListByCustomerTest(0,3,$pageId);
		}
		
		
	}		