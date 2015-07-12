<?php
	  add_action( 'show_user_profile', 'apw_show_matrikel' );
	  add_action( 'edit_user_profile', 'apw_show_matrikel' );

	  function apw_show_matrikel ($user)
	  {
		    $matrikel = esc_attr( get_user_meta( $user->ID, 'matrikel', true ) );
		    ?>
		        <h3>Weitere Informationen</h3>
		        <table class="form-table">
			        <tr>
				        <th><label for="matrikel">Matrikelnummer</label></th>
				        <td><input type="number" name="matrikel" id="matrikel" value="<?php echo $matrikel; ?>" class="regular-text" /><br /></td>
			        </tr>
		        </table>
		    <?php
	  }

	  add_action( 'personal_options_update', 'apw_save_matrikel' );
	  add_action( 'edit_user_profile_update', 'apw_save_matrikel' );

	  function apw_save_matrikel ($userid)
    {
		    if ( !current_user_can( 'edit_user', $userid ) )
			      return false;

		    update_usermeta( $userid, 'matrikel', $_POST['matrikel'] );
	  }

	  add_action( 'register_form', 'apw_register_form');

	  function apw_register_form ()
	  {
		    if (!($first = $_POST['first_name'])) $first = "";
		    if (!($last = $_POST['last_name'])) $last = "";
		    if (!($matrikel = $_POST['matrikel'])) $matrikel = "";

		    $first = esc_attr(stripslashes($first));
		    $last = esc_attr(stripslashes($last));
		    $matrikel = esc_attr(stripslashes($matrikel));

		    echo "<p><label for='first_name'>Vorname<br /><input type='text' name='first_name' id='first_name' class='input' value='".$first."' size='25' /></label>";
		    echo "<p><label for='last_name'>Nachname<br /><input type='text' name='last_name' id='last_name' class='input' value='".$last."' size='25' /></label>";
		    echo "<p><label for='matrikel'>Matrikelnummer<br /><input type='number' name='matrikel' id='matrikel' class='input' value='".$matrikel."' size='25' /></label>";
	  }

	  add_filter( 'registration_errors', 'apw_register_errors', 10, 3);

	  function apw_register_errors ($errors, $username, $usermail)
	  {
		    if ( empty( $_POST['first_name'] ) )
			      $errors->add( 'first_name_error', '<strong>Fehler:</strong> Bitte gib deinen Vornamen ein.' );
		    if ( empty( $_POST['last_name'] ) )
			      $errors->add( 'last_name_error', '<strong>Fehler:</strong> Bitte gib deinen Nachnamen ein.' );
		    if ( empty( $_POST['matrikel'] ) )
			      $errors->add( 'matrikel_error', '<strong>Fehler:</strong> Bitte gib deine Matrikelnummer ein.' );

		    return $errors;
	  }

	  add_action('user_register', 'apw_register_user');

	  function apw_register_user ($userid)
	  {
		    if ( isset( $_POST['first_name'] ) )
			      update_user_meta($userid, 'first_name', $_POST['first_name']);
		    if ( isset( $_POST['last_name'] ) )
			      update_user_meta($userid, 'last_name', $_POST['last_name']);
		    if ( isset( $_POST['matrikel'] ) )
			      update_user_meta($userid, 'matrikel', $_POST['matrikel']);
	  }
?>