<?php
    function apw_bots()
    {	
        $folder = apw_bots_folder();
        
        $status = "";

		    if (isset($_GET['remove']))
		    {
			      if (unlink($folder."/".$_GET['remove']))
			      {
				        $status = "Bot ".$_GET['remove']." wurde gelöscht!";
			      }
			      else
			      {
				        $status = "Bot ".$_GET['remove']." konnte nicht gelöscht werden!";
			      }
		    }
		
		    $folder = opendir($folder);
		    $uploads = apw_bots_get_uploads($folder);
		    closedir($folder);
           
        echo "<h1>Bot Uploads</h1>";
        echo "<table><tr><td>" . $status . "</td></tr><table><br />";

		    if (count($uploads) == 0)
		    {
			      echo "<table><tr><th>Du hast noch keinen Bot hochgeladen!</th></tr></table>";
		    }
		    else
		    {
			  ?>
            <table>
              <tr>
                <th width="150px" align="left">Herunterladen</th>
                <th>Löschen</th>
              </tr>
              <?php
                  foreach ($uploads as $index => $upload)
			            {
                      echo "<tr>";
				              echo "<th align='left'><a href='/wp-admin/admin-ajax.php?action=download_bot&file=".$upload."'>".$upload."</a></th>";
                      if ( get_option( 'apw_settings_upload_enabled' ) == 1 )
                      {
                          echo "<td align='center'><a href='".$_SERVER['REQUEST_URI']."&remove=".$upload."'>X</a></td>";
                      }
			                echo "</tr>";
                  }
              ?>
            </table>
        <?php
		    }
	  };

	  function apw_bots_folder_all()
	  {
		    $upload_dir = wp_upload_dir();
		    $upload_dir = $upload_dir['basedir'];

		    return $upload_dir."/bots/";
	  };

	  function apw_bots_folder()
	  {	
		    $upload_dir = apw_bots_folder_all();
		    $user = wp_get_current_user();
		    $user = $user->user_login;

		    $file = $upload_dir.$user;

		    if (!file_exists($file))
		    {
			      if (!mkdir($file))
			      {
				        echo "<h4>FEHLER! AKTUELL KÖNNEN KEINE BOTS HOCHGELADEN WERDEN!</h4>";
                echo "<h4>Bitte wende dich an das Wettbewerbs-Team!</h4>";
			      }
		    }
		    return $file;
	  };

	  function apw_bots_widget()
	  {		
		    $folder = apw_bots_folder();
		    $folder = opendir($folder);
		    $uploads = apw_bots_get_uploads($folder);

		    if (count($uploads) == 0)
		    {
			      $text = get_option('apw_settings_widget_text_none');
			      echo $text;
		    }
		    else
		    {
			      $text = get_option('apw_settings_widget_text');
			      $text = str_replace('%c', count($uploads), $text);
			      echo $text;
		    }
	  };
?>