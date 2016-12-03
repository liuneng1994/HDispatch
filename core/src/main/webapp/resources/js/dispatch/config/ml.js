/**
 * Created by liuneng on 2016/12/2.
 */

function $$l(code, zh, en) {
    switch (_locale) {
        case 'zh_CN':
            $l(code, zh);
            break;
        case 'en_GB' :
            $l(code.en);
            break;
    }
}

$$l('hdispatch.workflow.tip.jobName', '任务名称已存在', 'Job name has existed');
$$l('hdispatch.workflow.generateFlow', '生成新任务流', 'Generate new workflow');
$$l('hdispatch.workflow.tip.createflow', '任务流新建成功,是否立即生成', 'Workflow create success, did generate now?');
$$l('hdispatch.workflow.tip.saveflow', '任务流保存成功,是否立即生成', 'Workflow save success, did generate now?');
$$l('hdispatch.grid_find_no_data', '未找到任何数据', 'find nothing relative');
$$l('hdispatch.grid_data_total_num', '共', 'total');
$$l('hdispatch.grid_data_records', '条数据', 'records');
$$l('hdispatch.grid_page', '页', 'page');
$$l('hdispatch.grid_pages_per_page', '条每页', 'records/page');
$$l('hdispatch.grid_first_page', '第一页', 'first page');
$$l('hdispatch.grid_pre_page', '前一页', 'pre page');
$$l('hdispatch.grid_next_page', '后一页', 'next page');
$$l('hdispatch.grid_last_page', '最后一页', 'last page');
$$l('hdispatch.grid_refresh', '刷新', 'refresh');
$$l('hdispatch.workflow','任务流','workflow');
$$l('hdispatch.theme','任务组','theme');
$$l('hdispatch.layer','层次','layer');
$$l('hdispatch.tip.noAuthority', '没有操作权限', 'do not have operation authority');
$$l('hdispatch.tip.noflow','未生成工作流', 'No generated workflow');
$$l('hdispatch.workflow.scheduleFail','计划失败', 'schedule fail');
$$l('hdispatch.workflow.scheduleSuccess','计划成功', 'schedule success');