package com.example.pebble_avideoplayer

import android.net.Uri
import android.net.Uri.parse
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.pebble_avideoplayer.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        checkPermissions()

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Spectre", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }

        settingUpMyVideoPlayer()
    }

    private fun checkPermissions() {

            val requestPermissionlauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->

                if (isGranted == true) {
                    d("kelogs", "Permission Granted.")
                    Snackbar.make(binding.root, "Permission Status : $isGranted ", Snackbar.LENGTH_LONG).show()
                } else {
                    d("kelogs", "Permission denied.")
                }
            }

//        Snackbar.make(binding.root, "Permission Status : $requestPermissionlauncher ", Snackbar.LENGTH_LONG).show()

    }

    private fun settingUpMyVideoPlayer() {
        val videoView = binding.VideoView

        //specifying the location of the file
        val videoFilePath: Uri =
            parse("android.resource://" + packageName + "/" + R.raw.scared_of_dark)
        val mediaController = MediaController(this)

        mediaController.setAnchorView(videoView)

        videoView.setMediaController(mediaController)
        videoView.setVideoURI(videoFilePath)
        videoView.requestFocus()
        videoView.start()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    
}