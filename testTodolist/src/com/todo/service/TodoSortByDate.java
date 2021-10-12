package com.todo.service;
import java.util.Comparator;

import com.todo.dao.TodoItem;

public class TodoSortByDate implements Comparator<TodoItem> { //비교하는 메소드 
    @Override
    public int compare(TodoItem o1, TodoItem o2) { //비교하는메소드 투두아이템 타입1,투두아이템 타입 2 오버라이드하는 방법으로 구현한다.
    	
        return o1.getCurrent_date().compareTo(o2.getCurrent_date()); // 겟커런트데이트 메소드 2불러 겟커런트데이트 메소드 1 불러서 비교값 리턴 

    }
}
