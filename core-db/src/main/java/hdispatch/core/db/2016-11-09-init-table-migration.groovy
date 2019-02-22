package hdispatch.core.db


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2016-11-09-init-table-migration.groovy"){
    changeSet(author: " young", id: "20161109-table-hdispatch-group-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_group.sql"), encoding: "UTF-8")
    }
    changeSet(author: " young", id: "20161109-table-hdispatch-group-dependency-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_group_dependency.sql"), encoding: "UTF-8")
    }
    changeSet(author: " young", id: "20161109-table-hdispatch-job-property-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_job_property.sql"), encoding: "UTF-8")
    }
    changeSet(author: " young", id: "20161109-table-hdispatch-schedule-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_schedule.sql"), encoding: "UTF-8")
    }

    changeSet(author: " young", id: "20161109-table-hdispatch-workflow-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_workflow.sql"), encoding: "UTF-8")
    }
    changeSet(author: " young", id: "20161109-table-hdispatch-workflow-job-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_workflow_job.sql"), encoding: "UTF-8")
    }
    changeSet(author: " young", id: "20161109-table-hdispatch-workflow-property-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_workflow_property.sql"), encoding: "UTF-8")
    }
    changeSet(author: " young", id: "20161109-table-mut-flows-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/mut_flows.sql"), encoding: "UTF-8")
    }
    changeSet(author: " young", id: "20161109-table-dep-flows-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/dep_flows.sql"), encoding: "UTF-8")
    }
}