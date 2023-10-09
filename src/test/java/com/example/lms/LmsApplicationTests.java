package com.example.lms;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.lms.service.BookService;
import com.example.lms.vo.BookVO;
import com.example.lms.vo.PageVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class LmsApplicationTests {
    @Resource
    private BookService bookService;

    /**
     * 数据量大的复杂填充
     * <p>
     * 这里的解决方案是 确保模板list为最后一行，然后再拼接table.还有03版没救，只能刚正面加内存。
     */
    @Test
    public void complexFillWithTable() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        ClassLoader classLoader = LmsApplicationTests.class.getClassLoader();
        String templatePath = Objects.requireNonNull(classLoader.getResource("templates//excel//BookTemp.xlsx")).getPath();
        System.out.println("Path of template: " + templatePath);

        try (ExcelWriter excelWriter = EasyExcel.write("/C:/Users/zx/Downloads//" + UUID.randomUUID() + ".xlsx").withTemplate(templatePath).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 写入列表之前的数据
            Map<String, Object> map = new HashMap<>();
            map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,sss").format(new Date()));
            excelWriter.fill(map, writeSheet);

            // 写入列表数据
            excelWriter.fill(data(), writeSheet);

            // 设置列表行之后的统计行
            List<List<String>> totalListList = setStatistics(1, 3, "统计:1000");
            excelWriter.write(totalListList, writeSheet);
        }
    }

    /**
     * 填充统计数据
     *
     * @param lineNumber 列表行之后的第n行
     * @param fieldSerialNumber 第几列
     * @param content 填充内容
     */
    List<List<String>> setStatistics(int lineNumber, int fieldSerialNumber, String content) {
        List<List<String>> totalListList = new ArrayList<>();
        for (int i = 0; i < lineNumber; i++) {
            List<String> totalList = new ArrayList<>();
            for (int j = 0; j < fieldSerialNumber; j++) {
                totalList.add(null);
            }
            if (i == lineNumber - 1) {
                totalList.add(content);
            }
            totalListList.add(totalList);
        }

        return totalListList;
    }

    public List<BookVO> data() {
        // 查询用户表,具体service层实现就不写了
        PageVO<BookVO> pageVO = bookService.pageList(1);
        return pageVO.getData();
    }
}
