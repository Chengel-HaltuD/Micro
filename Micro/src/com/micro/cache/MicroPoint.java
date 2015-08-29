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
package com.micro.cache;

/**
 * 
 * @ClassName: MicroPoint
 * @Description: TODO
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:32:25
 * @version V1.0
 *
 */
public class MicroPoint {
	
	public double x;
	public double y;
	
	public MicroPoint() {
		super();
	}

	public MicroPoint(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		MicroPoint point = (MicroPoint)o;
		if(this.x == point.x && this.y == point.y){
			return true;   
		}
        return false;   
	}

	@Override
	public int hashCode() {
		return (int)(x*y)^8;
	}
	
	
}
