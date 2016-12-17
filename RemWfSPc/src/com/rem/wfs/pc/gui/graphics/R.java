package com.rem.wfs.pc.gui.graphics;

import java.io.File;
import java.io.InputStream;


public class R extends com.rem.wfs.graphics.R {


	public static InputStream getResource(String name){
		return R.class.getClassLoader().getResourceAsStream("com/rem/wfs/pc/gui/graphics/"+name.replace(File.separator, "/"));
	}

	public static File getFile(String name){
		return new File(R.class.getClassLoader().getResource("com/rem/wfs/pc/gui/graphics/"+name.replace(File.separator, "/")).getFile());
	}
	public static File getSourceLocation() {
		return new File(R.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	}

}
