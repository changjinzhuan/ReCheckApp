package cn.kcrxorg.recheckapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chang on 2017/9/21.
 */

public class Data implements Serializable {
    List<Map<String, Object>> list ;
    public void setList(List<Map<String, Object>> list)
    {
       this.list=list;
    }
    public List<Map<String, Object>> getList()
    {
        return  list;
    }
}
