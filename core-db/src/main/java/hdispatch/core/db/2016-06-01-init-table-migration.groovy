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


//    changeSet(author: "yangyazheng", id: "20160927-yangyazheng-1") {
//        createTable(tableName: "hdispatch_fnd_schedule_parameter") {
//            column(autoIncrement: "true", name: "SCHEDULE_PARA_ID", type: "BIGINT") {
//                constraints(primaryKey: "true")
//            }
//            column(name: "PARAMETER_NAME", type: "VARCHAR(240)")
//            column(name: "PARAMETER_SORT", type: "INT(11)", defaultValue:NULL)
//            column(name: "SUBJECT_NAME", type: "VARCHAR(240)")NOT NULL
//            column(name: "MAPPING_NAME", type: "VARCHAR(240)")NOT NULL
//            column(name: "SESSION_NAME", type: "VARCHAR(240)")NOT NULL
//            column(name: "WORKFLOW_NAME", type: "VARCHAR(240)")NOT NULL
//            column(name: "PARAMETER_VALUE", type: "VARCHAR(4000)")NOT NULL
//            column(name: "FORMAT_MASK", type: "VARCHAR(30)")NULL
//            column(name: "PARA_OFFSET", type: "INT(11)", defaultValue:NULL)
//            column(name: "FREQUENCY", type: "VARCHAR(30)")NULL
//            column(name: "ENABLE_FLAG", type: "VARCHAR(1)")NULL
//            column(name: "START_DATE_ACTIVE", type: "DATETIME")NULL
//            column(name: "END_DATE_ACTIVE", type: "DATETIME")NULL
//            column(name: "CREATION_DATE", type: "DATETIME")CURRENT_TIMESTAMP
//            column(name: "CREATED_BY", type: "INT(11)", defaultValue:"-1")
//            column(name: "LAST_UPDATED_BY", type: "INT(11)", defaultValue:"-1")
//            column(name: "LAST_UPDATE_DATE", type: "DATETIME")CURRENT_TIMESTAMP
//            column(name: "LAST_UPDATE_LOGIN", type: "INT(11)", defaultValue:NULL)
//            column(name: "PARAMETER_DESC", type: "VARCHAR(240)")NULL
//            column(name: "PARAMETER_VALUE_INI", type: "VARCHAR(240)")NULL
//
//            column(name: "layer_id", type: "BIGINT")
//            column(name: "schedule_expression", type: "VARCHAR(256)")
//            column(name: "flow_id", type: "VARCHAR(128)")
//            column(name: "active", type: "TINYINT")
//            column(name: "create_at", type: "DATETIME")
//            column(name: "update_at", type: "DATETIME")
//        }
//    }

    changeSet(author: "yangyazheng", id: "20160927-yangyazheng-2") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_fnd_schedule_parameter"), encoding: "UTF-8")
    }
}