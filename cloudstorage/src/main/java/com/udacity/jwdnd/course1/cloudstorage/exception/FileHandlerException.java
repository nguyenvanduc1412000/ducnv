package com.udacity.jwdnd.course1.cloudstorage.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class FileHandlerException {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("errorMessage", "File size exceeds the maximum allowed size.");
        return modelAndView;
    }
}
