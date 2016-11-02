package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.svn.SvnParameter;
import hdispatch.core.dispatch.service.SvnParameterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * 任务运行时参数控制器<br>
 * Created by yyz on 2016/9/24.
 *
 * @author yazheng.yang@hand-china.com
 */
@Controller
public class SvnParameterController extends BaseController{
    private Logger logger = Logger.getLogger(SvnParameterController.class);
    @Autowired
    private SvnParameterService svnParameterService;

    /**
     * 模糊查询任务运行时参数
     * @param request
     * @param page
     * @param pageSize
     * @param subjectName
     * @param mappingName
     * @param parameterName
     * @return
     */
    @RequestMapping(value = "/dispatcher/svnParameter/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getSvnParameters(HttpServletRequest request,
                                @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                @RequestParam(defaultValue = "") String subjectName,
                                @RequestParam(defaultValue = "") String mappingName,
                                @RequestParam(defaultValue = "") String parameterName) {
        subjectName = subjectName.trim();
        mappingName = mappingName.trim();
        parameterName = parameterName.trim();
        if("".equals(subjectName)){
            subjectName = null;
        }
        if("".equals(mappingName)){
            mappingName = null;
        }
        if("".equals(parameterName)){
            parameterName = null;
        }
        SvnParameter svnParameter = new SvnParameter();
        svnParameter.setSubjectName(subjectName).
                setMappingName(mappingName).
                setParameterName(parameterName);
        IRequest requestContext = createRequestContext(request);
        List<SvnParameter> svnParameterList = svnParameterService.selectBySvnParameter(requestContext,svnParameter,page,pageSize);
        ResponseData rd = new ResponseData(svnParameterList);

        return rd;
    }


    /**
     * 创建SVN 参数
     *
     * description:检查待插入的svnParameter是否已经存在；若存在，给出提示信息；若不存在，执行插入
     *
     * 修改：2016.10.24 by yazheng.yang@hand-china.com
     *  ,根据subjectName、mappingName、parameterName判断是否已经存在，若存在，直接更新；若不存在，执行插入
     * @param svnParameterList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/svnParameter/submit", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData addSvnParameters(@RequestBody @StdWho List<SvnParameter> svnParameterList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
//        //后台验证
//        getValidator().validate(svnParameterList, result);
//        if (result.hasErrors()) {
//            rd = new ResponseData(false);
//            rd.setMessage(getErrorMessage(result, request));
//            return rd;
//        }
//
//        //从后台判断是否存在
//        boolean[] isExist = svnParameterService.checkIsExist(svnParameterList);
//        StringBuilder sb = new StringBuilder();
//        boolean flag = false;
//        for (int i = 0; i < svnParameterList.size(); i++) {
//            if (isExist[i]) {
//                if (!flag) {
//                    sb.append(svnParameterList.get(i).getParameterName());
//                } else {
//                    sb.append("," + svnParameterList.get(i).getParameterName());
//                }
//                flag = true;
//            }
//        }
//
//        if (flag) {
//            rd = new ResponseData(false);
//            //以下任务已经存在
//            String errorMsg = getMessageSource().getMessage("hdispatch.job.job_create.job_name_already_exist", null, locale);
//            rd.setMessage(errorMsg+":" + sb.toString());
//            return rd;
//        }
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        IRequest requestContext = createRequestContext(request);
        //数据是否存在过滤，如果已经存在，那么变为update
        svnParameterService.preAddHandle(svnParameterList);
        try {
            List<SvnParameter> returnList = svnParameterService.batchUpdate(requestContext, svnParameterList);
//            for(SvnParameter temp : returnList){
//                temp.set__status(DTOStatus.ADD);
//            }
            rd = new ResponseData(returnList);
        } catch (Exception e) {
            //保存任务中途失败
            String errorMsg = getMessageSource().getMessage("hdispatch.job.job_create.error_during_saving", null, locale);
            logger.error(errorMsg, e);
            rd = new ResponseData(false);
            rd.setMessage(errorMsg);
            return rd;
        }
        return rd;
    }

    /**
     * 批量删除任务运行时参数
     */
    @RequestMapping(value = "/dispatcher/svnParameter/remove", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData deleteSvnParameters(@RequestBody List<SvnParameter> svnParameterList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(svnParameterList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        //获取语言环境
        Locale locale = RequestContextUtils.getLocale(request);
        try {
            List<SvnParameter> svnParameterListReturn = svnParameterService.batchUpdate(requestContext, svnParameterList);
            rd = new ResponseData(true);
        } catch (Exception e) {
            //删除任务中途失败
            String errorMsg = getMessageSource().getMessage("hdispatch.job.job_create.error_during_deleting", null, locale);
            logger.error(errorMsg, e);
            rd = new ResponseData(false);
            rd.setMessage(errorMsg);
            return rd;
        }
        return rd;
    }

    /**
     * 批量更新
     * @param svnParameterList
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/svnParameter/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseData updateSvnParameters(@RequestBody List<SvnParameter> svnParameterList, BindingResult result, HttpServletRequest request) {

        ResponseData rd = null;
        //后台验证
        getValidator().validate(svnParameterList, result);
        if (result.hasErrors()) {
            rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        rd = new ResponseData(svnParameterService.batchUpdate(requestContext,svnParameterList));
        return rd;
    }


    /**
     * 从excel文件中导入参数。
     * 根据subjectName、mappingName、parameterName判断是否已经存在，若存在，直接更新；若不存在，执行插入
     * @param files
     * @param req
     * @return
     */
    @RequestMapping(value = "/dispatcher/svnParameter/importFromExcel",method = RequestMethod.POST)
    public ResponseData addSvnParametersFromExcel(@RequestParam("excelFiles") CommonsMultipartFile[] files, HttpServletRequest req){

        List<SvnParameter> svnParameterList = null;
        ResponseData rd = null;
        try {
            svnParameterList = svnParameterService.batchCreateFromExcel(files);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("从Excel中批量导入SVN 参数中途出错",e);
            rd = new ResponseData(false);
            rd.setMessage("从Excel中批量导入SVN 参数中途出错");
            return rd;
        }

        rd = new ResponseData(svnParameterList);
        return rd;
    }
}
