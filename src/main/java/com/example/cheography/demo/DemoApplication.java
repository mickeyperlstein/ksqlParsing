package com.example.cheography.demo;

import io.confluent.ksql.parser.DefaultKsqlParser;
import io.confluent.ksql.parser.KsqlParser;
import io.confluent.ksql.parser.tree.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        String ksql = "CREATE STREAM shipped_orders AS " +
                "SELECT o.id as orderId, o.itemid as itemId, s.id as shipmentId, p.id as paymentId " +
                "FROM orders o " +
                "INNER JOIN payments p WITHIN 1 HOURS ON p.id = o.id " +
                "  INNER JOIN shipments s WITHIN 2 HOURS ON s.id = o.id;";
        getData(ksql);
    }

    public static void getData(String ksql){
        System.out.println("QUERY: "+ksql);
        KsqlParser parser = new DefaultKsqlParser();
        ksqlMetaStore ksqlMetaStore = new ksqlMetaStore();
        KsqlParser.ParsedStatement parsedStatement = parser.parse(ksql).get(0);
        KsqlParser.PreparedStatement preparedStatement = parser.prepare(parsedStatement, ksqlMetaStore);
        List<Cdata> myData = new ArrayList<>();

        if (preparedStatement.getStatement() instanceof InsertValues) {
            InsertValues InsertInto = (InsertValues) (preparedStatement.getStatement());
            myData.add(Cdata.builder().name(InsertInto.getTarget().toString()).action("Insert").type("Row").build());
        }

        if (preparedStatement.getStatement() instanceof CreateTable) {
            CreateTable createTable = (CreateTable) (preparedStatement.getStatement());
            myData.add(Cdata.builder().name(createTable.getName().toString()).action("CREATE").type("Table").build());
            myData.add(Cdata.builder().name(createTable.getProperties().getKafkaTopic()).action("READ").type("Topic").build());

        }
        if (preparedStatement.getStatement() instanceof CreateStreamAsSelect) {
            CreateStreamAsSelect createSteramAsSelectStatement = (CreateStreamAsSelect) preparedStatement.getStatement();
            if (((CreateStreamAsSelect) preparedStatement.getStatement()).getQuery().getFrom() instanceof Join) {
                Join joinRelation = (Join) ((CreateStreamAsSelect) preparedStatement.getStatement()).getQuery().getFrom();
                myData.add(Cdata.builder().name(createSteramAsSelectStatement.getName().toString()).action("CREATE").type("STREAM").build());

                joinRelation.getRights().forEach(st -> {
                    if (st.getRelation() instanceof AliasedRelation){
                        var name = ((AliasedRelation) (st.getRelation())).getRelation().toString();
                        myData.add(Cdata.builder().name(name).action("READ").type("STREAM").build());

                    }
                });
            }
            if ( createSteramAsSelectStatement.getQuery().getFrom() instanceof AliasedRelation) {

                AliasedRelation aliasedRelation = (AliasedRelation) createSteramAsSelectStatement.getQuery().getFrom();
                myData.add(Cdata.builder().name(createSteramAsSelectStatement.getName().toString()).action("CREATE").type("STREAM").build());
                myData.add(Cdata.builder().name(aliasedRelation.getRelation().toString()).action("READ").type("STREAM").build());
            }
        }

        System.out.println("MY DATA: "+ myData);
    }

}
