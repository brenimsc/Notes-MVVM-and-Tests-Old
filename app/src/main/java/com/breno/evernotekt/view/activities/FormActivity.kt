package com.breno.evernotekt.view.activities

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.breno.evernotekt.R
import com.breno.evernotekt.databinding.ActivityFormBinding
import com.breno.evernotekt.viewmodel.AddViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FormActivity : AppCompatActivity(), TextWatcher {

    private var toSave: Boolean = false
    private var noteId: Int? = null

    private val toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbarForm)
    }
    private val noteTitle by lazy {
        findViewById<TextView>(R.id.note_title)
    }
    private val noteEditor by lazy {
        findViewById<TextView>(R.id.note_editor)
    }

    private val viewModel by viewModel<AddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFormBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        noteId = intent.extras?.getInt("noteId")

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        noteId?.let {
            viewModel.getNote(it).observe(this) {
                it?.let {
                    displayNote(it.title.toString(), it.body.toString())
                }
            }
        }

        viewModel.saved.observe(this) {
            if (it) {
                finish()
            } else displayError("Titulo e nota devem ser informados")
        }
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)
        toggleToolbar(R.drawable.ic_arrow_back_black_24dp)

        noteTitle.addTextChangedListener(this)
        noteEditor.addTextChangedListener(this)
    }

    private fun toggleToolbar(@DrawableRes icon: Int) {
        supportActionBar?.let {
            it.title = null
            val upArrow = ContextCompat.getDrawable(this, icon)
            val colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_ATOP
                )
            upArrow?.colorFilter = colorFilter
            it.setHomeAsUpIndicator(upArrow)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }


    private fun displayError(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun displayNote(title: String, body: String) {
        noteTitle.text = title
        noteEditor.text = body
    }

    private fun returnToHome() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            return if (toSave && noteId == null) {
                saveNoteClicked()

                true
            } else {
                returnToHome()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNoteClicked() {
        viewModel.createNote()
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        toSave =
            if (noteEditor.text.toString().isEmpty() && noteTitle.text.toString().isEmpty()) {
                toggleToolbar(R.drawable.ic_arrow_back_black_24dp)
                false
            } else {
                toggleToolbar(R.drawable.ic_done_black_24dp)
                true
            }
    }

    override fun afterTextChanged(editable: Editable) {
    }

}