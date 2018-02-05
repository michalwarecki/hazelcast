/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.map.impl;

import com.hazelcast.map.impl.record.Record;

import static com.hazelcast.nio.Bits.INT_SIZE_IN_BYTES;
import static com.hazelcast.util.JVMUtil.OBJECT_HEADER_IN_BYTES;
import static com.hazelcast.util.JVMUtil.REFERENCE_COST_IN_BYTES;

/**
 * Size estimator for map.
 *
 * @param <T> : An instance of {@link com.hazelcast.map.impl.record.Record}.
 */
class MapSizeEstimator<T extends Record> implements SizeEstimator<T> {

    private volatile long size;

    public long getSize() {
        return size;
    }

    public void add(long size) {
        this.size += size;
    }

    public void reset() {
        size = 0;
    }

    public long getCost(T record) {
        if (record == null) {
            return 0L;
        }
        long cost = record.getCost();
        if (cost == 0L) {
            return cost;
        }

        return cost
                // key header
                + OBJECT_HEADER_IN_BYTES
                // value header
                + OBJECT_HEADER_IN_BYTES

                // CHM entry costs
                + INT_SIZE_IN_BYTES + (3 * REFERENCE_COST_IN_BYTES)
                // CHM entry header
                + OBJECT_HEADER_IN_BYTES;


    }
}