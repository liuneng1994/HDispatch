package hdispatch.core.db


import com.hand.hap.liquibase.MigrationHelper
import com.hand.hap.db.excel.ExcelDataLoader
def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()

databaseChangeLog(logicalFilePath:"2016-11-07-init-data-migration.groovy"){
