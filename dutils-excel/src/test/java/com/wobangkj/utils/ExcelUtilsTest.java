package com.wobangkj.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cliod
 * @since 1/5/21 11:35 AM
 */
public class ExcelUtilsTest {

	@Test
	public void write() throws IOException {
		List<Model> models = new ArrayList<>();
		Model model;
		for (int i = 0; i < 10; i++) {
			model = new Model();
			model.setName("测试" + i);
			model.setPrice(i + "元");
			models.add(model);
		}
		ExcelUtils.write(models, Model.class);
	}

	@Data
	public static class Model {
		@ExcelProperty(value = "名称")
		private String name;
		@ExcelProperty(value = "价格")
		private String price;
	}
}