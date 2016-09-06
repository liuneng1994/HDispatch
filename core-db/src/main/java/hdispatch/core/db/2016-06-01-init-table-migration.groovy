package hdispatch.core.db.table


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath: "hdispatch/core/db/2016-06-01-init-migration.groovy") {


    changeSet(author: "jessen", id: "20160601-hailor-1") {

        if (mhi.isDbType('oracle')) {
            createSequence(sequenceName: 'HAP_DEMO_S')
        }
        createTable(tableName: "HAP_DEMO") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "name", type: "VARCHAR(100)") {
            }

            column(name: "object_version_number", type: "BIGINT", defaultValue: "1")
            column(name: "request_id", type: "BIGINT", defaultValue: "-1")
            column(name: "program_id", type: "BIGINT", defaultValue: "-1")
            column(name: "created_by", type: "BIGINT", defaultValue: "-1")
            column(name: "creation_date", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "last_updated_by", type: "BIGINT", defaultValue: "-1")
            column(name: "last_update_date", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "last_update_login", type: "BIGINT", defaultValue: "-1")
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
}
