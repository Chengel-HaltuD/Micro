/*
 * Copyright (c) 2013  Chengel_HaltuD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.micro.view;

import android.content.Context;
import android.view.animation.AnimationUtils;

/**
 * 
 * @ClassName: ResLoader
 * @Description: TODO
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:19:28
 * @version V1.0
 *
 */
public class ResLoader {

    public static Object loadRes(ResType type, Context context, int id) {
        if (context == null || id < 1) return null;
        switch (type) {
            case Animation:
                return AnimationUtils.loadAnimation(context, id);
            case Boolean:
                return context.getResources().getBoolean(id);
            case Color:
                return context.getResources().getColor(id);
            case ColorStateList:
                return context.getResources().getColorStateList(id);
            case Dimension:
                return context.getResources().getDimension(id);
            case DimensionPixelOffset:
                return context.getResources().getDimensionPixelOffset(id);
            case DimensionPixelSize:
                return context.getResources().getDimensionPixelSize(id);
            case Drawable:
                return context.getResources().getDrawable(id);
            case Integer:
                return context.getResources().getInteger(id);
            case IntArray:
                return context.getResources().getIntArray(id);
            case Movie:
                return context.getResources().getMovie(id);
            case String:
                return context.getResources().getString(id);
            case StringArray:
                return context.getResources().getStringArray(id);
            case Text:
                return context.getResources().getText(id);
            case TextArray:
                return context.getResources().getTextArray(id);
            case Xml:
                return context.getResources().getXml(id);
            default:
                break;
        }

        return null;
    }
}
