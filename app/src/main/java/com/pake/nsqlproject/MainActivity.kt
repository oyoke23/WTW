package com.pake.nsqlproject

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.databinding.ActivityMainBinding
import com.pake.nsqlproject.model.ManageData
import com.pake.nsqlproject.ui.addlist.AddListFragment
import com.pake.nsqlproject.ui.comparelists.CompareListsDialogFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private val manageData : ManageData by lazy {
        ManageData(baseContext)
    }

    private lateinit var allData: AllData
    override fun onCreate(savedInstanceState: Bundle?) {
        //getData()
        setTheme(R.style.Theme_NSQLProject)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        allData = manageData.getData()!!
        binding.navView.getHeaderView(0).findViewById<android.widget.TextView>(R.id.tvUser).text = allData.personalInfo.name

        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeMenuItem -> {
                    if (findNavController(R.id.nav_host_fragment).currentDestination?.id == R.id.homeFragment) {
                        drawerLayout.closeDrawer(navigationView)
                    } else {
                        findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
                    }
                }
                R.id.compareListsMenuItem -> {
                    CompareListsDialogFragment().show(supportFragmentManager, "compareLists")
                }
                R.id.addBookMenuItem -> {
                    //abre el fragmento SearchBookFragment
                    findNavController(R.id.nav_host_fragment).navigate(R.id.searchBookFragment)
                    //AddBookFragment().show(supportFragmentManager,"addBook")
                }
                R.id.addListMenuItem -> {
                    AddListFragment().show(supportFragmentManager,"addList")
                }
                R.id.settingsMenuItem -> {
                    if (findNavController(R.id.nav_host_fragment).currentDestination?.id == R.id.settingsFragment) {
                        drawerLayout.closeDrawer(navigationView)
                    } else {
                        findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment)
                    }
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
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
/*

    private fun getData(){
        val sharedPreferences = baseContext.getSharedPreferences("data", Context.MODE_PRIVATE)
        val data = sharedPreferences.getString("data", "")
        println("NUESTRA DATA CONTIENE LOS SIGUIENTES DATOS: $data")
        if (data == ""){
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
    }*/
}
