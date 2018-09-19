package com.util;
import org.apache.commons.beanutils.ConvertUtils;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class BeanUtil {
    public static <T> T parseFromReq(HttpServletRequest request, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        Field[] fields = clazz.getDeclaredFields();
        T instance = clazz.newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            //field.set(instance,request.getParameter(field.getName()));
            //判断请求参数是否为空 如果为空，则不用设置，因为instance中构造完毕后，默认所有字段都是null
            if (request.getParameter(field.getName()) == null) {
                continue;
            } else {
                //如果请求参数不为空，则利用ConvertUtils.convert(String value,class type)方法，此方法的作用是将字符串 转换成指定类型
                Object convertedValue = ConvertUtils.convert(request.getParameter(field.getName()), field.getType());
                field.set(instance, convertedValue);
            }
        }
        return instance;
    }

}
