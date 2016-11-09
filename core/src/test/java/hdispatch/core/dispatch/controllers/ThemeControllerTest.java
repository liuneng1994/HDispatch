package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.system.dto.ResponseData;
import hdispatch.core.dispatch.TestUtil;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.service.HdispatchAuthorityService;
import hdispatch.core.dispatch.service.ThemeService;
import org.easymock.EasyMock;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

/**
 * ThemeController Tester.
 *
 * @author <Yazheng Young,yazheng.yang@hand-china.com>
 * @version 1.0
 * @since <pre>11/03/2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml","classpath:/spring/applicationContext-*.xml","classpath:/spring/appServlet/servlet-context.xml"})
@Transactional
@Rollback
public class ThemeControllerTest {
    @InjectMocks
    private ThemeController themeController;

    private MockMvc mockMvc;

    @Mock
    private ThemeService themeService;
    @Mock
    private HdispatchAuthorityService hdispatchAuthorityService;

    @Mock
    private Validator validator;

    @Mock
    private MessageSource messageSource;

    //用于模拟的数据
    private List<Theme> themesMock;
    private MockHttpServletRequest request;


    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(themeController).build();

        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("userId",3);
    }

    @After
    public void after() throws Exception {
//        themeController = null;
//        mockMvc = null;
//        themeService = null;
//        hdispatchAuthorityService = null;
//        themesMock = null;
    }

    /**
     * Method: getThemes(HttpServletRequest request, @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page, @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize, @RequestParam(name = "themeName", defaultValue = "") String themeName, @RequestParam(name = "themeDescription", defaultValue = "") String themeDescription)
     */
    @Test
    public void testGetThemes() throws Exception {
        //初始化模拟数据
        themesMock = new ArrayList<>();
        List<Theme> list_1 = new ArrayList<>();

        for(int i = 1; i <11; i++){
            Theme temp_1 = new Theme();
            Theme temp_2 = new Theme();
            temp_1.setThemeId(new Long(i)).setThemeName("abc"+i).setThemeDescription("Desc_abc"+i).setThemeSequence(new Long(i)).setThemeActive(1L);
            temp_2.setThemeId(new Long(i*2)).setThemeName("def"+i*2).setThemeDescription("Desc_def"+i*2).setThemeSequence(new Long(i*2)).setThemeActive(1L);
            themesMock.add(temp_1);
            themesMock.add(temp_2);
            list_1.add(temp_1);
        }
        IRequest requestContext = RequestHelper.createServiceRequest(request);
        //模拟的返回数据规则
        when(themeService.selectByTheme(anyObject(),anyObject(),eq(1),eq(20)))
                .thenReturn(themesMock);
//        when(themeService.selectByTheme(anyObject(),eq(null),anyInt(),anyInt()))
//                .thenThrow(NullPointerException.class);
        when(themeService.selectByTheme(anyObject(),anyObject(),eq(1),eq(10)))
                .thenReturn(list_1);
        //用例
        mockMvc.perform(post("/dispatcher/theme/query")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .param("page","1")
                .param("pageSize","20")
                .param("themeName","name")
                .param("themeDescription","desc"))
//                .content("{\"page\":1,\"pageSize\":20,\"themeName\":\"name\",\"themeDescription\":\"desc\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(20)));
        mockMvc.perform(post("/dispatcher/theme/query")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .param("page","1")
                .param("pageSize","10")
                .param("themeName","name")
                .param("themeDescription","desc"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(10)));

        mockMvc.perform(post("/dispatcher/theme/query")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(10)));//默认的数据条数

    }

    /**
     * Method: getAllThemes_operate(HttpServletRequest request)
     */
    @Test
    public void testGetAllThemes_operate() throws Exception {
        List<Theme> list = new ArrayList<>();
        for(int i = 1; i <11; i++){
            Theme temp_1 = new Theme();
            Theme temp_2 = new Theme();
            temp_1.setThemeId(new Long(i)).setThemeName("abc"+i).setThemeDescription("Desc_abc"+i).setThemeSequence(new Long(i)).setThemeActive(1L);
            temp_2.setThemeId(new Long(i*2)).setThemeName("def"+i*2).setThemeDescription("Desc_def"+i*2).setThemeSequence(new Long(i*2)).setThemeActive(1L);
            list.add(temp_1);
            list.add(temp_2);
        }
        Mockito.when(themeService.selectAll_opt(anyObject())).thenReturn(list);

        mockMvc.perform(get("/dispatcher/theme/queryAll_opt")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(eq(list.size()))));
    }

    /**
     * Method: getAllThemes_read(HttpServletRequest request)
     */
    @Test
    public void testGetAllThemes_read() throws Exception {
        List<Theme> list = new ArrayList<>();
        for(int i = 1; i <11; i++){
            Theme temp_1 = new Theme();
            temp_1.setThemeId(new Long(i)).setThemeName("abc"+i).setThemeDescription("Desc_abc"+i).setThemeSequence(new Long(i)).setThemeActive(1L);
            list.add(temp_1);
        }
        Mockito.when(themeService.selectAll_opt(anyObject())).thenReturn(list);

        mockMvc.perform(get("/dispatcher/theme/queryAll_read")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(eq(list.size()))));
    }

    /**
     * use case：添加主题
     * Method: addThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request)
     */
    @Test
    public void testAddThemes() throws Exception {
        List<Theme> dataList_1 = new ArrayList<>();
        dataList_1.add(new Theme().setThemeId(1L).setThemeName("theme_1").setThemeDescription("desc_1").setThemeActive(1L));
        dataList_1.add(new Theme().setThemeId(2L).setThemeName("theme_2").setThemeDescription("desc_2").setThemeActive(1L));
        boolean[] isExist = {false,false};
        when(themeService.checkIsExist(anyObject())).thenReturn(isExist);
        when(themeService.batchUpdate(anyObject(),anyObject())).thenReturn(dataList_1);
        byte[] sentDataBytes = TestUtil.convertObjectToJsonBytes(dataList_1);
        mockMvc.perform(post("/dispatcher/theme/submit","json")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(sentDataBytes))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(2)));


        //用于返回已经存在的数据列表
        List<Theme> dataList_2 = new ArrayList<>();
        dataList_2.add(new Theme().setThemeId(3L).setThemeName("theme_3").setThemeDescription("desc_3").setThemeActive(1L));
        dataList_2.add(new Theme().setThemeId(4L).setThemeName("theme_4").setThemeDescription("desc_4").setThemeActive(1L));
        //重新设置返回数据规则
        isExist = new boolean[]{true,true};
        when(themeService.checkIsExist(anyObject())).thenReturn(isExist);
        when(themeService.batchUpdate(anyObject(),anyObject())).thenReturn(dataList_2);
        sentDataBytes = TestUtil.convertObjectToJsonBytes(dataList_2);
        mockMvc.perform(post("/dispatcher/theme/submit","json")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(sentDataBytes))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("message", not(eq(""))));
    }

//    /**
//     * use case：添加主题(主题已经存在)
//     * Method: addThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request)
//     * @throws Exception
//     */
//    @Test
//    public void testAddThemes() throws Exception {
//        //用于返回已经存在的数据列表
//        List<Theme> dataList_2 = new ArrayList<>();
//        dataList_2.add(new Theme().setThemeId(3L).setThemeName("theme_3").setThemeDescription("desc_3").setThemeActive(1L));
//        dataList_2.add(new Theme().setThemeId(4L).setThemeName("theme_4").setThemeDescription("desc_4").setThemeActive(1L));
//        boolean[] isExist = {true,true};
//        when(themeService.checkIsExist(anyObject())).thenReturn(isExist);
//        when(themeService.batchUpdate(anyObject(),anyObject())).thenReturn(eq(null));
//        byte[] sentDataBytes = TestUtil.convertObjectToJsonBytes(dataList_2);
//        mockMvc.perform(post("/dispatcher/theme/submit")
//                .contentType(TestUtil.APPLICATION_JSON_UTF8)
//                .content(sentDataBytes))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("success", is(false)))
//                .andExpect(MockMvcResultMatchers.jsonPath("message", not(eq(null))))
//                .andExpect(MockMvcResultMatchers.jsonPath("message", not(eq(""))));
//
//    }

    /**
     * Method: deleteThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request)
     */
    @Test
    public void testDeleteThemes() throws Exception {
//TODO: Test goes here... 
    }


} 
