package messager.messager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.glassfish.hk2.api.Self;

import dto.InsertRecordDto;
import dto.MusicListResponse;
import dto.MusicListResponseList;
import dto.MusicUpdateDto;
import dto.ResponseList;
import dto.TempResponse;
import dto.YoutubeRequest;
import extras.MediaStreamer;
import impl.SelfImpl;


@Path("myClass")
public class SelfController {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public TempResponse controller1(){
//		ResponseList rl = SelfImpl.makeResponseList();
//		return rl;
		System.out.println("yay");
		
		TempResponse tr = SelfImpl.getTempResponse();
		return tr;
	}
	
	@GET
	@Path("ya")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseList controller2(){
		ResponseList rl = SelfImpl.makeResponseList();
		return rl;
//		TempResponse tr = SelfImpl.getTempResponse();
//		return tr;
	} 
	
	@POST
	@Path("youtube")
	@Consumes(MediaType.APPLICATION_JSON)
	public String youtubeDownload(YoutubeRequest yr){
		SelfImpl.startDownload(yr);
		return "wrking";
	}
	
	@GET
	@Path("cam")
	public String cam(){
		return SelfImpl.getCamImageBase64Data();
	}
	
	
	@POST
	@Path("insertRecord")
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertRecord(InsertRecordDto insertRecordDto){
		
		return SelfImpl.createNewRecord(insertRecordDto);
	}
	
	
    @GET
	@Path("getNameById")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFileNameById(@HeaderParam("Range") String range, @QueryParam("id") Integer id) throws Exception {
        String name =  SelfImpl.getFileNameById(id);
        return name;
    }
	
	@POST
	@Path("checkForUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MusicListResponseList checkForUpdate(MusicUpdateDto musicUpdateDto){
		
		return SelfImpl.checkForUpdates(musicUpdateDto);
	}

    @GET
	@Path("getFileById")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response streamAudio(@HeaderParam("Range") String range, @QueryParam("id") Integer id) throws Exception {
        return buildStream(SelfImpl.getMusicPathById(id), range);
    }

    private Response buildStream(final File asset, final String range) throws Exception {
        // range not requested : Firefox, Opera, IE do not send range headers
        if (range == null) {
            StreamingOutput streamer = new StreamingOutput() {
                @Override
                public void write(final OutputStream output) throws IOException, WebApplicationException {

                    final FileChannel inputChannel = new FileInputStream(asset).getChannel();
                    final WritableByteChannel outputChannel = Channels.newChannel(output);
                    try {
                        inputChannel.transferTo(0, inputChannel.size(), outputChannel);
                    } finally {
                        // closing the channels
                        inputChannel.close();
                        outputChannel.close();
                    }
                }

            };
            return Response.ok(streamer).header(HttpHeaders.CONTENT_LENGTH, asset.length()).build();
        }

        final int chunk_size = 1024 * 1024;
        String[] ranges = range.split("=")[1].split("-");
        final int from = Integer.parseInt(ranges[0]);
        /**
         * Chunk media if the range upper bound is unspecified. Chrome sends "bytes=0-"
         */
        int to = chunk_size + from;
        if (to >= asset.length()) {
            to = (int) (asset.length() - 1);
        }
        if (ranges.length == 2) {
            to = Integer.parseInt(ranges[1]);
        }

        final String responseRange = String.format("bytes %d-%d/%d", from, to, asset.length());
        final RandomAccessFile raf = new RandomAccessFile(asset, "r");
        raf.seek(from);

        final int len = to - from + 1;
        final MediaStreamer streamer = new MediaStreamer(len, raf);
        Response.ResponseBuilder res = Response.status(Status.PARTIAL_CONTENT).entity(streamer)
                .header("Accept-Ranges", "bytes")
                .header("Content-Range", responseRange)
                .header(HttpHeaders.CONTENT_LENGTH, streamer.getLenth())
                .header(HttpHeaders.LAST_MODIFIED, new Date(asset.lastModified()));
        return res.build();
    }
	
}
