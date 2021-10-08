package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    private String title;
    private String desc;
    private String current_date;
    private String due_date;
    private String category;
    private int is_completed;
    
    
    public TodoItem(String title, String desc, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.category=category;
        this.due_date=due_date;
        Date today = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
        this.current_date= f.format(today);
    }
    
    public TodoItem(String title, String desc, String category, String due_date, String current_date){
        this.title=title;
        this.desc=desc;
        this.category=category;
        this.due_date=due_date;
        this.current_date= current_date;
    }
    public TodoItem(String title, String desc, String category, String due_date, String current_date, int is_completed){
        this.title=title;
        this.desc=desc;
        this.category=category;
        this.due_date=due_date;
        this.current_date= current_date;
        this.is_completed= is_completed;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public String toSaveString() {
        return  category + "##" + title + "##" + desc + "##" + due_date + "##" +current_date + "\n";
       // return id + "##" + category + "##" + title + "##" + desc + "##" + due_date + "##" +current_date + "\n";
    }
    @Override
    public String toString() {
    	if(is_completed == 1) {
    		return  id +" [" + category + "] "+"[V]"+title +" - "+ desc +" - "+due_date+" - "+current_date;
    	}
    	else 
    		return  id +" [" + category + "] "+title +" - "+ desc +" - "+due_date+" - "+current_date;
    }

	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	public void setIscompleted(int is_completed) {
		// TODO Auto-generated method stub
		this.is_completed = is_completed;
	}
	public int getIscompleted() {
		// TODO Auto-generated method stub
		return is_completed;
	}
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
}
