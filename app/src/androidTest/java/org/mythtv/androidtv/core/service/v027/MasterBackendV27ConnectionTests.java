package org.mythtv.androidtv.core.service.v027;

import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.mythtv.androidtv.core.service.DvrService;
import org.mythtv.androidtv.core.service.v027.dvr.DvrServiceV27EventHandler;
import org.mythtv.androidtv.events.dvr.AllProgramsEvent;
import org.mythtv.androidtv.events.dvr.ProgramDetails;
import org.mythtv.androidtv.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.services.api.ApiVersion;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.ServerVersionQuery;

import java.util.List;

/**
 * Created by dmfrey on 11/26/14.
 */
@Ignore
public class MasterBackendV27ConnectionTests extends TestCase {

    String hostname = "192.168.10.200";
    int port = 6544;
    ApiVersion version = ApiVersion.v027;

    @Test
    public void testThatV027BackendIsConnected() throws Exception {

        ApiVersion apiVersion = ServerVersionQuery.getMythVersion( "http://" + hostname + ":" + port + "/" );
        assertEquals( ApiVersion.v027, apiVersion );

        MythTvApiContext mMythTvApiContext = MythTvApiContext.newBuilder().setHostName(hostname).setPort(port).setVersion(version).build();

        DvrService dvrService = new DvrServiceV27EventHandler( mMythTvApiContext );

        AllProgramsEvent event = dvrService.getRecordedPrograms(new RequestAllRecordedProgramsEvent(false, 0, -1, null, null, null));
        assertNotNull( event );
        assertTrue( event.isEntityFound() );

        List<ProgramDetails> programs = event.getDetails();
        assertFalse(programs.isEmpty());

    }

}
