package com.imooc.service;

import com.imooc.pojo.Stu;

public interface StuService {
    public Stu getStuInfo(int id);

    public void saveStu();

    public void updateStu();

    public void deleteStu();
}
