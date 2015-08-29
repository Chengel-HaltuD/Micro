/*
 * Copyright (C) 2013 www.jryghq.com
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
package com.demo;
/**
 * @ClassName: DimensionGuide
 * @Description: TODO
 * @Author：GeLe
 * @Date：2015-5-28 上午11:49:37
 * @version V1.0
 *
 */
public class DimensionGuide {

	private String Times;
	private String Message;
	private String Code;
	private Data Data;

	public class Data{
		private String FilePath;
		private String Count;
		private String Result;
		private String ResultInfo;
		public String getFilePath() {
			return FilePath;
		}
		public void setFilePath(String filePath) {
			FilePath = filePath;
		}
		public String getCount() {
			return Count;
		}
		public void setCount(String count) {
			Count = count;
		}
		public String getResult() {
			return Result;
		}
		public void setResult(String result) {
			Result = result;
		}
		public String getResultInfo() {
			return ResultInfo;
		}
		public void setResultInfo(String resultInfo) {
			ResultInfo = resultInfo;
		}
		@Override
		public String toString() {
			return "Data [FilePath=" + FilePath + ", Count=" + Count
					+ ", Result=" + Result + ", ResultInfo=" + ResultInfo + "]";
		}
		
	}

	public String getTimes() {
		return Times;
	}

	public void setTimes(String times) {
		Times = times;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public Data getData() {
		return Data;
	}

	public void setData(Data data) {
		Data = data;
	}

	@Override
	public String toString() {
		return "DimensionGuide [Times=" + Times + ", Message=" + Message
				+ ", Code=" + Code + ", Data=" + Data + "]";
	}

	
}
