<?php
	  /*
	  Plugin Name: AcaProgWett
	  Plugin URI:  http://wettbewerb.acagamics.de
	  Description: Plugin for the programming competition of OvGU and Acagamics e.V.
	  Version:     0.9
	  Author:      Max Klockmann
	  Author URI:  http://maximilian-klockmann.de
	  License:     GPL2
	  License URI: https://www.gnu.org/licenses/gpl-2.0.html
	  */

	  require('bots.php');
    require('bot upload.php');
	  require('settings.php');
	  require('user.php');
	  require('download.php');
    require('version_news.php');

	  defined( 'ABSPATH' ) or die( 'No script kiddies please!' );

	  add_action( 'admin_menu', 'apw_register_menus' );
	  add_action( 'wp_dashboard_setup', 'apw_register_widget' );
	  add_action( 'admin_init', 'apw_remove_dashboard_meta' );

	  function apw_remove_dashboard_meta()
	  {
          remove_meta_box( 'dashboard_incoming_links', 'dashboard', 'normal' );
          remove_meta_box( 'dashboard_plugins', 'dashboard', 'normal' );
          remove_meta_box( 'dashboard_primary', 'dashboard', 'side' );
          remove_meta_box( 'dashboard_secondary', 'dashboard', 'normal' );
          remove_meta_box( 'dashboard_quick_press', 'dashboard', 'side' );
          remove_meta_box( 'dashboard_recent_drafts', 'dashboard', 'side' );
          remove_meta_box( 'dashboard_recent_comments', 'dashboard', 'normal' );
          remove_meta_box( 'dashboard_right_now', 'dashboard', 'normal' );
          remove_meta_box( 'dashboard_activity', 'dashboard', 'normal');
	  }
	
	  function apw_register_widget()
	  {
        if (get_option( 'apw_settings_widget_show' ) == 1)
        {
		        wp_add_dashboard_widget(
			          'bot_widget',
			          'ProgWett',
			          'apw_bots_widget');
        }
	  }

	  function apw_register_menus()
	  {
		    add_menu_page(
			      'Bots', // Page-Title
			      'Bots', // Menu-Name
			      'read', // Capabilities
			      'bots', // Page
			      'apw_bots',
			      plugin_dir_url( __FILE__ ).'icon.png', // Menu-Icon
			      6 ); // Menu-Position

        if (get_option( 'apw_settings_upload_enabled' ) == 1)
        {
		        add_submenu_page(
			          'bots',
			          'Upload',
			          'Upload',
			          'read',
			          'upload',
			          'apw_bots_upload');
        }
      
        add_users_page(
            'Liste runterladen',
            'Liste runterladen',
            'manage_options',
            '/admin-ajax.php?action=download_user',
            '');

		    add_options_page(
			      'ProgWett', // Page-Title
			      'ProgWett', // Menu-Name
			      'manage_options', // Capabilities
			      'apw_settings', // Page
			      'apw_settings');
	  }
?>