package com.venne.PushPicPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class MiraiApi{
	
	public static final String MIRAI_API_SERVER ="http://localhost:8007";
	
	public static final String SEND_GROUP_MESSAGE = "/sendGroupMessage";
	
	public static final String SEND_FRIEND_MESSAGE = "/sendFriendMessage";
	
	//发送单条文本
	public static String sendOneGroupMessage(String text, Long target) throws Exception {
		List<MessageType> messageChain = new ArrayList<MessageType>();
		MessageType mT = new MessageType();
		mT.setText(text);
		mT.setType("Plain");
		messageChain.add(mT);
		return sendGroupMessage(messageChain, target);
	}
	
	//发送单张图片
	public static String sendOneGroupImage(String path, Long target) throws Exception {
		List<MessageType> messageChain = new ArrayList<MessageType>();
		MessageType mT = new MessageType();
		mT.setPath(path);
		mT.setType("Image");
		messageChain.add(mT);
		return sendGroupMessage(messageChain, target);
	}
	
	//发送一条文本+一张图片
	public static String sendOneGroupImage_Text(String text, String path, Long target) throws Exception {
		List<MessageType> messageChain = new ArrayList<MessageType>();
		MessageType mT2 = new MessageType();
		mT2.setText(text);
		mT2.setType("Plain");
		messageChain.add(mT2);
		
		MessageType mT = new MessageType();
		mT.setPath(path);
		mT.setType("Image");
		messageChain.add(mT);
			
		return sendGroupMessage(messageChain, target);
	}
	
	public static String sendGroupMessage(List<MessageType> messageChain, Long target) throws Exception {
		//POST
		Map<String, Object> params=new HashMap<>();
		
		params.put("target",target);
		
		params.put("messageChain", messageChain);
		
		String result = HttpUtil.post(MIRAI_API_SERVER + SEND_GROUP_MESSAGE, JSON.toJSONString(params));
		
		return result;
	}
}