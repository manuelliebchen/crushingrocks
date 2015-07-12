<?php	
	  add_action( 'wp_ajax_download_bot', 'apw_download_bot' );
	  add_action( 'wp_ajax_nopriv_download_bot', 'apw_download_bot' );

	  function apw_download_bot ()
	  {
		    auth_redirect();

		    if ( isset( $_GET['file'] ))
		    {
			      $upload_dir = wp_upload_dir();
			      $upload_dir = $upload_dir['basedir'];
			      $upload_dir = $upload_dir."/bots/";

			      $user = wp_get_current_user();
			      $user = $user->user_login;

			      $file = $upload_dir.$user;
			
			      $filename = $file."/".$_GET['file'];

			      if ( file_exists( $filename ) )
			      {
				        header("Cache-Control: private");
				        header("Content-Description: File Transfer");
				        header("Content-Length: ". filesize("$filename"));
				        header("Content-Disposition: filename=".$_GET['file']);
				        header("Content-Type: application/force-download"); 

				        readfile($filename);
			      }
			      else
			      {
				        echo "Datei nicht gefunden!";
			      }
		    }
		    else
		    {
			      echo "Keine Datei ausgewählt!";
		    }

		    wp_die();
	  };
  
	  add_action( 'wp_ajax_download_user', 'apw_download_user' );
	  add_action( 'wp_ajax_nopriv_download_user', 'apw_download_user' );
  
    function apw_download_user ()
	  {
		    auth_redirect();
    
        if (!current_user_can('manage_options'))
        {
            echo "Du hast dazu nicht die berechtigung!";
        }
        else
        {
            header("Cache-Control: private");
		        header("Content-Description: File Transfer");
		        header("Content-Disposition: filename=Userlist.csv");
		        header("Content-Type: application/force-download"); 
      
            echo "Nutzername,Mail,Vorname,Nachname,Matrikelnummer\n";
    
            foreach (get_users() as $user)
            {
                $login = str_replace(',', ';', $user->user_login);
                $email = str_replace(',', ';', $user->user_email);
                $first = str_replace(',', ';', $user->first_name);
                $last = str_replace(',', ';', $user->last_name);
                $matrikel = str_replace(',', ';', $user->matrikel);
                echo $login .",". $email .",". $first .",". $last .",". $matrikel ."\n";
            }
        }

		    wp_die();
	  };
?>