package com.example.cheography.demo;

import io.confluent.ksql.function.*;
import io.confluent.ksql.metastore.MetaStore;
import io.confluent.ksql.metastore.model.DataSource;
import io.confluent.ksql.name.FunctionName;
import io.confluent.ksql.name.SourceName;
import io.confluent.ksql.schema.ksql.SqlArgument;
import io.confluent.ksql.schema.ksql.types.SqlType;

import java.util.*;

public class ksqlMetaStore implements MetaStore {
    @Override
    public DataSource getSource(SourceName sourceName) {
        return null;
    }

    @Override
    public Map<SourceName, DataSource> getAllDataSources() {
        return null;
    }

    @Override
    public Set<SourceName> getSourceConstraints(SourceName sourceName) {
        return null;
    }

    @Override
    public MetaStore copy() {
        return null;
    }

    @Override
    public boolean isAggregate(FunctionName functionName) {
        return false;
    }

    @Override
    public boolean isTableFunction(FunctionName functionName) {
        return false;
    }

    @Override
    public boolean isPresent(FunctionName functionName) {
        return false;
    }

    @Override
    public UdfFactory getUdfFactory(FunctionName functionName) {
        return null;
    }

    @Override
    public TableFunctionFactory getTableFunctionFactory(FunctionName functionName) {
        return null;
    }

    @Override
    public AggregateFunctionFactory getAggregateFactory(FunctionName functionName) {
        return null;
    }

    @Override
    public KsqlAggregateFunction<?, ?, ?> getAggregateFunction(FunctionName functionName, SqlType sqlType, AggregateFunctionInitArguments aggregateFunctionInitArguments) {
        return null;
    }

    @Override
    public KsqlTableFunction getTableFunction(FunctionName functionName, List<SqlArgument> list) {
        return null;
    }

    @Override
    public List<UdfFactory> listFunctions() {
        return null;
    }

    @Override
    public List<TableFunctionFactory> listTableFunctions() {
        return null;
    }

    @Override
    public List<AggregateFunctionFactory> listAggregateFunctions() {
        return null;
    }

    @Override
    public boolean registerType(String name, SqlType type) {
        return false;
    }

    @Override
    public boolean deleteType(String name) {
        return false;
    }

    @Override
    public Optional<SqlType> resolveType(String name) {
        return Optional.empty();
    }

    @Override
    public Iterator<CustomType> types() {
        return null;
    }
}
