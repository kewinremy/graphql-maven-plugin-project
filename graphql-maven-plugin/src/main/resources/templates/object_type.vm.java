package ${package};

import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author generated by graphql-maven-plugin
 */
public class ${object.name} #if($object.implementz.size()>0)implements #foreach($impl in $object.implementz)$impl#if($foreach.hasNext), #end#end#end {

#foreach ($field in $object.fields)
	#if(${field.list})@JsonDeserialize(contentAs = ${field.type.concreteClassSimpleName}.class)#end
	#if(${field.list})List<#end${field.type.classSimpleName}#if(${field.list})>#end ${field.name};
#end

#foreach ($field in $object.fields)
	public void set${field.pascalCaseName}(#if(${field.list})List<#end${field.type.classSimpleName}#if(${field.list})>#end ${field.name}) {
		this.${field.name} = ${field.name};
	}

	public #if(${field.list})List<#end${field.type.classSimpleName}#if(${field.list})>#end get${field.pascalCaseName}() {
		return ${field.name};
	}
	
#end
    public String toString() {
        return "${object.name} {"
#foreach ($field in $object.fields)
				+ "${field.name}: " + ${field.name}
#if($foreach.hasNext)
				+ ", "
#end 
#end
        		+ "}";
    }
}
