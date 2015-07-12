<?php
	  add_action( 'admin_init', 'apw_settings_init' );

	  function apw_settings_init()
	  {
		    add_settings_section(
			      'apw_settings_section_widget',
			      'Widget',
			      'apw_settings_section_widget',
			      'apw_settings');	
		    add_settings_field(
			      'apw_settings_widget_show',
			      'Widget anzeigen',
			      'apw_settings_widget_show',
			      'apw_settings',
			      'apw_settings_section_widget');           
		    add_settings_field(
			      'apw_settings_widget_text',
			      'Text (Bots hochgeladen)',
			      'apw_settings_widget_text',
			      'apw_settings',
			      'apw_settings_section_widget');
		    add_settings_field(
			      'apw_settings_widget_text_none',
			      'Text (nichts hochgeladen)',
			      'apw_settings_widget_text_none',
			      'apw_settings',
			      'apw_settings_section_widget');

		    add_settings_section(
			      'apw_settings_section_upload',
			      'Upload',
			      'apw_settings_section_upload',
			      'apw_settings');
		    add_settings_field(
			      'apw_settings_upload_enabled',
			      'Upload aktiviert',
			      'apw_settings_upload_enabled',
			      'apw_settings',
			      'apw_settings_section_upload');
		    add_settings_field(
			      'apw_settings_upload_allowed',
			      'Erlaubte Typen',
			      'apw_settings_upload_allowed',
			      'apw_settings',
			      'apw_settings_section_upload');
		    add_settings_field(
			      'apw_settings_upload_max_bots',
			      'Max. Anzahl Bots',
			      'apw_settings_upload_max_bots',
			      'apw_settings',
			      'apw_settings_section_upload');
		    add_settings_field(
			      'apw_settings_upload_bot_size',
			      'Max. Upload größe',
			      'apw_settings_upload_bot_size',
			      'apw_settings',
			      'apw_settings_section_upload');
    
        add_settings_section(
			      'apw_settings_section_java',
			      'Java Programm',
			      'apw_settings_section_java',
			      'apw_settings');
		    add_settings_field(
			      'apw_settings_java_version',
			      'Version',
			      'apw_settings_java_version',
			      'apw_settings',
			      'apw_settings_section_java');
		    add_settings_field(
			      'apw_settings_java_news',
			      'Neuigkeiten',
			      'apw_settings_java_news',
			      'apw_settings',
			      'apw_settings_section_java');
    
		    add_settings_section(
			      'apw_settings_section_output',
			      'Ausgaben',
			      'apw_settings_section_output',
			      'apw_settings');
		    add_settings_field(
			      'apw_settings_text_upload_exists',
			      'Datei vorhanden',
			      'apw_settings_text_upload_exists',
			      'apw_settings',
			      'apw_settings_section_output');
		    add_settings_field(
			      'apw_settings_text_upload_allowed',
			      'Falscher Datentyp',
			      'apw_settings_text_upload_allowed',
			      'apw_settings',
			      'apw_settings_section_output');
		    add_settings_field(
			      'apw_settings_text_upload_maximum',
			      'Maximale Anzahl',
			      'apw_settings_text_upload_maximum',
			      'apw_settings',
			      'apw_settings_section_output');
		    add_settings_field(
			      'apw_settings_text_upload_size',
			      'Maximale Dateigröße',
			      'apw_settings_text_upload_size',
			      'apw_settings',
			      'apw_settings_section_output');

		    register_setting( 'apw_settings', 'apw_settings_widget_text' );
		    register_setting( 'apw_settings', 'apw_settings_widget_text_none' );
        register_setting( 'apw_settings', 'apw_settings_widget_show' );

        register_setting( 'apw_settings', 'apw_settings_upload_enabled' );
		    register_setting( 'apw_settings', 'apw_settings_upload_allowed' );
        register_setting( 'apw_settings', 'apw_settings_upload_max_bots' );
        register_setting( 'apw_settings', 'apw_settings_upload_bot_size' );
    
		    register_setting( 'apw_settings', 'apw_settings_java_version' );
        register_setting( 'apw_settings', 'apw_settings_java_news' );
      
		    register_setting( 'apw_settings', 'apw_settings_text_upload_allowed' );
		    register_setting( 'apw_settings', 'apw_settings_text_upload_exists' );
		    register_setting( 'apw_settings', 'apw_settings_text_upload_maximum' );
        register_setting( 'apw_settings', 'apw_settings_text_upload_size' );
	  };

	  function apw_settings()
	  {
		    echo "<h1>ProgWett Einstellungen</h1>";
		    echo "<form method='post' action='options.php'>";
		    settings_fields( 'apw_settings' );
		    do_settings_sections('apw_settings');
		    submit_button();
		    echo "</form>";
	  }

	  function apw_settings_section_widget()
	  {
		    echo "Hier kann das Widget auf der Startseite konfiguriert werden!";
	  }

	  function apw_settings_section_upload()
	  {
		    echo "Hier können die Bedingungen vom Upload konfiguriert werden!";
	  }
  
	  function apw_settings_section_output()
	  {
		    echo "Hier können die Texte für verschiedene Ausgaben konfiguriert werden!";
	  }

	  function apw_settings_widget_text()
	  {
		    echo "<textarea style='width: 300px; height: 100px' name='apw_settings_widget_text' id='apw_settings_widget_text' class='code'>".get_option('apw_settings_widget_text')."</textarea>";
		    echo "<p><code>%c</code> -> Anzahl Bots</p>";
	  }

	  function apw_settings_widget_text_none()
	  {
		    echo "<textarea style='width: 300px; height: 100px' name='apw_settings_widget_text_none' id='apw_settings_widget_text_none' class='code'>".get_option('apw_settings_widget_text_none')."</textarea>";
	  }
  
    function apw_settings_widget_show()
    {
        echo "<input name='apw_settings_widget_show' id='apw_settings_widget_show' type='checkbox' value='1' class='code' " . checked( 1, get_option( 'apw_settings_widget_show' ), false ) . " />";
    }

    function apw_settings_upload_enabled()
    {
        echo "<input name='apw_settings_upload_enabled' id='apw_settings_upload_enabled' type='checkbox' value='1' class='code' " . checked( 1, get_option( 'apw_settings_upload_enabled' ), false ) . " />";
    }
  
	  function apw_settings_upload_allowed()
	  {
		    echo "<input style='width: 300px;' type='text' name='apw_settings_upload_allowed' id='apw_settings_upload_allowed' class='code' value='".get_option('apw_settings_upload_allowed')."' />";
		    echo "<p>Mehrere Typen mit Leerzeichen trennen. Z.B. <code>.zip .7z</code></p>";
	  }
  
    function apw_settings_upload_max_bots()
    {
        echo "<input style='width: 300px;' type='number' name='apw_settings_upload_max_bots' id='apw_settings_upload_max_bots' class='code' value='".get_option('apw_settings_upload_max_bots')."' />";
    }
  
    function apw_settings_upload_bot_size()
    {
        echo "<input style='width: 300px;' type='number' name='apw_settings_upload_bot_size' id='apw_settings_upload_bot_size' class='code' value='".get_option('apw_settings_upload_bot_size')."' />";
        echo "<p>In Bytes</p>";
    }
  
    function apw_settings_java_version()
    {
        echo "<input style='width: 300px;' type='text' name='apw_settings_java_version' id='apw_settings_java_version' class='code' value='".get_option('apw_settings_java_version')."' />";
    }
  
	  function apw_settings_java_news()
	  {
		    echo "<textarea style='width: 300px; height: 100px' name='apw_settings_java_news' id='apw_settings_java_news' class='code'>".get_option('apw_settings_java_news')."</textarea>";
	  }
  
	  function apw_settings_text_upload_allowed()
	  {
		    echo "<textarea style='width: 300px; height: 100px' name='apw_settings_text_upload_allowed' id='apw_settings_text_upload_allowed' class='code'>".get_option('apw_settings_text_upload_allowed')."</textarea>";
		    echo "<p><code>%a</code> -> Erlaubte Datentypen</p>";
	  }
	
	  function apw_settings_text_upload_exists()
	  {
		    echo "<textarea style='width: 300px; height: 100px' name='apw_settings_text_upload_exists' id='apw_settings_text_upload_exists' class='code'>".get_option('apw_settings_text_upload_exists')."</textarea>";
		    echo "<p><code>%f</code> -> Dateiname</p>";
	  }
  
    function apw_settings_text_upload_maximum()
    {
        echo "<textarea style='width: 300px; height: 100px' name='apw_settings_text_upload_maximum' id='apw_settings_text_upload_maximum' class='code'>".get_option('apw_settings_text_upload_maximum')."</textarea>";
		    echo "<p><code>%c</code> -> Anzahl Bots</p>";
    }
  
    function apw_settings_text_upload_size()
    {
        echo "<textarea style='width: 300px; height: 100px' name='apw_settings_text_upload_size' id='apw_settings_text_upload_size' class='code'>".get_option('apw_settings_text_upload_size')."</textarea>";
        echo "<p><code>%f</code> -> Dateiname</p>";
        echo "<p><code>%s</code> -> Dateigröße</p>";
        echo "<p><code>%m</code> -> Maximale Dateigröße</p>";
    }
?>