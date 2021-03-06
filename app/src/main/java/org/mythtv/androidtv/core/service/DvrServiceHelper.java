package org.mythtv.androidtv.core.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.androidtv.core.MainApplication;
import org.mythtv.androidtv.core.domain.dvr.Program;
import org.mythtv.androidtv.events.dvr.AllProgramsEvent;
import org.mythtv.androidtv.events.dvr.ProgramDetails;
import org.mythtv.androidtv.events.dvr.RequestAllRecordedProgramsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dmfrey on 11/2/14.
 */
public class DvrServiceHelper {

    private static final String TAG = DvrServiceHelper.class.getSimpleName();

    public static final String ACTION_COMPLETE = "org.mythtv.androidtv.core.service.DvrServiceHelper.ACTION_COMPLETE";

    private final Context mContext;

    private DvrService mDvrService;

    private Map<String, List<Program>> mPrograms = new TreeMap<String, List<Program>>();
    private Map<String, String> mCategories = new TreeMap<String, String>();

    private List<Program> programs = new ArrayList<Program>();

    public DvrServiceHelper( final Context context ) {

        mContext = context;

        initializeClient();

        new ProgramsLoaderAsyncTask().execute();

    }

    public Map<String, List<Program>> getPrograms() {
        return mPrograms;
    }

    public Map<String, String> getCategories() {
        return mCategories;
    }

    private void preparePrograms() {

        for( Program program : programs ) {

            String cleanedTitle = program.getTitle(); //cleanArticles( program.getTitle() );
            if( !mPrograms.containsKey( cleanedTitle ) ) {

                List<Program> categoryPrograms = new ArrayList<Program>();
                categoryPrograms.add( program );
                mPrograms.put( cleanedTitle, categoryPrograms );

                mCategories.put( cleanedTitle, program.getTitle() );

            } else {

                mPrograms.get( cleanedTitle ).add( program );

            }

        }

    }

    private void initializeClient() {

        mDvrService = ( (MainApplication) mContext ).getDvrService();

    }

    private class ProgramsLoaderAsyncTask extends AsyncTask<Void, Void, AllProgramsEvent> {

        @Override
        protected AllProgramsEvent doInBackground( Void... params ) {

            return mDvrService.getRecordedPrograms( new RequestAllRecordedProgramsEvent( false, 1, -1, null, null, null ) );
        }

        @Override
        protected void onPostExecute( AllProgramsEvent event ) {
            Log.i( TAG, "onPostExecute : enter" );

            if( event.isEntityFound() ) {

                for( ProgramDetails program : event.getDetails() ) {

                    if( !"LiveTV".equals( program.getRecording().getStorageGroup() ) ) {
                        programs.add( Program.fromDetails( program ) );
                    }
                }

                preparePrograms();

            }

            Intent complete = new Intent( ACTION_COMPLETE );
            mContext.sendBroadcast( complete );

            Log.i( TAG, "onPostExecute : exit" );
        }

    }

}
