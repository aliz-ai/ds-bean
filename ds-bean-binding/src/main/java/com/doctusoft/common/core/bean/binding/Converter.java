package com.doctusoft.common.core.bean.binding;

public interface Converter<Source, Target> {
	
	Target convertSource(Source source);
	
	Source convertTarget(Target target);

}
