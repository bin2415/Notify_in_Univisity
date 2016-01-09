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
require_once 'Demos/Util/Image.php';

/**
 * @package Demos_Dao_Core
 */
class Core_CustomerClub extends Demos_Dao_Core
{
	/**
	 * @static
	 */
	const TABLE_NAME = 'customer_club';

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
	 * User login
	 * @param string $school
	 * @param string $name
	 * @param string $pass
	 */
	public function doAuth ($school,$name, $pass)
	{
		$sql = $this->select()
		->from($this->t1, '*')
		->where("{$this->t1}.school = ?", $school)
		->where("{$this->t1}.name = ?", $name)
		->where("{$this->t1}.pass = ?", $pass);

		$user = $this->dbr()->fetchRow($sql);
		if ($user) return $user;
		return false;
	}

	/**
	 * Get customer by id
	 * @param int $id
	 */
	public function getById ($id) {
		$customer = $this->read($id);
		$customer['faceUrl'] = Demos_Util_Image::getFaceUrl($customer['face']);
		return $customer;
	}

	/**
	 * Add blogcount by one
	 * @param int $id
	 */
	public function addActivitycount ($id, $addCount = 1)
	{
		$customer = $this->read($id);
		$customer['activitycount'] = $customer['activitycount'] + $addCount;
		$this->update($customer);
	}

	/**
	 * Add fanscount by one
	 * @param int $id
	 */
	/*public function addFanscount ($id, $addCount = 1)
	{
		$customer = $this->read($id);
		$customer['fanscount'] = intval($customer['fanscount']) + $addCount;
		$this->update($customer);
	}*/

	/**
	 * Get blog list
	 * @param $customerId Customer ID
	 */
	public function getListByPage ($pageId = 0)
	{
		$list = array();
		$sql = $this->select()
		->from($this->t1, '*')
		->order("{$this->t1}.uptime desc");

		return $this->dbr()->fetchAll($sql);
	}
}