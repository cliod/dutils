package com.wobangkj.domain;

import com.wobangkj.api.SessionSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-13 14:23:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query implements SessionSerializable {
	private static final long serialVersionUID = 8436350713244516781L;
	private String related;
	private String query;
}
