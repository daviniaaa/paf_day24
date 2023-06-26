package day24.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ModelAndView handleBankAccountNotFoundException (BankAccountNotFoundException ex,
    HttpServletRequest request) {
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimestamp(new Date());
        // errMsg.setMessage("Bank account does not exist or has been deactivated.");
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", errMsg);

        return mav;
    }

    // @ExceptionHandler(BankAccountNotFoundException.class)
    // public ResponseEntity<ErrorMessage> handleBankAccountNotFoundException (BankAccountNotFoundException ex,
    // HttpServletRequest request) {
    //     ErrorMessage errMsg = new ErrorMessage();
    //     errMsg.setStatusCode(404);
    //     errMsg.setTimestamp(new Date());
    //     errMsg.setMessage("Bank account does not exist or has been deactivated.");
    //     errMsg.setDescription(request.getRequestURI());

    //     return ResponseEntity.ok().body(errMsg);
    // }

    @ExceptionHandler(AccountBlockedOrInactiveException.class)
    public ModelAndView handleAccountBlockedOrInactiveException(AccountBlockedOrInactiveException ex,
    HttpServletRequest request) {
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimestamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", errMsg);

        return mav;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ModelAndView handleInsufficientFundsException(InsufficientFundsException ex,
    HttpServletRequest request) {
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimestamp(new Date());
        errMsg.setMessage(ex.getMessage());
        errMsg.setDescription(request.getRequestURI());

        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", errMsg);

        return mav;
    }


    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorMessage> handleHttpServerErrorException(HttpServerErrorException ex,
    HttpServletRequest request) {
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setStatusCode(404);
        errMsg.setTimestamp(new Date());
        errMsg.setMessage("Bank account does not exist or has been deactivated.");
        errMsg.setDescription(request.getRequestURI());

        return ResponseEntity.ok().body(errMsg);
    } 
}
