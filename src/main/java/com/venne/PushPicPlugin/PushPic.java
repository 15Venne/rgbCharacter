package com.venne.PushPicPlugin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.venne.PushPicPlugin.PluginData;


import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.console.plugin.jvm.SimpleJvmPluginDescription;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.BotConfiguration;

import net.mamoe.mirai.utils.MiraiLogger;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;


// TODO: Auto-generated Javadoc
/**
 * 插件主类
 * @author khjxiaogu
 * file: MiraiSongPlugin.java
 * time: 2020年8月26日
 */
public final class PushPic extends JavaPlugin{
	
	private static boolean qyqxIsLive=false;
	private static boolean quinIsLive=false;
	
	public static boolean getQyqxIsLive() {
		return qyqxIsLive;
	}
	
	public static void setQyqxIsLive(boolean qyqxIsLive) {
		PushPic.qyqxIsLive = qyqxIsLive;
	}
	
	public static boolean getQuinIsLive() {
		return quinIsLive;
	}
	public static void setQuinIsLive(boolean quinIsLive) {
		PushPic.quinIsLive = quinIsLive;
	}
	
	public static void sendNotice(String content) throws Exception {
		MiraiApi.sendOneGroupMessage(content, (long) 853023498); //测试群
		MiraiApi.sendOneGroupMessage(content, (long) 171393445);//
	}
	
	public static final PushPic INSTANCE = new PushPic();

	private PushPic() {
		super(new JvmPluginDescriptionBuilder(
				PluginData.id,
				PluginData.version)
				.name(PluginData.name)
				.author(PluginData.author)
				.info(PluginData.info)
				.build());
		//instance = this;
		//INSTANCE.onload
	}
	
	//@Override
	//public void onLoad() {
		
	//}
	
	
	@Override
	public void onEnable() {
		//getLogger().info("插件加载完毕!");
		
		
		getLogger().info("live notice 日志");
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        
       
        
        
        /********创建定时任务*****************/
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
        
        //创建触发器 每10秒执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group3")
        		.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(180).repeatForever())
        		.build();
        
        //创建调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			//将任务及其触发器放入调度器
	        scheduler.scheduleJob(jobDetail, trigger);
	        
	        //调度器开始调度任务
	        scheduler.start();
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        /*************************/
		
		/************************/
        eventChannel.subscribeAlways(GroupMessageEvent.class, g -> {
            //监听群消息
            getLogger().info(g.getMessage().contentToString());
            getLogger().info(g.getGroup().getId() + "");
            getLogger().info(g.getSender().getId() + "");
            
            /*mirai api 测试
            if(g.getMessage().contentToString().equals("查询 群成员")) {
            	String requestUrl = "http://localhost:8007";
            	getLogger().info("中");
            	requestUrl = requestUrl + "/memberList";
            	try {
					String result = HttpUtil.sendGet(requestUrl, "target=" + g.getGroup().getId());      		
					getLogger().info("结果: " + result);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            */
            String content = g.getMessage().contentToString();
            
            
            /**************调色rgb************************************/
            if(content.equals("rgb 调色rgb")) {
            	String nickname = g.getSenderName();
            	g.getSubject().sendMessage("调色rgb欢迎你，"+nickname+" 你可以色色，本轮生成的色色如下，请回复rgb和3个0~255的数字(空格隔开)以匹配色色");
            	
            	//生成色图
            	Random ran = new Random();
            	
            	int redO = 0;int greenO = 0;int blueO = 0;
            	redO = ran.nextInt(255);
            	greenO = ran.nextInt(255);
            	blueO = (int) (System.currentTimeMillis() % 255);
            	getLogger().info("redO: " + redO);
            	getLogger().info("greenO: " + greenO);
            	getLogger().info("blueO: " + blueO);
            	
            	String path = "";
            	try {
					path = getRgbImage(redO, greenO, blueO, g.getSender().getId(), 1);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	if(!path.equals("")) {
            		//发送图片
            		try {
            			getLogger().info("path: " + path);
						MiraiApi.sendOneGroupImage(path, g.getGroup().getId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            }else if(content.startsWith("rgbtest ")){
            	//返回一张rgb测试图
            	List<String> result =  Arrays.asList(content.split(" "));
            	if(result.size()!=4) {
            		//参数错误
            		System.out.println("rgb参数非3个");
        			g.getSubject().sendMessage(g.getSenderName() + " 色色参数错误，请回复rgbtest加三个0~255的数字，空格隔开，例如 rgbtest 155 200 33");
            	}else {
            		result.forEach(str ->{
                		//getLogger().info("rgb 回复分割: " + str);
                		if(str.equals("rgb")) {
                			
                		}else if(CommonUtil.isNumeric(str)) {
                			
                		}else {//不是数字
                			str = "0";
                		}  		
                	});
            		int red = Integer.valueOf(result.get(1));
                	int green = Integer.valueOf(result.get(2));
                	int blue = Integer.valueOf(result.get(3));
                	//范围修正
                	if(red > 255) {
                		red = 255;
                	}
                	if(green > 255) {
                		green = 255;
                	}
                	if(blue > 255) {
                		blue = 255;
                	}
                	String path = "";
                	try {
    					path = getRgbImage(red, green, blue, 1000, 0);
    				} catch (FileNotFoundException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                	
                	if(!path.equals("")) {
                		//发送图片
                		try {
                			//getLogger().info("path: " + path);
                			String reText = g.getSenderName() + " 本次testrgb为 r-"+red+" g-"+green+" b-"+blue+"";
    						MiraiApi.sendOneGroupImage_Text(reText, path, g.getGroup().getId());
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
                	}
                	
            	}
            	
            }else if(content.startsWith("rgb ")) {
            	getLogger().info("rgb 回复: " + content);
            	List<String> result =  Arrays.asList(content.split(" "));
            	
            	if(result.size()!=4) {
            		//参数错误
            		System.out.println("rgb参数非3个");
        			g.getSubject().sendMessage(g.getSenderName() + " 色色参数错误，请回复rgb加三个0~255的数字，空格隔开，例如 rgb 155 200 33");
            	}else {
            		    	
            	result.forEach(str ->{
            		getLogger().info("rgb 回复分割: " + str);
            		if(str.equals("rgb")) {
            			
            		}else if(CommonUtil.isNumeric(str)) {
            			
            		}else {//不是数字
            			str = "0";
            		}
            		
            	});
            	int red = Integer.valueOf(result.get(1));
            	int green = Integer.valueOf(result.get(2));
            	int blue = Integer.valueOf(result.get(3));
            	//范围修正
            	if(red > 255) {
            		red = 255;
            	}
            	if(green > 255) {
            		green = 255;
            	}
            	if(blue > 255) {
            		blue = 255;
            	}
            	
            	//获取对应用户rgb
            	int redO=0;int greenO=0;int blueO=0;
            	try {
            		String userTxtPath = "/data/mirai/" + g.getSender().getId() + ".txt";
            		File file=new File(userTxtPath);
            		if(file.isFile() && file.exists()){ //判断文件是否存在
            			InputStreamReader read = new InputStreamReader(new FileInputStream(file));//考虑到编码格式
            			BufferedReader bufferedReader = new BufferedReader(read);
            			String lineTxt = null;
            			String readTxt = "";
            			while((lineTxt = bufferedReader.readLine()) != null){
            				System.out.println("读取到的rgb文件： " + lineTxt);
            				readTxt = readTxt + lineTxt;
            			}
            			read.close();
            			
            			List<String> resultO =  Arrays.asList(readTxt.split(" "));
            			redO = Integer.valueOf(resultO.get(0));
            			greenO = Integer.valueOf(resultO.get(1));
            			blueO = Integer.valueOf(resultO.get(2));
            			
            			
            			//计算分数
                    	int score = CommonUtil.abs(redO - red) + CommonUtil.abs(greenO - green) + CommonUtil.abs(blueO- blue); 
                    	score = 300 - score;
                    	getLogger().info("score: " + score);
                    	if(score < 0)
                    		score = 0;
                    	//g.getSubject().sendMessage(g.getSenderName() + " 填充的色色是："+ red + " " + green + " "+ blue);
                    	
                    	//生成新的rgb图返回
                    	long userTmp = g.getSender().getId() * 100;
                    	String pathTmp = "";
                    	try {
        					pathTmp = getRgbImage(red, green, blue, userTmp, 0);
        					
        				} catch (FileNotFoundException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				} catch (IOException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
                    	String reText = g.getSenderName() + " 填充的色色是：" + red + " " + green + " " + blue
                    			+ ",rgb色色匹配分数: 【"+ String.format("%.2f", score/3.0) + "】";
                    	try {
        					MiraiApi.sendOneGroupImage_Text(reText, pathTmp, g.getGroup().getId());
        				} catch (Exception e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
                    	
                    	//删除txt
                    	file.delete();
                    	
                    	//更新用户信息 先读取 不存在则创建
                    	String userPath = "/data/mirai/user/" + g.getSender().getId() + ".txt";
                    	File fileUser =new File(userPath);
                    	if(fileUser.isFile() && fileUser.exists()){//更新用户信息
                    		InputStreamReader readUser = new InputStreamReader(new FileInputStream(fileUser));//考虑到编码格式
                			BufferedReader bufferedReaderUser = new BufferedReader(readUser);
                			String lineTxtUser = null;
                			String readTxtUser = "";
                			while((lineTxtUser = bufferedReaderUser.readLine()) != null){
                				System.out.println("读取到的rgb文件： " + lineTxtUser);
                				readTxtUser = readTxtUser + lineTxtUser;
                			}
                			readUser.close();
                			CharacterColour cc = JSON.parseObject(readTxtUser, CharacterColour.class);
                			cc.setExp(cc.getExp() + score);
                			cc.setTimes(cc.getTimes() + 1);
                			
                			//更新
                			BufferedWriter out = new BufferedWriter(new FileWriter(userPath));
                    		String ccStr = JSON.toJSONString(cc);
                        	out.write(ccStr);
                        	out.close();
                		                    		
                    	}else {
                    		System.out.println("找不到指定的用户文件，即将创建");//创建用户信息
                    		String name = g.getSenderName();
                    		long qqId = g.getSender().getId();
                    		CharacterColour cc = new CharacterColour();
                    		cc.setName(name);
                    		cc.setExp(score);
                    		cc.setLevel(1);
                    		cc.setTimes(1);
                    		cc.setQqId(qqId);
                    		BufferedWriter out = new BufferedWriter(new FileWriter(userPath));
                    		
                    		String ccStr = JSON.toJSONString(cc);
                    		
                        	out.write(ccStr);
                        	out.close();
                    	}
                    	
            	
            		}else {//txt文件不存在
            			System.out.println("找不到指定的文件");
            			g.getSubject().sendMessage(g.getSenderName() + " 未创建调色rgb，不可以色色");
            		}
            	}catch(Exception e) {
            		System.out.println("读取文件内容出错");
            		e.printStackTrace();
            	}
       	
            	}
            }
            
            /**************调色rgb************************************/
            

        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, f -> {
            //监听好友消息
            getLogger().info(f.getMessage().contentToString());
            getLogger().info("【私聊】= " + f.getSender() + " " + f.getSenderName());
            
            
            /*直播apitest
            String content = f.getMessage().contentToString();         
            if(content.contains("开播了吗")) {
            	String requestUrl = LiveApi.ACFUN_LIVE_API;
            	if(f.getMessage().contentToString().contains("qyqx开播了吗")) {      	
            		String result = HttpUtil.sendGet(requestUrl, "userId=" + LiveApi.QYQX_USERID);          	
            		getLogger().info("结果: " + result);           	
            		if(result.contains("liveId")) {           		
            			getLogger().info("开播了！");          	
            		}else {           		
            			getLogger().info("还没呢！");          	
            		}            	
            	}else{
            		String str1=content.substring(0, content.indexOf("开"));
            		if(CommonUtil.isInteger(str1)) {
            			int userId = Integer.valueOf(str1);
            			String result = HttpUtil.sendGet(requestUrl, "userId=" + userId);
            			getLogger().info("结果: " + result);                   	
                		if(result.contains("liveId")) {            		
                			getLogger().info("开播了！");
                			f.getSubject().sendMessage("开播了！");               	
                		}else {              		
                			getLogger().info("还没呢！");
                			f.getSubject().sendMessage("还没呢！");           	
                		}
            		}
            	}          	
            }
            */
            
        });
		
		
	}
	
	/*
	public static void main(String[] args) {
        Bot bot = createBot();
        bot.login();

        instance.getLogger().info("Bot avatarUrl: " + bot.getAvatarUrl());
        instance.getLogger().info("getid: " + bot.getId());
        instance.getLogger().info("getnick: " + bot.getNick());
        
        
        // 创建监听
        
		@SuppressWarnings({ "rawtypes", "unused" })
		Listener listener = bot.getEventChannel().subscribeAlways(GroupMessageEvent.class, event -> {
            MessageChain chain = event.getMessage(); // 可获取到消息内容等, 详细查阅 `GroupMessageEvent`
            if(chain.contentToString().equalsIgnoreCase("kira"))
            	event.getSubject().sendMessage("Miki!"); // 回复消息
        });
    }
    */

	
	public  Bot createBot() {
		Bot bot = BotFactory.INSTANCE.newBot(1234567, "test", new BotConfiguration() {{
		    // 配置，例如：
		    fileBasedDeviceInfo();
		}});
		
		return bot;
	}
	
	//flag 开玩标志
	public String getRgbImage(int r, int g, int b, long userId, int flag) throws FileNotFoundException, IOException {
		BufferedImage bi = new BufferedImage(200,100,BufferedImage.TYPE_INT_RGB);
		
		//取得图形
		Graphics gr = bi.getGraphics();
		
		//设置颜色
		gr.setColor(new Color(r, g, b));
		
		//填充
		gr.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		
		//创建图片文件
		String path = "/data/mirai/"+userId+".jpg";
		ImageIO.write(bi, "JPEG", new FileOutputStream(path));
		
		if(flag == 1) {//开玩才创建
			//创建文本文件
			BufferedWriter out = new BufferedWriter(new FileWriter("/data/mirai/" + userId + ".txt"));
        	out.write(r + " " + g + " " + b + "");
        	out.close();
		}
			
		return path;
	}
	
	public String readUser() {
		return null;
	}
	
	public void writeUser() {
		
	}


}
