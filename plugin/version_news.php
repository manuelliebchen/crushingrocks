<?php
    add_action( 'wp_ajax_version', 'apw_version' );
    add_action( 'wp_ajax_nopriv_version', 'apw_version' );
  
    function apw_version ()
	  {
		    $version = utf8_decode(get_option('apw_settings_java_version'));

		    header("Cache-Control: private");
		    header("Content-Description: File Transfer");
		    header("Content-Type: text/plain");             
		    header("Content-Length: " . strlen($version));
		    header("Content-Type: text/plain");            

		    echo "Version: \n".$version;

		    wp_die();
	  };

	
    add_action( 'wp_ajax_news', 'apw_news' );
    add_action( 'wp_ajax_nopriv_news', 'apw_news' );

	  function apw_news ()
	  {
		    $news = utf8_decode(get_option('apw_settings_java_news'));

		    header("Cache-Control: private");
		    header("Content-Description: File Transfer");
		    header("Content-Length: " . strlen($news));
		    header("Content-Type: text/plain");             

		    echo "News: \n".$news;

		    wp_die();
	  };
?>