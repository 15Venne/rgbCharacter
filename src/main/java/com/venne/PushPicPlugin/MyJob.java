package com.venne.PushPicPlugin;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.venne.PushPicPlugin.LiveApi;

public class MyJob implements Job{
	public void execute(JobExecutionContext arg0) throws JobExecutionException{
		Date date = new Date();
		
		System.out.println(date.toString());
		
		
		
		String result = HttpUtil.sendGet(LiveApi.ACFUN_LIVE_API, "userId=" + LiveApi.QYQX_USERID);
		System.out.println("秋先查询了1次");
		JSONObject joj =  JSONObject.parseObject(result);
		
		//System.out.println(result);
		
		
		if(result.contains("liveId")) {
			//判断是否已开播
			if(!PushPic.getQyqxIsLive()) {
				System.out.println("MyJob ===>qyqx change 开播了！");
				//发通知
				try {
					PushPic.sendNotice("qyqx 开播咯 https://live.acfun.cn/live/378269");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//更改变量
				PushPic.setQyqxIsLive(true);
			}			
		}else {//下播
			if(PushPic.getQyqxIsLive()) {
				System.out.println("MyJob --->qyqx change 下播了！");			
				//发通知
				try {
					PushPic.sendNotice("qyqx 下播咯");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				//更改变量
				PushPic.setQyqxIsLive(false);
			}
		}
		System.out.println("***************************************");
		//水姐
		String result1001 = HttpUtil.sendGet(LiveApi.ACFUN_LIVE_API, "userId=" + LiveApi.PROJECT1001_USERID);
		System.out.println("水姐查询了1次");
		//System.out.println(result1001);
		if(result1001.contains("liveId")) {
			//判断是否已开播
			if(!PushPic.getSuiyoubiLive()) {
				System.out.println("MyJob ===>1001project change 开播了！");
				//发通知
				try {
					PushPic.sendNotice("水姐 开播咯 https://live.acfun.cn/live/179922");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//更改变量
				PushPic.setSuiyoubiLive(true);
			}			
		}else {//下播
			if(PushPic.getSuiyoubiLive()) {
				System.out.println("MyJob --->1001project change 下播了！");			
				//发通知
				try {
					PushPic.sendNotice("水姐 下播咯");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				//更改变量
				PushPic.setSuiyoubiLive(false);
			}
		}
		
		System.out.println("***************************************");
		
		
		//CC直播
		//https://vapi.cc.163.com/video_play_url/361433
		String resultCC="";
		try {
			resultCC = HttpUtil.sendGet("http://vapi.cc.163.com/video_play_url/361433", "");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("CC直播查询了1次");
		System.out.println(resultCC);
		if(resultCC.contains("live_device")) {//开播
			if(!PushPic.getQuinIsLive()) {
				System.out.println("MyJob ===>Quin change 开播了！");
				//发通知
				try {
					PushPic.sendNotice("Mr.Quin 开播咯 https://cc.163.com/361433");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//更改变量
				PushPic.setQuinIsLive(true);
			}
		}else {//摸了
			if(PushPic.getQuinIsLive()) {
				System.out.println("MyJob --->Quin change 下播了！");
				//发通知
				try {
					PushPic.sendNotice("Mr.Quin 下播咯");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//更改变量
				PushPic.setQuinIsLive(false);
				
			}
			
		}
		
		
	}
	
	
	
}