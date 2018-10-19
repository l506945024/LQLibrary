package com.lq.lib.util;

import android.app.Activity;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author xhh
 */
public class ActivityUtil {

    private static Stack<Activity> activityStack;
    private static ActivityUtil instance;

    private ActivityUtil() {

    }

    /**
     * 单一实例
     */
    public static ActivityUtil getActivityUtil() {
        if (instance == null) {
            synchronized (ActivityUtil.class) {
                if (instance == null) {
                    instance = new ActivityUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        try {
            if (activityStack == null) {
                return;
            }
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
//此代码会抛出    异常
//        for (Activity activity : activityStack) {
//            if (activity.getClass().equals(cls)) {
//                if (activity != null) {
//                    activityStack.remove(activity);
//                    activity.finish();
//                    activity = null;
//                }
//            }
//        }


        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                if (activity != null) {
                    iterator.remove();
                    activity.finish();
                    activity = null;
                }
            }
        }
    }

    /**
     * 结束指定的多个Activity
     */
    public void finishActivityList(List<Class<?>> clsList) {
        for (int j = 0; j < clsList.size(); j++) {
            Class<?> cls = clsList.get(j);
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (activityStack.get(i).getClass().equals(cls)) {
                    activityStack.get(i).finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束除指定以外的所有Activity
     */
    public void finishNotActivity(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!activity.getClass().equals(cls)) {
                if (activity != null) {
                    iterator.remove();
                    activity.finish();
                    activity = null;
                }
            }
        }
    }

    public int getActivitySize() {
        return activityStack.size();
    }

}