<?php
/**
 * Demos Dao
 *
 * @category   Demos
 * @package    Demos_Dao_Core
 * @author     James.Huang <shagoo@gmail.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

require_once 'Demos/Dao/Core.php';
require_once 'Demos/Dao/Core/CustomerPerson.php';
require_once 'Demos/Dao/Core/CustomerClub.php';
require_once  'Demos/Dao/Core/Activity.php';


/**
 * @package Demos_Dao_Core
 */
class Core_Part extends Demos_Dao_Core
{
	/**
	 * @static
	 */
	const TABLE_NAME = 'part';
	
	/**
	 * @static
	 */
	const TABLE_PRIM = 'id';
	
	/**
	 * Initialize
	 */
	public function __init () 
	{
		$this->t1 = self::TABLE_NAME;
		$this->k1 = self::TABLE_PRIM;
		
		$this->_bindTable($this->t1, $this->k1);
	}
	
	/**
	 * Get all blog comment list
	 * @param int $activityId
	 * @param int $pageId
	 */
	public function getListByActivity ($activityId, $pageId = 0)
	{
		$list = array();
		$sql = $this->select()
			->from($this->t1, '*')
			->where("{$this->t1}.activityid = ?", $activityId)
			->order("{$this->t1}.uptime desc")
			->limitPage($pageId, 10);
		
		$res = $this->dbr()->fetchAll($sql);
		if ($res) {
			foreach ($res as $row) {
				if($row['personal'] == 0)
				{
					$customerDao = new Core_CustomerPerson();
				}
				else
				{
					$customerDao = new Core_CustomerClub();
				}
				$customer = $customerDao->read($row['customerid']);
				$comment = array(
					'id'		=> $row['id'],
					'customername' => $customer['name'],
					'uptime'	=> $row['uptime'],
				);
				array_push($list, $comment);
			}
		}
		return $list;
	}
	
	public function hasParted($customerid,$activityid)
	{
		$sql = $this->select()
		->from($this->t1, '*')
		->where("{$this->t1}.activityid = ?", $activityid)
		->where("{$this->t1}.customerid = ?", $customerid);

		$user = $this->dbr()->fetchRow($sql);
		if ($user) return $user;
		return false;
	}
	/**
	 * Get customer comment list 
	 * @param string $customerId Customer ID
	 * @param int $pageId
	 */
	public function getListByCustomer ($personal,$customerId, $pageId = 0)
	{
		$list = array();
		$sql = $this->select()
			->from($this->t1, '*')
			->where("{$this->t1}.customerid = ?", $customerId)
			->where("{$this->t1}.personal = ?", $personal)
			->order("{$this->t1}.uptime desc")
			->limitPage($pageId, 10);
		
		$res = $this->dbr()->fetchAll($sql);

		if($res)
		{
			foreach($res as $row)
			{
				$activityDao = new Core_Activity();
				$activity = $activityDao->read($row['activityid']);
				$per = $activity['personal'];
				if($per == 0)
				{
					$customerDao = new Core_CustomerPerson();
										
				}
				else 
				{
					$customerDao = new Core_CustomerClub();
				}
				$customer = $customerDao->read($activity['customerid']);
				$activity1 = array(
						'id'		=> $activity['id'],
						'title'		=> $activity['title'],
						'face'		=> _FACE_URL . $customer['face'],
						'picture'		=> _PICTURE_URL . $activity['picture'],
						'content'	=> $activity['content'],
						'person'    => $customer['name'],
						'personal'  => $activity['personal'],
						'comment'	=> '评论('.$activity['commentcount'].')',
						'like'		=>'赞('.$activity['likecount'].')',
						'part'		=> '参与('.$activity['partcount'].')',
						'uptime'	=> $activity['uptime'],
				);
				array_push($list, $activity1);
			}
		}
		return $list;
		
	}
	
	public function getListByCustomerTest ($personal,$customerId, $pageId = 0)
	{
		$list = array();
		$sql = $this->select()
		->from($this->t1, '*')
		->where("{$this->t1}.customerid = ?", $customerId)
		->where("{$this->t1}.personal = ?", $personal)
		->order("{$this->t1}.uptime desc")
		->limitPage($pageId, 10);
		
		$res = $this->dbr()->fetchAll($sql);
		
		if($res)
		{
			//echo 'hhhh';
			if($res)
			{
				foreach($res as $row)
				{
					$activityDao = new Core_Activity();
					echo $row['activityid'];
				}
			}
		}
	}
}