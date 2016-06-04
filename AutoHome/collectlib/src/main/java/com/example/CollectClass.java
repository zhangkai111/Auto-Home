package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class CollectClass {
    public static void main(String[] args) {
        //数据库图表
        //第一个是版本号,第二个参数是自动生成代码位置的包名
        Schema schema = new Schema(1, "com.lanou3g.autohome");
        addNote(schema);
        //自动生成代码
        //第一个参数是图表对象,第二个是自动生成代码的路径
        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 想数据库中添加所需的内容
     *
     * @param schema
     */

    public static void addNote(Schema schema) {
        //添加表名
        Entity entity = schema.addEntity("Collect");
        //添加id,并且id自增
        entity.addIdProperty().autoincrement().primaryKey();
        //添加类的属性,根据属性生成相应表中的字段
        entity.addStringProperty("url");
        entity.addStringProperty("imageUrl");
        entity.addStringProperty("title");
        entity.addStringProperty("time");
    }
}
