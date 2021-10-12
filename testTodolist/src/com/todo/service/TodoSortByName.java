package com.todo.service;
import java.util.Comparator;

import com.todo.dao.TodoItem;

public class TodoSortByName implements Comparator<TodoItem> { //투두ㅇ아이템타입의 컴페레터 메소드 

    @Override //오버라이드 
    public int compare(TodoItem o1, TodoItem o2) { //정수를 비교하는 함수  
        return o1.getTitle().compareTo(o2.getTitle()); //

    }
}
