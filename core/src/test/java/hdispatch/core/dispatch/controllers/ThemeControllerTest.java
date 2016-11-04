package hdispatch.core.dispatch.controllers;

import com.hand.hap.core.IRequest;
import hdispatch.core.dispatch.dto.theme.Theme;
import hdispatch.core.dispatch.service.HdispatchAuthorityService;
import hdispatch.core.dispatch.service.ThemeService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ThemeController Tester.
 *
 * @author <Yazheng Young,yazheng.yang@hand-china.com>
 * @version 1.0
 * @since <pre>11/03/2016</pre>
 */
public class ThemeControllerTest {
    @InjectMocks
    private ThemeController themeController;

    private MockMvc mockMvc;

    @Mock
    private ThemeService themeService;
    @Mock
    private HdispatchAuthorityService hdispatchAuthorityService;

    //用于模拟的数据
    private List<Theme> themesMock;
    private MockHttpServletRequest request;


    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(themeController).build();

        //初始化模拟数据
        themesMock = new ArrayList<>();
        List<Theme> list_1 = new ArrayList<>();
        List<Theme> list_2 = new ArrayList<>();

        for(int i = 1; i <11; i++){
            Theme temp_1 = new Theme();
            Theme temp_2 = new Theme();
            temp_1.setThemeId(new Long(i)).setThemeName("abc"+i).setThemeDescription("Desc_abc"+i).setThemeSequence(new Long(i)).setThemeActive(1L);
            temp_2.setThemeId(new Long(i*2)).setThemeName("def"+i*2).setThemeDescription("Desc_def"+i*2).setThemeSequence(new Long(i*2)).setThemeActive(1L);
            themesMock.add(temp_1);
            themesMock.add(temp_2);
            list_1.add(temp_1);
            list_2.add(temp_2);
        }
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("userId",3);

        //返回数据规则
        when(themeService.selectByTheme((IRequest) request,new Theme(),1,30)).thenReturn(themesMock);


    }

    @After
    public void after() throws Exception {
        themeController = null;
        mockMvc = null;
        themeService = null;
        hdispatchAuthorityService = null;
        themesMock = null;
    }

    /**
     * Method: getThemes(HttpServletRequest request, @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page, @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize, @RequestParam(name = "themeName", defaultValue = "") String themeName, @RequestParam(name = "themeDescription", defaultValue = "") String themeDescription)
     */
    @Test
    public void testGetThemes() throws Exception {

    }

    /**
     * Method: getAllThemes_operate(HttpServletRequest request)
     */
    @Test
    public void testGetAllThemes_operate() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAllThemes_read(HttpServletRequest request)
     */
    @Test
    public void testGetAllThemes_read() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request)
     */
    @Test
    public void testAddThemes() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteThemes(@RequestBody List<Theme> themeList, BindingResult result, HttpServletRequest request)
     */
    @Test
    public void testDeleteThemes() throws Exception {
//TODO: Test goes here... 
    }


} 
