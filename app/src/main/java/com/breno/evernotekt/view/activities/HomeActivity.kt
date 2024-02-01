package com.breno.evernotekt.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.breno.evernotekt.R
import com.breno.evernotekt.data.model.Note
import com.breno.evernotekt.view.adapters.NoteAdapter
import com.breno.evernotekt.viewmodel.HomeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private val viewModel by viewModel<HomeViewModel>()

    private val toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbarAppBar)
    }
    private val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }
    private val navView by lazy {
        findViewById<NavigationView>(R.id.nav_view)
    }
    private val homeRecyclerView by lazy {
        findViewById<RecyclerView>(R.id.home_recycler_view)
    }
    private val fab by lazy {
        findViewById<FloatingActionButton>(R.id.fab)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        homeRecyclerView.addItemDecoration(divider)
        homeRecyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(baseContext, FormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllNotes().observe(this) {
            it?.let {
                displayNotes(it)
            } ?: displayError("Erro ao carregar notas")
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_all_notes) {
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displayError(message: String) {
        showToast(message)
    }

    private fun displayNotes(notes: List<Note>) {
        homeRecyclerView.adapter =
            NoteAdapter(notes) { note ->
                val intent = Intent(baseContext, FormActivity::class.java)
                intent.putExtra("noteId", note.id)
                startActivity(intent)
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}