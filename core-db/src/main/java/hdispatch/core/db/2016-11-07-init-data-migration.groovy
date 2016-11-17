package hdispatch.core.db


import com.hand.hap.liquibase.MigrationHelper
import com.hand.hap.db.excel.ExcelDataLoader
def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()

databaseChangeLog(logicalFilePath:"2016-11-07-init-data-migration.groovy"){
//    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-theme-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_theme.sql"), encoding: "UTF-8")
//    }
//    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-layer-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_layer.sql"), encoding: "UTF-8")
//    }
//    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-job-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_job.sql"), encoding: "UTF-8")
//    }
//    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-fnd-schedule-parameter-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_fnd_schedule_parameter.sql"), encoding: "UTF-8")
//    }
//    //权限验证函数需要单独导入
////    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-permission-function-1") {
////        sqlFile(path: MigrationHelper.getInstance().dataPath("hbi/core/db/data/mysql/init/hdispatch_permission_function.sql"), encoding: "UTF-8")
////    }
//    //多语言数据
//    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-init-sys-prompts-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/sys_prompts_hdispatch_init_yyz_config.sql"), encoding: "UTF-8")
//    }
//
//    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-themegroup-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_themegroup.sql"), encoding: "UTF-8")
//    }
//    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-themegroup-theme-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_themegroup_theme.sql"), encoding: "UTF-8")
//    }
//    changeSet(author: "yazheng young", id: "20161107-data-hdispatch-authority-1") {
//        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_authority.sql"), encoding: "UTF-8")
//    }
}
