package hdispatch.core.db.table


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()

databaseChangeLog(logicalFilePath:"hdispatch/core/db/2016-06-01-init-data.groovy"){


    changeSet(author: "yourname", id: "20160601-yourname-1") {
            //sqlFile(path: MigrationHelper.getInstance().dataPath("com/hand/hap/db/data/"+dbType+"/demo.sql"), encoding: "UTF-8")
    }

    changeSet(author: "yangyazheng", id: "20160907-yangyazheng-3") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_theme.sql"), encoding: "UTF-8")
    }
    changeSet(author: "yangyazheng", id: "20160907-yangyazheng-4") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("hdispatch/core/db/data/mysql/init/hdispatch_layer.sql"), encoding: "UTF-8")
    }
}
