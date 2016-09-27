package hdispatch.core.dispatch.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import hdispatch.core.dispatch.dto.svn.SvnParameter;
import hdispatch.core.dispatch.mapper.SvnParameterMapper;
import hdispatch.core.dispatch.service.SvnParameterService;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yyz on 2016/9/24.
 *
 * @author yazheng.yang@hand-china.com
 */
@Service
public class SvnParameterServiceImpl implements SvnParameterService {
    private Logger logger = Logger.getLogger(SvnParameterServiceImpl.class);
    @Autowired
    private SvnParameterMapper svnParameterMapper;

    /**
     * 根据SvnParameter进行查询
     * @param requestContext
     * @param svnParameter
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<SvnParameter> selectBySvnParameter(IRequest requestContext, SvnParameter svnParameter, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SvnParameter> list;
        if (null == svnParameterMapper) {
            list = new ArrayList<SvnParameter>();
            logger.error("svnParameterMapper没有注入");
        } else {
            list = svnParameterMapper.selectBySvnParameter(svnParameter);
        }
        return list;
    }

    /**
     * 检查是否已经在数据库中存在
     * @param svnParameterList
     * @return
     */
    @Override
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
    public List<SvnParameter> batchUpdate(IRequest requestContext,@StdWho List<SvnParameter> svnParameterList) {
        for (SvnParameter svnParameter : svnParameterList) {
            if (svnParameter.get__status() != null) {
                switch (svnParameter.get__status()) {
                    case DTOStatus.ADD:
                        svnParameter.setCreationDate(new Date());
                        svnParameter.setLastUpdateDate(new Date());
                        svnParameterMapper.create(svnParameter);
                        break;
                    case DTOStatus.UPDATE:
                        svnParameter.setLastUpdateDate(new Date());
                        svnParameterMapper.updateById(svnParameter);
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
        XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row = null;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        // 循环输出表格中的从第二行开始内容
        SvnParameter svnParameter = null;
        for (int i=sheet.getFirstRowNum()+1; i <= sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if(null == row){
                break;
            }
            short firstCellNumIndex = row.getFirstCellNum();
            if (row != null) {
                XSSFCell cell_1 = row.getCell(firstCellNumIndex);
                XSSFCell cell_2 = row.getCell(firstCellNumIndex+1);
                XSSFCell cell_3 = row.getCell(firstCellNumIndex+2);
                XSSFCell cell_4 = row.getCell(firstCellNumIndex+3);
                XSSFCell cell_5 = row.getCell(firstCellNumIndex+4);
                XSSFCell cell_6 = row.getCell(firstCellNumIndex+5);
                XSSFCell cell_7 = row.getCell(firstCellNumIndex+6);
                XSSFCell cell_8 = row.getCell(firstCellNumIndex+7);
                XSSFCell cell_9 = row.getCell(firstCellNumIndex+8);
                XSSFCell cell_10 = row.getCell(firstCellNumIndex+9);
                XSSFCell cell_11 = row.getCell(firstCellNumIndex+10);
                XSSFCell cell_12 = row.getCell(firstCellNumIndex+11);
                svnParameter = new SvnParameter();
                boolean isFormatError = false;
                String errorMsg = "";
                if(null != cell_1){
                    svnParameter.setSubjectName(cell_1.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第1列数据缺失或格式有误\n";
                }
                if(null != cell_2){
                    svnParameter.setMappingName(cell_2.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第2列数据缺失或格式有误\n";
                }
                if(null != cell_3){
                    svnParameter.setSessionName(cell_3.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第3列数据缺失或格式有误\n";
                }
                if(null != cell_4){
                    svnParameter.setWorkFlowName(cell_4.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第4列数据缺失或格式有误\n";
                }
                if(null != cell_5){
                    svnParameter.setParameterName(cell_5.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第5列数据缺失或格式有误\n";
                }
                if(null != cell_6){
                    svnParameter.setParameterValue(cell_6.toString());
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
                        if(cell_8.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
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
                    try{
                        String cellValue = null;
                        if(cell_9.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
                            if(DateUtil.isCellDateFormatted(cell_9)){
                                cellValue = new DataFormatter().formatRawCellContents(cell_9.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                svnParameter.setStartDateActive(sdf.parse(cellValue));
                            }else {
                                isFormatError = true;
                                errorMsg += "第"+(i+1)+"行，第9列数据缺失或格式有误\n";
                            }
                        }
                    }catch (Exception e){
                        isFormatError = true;
                        errorMsg += "第"+(i+1)+"行，第9列数据缺失或格式有误\n";
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第9列数据缺失或格式有误\n";
                }
                if(null != cell_10){
                    try{
                        String cellValue = null;
                        if(cell_10.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
                            if(DateUtil.isCellDateFormatted(cell_10)){
                                cellValue = new DataFormatter().formatRawCellContents(cell_10.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                svnParameter.setEndDateActive(sdf.parse(cellValue));
                            }else {
                                isFormatError = true;
                                errorMsg += "第"+(i+1)+"行，第10列数据缺失或格式有误\n";
                            }
                        }
                    }catch (Exception e){
                        isFormatError = true;
                        errorMsg += "第"+(i+1)+"行，第10列数据缺失或格式有误\n";
                    }
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第10列数据缺失或格式有误\n";
                }
                if(null != cell_11){
                    svnParameter.setFrequency(cell_11.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第11列数据缺失或格式有误\n";
                }
                if(null != cell_12){
                    svnParameter.setEnableFlag(cell_12.toString());
                }
                else{
                    isFormatError = true;
                    errorMsg += "第"+(i+1)+"行，第12列数据缺失或格式有误\n";
                }
                if(isFormatError){
                    throw new Exception("Excel数据格式有误:\n"+errorMsg);
                }
                svnParameter.set__status(DTOStatus.ADD);
                list.add(svnParameter);
            }

        }
        return list;
    }

    public List<SvnParameter> batchCreateFromExcel(CommonsMultipartFile[] files) throws Exception{
//        ArrayList<SvnParameter> svnParameters = new ArrayList<>();
//        for(CommonsMultipartFile file : files){
//            List<SvnParameter> parameterList = excelFileToList(file);
//            svnParameters.addAll(parameterList);
//        }
//        return batchUpdate(null,svnParameters);
        return batchUpdate(null,excelFileToList(files[0]));
    }
}
