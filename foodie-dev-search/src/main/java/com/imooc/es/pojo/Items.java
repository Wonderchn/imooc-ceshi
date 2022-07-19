package com.imooc.es.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "foodie-items-ik", type = "_doc", createIndex = false)
public class Items {

    @Id
    @Field(store = true,type = FieldType.Text ,index =false)
    private String itemId;

    @Field(store = true,type = FieldType.Text ,index =true)
    private String itemName;

    @Field(store = true,type = FieldType.Text ,index =false)
    private Integer imgUrl;

    @Field(store = true,type = FieldType.Integer )
    private Integer price;

    @Field(store = true, type = FieldType.Integer)
    private String sellCounts;


}
