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

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: Q
 * @Description: StringUtils字符串工具类
 * @Author：Chengel_HaltuD
 * @Date：2015-8-29 下午2:24:27
 * @version V1.0
 *
 */
public class Q
{
  public static String SeparateMobile(String mobile)
  {
    if (TextUtils.isEmpty(mobile)) {
      return mobile;
    }

    if (mobile.length() == 11) {
      return mobile.substring(0, 3) + "-" + mobile.substring(3, 7) + "-" + mobile.substring(7, mobile.length());
    }
    return mobile;
  }

  public static String hiddenMobile(String mobile)
  {
    if (TextUtils.isEmpty(mobile)) {
      return mobile;
    }

    if (mobile.length() == 11) {
      return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
    }
    return mobile;
  }

  public static String isText(String text)
  {
    Pattern p = Pattern.compile("[0-9]*");
    Matcher m = p.matcher(text);
    if (m.matches())
    {
      return "SHUZI";
    }
    p = Pattern.compile("[a-zA-Z]");
    m = p.matcher(text);
    if (m.matches())
    {
      return "ZIMU";
    }
    p = Pattern.compile("[一-龥]");
    m = p.matcher(text);
    if (m.matches())
    {
      return "HANZI";
    }
    return "";
  }

  public static boolean isAllText(String text) {
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      boolean isValid = ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || (
        (c >= '0') && (
        c <= '9'));
      if (!isValid)
        return false;
    }
    return true;
  }

  public static boolean isAllChinese(String text)
  {
    Pattern p = Pattern.compile("[一-龥]");

    for (int i = 0; i < text.length(); i++) {
      Matcher m = p.matcher(String.valueOf(text.charAt(i)));
      if (!m.matches()) {
        return false;
      }
    }
    return true;
  }

  public static String subZeroAndDot(String s)
  {
    if (s.indexOf(".") > 0) {
      s = s.replaceAll("0+?$", "");
      s = s.replaceAll("[.]$", "");
    }
    return s;
  }

  public static boolean isEquals(String[] agrs)
  {
    String last = null;
    for (int i = 0; i < agrs.length; i++) {
      String str = agrs[i];
      if (isEmpty(str)) {
        return false;
      }
      if ((last != null) && (!str.equalsIgnoreCase(last))) {
        return false;
      }
      last = str;
    }
    return true;
  }

  public static boolean isEmpty(String value)
  {
    if ((value != null) && (!"".equalsIgnoreCase(value.trim())) && (!"null".equalsIgnoreCase(value.trim()))) {
      return false;
    }
    return true;
  }

  public static boolean isEmpty(String[] value)
  {
    int i = 0; if (i < value.length)
    {
      if ((value != null) && (!"".equalsIgnoreCase(value[i].trim())) && (!"null".equalsIgnoreCase(value[i].trim()))) {
        return false;
      }
      return true;
    }

    return true;
  }

  public static boolean isEmpty(Object obj)
  {
    if (obj == null)
      return true;
    if ((obj instanceof CharSequence)) {
      CharSequence str = (CharSequence)obj;
      if ((str == null) || ("".equals(str)) || ("null".equals(str)))
        return true;
    }
    else if ((obj instanceof TextView)) {
      TextView txt = (TextView)obj;
      if ((txt == null) || ("".equals(txt.getText()))) {
        return true;
      }
    }
    return false;
  }

  public static boolean isEmpty(Object[] obj)
  {
    for (int i = 0; i < obj.length; i++)
    {
      if (obj[i] == null)
        return true;
      if ((obj[i] instanceof CharSequence)) {
        CharSequence str = (CharSequence)obj[i];
        if ((str == null) || ("".equals(str)) || ("null".equals(str)))
          return true;
      }
      else if ((obj[i] instanceof TextView)) {
        TextView txt = (TextView)obj[i];
        if ((txt == null) || ("".equals(txt.getText()))) {
          return true;
        }
      }
    }

    return false;
  }

  public static CharSequence getHighLightText(String content, int color, int start, int end)
  {
    if (TextUtils.isEmpty(content)) {
      return "";
    }
    start = start >= 0 ? start : 0;
    end = end <= content.length() ? end : content.length();
    SpannableString spannable = new SpannableString(content);
    CharacterStyle span = new ForegroundColorSpan(color);
    spannable.setSpan(span, start, end, 33);
    return spannable;
  }

  public static Spanned getHtmlStyleString(Context context, int resId)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<a href=\"\"><u><b>").append(context.getResources().getString(resId)).append(" </b></u></a>");
    return Html.fromHtml(sb.toString());
  }

  public static String formatFileSize(long len) {
    return formatFileSize(len, false);
  }

  public static String formatFileSize(long len, boolean keepZero)
  {
    DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
    DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
    String size;
    if (len < 1024L) {
      size = String.valueOf(len + "B");
    }
    else
    {
      if (len < 10240L)
      {
        size = String.valueOf((float)(len * 100L / 1024L) / 100.0F) + "KB";
      }
      else
      {
        if (len < 102400L)
        {
          size = String.valueOf((float)(len * 10L / 1024L) / 10.0F) + "KB";
        }
        else
        {
          if (len < 1048576L)
          {
            size = String.valueOf(len / 1024L) + "KB";
          }
          else
          {
            if (len < 10485760L)
            {
              if (keepZero)
                size = String.valueOf(formatKeepTwoZero.format((float)(len * 100L / 1024L / 1024L) / 100.0F)) + "MB";
              else
                size = String.valueOf((float)(len * 100L / 1024L / 1024L) / 100.0F) + "MB";
            }
            else
            {
              if (len < 104857600L)
              {
                if (keepZero)
                  size = String.valueOf(formatKeepOneZero.format((float)(len * 10L / 1024L / 1024L) / 10.0F)) + "MB";
                else
                  size = String.valueOf((float)(len * 10L / 1024L / 1024L) / 10.0F) + "MB";
              }
              else
              {
                if (len < 1073741824L)
                {
                  size = String.valueOf(len / 1024L / 1024L) + "MB";
                }
                else
                  size = String.valueOf((float)(len * 100L / 1024L / 1024L / 1024L) / 100.0F) + "GB"; 
              }
            }
          }
        }
      }
    }
    return size;
  }
}