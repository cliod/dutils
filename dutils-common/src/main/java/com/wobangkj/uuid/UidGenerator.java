/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
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
package com.wobangkj.uuid;

import com.wobangkj.exception.UidGenerateException;

/**
 * Represents a unique id generator.
 *
 * @author yutianbao
 */
@FunctionalInterface
public interface UidGenerator {

	/**
	 * Get a unique ID
	 *
	 * @return UID
	 * @throws UidGenerateException 异常
	 */
	long getUid() throws UidGenerateException;

	/**
	 * Parse the UID into elements which are used to generate the UID. <br>
	 * Such as timestamp & workerId & sequence...
	 *
	 * @param uid uid
	 * @return Parsed info
	 */
	default String parseUid(long uid) {
		return String.format("{\"uid\": \"%d\"}", getUid());
	}

	/**
	 * Parse the UID into elements which are used to generate the UID. <br>
	 * Such as timestamp & workerId & sequence...
	 *
	 * @return Parsed info
	 */
	default String parseUid() {
		return parseUid(getUid());
	}
}
