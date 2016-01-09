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
require_once 'Demos/Util/Image.php';

/**
 * @package Demos_Dao_Core
 */
class Core_Activity extends Demos_Dao_Core
{
	/**
	 * @static
	 */
	const TABLE_NAME = 'activity';
	
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
	 * Add commentcount by one
	 * @param int $id
	 */
	public function addCommentcount ($id, $addCount = 1)
	{
		$blog = $this->read($id);
		$blog['commentcount'] = $blog['commentcount'] + $addCount;
		$this->update($blog);
	}
	
	/**
	 * 
	 * @param unknown $id
	 * @param number $addCount
	 */
	public function addPartcount($id, $addCount = 1)
	{
		$activity = $this->read($id);
		$activity['partcount'] = $activity['partcount'] + $addCount;
		$this->update($activity);
	}
	
	/**
	 * 
	 * @param unknown $id
	 * @param number $addCount
	 */
	public function addLikecount($id,$addCount = 1)
	{
		$activity = $this->read($id);
		$activity['likecount'] = $activity['likecount'] + $addCount;
		$this->update($activity);
	}
	
	/**
	 * Get all blog list 
	 * @param int $pageId
	 * @param tinyint $personal
	 */
	public function getListByPage ($personal,$pageId = 0)
	{
		$list = array();
		$sql = $this->select()
		->from($this->t1,'*')
		->where("{$this->t1}.personal = ?",$personal)
		->order("{$this->t1}.uptime desc")
		->limitPage($pageId,10);
		$res = $this->dbr()->fetchAll($sql);
		if($personal == 0)
		{
			if ($res) {
				$customerDao = new Core_CustomerPerson();
				foreach ($res as $row) {
					$customer = $customerDao->read($row['customerid']);
					$activity = array(
							'id'		=> $row['id'],
							'title'		=> $row['title'],
							'face'		=> __FACE_URL . $customer['face'],
							'picture'   => __PICTURE_URL .$row['picture'],
							'content'	=> $row['content'],
							'person'    => $customer['name'],
							'comment'	=> '评论('.$row['commentcount'].')',
							'like'		=>'赞('.$row['likecount'].')',
							'part'		=> '参与('.$row['partcount'].')',
							'uptime'	=> $row['uptime'],
					);
					array_push($list, $activity);
				}
			}
			
		}
		else
		{
			if ($res) {
				$customerDao = new Core_CustomerClub();
				foreach ($res as $row) {
					$customer = $customerDao->read($row['customerid']);
					$activity = array(
							'id'		=> $row['id'],
							'title'		=> $row['title'],
							'face'		=> __FACE_URL . $customer['face'],
							'picture'   => __PICTURE_URL .$row['picture'],
							'content'	=> $row['content'],
							'person'    => $customer['name'],
							'comment'	=> '评论('.$row['commentcount'].')',
							'like'		=>'赞('.$row['likecount'].')',
							'part'		=> '参与('.$row['partcount'].')',
							'uptime'	=> $row['uptime'],
					);
					array_push($list, $activity);
				}
			}
		}
		return $list;
	}
	
	/**
	 * Get blog list 
	 * @param string $customerId Customer ID
	 * @param int $pageId
	 */
	public function getListByCustomer ($personal,$customerId, $pageId = 0)
	{
		$list = array();
		$sql = $this->select()
			->from($this->t1, '*')
			->where("{$this->t1}.customerid = ?", $customerId)
			->order("{$this->t1}.uptime desc")
			->limitPage($pageId, 10);
		
		$res = $this->dbr()->fetchAll($sql);
		if ($res) {
			if($personal == 0)
			{
				if ($res) {
					$customerDao = new Core_CustomerPerson();
					foreach ($res as $row) {
						$customer = $customerDao->read($row['customerid']);
						$activity = array(
								'id'		=> $row['id'],
								'title'		=> $row['title'],
								'face'		=> __FACE_URL . $customer['face'],
								'picture'		=>__PICTURE_URL .$row['picture'],
								'content'	=> $row['content'],
								'person'    => $customer['name'],
								'comment'	=> '评论('.$row['commentcount'].')',
								'like'		=>'赞('.$row['likecount'].')',
								'part'		=> '参与('.$row['partcount'].')',
								'uptime'	=> $row['uptime'],
						);
						array_push($list, $activity);
					}
			}
			
		}
		else 
		{
			$customerDao = new Core_CustomerClub();
			foreach ($res as $row) {
				$customer = $customerDao->read($row['customerid']);
				$activity = array(
						'id'		=> $row['id'],
						'title'		=> $row['title'],
						'face'		=> __FACE_URL . $customer['face'],
						'picture'		=> __PICTURE_URL .$row['picture'],
						'content'	=> $row['content'],
						'person'    => $customer['name'],
						'comment'	=> '评论('.$row['commentcount'].')',
						'like'		=>'赞('.$row['commentcount'].')',
						'part'		=> '参与('.$row['commentcount'].')',
						'uptime'	=> $row['uptime'],
				);
				array_push($list, $activity);
			}
		}
		
	}
	return $list;
}

public function getById ($id) {
		$activity = $this->read($id);
		$activity['picture'] = __PICTURE_URL .$activity['picture'];
		return $activity;
	}
}