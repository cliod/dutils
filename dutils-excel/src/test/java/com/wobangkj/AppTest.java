package com.wobangkj;

import com.wobangkj.api.Pageable;
import com.wobangkj.utils.ExcelUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() throws IOException {
		assertTrue(true);

		File file = ExcelUtils.write(new ArrayList<Object>() {{
			add(new Pageable() {
				@Override
				public Integer getPage() {
					return 1;
				}

				@Override
				public Integer getSize() {
					return 2;
				}
			});
		}});
		System.out.println(file.exists());
	}
}
