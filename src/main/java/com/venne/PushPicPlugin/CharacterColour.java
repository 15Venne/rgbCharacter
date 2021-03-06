package com.venne.PushPicPlugin;


public class CharacterColour{
	
	private int red=0;
	
	private int green=0;
	
	private int blue=0;
	
	private int at=0;
	private int def=0;
	private int spd=0;
	
	private long exp=0;
	private int level=1;
	private int special=0;
	
	private String name="";
	private String avatar="";
	
	private long qqId;
	
	private int times;
	
	private int san;
	
	public int getSan() {
		return san;
	}
	public void setSan(int san) {
		this.san = san;
	}
	
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	
	public long getQqId() {
		return qqId;
	}
	public void setQqId(long qqId) {
		this.qqId = qqId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public long getExp() {
		return exp;
	}
	public int getLevel() {
		return level;
	}
	public int getSpecial() {
		return special;
	}
	public void setExp(long exp) {
		this.exp = exp;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	
	public int getAt() {
		return at;
	}
	public int getDef() {
		return def;
	}
	public int getSpd() {
		return spd;
	}
	
	public void setAt(int at) {
		this.at = at;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public void setSpd(int spd) {
		this.spd = spd;
	}
	
	public int getRed() {
		return red;
	}
	public int getGreen() {
		return green;
	}
	public int getBlue() {
		return blue;
	}
	
	public void setRed(int red) {
		this.red = red;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public void setBlue(int red) {
		this.red = red;
	}
	
}