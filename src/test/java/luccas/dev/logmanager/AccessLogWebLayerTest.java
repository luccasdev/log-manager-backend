package luccas.dev.logmanager;


import com.fasterxml.jackson.databind.ObjectMapper;
import luccas.dev.logmanager.controller.v1.AccessLogController;
import luccas.dev.logmanager.controller.v1.AccessLogDto;
import luccas.dev.logmanager.controller.v1.AccessLogMapper;
import luccas.dev.logmanager.model.AccessLog;
import luccas.dev.logmanager.service.AccessLogService;
import luccas.dev.logmanager.utils.Pages;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccessLogController.class)
@ActiveProfiles("test")
public class AccessLogWebLayerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    AccessLogService accessLogService;

    @Test
    public void get_accessLogById_returnsOkWithAccessLog() throws Exception {

        AccessLog accessLog = new AccessLog();
        accessLog.setCreatedAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        accessLog.setId(1L);
        accessLog.setIpAddress("192.168.234.82");
        accessLog.setRequestLine("GET / HTTP/1.1");
        accessLog.setResponseStatus(200);
        accessLog.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");

        Mockito.when(accessLogService.findById(1L)).thenReturn(accessLog);

        mockMvc.perform(MockMvcRequestBuilders.get("/access-log/v1/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ipAddress").value("192.168.234.82"));
    }

    @Test
    public void get_allAccessLogById_returnsOkWithAllAccessLog() throws Exception {

        List<AccessLog> accessLogList = new ArrayList<>();

        AccessLog accessLogOne = new AccessLog();
        accessLogOne.setCreatedAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        accessLogOne.setId(1L);
        accessLogOne.setIpAddress("192.168.234.82");
        accessLogOne.setRequestLine("GET / HTTP/1.1");
        accessLogOne.setResponseStatus(200);
        accessLogOne.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");

        AccessLog accessLogTwo = new AccessLog();
        accessLogTwo.setCreatedAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        accessLogTwo.setId(2L);
        accessLogTwo.setIpAddress("192.168.169.194");
        accessLogTwo.setRequestLine("GET / HTTP/1.1");
        accessLogTwo.setResponseStatus(200);
        accessLogTwo.setUserAgent("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0");

        accessLogList.add(accessLogOne);
        accessLogList.add(accessLogTwo);

        Mockito.when(accessLogService.findAll(PageRequest.of(0, 20)))
                .thenReturn(Pages.duplicate(accessLogList, PageRequest.of(0, 20), 20));

        mockMvc.perform(MockMvcRequestBuilders.get("/access-log/v1/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].ipAddress").value("192.168.234.82"));
    }

    @Test
    public void post_createAccessLog_returnsOkWithAccessLogAndStatusCreated() throws Exception {
        AccessLogDto accessLogDto = new AccessLogDto();
        accessLogDto.setCreatedAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        accessLogDto.setIpAddress("192.168.217.28");
        accessLogDto.setRequestLine("GET / HTTP/1.1");
        accessLogDto.setResponseStatus(200);
        accessLogDto.setUserAgent("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0");

        Mockito.when(accessLogService.create(Mockito.any(AccessLog.class))).thenReturn(AccessLogMapper.dtoToEntity(accessLogDto));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/access-log/v1")
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(accessLogDto));

        mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ipAddress").value("192.168.217.28"))
                .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(accessLogDto)));
    }

    @Test
    public void put_updateAccessLog_returnsOkWithAccessLog() throws Exception {
        AccessLogDto accessLogDto = new AccessLogDto();
        accessLogDto.setCreatedAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        accessLogDto.setIpAddress("192.168.217.28");
        accessLogDto.setId(1L);
        accessLogDto.setRequestLine("GET / HTTP/1.1");
        accessLogDto.setResponseStatus(200);
        accessLogDto.setUserAgent("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0");

        Mockito.when(accessLogService.update(Mockito.any(AccessLog.class))).thenReturn(AccessLogMapper.dtoToEntity(accessLogDto));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/access-log/v1")
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(accessLogDto));

        mockMvc.perform(builder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ipAddress").value("192.168.217.28"))
                .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(accessLogDto)));
    }

    @Test
    public void delete_deleteAccessLogById_returnsOk() throws Exception {
        mockMvc.perform(delete("/access-log/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
