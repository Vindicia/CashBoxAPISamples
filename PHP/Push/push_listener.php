<?
date_default_timezone_set("America/Chicago");

$ipAddress = $_SERVER['REMOTE_ADDR'];
if (array_key_exists('HTTP_X_FORWARDED_FOR', $_SERVER)) {
    $ipAddress = array_pop(explode(',', $_SERVER['HTTP_X_FORWARDED_FOR']));
}

if (is_writable("push.log") ) {
  $raw = file_get_contents('php://input');
  $raw_json = json_decode($raw);
  print "<p>Writeable</p>\n";
  $time =microtime(true);
  $micro_time=sprintf("%06d",($time - floor($time)) * 1000000);
  $date=new DateTime( date('Ymd H:i:s.'.$micro_time,$time) );
  print "Date with microseconds :<br> "
    .$date->format("Y-m-d H:i:s.u");
  $fd = fopen("push.log", "a");
  fwrite($fd, $date->format("Y-m-d H:i:s.u") . "  $ipAddress    " . $_POST['signature'] . "\n" . 
    print_r(apache_request_headers(), true) .
    $raw . "\n"
  );
  fclose($fd);
} else {
  print "<p>Not writeable</p>\n";
}
?>
