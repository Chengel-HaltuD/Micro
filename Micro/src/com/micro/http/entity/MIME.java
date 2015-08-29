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
package com.micro.http.entity;

import java.nio.charset.Charset;

/**
 * 
 * @ClassName: MIME
 * @Description: TODO
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:47:04
 * @version V1.0
 *
 */
public final class MIME {

    public static final String CONTENT_TYPE          = "Content-Type";
    public static final String CONTENT_TRANSFER_ENC  = "Content-Transfer-Encoding";
    public static final String CONTENT_DISPOSITION   = "Content-Disposition";

    public static final String ENC_8BIT              = "8bit";
    public static final String ENC_BINARY            = "binary";

    /** The default character set to be used, i.e. "US-ASCII" */
    public static final Charset DEFAULT_CHARSET      = Charset.forName("US-ASCII");

}
