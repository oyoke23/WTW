package com.pake.nsqlproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.pake.nsqlproject.databinding.ActivityMainBinding
import com.pake.nsqlproject.ui.addbook.AddBookFragment
import com.pake.nsqlproject.ui.addlist.AddListFragment


class MainActivity : AppCompatActivity() {
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
                R.id.homeMenuItem -> {
                    if (findNavController(R.id.nav_host_fragment).currentDestination?.id == R.id.homeFragment) {
                        drawerLayout.closeDrawer(navigationView)
                    } else {
                        findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
                        supportActionBar?.title = "Home"
                    }
                }
                R.id.compareListsMenuItem -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.compareListsFragment)
                    supportActionBar?.title = "Compare Lists"
                }
                R.id.addBookMenuItem -> {
                    var dialog = AddBookFragment()
                    dialog.show(supportFragmentManager,"addBook")
                }
                R.id.addListMenuItem -> {
                    var dialog = AddListFragment()
                    dialog.show(supportFragmentManager,"addList")
                }
                R.id.settingsMenuItem -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment)
                    supportActionBar?.title = "Settings"
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}