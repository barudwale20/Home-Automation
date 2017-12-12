<?php
$file = "buttonStatus.txt";

if (isset($_GET['on1']))
{
    $handle = fopen($file,'w+');
	$stat = readfile($file);
	if($stat == 10 || $stat == 00){
		$onstring = "10";
		fwrite($handle,$onstring);
		fclose($handle);
	}
	elseif($stat == 11 || $stat == 01){
		$onstring = "11";
		fwrite($handle,$onstring);
		fclose($handle);
	}
	
}

elseif(isset($_GET['off1']))
{
    $handle = fopen($file,'w+');
	$stat = readfile($file);
	if($stat == 10 || $stat == 00){
		$offstring = "00";
		fwrite($handle,$offstring);
		fclose($handle);
	}
	elseif($stat == 11 || $stat == 01){
		$offstring = "01";
		fwrite($handle,$offstring);
		fclose($handle);
	}
}

if (isset($_GET['on2']))
{
    $handle = fopen($file,'w+');
	$stat = readfile($file);
	if($stat == 00 || $stat == 01){
		$onstring = "01";
		fwrite($handle,$onstring);
		fclose($handle);
	}
	elseif($stat == 10 || $stat == 11){
		$onstring = "11";
		fwrite($handle,$onstring);
		fclose($handle);
	}
}

elseif (isset($_GET['off2']))
{
    $handle = fopen($file,'w+');
	$stat = readfile($file);
	if($stat == 00 OR $stat == 01){
		$offstring = "00";
		fwrite($handle,$offstring);
		fclose($handle);
	}
	elseif($stat == 10 OR $stat == 11){
		$offstring = "10";
		fwrite($handle,$offstring);
		fclose($handle);
	}
}
?>