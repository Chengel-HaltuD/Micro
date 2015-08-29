/*
 * Copyright (C) 2013 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.micro.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.micro.http.MicroAppProcessInfo;
import com.micro.http.MicroCPUInfo;
import com.micro.http.MicroProcessInfo;
import com.micro.http.MicroPsRow;


// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: A
 * @Description: A是AbAppUtil应用工具类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:56:47
 * @version V1.0
 *
 */


public class A
{
  public static List<String[]> mProcessList = null;

  public static boolean isBackground(Context context)
  {
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.processName.equals(context.getPackageName())) {
        if (appProcess.importance == 400) {
          L.I("后台" + appProcess.processName);
          return true;
        }
        L.I("前台" + appProcess.processName);
        return false;
      }
    }

    return false;
  }

  public static boolean isApplicationBroughtToBackground(Context context)
  {
    ActivityManager am = (ActivityManager)context.getSystemService("activity");
    List tasks = am.getRunningTasks(1);
    if (!tasks.isEmpty()) {
      ComponentName topActivity = ((ActivityManager.RunningTaskInfo)tasks.get(0)).topActivity;
      if (!topActivity.getPackageName().equals(context.getPackageName())) {
        return true;
      }
    }
    return false;
  }

  public static String getAppVersionName(Context context)
  {
    String versionName = "";
    try
    {
      PackageManager pm = context.getPackageManager();
      PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
      versionName = pi.versionName;
      if ((versionName == null) || (versionName.length() <= 0))
        return "";
    }
    catch (Exception e) {
      L.E(e);
    }
    return versionName;
  }

  public static void installApk(Context context, File file)
  {
    Intent intent = new Intent();
    intent.addFlags(268435456);
    intent.setAction("android.intent.action.VIEW");
    intent.setDataAndType(Uri.fromFile(file), 
      "application/vnd.android.package-archive");
    context.startActivity(intent);
  }

  public static void uninstallApk(Context context, String packageName)
  {
    Intent intent = new Intent("android.intent.action.DELETE");
    Uri packageURI = Uri.parse("package:" + packageName);
    intent.setData(packageURI);
    context.startActivity(intent);
  }

  public static boolean isServiceRunning(Context context, String className)
  {
    boolean isRunning = false;
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    List servicesList = activityManager.getRunningServices(2147483647);
    Iterator l = servicesList.iterator();
    while (l.hasNext()) {
      ActivityManager.RunningServiceInfo si = (ActivityManager.RunningServiceInfo)l.next();
      if (className.equals(si.service.getClassName())) {
        isRunning = true;
      }
    }
    return isRunning;
  }

  public static boolean stopRunningService(Context context, String className)
  {
    Intent intent_service = null;
    boolean ret = false;
    try {
      intent_service = new Intent(context, Class.forName(className));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (intent_service != null) {
      ret = context.stopService(intent_service);
    }
    return ret;
  }

  public static int getNumCores()
  {
    try
    {
      File dir = new File("/sys/devices/system/cpu/");

      File[] files = dir.listFiles(new FileFilter()
      {
        public boolean accept(File pathname)
        {
          if (Pattern.matches("cpu[0-9]", pathname.getName())) {
            return true;
          }
          return false;
        }
      });
      return files.length;
    } catch (Exception e) {
      e.printStackTrace();
    }return 1;
  }

  public static boolean isNetworkAvailable(Context context)
  {
    try
    {
      ConnectivityManager connectivity = (ConnectivityManager)context
        .getSystemService("connectivity");
      if (connectivity != null) {
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if ((info != null) && (info.isConnected()) && 
          (info.getState() == NetworkInfo.State.CONNECTED))
          return true;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
    return false;
  }

  public static boolean isGpsEnabled(Context context)
  {
    LocationManager lm = (LocationManager)context.getSystemService("location");
    return lm.isProviderEnabled("gps");
  }

  public static boolean isMobile(Context context)
  {
    ConnectivityManager connectivityManager = (ConnectivityManager)context
      .getSystemService("connectivity");
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    if ((activeNetInfo != null) && 
      (activeNetInfo.getType() == 0)) {
      return true;
    }
    return false;
  }

  public static boolean importDatabase(Context context, String dbName, int rawRes)
  {
    int buffer_size = 1024;
    InputStream is = null;
    FileOutputStream fos = null;
    boolean flag = false;
    try
    {
      String dbPath = "/data/data/" + context.getPackageName() + "/databases/" + dbName;
      File dbfile = new File(dbPath);

      if (!dbfile.exists())
      {
        if (!dbfile.getParentFile().exists()) {
          dbfile.getParentFile().mkdirs();
        }
        dbfile.createNewFile();
        is = context.getResources().openRawResource(rawRes);
        fos = new FileOutputStream(dbfile);
        byte[] buffer = new byte[buffer_size];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
          fos.write(buffer, 0, count);
        }
        fos.flush();
      }
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();

      if (fos != null)
        try {
          fos.close();
        }
        catch (Exception localException1) {
        }
      if (is != null)
        try {
          is.close();
        }
        catch (Exception localException2)
        {
        }
    }
    finally
    {
      if (fos != null)
        try {
          fos.close();
        }
        catch (Exception localException3) {
        }
      if (is != null)
        try {
          is.close();
        }
        catch (Exception localException4) {
        }
    }
    return flag;
  }

  public static DisplayMetrics getDisplayMetrics(Context context)
  {
    Resources mResources;
    if (context == null) {
      mResources = Resources.getSystem();
    }
    else {
      mResources = context.getResources();
    }

    DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
    return mDisplayMetrics;
  }

  public static void showSoftInput(Context context)
  {
    InputMethodManager inputMethodManager = (InputMethodManager)context
      .getSystemService("input_method");
    inputMethodManager.toggleSoftInput(0, 2);
  }

  public static void closeSoftInput(Context context)
  {
    InputMethodManager inputMethodManager = (InputMethodManager)context
      .getSystemService("input_method");
    if ((inputMethodManager != null) && (((Activity)context).getCurrentFocus() != null))
      inputMethodManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus()
        .getWindowToken(), 2);
  }

  public static void openKeybord(EditText mEditText, Context mContext)
  {
    InputMethodManager imm = (InputMethodManager)mContext
      .getSystemService("input_method");
    imm.showSoftInput(mEditText, 2);
    imm.toggleSoftInput(2, 
      1);
  }

  public static void closeKeybord(EditText mEditText, Context mContext)
  {
    InputMethodManager imm = (InputMethodManager)mContext
      .getSystemService("input_method");

    imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
  }

  public static PackageInfo getPackageInfo(Context context)
  {
    PackageInfo info = null;
    try {
      String packageName = context.getPackageName();
      info = context.getPackageManager().getPackageInfo(packageName, 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return info;
  }

  public static List<MicroAppProcessInfo> getRunningAppProcesses(Context context)
  {
    ActivityManager activityManager = null;
    List list = null;
    PackageManager packageManager = null;
    try {
      activityManager = (ActivityManager)context.getSystemService("activity");
      packageManager = context.getApplicationContext().getPackageManager();
      list = new ArrayList();

      List<RunningAppProcessInfo> appProcessList = activityManager.getRunningAppProcesses();
      ApplicationInfo appInfo = null;
      MicroAppProcessInfo abAppProcessInfo = null;
      PackageInfo packageInfo = getPackageInfo(context);

      if (mProcessList != null) {
        mProcessList.clear();
      }
      mProcessList = getProcessRunningInfo();

      for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
        abAppProcessInfo = new MicroAppProcessInfo(appProcessInfo.processName, appProcessInfo.pid, appProcessInfo.uid);
        appInfo = getApplicationInfo(context, appProcessInfo.processName);
        if (appInfo != null) {
          Drawable icon = appInfo.loadIcon(packageManager);
          String appName = appInfo.loadLabel(packageManager).toString();
          abAppProcessInfo.icon = icon;
          abAppProcessInfo.appName = appName;
        }
        else {
          if (appProcessInfo.processName.indexOf(":") != -1) {
            appInfo = getApplicationInfo(context, appProcessInfo.processName.split(":")[0]);
            Drawable icon = appInfo.loadIcon(packageManager);
            abAppProcessInfo.icon = icon;
          }
          abAppProcessInfo.appName = appProcessInfo.processName;
        }

        MicroProcessInfo processInfo = getMemInfo(appProcessInfo.processName);
        abAppProcessInfo.memory = processInfo.memory;
        abAppProcessInfo.cpu = processInfo.cpu;
        abAppProcessInfo.status = processInfo.status;
        abAppProcessInfo.threadsCount = processInfo.threadsCount;
        list.add(abAppProcessInfo);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }

  public static ApplicationInfo getApplicationInfo(Context context, String processName)
  {
    if (processName == null) {
      return null;
    }

    PackageManager packageManager = context.getApplicationContext().getPackageManager();
    List<ApplicationInfo> appList = packageManager.getInstalledApplications(8192);
    for (ApplicationInfo appInfo : appList) {
      if (processName.equals(appInfo.processName)) {
        return appInfo;
      }
    }
    return null;
  }

  public static void killProcesses(Context context, int pid, String processName)
  {
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    String packageName = null;
    try {
      if (processName.indexOf(":") == -1)
        packageName = processName;
      else {
        packageName = processName.split(":")[0];
      }

      activityManager.killBackgroundProcesses(packageName);

      Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", new Class[] { String.class });
      forceStopPackage.setAccessible(true);
      forceStopPackage.invoke(activityManager, new Object[] { packageName });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static List<MicroPsRow> ps()
  {
    List psRowlist = new ArrayList();
    String ps = runScript("ps");
    String[] lines = ps.split("\n");
    psRowlist = new ArrayList();
    for (String line : lines) {
      MicroPsRow row = new MicroPsRow(line);
      if (row.pid != null) psRowlist.add(row);
    }
    return psRowlist;
  }

  public static MicroPsRow getPsRow(String processName)
  {
    List<MicroPsRow> psRowlist = ps();
    for (MicroPsRow row : psRowlist) {
      if (processName.equals(row.cmd)) {
        return row;
      }
    }
    return null;
  }

  public static MicroProcessInfo getMemInfo(String processName)
  {
    MicroProcessInfo process = new MicroProcessInfo();
    if (mProcessList == null) {
      mProcessList = getProcessRunningInfo();
    }
    String processNameTemp = "";

    for (Iterator iterator = mProcessList.iterator(); iterator.hasNext(); ) {
      String[] item = (String[])iterator.next();
      processNameTemp = item[9];

      if ((processNameTemp != null) && (processNameTemp.equals(processName)))
      {
        process.pid = Integer.parseInt(item[0]);

        process.cpu = item[2];

        process.status = item[3];

        process.threadsCount = item[4];

        long mem = 0L;
        if (item[6].indexOf("M") != -1)
          mem = Long.parseLong(item[6].replace("M", "")) * 1000L * 1024L;
        else if (item[6].indexOf("K") != -1)
          mem = Long.parseLong(item[6].replace("K", "")) * 1000L;
        else if (item[6].indexOf("G") != -1) {
          mem = Long.parseLong(item[6].replace("G", "")) * 1000L * 1024L * 1024L;
        }
        process.memory = mem;

        process.uid = item[8];

        process.processName = item[9];
        break;
      }
    }
    if (process.memory == 0L) {
      L.D("##" + processName + ",top -n 1未找到");
    }
    return process;
  }

  public static MicroProcessInfo getMemInfo(int pid)
  {
    MicroProcessInfo process = new MicroProcessInfo();
    if (mProcessList == null) {
      mProcessList = getProcessRunningInfo();
    }
    String tempPidString = "";
    int tempPid = 0;
    int count = mProcessList.size();
    for (int i = 0; i < count; i++) {
      String[] item = (String[])mProcessList.get(i);
      tempPidString = item[0];
      if (tempPidString != null)
      {
        tempPid = Integer.parseInt(tempPidString);
        if (tempPid == pid)
        {
          process.pid = Integer.parseInt(item[0]);

          process.cpu = item[2];

          process.status = item[3];

          process.threadsCount = item[4];

          long mem = 0L;
          if (item[6].indexOf("M") != -1)
            mem = Long.parseLong(item[6].replace("M", "")) * 1000L * 1024L;
          else if (item[6].indexOf("K") != -1)
            mem = Long.parseLong(item[6].replace("K", "")) * 1000L;
          else if (item[6].indexOf("G") != -1) {
            mem = Long.parseLong(item[6].replace("G", "")) * 1000L * 1024L * 1024L;
          }
          process.memory = mem;

          process.uid = item[8];

          process.processName = item[9];
          break;
        }
      }
    }
    return process;
  }

  public static String runCommand(String[] command, String workdirectory)
  {
    String result = "";
    L.D("#" + command);
    try {
      ProcessBuilder builder = new ProcessBuilder(command);

      if (workdirectory != null) {
        builder.directory(new File(workdirectory));
      }
      builder.redirectErrorStream(true);
      Process process = builder.start();
      InputStream in = process.getInputStream();
      byte[] buffer = new byte[1024];
      while (in.read(buffer) != -1) {
        String str = new String(buffer);
        result = result + str;
      }
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
	 * 
	 * 描述：运行脚本.
	 * @param script
	 * @return
	 */
	public static String runScript(String script){
		String sRet = "";
		try {
			final Process m_process = Runtime.getRuntime().exec(script);
			final StringBuilder sbread = new StringBuilder();
			Thread tout = new Thread(new Runnable() {
				public void run() {
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(m_process.getInputStream()),
							8192);
					String ls_1 = null;
					try {
						while ((ls_1 = bufferedReader.readLine()) != null) {
							sbread.append(ls_1).append("\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							bufferedReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			tout.start();
			
			final StringBuilder sberr = new StringBuilder();
			Thread terr = new Thread(new Runnable() {
				public void run() {
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(m_process.getErrorStream()),
							8192);
					String ls_1 = null;
					try {
						while ((ls_1 = bufferedReader.readLine()) != null) {
							sberr.append(ls_1).append("\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							bufferedReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			terr.start();
			
			int retvalue = m_process.waitFor();
			while (tout.isAlive()) {
				Thread.sleep(50);
			}
			if (terr.isAlive())
				terr.interrupt();
			String stdout = sbread.toString();
			String stderr = sberr.toString();
			sRet = stdout + stderr;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sRet;
	}

  protected InputStream getErrorStream() {
	// TODO Auto-generated method stub
	return null;
}

protected InputStream getInputStream() {
	// TODO Auto-generated method stub
	return null;
}

public static boolean getRootPermission(Context context)
  {
    String packageCodePath = context.getPackageCodePath();
    Process process = null;
    DataOutputStream os = null;
    try {
      String cmd = "chmod 777 " + packageCodePath;

      process = Runtime.getRuntime().exec("su");
      os = new DataOutputStream(process.getOutputStream());
      os.writeBytes(cmd + "\n");
      os.writeBytes("exit\n");
      os.flush();
      process.waitFor();
    } catch (Exception e) {
      return false;
    } finally {
      try {
        if (os != null) {
          os.close();
        }
        process.destroy();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  public static List<String[]> getProcessRunningInfo()
  {
    List processList = null;
    try {
      String result = runCommandTopN1();
      processList = parseProcessRunningInfo(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return processList;
  }

  public static String runCommandTopN1()
  {
    String result = null;
    try {
      String[] args = { "/system/bin/top", "-n", "1" };
      result = runCommand(args, "/system/bin/");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static MicroCPUInfo getCPUInfo()
  {
    MicroCPUInfo CPUInfo = null;
    try {
      String result = runCommandTopN1();
      CPUInfo = parseCPUInfo(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return CPUInfo;
  }

  public static List<String[]> parseProcessRunningInfo(String info)
  {
    List processList = new ArrayList();
    int Length_ProcStat = 10;
    String tempString = "";
    boolean bIsProcInfo = false;
    String[] rows = null;
    String[] columns = null;
    rows = info.split("[\n]+");

    for (int i = 0; i < rows.length; i++) {
      tempString = rows[i];

      if (tempString.indexOf("PID") == -1) {
        if (bIsProcInfo) {
          tempString = tempString.trim();
          columns = tempString.split("[ ]+");
          if (columns.length == Length_ProcStat)
          {
            if (!columns[9].startsWith("/system/bin/"))
            {
              processList.add(columns);
            }
          }
        }
      } else bIsProcInfo = true;
    }

    return processList;
  }

  public static MicroCPUInfo parseCPUInfo(String info)
  {
    MicroCPUInfo CPUInfo = new MicroCPUInfo();
    String tempString = "";
    String[] rows = null;
    String[] columns = null;
    rows = info.split("[\n]+");

    for (int i = 0; i < rows.length; i++) {
      tempString = rows[i];

      if ((tempString.indexOf("User") != -1) && (tempString.indexOf("System") != -1)) {
        tempString = tempString.trim();
        columns = tempString.split(",");
        for (int j = 0; j < columns.length; j++) {
          String col = columns[j].trim();
          String[] cpu = col.split(" ");
          if (j == 0)
            CPUInfo.User = cpu[1];
          else if (j == 1)
            CPUInfo.System = cpu[1];
          else if (j == 2)
            CPUInfo.IOW = cpu[1];
          else if (j == 3) {
            CPUInfo.IRQ = cpu[1];
          }
        }
      }
    }
    return CPUInfo;
  }

  public static long getAvailMemory(Context context)
  {
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(memoryInfo);

    return memoryInfo.availMem;
  }

  public static long getTotalMemory(Context context)
  {
    String file = "/proc/meminfo";

    long memory = 0L;
    try
    {
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader, 8192);

      String memInfo = bufferedReader.readLine();
      String[] strs = memInfo.split("\\s+");
      for (String str : strs) {
        L.D(str + "\t");
      }

      memory = Integer.valueOf(strs[1]).intValue() * 1024;
      bufferedReader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return memory;
  }
}
