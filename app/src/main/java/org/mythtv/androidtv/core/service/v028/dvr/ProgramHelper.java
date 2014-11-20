package org.mythtv.androidtv.core.service.v028.dvr;

import org.mythtv.androidtv.events.dvr.ArtworkInfoDetails;
import org.mythtv.androidtv.events.dvr.CastMemberDetails;
import org.mythtv.androidtv.events.dvr.ProgramDetails;
import org.mythtv.services.api.v028.beans.ArtworkInfo;
import org.mythtv.services.api.v028.beans.ArtworkInfoList;
import org.mythtv.services.api.v028.beans.CastMember;
import org.mythtv.services.api.v028.beans.CastMemberList;
import org.mythtv.services.api.v028.beans.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/15/14.
 */
public class ProgramHelper {

    public static ProgramDetails toDetails( Program program ) {

        ProgramDetails details = new ProgramDetails();
        details.setStartTime( program.getStartTime() );
        details.setEndTime( program.getEndTime() );
        details.setTitle( program.getTitle() );
        details.setSubTitle( program.getSubTitle() );
        details.setCategory( program.getCategory() );
        details.setCatType( program.getCatType() );
        details.setRepeat( program.isRepeat() );
        details.setVideoProps( program.getVideoProps() );
        details.setAudioProps( program.getAudioProps() );
        details.setSubProps( program.getSubProps() );
        details.setSeriesId( program.getSeriesId() );
        details.setProgramId( program.getProgramId() );
        details.setStars( program.getStars() );
        details.setFileSize( program.getFileSize() );
        details.setLastModified( program.getLastModified() );
        details.setProgramFlags( program.getProgramFlags() );
        details.setFileName( program.getFileName() );
        details.setHostName( program.getHostName() );
        details.setAirdate( program.getAirdate() );
        details.setDescription( program.getDescription() );
        details.setInetref( program.getInetref() );
        details.setSeason( program.getSeason() );
        details.setEpisode( program.getEpisode() );
        details.setTotalEpisodes( -1 );

        if( null != program.getChannel() ) {
            details.setChannel( ChannelInfoHelper.toDetails(program.getChannel()) );
        }

        if( null != program.getRecording() ) {
            details.setRecording( RecordingInfoHelper.toDetails(program.getRecording()) );
        }

        List<ArtworkInfoDetails> artworkInfoDetails = new ArrayList<ArtworkInfoDetails>();
        if( null != program.getArtwork() && null != program.getArtwork().getArtworkInfos() && program.getArtwork().getArtworkInfos().length > 0 ) {
            for( ArtworkInfo artworkInfo : program.getArtwork().getArtworkInfos() ) {
                artworkInfoDetails.add( ArtworkInfoHelper.toDetails(artworkInfo) );
            }
        }
        details.setArtworkInfos( artworkInfoDetails );

        List<CastMemberDetails> castMemberDetails = new ArrayList<CastMemberDetails>();
        if( null != program.getCast() && null != program.getCast().getCastMembers() && program.getCast().getCastMembers().length > 0 ) {
            for( CastMember castMember : program.getCast().getCastMembers() ) {
                castMemberDetails.add(CastMemberHelper.toDetails( castMember ) );
            }
        }
        details.setCastMembers( castMemberDetails );

        return details;
    }

    public static Program fromDetails( ProgramDetails details ) {

        Program program = new Program();
        program.setStartTime( details.getStartTime() );
        program.setEndTime( details.getEndTime() );
        program.setTitle( details.getTitle() );
        program.setSubTitle( details.getSubTitle() );
        program.setCategory( details.getCategory() );
        program.setCatType( details.getCatType() );
        program.setRepeat( details.getRepeat() );
        program.setVideoProps( details.getVideoProps() );
        program.setAudioProps( details.getAudioProps() );
        program.setSubProps( details.getSubProps() );
        program.setSeriesId( details.getSeriesId() );
        program.setProgramId( details.getProgramId() );
        program.setStars( details.getStars() );
        program.setFileSize( details.getFileSize() );
        program.setLastModified( details.getLastModified() );
        program.setProgramFlags( details.getProgramFlags() );
        program.setFileName( details.getFileName() );
        program.setHostName( details.getHostName() );
        program.setAirdate( details.getAirdate() );
        program.setDescription( details.getDescription() );
        program.setInetref( details.getInetref() );
        program.setSeason( details.getSeason() );
        program.setEpisode( details.getEpisode() );

        if( null != details.getChannel() ) {
            program.setChannel( ChannelInfoHelper.fromDetails(details.getChannel()) );
        }

        if( null != details.getRecording() ) {
            program.setRecording( RecordingInfoHelper.fromDetails(details.getRecording()) );
        }

        List<ArtworkInfo> artworkInfos = new ArrayList<ArtworkInfo>();
        if( null != details.getArtworkInfos() && !details.getArtworkInfos().isEmpty() ) {
            for( ArtworkInfoDetails detail : details.getArtworkInfos() ) {
                artworkInfos.add( ArtworkInfoHelper.fromDetails(detail) );
            }
        }
        ArtworkInfoList artworkInfoList = new ArtworkInfoList();
        artworkInfoList.setArtworkInfos( artworkInfos.toArray( new ArtworkInfo[ artworkInfos.size() ] ) );
        program.setArtwork(artworkInfoList);

        List<CastMember> castMembers = new ArrayList<CastMember>();
        if( null != details.getCastMembers() && !details.getCastMembers().isEmpty() ) {
            for( CastMemberDetails detail : details.getCastMembers() ) {
                castMembers.add( CastMemberHelper.fromDetails( detail ) );
            }
        }
        CastMemberList castMemberList = new CastMemberList();
        castMemberList.setCastMembers( castMembers.toArray( new CastMember[ castMembers.size() ] ) );
        program.setCast( castMemberList );

        return program;
    }

}
