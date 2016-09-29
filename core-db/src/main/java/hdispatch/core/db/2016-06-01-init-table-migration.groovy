package hbi.core.db.table


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"hdispatch/core/db/2016-06-01-init-migration.groovy"){


    changeSet(author: "jessen", id: "20160601-hailor-1") {

        if(mhi.isDbType('oracle')){
            createSequence(sequenceName: 'HAP_DEMO_S')
        }
        createTable(tableName: "HAP_DEMO") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "name", type: "VARCHAR(100)") {
            }

            column(name: "object_version_number", type: "BIGINT", defaultValue : "1")
            column(name: "request_id", type: "BIGINT", defaultValue : "-1")
            column(name: "program_id", type: "BIGINT", defaultValue : "-1")
            column(name: "created_by", type: "BIGINT", defaultValue : "-1")
            column(name: "creation_date", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "last_updated_by", type: "BIGINT", defaultValue : "-1")
            column(name: "last_update_date", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "last_update_login", type: "BIGINT", defaultValue : "-1")
            column(name: "attribute_category", type: "VARCHAR(30)")
            column(name: "attribute1", type: "VARCHAR(240)")
            column(name: "attribute2", type: "VARCHAR(240)")
            column(name: "attribute3", type: "VARCHAR(240)")
            column(name: "attribute4", type: "VARCHAR(240)")
            column(name: "attribute5", type: "VARCHAR(240)")
            column(name: "attribute6", type: "VARCHAR(240)")
            column(name: "attribute7", type: "VARCHAR(240)")
            column(name: "attribute8", type: "VARCHAR(240)")
            column(name: "attribute9", type: "VARCHAR(240)")
            column(name: "attribute10", type: "VARCHAR(240)")
            column(name: "attribute11", type: "VARCHAR(240)")
            column(name: "attribute12", type: "VARCHAR(240)")
            column(name: "attribute13", type: "VARCHAR(240)")
            column(name: "attribute14", type: "VARCHAR(240)")
            column(name: "attribute15", type: "VARCHAR(240)")

        }

    }
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