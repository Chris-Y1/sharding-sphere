/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
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
 * </p>
 */

package io.shardingsphere.transaction.manager.xa.convert;

import io.shardingsphere.core.rule.DataSourceParameter;

import javax.sql.DataSource;

/**
 * Get property of common datasource pool then convert to {@code DataSourceParameter}.
 *
 * @author zhaojun
 */
public class DataSourceParameterFactory {
    
    /**
     * Create datasource parameter.
     *
     * @param dataSource data source
     * @return datasource parameter
     */
    public static DataSourceParameter build(final DataSource dataSource) {
        switch (getPoolType(dataSource)) {
            case HIKARI:
            case DRUID:
            case DBCP:
                return new DBCPConverter().convertTo(dataSource);
            default:
                return null;
        }
    }
    
    private static PoolType getPoolType(final DataSource dataSource) {
        return null;
    }
}