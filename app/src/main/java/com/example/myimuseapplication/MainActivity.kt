package com.example.myimuseapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.myimuseapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    companion object{
        lateinit var auth:FirebaseAuth
       lateinit var MusicListMA : ArrayList<Music>
    }



    //Creating member variables
    private var mFirebaseDatabase: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null

    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       if( requestRunTimePermission()) {
           MusicListMA = getAllAudio()
           setFragment(Home())
       }



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setTheme(R.style.cool_Nav)



        //for Nav Drawer
        toggle =
            ActionBarDrawerToggle(this@MainActivity, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setFragment(Home())



        binding.NavView.setOnClickListener{
            if(toggle.isDrawerSlideAnimationEnabled) {
                val em = updateData()
            }

       }




        binding.bottomNavView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home_btn -> setFragment(Home())
                R.id.favourite_btn -> setFragment(Favourites())
                R.id.playlists_btn -> setFragment(Playlists())
                R.id.search_btn -> setFragment(Search())

            }
            return@setOnItemSelectedListener true

        }

            val user = FirebaseAuth.getInstance().currentUser




        binding.NavView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.navSettings->Toast.makeText(this,"settings clicked",Toast.LENGTH_SHORT).show()
                R.id.navAbout->startActivity(Intent(this, AboutActivity::class.java))
                R.id.navFeedback->startActivity(Intent(this@MainActivity,FeedbackActivity::class.java))
                R.id.navSignOut->startActivity(Intent(this@MainActivity,LoginActivity::class.java))

            }
            return@setNavigationItemSelectedListener true
        }


        }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frame_layout, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return (true)
        return super.onOptionsItemSelected(item)

    }

    private fun updateData(): String {
        return "Email:${auth.currentUser?.email}"
    }

    //for requesting permission
    private fun requestRunTimePermission():Boolean{
        if (ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),15)
            return false
        }
        return true

    }

    @SuppressLint("Recycle", "Range")
    private fun getAllAudio(): ArrayList<Music>{

        val tempList =ArrayList<Music>()
        val selection= MediaStore.Audio.Media.IS_MUSIC +  " !=0"
        val projection= arrayOf(MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ALBUM_ID)

        val cursor = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,
        MediaStore.Audio.Media.DATE_ADDED + " DESC", null)



        if (cursor !=null) {
            do {
                val titleC =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val idC =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                val albumC =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val artistC =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val pathC =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val durationC =cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                val albumIdC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                val uri = Uri.parse("content://media/external/audio/albumart")
                val artUriC = Uri.withAppendedPath(uri, albumIdC).toString()


                val music = Music(id=idC, title = titleC, album = albumC, artist = artistC, path = pathC, duration = durationC , artUri = artUriC)
                val file = File(music.path)

                if (file.exists())
                    tempList.add(music)


            } while (cursor.moveToNext())
            cursor.close()
        }

         return tempList
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==15){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED )
                Toast.makeText(this,"Permission Granted !!",Toast.LENGTH_SHORT).show()



            else
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),15)


        }
    }

}







