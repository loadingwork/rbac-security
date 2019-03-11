package com.iveiv.rbac.base.ext.jackson;

import com.iveiv.rbac.base.annotation.DictTransform;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JscksonTest {
	
	@DictTransform(code="1111", type="1111")
	private String name;

}
