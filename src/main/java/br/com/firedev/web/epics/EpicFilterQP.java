package br.com.firedev.web.epics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY, description = "", name = "id", schema = @Schema(type = "long", example = ""))
@Parameter(in = ParameterIn.QUERY, description = "", name = "epic", schema = @Schema(type = "string", example = "*-*"))
@Parameter(in = ParameterIn.QUERY, description = "", name = "created.start", schema = @Schema(type = "localDate", example = "2022-07-01"))
@Parameter(in = ParameterIn.QUERY, description = "", name = "created.end", schema = @Schema(type = "localDate", example = "2022-07-30"))
@Parameter(in = ParameterIn.QUERY, description = "", name = "duedate.start", schema = @Schema(type = "localDate", example = ""))
@Parameter(in = ParameterIn.QUERY, description = "", name = "duedate.end", schema = @Schema(type = "localDate", example = ""))
public @interface EpicFilterQP {

}
