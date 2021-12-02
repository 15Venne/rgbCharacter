package com.venne.PushPicPlugin;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

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
		MiraiApi.sendOneGroupMessage(content, (long) 853023498);
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
	


}
