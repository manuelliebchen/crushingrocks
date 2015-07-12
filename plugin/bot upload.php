<?php
	function apw_bots_get_uploads($folder)
	{
		$uploads = array();
		while (false !== ($entry = readdir($folder)))
		{
			if ($entry != "." && $entry != "..")
			{
				$uploads[] = $entry;
			}
		}

		return $uploads;
	};

	function apw_bots_is_allowed_upload($uploadfile)
	{
		$list = get_option('apw_settings_upload_allowed');
		$allowed = explode(" ", $list);
		$result = false;

		foreach ($allowed as $file)
		{
			if (strripos($uploadfile, $file) == (strlen($uploadfile) - strlen($file)))
				$result = true;
		}
		return $result;
	};

	function apw_bots_upload()
	{
		$status = "";

		if ( get_option( 'apw_settings_upload_enabled' ) == 0 )
		{
			$status = "Kein Upload möglich!";
			return;
		}

		$folder = apw_bots_folder();
		$folder = opendir($folder);
		$uploads = apw_bots_get_uploads($folder);

		if ( count( $uploads ) >= intval( get_option('apw_settings_upload_max_bots') ) )
		{
			$text = get_option('apw_settings_text_upload_maximum');
			$text = str_replace('%c', count( $uploads ), $text);
			$status = $text;
			return;
		}

		if ( isset( $_FILES['bot'] ) && !empty( $_FILES['bot']['name'] ) )
		{
			$uploaddir = apw_bots_folder()."/";
			$uploadfile = $uploaddir . basename($_FILES['bot']['name']);

			$abort = false;

			if ( $_FILES['bot']['size'] > intval( get_option('apw_settings_upload_bot_size') ) )
			{
				$text = get_option('apw_settings_text_upload_size');
				$text = str_replace('%f', $_FILES['bot']['name'], $text);
				$text = str_replace('%s', ($_FILES['bot']['size'] / 1000) . "kB", $text);
				$text = str_replace('%m', (get_option('apw_settings_upload_bot_size') / 1000) . "kB", $text);
				$status = $text;
			}
			else if (file_exists($uploadfile))
			{
				$text = get_option('apw_settings_text_upload_exists');
				$text = str_replace('%f', $_FILES['bot']['name'], $text);
				$status = $text;
			}
			else if (!apw_bots_is_allowed_upload($uploadfile))
			{
				$text = get_option('apw_settings_text_upload_allowed');
				$text = str_replace('%a', get_option('apw_settings_upload_allowed'), $text);
				$status = $text;
			}
			else if (move_uploaded_file($_FILES['bot']['tmp_name'], $uploadfile))
			{
				$status = "Dein Bot wurde hochgeladen!";
				$abort = true;
			}
			else
			{
				$status = "Beim Upload ist ein fehler aufgetreten!";
			}

			
		}
		
		if ($abort)
		{
			echo "<h1>Bot Upload</h1>";
			echo "<table><tr><td>" . $status . "</td></tr><table>";
			return;
		}

		?>
		<h1>Bot Upload</h1>
		<form enctype="multipart/form-data" action="<?php echo $_SERVER['REQUEST_URI']; ?>" method="POST">
		  <table>
		    <tr>
			  <td><?php echo $status; if ($status != "") echo "<br /><br />"; ?></td>
		    </tr>
			<tr>
			  <th align='left'>Wähle einen Bot aus:</th>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
			  <td><input name="bot" id="bot" type="file" /></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
			  <td><input style="width: 120px" type="submit" value="Hochladen" /></td>
			</tr>
		  </table>	
		</form>
		<?php
	};
?>