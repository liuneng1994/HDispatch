/**
 * Created by liuneng on 2016/11/7.
 */

package hdispatch.core.db.table


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"hdispatch/core/db/2016-06-01-init-migration.groovy"){
}
