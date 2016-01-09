<?php 

$_DataMap = array(
	'CustomerPerson' => array(
		'id' => 'id',
		'sid' => 'sid',
		'name' => 'name',
		'school' => 'school',
		'contact' => 'contact',
		'activitycount' => 'activitycount',
		'face'	=> 'face',
		'faceUrl' => 'faceUrl',
		'uptime' => 'uptime',
	),
	'CustomerClub' => array(
		'id' => 'id',
		'sid' => 'sid',
		'name' => 'name',
		'contact' => 'contact',
		'admin' => 'admin',
		'school' => 'school',
		'activitycount' => 'activitycount',
		'face'	=> 'face',
		'faceUrl' => 'faceUrl',
		'uptime' => 'uptime',
	),
	'Activity' => array(
		'id' => 'id',
		'picture' => 'picture',
		'face' => 'face',
		'title' => 'title',
		'content' => 'content',
		'person' => 'person',
		'comment' => 'comment',
		'like' => 'like',
		'part' => 'part',
		'personal' => 'personal',
		'uptime' => 'uptime',
	),
	'Comment' => array(
		'id' => 'id',
		'customername' => 'customername',
		'content' => 'content',
		'uptime' => 'uptime',
	),
	'Like' => array(
		'id' => 'id',
		'customername' => 'customername',
		'uptime' => 'uptime',
	),
	'Part' => array(
		'id' => 'id',
		'customername' => 'customername',
		'uptime' => 'uptime',
	),
	'Image' => array(
		'id' => 'id',
		'url' => 'url',
		'type' => 'type',
	),
	'Notice' => array(
		'id' => 'id',
		'message' => 'message'
	),
);

function M ($model, $data)
{
	global $_DataMap;
	
	$dataMap = isset($_DataMap[$model]) ? $_DataMap[$model] : null;
	if ($dataMap) {
		$dataRes = array();
		foreach ((array) $data as $k => $v) {
			if (array_key_exists($k, $dataMap)) {
				$mapKey = $dataMap[$k];
				$dataRes[$mapKey] = $v;
			}
		}
		return $dataRes;
	}
	
	return $data;
}