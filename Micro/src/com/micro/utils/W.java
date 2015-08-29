/*
 * Copyright (C) 2012 Chengel_HaltuD
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

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * 
 * @ClassName: W
 * @Description: SystemUtils 系统工具类
 * @Author：Chengel_HaltuD
 * @Date：2015-8-29 下午2:25:56
 * @version V1.0
 *
 */
public class W
{
  public static String getOSVersion()
  {
    String release = Build.VERSION.RELEASE;
    release = "android" + release;
    return release;
  }

  public static String getOSVersionSDK()
  {
    return Build.VERSION.SDK;
  }

  public static int getOSVersionSDKINT()
  {
    return Build.VERSION.SDK_INT;
  }

  public static String getDeviceModel()
  {
    return Build.MODEL;
  }

  public static String getIMEI(Context context)
  {
    if (context == null) {
      return null;
    }
    String imei = null;
    try {
      TelephonyManager tm = (TelephonyManager)context.getSystemService("phone");
      imei = tm.getDeviceId();
    } catch (Exception e) {
      L.E(e);
    }
    return imei;
  }

  public static boolean isCheckSimCardAvailable(Context context)
  {
    if (context == null) {
      return false;
    }
    TelephonyManager tm = (TelephonyManager)context.getSystemService("phone");
    return tm.getSimState() == 5;
  }

  public static boolean isCanUseSim(Context context)
  {
    if (context == null)
      return false;
    try
    {
      TelephonyManager mgr = (TelephonyManager)context.getSystemService("phone");
      return 5 == mgr.getSimState();
    } catch (Exception e) {
      L.E(e);
    }
    return false;
  }

  public static String getIMSI(Context context)
  {
    if (context == null) {
      return null;
    }
    String imsi = null;
    try {
      TelephonyManager tm = (TelephonyManager)context.getSystemService("phone");
      imsi = tm.getSubscriberId();
    } catch (Exception e) {
      L.E(e);
    }
    return imsi;
  }

  public static String getNativePhoneNumber(Context context)
  {
    if (context == null) {
      return null;
    }

    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
    String NativePhoneNumber = null;
    NativePhoneNumber = telephonyManager.getLine1Number();
    return NativePhoneNumber;
  }

  public static String getProvidersName(Context context)
  {
    String ProvidersName = null;

    String IMSI = getIMSI(context);

    if ((IMSI.startsWith("46000")) || (IMSI.startsWith("46002")))
      ProvidersName = "中国移动";
    else if (IMSI.startsWith("46001"))
      ProvidersName = "中国联通";
    else if (IMSI.startsWith("46003"))
      ProvidersName = "中国电信";
    else {
      ProvidersName = "其他服务商:" + IMSI;
    }
    return ProvidersName;
  }

  public static String getSimSN(Context context)
  {
    if (context == null) {
      return null;
    }
    String simSN = null;
    try {
      TelephonyManager tm = (TelephonyManager)context.getSystemService("phone");
      simSN = tm.getSimSerialNumber();
    } catch (Exception e) {
      L.E(e);
    }
    return simSN;
  }

  public static String getMacAddress(Context context)
  {
    if (context == null) {
      return null;
    }
    String mac = null;
    try {
      WifiManager wm = (WifiManager)context.getSystemService("wifi");
      WifiInfo info = wm.getConnectionInfo();
      mac = info.getMacAddress();
    } catch (Exception e) {
      L.E(e);
    }
    return mac;
  }

  /** 获得设备ip地址 */
	public static String getLocalAddress() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface intf = en.nextElement();
				Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
				while (enumIpAddr.hasMoreElements()) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			L.E(e);
		}
		return null;
	}

  public static int[] getResolution(Context context)
  {
    if (context == null) {
      return null;
    }
    WindowManager windowMgr = (WindowManager)context.getSystemService("window");
    int[] res = new int[2];
    res[0] = windowMgr.getDefaultDisplay().getWidth();
    res[1] = windowMgr.getDefaultDisplay().getHeight();
    return res;
  }

  public static float getWidthDpi(Context context)
  {
    if (context == null) {
      return 0.0F;
    }
    DisplayMetrics dm = null;
    try {
      if (context != null) {
        dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
      }

      return dm.densityDpi;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0.0F;
  }

  public static float getHeightDpi(Context context)
  {
    if (context == null) {
      return 0.0F;
    }
    DisplayMetrics dm = new DisplayMetrics();
    dm = context.getApplicationContext().getResources().getDisplayMetrics();
    return dm.ydpi;
  }

  public static String[] getDivceInfo()
  {
    String str1 = "/proc/cpuinfo";
    String str2 = "";
    String[] cpuInfo = { "", "" };
    try
    {
      FileReader fr = new FileReader(str1);
      BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
      str2 = localBufferedReader.readLine();
      String[] arrayOfString = str2.split("\\s+");
      for (int i = 2; i < arrayOfString.length; i++) {
        cpuInfo[0] = (cpuInfo[0] + arrayOfString[i] + " ");
      }
      str2 = localBufferedReader.readLine();
      arrayOfString = str2.split("\\s+");
      int tmp123_122 = 1;
      String[] tmp123_121 = cpuInfo; tmp123_121[tmp123_122] = (tmp123_121[tmp123_122] + arrayOfString[2]);
      localBufferedReader.close();
    } catch (IOException e) {
      L.E(e);
    }
    return cpuInfo;
  }

  public static boolean isNEON()
  {
    boolean isNEON = false;
    String cupinfo = getCPUInfos();
    if (cupinfo != null) {
      cupinfo = cupinfo.toLowerCase();
      isNEON = (cupinfo != null) && (cupinfo.contains("neon"));
    }
    return isNEON;
  }

  private static String getCPUInfos()
  {
    String str1 = "/proc/cpuinfo";
    String str2 = "";
    StringBuilder resusl = new StringBuilder();
    String resualStr = null;
    try {
      FileReader fr = new FileReader(str1);
      BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
      while ((str2 = localBufferedReader.readLine()) != null) {
        resusl.append(str2);
      }

      if (resusl != null)
        return resusl.toString();
    }
    catch (IOException e)
    {
      L.E(e);
    }
    return resualStr;
  }

  public static int getCPUModel()
  {
    return matchABI(getSystemProperty("ro.product.cpu.abi")) | matchABI(getSystemProperty("ro.product.cpu.abi2"));
  }

  private static int matchABI(String abiString)
  {
    if (TextUtils.isEmpty(abiString)) {
      return 0;
    }
    if ("armeabi".equals(abiString))
      return 1;
    if ("armeabi-v7a".equals(abiString))
      return 2;
    if ("x86".equals(abiString))
      return 4;
    if ("mips".equals(abiString)) {
      return 8;
    }
    return 0;
  }

  public static int getCpuCount()
  {
    return Runtime.getRuntime().availableProcessors();
  }

  public static String getRomversion()
  {
    String rom = "";
    try {
      String modversion = getSystemProperty("ro.modversion");
      String displayId = getSystemProperty("ro.build.display.id");
      if ((modversion != null) && (!modversion.equals(""))) {
        rom = modversion;
      }
      if ((displayId != null) && (!displayId.equals("")))
        rom = displayId;
    }
    catch (Exception e) {
      L.E(e);
    }
    return rom;
  }

  public static String getSystemProperty(String key)
  {
    String pValue = null;
    try {
      Class c = Class.forName("android.os.SystemProperties");
      Method m = c.getMethod("get", new Class[] { String.class });
      pValue = m.invoke(null, new Object[] { key }).toString();
    } catch (Exception e) {
      L.E(e);
    }
    return pValue;
  }

  public static List<String> getSystemLibs(Context context)
  {
    if (context == null) {
      return null;
    }

    PackageManager pm = context.getPackageManager();
    String[] libNames = pm.getSystemSharedLibraryNames();
    List listLibNames = Arrays.asList(libNames);
    L.D("SystemLibs: " + listLibNames);
    return listLibNames;
  }

  public static long getExternalTotalSpace()
  {
    long totalSpace = -1L;
    if (F.isCanUseSD()) {
      try {
        String path = Environment.getExternalStorageDirectory().getPath();
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        totalSpace = totalBlocks * blockSize;
      } catch (Exception e) {
        L.E(e);
      }
    }
    return totalSpace;
  }

  public static long getExternalSpace()
  {
    long availableSpace = -1L;
    if (F.isCanUseSD()) {
      try {
        String path = Environment.getExternalStorageDirectory().getPath();
        StatFs stat = new StatFs(path);
        availableSpace = stat.getAvailableBlocks() * stat.getBlockSize();
      } catch (Exception e) {
        L.E(e);
      }
    }
    return availableSpace;
  }

  public static long getTotalInternalSpace()
  {
    long totalSpace = -1L;
    try {
      String path = Environment.getDataDirectory().getPath();
      StatFs stat = new StatFs(path);
      long blockSize = stat.getBlockSize();
      long totalBlocks = stat.getBlockCount();
      totalSpace = totalBlocks * blockSize;
    } catch (Exception e) {
      L.E(e);
    }
    return totalSpace;
  }

  public static long getAvailableInternalMemorySize()
  {
    long availableSpace = -1L;
    try {
      String path = Environment.getDataDirectory().getPath();
      StatFs stat = new StatFs(path);
      long blockSize = stat.getBlockSize();
      long availableBlocks = stat.getAvailableBlocks();
      availableSpace = availableBlocks * blockSize;
    } catch (Exception e) {
      L.E(e);
    }
    return availableSpace;
  }

  public static long getOneAppMaxMemory(Context context)
  {
    if (context == null) {
      return -1L;
    }
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    return activityManager.getMemoryClass() * 1024 * 1024;
  }

  public static long getUsedMemory(Context context)
  {
    return getUsedMemory(context, null);
  }

  public static long getUsedMemory(Context context, String packageName)
  {
    if (context == null) {
      return -1L;
    }
    if (TextUtils.isEmpty(packageName)) {
      packageName = context.getPackageName();
    }
    long size = 0L;
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    List<RunningAppProcessInfo> runapps = activityManager.getRunningAppProcesses();
    for (ActivityManager.RunningAppProcessInfo runapp : runapps) {
      if (packageName.equals(runapp.processName))
      {
        Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[] { runapp.pid });

        size = processMemoryInfo[0].getTotalPrivateDirty() * 1024;
      }
    }
    return size;
  }

  public static long getAvailableMemory(Context context)
  {
    if (context == null) {
      return -1L;
    }
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(info);
    return info.availMem;
  }

  public static long getThresholdMemory(Context context)
  {
    if (context == null) {
      return -1L;
    }
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(info);
    return info.threshold;
  }

  public static boolean isLowMemory(Context context)
  {
    if (context == null) {
      return false;
    }
    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(info);
    return info.lowMemory;
  }
}