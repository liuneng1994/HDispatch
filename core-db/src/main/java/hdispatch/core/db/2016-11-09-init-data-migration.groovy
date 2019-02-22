package hdispatch.core.db


import com.hand.hap.liquibase.MigrationHelper
import com.hand.hap.db.excel.ExcelDataLoader
def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"hdispatch/core/db/2016-11-09-init-table-migration.groovy"){
    changeSet(author: " young", id: "20161109-init-data-xlsx", runAlways:"true"){
        customChange(class:ExcelDataLoader.class.name){
            param(name:"filePath",value:MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/2016-11-15-init-data.xlsx"))
        }
    }
}