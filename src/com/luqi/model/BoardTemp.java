package com.luqi.model;

import com.luqi.common.model.T1doBoard;

/**
 * @ClassName BoardTemp
 * @Description 项目看板临时表
 * @auther Sherry
 * @date 2019/11/18 11:40 AM
 */
public class BoardTemp {
    //节点
    private T1doBoard t1doBoard;
    //类型1新增2修改3删除
    private int type;


    public BoardTemp(T1doBoard t1doBoard, int type) {
        this.t1doBoard = t1doBoard;
        this.type = type;
    }

    public T1doBoard getT1doBoard() {
        return t1doBoard;
    }

    public void setT1doBoard(T1doBoard t1doBoard) {
        this.t1doBoard = t1doBoard;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
