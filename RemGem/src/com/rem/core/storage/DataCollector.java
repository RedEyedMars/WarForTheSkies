package com.rem.core.storage;

import java.io.IOException;

public interface DataCollector {

	public Integer collect(Integer value) throws IOException;
	public Float collect(Float value) throws IOException;
	public Long collect(Long value) throws IOException;
	public Boolean collect(Boolean value) throws IOException;
	public String collect(String value) throws IOException;
}
