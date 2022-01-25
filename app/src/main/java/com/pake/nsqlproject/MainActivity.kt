package com.pake.nsqlproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.pake.nsqlproject.databinding.ActivityMainBinding
import com.pake.nsqlproject.ui.addbook.AddBookFragment
import com.pake.nsqlproject.ui.addlist.AddListFragment


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Home"

        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
                    supportActionBar?.title = "Home"
                }
                R.id.addBookFragment -> {
                    var dialog = AddBookFragment()
                    dialog.show(supportFragmentManager,"addBook")
                    //findNavController(R.id.nav_host_fragment).navigate(R.id.addBookFragment)
                    //supportActionBar?.title = "Add Book"
                }
                R.id.addListFragment -> {
                    var dialog = AddListFragment()
                    dialog.show(supportFragmentManager,"addList")
                    //findNavController(R.id.nav_host_fragment).navigate(R.id.addListFragment2)
                    //supportActionBar?.title = "Add List"
                }
                R.id.settingsFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment)
                    supportActionBar?.title = "Settings"
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    /*override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)

    }
}