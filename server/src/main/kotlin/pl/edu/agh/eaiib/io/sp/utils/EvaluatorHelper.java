package pl.edu.agh.eaiib.io.sp.utils;

public class EvaluatorHelper {
    public static Integer evaluate(Double accRead, Double min, Double max) {
        if(accRead<=9.81){
            double diff = 9.81 - min;
            if(accRead > 9.81 - diff/10 ){ return 10; }
            else if(accRead > 9.81 - 2*diff/10){ return 9; }
            else if(accRead > 9.81 - 3*diff/10){ return 8; }
            else if(accRead > 9.81 - 4*diff/10){ return 7; }
            else if(accRead > 9.81 - 5*diff/10){ return 6; }
            else if(accRead > 9.81 - 6*diff/10){ return 5; }
            else if(accRead > 9.81 - 7*diff/10){ return 4; }
            else if(accRead > 9.81 - 8*diff/10){ return 3; }
            else if(accRead > 9.81 - 9*diff/10){ return 2; }
            else { return 1; }
        } else {
            double diff = max - 9.81;
            if(accRead < 9.81 + diff/10){ return 10; }
            else if(accRead < 9.81 + 2*diff/10){ return 9; }
            else if(accRead < 9.81 + 3*diff/10){ return 8; }
            else if(accRead < 9.81 + 4*diff/10){ return 7; }
            else if(accRead < 9.81 + 5*diff/10){ return 6; }
            else if(accRead < 9.81 + 6*diff/10){ return 5; }
            else if(accRead < 9.81 + 7*diff/10){ return 4; }
            else if(accRead < 9.81 + 8*diff/10){ return 3; }
            else if(accRead < 9.81 + 9*diff/10){ return 2; }
            else { return 1; }
        }
    }
}
