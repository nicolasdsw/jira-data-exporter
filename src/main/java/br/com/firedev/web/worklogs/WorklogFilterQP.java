package br.com.firedev.web.worklogs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY, description = "", name = "startdate.start", schema = @Schema(type = "localDate", example = "2022-07-01"))
@Parameter(in = ParameterIn.QUERY, description = "", name = "startdate.end", schema = @Schema(type = "localDate", example = "2022-07-30"))
@Parameter(in = ParameterIn.QUERY, description = "", name = "issueId", schema = @Schema(type = "long", example = ""))
@Parameter(in = ParameterIn.QUERY, description = "", name = "issue", schema = @Schema(type = "string", example = ""))
@Parameter(in = ParameterIn.QUERY, description = "", name = "projectId", schema = @Schema(type = "long", example = ""))
@Parameter(in = ParameterIn.QUERY, description = "", name = "authorIds", array = @ArraySchema(schema = @Schema(type = "long", example = "")))
public @interface WorklogFilterQP {

}
