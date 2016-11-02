package hdispatch.core.db.table


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()

databaseChangeLog(logicalFilePath:"hdispatch/core/db/2016-06-01-init-data.groovy"){

}
