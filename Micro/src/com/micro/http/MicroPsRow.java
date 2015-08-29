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
package com.micro.http;



// TODO: Auto-generated Javadoc
/**
 * 
 * @ClassName: MicroPsRow
 * @Description: 描述：PS后得到的行信息
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:52:30
 * @version V1.0
 *
 */
public class MicroPsRow {
	
	 public String pid;
	 public String cmd;
	 public String ppid;
	 public String user;
	 
	 /** 暂用的内存. */
	 public int mem;
	 
	 /** 主进程ID. */
	 public String rootPid;

     public MicroPsRow(String line) {
         if (line == null) return;
         String[] p = line.split("[\\s]+");
         if (p.length != 9) return;
         user = p[0];
         pid = p[1];
         ppid = p[2];
         mem = Integer.parseInt(p[4]);
         cmd = p[8];
         if (isRoot()) {
        	 rootPid = pid;
         }
     }

     public boolean isRoot() {
         return "zygote".equals(cmd);
     }

     public boolean isMain() {
         return ppid.equals(rootPid) && user.startsWith("app_");
     }

     public String toString() {
         final String TAB = ";";
         String retValue = "";
         retValue = "PsRow ( " + super.toString() + TAB + "pid = " + this.pid + TAB + "cmd = " + this.cmd
                 + TAB + "ppid = " + this.ppid + TAB + "user = " + this.user + TAB + "mem = " + this.mem
                 + " )";
         return retValue;
     }


}
