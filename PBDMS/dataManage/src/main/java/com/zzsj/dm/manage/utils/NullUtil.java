package com.zzsj.dm.manage.utils;




import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/7 23:44
 * @description：返回对象为空列
 */
public class NullUtil {
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
            {emptyNames.add(pd.getName());}
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
