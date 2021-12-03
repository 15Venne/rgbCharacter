package com.venne.PushPicPlugin;


//
public class MessageType{
	
	
	
		private String type="Plain";//文本消息-Plain, 图片消息-Image
		private String text;
		
		private String path;
		
		private String url;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		
		public String getType(){
			return type;
		}
		public void setType(String type) {
			this.type=type;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
	
	
	
	
	
}