package hdispatch.core.db


import com.hand.hap.liquibase.MigrationHelper
import com.hand.hap.db.excel.ExcelDataLoader
def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2016-11-07-init-table-migration.groovy"){
    changeSet(author: "yazheng young", id: "20161107-table-hdispatch-theme-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_theme.sql"), encoding: "UTF-8")
    }
    changeSet(author: "yazheng young", id: "20161107-table-hdispatch-layer-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_layer.sql"), encoding: "UTF-8")
    }
    changeSet(author: "yazheng young", id: "20161107-table-hdispatch-job-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_job.sql"), encoding: "UTF-8")
    }
    changeSet(author: "yazheng young", id: "20161107-table-hdispatch-fnd-schedule-parameter-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_fnd_schedule_parameter.sql"), encoding: "UTF-8")
    }
    //需要单独执行
//    changeSet(author: "yazheng young", id: "20161107-table-hdispatch-permission-function-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hbi/core/db/data/mysql/tables/hdispatch_permission_function.sql"), encoding: "UTF-8")
//    }
    changeSet(author: "yazheng young", id: "20161107-table-hdispatch-themegroup-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_themegroup.sql"), encoding: "UTF-8")
    }
    changeSet(author: "yazheng young", id: "20161107-table-hdispatch-themegroup-theme-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_themegroup_theme.sql"), encoding: "UTF-8")
    }
    changeSet(author: "yazheng young", id: "20161107-table-hdispatch-authority-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/tables/hdispatch_authority.sql"), encoding: "UTF-8")
    }


}