package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.dto.svn.SvnParameter;
import hdispatch.core.dispatch.service.SvnParameterService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    @RequestMapping(value = "/dispatcher/svnParameter/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getSvnParameters(HttpServletRequest request,
                                @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                @RequestParam(name = "subjectName", defaultValue = "") String subjectName,
                                @RequestParam(name = "mappingName", defaultValue = "") String mappingName,
                                @RequestParam(name = "parameterName", defaultValue = "") String parameterName) {
        IRequest requestContext = createRequestContext(request);
        if(!svnParameterService.hasReadPermission(requestContext)){
            return new ResponseData(true);
        }
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
        svnParameter.setSubjectName(subjectName);
        svnParameter.setMappingName(mappingName);
        svnParameter.setParameterName(parameterName);
        List<SvnParameter> svnParameterList = svnParameterService.selectBySvnParameter(requestContext,svnParameter,page,pageSize);
        ResponseData rd = new ResponseData(svnParameterList);

        return rd;
    }


    /**
     * 创建任务运行时参数
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
        IRequest requestContext = createRequestContext(request);
        rd = new ResponseData(svnParameterService.batchUpdate(requestContext,svnParameterList,initFeedbackMsg(request)));
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
        return new ResponseData(svnParameterService.batchUpdate(requestContext,svnParameterList,initFeedbackMsg(request)));
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
        rd = new ResponseData(svnParameterService.batchUpdate(requestContext,svnParameterList,initFeedbackMsg(request)));
        return rd;
    }


    /**
     * 从excel文件中导入参数。
     * 根据subjectName、mappingName、parameterName判断是否已经存在，若存在，直接更新；若不存在，执行插入
     * @param request
     * @return
     */
    @RequestMapping(value = "/dispatcher/svnParameter/importFromExcel",method = RequestMethod.POST)
    public ResponseData addSvnParametersFromExcel(HttpServletRequest request) throws FileUploadException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        Locale locale = RequestContextUtils.getLocale(request);
        // 不是文件上传
        if (!isMultipart) {
            ResponseData responseData = new ResponseData(false);
            String errorMessage = getMessageSource().getMessage("hdispatch.upload_file.no_file", null, locale);
            responseData.setMessage(errorMessage);
            return responseData;
        }
        // 初始化执行链
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> items = upload.parseRequest(request);
        if(null == items || 0 == items.size()){
            ResponseData responseData = new ResponseData(false);
            String errorMessage = getMessageSource().getMessage("hdispatch.upload_file.no_file", null, locale);
            responseData.setMessage(errorMessage);
            return responseData;
        }
        FileItem item = items.get(0);
        CommonsMultipartFile[] filesFromHap = new CommonsMultipartFile[1];
        filesFromHap[0] = new CommonsMultipartFile(item);

        List<SvnParameter> svnParameterList = null;
        ResponseData rd = null;
        IRequest requestContext = createRequestContext(request);
        try {
            svnParameterList = svnParameterService.batchCreateFromExcel(requestContext,filesFromHap,initFeedbackMsg(request));
        } catch (Exception e) {
            String errorTips = getMessageSource().getMessage("hdispatch.error.job_parameter.error_during_import_from_excel",null,locale);
            logger.error(errorTips,e);
            rd = new ResponseData(false);
            rd.setMessage(errorTips);
            return rd;
        }

        rd = new ResponseData(svnParameterList);
        return rd;
    }

    /**
     * 判断当前用户是否有操作任务运行时参数的权限<br>
     * @param request
     * @return ResponseData中的total如果大于0表示有操作权限；否则，没有操作权限
     */
    @RequestMapping(value = "/dispatcher/svnParameter/hasOperatePermission", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData hasOperatePermission(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ResponseData rd = null;
        List<String> list = new ArrayList<>();
        boolean hasPermission = svnParameterService.hasOperatePermission(requestContext);
        if(hasPermission) {
            list.add("YOU");
            list.add("HAVE");
            list.add("THIS");
            list.add("PAGE");
            list.add("OPERATION");
            list.add("PERMISSION");
        }
        rd = new ResponseData(list);
        return rd;
    }

    private Map<String,String> initFeedbackMsg(HttpServletRequest request){
        Map<String,String> feedbackMsg = new HashedMap();
        Locale locale = RequestContextUtils.getLocale(request);
        feedbackMsg.put("ALREADY_EXIST",getMessageSource().getMessage("hdispatch.server.error_tips.already_exist", null, locale));
        feedbackMsg.put("ERROR_DURING_SAVE",getMessageSource().getMessage("hdispatch.job.job_create.error_during_saving",null,locale));
        feedbackMsg.put("ERROR_DURING_DELETE",getMessageSource().getMessage("hdispatch.job.job_create.error_during_deleting",null,locale));

        return feedbackMsg;
    }
}
