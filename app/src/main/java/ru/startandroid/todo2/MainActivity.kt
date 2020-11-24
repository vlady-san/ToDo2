package ru.startandroid.todo2

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener{

    private var mAppBarConfiguration : AppBarConfiguration? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.app_name, R.string.next
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private var date = Calendar.getInstance()
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //var date : Calendar=Calendar.getInstance()
        val id = item.itemId

        if(id == R.id.all_task)
        {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_fragment_first_self)
        }
        else if (id == R.id.today) {
            date = Calendar.getInstance()
            sortedByDateFragment()
        }
        else if (id == R.id.tomorrow) {
            date = Calendar.getInstance()
            date.add(Calendar.DAY_OF_MONTH,1);
            sortedByDateFragment()
        }
        else if (id == R.id.upcoming) {
            DatePickerDialog(
                this, this,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        date[Calendar.YEAR] = p1
        date[Calendar.MONTH] = p2
        date[Calendar.DAY_OF_MONTH] = p3
        sortedByDateFragment()
    }

    private fun sortedByDateFragment(){
        var bundle = Bundle()
        val dateFormat = SimpleDateFormat("dd/MM/yy")
        System.out.println(dateFormat.format(date.time))
        bundle.putString("date",dateFormat.format(date.time))
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_fragment_first_self, bundle)
    }
}