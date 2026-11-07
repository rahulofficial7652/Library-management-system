package org.example.library_management_system.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
//Yeh Jackson library ka annotation hai. Agar DTO ke kisi field ki value null hai, toh us field ko final JSON output se ignore kar dega.
public class ErrorResponseDTO {

    private String traceId;
    private LocalDateTime timeStamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;
}
