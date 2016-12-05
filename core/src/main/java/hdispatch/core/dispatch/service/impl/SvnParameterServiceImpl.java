package hdispatch.core.dispatch.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.IBaseService;
import hdispatch.core.dispatch.dto.svn.SvnParameter;
import hdispatch.core.dispatch.mapper_hdispatch.SvnParameterMapper;
import hdispatch.core.dispatch.service.SvnParameterService;
import hdispatch.core.dispatch.utils.ConfigUtil;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务运行时参数service接口实现类<br>
 * Created by yyz on 2016/9/24.
 * @author yazheng.yang@hand-china.com
 */
@Service
public class SvnParameterServiceImpl extends HdispatchBaseServiceImpl<SvnParameter> implements SvnParameterService {
    private Logger logger = Logger.getLogger(SvnParameterServiceImpl.class);
    @Autowired
    private SvnParameterMapper svnParameterMapper;

    /**
     * 根据SvnParameter进行模糊查询
     * @param requestContext
     * @param svnParameter
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public List<SvnParameter> selectBySvnParameter(IRequest requestContext, SvnParameter svnParameter, int page, int pageSize) {
        return super.select(requestContext,svnParameter,page,pageSize);
    }

    /**
     * 检查是否已经在数据库中存在
     * @param svnParameterList
     * @return
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public boolean[] checkIsExist(List<SvnParameter> svnParameterList) {
        boolean[] isExist = new boolean[svnParameterList.size()];
        int i = 0;
        for (SvnParameter svnParameter : svnParameterList) {
            List<SvnParameter> svnParameterListReturn = svnParameterMapper.selectForCheck(svnParameter);
            if (null != svnParameterListReturn && svnParameterListReturn.size() > 0) {
                isExist[i] = true;
            }
            i++;
        }

        return isExist;
    }

    /**
     * 批量操作：插入、删除、更新
     * @param requestContext
     * @param svnParameterList
     * @return
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public List<SvnParameter> batchUpdate(IRequest requestContext,@StdWho List<SvnParameter> svnParameterList, Map<String,String> feedbackMsg) {
        IBaseService<SvnParameter> self = ((IBaseService<SvnParameter>) AopContext.currentProxy());
        for (SvnParameter svnParameter : svnParameterList) {
            if (svnParameter.get__status() != null) {
                switch (svnParameter.get__status()) {
                    case DTOStatus.ADD:
                        if(isExistParameter(svnParameter)){
                            self.updateByPrimaryKey(requestContext,svnParameter);
                            svnParameter.set__status("");
                        }
                        else {
                            svnParameter.setEnableFlag("Y");
                            svnParameter.setStartDateActive(new Date());
                            self.insert(requestContext,svnParameter);
                            svnParameter.set__status("");
                        }
                        break;
                    case DTOStatus.UPDATE:
                        self.updateByPrimaryKey(requestContext,svnParameter);
                        svnParameter.set__status("");
                        break;
                    case DTOStatus.DELETE:
                        svnParameterMapper.deleteById(svnParameter);
                        break;
                    default:
                        break;
                }
            }
        }
        return svnParameterList;
    }


    private List<SvnParameter> excelFileToList(CommonsMultipartFile file) throws Exception{
        List<SvnParameter> list = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
//        XSSFWorkbook xwb = null;
        Workbook xwb = null;
        String originalFilename = file.getOriginalFilename();
        if (originalFilename.endsWith("xlsx")){
            //2007
            xwb = new XSSFWorkbook(inputStream);
        }else if (originalFilename.endsWith("xls")){
            //2003
            xwb = new HSSFWorkbook(inputStream);
        }else {
            return list;
        }

//        XSSFSheet sheet = xwb.getSheetAt(0);
        Sheet sheet = xwb.getSheetAt(0);
//        XSSFRow row = null;
        Row row = null;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        // 循环输出表格中的从第二行开始内容
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        int firstRowNum = sheet.getFirstRowNum();
        SvnParameter svnParameter = null;
        for (int i=sheet.getFirstRowNum()+1; i <= sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if(null == row){
                break;
            }
            short firstCellNumIndex = row.getFirstCellNum();
            if (row != null) {
                Cell cell_1 = row.getCell(firstCellNumIndex);
                Cell cell_2 = row.getCell(firstCellNumIndex+1);
                Cell cell_3 = row.getCell(firstCellNumIndex+2);
                Cell cell_4 = row.getCell(firstCellNumIndex+3);
                Cell cell_5 = row.getCell(firstCellNumIndex+4);
                Cell cell_6 = row.getCell(firstCellNumIndex+5);
                Cell cell_7 = row.getCell(firstCellNumIndex+6);
                Cell cell_8 = row.getCell(firstCellNumIndex+7);
                Cell cell_9 = row.getCell(firstCellNumIndex+8);

                svnParameter = new SvnParameter();
                boolean isFormatError = false;
                String errorMsg = "";
                if(null != cell_1){
//                    svnParameter.setParameterName(cell_1.toString());
                    svnParameter.setSubjectName(cell_1.toString());
                    if(cell_1.toString().trim().equals("")){
//                        isFormatError = true;
//                        errorMsg += "第"+(i+1)+"行，第1列数据缺失或格式有误\n";
                        //防止无限读取空行
                        break;
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第1列数据缺失或格式有误\n";
                }
                if(null != cell_2){
//                    svnParameter.setSubjectName(cell_2.toString());
                    svnParameter.setMappingName(cell_2.toString());
                    if(cell_2.toString().trim().equals("")){
                        isFormatError = true;
                        errorMsg += "第"+(i+1)+"行，第2列数据缺失或格式有误\n";
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第2列数据缺失或格式有误\n";
                }
                if(null != cell_3){
//                    svnParameter.setMappingName(cell_3.toString());
                    svnParameter.setSessionName(cell_3.toString());
                    if(cell_3.toString().trim().equals("")){
                        isFormatError = true;
                        errorMsg += "第"+(i+1)+"行，第3列数据缺失或格式有误\n";
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第3列数据缺失或格式有误\n";
                }
                if(null != cell_4){
//                    svnParameter.setSessionName(cell_4.toString());
                    svnParameter.setWorkFlowName(cell_4.toString());
                    if(cell_4.toString().trim().equals("")){
                        isFormatError = true;
                        errorMsg += "第"+(i+1)+"行，第4列数据缺失或格式有误\n";
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第4列数据缺失或格式有误\n";
                }
                if(null != cell_5){
//                    svnParameter.setWorkFlowName(cell_5.toString());
                    svnParameter.setParameterName(cell_5.toString());
                    if(cell_5.toString().trim().equals("")){
                        isFormatError = true;
                        errorMsg += "第"+(i+1)+"行，第5列数据缺失或格式有误\n";
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第5列数据缺失或格式有误\n";
                }
                if(null != cell_6){
                    svnParameter.setParameterValue(cell_6.toString());
                    if(cell_6.toString().trim().equals("")){
                        isFormatError = true;
                        errorMsg += "第"+(i+1)+"行，第6列数据缺失或格式有误\n";
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第6列数据缺失或格式有误\n";
                }
                if(null != cell_7){
                    svnParameter.setFormatMask(cell_7.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第7列数据缺失或格式有误\n";
                }
                if(null != cell_8){
                    try{
                        if(cell_8.getCellType()==Cell.CELL_TYPE_NUMERIC){
                            String cellValue = String.valueOf(cell_8.getNumericCellValue());
                            double parseDouble = Double.parseDouble(cellValue);
                            int value = (int)parseDouble;
                            svnParameter.setParaOffset(Long.valueOf(value));
                        }else {
                            svnParameter.setParaOffset(Long.parseLong(cell_8.toString()));
                        }
                    }catch (Exception e){
                        isFormatError = true;
                        errorMsg += "第"+(i+1)+"行，第8列数据缺失或格式有误\n";
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第8列数据缺失或格式有误\n";
                }
                if(null != cell_9){
                    svnParameter.setFrequency(cell_9.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第9列数据缺失或格式有误\n";
                }

                if(isFormatError){
                    throw new Exception("Excel数据格式有误:\n"+errorMsg);
                }
                svnParameter.set__status(DTOStatus.ADD);
                svnParameter.setEnableFlag("Y");
                svnParameter.setStartDateActive(new Date());
                list.add(svnParameter);
            }

        }
        return list;
    }

    @Override
    @Transactional(transactionManager = "hdispatchTM",rollbackFor = Exception.class)
    public List<SvnParameter> batchCreateFromExcel(IRequest requestContext,CommonsMultipartFile[] files, Map<String,String> feedbackMsg) throws Exception {
//        ArrayList<SvnParameter> svnParameters = new ArrayList<>();
//        for(CommonsMultipartFile file : files){
//            List<SvnParameter> parameterList = excelFileToList(file);
//            svnParameters.addAll(parameterList);
//        }
//        return batchUpdate(null,svnParameters);
        List<SvnParameter> list = null;
        try {
            list = excelFileToList(files[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(feedbackMsg.get("ERROR_DURING_SAVE"));
        }
//        preAddHandle(list);
        return batchUpdate(requestContext,list,feedbackMsg);
    }

    /**
     * 根据subjectName、mappingName、parameterName判断是否已经存在,
     * 如果已经存在，那个直接将add状态变为update状态
     * @param svnParameterList
     * @return
     */
    @Override
    @Transactional("hdispatchTM")
    public void preAddHandle(List<SvnParameter> svnParameterList) {
        for(SvnParameter parameter : svnParameterList){
            SvnParameter temp = new SvnParameter();
            temp.setSubjectName(parameter.getSubjectName()).
                    setMappingName(parameter.getMappingName()).
                    setParameterName(parameter.getParameterName());
            List<SvnParameter> returnList = svnParameterMapper.selectForCheck_2(temp);
            if(null != returnList && 0 < returnList.size()){
                parameter.set__status(DTOStatus.UPDATE);
                //id回写，防止页面中缺失id
                if(null == parameter.getScheduleParaId() || 0 == parameter.getScheduleParaId().longValue()){
                    parameter.setScheduleParaId(returnList.get(0).getScheduleParaId());
                }
            }
        }
    }

    /**
     * 判断当前用户是否有操作任务运行时参数的权限<br>
     * @param requestContext
     * @return
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public boolean hasOperatePermission(IRequest requestContext) {
        String themeGroupName = ConfigUtil.getJobRuntimeParameter_themeGroupName();
        if(null == themeGroupName){
            logger.error("任务运行时参数（权限控制）：读取不到挂载任务参数的主题组名称",
                    new RuntimeException("任务运行时参数（权限控制）：读取不到挂载任务参数的主题组名称"));
        }
        Long rows = svnParameterMapper.hasOperatePermission(themeGroupName);
        if(null == rows || rows < 1){
            return false;
        }
        return true;
    }

    /**
     * 判断当前用户是否有读取任务运行时参数的权限<br>
     * @param requestContext
     * @return
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public boolean hasReadPermission(IRequest requestContext) {
        String themeGroupName = ConfigUtil.getJobRuntimeParameter_themeGroupName();
        if(null == themeGroupName){
            logger.error("任务运行时参数（权限控制）：读取不到挂载任务参数的主题组名称",
                    new RuntimeException("任务运行时参数（权限控制）：读取不到挂载任务参数的主题组名称"));
        }
        Long rows = svnParameterMapper.hasReadPermission(themeGroupName);
        if(null == rows || rows < 1){
            return false;
        }
        return true;
    }


    /**
     * 根据subjectName、mappingName、parameterName判断是否已经存在,
     * @param svnParameter
     * @return
     */
    @Override
    @Transactional(transactionManager = "hdispatchTM",propagation = Propagation.SUPPORTS)
    public boolean isExistParameter(SvnParameter svnParameter) {
        SvnParameter temp = new SvnParameter();
        temp.setSubjectName(svnParameter.getSubjectName()).
                setMappingName(svnParameter.getMappingName()).
                setParameterName(svnParameter.getParameterName());
        List<SvnParameter> returnList = svnParameterMapper.selectForCheck_2(temp);

        return null != returnList && 0 < returnList.size();
    }
}
