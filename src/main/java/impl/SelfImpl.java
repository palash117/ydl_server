package impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Dao;
import dto.InsertRecordDto;
import dto.MusicListResponse;
import dto.MusicListResponseList;
import dto.MusicUpdateDto;
import dto.Response;
import dto.ResponseList;
import dto.TempResponse;
import dto.YoutubeRequest;
import extras.CamBase64Data;
public class SelfImpl {

	private static Dao daoObj = new Dao();

	public static ResponseList makeResponseList() {
		Response response = new Response();
		response.setTime(String.valueOf(System.currentTimeMillis()));
		ResponseList rl = new ResponseList(response);
		return rl;
	}

	public static TempResponse getTempResponse() {
		// TODO Auto-generated method stub
		TempResponse tr = new TempResponse();
		tr.setStr("h3llo world");
		return tr;
	}

	public static void startDownload(YoutubeRequest yr) {
		// TODO Auto-generated method stub
		System.out.println(yr.getLink());
		/*
		 * try { Process p = Runtime.getRuntime().exec("youtube-dl "
		 * +yr.getLink());
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	public static String getCamImageBase64Data() {
		return new CamBase64Data().getData();
	}

	public static String createNewRecord(InsertRecordDto insertRecordDto) {
		// TODO Auto-generated method stub
		String link = parseLink(insertRecordDto.getLink());

		if (link != null) {
			daoObj.createNewEntry("", insertRecordDto.getUser(), false, System.currentTimeMillis(),
					System.currentTimeMillis(), "", link);
			return new String("new entry added");
		} else
			return "null link";
	}

	private static String parseLink(String link) {
		// TODO Auto-generated method stub
		StringBuilder parsed =null;
		String response = null;
		if(link != null){
			parsed= new StringBuilder();
			String[] parts =link.split("\\?");
			String domain = parts[0];
			Map<String, String> params = new HashMap<>();
			if(parts.length>1){
				String[] values = parts[1].split("&|#");
				for(String keyValue: values){
					params.put(keyValue.split("=")[0],keyValue.split("=")[1]);
				}
			}
			parsed.append(domain).append("?").append("v").append("=").append(params.get("v"));
			response = parsed.toString();
		}
		return response;
	}

	public static MusicListResponseList checkForUpdates(MusicUpdateDto musicUpdateDto){
	//	ArrayList<MusicListResponse> musicList = daoObj.getNewEntries(musicUpdateDto.getLastChecked(),musicUpdateDto.getUser());
		ArrayList<MusicListResponse> musicList = daoObj.getAllEntries(musicUpdateDto.getLastChecked(),musicUpdateDto.getUser());
		MusicListResponseList response = new MusicListResponseList(musicList);
		return response;
	}

	public static File getMusicPathById(Integer id) {
		// TODO Auto-generated method stub
		String path = daoObj.getPathById(id);
		if(path==null){
			System.out.println("UNABLE TO GET PATH FOR ID "+ id);
		}
		else{
			File file = new File(path);
			return file;
		}
		return null;
	}

	public static String getFileNameById(Integer id) {
		// TODO Auto-generated method stub
		String name = daoObj.getNameByid(id);
		return name;
	}
}
