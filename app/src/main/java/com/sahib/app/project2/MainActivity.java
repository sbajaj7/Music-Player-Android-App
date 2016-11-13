package com.sahib.app.project2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<Song> mySong = new ArrayList<Song>();

    private ArrayList<Uri> mUriList = new ArrayList<Uri>();

    private ArrayList<Uri> songUriList = new ArrayList<Uri>();

    private ArrayList<Uri> artistUriList = new ArrayList<Uri>();

    ListView list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflating the main layout (from the res folder)
        setContentView(R.layout.activity_main);

        populateSongList();
        populateListView();
        registerClickCallback();

        //Initializing the youtube url's of the songs
        initializeSongUris() ;

        //Initializing the Wikipedia url's of the songs
        initializeWikiSongUris() ;

        //Initializing the Wikipedia url's of the Artists/Bands
        initializeWikiArtistUris() ;

        //Code for directly registering Context Menu without implementing OnItemLongClickListener.
        //ListView list = (ListView) findViewById(R.id.songListView);
        //registerForContextMenu(list);

        list = (ListView) findViewById(R.id.songListView);

        list.setLongClickable(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> ad, View v, int pos, long id) {

                registerForContextMenu(MainActivity.this.list);
                return false;
            }
        });

        Log.i("MainActivity", "onCreate method completed");

    }

    //Filling up the array list containing songs using the constructor of Song class.
    private void populateSongList() {
        mySong.add(new Song("All of me", "John Legend", R.drawable.allofmecrop));
        mySong.add(new Song("The Reason", "HoobaStank", R.drawable.hoobastankcrop));
        mySong.add(new Song("Fix You", "ColdPlay", R.drawable.fixyoucrop));
        mySong.add(new Song("21 Guns", "Green Day", R.drawable.gunscrop));
        mySong.add(new Song("Blank Space", "Taylor Swift", R.drawable.blankspacecrop));
        mySong.add(new Song("Hello", "Adele", R.drawable.hellocrop));
        mySong.add(new Song("Last Friday Night", "Katy Perry", R.drawable.lastfridaynightcrop));

        Log.i("MainActivity", "List of Songs populated.");
    }

    //Populating the ListView.
    private void populateListView() {
        ArrayAdapter<Song> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.songListView);
        list.setAdapter(adapter);
    }

    //This method is called when an item in the list view is clicked.
    //When an item in the list view is clicked it opens the browser with the you tube song selected.
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.songListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                //Song clickedSong = mySong.get(position);

                Uri aUri = mUriList.get(position);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(aUri);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                startActivity(intent);

                //Following Commented piece of code is for displaying a Toast message on the screen
                /*String message = "You clicked position " + position
                        + " Which is the Song " + clickedSong.getTitle();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();*/
            }
        });
    }

    //Custom Adapter extending ArrayAdapter.
    private class MyListAdapter extends ArrayAdapter<Song> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, mySong);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (Re-cycling View)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            // Find the reference of the Song clicked.
            Song currentSong = mySong.get(position);

            // Fill the view with the image
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentSong.getIconID());

            // Fill the List View with the Title:
            TextView makeText = (TextView) itemView.findViewById(R.id.item_title);
            makeText.setText(currentSong.getTitle());

            // Fill the List View with the Artist/Band:
            TextView yearText = (TextView) itemView.findViewById(R.id.item_artist);
            yearText.setText("" + currentSong.getArtist());

            return itemView;
        }
    }


    //Method definition for initializing the url's of you tube links
    private void initializeSongUris() {

        mUriList.add(Uri.parse(getString(R.string.allofme))) ;
        mUriList.add(Uri.parse(getString(R.string.thereason))) ;
        mUriList.add(Uri.parse(getString(R.string.fixyou))) ;

        mUriList.add(Uri.parse(getString(R.string.guns))) ;
        mUriList.add(Uri.parse(getString(R.string.blankspace))) ;
        mUriList.add(Uri.parse(getString(R.string.hello))) ;
        mUriList.add(Uri.parse(getString(R.string.lastfridaynight))) ;
    }

    //Method definition for initializing the url's of Wikipedia links for songs.
    private void initializeWikiSongUris(){

        songUriList.add(Uri.parse(getString(R.string.allofmewiki))) ;
        songUriList.add(Uri.parse(getString(R.string.thereasonwiki))) ;
        songUriList.add(Uri.parse(getString(R.string.fixyouwiki))) ;

        songUriList.add(Uri.parse(getString(R.string.gunswiki))) ;
        songUriList.add(Uri.parse(getString(R.string.blankspacewiki))) ;
        songUriList.add(Uri.parse(getString(R.string.hellowiki))) ;
        songUriList.add(Uri.parse(getString(R.string.lastfridaynightwiki))) ;

    }

    //Method definition for initializing the url's of Wikipedia links for Artists/Bands.
    private void initializeWikiArtistUris(){

        artistUriList.add(Uri.parse(getString(R.string.legendwiki))) ;
        artistUriList.add(Uri.parse(getString(R.string.hoobastankwiki))) ;
        artistUriList.add(Uri.parse(getString(R.string.coldplaywiki))) ;

        artistUriList.add(Uri.parse(getString(R.string.greendaywiki))) ;
        artistUriList.add(Uri.parse(getString(R.string.swiftwiki))) ;
        artistUriList.add(Uri.parse(getString(R.string.adelewiki))) ;
        artistUriList.add(Uri.parse(getString(R.string.perrywiki))) ;

    }


    //This method is called when an item in the list view is long pressed/clicked.
    //This method displays the context menu with 3 menu options.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //System.out.println("Item Long Clicked");

        menu.add(R.string.viewclip);
        menu.add(R.string.songwiki);
        menu.add(R.string.artistwiki);
    }

    //This method is called when one out of the 3 options are selected from the context menu after long pressing a list view item.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //Toast.makeText(this, "Longpress: " + info.position, Toast.LENGTH_SHORT).show();

        if(item.getTitle().equals("View the clip")){

            //System.out.println("View the clip");

            Uri aUri = mUriList.get(info.position);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(aUri);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(intent);
        }

        if(item.getTitle().equals("View Wikipedia page of the Song")){

            //System.out.println("View the clip");

            Uri aUri = songUriList.get(info.position);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(aUri);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(intent);
        }

        if(item.getTitle().equals("View Wikipedia page of the Artist")){

            //System.out.println("View the clip");

            Uri aUri = artistUriList.get(info.position);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(aUri);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(intent);
        }
        return true;
    }


}
