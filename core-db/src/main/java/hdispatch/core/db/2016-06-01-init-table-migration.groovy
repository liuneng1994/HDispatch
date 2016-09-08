package hdispatch.core.db.table


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath: "hdispatch/core/db/2016-06-01-init-migration.groovy") {

    changeSet(author: "liuneng", id: "20160906-liuneng-1") {
        createTable(tableName: "HDISPATCH_GROUP") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true")
            }
            column(name: "group_name", type: "VARCHAR(128)")
            column(name: "layer_id", type: "BIGINT")
            column(name: "schedule_expression", type: "VARCHAR(256)")
            column(name: "flow_id", type: "VARCHAR(128)")
            column(name: "active", type: "TINYINT")
            column(name: "create_at", type: "DATETIME")
            column(name: "update_at", type: "DATETIME")
        }
    }

    changeSet(author: "liuneng", id: "20160906-liuneng-2") {
        createTable(tableName: "HDISPATCH_GROUP_DEPENDENCY") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true")
            }
            column(name: "group_id", type: "BIGINT")
            column(name: "depent_group_id", type: "BIGINT")
        }
    }

    changeSet(author: "yangyazheng", id: "20160907-yangyazheng-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_theme.sql"), encoding: "UTF-8")
    }
    changeSet(author: "yangyazheng", id: "20160907-yangyazheng-2") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_layer.sql"), encoding: "UTF-8")
    }

}