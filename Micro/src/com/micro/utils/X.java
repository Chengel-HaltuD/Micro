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

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: X
 * @Description: VerifyCheck 描述：验证类
 * @Author：Chengel_HaltuD
 * @Date：2015-8-29 下午2:26:17
 * @version V1.0
 *
 */
public class X
{
  private static int[] idsArray = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

  public static boolean isCarBrand(String carBrand)
  {
    if ((carBrand == null) || ("".equals(carBrand.trim()))) {
      return false;
    }
    Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\u4e00-\u9fa5]$");
    Matcher m = p.matcher(carBrand);
    return m.matches();
  }

  public static boolean isAccountVerify(String account)
  {
    if ((account == null) || ("".equals(account.trim()))) {
      return false;
    }
    String accountTrim = account.trim();
    Pattern patternAccount = Pattern.compile("^([a-zA-Z0-9_.\u4e00-\u9fa5]{2,16})+$");
    Matcher matcherAccount = patternAccount.matcher(accountTrim);
    if (!matcherAccount.matches()) {
      return false;
    }
    return true;
  }

  public static boolean isMobilePhoneVerify(String mobileString)
  {
    if ((mobileString == null) || ("".equals(mobileString.trim()))) {
      return false;
    }
    String mobileTrim = mobileString.trim();
    Pattern patternMobile = Pattern.compile("^1[3|5|4|7|8][0-9]{9}$");
    Matcher matcherMobile = patternMobile.matcher(mobileTrim);
    if (!matcherMobile.matches()) {
      return false;
    }
    return true;
  }

  public static boolean isMobileNo(String mobile)
  {
    if (TextUtils.isEmpty(mobile)) {
      return false;
    }
    Pattern p = Pattern.compile("1[3|4|5|6|8|9]\\d{9}$");
    Matcher m = p.matcher(mobile);
    return m.matches();
  }

  public static boolean isEmailVerify(String emailString)
  {
    if ((emailString == null) || ("".equals(emailString.trim()))) {
      return false;
    }
    String emailTrim = emailString.trim();
    Pattern patternEmail = Pattern.compile("^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$");
    Matcher matcherEmail = patternEmail.matcher(emailTrim);
    if (!matcherEmail.matches()) {
      return false;
    }
    return true;
  }

  public static boolean isRealnameVerify(String realitynameString)
  {
    if ((realitynameString == null) || ("".equals(realitynameString.trim()))) {
      return false;
    }
    String realitynameTrim = realitynameString.trim();
    Pattern patternRealityname = Pattern.compile("[\u4e00-\u9fa5]{2,10}");
    Matcher matcherRealityname = patternRealityname.matcher(realitynameTrim);
    if (!matcherRealityname.matches()) {
      return false;
    }
    return true;
  }

  public static boolean isIDCardVerify(String idCardNumber)
  {
    if (idCardNumber == null) {
      return false;
    }

    if ("".equals(idCardNumber.trim())) {
      return false;
    }

    String idCardNumberTrim = idCardNumber.trim();
    String idPattern = "^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\\d{4}((19\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))|(19\\d{2}(0[13578]|1[02])31)|(19\\d{2}02(0[1-9]|1\\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\\d{3}(\\d|X|x)?$";
    Pattern patternId = Pattern.compile(idPattern);
    Matcher matcherId = patternId.matcher(idCardNumberTrim);
    if (!matcherId.matches()) {
      return false;
    }

    int temp = 0;
    if (idCardNumberTrim.length() == 18) {
      char[] idArray = idCardNumberTrim.toCharArray();

      for (int i = 0; i < idArray.length - 1; i++) {
        String valueOf = String.valueOf(idArray[i]);
        int parseInt = Integer.parseInt(valueOf);
        temp += parseInt * idsArray[i];
      }
      int temp2 = temp % 11;
      String lastChar = "";
      switch (temp2) {
      case 0:
        lastChar = "1";
        break;
      case 1:
        lastChar = "0";
        break;
      case 2:
        lastChar = "X";
        break;
      case 3:
        lastChar = "9";
        break;
      case 4:
        lastChar = "8";
        break;
      case 5:
        lastChar = "7";
        break;
      case 6:
        lastChar = "6";
        break;
      case 7:
        lastChar = "5";
        break;
      case 8:
        lastChar = "4";
        break;
      case 9:
        lastChar = "3";
        break;
      case 10:
        lastChar = "2";
      }

      char charAtLast = idCardNumberTrim.charAt(17);
      if (!(""+charAtLast).equalsIgnoreCase(lastChar)) {
        return false;
      }
    }

    return true;
  }

  public static String judgePassLevel(String string)
  {
    String str1 = "弱";
    String str2 = "中";
    String str3 = "强";
    if (string.length() < 5) {
      return str1;
    }

    int num = judgePassNum(string);
    if ((num <= 0) || (num == 1))
      return str1;
    if (num == 2) {
      return str2;
    }
    return str3;
  }

  public static int judgePassNum(String string)
  {
    String str1 = "^[0-9]$";
    String str2 = "^[a-z]$";
    String str3 = "^[A-Z]$";
    String str4 = "^[^0-9a-zA-Z]$";
    Pattern pattern1 = Pattern.compile(str1);
    Pattern pattern2 = Pattern.compile(str2);
    Pattern pattern3 = Pattern.compile(str3);
    Pattern pattern4 = Pattern.compile(str4);

    List list = new ArrayList();

    String[] split = string.split("");
    for (int i = 1; i < split.length; i++) {
      if ((pattern1.matcher(split[i]).matches()) && 
        (!list.contains(Integer.valueOf(1)))) {
        list.add(Integer.valueOf(1));
      }

      if ((pattern2.matcher(split[i]).matches()) && 
        (!list.contains(Integer.valueOf(2)))) {
        list.add(Integer.valueOf(2));
      }

      if ((pattern3.matcher(split[i]).matches()) && 
        (!list.contains(Integer.valueOf(3)))) {
        list.add(Integer.valueOf(3));
      }

      if ((pattern4.matcher(split[i]).matches()) && 
        (!list.contains(Integer.valueOf(4)))) {
        list.add(Integer.valueOf(4));
      }
    }

    return list.size();
  }
}