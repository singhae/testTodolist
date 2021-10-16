package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
	private int id;
	private int is_completed;

    public TodoItem(String title, String desc, String category, String due_date){ //스트링타입의 인자를 갖는 메소드 
        this.title=title; //클래스의 속성을 고대로 사용할때 this. 붙여준다 
        this.desc=desc;
        this.category=category;
        this.due_date=due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date());
       
    }
    
    public TodoItem(String title, String desc, String category, String current_date, String due_date) {
		// TODO Auto-generated constructor stub
    	this.title=title; //클래스의 속성을 고대로 사용할때 this. 붙여준다 
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date());
        this.category=category;
        this.due_date=due_date;
	}
   public TodoItem(String title, String desc, String category, String due_date, int is_completed) {
		// TODO Auto-generated constructor stub
	   this.title=title; //클래스의 속성을 고대로 사용할때 this. 붙여준다 
       this.desc=desc;
       this.category=category;
       this.due_date=due_date;
       SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
       this.current_date=f.format(new Date());
       this.is_completed=is_completed;
	}

public String getCategory() {
		return category;
		
	}
   public void setCategory(String category) {
	   this.category = category;
   }

	public String getTitle() {  
        return title;
    }

    public void setTitle(String title) { 
        this.title = title; 
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
    	//current_date.toString();
    	return current_date;
        
    }

    public void setCurrent_date(String current_date) {  
        this.current_date = current_date;
    }
    
    public String toSaveString() {
    	return "[" + category  + "]" + "-" + title + "-" + desc + "-" + current_date + "-" + due_date + "\n";
 
    }
	@Override
    public String toString() { //이거 없어서 이상한거 떳구나 
		
			return "[" + category  + "]" + "-" + title + "-" + desc + "-" + current_date + "-" + due_date ;
    }
	
	public String toCompString() { //이거 없어서 이상한거 떳구나 
			System.out.print(title);
			return "[" + category  + "]" + "[V]" + "-" + title + "-" + desc + "-" + current_date + "-" + due_date ; // 지금 순서가 이상하다커런트데이트 먼저 나오면 어쩌자는거야 
		//else
		//	return "[" + category  + "]" + "[ ]" + "-" + title + "-" + desc + "-" + current_date + "-" + due_date ;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	
   
}
